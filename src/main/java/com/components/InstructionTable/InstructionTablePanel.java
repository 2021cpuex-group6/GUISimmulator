package com.components.InstructionTable;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.utils.ConstantsClass;

import java.awt.BorderLayout;

public class InstructionTablePanel extends JPanel{

    private InstructionTableModel model;
    private JTable table;


    public InstructionTablePanel(){
        super();
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(ConstantsClass.SCROLL_INCREMENT);
        
        model = new InstructionTableModel();
        table = new JTable(model);
        scrollPane.setViewportView(table);

        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.SOUTH);

    }

    public void setTable(String filePath){
        // ファイルを読み込んでそれを表に表示する
        model = new InstructionTableModel(filePath);
        table.setModel(model);

    }
    
}
