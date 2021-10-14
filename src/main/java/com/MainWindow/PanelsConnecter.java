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

    public void setRegister(boolean forInteger, int index, int value, boolean highlight){
        mainWindow.registersPanel.setRegister(forInteger, index, value, highlight);
    }
}
