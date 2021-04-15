package org.atzberger.mango.table;

import org.atzberger.mango.table.TableData_Pathname;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * Handles displaying the data type within the table fields.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableRenderer_Pathname extends JLabel
  implements TableCellRenderer {

  public TableRenderer_Pathname() {
    setOpaque(true); //MUST do this for background to show up.
    setBackground(Color.white);
  }

  public Component getTableCellRendererComponent(JTable table, Object dataValue,
    boolean isSelected, boolean hasFocus,
    int row, int column) {

    TableData_Pathname tableData_Pathname = (TableData_Pathname) dataValue;

    //setToolTipText("Pathname Field");
    setToolTipText(tableData_Pathname.pathname);
    
    setText(tableData_Pathname.pathname);

    return this;
  }
  
}
