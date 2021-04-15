package org.atzberger.mango.table;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * Dialog editor for changing color attribute data type found within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JDialog_Edit_Color extends JDialog_Edit
           implements ActionListener {
  
  JButton       jButton_trigger;
  JColorChooser colorChooser;
  JDialog       dialog;

  public JDialog_Edit_Color() {

    initComponents();

  }

  public JDialog_Edit_Color(JButton button, Object oldValue_in, String ParamName_in, String ParamType_in) {

    jButton_trigger = button;

    initComponents();

  }

  void initComponents() {

    //Set up the dialog that the button brings up.
    colorChooser = new JColorChooser();
    dialog = JColorChooser.createDialog(jButton_trigger,
                                        "Pick a Color",
                                        true, //modal
                                        colorChooser,
                                        this, //OK button handler
                                        null); //no CANCEL button handler
    
  }

    /**
     * Handles events from the editor button and from
     * the dialog's OK button.
     */
    public void actionPerformed(ActionEvent e) {

      System.out.println("Color Editor Event: " + e.getActionCommand());

      if ("OK".equals(e.getActionCommand())) {
        flagChangeValue = true;        
        newValue        = (Object) colorChooser.getColor();

        this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);
      }
      
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

    colorChooser.setColor((Color) oldValue);

    //jTextField_SimpleValue_Value.setText(oldValue.toString());

  }

  @Override
  public void setModal(boolean modal) {
    dialog.setModal(modal);
    
  }

  @Override
  public void setVisible(boolean flag) {
    dialog.setVisible(flag);

    System.out.println("Trying to set color chooser to be visible");
  }
   
}

