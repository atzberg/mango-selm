package org.atzberger.mango.table;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * Handles displaying the data type within the table fields.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableRenderer_String extends JComboBox
                           implements TableCellRenderer {

  String dataValue;

  Border unselectedBorder = null;
  Border selectedBorder   = null;
  boolean isBordered      = false;

  public TableRenderer_String() {
    setOpaque(true);
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    dataValue = (String) value;
            
    JLabel jLabel = new JLabel();
    jLabel.setText(dataValue);
    jLabel.setHorizontalAlignment(jLabel.LEFT);
    jLabel.setOpaque(true);
    jLabel.setBorder(null);
    
    if (isBordered) {
      if (isSelected) {
        if (selectedBorder == null) {
          selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                           table.getSelectionBackground());
        }
        jLabel.setBorder(selectedBorder);
        jLabel.setBackground(table.getSelectionBackground());
      } else {
        if (unselectedBorder == null) {
          unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                             table.getBackground());
        }
        jLabel.setBorder(unselectedBorder);
        jLabel.setBackground(table.getBackground());
      }
    } else {
      if (isSelected) {       
        jLabel.setBackground(table.getSelectionBackground());
      } else {
        jLabel.setBackground(table.getBackground());
      }
    }

//    if (isSelected) {
//      jLabel.setForeground(table.getSelectionBackground());
//      jLabel.setBackground(table.getSelectionBackground());
//    } else {
//      jLabel.setBackground(table.getBackground());
//    }
    
//    Color testColor = jLabel.getBackground();

    return jLabel;
    
  }

}
