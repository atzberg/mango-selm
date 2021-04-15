package org.atzberger.application.selm_builder;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * Customised header control for tables.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTableHeaderRender_Default1  extends JLabel implements TableCellRenderer {

  //implements TableCellRenderer
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                 boolean hasFocus, int rowIndex, int vColIndex) {
    
    // 'value' is column header value of column 'vColIndex'
    // rowIndex is always -1
    // isSelected is always false
    // hasFocus is always false
    // Configure the component with the specified value

    setText(value.toString());
    // Set tool tip if desired
    setToolTipText((String)value);

    // Since the renderer is a component, return itself

    return this;
  }


}
