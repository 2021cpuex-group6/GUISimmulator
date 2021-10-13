package com.outerProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.utils.ErrorChecker;

public class OuterProcessHandler {
    //外部のシミュレータとの通信をするクラス
    private final static String SIMMULATOR_EXE = "main.exe";
    private final static String CHARA_CODE = "Shift-JIS";
    private final static String ERROR_CODE = "Error";
    private final static String BUG_REPORT = "バグと思われるので報告お願いします。";

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
    

    private PrintStream sender; // プログラムに送る
    private BufferedReader receiver; //結果受け取り
    private Process process;
    public OuterProcessHandler(String path){
        // 開くアセンブリファイル (or バイナリコード)を指定してプロセスを起動する
        try {
            process = Runtime.getRuntime().exec(SIMMULATOR_EXE + " "+ path + " -g");
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
    
}
