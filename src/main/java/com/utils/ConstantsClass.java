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
    public final static int MEMORY_BYTE_N = 0x1000; //2^12バイト
    public final static int MEMORY_SHOW_LINE_N = 0x100; // 2^8 * 8バイト表示 
    public final static int MEMORY_COLUMN_N = 8;
}
