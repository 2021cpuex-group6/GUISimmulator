package com.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.tools.Translator.Translator;

public class MenuBar extends JMenuBar{
    private final static String MENU_TOOL = "ツール";

    private final static String ITEM_TRANSLATOR = "変換ツール";
    private final static String ITEM_RESET = "リセット";

    private MainWindow main;
    public MenuBar(MainWindow mainWindow){
        this.main= mainWindow;

        JMenu toolMenu = new JMenu(MENU_TOOL);

        JMenuItem translatorItem = new JMenuItem(ITEM_TRANSLATOR);
        JMenuItem resetItem = new JMenuItem(ITEM_RESET);
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
        toolMenu.add(translatorItem);
        toolMenu.add(resetItem);

        add(toolMenu);


    }
    
}
