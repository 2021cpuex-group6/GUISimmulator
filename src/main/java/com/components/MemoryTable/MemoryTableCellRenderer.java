package com.components.MemoryTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

class MemoryTableCellRenderer extends DefaultTableCellRenderer {

    private final static int BORDERWIDTH1 = 1;
    private final static int BORDERWIDTH2 = 2;
    private final static int BORDERWIDTH3 = 3;
    final static String ADDRESS_FORMAT = "%06x";
    final static String MEMORY_FORMAT = "%02x";

    private final Border b1 = BorderFactory.createMatteBorder(
        0, 0, BORDERWIDTH1, BORDERWIDTH1, Color.GRAY);

    private final Border b2 = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, BORDERWIDTH2, 0, Color.BLACK),
        BorderFactory.createMatteBorder(0, 0, 0, BORDERWIDTH1, Color.GRAY));
    private final Border b3 = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, BORDERWIDTH3, 0, Color.BLACK),
        BorderFactory.createMatteBorder(0, 0, 0, BORDERWIDTH1, Color.GRAY));

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
        if (column == 0) {
            //アドレス表示
            this.setText(String.format(ADDRESS_FORMAT, model.startAddress + row * MemoryTableModel.MEMORY_COLUMN_N));
        }else{
            this.setText(String.format(MEMORY_FORMAT, table.getValueAt(row, column)));
        }
        if (column % MemoryTableModel.MEMORY_COLUMN_SPLIT_N == 0) {
            if(column == 0){
                setBorder(b3);
            }else{
                    setBorder(b2);
            }
        } else {
            setBorder(b1);
        }
        return this;
        }
  }