package org.atzberger.application.selm_builder;

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


/**
 *
 * Customised editor for data within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_LAMMPS_PAIR_COEFF_tableFilename extends AbstractCellEditor
                                  implements TableCellEditor,
			                                  ActionListener {

    JButton       button;
    JFileChooser  fileChooser;
    JDialog       dialog;

    Object        newValue = null;
    Object        oldValue = null;

    protected static final String EDIT = "edit";

    public TableEditor_LAMMPS_PAIR_COEFF_tableFilename() {

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

            //System.out.println("File select was:" + file.getAbsolutePath());

            TableData_LAMMPS_pair_coeff_tableFilename tableData_Filename = (TableData_LAMMPS_pair_coeff_tableFilename) oldValue;
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
        oldValue = value;
        newValue = oldValue;
        //currentColor = (Color)value;
        return button;
    }
}

