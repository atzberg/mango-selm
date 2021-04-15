package org.atzberger.application.selm_builder;

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
 * Handles display in the table of this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableRenderer_LAMMPS_pair_coeff_tableFilename extends JLabel
  implements TableCellRenderer {

  public TableRenderer_LAMMPS_pair_coeff_tableFilename() {
    setOpaque(true); //MUST do this for background to show up.
    setBackground(Color.white);
  }

  public Component getTableCellRendererComponent(JTable table, Object dataValue,
    boolean isSelected, boolean hasFocus,
    int row, int column) {

    TableData_LAMMPS_pair_coeff_tableFilename tableData_Filename = (TableData_LAMMPS_pair_coeff_tableFilename) dataValue;

    //setToolTipText("Filename Field");
    setToolTipText(tableData_Filename.getFilename());
    
    setText(tableData_Filename.getFilename());

    return this;
  }
  
}
