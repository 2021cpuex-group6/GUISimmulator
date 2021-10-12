package com.utils;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class IntegerInputVerifier extends InputVerifier {

    public IntegerInputVerifier(){
        super();
    }

    @Override public boolean verify(JComponent c) {
      boolean verified = false;
      JTextField textField = (JTextField) c;
      String text = textField.getText();
      int ans = 0;
      try{
          ans = Integer.parseInt(text);
          return true;
      }catch(NumberFormatException e){
      }
      try{
          if(text.startsWith("0x")){
              ans = Integer.parseUnsignedInt(text.substring(2), 16);
          }else if(text.startsWith("0b")){
              ans = Integer.parseUnsignedInt(text.substring(2), 2);
          }else if(text.startsWith("0o")){
              ans = Integer.parseUnsignedInt(text.substring(2), 8);
          }else{
              ans = Integer.parseUnsignedInt(text.substring(2));
          }
          verified = true;
      } catch (NumberFormatException e) {
        UIManager.getLookAndFeel().provideErrorFeedback(c);
        // Toolkit.getDefaultToolkit().beep();
      }
      return verified;
    }
  }