package com.MainWindow;

import com.outerProcess.Command;
import com.outerProcess.OuterProcessHandler;
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

    public void memSetByte(long address, byte value){
        mainWindow.memoryTablePanel.setByte(address, value);
    }

    public void setHighlightWord(long address){
        mainWindow.memoryTablePanel.setHighlightWord(address);
    }

    public void clearMemHighlight(){
        mainWindow.memoryTablePanel.clearHighlight();
    }

    public long getMemStartAddress(){
        return mainWindow.memoryTablePanel.getNowStartAddress();
    }

    public void resetAll(){
        mainWindow.processHandler.shutdown();
        resetState();
        mainWindow.instructionPanel.reset();
        
    }

    public void changeBase(BaseNumber base, boolean signed){
        mainWindow.registersPanel.changeBase(base, signed);
    }

    // 開いているファイルはリセットせず，命令を実行前の状態に戻す
    public void resetState(){
        if(mainWindow.processHandler != null){
            mainWindow.processHandler.doSingleCommand(Command.Reset);
            mainWindow.instructionPanel.showNowInstruction(0);
        }
        mainWindow.registersPanel.reset();
        mainWindow.memoryTablePanel.reset();

    }


}
