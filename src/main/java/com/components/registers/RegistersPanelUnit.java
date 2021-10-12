package com.components.registers;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.utils.IntegerInputVerifier;

public class RegistersPanelUnit extends JPanel implements FocusListener{
    final static int FIELD_W = 10;
    private JLabel label;
    private JTextField field;
    private int fieldV;

    public RegistersPanelUnit(String name, String initV){
        super();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        label = new JLabel(name);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        field = new JTextField(initV, FIELD_W);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setHorizontalAlignment(JTextField.RIGHT);
        fieldV = Integer.parseInt(initV);
        field.setInputVerifier(new IntegerInputVerifier());
        field.addFocusListener(this);

        add(label);
        add(field);

    }


    @Override
    public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void focusLost(FocusEvent e) {
        // TODO Auto-generated method stub
        fieldV = fieldParse(field.getText());
        
        
    }
    

    private int fieldParse(String text){
        int ans = 0;
        try{
            ans = Integer.parseInt(text);
            return ans;

        }catch(NumberFormatException e){
        }
        if(text.startsWith("0x")){
            ans = Integer.parseUnsignedInt(text.substring(2), 16);
        }else if(text.startsWith("0b")){
            ans = Integer.parseUnsignedInt(text.substring(2), 2);
        }else if(text.startsWith("0o")){
            ans = Integer.parseUnsignedInt(text.substring(2), 8);
        }else{
            ans = Integer.parseUnsignedInt(text.substring(2));
        }

        return ans;

    }

    public void changeBase(int base){
        // 表示の進法、符号の有無を変える
    }
}
