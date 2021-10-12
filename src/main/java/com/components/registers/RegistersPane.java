package com.components.registers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RegistersPane extends JScrollPane{

    private final static int REGISTERS_COL_N = 1;
    private final static int REGISTERS_ROW_N = 32;
    private final static int SCROLL_INCREMENT = 30;


    private JPanel iPanel;
    private JPanel fPanel;

    public RegistersPane(){
        super();
        iPanel = getIPanel();
        getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);

        setViewportView(iPanel);
    }

    private JPanel getIPanel(){
        JPanel panel = new JPanel();
        
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(REGISTERS_ROW_N, REGISTERS_COL_N));

        for (int i = 0; i < REGISTERS_COL_N*REGISTERS_ROW_N; i++) {
            String name = String.format("x%02d", i);
            innerPanel.add(new RegistersPanelUnit(name, "0"));
        }
        panel.add(innerPanel);


        return panel;
    }
    
}
