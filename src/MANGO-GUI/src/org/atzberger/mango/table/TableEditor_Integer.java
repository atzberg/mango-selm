
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
 * Handles editing of integer data types.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a> 
 */
public class TableEditor_Integer extends AbstractCellEditor
        implements TableCellEditor {

  int                 dataValue = 0;
  JFormattedTextField jField;
  
  public TableEditor_Integer() {

  }

  //Implement the one CellEditor method that AbstractCellEditor doesn't.
  public Object getCellEditorValue() {
    boolean flagSet = false;

    Object returnVal = jField.getValue();

    dataValue = ((Number)returnVal).intValue();
          
    return dataValue;
  }

  //Implement the one method defined by TableCellEditor.
  public Component getTableCellEditorComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               int row,
                                               int column) {

    dataValue = (Integer) value;

    jField    = new JFormattedTextField();

    jField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        act_on_KeyReleased(evt);
      }
    });

    jField.setBorder(null);
    
    jField.setValue(new Integer(dataValue));
        
    return jField;
    
  }

  void act_on_KeyReleased(java.awt.event.KeyEvent evt) {

    if (evt.getKeyCode() == evt.VK_ENTER) {      
      stopCellEditing();
    }
    
  }

  
}
