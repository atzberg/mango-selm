package org.atzberger.mango.table;

import java.awt.Color;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * Handles table behaviors in response to user attempts to edit and in response to requests to display the data.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Properties1_General extends AbstractTableModel {

  private final static boolean DEBUG = false;

  public final static boolean EDITABLE     = true;
  public final static boolean NOT_EDITABLE = false;

  public String[]    columnNames  = new String[0];
  public Object[][]  data         = new Object[0][0];
  public boolean[][] dataEditable = new boolean[0][0];

  public int                  maxRowUsed    = -1;
  public int                  maxColumnUsed = -1;

  public TableModel_Properties1_General() {

    int i, j;

    /* !!! Warning Number of Columns resize does not
     * appear permitted for Tables!!!  They do not render.
     */

    maxColumnUsed  = 0;
    maxRowUsed     = 0;

    /*
    columnNames    = new String[maxColumnUsed + 1];
    columnNames[0] = "Header";     
     */

    columnNames    = new String[maxColumnUsed + 1];
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

  public TableModel_Properties1_General(String[] columnNames_in) {
    columnNames    = columnNames_in;
    init();
  }

  public  void init() {
    int rowAlloc   = 1;

    maxRowUsed     = -1;
    maxColumnUsed  = columnNames.length - 1;

    data           = new Object[rowAlloc][maxColumnUsed + 1];
    dataEditable   = new boolean[rowAlloc][maxColumnUsed + 1];
  }

  //private String[] columnNames = {"Test 1", "Test 2"};
  //private Object[][] data = ...//same as before...
  @Override
  public int getColumnCount() {
    return (maxColumnUsed + 1);
  }

  @Override
  public int getRowCount() {
    //return data.length;
    return (maxRowUsed + 1);
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  public void setColumnNames(String[] columnNames_in) {
    columnNames   = columnNames_in; /* PJA codes */

    /*
    if (columnNames.length - 1 > maxColumnUsed) {
      maxColumnUsed = columnNames.length - 1;
    }
     */
    
  }

  public void removeAllEntries() {
    removeAllEntries(true);
  }

  public void removeAllEntries(boolean flagFireTableDataChanged) {
    maxColumnUsed  = 0;
    maxRowUsed     = 0;

    columnNames    = new String[maxColumnUsed + 1];
    data           = new Object[maxRowUsed + 1][maxColumnUsed + 1];
    dataEditable   = new boolean[maxRowUsed + 1][maxColumnUsed + 1];

    if (flagFireTableDataChanged) {
      fireTableDataChanged();
    }
  }


  @Override
  public Object getValueAt(int row, int col) {
    if ((row < 0) || (col < 0)) {
      return null;
    } else {
      if ((row <= maxRowUsed) && (col <= maxColumnUsed)) {
        return data[row][col];
      } else {
        return null;
      }
    }
  }


  public boolean getEditableAt(int row, int col) {
    return dataEditable[row][col];
  }

  @Override
  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();   
  }
  
  @Override
  public boolean isCellEditable(int row, int col) {

    boolean flag = true; /* default is true */

    if ((row <= maxRowUsed) && (col <= maxColumnUsed)) {

      flag = dataEditable[row][col];   /* set using data table */

      // do not allow cells outside used area to be edited
      if (row > maxRowUsed) {
        flag = false;
      }

      if (col > maxColumnUsed) {
        flag = false;
      }

    } else {
      flag = false;
    }

    return flag;

  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    boolean flagEditable = true;    
    setValueAt(value, row, col, flagEditable);
  }

  public void setValueAt(Object value, int row, int col, boolean flagEditable) {
    setValueAt(value, row, col, flagEditable, true);
  }

  /* set values in the table and resize data structures if outside current
   * range.
   */
  protected void setValueAt(Object value, int row, int col, boolean flagEditable, boolean flagFireCellUpdate) {
    boolean flagSet = false;

    int N_r = data.length;
    int N_c = data[0].length;

    int N_r_new = N_r;
    int N_c_new = N_c;

    if (DEBUG) {
      System.out.println("Setting value at " + row + "," + col
              + " to " + value
              + " (an instance of "
              + value.getClass() + ")");
    }

    /* Attempt to set the data if within range (otherwise resize) */
    if (row < N_r) {
      if (col < N_c) {

        data[row][col]         = value;
        dataEditable[row][col] = flagEditable;
        flagSet                = true;

      }
    }

    /* If we could not set it, resize the
     * data array and try again.
     */
    if (!flagSet) {

      if ((row >= N_r) && (col >= N_c)) {
        /* double the size of the data array in rows and columns */
        N_r_new = 2*N_r;
        N_c_new = 2*N_c;
      } else if (row >= N_r) {
        /* double the size of the data array in rows */
        N_r_new = 2*N_r;
        N_c_new = N_c;
      } else if (col >= N_c) {
        /* double the size of the data array in columns */
        N_r_new = N_r;
        N_c_new = 2*N_c;
      }

      resizeTable(N_r_new, N_c_new);

      /* call the set command again */
      setValueAt(value, row, col, flagEditable, flagFireCellUpdate);

    } /* end !flagSet */

    /* Adjust the row and column used sizes */
    if (row > maxRowUsed) {
      maxRowUsed = row;
    }

    if (col > maxColumnUsed) {
      maxColumnUsed = col;
    }

    if (DEBUG) {
      System.out.println("New value of data:");
      printDebugData();
    }

    if (flagFireCellUpdate) {
      fireTableCellUpdated(row, col);
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
  
  private void copyData(Object[][] data_src, Object[][] data_dest) {

    int N_r = data.length;
    int N_c = data[0].length;

    for (int i = 0; i < N_r; i++) {
      for (int j = 0; j < N_c; j++) {
        data_dest[i][j] = data_src[i][j];
      }
    }

  }

  private void copyData(boolean[][] data_src, boolean[][] data_dest) {

    int N_r = dataEditable.length;
    int N_c = dataEditable[0].length;

    for (int i = 0; i < N_r; i++) {
      for (int j = 0; j < N_c; j++) {
        data_dest[i][j] = data_src[i][j];
      }
    }

  }

  private void setEditableArray(boolean [][] array, boolean flag) {

    int N_r = array.length;
    int N_c = array[0].length;

    for (int i = 0; i < N_r; i++) {
      for (int j = 0; j < N_c; j++) {
        array[i][j] = flag;
      }
    }
    
  }

  public void resizeTable(int N_r_new, int N_c_new) {

    int N_r = data.length;
    int N_c = data[0].length;

    Object[][] data_new = null;
    boolean[][] dataEditable_new = null;

    String[] columnNames_new;

    data_new = new Object[N_r_new][N_c_new];
    dataEditable_new = new boolean[N_r_new][N_c_new];

    /* when resizing copy data to the new array */
    copyData(data, data_new);
    data = data_new;

    copyData(dataEditable, dataEditable_new);
    dataEditable = dataEditable_new;

    /* when resizing columns copy names to larger array */
    if (data_new[0].length != N_c) {
      columnNames_new = new String[N_c_new];
      for (int k = 0; k < N_c; k++) {
        columnNames_new[k] = columnNames[k];
      }
      for (int k = N_c; k < N_c_new; k++) {
        columnNames_new[k] = "";
      }
      columnNames = columnNames_new;
    }

  } /* end resizeTable */

}







