package com.components.MemoryTable;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.Renderer;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import com.MainWindow.MainWindow;
import com.utils.ConstantsClass;

import java.awt.BorderLayout;

public class MemoryTablePanel extends JPanel{
    private final static int ADDRESS_C_WIDTH = 60;
    private final static int MEMORY_C_WIDTH = 25;
    private final static int CONTROL_PANEL_INTERVAL = 50;
    private final static String CONTROL_LABEL_1 = "開始アドレス: 0x";
    private final static String CONTROL_LABEL_2 = "000";
    private final static String CONTROL_BUTTON_TEXT = "変更";
    private final static String START_ADDRESS_OUT_OF_RANGE = "適切な範囲内のアドレスを16進数で入力してください（ 0x0 ~ 0x%x）．";
    private final static int START_ADDRESS_SWITCH_UNIT = 0x1000;


    private MemoryTableModel model;
    private JTable table;
    private int pageN;
    private MainWindow main;
    private JSpinner spinner;




    public MemoryTablePanel(MainWindow main){
        super();
        this.main = main;
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(ConstantsClass.SCROLL_INCREMENT);

        
        table = new JTable();
        reset();
        scrollPane.setViewportView(table);

        add(getControlPanel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(200, 500));

    }

    public void reset(){
        model = new MemoryTableModel(getInitTableData());
        table.setModel(model);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setIntercellSpacing(new Dimension());
        MemoryTableCellRenderer rendrer = new MemoryTableCellRenderer();
        table.setDefaultRenderer(Byte.class, rendrer);
        tableSetting();
    }

    // 特に初期データがないとき
    private Vector<Vector<Byte>> getInitTableData(){
        Vector<Vector<Byte>> data = new Vector<>();
        for (int i = 0; i < ConstantsClass.MEMORY_COLUMN_N; i++) {
            Vector<Byte> innerData = new Vector<Byte>(ConstantsClass.MEMORY_SHOW_LINE_N);
            for (int j = 0; j < ConstantsClass.MEMORY_SHOW_LINE_N; j++) {
                innerData.add((byte) 0);
            }
            data.add(innerData);
        }
        return data;
    }

    // メモリの初期データを受け取って表示
    public void setTable(Vector<Vector<Byte>> data){
        model = new MemoryTableModel(data);
        table.setModel(model);
        tableSetting();
    }

    // 表の設定
    private void tableSetting(){
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(ADDRESS_C_WIDTH);
        for (int i = 0; i < ConstantsClass.MEMORY_COLUMN_N; i++) {
            columnModel.getColumn(i+1).setMaxWidth(MEMORY_C_WIDTH);            
        }
        

    }

    // メモリの表示範囲を指定できるGUI部分
    private JPanel getControlPanel(){
        JPanel outerPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel label1 = new JLabel(CONTROL_LABEL_1);
        JLabel label2 = new JLabel(CONTROL_LABEL_2);
        
        pageN = ConstantsClass.MEMORY_BYTE_N / (ConstantsClass.MEMORY_SHOW_LINE_N * ConstantsClass.MEMORY_COLUMN_N);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, pageN, 1);
        spinner = new JSpinner(spinnerModel);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        JFormattedTextField ftf = editor.getTextField();
        ftf.setFormatterFactory(makeFFactory());

        JButton button = new JButton(CONTROL_BUTTON_TEXT);
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // メモリの表示開始アドレスを変え，読み込みしなおす
                model.startAddress = ((int)spinner.getValue()) * START_ADDRESS_SWITCH_UNIT;
                main.processHandler.memSynchronize();
                repaint();
            }
        });

        panel.add(Box.createHorizontalStrut(CONTROL_PANEL_INTERVAL));
        panel.add(label1);
        panel.add(spinner);
        panel.add(label2);
        panel.add(Box.createHorizontalStrut(ConstantsClass.SEPARATE_INTERVAL));
        panel.add(button);
        panel.add(Box.createHorizontalStrut(CONTROL_PANEL_INTERVAL));

        outerPanel.add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.NORTH);
        outerPanel.add(panel);
        outerPanel.add(Box.createVerticalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.SOUTH);
        return outerPanel;
    }

    // 16進で表示，パースするformat (spinner用)
    private DefaultFormatterFactory makeFFactory() {
        DefaultFormatter formatter = new DefaultFormatter() {
          @Override public Object stringToValue(String text) throws ParseException {
            Pattern pattern = Pattern.compile("^\\s*(\\p{XDigit}{1,6})\\s*$");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
              Integer iv = Integer.valueOf(text, 16);
              if (iv <= pageN) {
                return iv;
              }
            }
            Toolkit.getDefaultToolkit().beep();
            main.setMessage(String.format(START_ADDRESS_OUT_OF_RANGE, pageN));
            throw new ParseException(text, 0);

          }

          @Override public String valueToString(Object value) {
            Integer iv = (Integer) value;
            return String.format("%x", iv);
          }
        };
        formatter.setValueClass(Integer.class);
        formatter.setOverwriteMode(true);
        return new DefaultFormatterFactory(formatter);
      }

    // 現在表示している部分で一番若いアドレスを返す
    public long getNowStartAddress(){
        return model.startAddress;
    }

    public void setNowStartAddress(long address){
        model.startAddress = address;
    }

    // 現在表示している領域にアドレスが含まれるか
    public boolean inPrintedRange(long address){
        return getNowStartAddress() <= address && address < getNowStartAddress() + ConstantsClass.MEMORY_SHOW_LINE_N * ConstantsClass.MEMORY_COLUMN_N;
    }

    // そのワード全体を強調表示
    public void setHighlightWord(long address){
        model.hasHighlighted = true;
        model.highlightedWord = address;
        repaint();
    }

    public void clearHighlight(){
        model.hasHighlighted = false;
        repaint();
    }

    public void setByte(long address, byte value){
        if(!inPrintedRange(address)){
            return;
        }
        int rowInd = (int)((address - getNowStartAddress()) / ConstantsClass.MEMORY_COLUMN_N);
        int columnInd = (int)((address - getNowStartAddress()) % ConstantsClass.MEMORY_COLUMN_N);
        table.setValueAt(value, rowInd, columnInd + 1);

    }



    public void showNowInstruction(int address){
        // 現在のメモリにスクロール
        // int row = (PC - ConstantsClass.INSTRUCTION_START_ADDRESS) / ConstantsClass.INSTRUCTION_BYTE_N;
        // table.changeSelection(row, 1, false, false);
        // table.changeSelection(row, 1, false, true);
    }
    
}
