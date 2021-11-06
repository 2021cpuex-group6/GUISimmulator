package com.utils;

public class ConstantsClass {
    public final static int SCROLL_INCREMENT = 30;
    public final static int SEPARATE_INTERVAL = 5;
    public final static int INSTRUCTION_BYTE_N = 4;
    public final static int WORD_BYTE_N = 4;
    public final static int REGISTER_N = 32;
    public final static int INSTRUCTION_START_ADDRESS = 0;

    public final static int REGISTER_KINDS = 4; // レジスタの種別
    public final static String INTEGER_REGISTER_PREFIX = "x";
    public final static String FLOAT_REGISTER_PREFIX = "f";
    public final static int MEMORY_BYTE_N = 0x10000; //2^16バイト
    public final static int MEMORY_SHOW_LINE_N = 0x200; // 2^9 * 8バイト表示 
    public final static int MEMORY_COLUMN_N = 8;

    private final static String INVALID_CONSTANT_ERROR  = "定数が制約を満たしていません";

    public  static void checkConstants(){
        // 定数が制約を満たしているか確認
        boolean res = true;
        res  = res && ((MEMORY_SHOW_LINE_N  * MEMORY_COLUMN_N) == 0x1000);
        res = res && ((MEMORY_BYTE_N % (MEMORY_SHOW_LINE_N * MEMORY_COLUMN_N)) == 0);
        if(!res){
            ErrorChecker.errorCheck(INVALID_CONSTANT_ERROR);
        }
    }
}
