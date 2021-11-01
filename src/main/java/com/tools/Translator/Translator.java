package com.tools.Translator;

import java.util.Properties;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.nio.ByteBuffer;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.crypto.Data;

public class Translator extends JFrame{
    private final static int MARGIN_W = 10;
    private final static int PANEL_H = 90;
    private final static int FONT_SIZE = 12;
    private final static String TITLE = "CPUSimmulator";
    private final static String INIT_X = "initX";
    private final static String INIT_Y = "initY";
    private final static String INIT_W = "initW";
    private final static String INIT_H = "initH";

    private final static String INPUT_LABEL = "入力";
    private final static String BEFORE_LABEL = "変換前";
    private final static String AFTER_LABEL = "変換後";
    private final static String ARROW_LABEL = "→";
    private final static String INVALID_INPUT = "入力が不正です";

    private final static String BIN_TOP = "BIN__: ";
    private final static String HEX_TOP = "HEX__: ";
    private final static String SDEC_TOP = "SDEC_: ";
    private final static String UDEC_TOP = "UDEC_: ";
    private final static String FLOAT_TOP = "FLOAT: ";
    private final static String WORD_TOP = "WORD_: ";


    protected Properties properties;
    protected JTextArea resultField;
    protected JTextField field;
    JComboBox<DataType> beforeList;
    
    public Translator(Properties properties){
        super();
        setTitle(TITLE);
        this.properties = properties;
        setBounds(Integer.parseInt(properties.getProperty(INIT_X)), Integer.parseInt(properties.getProperty(INIT_Y)),
        Integer.parseInt(properties.getProperty(INIT_W)) /3, Integer.parseInt(properties.getProperty(INIT_H))/2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(getMainPanel());

        setVisible(true);


    }

    private JPanel getMainPanel(){
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(Box.createVerticalStrut(MARGIN_W), BorderLayout.SOUTH);
        outerPanel.add(Box.createVerticalStrut(MARGIN_W), BorderLayout.NORTH);
        outerPanel.add(Box.createHorizontalStrut(MARGIN_W), BorderLayout.EAST);
        outerPanel.add(Box.createHorizontalStrut(MARGIN_W), BorderLayout.WEST);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(getListPanel(), BorderLayout.NORTH);
        panel.add(getResultPanel(), BorderLayout.CENTER);
        panel.add(getInputPanel(), BorderLayout.SOUTH);

        outerPanel.add(panel, BorderLayout.CENTER);
        return outerPanel;
        
    }

    private JPanel getListPanel(){
        JPanel panel = new JPanel(new GridLayout(2, 3));
        

        JLabel beforeLabel = new JLabel(BEFORE_LABEL);
        beforeList = new JComboBox<DataType>(DataType.values());
        beforeList.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showResult();
            }

        });

        panel.add(beforeLabel);
        panel.add(beforeList);

        return panel;
    }

    private JPanel getResultPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(Box.createHorizontalStrut(MARGIN_W), BorderLayout.EAST);
        panel.add(Box.createHorizontalStrut(MARGIN_W), BorderLayout.WEST);
        panel.add(Box.createVerticalStrut(MARGIN_W), BorderLayout.SOUTH);
        panel.add(Box.createVerticalStrut(MARGIN_W), BorderLayout.NORTH);

        resultField = new JTextArea("Result");

        resultField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        resultField.setEditable(false);
        panel.add(resultField);

        return panel;
    }

    private JPanel getInputPanel(){
        JPanel panel = new JPanel(new BorderLayout());

        JLabel inputLabel = new JLabel(INPUT_LABEL);
        field = new JTextField();
        field.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showResult();
            }

        });

        panel.add(inputLabel, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;

    }

    private void showResult(){
        Object[] res = translateToInt(field.getText());
        if((boolean) res[1]){
            resultField.setText(getTranlatedRes((ByteBuffer) res[0]));
        }else{
            resultField.setText(INVALID_INPUT);
        }

    }

    // 第一要素 ByteBufferにラップした値
    // 第二要素 適切に変換できるかのbool
    private Object[] translateToInt(String text){
        Object[] res= new Object[2];
        ByteBuffer buffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE + 4);
        res[0] = buffer;
        res[1] = true;
        try{
            int i = 0;
            switch((DataType) beforeList.getSelectedItem()){
                case Float:
                    float f = Float.parseFloat(text);
                    buffer.putFloat(f);
                    break;
                case Int32D:
                    i = Integer.parseInt(text);
                    buffer.putInt(i);
                    break;
                case UInt32D:
                    i = Integer.parseUnsignedInt(text);
                    buffer.putInt(i);
                    break;
                case Int32H:
                    i = Integer.parseInt(text, 16);
                    buffer.putInt(i);
                    break;
                case Int32B:
                    i = Integer.parseInt(text, 2);
                    buffer.putInt(i);
                    break;
                case Int32O:
                    i = Integer.parseInt(text, 8);
                    buffer.putInt(i);
                    break;
                default:
                    res[0] = -1;
                    res[1] = false;
                
            }
            System.out.println(buffer.remaining());
            return res;
        }catch(Exception e){
            res[0] = -1;
            res[1] = false;
            return res;
        }

    }

    private String getTranlatedRes(ByteBuffer buffer){
        buffer.rewind();
        int int32 = buffer.getInt(0);
        buffer.rewind();
        float float32 = buffer.getFloat();
        byte[] byte4 = new byte[Integer.SIZE / Byte.SIZE];
        buffer.rewind();
        buffer.get(byte4, 0, 4);

        StringBuilder sb = new StringBuilder();
        sb.append(BIN_TOP);
        sb.append(String.format("%32s", Integer.toBinaryString(int32)).replace(" ", "0"));
        sb.append(System.lineSeparator());
        sb.append(HEX_TOP);
        sb.append(String.format("%08x", int32));
        sb.append(System.lineSeparator());
        sb.append(SDEC_TOP);
        sb.append(String.valueOf(int32));
        sb.append(System.lineSeparator());
        sb.append(UDEC_TOP);
        sb.append(Integer.toUnsignedString(int32));
        sb.append(System.lineSeparator());
        sb.append(WORD_TOP);
        for(int i = Integer.BYTES-1; i >= 0; i--){
            sb.append(String.format("%02x ", Byte.toUnsignedInt(byte4[i] )));
        }
        sb.append(System.lineSeparator());
        sb.append(FLOAT_TOP);
        sb.append(String.valueOf(float32));
        sb.append(System.lineSeparator());
        return sb.toString();

    }
}
