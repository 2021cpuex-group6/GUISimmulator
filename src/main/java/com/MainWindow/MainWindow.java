package com.MainWindow;

import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.components.registers.RegistersPanel;

public class MainWindow extends JFrame implements WindowListener{
    private final static String TITLE = "CPUSimmulator";
    private final String INIT_X = "initX";
    private final String INIT_Y = "initY";
    private final String INIT_W = "initW";
    private final String INIT_H = "initH";

    private Properties properties;
    private JPanel registersPanel;

    public MainWindow(Properties properties){
        super();
        setTitle(TITLE);
        this.properties = properties;
        setBounds(Integer.parseInt(properties.getProperty(INIT_X)), Integer.parseInt(properties.getProperty(INIT_Y)),
        Integer.parseInt(properties.getProperty(INIT_W)), Integer.parseInt(properties.getProperty(INIT_H)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);

        JPanel panel = new JPanel(new BorderLayout());
        registersPanel = new RegistersPanel();
        panel.add(registersPanel);

        setContentPane(panel);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Rectangle rec = this.getBounds();
        properties.setProperty(INIT_X, "" + rec.x);
        properties.setProperty(INIT_Y, "" + rec.y);
        properties.setProperty(INIT_W, "" + rec.width);
        properties.setProperty(INIT_H, "" + rec.height);
        PropertiesClass.setProperties(properties);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }
}
