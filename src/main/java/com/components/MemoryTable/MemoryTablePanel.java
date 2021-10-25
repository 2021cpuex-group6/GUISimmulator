package com.components.MemoryTable;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.utils.ConstantsClass;

import java.awt.BorderLayout;

public class MemoryTablePanel extends JPanel{
    private final static int ADDRESS_C_WIDTH = 50;
    private final static int MEMORY_C_WIDTH = 40;


    private MemoryTableModel model;
    private JTable table;


    public MemoryTablePanel(){
        super();
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(ConstantsClass.SCROLL_INCREMENT);
        
        model = new MemoryTableModel();
        table = new JTable(model);
        tableSetting();
        scrollPane.setViewportView(table);

        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.SOUTH);

        setMaximumSize(new Dimension(400, 300));

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
        for (int i = 0; i < MemoryTableModel.MEMORY_COLUMN_N; i++) {
            columnModel.getColumn(2).setMaxWidth(MEMORY_C_WIDTH);            
        }
        

    }

    public void showNowInstruction(int address){
        // 現在の命令にスクロール、強調表示
        // int row = (PC - ConstantsClass.INSTRUCTION_START_ADDRESS) / ConstantsClass.INSTRUCTION_BYTE_N;
        // table.changeSelection(row, 1, false, false);
        // table.changeSelection(row, 1, false, true);
    }
    
}
