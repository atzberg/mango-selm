package org.atzberger.mango.table;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * Handles displaying the data type within the table fields.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableRenderer_Integer extends JComboBox
                           implements TableCellRenderer {

  int dataValue;

  public TableRenderer_Integer() {
    setOpaque(true);
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {
    
    dataValue = (Integer) value;
            
    JLabel jLabel = new JLabel();
    jLabel.setText(Integer.toString(dataValue));
    jLabel.setHorizontalAlignment(jLabel.LEFT);

    return jLabel;
    
  }

}
