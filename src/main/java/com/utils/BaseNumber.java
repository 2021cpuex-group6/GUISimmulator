package com.utils;

public enum BaseNumber {
    HEX(16), 
    DEC(10), 
    OCT(8), 
    BIN(2);
    
    private final int value;
    private BaseNumber(final int value){
        this.value = value;
    }

    public int GetValue(){
        return value;
    }
}
