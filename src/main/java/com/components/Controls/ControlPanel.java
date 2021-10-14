package com.components.Controls;

import java.awt.BorderLayout;
import java.awt.Component;
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

import com.MainWindow.MainWindow;
import com.MainWindow.PropertiesClass;
import com.outerProcess.Command;
import com.outerProcess.OuterProcessHandler;

public class ControlPanel extends JPanel {
    final static int BUTTON_N = 4;
    final static int PANEL_W = 400;
    final static int PANEL_H = 60;

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
    private JLabel fileLabel;

    private File opendFile;
    private Properties property;
    private MainWindow mainWindow;

    public ControlPanel(Properties property, MainWindow mainWindow){
        super();
        this.property = property;
        this.mainWindow = mainWindow;
        controlPanel = this;
        setLayout(new BorderLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
        nextButton = new JButton(NEXT_B_TEXT);
        nextBreakButton = new JButton(NEXT_BB_TEXT);
        nextAllButton = new JButton(NEXT_BA_TEXT);
        backButton = new JButton(BACK_B_TEXT);
        nextButton.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        nextBreakButton.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        nextAllButton.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));
        backButton.setPreferredSize(new Dimension(BUTTON_W, BUTTON_H));

        buttonSetup();
        innerPanel.add(Box.createRigidArea(new Dimension(BUTTON_W, BUTTON_H)));
        innerPanel.add(backButton);
        innerPanel.add(Box.createHorizontalGlue());
        innerPanel.add(nextButton);
        innerPanel.add(Box.createHorizontalGlue());
        innerPanel.add(nextBreakButton);
        innerPanel.add(Box.createHorizontalGlue());
        innerPanel.add(nextAllButton);
        innerPanel.add(Box.createRigidArea(new Dimension(BUTTON_W, BUTTON_H)));

        JPanel outerPanel = new JPanel(new BorderLayout());

        outerPanel.add(innerPanel, BorderLayout.NORTH);
        outerPanel.add(Box.createVerticalStrut(BUTTON_H/2), BorderLayout.CENTER);
        outerPanel.add(getFilePanel(), BorderLayout.SOUTH);

        add(Box.createHorizontalStrut(BUTTON_H/4), BorderLayout.NORTH);
        add(outerPanel);
        add(Box.createHorizontalStrut(BUTTON_H/4), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        setMaximumSize(new Dimension(600, 70));

    }

    private JPanel getFilePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        fileLabel = new JLabel("");

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
                    String filePath = opendFile.getPath();
                    fileLabel.setText(filePath);
                    mainWindow.connecter.instructionFileSet(filePath);
                    mainWindow.processHandler = new OuterProcessHandler(mainWindow, filePath);

                }
                
            }

        });

        panel.add(Box.createHorizontalStrut(BUTTON_W));
        panel.add(button);
        panel.add(fileLabel);

        return panel;
    }

    private void buttonSetup() {
        // ボタンにイベントを追加
        nextButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(mainWindow.processHandler != null){
                    mainWindow.processHandler.doSingleCommand(Command.DoNext);
                }
            }

        });

        nextBreakButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(mainWindow.processHandler != null){
                    mainWindow.processHandler.doSingleCommand(Command.DoNextBreak);
                }
            }

        });

        nextAllButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(mainWindow.processHandler != null){
                    mainWindow.processHandler.doSingleCommand(Command.DoAll);     
                }
                
            }

        });

        backButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(mainWindow.processHandler != null){
                    mainWindow.processHandler.doSingleCommand(Command.Back);
                }
                
            }

        });
    }
}
