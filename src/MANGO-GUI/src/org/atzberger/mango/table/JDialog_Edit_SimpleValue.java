/* Handles editing of simple value types such as
 * String, Double, Integer, etc...
 */

package org.atzberger.mango.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Dialog editor for changing a simple data type found within a table.  This includes
 * Doubles, Integers, Strings.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JDialog_Edit_SimpleValue extends JDialog_Edit implements ActionListener {

  protected static final String ACTION_BUTTON_OK = "ACTION_BUTTON_OK";
  protected static final String ACTION_BUTTON_CANCEL = "ACTION_BUTTON_CANCEL";
  JButton jButton_trigger = null;  /* button triggering opening of the dialog */

  Boolean flagChangeValue = false; /* indicates if value should be changed */

  Label label_parameter_name = new java.awt.Label();
  JTextField jTextField_SimpleValue_Value = new javax.swing.JTextField();
  Label label_Request_SimpleValue_Value = new java.awt.Label();
  JButton jButton_Cancel = new javax.swing.JButton();
  JButton jButton_OK = new javax.swing.JButton();

  public JDialog_Edit_SimpleValue() {

    initComponents();

  }

  public JDialog_Edit_SimpleValue(JButton button, Object oldValue_in, String ParamName_in, String ParamType_in) {

    super(oldValue_in, ParamName_in, ParamType_in);

    jButton_trigger = button;

    initComponents();

  }

  void initComponents() {

    this.setTitle("Edit Simple Value");
    this.setName("jDialog_Edit_SimpleValue");

    //label_parameter_name.setFont(resourceMap.getFont("label_parameter_name.font")); // NOI18N
    label_parameter_name.setName("label_parameter_name"); // NOI18N
    label_parameter_name.setText(ParameterName); // NOI18N

    if (oldValue != null) {
      jTextField_SimpleValue_Value.setText(oldValue.toString()); // NOI18N
    } else {
      jTextField_SimpleValue_Value.setText(""); // NOI18N
    }
    jTextField_SimpleValue_Value.setName("jTextField_SimpleValue_Value"); // NOI18N

    //label_Request_SimpleValue_Value.setFont(resourceMap.getFont("label_Request_SimpleValue_Value.font")); // NOI18N
    label_Request_SimpleValue_Value.setName(ParameterType); // NOI18N
    label_Request_SimpleValue_Value.setText("Enter Value"); // NOI18N

    jButton_Cancel.setText("Cancel"); // NOI18N
    jButton_Cancel.setName("jButton_Cancel"); // NOI18N
    jButton_Cancel.addActionListener(this);
    jButton_Cancel.setActionCommand(ACTION_BUTTON_CANCEL);

    jButton_OK.setText("OK"); // NOI18N
    jButton_OK.setName("jButton_OK"); // NOI18N
    jButton_OK.addActionListener(this);
    jButton_OK.setActionCommand(ACTION_BUTTON_OK);

    javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this.getContentPane());
    this.getContentPane().setLayout(thisLayout);
    thisLayout.setHorizontalGroup(
      thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(thisLayout.createSequentialGroup().addContainerGap().addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(label_parameter_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(label_Request_SimpleValue_Value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(thisLayout.createSequentialGroup().addComponent(jButton_OK).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton_Cancel).addGap(97, 97, 97)).addComponent(jTextField_SimpleValue_Value, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(38, Short.MAX_VALUE)));
    thisLayout.setVerticalGroup(
      thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(thisLayout.createSequentialGroup().addContainerGap().addComponent(label_parameter_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(label_Request_SimpleValue_Value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField_SimpleValue_Value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE).addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton_OK).addComponent(jButton_Cancel)).addContainerGap()));

    Dimension size = new Dimension(398, 220);
    this.setPreferredSize(size);
    this.setMinimumSize(size);
    this.setMaximumSize(size);

    /* setup so object destroyed on close */
    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

  }

  public void actionPerformed(ActionEvent e) {

    if (ACTION_BUTTON_OK.equals(e.getActionCommand())) {
      this.setVisible(false);
      flagChangeValue = true;

      /* make sure the value is of correct type */

      newValue = (Object) jTextField_SimpleValue_Value.getText();

      this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);

    }

    if (ACTION_BUTTON_CANCEL.equals(e.getActionCommand())) {

      this.setVisible(false);

      flagChangeValue = false;
      newValue        = null;
      
      this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);
    }

  }

  public Object getDataValue() {

    newValue = (Object) jTextField_SimpleValue_Value.getText();

    if (flagChangeValue) {
      return newValue;
    } else {
      return oldValue;
    }

  }

  public void setDataValue(Object value, String dataName, String dataType) {

    super.setDataValue(value,dataName,dataType);

    jTextField_SimpleValue_Value.setText(oldValue.toString()); 
    
  }



} /* end of class */
