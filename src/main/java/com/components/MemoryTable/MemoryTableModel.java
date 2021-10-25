package com.components.MemoryTable;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.utils.ConstantsClass;


public class MemoryTableModel extends DefaultTableModel{

    final static String ADDRESS_COLUMN = "Address";
    
    final static String MEMORY_FORMAT = "%02x";

    final static int MEMORY_COLUMN_SPLIT_N = 4;

    public int startAddress = 0; //先頭アドレス
    private Vector<Vector<Byte>> memory;


    
    public MemoryTableModel(){
        super();
        addColumn(ADDRESS_COLUMN);
        for (int i = 0; i < ConstantsClass.MEMORY_COLUMN_N; i++) {
            addColumn(String.format(MEMORY_FORMAT, i));
        }
    }

    public MemoryTableModel(Vector<Vector<Byte>> vector){
        super();
        memory = vector;

        addColumn(ADDRESS_COLUMN);
        for (int i = 0; i < ConstantsClass.MEMORY_COLUMN_N; i++) {
            addColumn(String.format(MEMORY_FORMAT, i), vector.get(i));
        }


    }

    public Class<?> getColumnClass(int column){
        switch(column){
            case 0:
                return Byte.class;
            default:
                return Byte.class;
        }
    }

    @Override public boolean isCellEditable(int row, int column) {
        // breakPoints以外は編集不可
        return false;
      }
}
