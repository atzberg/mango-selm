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
public class TableRenderer_LagrangianList implements TableCellRenderer {
  
  JLabel jLabel = null;

  public TableRenderer_LagrangianList() {
    jLabel = new JLabel();
    jLabel.setOpaque(true); //MUST do this for background to show up.
    jLabel.setBackground(Color.white);
  }

  public Component getTableCellRendererComponent(JTable table, Object dataValue,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    Component returnComponent = null;

    TableData_LagrangianList data = (TableData_LagrangianList)dataValue;

    //data.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;

    switch (data.flagDisplayMode) {

      case TableData_LagrangianList.DISPLAY_NAME_LIST:

        jLabel.setToolTipText("Array Field");
        String outputStr = printArray(data.lagrangianList, "[", "]", ", ");
        jLabel.setText(outputStr);
        jLabel.setHorizontalAlignment(JLabel.LEFT);
        returnComponent = jLabel;

      break;
      
      case TableData_LagrangianList.DISPLAY_DEFAULT:
      case TableData_LagrangianList.DISPLAY_BRACKET_NAME:
      default:        
        jLabel.setToolTipText("Click the Lagrangian DOF tab to edit this list.");
        jLabel.setText("[Lagrangian List]");
        jLabel.setHorizontalAlignment(JLabel.LEFT);
        returnComponent = jLabel;
      break;
    }

    return returnComponent;
    
  }

  protected String printArray(SELM_Lagrangian[] array, String openStr, String closeStr, String separator) {

    String outputStr = "";

    outputStr += openStr;
    int N = array.length;
    for (int k = 0; k < N; k++) {
      if (array[k] != null) {
        outputStr += (array[k]).LagrangianName;
      } else {
        outputStr += "(null)";
      }
      if (k != N - 1) {
        outputStr += separator;
      }
    }
    outputStr += closeStr;

    return outputStr;
  }


}
