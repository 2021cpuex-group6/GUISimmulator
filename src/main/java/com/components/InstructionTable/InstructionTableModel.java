package com.components.InstructionTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class InstructionTableModel extends DefaultTableModel{
    final static int INSTRUCTION_BYTE_N = 4;
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
                instructionList.set(i, now);
                addressList.set(i, String.format(ADDRESS_FORMAT, i*INSTRUCTION_BYTE_N));
                now = br.readLine();
            }
            br.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
                return Integer.class;
            default:
                return String.class;
        }
    }
}
