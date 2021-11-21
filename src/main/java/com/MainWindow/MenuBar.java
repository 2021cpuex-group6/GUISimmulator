package com.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.outerProcess.Command;
import com.tools.MMIOChecker;
import com.tools.Translator.Translator;

public class MenuBar extends JMenuBar{
    private final static String MENU_TOOL = "ツール";

    private final static String ITEM_TRANSLATOR = "変換ツール";
    private final static String ITEM_OUTPUT = "output.ppmに出力";
    private final static String ITEM_MMIO = "MMIOチェッカー";
    private final static String ITEM_RESET = "リセット";

    private MainWindow main;
    public MenuBar( MainWindow mainWindow){
        this.main= mainWindow;

        JMenu toolMenu = new JMenu(MENU_TOOL);

        JMenuItem translatorItem = new JMenuItem(ITEM_TRANSLATOR);
        JMenuItem resetItem = new JMenuItem(ITEM_RESET);
        JMenuItem mmioItem = new JMenuItem(ITEM_MMIO);
        JMenuItem outputItem = new JMenuItem(ITEM_OUTPUT);
        translatorItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Translator translator = new Translator(main.properties);
                
            }
        });
        resetItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                main.connecter.resetState();                
            }
        });
        mmioItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                MMIOChecker mmio = new MMIOChecker(main.properties, main);
            }
        });
        outputItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(main.processHandler!= null){
                    main.processHandler.doSingleCommand(Command.OUTPUT);
                }
            }
        });
        toolMenu.add(outputItem);
        toolMenu.add(mmioItem);
        toolMenu.add(translatorItem);
        toolMenu.add(resetItem);


        add(toolMenu);


    }
    
}
