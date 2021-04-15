package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.JDialog_Edit;
import org.atzberger.mango.table.JTable_Properties1_Default;
import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

/**
 *
 * Editor for the list of named data entries.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JDialog_Edit_LagrangianList extends JDialog_Edit implements TableModelListener {

  //protected static final String ACTION_BUTTON_OK = "ACTION_BUTTON_OK";
  //protected static final String ACTION_BUTTON_CANCEL = "ACTION_BUTTON_CANCEL";
  JButton jButton_trigger = null;  /* button triggering opening of the dialog */

  Boolean flagChangeValue = false; /* indicates if value should be changed */
  
  TableData_LagrangianList tableData_LagrangianList = null;

  /** Creates new form JDialog_Edit_LagrangianList */
  public JDialog_Edit_LagrangianList(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    //modifyComponents();
  }

  public JDialog_Edit_LagrangianList() {    
    initComponents();
    //modifyComponents();
    setModal(true);  /* must be Modal to handle event processing correctly */
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    jTable_LagrangianList = new JTable_Properties1_Default();
    jPanel1 = new javax.swing.JPanel();
    jButton_OK = new javax.swing.JButton();
    jButton_Remove = new javax.swing.JButton();
    jComboBox_Add_Lagrangian = new javax.swing.JComboBox();
    jButton_ImportData = new javax.swing.JButton();
    jButton_ExportData = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setName("Form"); // NOI18N

    jScrollPane1.setName("jScrollPane1"); // NOI18N

    jTable_LagrangianList.setModel(new TableModel_LagrangianList());
    jTable_LagrangianList.getModel().addTableModelListener(this);
    jTable_LagrangianList.setName("jTable_LagrangianList"); // NOI18N
    jScrollPane1.setViewportView(jTable_LagrangianList);

    jPanel1.setName("jPanel1"); // NOI18N

    jButton_OK.setMaximumSize(new java.awt.Dimension(79, 100));
    jButton_OK.setMinimumSize(new java.awt.Dimension(79, 100));
    jButton_OK.setName("jButton_OK"); // NOI18N
    jButton_OK.setPreferredSize(new java.awt.Dimension(79, 100));
    jButton_OK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton_OKActionPerformed(evt);
      }
    });

    jButton_Remove.setMaximumSize(new java.awt.Dimension(79, 100));
    jButton_Remove.setMinimumSize(new java.awt.Dimension(79, 100));
    jButton_Remove.setName("jButton_Remove"); // NOI18N
    jButton_Remove.setPreferredSize(new java.awt.Dimension(79, 100));
    jButton_Remove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton_RemoveActionPerformed(evt);
      }
    });

    jComboBox_Add_Lagrangian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Add Lagrangian DOF", "CONTROL_PTS", "SPECTRAL_FILAMENT1" }));
    jComboBox_Add_Lagrangian.setName("jComboBox_Add_Lagrangian"); // NOI18N
    jComboBox_Add_Lagrangian.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jComboBox_Add_LagrangianActionPerformed(evt);
      }
    });

    jButton_ImportData.setName("jButton_ImportData"); // NOI18N
    jButton_ImportData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton_ImportDataActionPerformed(evt);
      }
    });

    jButton_ExportData.setName("jButton_ExportData"); // NOI18N
    jButton_ExportData.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton_ExportDataActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jButton_OK, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
          .addComponent(jButton_ImportData, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButton_ExportData, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jComboBox_Add_Lagrangian, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(jButton_Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jComboBox_Add_Lagrangian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jButton_Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jButton_ImportData)
          .addComponent(jButton_ExportData))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButton_OK, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(31, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(12, 12, 12)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
        .addGap(12, 12, 12))
      .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed

    /* register any partially completed edits as being final */
    TableCellEditor editor = jTable_LagrangianList.getCellEditor();
    if (editor != null) {
      editor.stopCellEditing();
    }

    /* close the window and fire related events */
    this.setVisible(false);
    flagChangeValue = true;
    newValue = tableData_LagrangianList;

    this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);
    
  }//GEN-LAST:event_jButton_OKActionPerformed

  private void jButton_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RemoveActionPerformed

    /* Determine the selected rows and remove entries from the lagrangianList */
    int selectedRows[] = jTable_LagrangianList.getSelectedRows();

    if (selectedRows.length > 0) {
      int k;
      int N = tableData_LagrangianList.lagrangianList.length;
      int mask[] = new int[N];
      int I;
      int newN;
      SELM_Lagrangian[] lagrangianList_new;

      for (k = 0; k < N; k++) {
        mask[k] = 1; /* indicates to keep this entry */
      }

      newN = N;
      for (k = 0; k < selectedRows.length; k++) {
        I = selectedRows[k];
        mask[I] = 0; /* indicates this is row to delete */
        newN--;
      }

      lagrangianList_new = new SELM_Lagrangian[newN];
      I = 0;
      for (k = 0; k < N; k++) {
        if (mask[k] == 1) {
          lagrangianList_new[I] = tableData_LagrangianList.lagrangianList[k];
          I++;
        }
      }

      tableData_LagrangianList.lagrangianList = lagrangianList_new;

      setDataValue(tableData_LagrangianList, "Name", "Type");

      /* set the selected row */
      if (selectedRows[0] < newN) {
        jTable_LagrangianList.setRowSelectionInterval(selectedRows[0], selectedRows[0]);
      } else if (newN > 0) {
        jTable_LagrangianList.setRowSelectionInterval(newN - 1, newN - 1);
      }

    }
        
  }//GEN-LAST:event_jButton_RemoveActionPerformed

  private void jComboBox_Add_LagrangianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_Add_LagrangianActionPerformed

    String selectedType = (String) jComboBox_Add_Lagrangian.getSelectedItem();

    SELM_Lagrangian   newLagrangian = null;
    SELM_Lagrangian[] lagrangianList_new;

    if (selectedType.equals("CONTROL_PTS")) {

      SELM_Lagrangian newLagrangian_CONTROL_PTS = new SELM_Lagrangian_CONTROL_PTS_BASIC1();

      newLagrangian_CONTROL_PTS.LagrangianName    = "Name Here";

      newLagrangian = newLagrangian_CONTROL_PTS;
    }

    if (newLagrangian != null) {
      /* add the lagrangian_DOF to the list */
      int N = tableData_LagrangianList.lagrangianList.length;
      lagrangianList_new = new SELM_Lagrangian[N + 1];
      System.arraycopy(tableData_LagrangianList.lagrangianList, 0,
              lagrangianList_new, 0, N);

      lagrangianList_new[N] = newLagrangian;

      tableData_LagrangianList.lagrangianList = lagrangianList_new;

      setDataValue(tableData_LagrangianList,"Name","Type");
    }

    /* reset the selection to zero */
    jComboBox_Add_Lagrangian.setSelectedIndex(0);
  }//GEN-LAST:event_jComboBox_Add_LagrangianActionPerformed

  private void jButton_ImportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ImportDataActionPerformed

    /* for the pressed data add Lagrangian by parsing the file */

    //SELM_Lagrangian_CONTROL_PTS_BASIC1 newLagrangian_CONTROL_PTS = new SELM_Lagrangian_CONTROL_PTS_BASIC1();
    

  }//GEN-LAST:event_jButton_ImportDataActionPerformed

  private void jButton_ExportDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ExportDataActionPerformed
    
    // TODO add your handling code here:
    
  }//GEN-LAST:event_jButton_ExportDataActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        JDialog_Edit_LagrangianList dialog = new JDialog_Edit_LagrangianList(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton_ExportData;
  private javax.swing.JButton jButton_ImportData;
  private javax.swing.JButton jButton_OK;
  private javax.swing.JButton jButton_Remove;
  private javax.swing.JComboBox jComboBox_Add_Lagrangian;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable jTable_LagrangianList;
  // End of variables declaration//GEN-END:variables


  public Object getDataValue() {

    //newValue = (Object) jTextField_SimpleArray_Value.getText();

    if (flagChangeValue) {
      return newValue;
    } else {
      return oldValue;
    }

  }

  public void setDataValue(Object dataValue, String dataName, String dataType) {

    SELM_Lagrangian lagrangian_DOF;
    String name;
    int k, N;

    TableModel_LagrangianList tableModel_LagrangianList;

    super.setDataValue(dataValue, dataName, dataType);

    tableModel_LagrangianList = (TableModel_LagrangianList) jTable_LagrangianList.getModel();

    tableData_LagrangianList = (TableData_LagrangianList) dataValue;

    /* setup the table values */
    N = tableData_LagrangianList.lagrangianList.length;
    for (k = 0; k < N; k++) {
      lagrangian_DOF = tableData_LagrangianList.lagrangianList[k];

      if (lagrangian_DOF != null) {
        tableModel_LagrangianList.setValueAt(lagrangian_DOF.LagrangianName, k, 0, tableModel_LagrangianList.EDITABLE);
        tableModel_LagrangianList.setValueAt(lagrangian_DOF.LagrangianTypeStr, k, 1, tableModel_LagrangianList.NOT_EDITABLE);
      } else {
        tableModel_LagrangianList.setValueAt("(null)", k, 0, tableModel_LagrangianList.NOT_EDITABLE);
        tableModel_LagrangianList.setValueAt("NULL", k, 1, tableModel_LagrangianList.NOT_EDITABLE);
      }
    }

    tableModel_LagrangianList.maxRowUsed = N - 1;

    tableModel_LagrangianList.fireTableDataChanged();
    
  }

  public void tableChanged(TableModelEvent e) {

    int row    = e.getFirstRow();
    int column = e.getColumn();

    SELM_Lagrangian lagrangian_DOF;

    TableModel model = (TableModel) e.getSource();

    Object data       = model.getValueAt(row, column); /* not column -1 since not selectable */
       
    if ( (data != null) && (e.getType() == e.UPDATE) && (column == 0) ) {            
      /* update all the lagrangian_DOF data */
      lagrangian_DOF                                    = tableData_LagrangianList.lagrangianList[row];
      if (lagrangian_DOF != null) {
        lagrangian_DOF.LagrangianName                    = (String) model.getValueAt(row, 0);
      }
      //tableData_LagrangianList.lagrangianList[row] = lagrangian_DOF;
    }

  }




  @Override
  public void setVisible(boolean flag) {
    super.setVisible(flag);

    if (flag == false) {
      //jTable_LagrangianList.changeSelection(0, 1, false, false);
      jTable_LagrangianList.clearSelection();
    }

  }
}
