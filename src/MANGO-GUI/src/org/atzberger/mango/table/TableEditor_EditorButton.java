package org.atzberger.mango.table;

import javax.swing.table.TableCellEditor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * Handles editing of generic data type by invoking a dialog editor for this type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_EditorButton extends AbstractCellEditor
  implements TableCellEditor,
  ActionListener, PropertyChangeListener {

  int                    row;
  int                    column;
  JTable                 table;
  JButton                button;  
  JDialog_Edit           dialog;

  /* data to edit located at (row, column - 1) */
  String dataType   = "Not_Set_Yet";
  String dataName   = "Data Name Here";
  Object dataValue  = "Test Value";

  int            list_max_num_dataTypes = 100;
  int            list_num_dataTypes     = 0;
  String[]       list_dataTypes         = new String[list_max_num_dataTypes];
  JDialog_Edit[] list_jDialog_Edit      = new JDialog_Edit[list_max_num_dataTypes];

  /* the special button located at the current cell */
  TableData_EditorButton specialButtonData;

  protected static final String ACTION_PERFORM_EDIT = "ACTION_PERFORM_EDIT";

  //protected static final String EDIT_DATA_TYPE = "String";
  public TableEditor_EditorButton() {

    //Set up the editor (from the table's point of view),
    //which is a button.
    //This button brings up the color chooser dialog,
    //which is the editor from the user's point of view.

    button = new JButton();
    button.setActionCommand(ACTION_PERFORM_EDIT);
    button.addActionListener(this);
    button.setBorderPainted(false);

    /*
    //Set up the dialog that the button brings up.
    colorChooser = new JColorChooser();
    dialog = JColorChooser.createDialog(button,
    "Pick a Color",
    true,  //modal
    colorChooser,
    this,  //OK button handler
    null); //no CANCEL button handler
     */

    /* link to button (try adding it to the component) */
    //button.add(dialog);

  }

  public void addDataType(String dataType, JDialog_Edit jDialog_Edit) {

    /* WARNING!!! We only currently allow up to 100 data types */
    list_dataTypes[list_num_dataTypes]    = dataType;
    list_jDialog_Edit[list_num_dataTypes] = jDialog_Edit;
    list_num_dataTypes++;

  }

  /**
   * Handles events from the editor button and from
   * the dialog's OK button.
   */
  public void actionPerformed(ActionEvent e) {

    Boolean flagNotSet = true;

    if (ACTION_PERFORM_EDIT.equals(e.getActionCommand())) {
     
      //System.out.println("dataType of Obj to edit = " + dataType);

      /* -- Determine the type of data being edited */
      for (int k = 0; k < list_num_dataTypes; k++) {
        if (flagNotSet && dataType.equals(list_dataTypes[k])) {
          flagNotSet = false;
          dialog     = list_jDialog_Edit[k];
        }
      } /* end of k loop */

      if (flagNotSet) {
        dialog = null;

        /* issue message that this is an unrecognized type */
        JOptionPane.showMessageDialog(button, "No special editor for this data type. (dataType = " + dataType + ")");

        return;
      } 

      /* setup the dialog data */
      dialog.setDataValue(dataValue, dataName, dataType);
      
      /* setup the responses to the dialog */
      dialog.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
          dialogClosing(we);
        }
      });

      dialog.setModal(true); /* requires edit done before next instance created */

      dialog.addPropertyChangeListener(this);

      dialog.setVisible(true);

      //Make the renderer reappear.
      fireEditingStopped();

      System.out.println("Action: " + ACTION_PERFORM_EDIT);

    } else { //User pressed dialog's "OK" button.
      // currentTableData set from editor
      //currentColor = colorChooser.getColor();
      System.out.println("Else Action");
    }

    /*
    if (ACTION_BUTTON_OK.equals(e.getActionCommand())) {
    dialog.setVisible(false);
    }

    if (ACTION_BUTTON_CANCEL.equals(e.getActionCommand())) {
    dialog.setVisible(false);
    }
     */

  }

  /* dialog on-close */
  public void dialogClosing(WindowEvent we) {

    /* handle events when dialog is closed by the user */
    System.out.println("Dialog Window Closed");

    dialog.setVisible(false);

    dialog.firePropertyChange(dialog.ACTION_DIALOG_DONE, 1, 0);

    //dialog.remove(dialog);

  }

  /** This method reacts to state changes in the option pane. */
  public void propertyChange(PropertyChangeEvent e) {

    String prop = e.getPropertyName();

    if (e.getSource() == dialog) {

      //System.out.println("Dialog property change event. Property Name = " + prop);
      
      dataValue = dialog.getDataValue();

    } /* end event from dialog */

  }

  //Implement the one CellEditor method that AbstractCellEditor doesn't.
  public Object getCellEditorValue() {

    /* Edit the entry on which this button is designed to act */
    table.setValueAt(dataValue, row, column - 1);

    /* return edit button */
    return specialButtonData; /* note we only change the data entry this button edits */
  }

  //Implement the one method defined by TableCellEditor.
  public Component getTableCellEditorComponent(JTable table_in,
                                               Object value,
                                               boolean isSelected,
                                               int row_in,
                                               int column_in) {

    /* assumes in table (data Name, data Value, EditButton) */
    table      = table_in;
    row        = row_in;
    column     = column_in;
    dataName   = (String) table.getValueAt(row, column - 2);
    dataValue  = table.getValueAt(row, column - 1);
    dataType   = dataValue.getClass().getName();

    //Double[] tmp = new Double[10];

    //String tmpStr   = tmp.getClass().getName();
    
    //currentColor = (Color)value;
    specialButtonData = (TableData_EditorButton) value;

    return button;
  }
  
}
