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
    private final static int BREAK_C_WIDTH = 40;
    private final static int ADDRESS_C_WIDTH = 50;
    private final static int INSTRUCTION_C_WIDTH = 350;


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

        setMaximumSize(new Dimension(400, 300));

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

    public void showNowInstruction(int PC){
        // 現在の命令にスクロール、強調表示
        int row = (PC - ConstantsClass.INSTRUCTION_START_ADDRESS) / ConstantsClass.INSTRUCTION_BYTE_N;
        row = model.lineList.get(row);
        table.changeSelection(row, 1, false, false);
        table.changeSelection(row, 1, false, true);
    }
    
}
