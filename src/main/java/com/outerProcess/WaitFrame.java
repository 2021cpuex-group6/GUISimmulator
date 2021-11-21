package com.outerProcess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.MainWindow.MainWindow;

public class WaitFrame extends JFrame{

    private final static String TITLE = "実行中…";
    private final static String STOP = "中断 (強制終了)";

    private final static int SIZE = 100;
    
    private MainWindow mainWindow;

    public WaitFrame(MainWindow main){
        super();
        this.mainWindow = main;
        setTitle(TITLE);
        int thisX = main.getX() + main.getWidth() / 2 - SIZE / 2;
        int thisY = main.getY() + main.getHeight() / 2 - SIZE / 2;
        setBounds(thisX, thisY, SIZE, SIZE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        JLabel label = new JLabel(TITLE);
        JButton button = new JButton(STOP);
        button.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                mainWindow.processHandler.forceToShutdown();
                mainWindow.processHandler = null;
                mainWindow.dispose();
                dispose();
            }
            
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        setContentPane(panel);



    }
    
}
