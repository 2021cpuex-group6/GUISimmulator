package com.outerProcess;

public enum Command {
    DoAll(OuterProcessHandler.COMMAND_DO_ALL), 
    DoNextBreak(OuterProcessHandler.COMMAND_NEXT_BLOCK),
    DoNext(OuterProcessHandler.COMMAND_NEXT),
    Back(OuterProcessHandler.COMMAND_BACK), 
    BreakDelete(OuterProcessHandler.COMMAND_BREAK_DELETE), 
    BreakSet(OuterProcessHandler.COMMAND_BREAK_SET), 
    RegRead(OuterProcessHandler.COMMAND_REG_READ), 
    RegWrite(OuterProcessHandler.COMMAND_REG_WRITE), 
    Reset(OuterProcessHandler.COMMAND_RESET), 
    Info(OuterProcessHandler.COMMAND_INFO), 
    Quit(OuterProcessHandler.COMMAND_QUIT);

    private String command;
    private Command(String command){
        this.command = command;
    }

    public String getCommand(){
        return this.command;
    }
    
}
