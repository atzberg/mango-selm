package org.atzberger.mango.table;

import org.atzberger.mango.table.TableModel_Array_Simple;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * Dialog editor for arrays of simple data types found within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JDialog_Edit_Array_Simple extends JDialog_Edit implements ActionListener {

  protected static final String ACTION_BUTTON_OK = "ACTION_BUTTON_OK";
  protected static final String ACTION_BUTTON_CANCEL = "ACTION_BUTTON_CANCEL";
  JButton jButton_trigger = null;  /* button triggering opening of the dialog */

  Boolean flagChangeValue = false; /* indicates if value should be changed */

  Label label_parameter_name = new java.awt.Label();
  JTextField jTextField_SimpleArray_Value = new javax.swing.JTextField();
  Label label_Request_SimpleArray_Value = new java.awt.Label();
  JButton jButton_Cancel = new javax.swing.JButton();
  JButton jButton_OK = new javax.swing.JButton();
  
  JTable  jTable_SimpleArray = new javax.swing.JTable();

  JScrollPane jScrollPane1 = new javax.swing.JScrollPane();

  public JDialog_Edit_Array_Simple() {
    initComponents();

    setModal(true);  /* must be Modal to handle event processing correctly */
  }

  public JDialog_Edit_Array_Simple(JButton button, Object oldValue_in, String ParamName_in, String ParamType_in) {

    super(oldValue_in, ParamName_in, ParamType_in);

    jButton_trigger = button;

    initComponents();

  }

  void initComponents() {

    this.setTitle("Edit Simple Array"); // NOI18N
    this.setName("jDialog_Edit_String"); // NOI18N

    //label_parameter_name.setFont(resourceMap.getFont("label_parameter_name.font")); // NOI18N
    label_parameter_name.setName("label_parameter_name"); // NOI18N
    label_parameter_name.setText("Param Name Here..."); // NOI18N

    jButton_Cancel.setText("Cancel"); // NOI18N
    jButton_Cancel.setName("jButton_Cancel"); // NOI18N
    jButton_Cancel.addActionListener(this);
    jButton_Cancel.setActionCommand(ACTION_BUTTON_CANCEL);

    jButton_OK.setText("OK"); // NOI18N
    jButton_OK.setName("jButton_OK"); // NOI18N
    jButton_OK.addActionListener(this);
    jButton_OK.setActionCommand(ACTION_BUTTON_OK);
    
    jScrollPane1.setName("jScrollPane1"); // NOI18N

    jTable_SimpleArray.setModel(new TableModel_Array_Simple());
    
    jTable_SimpleArray.setName("jTable_dataArray"); // NOI18N
    jTable_SimpleArray.getTableHeader().setReorderingAllowed(false);
  
    jScrollPane1.setViewportView(jTable_SimpleArray);

    javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this.getContentPane());
    this.getContentPane().setLayout(thisLayout);
    thisLayout.setHorizontalGroup(
      thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(thisLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(thisLayout.createSequentialGroup()
            .addComponent(jButton_OK)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton_Cancel))
          .addComponent(label_parameter_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(204, Short.MAX_VALUE))
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
        .addContainerGap(13, Short.MAX_VALUE)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    thisLayout.setVerticalGroup(
      thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(thisLayout.createSequentialGroup()
        .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButton_OK)
          .addComponent(jButton_Cancel))
        .addGap(2, 2, 2)
        .addComponent(label_parameter_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    Dimension size = new Dimension(380, 500);

    this.setPreferredSize(size);
    this.setMinimumSize(size);
    this.setMaximumSize(size);

    /* setup so object destroyed on close */
    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

  }

  public void actionPerformed(ActionEvent e) {

    if (ACTION_BUTTON_OK.equals(e.getActionCommand())) {

      TableCellEditor editor = jTable_SimpleArray.getCellEditor();
      if (editor != null) {
        editor.stopCellEditing();
      }

      this.setVisible(false);
      flagChangeValue = true;
      setNewValueFromTable();

      //newValue = (Object) jTextField_SimpleArray_Value.getText();

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

    //newValue = (Object) jTextField_SimpleArray_Value.getText();

    if (flagChangeValue) {
      return newValue;
    } else {
      return oldValue;
    }

  }

  public void setDataValue(Object dataValue, String dataName, String dataType) {

    super.setDataValue(dataValue,dataName,dataType);

    jTextField_SimpleArray_Value.setText(oldValue.toString());
    label_parameter_name.setText(dataName);
       
    String DoubleArrayStr = double[].class.getName();
    if (DoubleArrayStr.equals(dataType)) {

      double[] doubleArray = (double[]) dataValue;

      /* setup the table values */      
      int N = doubleArray.length;
      for (int k = 0; k < N; k++) {     
        
        int valInt;
        valInt = k + 1;
        jTable_SimpleArray.setValueAt(valInt, k, 0);
        
        double valDouble;
        valDouble = doubleArray[k];
        jTable_SimpleArray.setValueAt(valDouble, k, 1);
      }
      
    } /* end double[] */
    
    String IntegerArrayStr = int[].class.getName();
    if (IntegerArrayStr.equals(dataType)) {

      int[] intArray = (int[]) dataValue;

      /* setup the table values */

      int N = intArray.length;
      for (int k = 0; k < N; k++) {

        int valInt;
        valInt = k + 1;
        jTable_SimpleArray.setValueAt(valInt, k, 0);
        
        valInt = intArray[k];
        jTable_SimpleArray.setValueAt(valInt, k, 1);
      }

    } /* end int[] */

    String StringArrayStr = String[].class.getName();
    if (StringArrayStr.equals(dataType)) {

      String[] StringArray = (String[]) dataValue;

      /* setup the table values */
      int N = StringArray.length;
      for (int k = 0; k < N; k++) {

        int valInt;
        valInt = k + 1;
        jTable_SimpleArray.setValueAt(valInt, k, 0);

        String valString;
        valString = StringArray[k];
        jTable_SimpleArray.setValueAt(valString, k, 1);
      }

    } /* end String[] */

  }



  void setNewValueFromTable() {
    
    String DoubleArrayStr = double[].class.getName();        
    if (DoubleArrayStr.equals(oldValue.getClass().getName())) {
      
      double[] newArray;
      double[] oldArray = (double[]) oldValue;

      newArray = new double[oldArray.length];

      /* set newValue from the table values */
      int N = newArray.length;
      for (int k = 0; k < N; k++) {        
        newArray[k] = (Double)jTable_SimpleArray.getValueAt(k, 1);
      }

      newValue = (Object)newArray;
     
    } /* end double[] */

    String IntArrayStr    = int[].class.getName();
    if (IntArrayStr.equals(oldValue.getClass().getName())) {

      int[] newArray;
      int[] oldArray = (int[]) oldValue;

      newArray = new int[oldArray.length];

      /* set newValue from the table values */
      int N = newArray.length;
      for (int k = 0; k < N; k++) {
        newArray[k] = (Integer)jTable_SimpleArray.getValueAt(k, 1);
      }

      newValue = (Object)newArray;

    } /* end int[] */

    String StringArrayStr = String[].class.getName();
    if (StringArrayStr.equals(oldValue.getClass().getName())) {

      String[] newArray;
      String[] oldArray = (String[]) oldValue;

      newArray = new String[oldArray.length];

      /* set newValue from the table values */
      int N = newArray.length;
      for (int k = 0; k < N; k++) {
        newArray[k] = (String)jTable_SimpleArray.getValueAt(k, 1);
      }

      newValue = (Object)newArray;

    } /* end String[] */
    
  }

  @Override
  public void setVisible(boolean flag) {
    super.setVisible(flag);

    if (flag == false) {
      //jTable_SimpleArray.changeSelection(0, 1, false, false);
      jTable_SimpleArray.clearSelection();
    }
    
  }


} /* end of class */
