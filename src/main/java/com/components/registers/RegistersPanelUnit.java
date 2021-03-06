package com.components.registers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.nio.ByteBuffer;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.MainWindow.MainWindow;
import com.utils.BaseNumber;
import com.utils.ErrorChecker;
import com.utils.IntegerInputVerifier;

public class RegistersPanelUnit extends JPanel implements FocusListener{
    private final static String INPUT_VALID_VALUE = "適切な値を入力してください";
    private final static int FIELD_W = 12;
    private final static int FIELD_W_BIN = 33;
    private final static int HEX_LEN = 8;
    private final static int BIN_LEN = 32;
    private final static int OCT_LEN = 11;
    private final static int FONT_SIZE = 12;

    private JLabel label;
    private JTextField field;
    private int fieldV;
    private BaseNumber base;
    private boolean signed;
    private int index; // 何番目のレジスタに対応するか
    private boolean forInteger; // 整数レジスタか、浮動小数点レジスタか
    private MainWindow mainWindow;

    public RegistersPanelUnit(String name, String initV, int index, boolean forInteger, MainWindow mainWindow){
        super();
        this.mainWindow = mainWindow;
        base = BaseNumber.DEC;
        signed = true;
        this.index = index;
        this.forInteger = forInteger;
        this.mainWindow = mainWindow;
        

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
        field.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        if(index == 0 ){
            field.setEditable(false);
        }

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
        int[] ansPair = fieldParse(field.getText());
        if(ansPair[1] >= 0){
            fieldV = ansPair[0];
            if(mainWindow.processHandler != null){
                mainWindow.processHandler.writeRegister(index, forInteger, fieldV);
            }
        }
        
        
    }

    // fieldの値をパースし，適切な値なら第一要素にその値，第二要素に0以上の値を入れる
    private int[] fieldParse(String text){
        int ans = 0;
        int[] ansPair = new int[2];
        ansPair[1] = 0;
        try{
            ans = Integer.parseInt(text);
            ansPair[0] = ans;
            return ansPair;

        }catch(NumberFormatException e){
        }

        try{
            if(text.startsWith("0x")){
                ans = Integer.parseUnsignedInt(text.substring(2), 16);
            }else if(text.startsWith("0b")){
                ans = Integer.parseUnsignedInt(text.substring(2), 2);
            }else if(text.startsWith("0o")){
                ans = Integer.parseUnsignedInt(text.substring(2), 8);
            }else{
                if(!forInteger){
                    float ansF = Float.parseFloat(text);
                    ByteBuffer buffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE + 4);
                    buffer.putFloat(ansF);
                    buffer.rewind();
                    ans = buffer.getInt();
                }else{
                    ans = Integer.parseUnsignedInt(text.substring(2));
                }
            }

        }catch(NumberFormatException e){
            ansPair[1] = -1;
            mainWindow.setMessage(INPUT_VALID_VALUE);
        }
        ansPair[0] = ans;

        return ansPair;

    }

    private void refreshFieldString(){
        // 表示を設定の進法、符号有無で更新する
        String setValue = "";
        String value;
        String zeros;
        switch(base){
            case BIN:
                value = Integer.toUnsignedString(fieldV, 2);
                zeros = String.join("", Collections.nCopies(BIN_LEN - value.length(), "0"));
                setValue = "0b" + zeros + value;
                field.setColumns(FIELD_W_BIN);
                break;
            case OCT:
                value = Integer.toUnsignedString(fieldV, 8);
                zeros = String.join("", Collections.nCopies(OCT_LEN - value.length(), "0"));
                setValue = "0o" + zeros + value;
                field.setColumns(FIELD_W);
                break;
            case DEC:
                if(signed){
                    if(!forInteger){
                        ByteBuffer buffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE + 4);
                        buffer.putInt(fieldV);
                        buffer.rewind();
                        setValue = Float.toString(buffer.getFloat());
                    }else{
                        setValue = Integer.toString(fieldV);
                    }
                }else{
                    setValue = Integer.toUnsignedString(fieldV);
                }
                field.setColumns(FIELD_W);
                break;
            case HEX:
                value = Integer.toUnsignedString(fieldV, 16);
                zeros = String.join("", Collections.nCopies(HEX_LEN - value.length(), "0"));
                setValue = "0x" + zeros + value;
                field.setColumns(FIELD_W);
                break;
            }
            revalidate();
            repaint();
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
        label.setBackground(Color.ORANGE);
        repaint();
    }

    public int getFieldV(){
        return fieldV;
    }
}
