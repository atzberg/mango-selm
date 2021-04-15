package org.atzberger.mango.table;

import org.atzberger.mango.table.TableData_MultipleChoice1;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * Handles displaying the data type within the table fields.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableRenderer_MultipleChoice1 extends JComboBox
                           implements TableCellRenderer, ItemListener  {

  TableData_MultipleChoice1 dataValue;
  boolean flagFireTableEvent = true;
  Object  lastSelectedItem = null;

  public TableRenderer_MultipleChoice1() {
    setOpaque(true);

    this.addItemListener(this);
    
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

    dataValue = (TableData_MultipleChoice1) value;

    setModel(new javax.swing.DefaultComboBoxModel(dataValue.ItemPossibleValues));    
    setSelectedItem(dataValue.ItemSelected);

    //System.out.println("TableRenderer_MultipleChoice1 : dataValue.ItemSelected = " + dataValue.ItemSelected);
   
    setToolTipText("Select from options.");

    /* we display the combination in plain-text b
     * but use the combo box in the editor.
     *
     * Can change this by simply returning "this"
     * instead of jLabel
     */
    JLabel jLabel = new JLabel();
    jLabel.setText(dataValue.ItemSelected.toString());
    jLabel.setHorizontalAlignment(jLabel.LEFT);

    return jLabel;
  }

  public void itemStateChanged(ItemEvent e) {

//    System.out.println("TableRenderer_MultipleChoice1 : e.getID() = " + e.getID());
//    System.out.println("TableRenderer_MultipleChoice1 : e.ITEM_STATE_CHANGED = " + e.ITEM_STATE_CHANGED);
//    System.out.println("TableRenderer_MultipleChoice1 : e.getStateChange() = " + e.getStateChange());
//    System.out.println("TableRenderer_MultipleChoice1 : e.SELECTED = " + e.SELECTED);
//    System.out.println("TableRenderer_MultipleChoice1 : e.DESELECTED = " + e.DESELECTED);
//    System.out.println("TableRenderer_MultipleChoice1 : (String) e.getItem() = " + (String) e.getItem());
//
//    if (e.getID() == e.ITEM_STATE_CHANGED) {
//
//      if (e.getStateChange() == e.SELECTED) {
//
//        String selectStr = (String) e.getItem();
//
//        System.out.println("TableRenderer_MultipleChoice1 : selectStr = " + selectStr);
//
//        if ((lastSelectedItem == null) || (!lastSelectedItem.equals(selectStr))) {
//
//          //System.out.println("TableRenderer_MultipleChoice1 : !!itemStateChanged!!! : (String) e.getItem() = " + (String) e.getItem());
//          dataValue.selectMatch(selectStr);
//
//          if (dataValue.tableModel != null) {
//            lastSelectedItem = selectStr;
//
//            /* fire data change event */
//            dataValue.tableModel.fireTableDataChanged();
//
//            System.out.println("TableRenderer_MultipleChoice1 : dataValue.tableModel.fireTableDataChanged();");
//          }
//
//        } /* e.getItem() */
//
//      } /* e.SELECTED */
//
//    } /* e.getID() */

  }



}
