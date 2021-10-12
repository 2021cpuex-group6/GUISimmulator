package com.components.registers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.utils.BaseNumber;
import com.utils.IntegerInputVerifier;

public class RegistersPanelUnit extends JPanel implements FocusListener{
    final static int FIELD_W = 10;
    private JLabel label;
    private JTextField field;
    private int fieldV;
    private BaseNumber base;
    private boolean signed;

    public RegistersPanelUnit(String name, String initV){
        super();
        base = BaseNumber.DEC;
        signed = true;
        

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        label = new JLabel(name);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setOpaque(false);
        label.setBackground(Color.ORANGE);
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

    private void refreshFieldString(){
        // 表示を設定の進法、符号有無で更新する
        String setValue = "";
        switch(base){
            case BIN:
                setValue = "0b" + Integer.toUnsignedString(fieldV, 2);
                break;
            case OCT:
                setValue = "0o" + Integer.toUnsignedString(fieldV, 8);
                break;
            case DEC:
                if(signed){
                    setValue = Integer.toString(fieldV);
                }else{
                    setValue = Integer.toUnsignedString(fieldV);
                }
                break;
            case HEX:
                setValue = "0x" + Integer.toUnsignedString(fieldV, 16);
                break;
        }
        field.setText(setValue);
        return;
    }

    public void changeBase(BaseNumber base, boolean signed){
        // 表示の進法、符号の有無を変える
        this.base = base;
        this.signed = signed;
        refreshFieldString();
        return;
    }

    public void setFieldV(int value){
        fieldV = value;
        refreshFieldString();
    }

    public void setHighlighted(boolean bool){
        // 強調表示をするか
        label.setOpaque(bool);
    }
}
