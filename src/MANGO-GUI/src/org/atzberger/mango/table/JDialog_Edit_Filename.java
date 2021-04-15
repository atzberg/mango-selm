package org.atzberger.mango.table;

import org.atzberger.mango.table.TableData_Filename;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 *
 * Dialog editor for changing filename attribute data type found within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JDialog_Edit_Filename extends JDialog_Edit {
  
  JButton       jButton_trigger;
  JFileChooser  fileChooser;
  JDialog       dialog;

  public JDialog_Edit_Filename() {

    initComponents();

  }

  public JDialog_Edit_Filename(JButton button, Object oldValue_in, String ParamName_in, String ParamType_in) {

    jButton_trigger = button;

    initComponents();

  }

  void initComponents() {

    //Set up the dialog that the button brings up.
    fileChooser = new JFileChooser();
           
  }

  public Object getDataValue() {

    //newValue = (Object) currentColor;

    if (flagChangeValue) {
      return newValue;
    } else {
      return oldValue;
    }

  }

  public void setDataValue(Object value, String dataName, String dataType) {

    super.setDataValue(value, dataName, dataType);

    //colorChooser.setColor((Color) oldValue);

    //jTextField_SimpleValue_Value.setText(oldValue.toString());

  }

  @Override
  public void setModal(boolean modal) {

    //dialog.setModal(modal);

    /* override to make sure parent dialog window (invisible)
     * does not become modal.
     */
    
  }

  @Override
  public void setVisible(boolean flag) {

    if (flag == true) {
      int returnVal = fileChooser.showDialog(jButton_trigger, "Select");

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();

        System.out.println("File select was:" + file.getAbsolutePath());
        
        TableData_Filename tableData_Filename = (TableData_Filename)oldValue;
        tableData_Filename.setFilename(file.getAbsolutePath());
        newValue                              = (Object) tableData_Filename;

        this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);
      }
      
    }

    /*
    dialog.setVisible(flag);

    System.out.println("Trying to set color chooser to be visible");
     */
    
  }
   
}

