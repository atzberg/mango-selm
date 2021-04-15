package org.atzberger.mango.table;

import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.units.Atz_Unit;
import javax.swing.JLabel;
import javax.swing.JTable;
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
public class TableRenderer_Units_Ref extends JLabel
  implements TableCellRenderer {

  public TableRenderer_Units_Ref() {
    setOpaque(true); //MUST do this for background to show up.
    setBackground(Color.white);
  }

  public Component getTableCellRendererComponent(JTable table, Object dataValue,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    
    boolean flagSet = false;

    TableData_Units_Ref tableData_UnitsRef = (TableData_Units_Ref)dataValue;

    Atz_UnitsRef atz_UnitsRef = tableData_UnitsRef.atz_UnitsRef;
    Atz_Unit atz_Unit;

    String outputString = "[";

    atz_Unit = (Atz_Unit) atz_UnitsRef.unitsSelected.get(Atz_Unit.MASS_STR);
    outputString += atz_Unit.unitShortName + ", ";

    atz_Unit = (Atz_Unit) atz_UnitsRef.unitsSelected.get(Atz_Unit.LENGTH_STR);
    outputString += atz_Unit.unitShortName + ", ";

    atz_Unit = (Atz_Unit) atz_UnitsRef.unitsSelected.get(Atz_Unit.TIME_STR);
    outputString += atz_Unit.unitShortName + "]";
   
    setToolTipText("Click to edit.");
    setText(outputString);
    setHorizontalAlignment(LEFT);

    return this;
  }
  
}
