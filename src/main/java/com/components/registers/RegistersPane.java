package com.components.registers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.utils.ConstantsClass;

public class RegistersPane extends JPanel{

    private final static int REGISTERS_COL_N = 1;
    private final static int REGISTERS_ROW_N = 32;
    private final static int NON_REG_IND = -1; // 該当するレジスタが存在しないときのインデックス



    private ArrayList<RegistersPanelUnit> iRegisters;
    private ArrayList<RegistersPanelUnit> fRegisters;
    private JPanel iPanel;
    private JPanel fPanel;
    private int highlightedReg;

    public RegistersPane(){
        super();
        highlightedReg = NON_REG_IND;
        iRegisters = new ArrayList<>(ConstantsClass.REGISTER_N+1);
        fRegisters = new ArrayList<>(ConstantsClass.REGISTER_N+1);
        iPanel = getIPanel();
        fPanel = getFPanel();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(ConstantsClass.SCROLL_INCREMENT);
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
        RegistersPanelUnit pc = new RegistersPanelUnit("  pc", "0");
        innerPanel.add(pc);
        innerPanel.add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL));
        innerPanel.add(new JSeparator());
        innerPanel.add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL));
        
        // 32個のレジスタを配置
        JPanel registersPanel = new JPanel();
        registersPanel.setLayout(new GridLayout(ConstantsClass.REGISTER_N+2, 1));
        
        for (int i = 0; i < ConstantsClass.REGISTER_N; i++) {
            String name = String.format("x%02d ", i);
            RegistersPanelUnit register = new RegistersPanelUnit(name, "0");
            iRegisters.add(register);
            registersPanel.add(register);
        }
        iRegisters.add(pc); // 32番目のレジスタとしてもpcをセット

        innerPanel.add(registersPanel);
        panel.add(innerPanel);

        return panel;
    }
    
    private JPanel getFPanel(){
        JPanel panel = new JPanel();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        RegistersPanelUnit fcsr = new RegistersPanelUnit("fcsr", "0");
        innerPanel.add(fcsr);
        innerPanel.add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL));
        innerPanel.add(new JSeparator());
        innerPanel.add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL));
        
        // 32個のレジスタを配置
        JPanel registersPanel = new JPanel();
        registersPanel.setLayout(new GridLayout(ConstantsClass.REGISTER_N+2, 1));
        
        for (int i = 0; i < ConstantsClass.REGISTER_N; i++) {
            String name = String.format("f%02d ", i);
            RegistersPanelUnit register = new RegistersPanelUnit(name, "0");
            fRegisters.add(register);
            registersPanel.add(register);
        }
        fRegisters.add(fcsr); // fcsrも同様

        innerPanel.add(registersPanel);
        panel.add(innerPanel);

        return panel;
    }

    public void setRegister(boolean forInteger, int index, int value, boolean highlight){
        // レジスタに値をセット
        // forInteger ... 整数レジスタへセットするならtrue, 浮動小数点レジスタならfalse
        if(highlightedReg != NON_REG_IND){
            iRegisters.get(highlightedReg).setHighlighted(false);
            highlightedReg = NON_REG_IND;
        }
        if(highlight){
            iRegisters.get(index).setHighlighted(true);
            highlightedReg = index;
        }

        if(forInteger){
            iRegisters.get(index).setFieldV(value);
        }
    }

    public int pcIncrement(){
        // pcの値を増やし、画面に適用させ、増やした後のpcを返す
        int now = iRegisters.get(ConstantsClass.REGISTER_N).getFieldV();
        now += ConstantsClass.INSTRUCTION_BYTE_N;
        iRegisters.get(ConstantsClass.REGISTER_N).setFieldV(now);
        return now;

    }

    public int getPC(){
        return iRegisters.get(ConstantsClass.REGISTER_N).getFieldV();
    }

    public void clearHighlight(){
        // highlight表示を消す（nopなどの実行の時）
        if(highlightedReg != NON_REG_IND){
            iRegisters.get(highlightedReg).setHighlighted(false);
            highlightedReg = NON_REG_IND;
        }
    }
}
