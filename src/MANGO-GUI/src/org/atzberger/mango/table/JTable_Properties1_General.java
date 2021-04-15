/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.mango.table;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * Customised table that allows for displaying many different types of data types.
 *
 * This class provides interface to choose appropriate editor to invoke on the data
 * and renders to display to the user the particular data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTable_Properties1_General extends JTable {

  public JTable_Properties1_General() {
    super();
  }

  public JTable_Properties1_General(TableModel dm) {
    this(dm, null, null);
  }

  public JTable_Properties1_General(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
    super(dm, cm, sm);
  }

  @Override
  public TableCellRenderer getCellRenderer(int row, int column) {

    /* PJA: set the renderer based on the specific cell object type */
    /* register the default renderer for this type on-creation above */
    Object            dataVal;
    TableCellRenderer renderer;

    dataVal  = super.dataModel.getValueAt(row,column);

    if (dataVal != null)
      renderer = this.getDefaultRenderer(dataVal.getClass());
    else
      renderer = this.getDefaultRenderer(String.class);

    return renderer;

  }

  @Override
  public TableCellEditor getCellEditor(int row, int column) {

    /* PJA: set the renderer based on the specific cell object type */
    /* register the default renderer for this type on-creation above */
    Object          dataVal;
    TableCellEditor editor;

    dataVal  = super.dataModel.getValueAt(row,column);

    if (dataVal != null)
      editor   = this.getDefaultEditor(dataVal.getClass());
    else
      editor   = this.getDefaultEditor(String.class);

    /*
    if ((row == 0) & (column == 0)) {
      System.out.println("(0,0) column edit");
    }
     */
    
    return editor;
  }

  /* we override the default behavior so no checking is done on
   * row and column, since we do this automatically and add entries
   * to data[][] as needed.
   */
  /*
  @Override
  public void setValueAt(Object aValue, int row, int column) {
    getModel().setValueAt(aValue, row, column);
  }
   */

}