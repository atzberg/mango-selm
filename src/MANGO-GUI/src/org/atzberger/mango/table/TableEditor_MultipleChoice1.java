
package org.atzberger.mango.table;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;


/**
 *
 * Handles editing of multiple choice data types.  Offers multiple choice menu to select value desired.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_MultipleChoice1 extends AbstractCellEditor
        implements TableCellEditor {

  TableData_MultipleChoice1 dataValue = null;

  String lastSelectedItem = null;

  public TableEditor_MultipleChoice1() {

  }

  //Implement the one CellEditor method that AbstractCellEditor doesn't.
  public Object getCellEditorValue() {
    return dataValue;
  }

  //Implement the one method defined by TableCellEditor.
  public Component getTableCellEditorComponent(JTable table,
          Object value,
          boolean isSelected,
          int row,
          int column) {
    
    JComboBox jComboBox;

    jComboBox = new JComboBox();

    dataValue = (TableData_MultipleChoice1) value;

    jComboBox.setModel(new javax.swing.DefaultComboBoxModel(dataValue.ItemPossibleValues));
    jComboBox.setSelectedItem(dataValue.getSelectedItem());

    jComboBox.setToolTipText("Select from options.");

    jComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        act_itemStateChanged(evt);
      }
    });

    return jComboBox;
  }

  public void act_itemStateChanged(java.awt.event.ItemEvent evt) {

    dataValue.selectMatch((String)evt.getItem());

//    System.out.println("TableEditor_MultipleChoice1 : evt.getID() = " + evt.getID());
//    System.out.println("TableEditor_MultipleChoice1 : evt.ITEM_STATE_CHANGED = " + evt.ITEM_STATE_CHANGED);
//    System.out.println("TableEditor_MultipleChoice1 : evt.getStateChange() = " + evt.getStateChange());
//    System.out.println("TableEditor_MultipleChoice1 : evt.SELECTED = " + evt.SELECTED);
//    System.out.println("TableEditor_MultipleChoice1 : evt.DESELECTED = " + evt.DESELECTED);
//    System.out.println("TableEditor_MultipleChoice1 : (String) evt.getItem() = " + (String) evt.getItem());

    if (evt.getID() == evt.ITEM_STATE_CHANGED) {

      if (evt.getStateChange() == evt.SELECTED) {

        String selectStr = (String) evt.getItem();

//        System.out.println("TableEditor_MultipleChoice1 : selectStr = " + selectStr);

        if ((lastSelectedItem == null) || (!lastSelectedItem.equals(selectStr))) {

          //System.out.println("TableEditor_MultipleChoice1 : !!itemStateChanged!!! : (String) e.getItem() = " + (String) e.getItem());
          dataValue.selectMatch(selectStr);

          if (dataValue.tableModel != null) {
            lastSelectedItem = selectStr;

            /* fire data change event */
            dataValue.tableModel.fireTableDataChanged();

//            System.out.println("TableEditor_MultipleChoice1 : dataValue.tableModel.fireTableDataChanged();");
          }

        } /* evt.getItem() */

      } /* e.SELECTED */

    } /* e.getID() */

    fireEditingStopped();
    
  }


}
