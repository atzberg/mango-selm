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
 * Handles editing of file path data types.  Offers file chooser to select path.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_Pathname extends AbstractCellEditor
                                  implements TableCellEditor,
			                                  ActionListener {

    JButton       button;
    JFileChooser  fileChooser;
    JDialog       dialog;
    JTextField    jField;

    Object        newValue = null;
    Object        oldValue = null;

    protected static final String EDIT = "edit";

    public TableEditor_Pathname() {

        //Set up the editor (from the table's point of view),
        //which is a button.
        //This button brings up the color chooser dialog,
        //which is the editor from the user's point of view.
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        //Set up the dialog that the button brings up.
        fileChooser = new JFileChooser();

        // -- Set up text field.
        jField    = new JTextField();

        jField.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyReleased(java.awt.event.KeyEvent evt) {
          act_on_KeyReleased(evt);
        }
        });

       jField.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            act_on_jTextField(evt);
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

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fileChooser.showDialog(button, "Select Path");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();

          //System.out.println("File select was:" + file.getAbsolutePath());

          TableData_Pathname tableData_Pathname = (TableData_Pathname) oldValue;
          tableData_Pathname.pathname = file.getAbsolutePath() + file.separator;
          
          newValue = (Object) tableData_Pathname;
                    
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

        TableData_Pathname data = (TableData_Pathname)value;

        if (data.getPreferredEditMode() == TableData_Filename.EDIT_MODE_FILE_CHOOSER) {
          /* show button when opening file chooser */
          editComponent = button;
        } else if (data.getPreferredEditMode() == TableData_Filename.EDIT_MODE_STRING) {
          TableData_Pathname tableData_Pathname = (TableData_Pathname) oldValue;
          jField.setText(tableData_Pathname.pathname);
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
      
      TableData_Pathname tableData_Pathname = (TableData_Pathname) oldValue;
      tableData_Pathname.pathname           = jField.getText();
      newValue                              = (Object) tableData_Pathname;

      System.out.println("TableEditor_Pathname : Enter release + stop editing...");

      stopCellEditing();
    }

  }

   /* handle events for the JTextField */
   void act_on_jTextField(ActionEvent evt) {
     TableData_Pathname tableData_Pathname = (TableData_Pathname) oldValue;
     tableData_Pathname.pathname           = jField.getText();
     newValue                              = (Object) tableData_Pathname;
   }



}

