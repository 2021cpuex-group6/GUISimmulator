package com.MainWindow;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import com.components.Controls.ControlPanel;
import com.components.registers.RegistersPane;

public class MainWindow extends JFrame implements WindowListener{
    private final static int GRID_W = 5;
    private final static int GRID_H = 5;
    private final static String TITLE = "CPUSimmulator";
    private final String INIT_X = "initX";
    private final String INIT_Y = "initY";
    private final String INIT_W = "initW";
    private final String INIT_H = "initH";


    private JLabel messageLabel;
    private JPanel mainPanel;
    private Properties properties;
    private RegistersPane registersPanel;
    private ControlPanel controlPanel;
    private GridBagLayout layout;

    public MainWindow(Properties properties){
        super();
        setTitle(TITLE);
        this.properties = properties;
        setBounds(Integer.parseInt(properties.getProperty(INIT_X)), Integer.parseInt(properties.getProperty(INIT_Y)),
        Integer.parseInt(properties.getProperty(INIT_W)), Integer.parseInt(properties.getProperty(INIT_H)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);

        
        JPanel outerPanel = new JPanel(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        registersPanel = new RegistersPane();
        controlPanel = new ControlPanel();
        mainPanel.add(registersPanel);
        mainPanel.add(controlPanel);

        JPanel messagePanel = getMessagePanel();
        
        outerPanel.add(mainPanel, BorderLayout.CENTER);
        outerPanel.add(messagePanel, BorderLayout.SOUTH);

        setContentPane(outerPanel);
        

    }

    private  void addComponent(JComponent comp, int x, int y, int w, int h){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        layout.setConstraints(comp, gbc);
        mainPanel.add(comp);
    }

    private JPanel getMessagePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JLabel message = new JLabel("test");
        message.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
        panel.add(message);

        return panel;

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
