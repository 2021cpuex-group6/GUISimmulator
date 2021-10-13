package com.components.Controls;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.MainWindow.PropertiesClass;

public class ControlPanel extends JPanel {
    final static int BUTTON_N = 4;
    final static int PANEL_W = 300;
    final static int PANEL_H = 50;

    final static int BUTTON_W = 50;
    final static int BUTTON_H = 30;

    private static final String FILE_B_TEXT = "File";

    private static final String NEXT_B_TEXT = ">";
    private static final String NEXT_BB_TEXT = ">|";
    private static final String NEXT_BA_TEXT = ">>";
    private static final String BACK_B_TEXT = "<";

    private static final String LAST_DIRECTORY = "lastDirectory";


    private JButton nextButton;
    private JButton nextBreakButton;
    private JButton nextAllButton;
    private JButton backButton;
    private JPanel controlPanel;

    private File opendFile;
    private Properties property;

    public ControlPanel(Properties property){
        super();
        this.property = property;
        controlPanel = this;
        setLayout(new GridLayout(2, 1));
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
        nextButton = new JButton(NEXT_B_TEXT);
        nextBreakButton = new JButton(NEXT_BB_TEXT);
        nextAllButton = new JButton(NEXT_BA_TEXT);
        backButton = new JButton(BACK_B_TEXT);
        nextButton.setMaximumSize(new Dimension(BUTTON_W, BUTTON_H));
        nextBreakButton.setMaximumSize(new Dimension(BUTTON_W, BUTTON_H));
        nextAllButton.setMaximumSize(new Dimension(BUTTON_W, BUTTON_H));
        backButton.setMaximumSize(new Dimension(BUTTON_W, BUTTON_H));

        buttonSetup();
        innerPanel.add(Box.createHorizontalGlue());
        innerPanel.add(backButton);
        innerPanel.add(Box.createHorizontalGlue());
        innerPanel.add(nextButton);
        innerPanel.add(Box.createHorizontalGlue());
        innerPanel.add(nextBreakButton);
        innerPanel.add(Box.createHorizontalGlue());
        innerPanel.add(nextAllButton);
        innerPanel.add(Box.createHorizontalGlue());

        add(innerPanel);
        add(getFilePanel());

        setPreferredSize(new Dimension(PANEL_W, PANEL_H));
                

    }

    private JPanel getFilePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JButton button = new JButton(FILE_B_TEXT);
        button.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String lastPath = property.getProperty(LAST_DIRECTORY);
                File lastAccessedFolder = new File(lastPath);
                JFileChooser chooser;
                if(lastPath.equals("")){
                    chooser = new JFileChooser();
                }else{
                    chooser = new JFileChooser(lastAccessedFolder);
                }
                int selected = chooser.showOpenDialog(controlPanel);
                if(selected == JFileChooser.APPROVE_OPTION){
                    // 何かファイルが指定された
                    opendFile = chooser.getSelectedFile();
                    property.setProperty(LAST_DIRECTORY, opendFile.getParent());
                    PropertiesClass.setProperties(property);
                }
                
            }

        });
        JLabel label = new JLabel("");

        panel.add(Box.createHorizontalStrut(BUTTON_W));
        panel.add(button);
        panel.add(label);

        return panel;
    }

    private void buttonSetup() {
        // ボタンにイベントを追加
        nextButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }

        });

        nextBreakButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }

        });

        nextAllButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }

        });

        backButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }

        });
    }
}
