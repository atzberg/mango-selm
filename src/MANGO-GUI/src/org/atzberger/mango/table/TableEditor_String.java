
package org.atzberger.mango.table;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JTable;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;


/**
 *
 * Handles editing of string data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_String extends AbstractCellEditor
        implements TableCellEditor {

  String dataValue = new String("");

  JTextField jTextField = new JTextField();
    
  public TableEditor_String() {

  }

  //Implement the one CellEditor method that AbstractCellEditor doesn't.
  public Object getCellEditorValue() {
    boolean flagSet = false;
    
    dataValue = jTextField.getText();
          
    return dataValue;
  }

  //Implement the one method defined by TableCellEditor.
  public Component getTableCellEditorComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               int row,
                                               int column) {

    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = false;

    dataValue = (String) value;
    jTextField.setText(dataValue);
   
    jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        act_on_KeyReleased(evt);
      }
    });

    jTextField.setBorder(null);

    isSelected = true; /* WARNING: assumes cell will be selected! */

    if (isBordered) {
      if (isSelected) {
        if (selectedBorder == null) {
          selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                  table.getSelectionBackground());
        }
        jTextField.setBorder(selectedBorder);
        //jTextField.setBackground(table.getSelectionBackground());
      } else {
        if (unselectedBorder == null) {
          unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                  table.getBackground());
        }
        jTextField.setBorder(unselectedBorder);
        //jTextField.setBackground(table.getBackground());
      }
    }
               
    return jTextField;
    
  }

  void act_on_KeyReleased(java.awt.event.KeyEvent evt) {

    if (evt.getKeyCode() == evt.VK_ENTER) {      
      stopCellEditing();
    }
    
  }

  public boolean shouldSelectCell(EventObject anEvent) {
    return true;
  }
  
}
