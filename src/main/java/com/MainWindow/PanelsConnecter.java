package com.MainWindow;

import com.utils.BaseNumber;

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

    public int pcIncrement(int factor){
        return mainWindow.registersPanel.pcIncrement(factor);
    }

    public int getPC(){
        return mainWindow.registersPanel.getPC();
    }

    public void showNowInstruction(int PC){
        mainWindow.instructionPanel.showNowInstruction(PC);
    }

    public void clearHighlight(){
        mainWindow.registersPanel.clearHighlight();
    }

    public void reset(){

    }

    public void changeBase(BaseNumber base, boolean signed){
        mainWindow.registersPanel.changeBase(base, signed);
    }


}
