
package org.atzberger.mango.table;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JTable;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;

/**
 *
 * Handles editing of Double data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_Units_Double extends AbstractCellEditor
        implements TableCellEditor {

  TableData_Units_Double    dataValue = null;
  JFormattedTextField jField;
  
  public TableEditor_Units_Double() {

  }

  //Implement the one CellEditor method that AbstractCellEditor doesn't.
  public Object getCellEditorValue() {
    boolean flagSet = false;

    Object returnVal = jField.getValue();

    dataValue.value = ((Number)returnVal).doubleValue();
          
    return dataValue;
  }

  //Implement the one method defined by TableCellEditor.
  public Component getTableCellEditorComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               int row,
                                               int column) {

    dataValue = (TableData_Units_Double) value;

    jField    = new JFormattedTextField(dataValue.getFormatEdit());

    jField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        act_on_KeyReleased(evt);
      }
    });

    jField.setBorder(null);
    
    jField.setValue(new Double(dataValue.value));
        
    return jField;
    
  }

  void act_on_KeyReleased(java.awt.event.KeyEvent evt) {

    if (evt.getKeyCode() == evt.VK_ENTER) {      
      stopCellEditing();
    }
    
  }

  
}
