package com.MainWindow;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
    private final static String TITLE = "CPUSimmulator";
    private final int INIT_XY = 100;
    private final int INIT_W = 600;
    private final int INIT_H = 400;


    public MainWindow(){
        super();
        setTitle(TITLE);
        setBounds(INIT_XY, INIT_XY, INIT_W, INIT_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());

    }
}
