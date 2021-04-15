package org.atzberger.mango.table;

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
public class TableRenderer_Array_Simple extends JLabel
  implements TableCellRenderer {

  public final static int MAX_N = 20; /* maximum number of extries displayed */

  public TableRenderer_Array_Simple() {
    setOpaque(true); //MUST do this for background to show up.
    setBackground(Color.white);
  }

  public Component getTableCellRendererComponent(JTable table, Object dataValue,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {
    
    boolean flagSet = false;
    
    setToolTipText("Array Field");

    String dataType = dataValue.getClass().getName();
    
    String DoubleArrayStr = double[].class.getName();
    if (DoubleArrayStr.equals(dataType)) {
      double[] doubleArray = (double[]) dataValue;
      String   outputStr   = printArray(doubleArray, "[","]",", ");
      setText(outputStr);
      flagSet = true;
    }

    String IntArrayStr = int[].class.getName();
    if (IntArrayStr.equals(dataType)) {
      int[]  intArray  = (int[]) dataValue;
      String outputStr = printArray(intArray, "[","]",", ");
      setText(outputStr); /* show the output */
      flagSet = true;
    }
    
    String StringArrayStr = String[].class.getName();
    if (StringArrayStr.equals(dataType)) {
      String[] StringArray = (String[]) dataValue;
      String   outputStr   = printArray(StringArray, "[","]",", ");
      setText(outputStr);
      flagSet = true;
    }

    setHorizontalAlignment(LEFT);

    if (flagSet == false) {
      setText("(Array Type Unknown)");
    }

    return this;
  }

  protected String printArray(Object[] array, String openStr, String closeStr, String separator) {

    String outputStr = "";

    outputStr += openStr;
    int N = array.length;
    if (N > MAX_N) {
      N = MAX_N;
    }
    for (int k = 0; k < N; k++) {
      outputStr += array[k].toString();
      if (k != N - 1) {
        outputStr += separator;
      }
    }

    if (array.length > MAX_N) {
      outputStr += separator;
      outputStr += "...";
    }

    outputStr += closeStr;

    return outputStr;
  }

  protected String printArray(int[] array, String openStr, String closeStr, String separator) {

    String outputStr = "";

    outputStr += openStr;
    int N = array.length;
    if (N > MAX_N) {
      N = MAX_N;
    }
    for (int k = 0; k < N; k++) {
      outputStr += array[k];
      if (k != N - 1) {
        outputStr += separator;
      }
    }

    if (array.length > MAX_N) {
      outputStr += separator;
      outputStr += "...";
    }

    outputStr += closeStr;

    return outputStr;
  }

  protected String printArray(double[] array, String openStr, String closeStr, String separator) {

    String outputStr = "";

    outputStr += openStr;
    int N = array.length;
    if (N > MAX_N) {
      N = MAX_N;
    }
    for (int k = 0; k < N; k++) {
      outputStr += array[k];
      if (k != N - 1) {
        outputStr += separator;
      }
    }

    if (array.length > MAX_N) {
      outputStr += separator;
      outputStr += "...";
    }

    outputStr += closeStr;

    return outputStr;
  }

  
}
