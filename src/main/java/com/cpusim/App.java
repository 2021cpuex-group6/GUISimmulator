
package com.cpusim;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.MainWindow.MainWindow;
import com.MainWindow.PropertiesClass;
public class App 
{
    public static void main( String[] args )
    {
        Properties properties = PropertiesClass.getProperties();
        MainWindow main = new MainWindow(properties);
        main.setVisible(true);
    }
}
