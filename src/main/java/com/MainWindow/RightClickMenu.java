package com.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class RightClickMenu extends JPopupMenu{

    private final static String RESET_MENU = "リセット";


    private  MainWindow mainWindow;
    public RightClickMenu(MainWindow main){
        this.mainWindow = main;
        JMenuItem reset = new JMenuItem(RESET_MENU);
        add(reset);
        reset.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                mainWindow.connecter.resetState();
            }

        });

    }
    
}
