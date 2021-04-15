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
public class TableRenderer_Units_Double extends JComboBox
                           implements TableCellRenderer {

    TableData_Units_Double dataValue;

  public TableRenderer_Units_Double() {
    setOpaque(true);
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {
    
    dataValue = (TableData_Units_Double) value;
            
    JLabel jLabel = new JLabel();
    jLabel.setText(dataValue.getDisplayStr());
    jLabel.setHorizontalAlignment(jLabel.LEFT);

    return jLabel;
    
  }




}
