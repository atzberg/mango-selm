package org.atzberger.application.selm_builder;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * Handles display in the table of this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableRenderer_CouplingOperatorList extends JLabel
  implements TableCellRenderer {

  public TableRenderer_CouplingOperatorList() {
    setOpaque(true); //MUST do this for background to show up.
    setBackground(Color.white);
  }

  public Component getTableCellRendererComponent(JTable table, Object dataValue,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    
    boolean flagSet = false;

    setToolTipText("Click the Coupling Operator tab to edit this list.");
    setText("[Coupling Op List]");
    setHorizontalAlignment(LEFT);

    return this;
  }
  
}
