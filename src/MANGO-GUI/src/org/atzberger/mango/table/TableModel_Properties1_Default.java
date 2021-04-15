/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.atzberger.mango.table;

import org.atzberger.mango.table.TableModel_Properties1_General;
import javax.swing.event.TableModelEvent;

/**
 *
 * Handles table behaviors when user attempts editor, setting values, or data display is requested.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a> 
 */
public class TableModel_Properties1_Default extends TableModel_Properties1_General {

  private   final static boolean DEBUG        = false;
    
  public TableModel_Properties1_Default() {
    int i, j;

    maxColumnUsed  = 2;
    maxRowUsed     = 0;
    
    columnNames    = new String[maxColumnUsed + 1];
    columnNames[0] = "Parameter";
    columnNames[1] = "Value";
    columnNames[2] = "Editor";

    data           = new Object[maxRowUsed + 1][maxColumnUsed + 1];
    dataEditable   = new boolean[maxRowUsed + 1][maxColumnUsed + 1];
        
    /*
    maxRowUsed     = 4;
    maxColumnUsed  = 2;
       
    data           = new Object[maxRowUsed + 1][maxColumnUsed + 1];
    
    for (i = 0; i <= maxRowUsed; i++) {
      for (j = 0; j <= maxColumnUsed; j++) {
        data[i][j] = null;
      }
    }
     */

  }
 

  private void printDebugData() {
    int numRows = getRowCount();
    int numCols = getColumnCount();

    for (int i = 0; i < numRows; i++) {
      System.out.print("    row " + i + ":");
      for (int j = 0; j < numCols; j++) {
        System.out.print("  " + data[i][j]);
      }
      System.out.println();
    }
    System.out.println("--------------------------");
  }

  public int getEntryIndexByName(String entryName) {

    int    entryI         = -1;
    int    k;
    String paramString;

    /* loop over all of the parameter entries */
    for (k = 0; k <= maxRowUsed; k++) {
      paramString = (String) data[k][0];
      if (paramString.equals(entryName)) {
        entryI    = k;
      }
    }

    return entryI;

  }

  public Object getEntryByName(String entryName) {

    int entryI = getEntryIndexByName(entryName);
    Object entryDataValue = null;

    /* get data for the found entry */
    if (entryI >= 0) {
      entryDataValue = data[entryI][1];
    }

    return entryDataValue;

  } /* end getEntryByName */

  public void setEntryByName(String entryName, Object dataValue) {

    int entryI = getEntryIndexByName(entryName);
    Object entryDataValue = null;

    /* get data for the found entry */
    if (entryI >= 0) {
      setValueAt(dataValue,entryI,1);
      //data[entryI][1] = dataValue;
    }

  } /* end getEntryByName */


}







