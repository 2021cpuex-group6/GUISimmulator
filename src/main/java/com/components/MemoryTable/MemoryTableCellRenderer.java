package com.components.MemoryTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import com.utils.ConstantsClass;

class MemoryTableCellRenderer extends DefaultTableCellRenderer {

    private final static int BORDERWIDTH1 = 1;
    private final static int BORDERWIDTH2 = 2;
    private final static int BORDERWIDTH3 = 3;
    final static String ADDRESS_FORMAT = "%06x";
    final static String MEMORY_FORMAT = "%02x";
    private final static Color Highlight = Color.getHSBColor(0.1f, 0.75f, 1f);

    private final Border b1 = BorderFactory.createMatteBorder(
        0, 0, BORDERWIDTH1, BORDERWIDTH1, Color.GRAY);

    private final Border b2 = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, BORDERWIDTH1, 0, Color.GRAY),
        BorderFactory.createMatteBorder(0, 0, 0, BORDERWIDTH2, Color.BLACK));
    private final Border b3 = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, BORDERWIDTH1, 0, Color.GRAY),
        BorderFactory.createMatteBorder(0, 0, 0, BORDERWIDTH3, Color.BLACK));

    protected MemoryTableCellRenderer() {
      super();

    }
  
    @Override public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
        boolean isEditable = false;
        super.getTableCellRendererComponent(
            table, value, isEditable && isSelected, hasFocus, row, column);
        MemoryTableModel model = (MemoryTableModel) table.getModel();
        long address = model.startAddress + row * ConstantsClass.MEMORY_COLUMN_N + column -1;
        if (column == 0) {
            //アドレス表示
            setHorizontalAlignment(SwingConstants.RIGHT);
            this.setText(String.format(ADDRESS_FORMAT, address+1));
        }else{
            setHorizontalAlignment(SwingConstants.CENTER);
            this.setText(String.format(MEMORY_FORMAT, table.getValueAt(row, column)));
            // 強調表示
            if(model.hasHighlighted){
                if(model.highlightedWord <= address && 
                    address < model.highlightedWord + ConstantsClass.WORD_BYTE_N){
                    setBackground(Highlight);
                }else{
                    setBackground(Color.white);
                }
            }
        }
        if(column == 0 || column == 4){
            setBorder(b2);

        } else {
            setBorder(b1);
        }
        return this;
        }
  }