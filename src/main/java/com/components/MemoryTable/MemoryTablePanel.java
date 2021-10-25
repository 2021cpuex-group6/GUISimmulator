package com.components.MemoryTable;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Renderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.utils.ConstantsClass;

import java.awt.BorderLayout;

public class MemoryTablePanel extends JPanel{
    private final static int ADDRESS_C_WIDTH = 60;
    private final static int MEMORY_C_WIDTH = 25;


    private MemoryTableModel model;
    private JTable table;

    private int highlightedRow = -1;
    private int highlightedColumn = -1;


    public MemoryTablePanel(){
        super();
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(ConstantsClass.SCROLL_INCREMENT);

        
        model = new MemoryTableModel(getInitTableData());
        table = new JTable(model);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setIntercellSpacing(new Dimension());
        MemoryTableCellRenderer rendrer = new MemoryTableCellRenderer();
        table.setDefaultRenderer(Byte.class, rendrer);
        tableSetting();
        scrollPane.setViewportView(table);

        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(200, 500));

    }

    private Vector<Vector<Byte>> getInitTableData(){
        // 特に初期データがないとき
        Vector<Vector<Byte>> data = new Vector<>();
        for (int i = 0; i < ConstantsClass.MEMORY_COLUMN_N; i++) {
            Vector<Byte> innerData = new Vector<Byte>(ConstantsClass.MEMORY_SHOW_LINE_N);
            for (int j = 0; j < ConstantsClass.MEMORY_SHOW_LINE_N; j++) {
                innerData.add((byte) 0);
            }
            data.add(innerData);
        }
        return data;
    }

    public void setTable(Vector<Vector<Byte>> data){
        // メモリの初期データを受け取って表示
        model = new MemoryTableModel(data);
        table.setModel(model);
        tableSetting();
    }

    private void tableSetting(){
        // 表の設定
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(ADDRESS_C_WIDTH);
        for (int i = 0; i < ConstantsClass.MEMORY_COLUMN_N; i++) {
            columnModel.getColumn(i+1).setMaxWidth(MEMORY_C_WIDTH);            
        }
        

    }

    public long getNowStartAddress(){
        // 現在表示している部分で一番若いアドレスを返す
        return model.startAddress;
    }

    public void setNowStartAddress(long address){
        model.startAddress = address;
    }

    public boolean inPrintedRange(long address){
        // 現在表示している領域にアドレスが含まれるか
        return getNowStartAddress() <= address && address < getNowStartAddress() + ConstantsClass.MEMORY_SHOW_LINE_N * ConstantsClass.MEMORY_COLUMN_N;
    }

    public void setHighlight(long address){
        // 強調表示

    }

    public void setByte(long address, byte value){
        if(!inPrintedRange(address)){
            return;
        }
        int rowInd = (int)((address - getNowStartAddress()) / ConstantsClass.MEMORY_COLUMN_N);
        int columnInd = (int)((address - getNowStartAddress()) % ConstantsClass.MEMORY_COLUMN_N);
        table.setValueAt(value, rowInd, columnInd + 1);

    }



    public void showNowInstruction(int address){
        // 現在のメモリにスクロール
        // int row = (PC - ConstantsClass.INSTRUCTION_START_ADDRESS) / ConstantsClass.INSTRUCTION_BYTE_N;
        // table.changeSelection(row, 1, false, false);
        // table.changeSelection(row, 1, false, true);
    }
    
}
