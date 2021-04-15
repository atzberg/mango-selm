package org.atzberger.mango.table;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 *
 * Handles editing of file name data type by invoking a dialog file chooser.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_Filename extends AbstractCellEditor
                                  implements TableCellEditor,
			                                  ActionListener {

    JButton       button;
    JFileChooser  fileChooser;
    JDialog       dialog;
    JTextField    jField;

    Object        newValue = null;
    Object        oldValue = null;

    protected static final String EDIT = "edit";

    public TableEditor_Filename() {

        // -- Set up the editor (from the table's point of view),
        //which is a button.
        //This button brings up the color chooser dialog,
        //which is the editor from the user's point of view.
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        // -- Set up the dialog that the button brings up.
        fileChooser = new JFileChooser();

        // -- Set up text field.
        jField    = new JTextField();

        jField.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyReleased(java.awt.event.KeyEvent evt) {
          act_on_KeyReleased(evt);
        }
        });

        jField.setBorder(null);

        jField.setText("");
        
    }

    /**
     * Handles events from the editor button and from
     * the dialog's OK button.
     */
    public void actionPerformed(ActionEvent e) {

        if (EDIT.equals(e.getActionCommand())) {

          int returnVal = fileChooser.showDialog(button, "Select");

          if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            System.out.println("File select was:" + file.getAbsolutePath());

            TableData_Filename tableData_Filename = (TableData_Filename) oldValue;
            tableData_Filename.setFilename(file.getAbsolutePath());
            tableData_Filename.recordLastEditorChangeTime();

            newValue                              = (Object) tableData_Filename;

            button.setText(tableData_Filename.getFilename());

            //this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);

          }
          
        }
    }

    //Implement the one CellEditor method that AbstractCellEditor doesn't.
    public Object getCellEditorValue() {     
      return newValue;
    }

    //Implement the one method defined by TableCellEditor.
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        Component editComponent = null;

        oldValue = value;
        newValue = oldValue;

        TableData_Filename data = (TableData_Filename)value;

        if (data.getPreferredEditMode() == TableData_Filename.EDIT_MODE_FILE_CHOOSER) {
          /* show button when opening file chooser */
          editComponent = button;
        } else if (data.getPreferredEditMode() == TableData_Filename.EDIT_MODE_STRING) {
          editComponent = jField;
        } else {
          /* show button when opening file chooser */
          editComponent = button;
        }

        //currentColor = (Color)value;
        return editComponent;
        
    }



   /* handle events for the JTextField */
   void act_on_KeyReleased(java.awt.event.KeyEvent evt) {

    if (evt.getKeyCode() == evt.VK_ENTER) {
      newValue = (Object) jField.getText();
      stopCellEditing();
    }

  }

}

