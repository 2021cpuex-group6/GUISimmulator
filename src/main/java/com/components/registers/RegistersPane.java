package com.components.registers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class RegistersPane extends JPanel{

    private final static int REGISTER_N = 32;
    private final static int REGISTERS_COL_N = 1;
    private final static int REGISTERS_ROW_N = 32;
    private final static int SCROLL_INCREMENT = 30;
    private final static int SEPARATE_INTERVAL = 5;


    private ArrayList<RegistersPanelUnit> iRegisters;
    private ArrayList<RegistersPanelUnit> fRegisters;
    private RegistersPanelUnit pc;
    private RegistersPanelUnit fcsr;
    private JPanel iPanel;
    private JPanel fPanel;

    public RegistersPane(){
        super();
        iRegisters = new ArrayList<>(REGISTER_N);
        fRegisters = new ArrayList<>(REGISTER_N);
        iPanel = getIPanel();
        fPanel = getFPanel();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(iPanel);
        panel.add(fPanel);

        scrollPane.setViewportView(panel);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(Box.createVerticalStrut(100), BorderLayout.WEST);

    }

    private JPanel getIPanel(){
        JPanel panel = new JPanel();
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        pc = new RegistersPanelUnit("  pc", "0");
        innerPanel.add(pc);
        innerPanel.add(Box.createVerticalStrut(SEPARATE_INTERVAL));
        innerPanel.add(new JSeparator());
        innerPanel.add(Box.createVerticalStrut(SEPARATE_INTERVAL));
        
        // 32個のレジスタを配置
        JPanel registersPanel = new JPanel();
        registersPanel.setLayout(new GridLayout(REGISTER_N+2, 1));
        
        for (int i = 0; i < REGISTER_N; i++) {
            String name = String.format("x%02d ", i);
            RegistersPanelUnit register = new RegistersPanelUnit(name, "0");
            iRegisters.add(register);
            registersPanel.add(register);
        }

        innerPanel.add(registersPanel);
        panel.add(innerPanel);

        return panel;
    }
    
    private JPanel getFPanel(){
        JPanel panel = new JPanel();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        fcsr = new RegistersPanelUnit("fcsr", "0");
        innerPanel.add(fcsr);
        innerPanel.add(Box.createVerticalStrut(SEPARATE_INTERVAL));
        innerPanel.add(new JSeparator());
        innerPanel.add(Box.createVerticalStrut(SEPARATE_INTERVAL));
        
        // 32個のレジスタを配置
        JPanel registersPanel = new JPanel();
        registersPanel.setLayout(new GridLayout(REGISTER_N+2, 1));
        
        for (int i = 0; i < REGISTER_N; i++) {
            String name = String.format("f%02d ", i);
            RegistersPanelUnit register = new RegistersPanelUnit(name, "0");
            fRegisters.add(register);
            registersPanel.add(register);
        }

        innerPanel.add(registersPanel);
        panel.add(innerPanel);

        return panel;
    }
}
