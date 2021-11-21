package com.tools;


import java.util.Properties;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.crypto.Data;

import com.MainWindow.MainWindow;

public class MMIOChecker extends JFrame{
    private final static int MARGIN_W = 10;

    private final static String TITLE = "MMIOChecker";
    private final static String INIT_X = "initX";
    private final static String INIT_Y = "initY";
    private final static String INIT_W = "initW";
    private final static String INIT_H = "initH";

    private final static String NOT_STARTED = "実行前です．ファイルを実行してから開いてください．";

    protected Properties properties;
    protected JTextArea resultField;
    protected JTextField field;
    private MainWindow mainWindow;
    
    public MMIOChecker(Properties properties, MainWindow mainWindow){
        super();
        this.mainWindow = mainWindow;
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

        
        
        JTextArea textArea = new JTextArea("", 40, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        outerPanel.add(scrollPane, BorderLayout.CENTER);
        if(mainWindow.processHandler != null){
            mainWindow.processHandler.getMMIOInfo(textArea);
        }else{
            textArea.setText(NOT_STARTED);
        }

        
        
        return outerPanel;
    }

}
