package org.atzberger.mango.table;

import org.atzberger.mango.units.JDialog_Edit_Units_Ref;
import org.atzberger.mango.table.JDialog_Edit;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * Handles editing of physical units in use.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_Units_Ref extends AbstractCellEditor
        implements TableCellEditor, PropertyChangeListener,
        ActionListener {

  final static boolean DEBUG = false;

  Object       dataValue;

  JButton      button;
  JDialog_Edit dialog = null;

  protected static final String EDIT = "edit";

  public TableEditor_Units_Ref() {
    //Set up the editor (from the table's point of view),
    //which is a button.
    //This button brings up the color chooser dialog,
    //which is the editor from the user's point of view.
    button = new JButton();
    button.setActionCommand(EDIT);
    button.addActionListener(this);
    button.setBorderPainted(false);

    setupDialog();
    
  }

  void setupDialog() {

    //Set up the dialog that the button brings up.
    dialog = new JDialog_Edit_Units_Ref();

    dialog.addPropertyChangeListener(this);

    /* setup the responses to the dialog */
    dialog.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        dialogClosing(we);
      }
    });
    
  }

  /**
   * Handles events from the editor button and from
   * the dialog's OK button.
   */
  public void actionPerformed(ActionEvent e) {
    
    if (EDIT.equals(e.getActionCommand())) {

      //The user has clicked the cell, so
      //bring up the dialog.
      //button.setBackground(currentColor);
      //colorChooser.setColor(currentColor);      
      dialog.setVisible(true);

      dialog.setAlwaysOnTop(true);
      
      //Make the renderer reappear.
      fireEditingStopped();

    } else { //User pressed dialog's "OK" button.
      //currentColor = colorChooser.getColor();
      dataValue = dialog.getDataValue();
    }

  }


  

  //Implement the one CellEditor method that AbstractCellEditor doesn't.
  public Object getCellEditorValue() {    
    return dataValue;
  }

  //Implement the one method defined by TableCellEditor.
  public Component getTableCellEditorComponent(JTable table,
                                               Object  value,
                                               boolean isSelected,
                                               int row,
                                               int column) {

    dataValue = value;

    String dataName = "Array Values";
    String dataType = dataValue.getClass().getName();

    setupDialog();

    dialog.setDataValue(dataValue, dataName, dataType);

    return button;
  }

  /** This method reacts to state changes in the option pane. */
  public void propertyChange(PropertyChangeEvent e) {

    String prop     = e.getPropertyName();
    String code_str = this.getClass().getName();

    if (e.getSource() == dialog) {

      if (DEBUG)
        System.out.println(code_str + ": Dialog property change event. Property Name = " + prop);

      if (prop.equals(dialog.ACTION_DIALOG_DONE)) {
        dataValue = dialog.getDataValue();

        //Make the renderer reappear.
        fireEditingStopped();
        
        if (DEBUG)
          System.out.println(code_str + ": Setting dataValue");
        
      }

    } /* end event from dialog */

  }

  /* dialog on-close */
  public void dialogClosing(WindowEvent we) {

    /* handle events when dialog is closed by the user */
    System.out.println("Dialog Window Closed");

    dialog.setVisible(false);

    dialog.firePropertyChange(dialog.ACTION_DIALOG_DONE, 1, 0);

    //dialog.remove(dialog);

  }


}
