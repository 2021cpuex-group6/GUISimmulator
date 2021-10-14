package com.MainWindow;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class RightClickMenu extends JPopupMenu{

    private final static String RESET_MENU = "リセット";


    private  MainWindow mainWindow;
    public RightClickMenu(MainWindow main){
        this.mainWindow = main;
        JMenuItem reset = new JMenuItem(RESET_MENU);
        reset.setAction(new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                mainWindow.connecter.reset();
            }
            
        });
        add(reset);

    }
    
}
