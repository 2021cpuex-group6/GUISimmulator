package com.components.InstructionTable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellRenderer;

class InstructionTableCellRenderer extends JCheckBox implements TableCellRenderer, UIResource {
    private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    public InstructionTableCellRenderer() {
      super();
      setHorizontalAlignment(SwingConstants.CENTER);
      setBorderPainted(true);
      setRolloverEnabled(true);
      setOpaque(true);
    }
  
    @Override public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected, boolean hasFocus, int row, int column) {
        InstructionTableModel model = (InstructionTableModel) table.getModel();
        boolean isInstRow = model.isInstRow(row);
        setEnabled(isInstRow);
        
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground( UIManager.getColor( "Panel.background" ));
        }
        setSelected((value != null && ((Boolean) value).booleanValue()));
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(noFocusBorder);
        }


        return this;
    }
  }
