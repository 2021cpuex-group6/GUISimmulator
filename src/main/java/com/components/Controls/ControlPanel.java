package com.components.Controls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.MainWindow.MainWindow;
import com.MainWindow.PropertiesClass;
import com.outerProcess.Command;
import com.outerProcess.OuterProcessHandler;
import com.utils.BaseNumber;
import com.utils.ConstantsClass;

public class ControlPanel extends JPanel {
    final static int BUTTON_N = 4;
    final static int PANEL_W = 400;
    final static int PANEL_H = 90;

    final static int BUTTON_W = 50;
    final static int BUTTON_H = 30;

    private static final String FILE_B_TEXT = "File";

    private static final String NEXT_B_TEXT = ">";
    private static final String NEXT_BB_TEXT = ">|";
    private static final String NEXT_BA_TEXT = ">>";
    private static final String BACK_B_TEXT = "<";

    private final static String HEX_RADIO = "HEX";
    private final static String DEC_RADIO = "DEC";
    private final static String OCT_RADIO = "OCT";
    private final static String BIN_RADIO = "BIN";
    private final static String UNSIGNED_RADIO = "UNSIGNED";

    private static final String NEXT_B_DESCRIPTION = "次の命令を実行";
    private static final String NEXT_BB_DESCRIPTION = "次のブレークポイントまでを実行";
    private static final String NEXT_AB_DESCRIPTION = "全命令実行";
    private static final String BACK_B_DESCRIPTION = "命令を一つ戻る";

    private static final String LAST_DIRECTORY = "lastDirectory";
    private static final String USE_BINARY_CHECK = "バイナリファイルを使用";

    public JCheckBox useBinary;


    private JButton nextButton;
    private JButton nextBreakButton;
    private JButton nextAllButton;
    private JButton backButton;
    private JPanel controlPanel;
    private JLabel fileLabel;

    private JRadioButton hex = new JRadioButton(HEX_RADIO);
    private JRadioButton dec = new JRadioButton(DEC_RADIO);
    private JRadioButton oct = new JRadioButton(OCT_RADIO);
    private JRadioButton bin = new JRadioButton(BIN_RADIO);
    private JRadioButton unsigned = new JRadioButton(UNSIGNED_RADIO);

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
        outerPanel.add(getRadioPanel(), BorderLayout.CENTER);
        outerPanel.add(getFilePanel(), BorderLayout.SOUTH);

        add(Box.createHorizontalStrut(BUTTON_H/4), BorderLayout.NORTH);
        add(outerPanel);
        add(Box.createHorizontalStrut(BUTTON_H/4), BorderLayout.SOUTH);

        setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        setMaximumSize(new Dimension(600, 70));

    }

    private JPanel getFilePanel(){
        JPanel outerPanel = new JPanel(new BorderLayout());
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
                    if(mainWindow.processHandler != null){
                        mainWindow.connecter.resetAll();
                    }
                    mainWindow.connecter.instructionFileSet(filePath);
                    mainWindow.processHandler = new OuterProcessHandler(mainWindow, filePath);

                }
                
            }

        });

        useBinary = new JCheckBox(USE_BINARY_CHECK, false);
        useBinary.setEnabled(false);
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.X_AXIS));
        checkPanel.add(Box.createHorizontalStrut(BUTTON_W));
        checkPanel.add(useBinary);

        panel.add(Box.createHorizontalStrut(BUTTON_W));
        panel.add(button);
        panel.add(fileLabel);

        outerPanel.add(panel, BorderLayout.NORTH);
        outerPanel.add(Box.createHorizontalStrut(ConstantsClass.SEPARATE_INTERVAL), BorderLayout.CENTER);
        outerPanel.add(checkPanel);

        return outerPanel;
    }

    private JPanel getRadioPanel(){
        // ラジオボタンを集めたパネルを作る
        JPanel panel = new JPanel(new GridLayout(1, 5));
        ButtonGroup bgroup = new ButtonGroup();

        hex = new JRadioButton(HEX_RADIO);
        dec = new JRadioButton(DEC_RADIO);
        oct = new JRadioButton(OCT_RADIO);
        bin = new JRadioButton(BIN_RADIO);
        unsigned = new JRadioButton(UNSIGNED_RADIO);

        bgroup.add(hex);
        bgroup.add(dec);
        bgroup.add(oct);
        bgroup.add(bin);
        bgroup.add(unsigned);

        panel.add(hex);
        panel.add(dec);
        panel.add(oct);
        panel.add(bin);
        panel.add(unsigned);

        dec.setSelected(true);

        ActionListener radioListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JRadioButton button = (JRadioButton) e.getSource();
                if(button == hex){
                    mainWindow.connecter.changeBase(BaseNumber.HEX, false);
                }else if(button == dec){
                    mainWindow.connecter.changeBase(BaseNumber.DEC, true);
                }else if(button == oct){
                    mainWindow.connecter.changeBase(BaseNumber.OCT, false);
                }else if(button == bin){
                    mainWindow.connecter.changeBase(BaseNumber.BIN, false);
                }else{
                    mainWindow.connecter.changeBase(BaseNumber.DEC, false);
                }

            }

        };

        hex.addActionListener(radioListener);
        bin.addActionListener(radioListener);
        oct.addActionListener(radioListener);
        dec.addActionListener(radioListener);
        unsigned.addActionListener(radioListener);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(Box.createVerticalStrut(BUTTON_H/4), BorderLayout.NORTH);
        outerPanel.add(panel);
        outerPanel.add(Box.createVerticalStrut(BUTTON_H/4), BorderLayout.SOUTH);

        outerPanel.setMinimumSize(new Dimension(PANEL_W/2, BUTTON_H * 2));

        return outerPanel;


    }

    private void buttonSetup() {
        // ボタンにイベントを追加
        nextButton.setToolTipText(NEXT_B_DESCRIPTION);
        nextButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(mainWindow.processHandler != null){
                    mainWindow.processHandler.doSingleCommand(Command.DoNext);
                }
            }

        });

        nextBreakButton.setToolTipText(NEXT_BB_DESCRIPTION);
        nextBreakButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(mainWindow.processHandler != null){
                    mainWindow.processHandler.doSingleCommand(Command.DoNextBreak);
                }
            }

        });

        nextAllButton.setToolTipText(NEXT_AB_DESCRIPTION);
        nextAllButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(mainWindow.processHandler != null){
                    mainWindow.processHandler.doSingleCommand(Command.DoAll);     
                }
                
            }

        });

        backButton.setToolTipText(BACK_B_DESCRIPTION);
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
