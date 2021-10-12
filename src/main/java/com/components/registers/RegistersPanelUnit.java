package com.components.registers;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.utils.IntegerInputVerifier;

public class RegistersPanelUnit extends JPanel{
    final static int FIELD_W = 10;
    private JLabel label;
    private JTextField field;
    private int fieldV;

    public RegistersPanelUnit(String name, String initV){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        label = new JLabel(name);
        field = new JTextField(initV, FIELD_W);
        fieldV = Integer.parseInt(initV);
        field.setInputVerifier(new IntegerInputVerifier());

        add(label);
        add(field);

    }
    
}
