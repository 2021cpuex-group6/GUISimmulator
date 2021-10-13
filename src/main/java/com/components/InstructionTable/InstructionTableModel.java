package com.components.InstructionTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.utils.ConstantsClass;
import com.utils.ErrorChecker;

public class InstructionTableModel extends DefaultTableModel{
    private final static int BREAK_C_NUM = 0;

    final static String BREAK_COLUMN = "Break";
    final static String ADDRESS_COLUMN = "Address";
    final static String INSTRUCTION_COLUMN = "Instruction";
    final static String ADDRESS_FORMAT = "%06x";

    private Vector<String> instructionList;
    private Vector<String> addressList;
    private Vector<Boolean> breakList;
    
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

            instructionList = new Vector<String>(lineN);
            addressList = new Vector<String>(lineN);
            breakList = new Vector<Boolean> (lineN);

            br = new BufferedReader(new FileReader(filepath));
            now = br.readLine();
            int i = 0;
            while(now != null){
                instructionList.add(now);
                addressList.add(String.format(ADDRESS_FORMAT, i*ConstantsClass.INSTRUCTION_BYTE_N));
                breakList.add(false);
                now = br.readLine();
                i++;
            }
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

    @Override public boolean isCellEditable(int row, int column) {
        // breakPoints以外は編集不可
        if(column == BREAK_C_NUM){
            return true;
        }
        return false;
      }
}
