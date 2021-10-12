package com.components.registers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class RegistersPanel extends JPanel{

    private final static int REGISTERS_COL_N = 8;
    private final static int REGISTERS_ROW_N = 4;

    private JPanel iPanel;
    private JPanel fPanel;

    public RegistersPanel(){
        super();
        iPanel = getIPanel();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(iPanel);

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
