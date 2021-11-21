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
    private final static String WARNING_CODE = "Warning";
    private final static String BUG_REPORT = "バグと思われるので報告お願いします。";
    private final static String INVALID_ARG_BUG = "バグ: 引数を間違えています。";
    private final static String ALREADY_END = "すでに終了しています。ツールメニュー→リセットを押してください。";
    private final static String NO_HISTORY = "これ以上戻れません。";
    private final static String FILE_ENDED = "終了しました。";
    private final static String NOW_EXECUTING = "実行中…";
    private final static String STOPPED = "中断しました";

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
    protected final static String COMMAND_MEMREAD = "mr";

    private final static String RES_END = "End";
    private final static String RES_ALREADY_END = "AEnd";
    private final static String RES_Stop = "Stop";
    private final static String RES_NO_CHANGE = "No";
    private final static String RES_NO_HISTORY = "NoHis";
    private final static String RES_MEMCHANGE = "mem";
    private final static String RES_SEND = "send";
    
    private MainWindow mainWindow;
    private PrintStream sender; // プログラムに送る
    private BufferedReader receiver; //結果受け取り
    private Process process;
    // 開くアセンブリファイル (or バイナリコード)を指定してプロセスを起動する
    public OuterProcessHandler(MainWindow main, String path){
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

    // プロセスにコマンドを送る
    private void sendCommand(String message){
        sender.println(message);
        sender.flush();
    }

    // プロセスからメッセージを受け取り、Errorかどうかチェックする
    private String receiveWithCheck(){
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

    // startAddressを開始時点として，MEMORY_SHOW_LINE_N行分のメモリを更新する
    public void memSynchronize(){
        long startAddress = mainWindow.connecter.getMemStartAddress();
        int wordN = ConstantsClass.MEMORY_COLUMN_N / ConstantsClass.WORD_BYTE_N * ConstantsClass.MEMORY_SHOW_LINE_N;
        String command = COMMAND_MEMREAD + " " + startAddress + " " + wordN;
        sendCommand(command);
        boolean loopFlag = true;
        long nowAddress = startAddress;
        while(loopFlag){
            // 一行ずつパースし，テーブルに書き込む
            String[] resList = receiveWithCheck().split(" ");
            for (String string : resList) {
                byte value = Byte.parseByte(string);
                mainWindow.connecter.memSetByte(nowAddress++, value);
            }
            wordN -= resList.length / ConstantsClass.WORD_BYTE_N;
            loopFlag = (wordN > 0);
        }


    }

    // 特に引数を受け取らない命令を実行する
    public void doSingleCommand(Command command){
        if(command == Command.BreakDelete || command == Command.BreakSet || 
                command == Command.RegWrite){
            ErrorChecker.errorCheck(INVALID_ARG_BUG + " doSingleCommand内");
        }
        sendCommand(command.getCommand());

        String res;
        switch(command){
            case DoAll:
                mainWindow.setMessage(NOW_EXECUTING);
                res  = receiveWithCheck();
                if(res.startsWith(RES_ALREADY_END)){
                    // すでに終了済
                    mainWindow.setMessage(ALREADY_END);
                    return;
                }else{
                    readRegisters();
                    int nowPC = mainWindow.connecter.getPC();
                    mainWindow.connecter.showNowInstruction(nowPC-ConstantsClass.INSTRUCTION_BYTE_N);
                    memSynchronize();
                    mainWindow.setMessage(FILE_ENDED);
                }
                break;
            case DoNext:
                res = receiveWithCheck();
                if(res.startsWith(RES_END)){
                    mainWindow.setMessage(FILE_ENDED);
                    res = receiveWithCheck();
                }else if(res.startsWith(RES_ALREADY_END)){
                    mainWindow.setMessage(ALREADY_END);
                    break;
                }
                checkDif(res, false);
                break;
            case Back:
                res = receiveWithCheck();
                if(res.startsWith(RES_NO_HISTORY)){
                    mainWindow.setMessage(NO_HISTORY);
                    break;
                }
                checkDif(res, true);
                break;
            case DoNextBreak:
                mainWindow.setMessage(NOW_EXECUTING);
                res  = receiveWithCheck();
                if(res.startsWith(RES_ALREADY_END)){
                    // すでに終了済
                    mainWindow.setMessage(ALREADY_END);
                    return;
                }else{
                    readRegisters();
                    memSynchronize();
                    mainWindow.setMessage(FILE_ENDED);
                    int nowPC = mainWindow.connecter.getPC();
                    if(res == RES_END) nowPC -= ConstantsClass.INSTRUCTION_BYTE_N;
                    mainWindow.connecter.showNowInstruction(nowPC);
                }
                break;
            case Quit:
                resErrorCheck();
                break;
            default:
                break;
        }
    }


    // 命令実行後，返ってくる差分を調べて反映させる
    private void checkDif(String res, boolean back){
        if(res.startsWith(RES_MEMCHANGE)){
            res = receiveWithCheck();
            checkMemChange(res, back);
            mainWindow.connecter.clearHighlight();;
        }else if(res.startsWith(RES_SEND)){
            return; //mmioはまとめてやる
        }else{
            checkRegChange(res, back);
            mainWindow.connecter.clearMemHighlight();
        }
    }

    private void checkMemChange(String res, boolean back){
        // resはアドレスとLSB側からByteを4つ空白区切りでまとめたもの
        String resList[] = res.split(" ");
        long address = Long.parseLong(resList[0]);
        for (int i = 0; i < ConstantsClass.WORD_BYTE_N; i++) {
            byte value = Byte.parseByte(resList[1+i]);
            mainWindow.connecter.memSetByte(address+i, value);
        }
        mainWindow.connecter.setHighlightWord(address);

        int showInstructionPC =  mainWindow.connecter.getPC();
        if(back) showInstructionPC -= ConstantsClass.INSTRUCTION_BYTE_N;
        int factor = back ? -1 : 1;

        mainWindow.connecter.pcIncrement(factor);
        mainWindow.connecter.showNowInstruction(showInstructionPC);

    }


    // main.exe を終了する
    public void shutdown(){
        doSingleCommand(Command.Quit);
        process.destroy();
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
            if(index == ConstantsClass.REGISTER_N){
            }else{
                targetReg = ConstantsClass.FLOAT_REGISTER_PREFIX+String.format("%02d", index);
            }
        }
        String message = COMMAND_REG_WRITE + " " + targetReg +" "  +value;
        sendCommand(message);
        resErrorCheck();

    }

    // 正常なら何も返されないときにエラーかどうかを調べる
    private void resErrorCheck(){
        try {
            if(receiver.ready()){
                if(receiver.readLine().startsWith(ERROR_CODE) || receiver.readLine().startsWith(WARNING_CODE)){
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

    // 命令実行(順方向、逆方向)後、レジスタの変更を確認
    private void checkRegChange(String res, boolean back){
        int showInstructionPC =  mainWindow.connecter.getPC();
        if(back) showInstructionPC -= ConstantsClass.INSTRUCTION_BYTE_N;
        int factor = back ? -1 : 1;
        mainWindow.connecter.clearHighlight();
        if(res.startsWith(RES_ALREADY_END)){
            // 終了済み
            mainWindow.setMessage(ALREADY_END);
            return;
            
        }else if(res.startsWith(RES_NO_HISTORY)){
            mainWindow.setMessage(NO_HISTORY);
            return;
        } else if(!res.startsWith(RES_NO_CHANGE)){
            boolean pcChanged = false;
            boolean continueFlag;
            do{
                continueFlag  = false;
                // 2箇所以上レジスタが変更されている場合は，表示が数行にわたるため，whileですべて検査
                String resList[] = res.split(" ");
                int regInd = 0;
                boolean isInteger = false;
    
                if(resList[0].equals("pc")){
                    isInteger = true;
                    regInd = ConstantsClass.REGISTER_N;   
                    pcChanged = true;
                }else {
                    isInteger = resList[0].substring(0, 1).equals(ConstantsClass.INTEGER_REGISTER_PREFIX);                    
                    regInd = Integer.parseInt(resList[0].substring(1));
                    
                }
                mainWindow.connecter.setRegister(isInteger, regInd, Integer.parseInt(resList[1]),
                true);

                try {
                    if(receiver.ready()){
                        // まだ表示があるので，再びパース
                        res = receiver.readLine();
                        if(res.startsWith(RES_END)){
                            mainWindow.setMessage(FILE_ENDED);
                        }else{
                            continueFlag = true;
                        }
                    }
                } catch (IOException e) {
                    ErrorChecker.errorCheck(e);
                }

            }while(continueFlag);

            // pcが変更されてなければインクリメント
            if(!pcChanged){
                mainWindow.connecter.pcIncrement(factor);
            }
                        
        }else{
            mainWindow.connecter.pcIncrement(factor);
        }
        mainWindow.connecter.showNowInstruction(showInstructionPC);
    }

    // レジスタの情報を受け取り、更新
    private void readRegisters(){
        sendCommand(COMMAND_REG_READ);
        String[] results = new String[ConstantsClass.REGISTER_KINDS];
        results[0] = receiveWithCheck();
        results[1] = receiveWithCheck();
        results[2] = receiveWithCheck();
        updateRegisters(results);
    }
    
    // レジスタの更新
    private void updateRegisters(String[] res){
        // シミュレータからの結果を改行ごとに区切って配列で受け取る
        mainWindow.connecter.setRegister(true, ConstantsClass.REGISTER_N, Integer.parseInt(res[0]), false);
        String[] resultsI = res[1].split(" ");
        for(int i = 0; i < ConstantsClass.REGISTER_N; i++){
            mainWindow.connecter.setRegister(true, i, Integer.parseInt(resultsI[i]), false);
        }
        String[] resultsF = res[2].split(" ");
        for(int i = 0; i < ConstantsClass.REGISTER_N; i++){
            mainWindow.connecter.setRegister(false, i, Integer.parseInt(resultsF[i]), false);
        }

    }

    public void breakPointChange(boolean set, int address){
        String command = set ? COMMAND_BREAK_SET : COMMAND_BREAK_DELETE ;
        command = command + " " +  address;
        sendCommand(command);
        resErrorCheck();
    }

    
}
