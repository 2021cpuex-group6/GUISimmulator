package com.components.InstructionTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

import com.utils.ConstantsClass;
import com.utils.ErrorChecker;

public class InstructionTableModel extends DefaultTableModel{
    private final static int BREAK_C_NUM = 0;
    private final static String INSTRE = "^\\s+[a-z]+.*";
    private final static String NOT_INST_ROW = "------";
    private final static String JUMP_TO_ENTRYPOINT = "JUMP TO ENTRYPOPINT";
    final static String BREAK_COLUMN = "Break";
    final static String ADDRESS_COLUMN = "Address";
    final static String INSTRUCTION_COLUMN = "Instruction";
    final static String ADDRESS_FORMAT = "%06x";


    private Vector<String> instructionList;
    private Vector<String> addressList;
    private Vector<Boolean> breakList;
    protected ArrayList<Integer> lineList; // 命令アドレス/4からファイルの行数へのマップ
    
    public InstructionTableModel(){
        super();
        addColumn(BREAK_COLUMN);
        addColumn(ADDRESS_COLUMN);
        addColumn(INSTRUCTION_COLUMN);

    }

    public InstructionTableModel(String filepath){
        super();

        int lineN=0;
        try {
            // 行数を数える
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String now = br.readLine();
            while(now != null){
                lineN++;
                now = br.readLine();
            }
            br.close();

            instructionList = new Vector<String>(lineN+1);
            addressList = new Vector<String>(lineN+1);
            breakList = new Vector<Boolean> (lineN+1);
            lineList =new ArrayList<Integer>(0);
            lineList.ensureCapacity(lineN+1);

            Pattern p = Pattern.compile(INSTRE);

            br = new BufferedReader(new FileReader(filepath));
            now = br.readLine();
            
            //最初に追加されるエントリポイントへのジャンプ分
            instructionList.add(JUMP_TO_ENTRYPOINT);
            addressList.add(String.format(ADDRESS_FORMAT, 0));
            lineList.add(0);
            breakList.add(false);

            int i = 1;
            int instN = 1; // 命令の行（コメントなどはのぞく
            while(now != null){
                instructionList.add(now);
                if(p.matcher(now).find()){
                    // 命令の行
                    addressList.add(String.format(ADDRESS_FORMAT, instN*ConstantsClass.INSTRUCTION_BYTE_N));
                    lineList.add(i);
                    instN++;
                }else{
                    addressList.add(NOT_INST_ROW);
                }
                breakList.add(false);
                now = br.readLine();
                i++;
            }
            lineList.trimToSize();
            br.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            ErrorChecker.errorCheck(e);
        } catch (IOException e) {
            ErrorChecker.errorCheck(e);
        }
        addColumn(BREAK_COLUMN, breakList);
        addColumn(ADDRESS_COLUMN, addressList);
        addColumn(INSTRUCTION_COLUMN, instructionList);

    }

    public Class<?> getColumnClass(int column){
        switch(column){
            case 0:
                return Boolean.class;
            case 1:
                return String.class;
            default:
                return String.class;
        }
    }

    // その行が命令行ならtrue, コメント，ラベルなどならfalse
    protected boolean isInstRow(int row){
        if(row < 0 || row >= addressList.size()) return false;
        return addressList.get(row) != NOT_INST_ROW;
    }

    @Override public boolean isCellEditable(int row, int column) {
        // breakPoints以外は編集不可
        if(column == BREAK_C_NUM){
            return isInstRow(row);
        }
        return false;
      }

    
}
