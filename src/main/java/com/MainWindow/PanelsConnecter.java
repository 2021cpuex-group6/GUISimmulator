package com.MainWindow;

public class PanelsConnecter {
    //パネル間でデータの受け渡しなどがあるときに仲介する
    private MainWindow mainWindow;
    
    public PanelsConnecter(MainWindow main){
        this.mainWindow = main;
    }

    public void instructionFileSet(String path){
        // 命令コードの書かれたファイルをセット
        mainWindow.instructionPanel.setTable(path);

    }
}
