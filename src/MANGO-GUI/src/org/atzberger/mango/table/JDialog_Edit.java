/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.mango.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Prototype editor used for testing purposes.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JDialog_Edit extends JDialog {

  public static final String ACTION_DIALOG_DONE   = "ACTION_DIALOG_DONE";

  protected Boolean    flagChangeValue = false; /* indicates if value should be changed */
  
  protected Object     oldValue = null;
  protected Object     newValue = null;

  protected String     ParameterName = "";
  protected String     ParameterType = "";

  public JDialog_Edit() {

  }

  public JDialog_Edit(Object oldValue_in, String ParameterName_in, String ParameterType_in) {
    this();

    setDataValue(oldValue_in, ParameterName_in, ParameterType_in);
    
  }


  public JDialog_Edit(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
  }
  
  public Object getDataValue() {
    return null;
  }

  public void setDataValue(Object value, String dataName, String dataType) {
    oldValue      = value;
    ParameterName = dataName;
    ParameterType = dataType;
  }

} /* end of class */
