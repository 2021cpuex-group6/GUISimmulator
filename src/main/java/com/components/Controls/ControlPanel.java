package com.components.Controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    final static int BUTTON_N = 4;

    private static final String NEXT_B_TEXT = ">";
    private static final String NEXT_BB_TEXT = ">|";
    private static final String NEXT_BA_TEXT = ">>";
    private static final String BACK_B_TEXT = "<";


    private JButton nextButton;
    private JButton nextBreakButton;
    private JButton nextAllButton;
    private JButton backButton;

    public ControlPanel(){
        super();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(1, BUTTON_N));
        nextButton = new JButton(NEXT_B_TEXT);
        nextBreakButton = new JButton(NEXT_BB_TEXT);
        nextAllButton = new JButton(NEXT_BA_TEXT);
        backButton = new JButton(BACK_B_TEXT);

        buttonSetup();
        innerPanel.add(backButton);
        innerPanel.add(nextButton);
        innerPanel.add(nextBreakButton);
        innerPanel.add(nextAllButton);

        add(innerPanel);
        

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
