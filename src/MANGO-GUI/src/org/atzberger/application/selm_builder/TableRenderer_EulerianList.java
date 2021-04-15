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
public class TableRenderer_EulerianList extends JLabel
  implements TableCellRenderer {

  JLabel jLabel = null;

  public TableRenderer_EulerianList() {
    jLabel = new JLabel();
    jLabel.setOpaque(true); //MUST do this for background to show up.
    jLabel.setBackground(Color.white);
  }

  public Component getTableCellRendererComponent(JTable table, Object dataValue,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {


    Component returnComponent = null;

    int index;

    TableData_EulerianList data = (TableData_EulerianList)dataValue;

    //data.flagDisplayMode = TableData_EulerianList.DISPLAY_NAME_LIST;

    switch (data.flagDisplayMode) {

      case TableData_EulerianList.DISPLAY_NAME_LIST:

        jLabel.setToolTipText("Array Field");
        String outputStr = printArray(data.eulerianList, "[", "]", ", ");
        jLabel.setText(outputStr);
        jLabel.setHorizontalAlignment(JLabel.LEFT);
        returnComponent = jLabel;

      break;

      case TableData_EulerianList.DISPLAY_MULTIPLE_CHOICE:
        
        jLabel.setToolTipText("Select from options by clicking");
        if (data.itemSelectedIndex <= 0) {
          index = 0;
        } else {
          index = data.itemSelectedIndex;
        }
        jLabel.setText(data.eulerianList[index].EulerianName);
        jLabel.setHorizontalAlignment(jLabel.LEFT); 
        returnComponent = jLabel;

      break;

      case TableData_EulerianList.DISPLAY_DEFAULT:
      case TableData_EulerianList.DISPLAY_BRACKET_NAME:
      default:
        jLabel.setToolTipText("Click to edit list.");
        jLabel.setText("[Eulerian List]");
        jLabel.setHorizontalAlignment(JLabel.LEFT);
        returnComponent = jLabel;
      break;
    }

    return returnComponent;

  }

  protected String printArray(SELM_Eulerian[] array, String openStr, String closeStr, String separator) {

    String outputStr = "";

    outputStr += openStr;
    int N = array.length;
    for (int k = 0; k < N; k++) {
      if (array[k] != null) {
        outputStr += (array[k]).EulerianName;
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
