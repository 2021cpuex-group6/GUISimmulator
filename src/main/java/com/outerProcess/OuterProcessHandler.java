package com.outerProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.MainWindow.MainWindow;
import com.utils.ConstantsClass;
import com.utils.ErrorChecker;

public class OuterProcessHandler {
    //外部のシミュレータとの通信をするクラス
    private final static String SIMMULATOR_EXE = "main.exe";
    private final static String CHARA_CODE = "Shift-JIS";
    private final static String ERROR_CODE = "Error";
    private final static String BUG_REPORT = "バグと思われるので報告お願いします。";
    private final static String INVALID_ARG_BUG = "バグ: 引数を間違えています。";
    private final static String ALREADY_END = "すでに終了しています。右クリックメニューからリセットをしてください(未実装)。";
    private final static String NO_HISTORY = "これ以上戻れません。";
    private final static String FILE_ENDED = "終了しました。";

    protected final static String COMMAND_DO_ALL = "a";
    protected final static String COMMAND_NEXT_BLOCK = "nb";
    protected final static String COMMAND_NEXT = "n";
    protected final static String COMMAND_BREAK_SET = "bs";
    // protected final static String COMMAND_BREAK_LIST = "bl";
    protected final static String COMMAND_BREAK_DELETE = "bd";
    protected final static String COMMAND_REG_READ = "rr";
    protected final static String COMMAND_REG_WRITE = "rw";
    protected final static String COMMAND_BACK = "ba";
    protected final static String COMMAND_RESET = "re";
    protected final static String COMMAND_INFO = "i";
    protected final static String COMMAND_QUIT = "quit";

    private final static String RES_END = "End";
    private final static String RES_ALREADY_END = "AEnd";
    private final static String RES_Stop = "Stop";
    private final static String RES_NO_CHANGE = "No";
    private final static String RES_NO_HISTORY = "NoHis";
    
    private MainWindow mainWindow;
    private PrintStream sender; // プログラムに送る
    private BufferedReader receiver; //結果受け取り
    private Process process;
    public OuterProcessHandler(MainWindow main, String path){
        // 開くアセンブリファイル (or バイナリコード)を指定してプロセスを起動する
        this.mainWindow = main;
        try {
            process = Runtime.getRuntime().exec(SIMMULATOR_EXE + " \""+ path + "\" -g");
            sender = new PrintStream(process.getOutputStream());
            receiver = new BufferedReader(new InputStreamReader(process.getInputStream(), CHARA_CODE));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ErrorChecker.errorCheck(e);
        }
    }

    private void sendCommand(String message){
        // プロセスにコマンドを送る
        sender.println(message);
        sender.flush();
    }

    private String receiveWithCheck(){
        // プロセスからメッセージを受け取り、Errorかどうかチェックする
        try {
            String message = receiver.readLine();
            if(message.startsWith(ERROR_CODE)){
                // シミュレータでエラー
                int lineN = Integer.parseInt(receiver.readLine());
                if(lineN >= 0){
                    message += System.lineSeparator() + String.format("%d行目", lineN);
                }
                message += System.lineSeparator() + receiver.readLine();
                if(lineN <= 0){
                    message += System.lineSeparator() + BUG_REPORT;
                }
                JFrame frame = new JFrame();

                JOptionPane.showMessageDialog(frame, message);
                process.destroy();
                System.exit(-1);
            }
            return message;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ErrorChecker.errorCheck(e);
            return null;
        }
    }

    public void doSingleCommand(Command command){
        // 特に引数を受け取らない命令を実行する
        if(command == Command.BreakDelete || command == Command.BreakSet || 
                command == Command.RegWrite){
            ErrorChecker.errorCheck(INVALID_ARG_BUG + " doSingleCommand内");
        }
        sendCommand(command.getCommand());

        String res;
        switch(command){
            case DoAll:
                res  = receiveWithCheck();
                if(res.startsWith(RES_ALREADY_END)){
                    // すでに終了済
                    mainWindow.setMessage(ALREADY_END);
                    return;
                }else{
                    readRegisters();
                    int nowPC = mainWindow.connecter.getPC();
                    mainWindow.connecter.showNowInstruction(nowPC-ConstantsClass.INSTRUCTION_BYTE_N);
                }
                break;
            case DoNext:
                res = receiveWithCheck();
                checkEnd();
                checkRegChange(res, false);
                break;
            case Back:
                res = receiveWithCheck();
                checkRegChange(res, true);
                break;
            case Quit:
                resErrorCheck();
                break;
                


        }
    }

    private void checkEnd() {
        // nextでちょうど終了したとき、追加で End とくるので、それが来ていたら検知
        try {
            if(receiver.ready()){
                if(receiver.readLine().startsWith(RES_END)){
                    mainWindow.setMessage(FILE_ENDED);
                    
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ErrorChecker.errorCheck(e);
        }
    }

    public void shutdown(){
        // main.exe を終了する
        doSingleCommand(Command.Quit);
    }

    public void writeRegister(int index, boolean forInteger, int value){
        String targetReg = "";
        if(forInteger){
            if(index == ConstantsClass.REGISTER_N){
                targetReg = "pc";
            }else{
                targetReg = ConstantsClass.INTEGER_REGISTER_PREFIX+String.format("%02d", index);
            }
        }else{
            return;
        }
        String message = COMMAND_REG_WRITE + " " + targetReg +" "  +value;
        sendCommand(message);
        resErrorCheck();

    }

    private void resErrorCheck(){
        // 正常なら何も返されないときにエラーかどうかを調べる
        try {
            if(receiver.ready()){
                if(receiver.readLine().startsWith(ERROR_CODE)){
                    String message = ERROR_CODE;
                    int lineN = Integer.parseInt(receiver.readLine());
                    if(lineN >= 0){
                        message += System.lineSeparator() + String.format("%d行目", lineN);
                    }
                    message += System.lineSeparator() + receiver.readLine();
                    if(lineN <= 0){
                        message += System.lineSeparator() + BUG_REPORT;
                    }
                    JFrame frame = new JFrame();
    
                    JOptionPane.showMessageDialog(frame, message);
                    process.destroy();
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ErrorChecker.errorCheck(e);
        }
    }

    private void checkRegChange(String res, boolean back){
        // 命令実行(順方向、逆方向)後、レジスタの変更を確認
        int showInstructionPC =  mainWindow.connecter.getPC();
        if(back) showInstructionPC -= ConstantsClass.INSTRUCTION_BYTE_N;
        int factor = back ? -1 : 1;
        if(res.startsWith(RES_ALREADY_END)){
            // 終了済み
            mainWindow.setMessage(ALREADY_END);
            return;
            
        }else if(res.startsWith(RES_NO_HISTORY)){
            mainWindow.setMessage(NO_HISTORY);
            return;
        } else if(!res.startsWith(RES_NO_CHANGE)){
            String resList[] = res.split(" ");
            int regInd = 0;

            if(resList[0].equals("pc")){
                regInd = ConstantsClass.REGISTER_N;   
            }else {
                regInd = Integer.parseInt(resList[0].substring(1));
                // pcが変更されてなければインクリメント

                mainWindow.connecter.pcIncrement(factor);

            }
            mainWindow.connecter.setRegister(true, regInd, Integer.parseInt(resList[1]),
                        true);
                        
        }else{
            mainWindow.connecter.pcIncrement(factor);
            mainWindow.connecter.clearHighlight();
        }
        mainWindow.connecter.showNowInstruction(showInstructionPC);
    }

    private void readRegisters(){
        // レジスタの情報を受け取り、更新
        sendCommand(COMMAND_REG_READ);
        String[] results = new String[ConstantsClass.REGISTER_KINDS];
        results[0] = receiveWithCheck();
        results[1] = receiveWithCheck();
        updateRegisters(results);
    }
    
    private void updateRegisters(String[] res){
        // レジスタの更新
        // シミュレータからの結果を改行ごとに区切って配列で受け取る
        mainWindow.connecter.setRegister(true, ConstantsClass.REGISTER_N, Integer.parseInt(res[0]), false);
        String[] results = res[1].split(" ");
        for(int i = 0; i < ConstantsClass.REGISTER_N; i++){
            mainWindow.connecter.setRegister(true, i, Integer.parseInt(results[i]), false);
        }

    }

    
}
