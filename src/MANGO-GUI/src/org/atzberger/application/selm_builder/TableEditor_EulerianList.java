package org.atzberger.application.selm_builder;

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
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * Customised editor for data within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableEditor_EulerianList extends AbstractCellEditor
        implements TableCellEditor, PropertyChangeListener,
        ActionListener {

  final static boolean DEBUG = false;

  TableData_EulerianList  dataValue;

  JButton      jButton = null;
  JComboBox    jComboBox = null;
  JDialog_Edit dialog = null;

  protected static final String JBUTTON_EDIT = "JBUTTON_EDIT";
  protected static final String JCOMBO_EDIT = "JCOMBO_EDIT";

  public TableEditor_EulerianList() {
    
    //Set up the editor (from the table's point of view),
    //which is a jButton.
    //This jButton brings up the color chooser dialog,
    //which is the editor from the user's point of view.
    jButton = new JButton();
    jButton.setActionCommand(JBUTTON_EDIT);
    jButton.addActionListener(this);
    jButton.setBorderPainted(false);

    jComboBox = new JComboBox();
    jComboBox.setActionCommand(JCOMBO_EDIT);
    jComboBox.addActionListener(this);

    setupDialog();
    
  }

  void setupDialog() {

    //Set up the dialog that the jButton brings up.
    dialog = new JDialog_Edit_InteractionList();

    dialog.addPropertyChangeListener(this);

    /* setup the responses to the dialog */
    dialog.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        dialogClosing(we);
      }
    });
    
  }

  /**
   * Handles events from the editor jButton and from
   * the dialog's OK jButton.
   */
  public void actionPerformed(ActionEvent e) {
    
    if (JBUTTON_EDIT.equals(e.getActionCommand())) {

      //The user has clicked the cell, so
      //bring up the dialog.
      //jButton.setBackground(currentColor);
      //colorChooser.setColor(currentColor);      
      dialog.setVisible(true);

      dialog.setAlwaysOnTop(true);
      
      //Make the renderer reappear.
      fireEditingStopped();

    } else if (JCOMBO_EDIT.equals(e.getActionCommand())) {
      dataValue.itemSelectedIndex = jComboBox.getSelectedIndex();
    } else { //User pressed dialog's "OK" jButton.
      //currentColor = colorChooser.getColor();
      dataValue = (TableData_EulerianList) dialog.getDataValue();
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

    Component returnComponent = null;

    dataValue = (TableData_EulerianList)value;

    //dataValue.flagDisplayMode = TableData_EulerianList.DISPLAY_NAME_LIST;

    switch (dataValue.flagDisplayMode) {

      case TableData_EulerianList.DISPLAY_MULTIPLE_CHOICE:

        jComboBox.removeAllItems();
        for (int k = 0; k < dataValue.eulerianList.length; k++) {
          jComboBox.addItem(dataValue.eulerianList[k].EulerianName);
        }
        jComboBox.setToolTipText("Select from options by clicking");
        
        returnComponent = jComboBox;

      break;

      case TableData_EulerianList.DISPLAY_NAME_LIST:
      case TableData_EulerianList.DISPLAY_DEFAULT:
      case TableData_EulerianList.DISPLAY_BRACKET_NAME:
      default:
        String dataName = "Array Values";
        String dataType = dataValue.getClass().getName();

        setupDialog();

        dialog.setDataValue(dataValue, dataName, dataType);
        
        returnComponent = jButton;
      break;
    }

    return returnComponent;

  }

  /** This method reacts to state changes in the option pane. */
  public void propertyChange(PropertyChangeEvent e) {

    String prop     = e.getPropertyName();
    String code_str = this.getClass().getName();

    if (e.getSource() == dialog) {

      if (DEBUG)
        System.out.println(code_str + ": Dialog property change event. Property Name = " + prop);

      if (prop.equals(dialog.ACTION_DIALOG_DONE)) {
        dataValue = (TableData_EulerianList) dialog.getDataValue();

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
