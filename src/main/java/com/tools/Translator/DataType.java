package com.tools.Translator;

public enum DataType {
    Float("float"),
    Int32D("符号付き10進"),
    UInt32D("符号なし10進"),
    Int32H("16進"), 
    Int32O("8進"), 
    Int32B("2進");

    public String label;

    private DataType(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return this.label;
    }


    
}
