package com.MainWindow;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import com.components.InstructionTable.InstructionTablePanel;
import com.components.MemoryTable.MemoryTablePanel;
import com.components.registers.RegistersPane;
import com.outerProcess.OuterProcessHandler;
import com.utils.ErrorChecker;

public class MainWindow extends JFrame implements WindowListener, MouseListener{
    private final static int GRID_W = 5;
    private final static int GRID_H = 5;
    private final static String TITLE = "CPUSimmulator";
    private final String INIT_X = "initX";
    private final String INIT_Y = "initY";
    private final String INIT_W = "initW";
    private final String INIT_H = "initH";


    private JLabel messageLabel;
    private JPanel mainPanel;
    protected Properties properties;
    protected RegistersPane registersPanel;
    protected InstructionTablePanel instructionPanel;
    protected ControlPanel controlPanel;
    protected MemoryTablePanel memoryTablePanel;
    private GridBagLayout layout;

    public OuterProcessHandler processHandler;
    public PanelsConnecter connecter;

    public MainWindow(Properties properties){
        super();
        connecter = new PanelsConnecter(this);
        setTitle(TITLE);
        this.properties = properties;
        this.processHandler = null;
        setBounds(Integer.parseInt(properties.getProperty(INIT_X)), Integer.parseInt(properties.getProperty(INIT_Y)),
        Integer.parseInt(properties.getProperty(INIT_W)), Integer.parseInt(properties.getProperty(INIT_H)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);
        addMouseListener(this);

        
        JPanel outerPanel = new JPanel(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        registersPanel = new RegistersPane(this);
        memoryTablePanel = new MemoryTablePanel(this);
        JPanel centerPanel = getCenterPanel();
        mainPanel.add(registersPanel);
        mainPanel.add(centerPanel);
        mainPanel.add(memoryTablePanel);

        JPanel messagePanel = getMessagePanel();
        
        outerPanel.add(mainPanel, BorderLayout.CENTER);
        outerPanel.add(messagePanel, BorderLayout.SOUTH);

        setContentPane(outerPanel);

        // ????????????main.exe????????????????????????
        Runtime.getRuntime().addShutdownHook(
            new Thread(){
                public void run(){
                    if(processHandler != null){
                        processHandler.shutdown();
                        processHandler = null;
                    }
                }
            }
        );

        MenuBar menuBar = new MenuBar(this);
        setJMenuBar(menuBar);
        

    }

    private JPanel getCenterPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        controlPanel = new ControlPanel(properties, this);
        instructionPanel = new InstructionTablePanel(this);

        panel.add(instructionPanel);
        panel.add(controlPanel);

        return panel;
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

        messageLabel = new JLabel("test");
        messageLabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
        panel.add(messageLabel);

        return panel;

    }
    
    public void setMessage(String text){
        messageLabel.setText(text);
        messageLabel.repaint();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            ErrorChecker.errorCheck(e);
        }
        Toolkit.getDefaultToolkit().beep();

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

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
