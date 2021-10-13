package com.components.InstructionTable;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.utils.ConstantsClass;

import java.awt.BorderLayout;

public class InstructionTablePanel extends JPanel{
    private final static int BREAK_C_WIDTH = 30;
    private final static int ADDRESS_C_WIDTH = 50;
    private final static int INSTRUCTION_C_WIDTH = 250;


    private InstructionTableModel model;
    private JTable table;


    public InstructionTablePanel(){
        super();
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(ConstantsClass.SCROLL_INCREMENT);
        
        model = new InstructionTableModel();
        table = new JTable(model);
        tableSetting();
        scrollPane.setViewportView(table);

        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.SOUTH);

        setMaximumSize(new Dimension(300, 200));

    }

    public void setTable(String filePath){
        // ファイルを読み込んでそれを表に表示する
        model = new InstructionTableModel(filePath);
        table.setModel(model);
        tableSetting();

    }

    private void tableSetting(){
        // 表の設定
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(BREAK_C_WIDTH);
        columnModel.getColumn(1).setMaxWidth(ADDRESS_C_WIDTH);
        columnModel.getColumn(2).setMaxWidth(INSTRUCTION_C_WIDTH);
        

    }
    
}
