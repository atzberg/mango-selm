/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.atzberger.mango.table;

import java.awt.Color;
import javax.swing.table.AbstractTableModel;


/**
 *
 * Handles editing of generic arrays. (not fully implimented yet)
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Array_General extends AbstractTableModel {

  private final static boolean DEBUG = true;

  int     maxRowUsed = -1;
  private String[] columnNames = {"Index", "Value"};

  /* @@@ changed first entry to color type */
  private Object[][] data = {
    {1, 0.0},
    {2, 0.0}
  };

  public TableModel_Array_General() {

    /* initialize the data */
    data       = new Object[2][2];
    maxRowUsed = -1;

    /*
    int N    = data.length;
    for (int k = 0; k < N; k++) {
      data[k][0] = k;
      data[k][1] = 0.0;
    }
     */

  }

  //private String[] columnNames = {"Test 1", "Test 2"};
  //private Object[][] data = ...//same as before...
  public int getColumnCount() {
    return columnNames.length;
  }

  public int getRowCount() {
    return (maxRowUsed + 1);
    //return data.length;
  }

  public void setColumnNames(String[] columnNames_in) {
    columnNames = columnNames_in;
  }

  public void setTableData(Object[][] data_in) {
    data       = data_in;
    maxRowUsed = data_in.length - 1;
  }

  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col) {
    return data[row][col];
  }

  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
    //return String.class;
  }

  /*
   * Don't need to implement this method unless your table's
   * editable.
   */
  //@Override
  public boolean isCellEditable(int row, int col) {

    boolean flag = true;

    //Note that the data/cell address is constant,
    //no matter where the cell appears onscreen.
    if (col == 0) {
      flag = false;
    } else {
      flag = true;
    }

    if (row > maxRowUsed) {
     flag = false;
    }

    return flag;

  }

  /*
   * Don't need to implement this method unless your table's
   * data can change.
   */
  public void setValueAt(Object value, int row, int col) {

    boolean flagSet = false;

    /* check to see if request is within range,
     * otherwise resize the data array.
     */
    if (row < data.length) {
      if (col < data[row].length) {
        data[row][col] = value;
        flagSet        = true;
      }
    }

    /* if we could not set it resize the data array
     * and try again.
     */
    if (!flagSet) {
      /* double the size of the data array */
      int N_c             = columnNames.length;
      int N_r             = data.length;
      Object[][] data_new = new Object[2*N_r][N_c];

      copyData(data,data_new);

      data = data_new;

      /* call the set command again */
      setValueAt(value, row, col);

    }

    if (row > maxRowUsed) {
      maxRowUsed = row;
    }
       
    fireTableCellUpdated(row, col);

    //fireTableCellUpdated();
    
  }

  private void copyData(Object[][] data_src, Object[][] data_dest) {

    int N_r = data.length;
    int N_c = columnNames.length;
    
    for (int i = 0; i < N_r; i++) {
      for (int j = 0; j < N_c; j++) {
        data_dest[i][j] = data_src[i][j];
      }
    }

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




}
