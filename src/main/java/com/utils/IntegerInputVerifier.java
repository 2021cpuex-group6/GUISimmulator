package com.utils;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.UIManager;

class IntegerInputVerifier extends InputVerifier {
    @Override public boolean verify(JComponent c) {
      boolean verified = false;
      JTextField textField = (JTextField) c;
      try {
        Integer.parseInt(textField.getText());
        verified = true;
      } catch (NumberFormatException e) {
        UIManager.getLookAndFeel().provideErrorFeedback(c);
        // Toolkit.getDefaultToolkit().beep();
      }
      return verified;
    }
  }