package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.JPanel_Model_View_RenderPanel;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * Panel for editing the this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_Editor_Integrator extends javax.swing.JPanel {

    application_SharedData applSharedData = null;

    private JPanel_Model_View_RenderPanel jPanel_Render_ControlPts;
    
    private MouseListener jToggleButton_AddingPairs_MouseListener = null;
    private MouseListener jToggleButton_RemovingPairs_MouseListener = null;

    private boolean jComboBox_Integrator_flagRebuildingList = false;

    private TableModel[] jTable_Integrator_ModelList; /* used for switchable table data */

    private SELM_IntegratorRenderView[] integratorRenderViewList;

    JPanel_Integrator jPanel_Integrator_NULL = null;

    /** Creates new form JPanel_Editor_Test1 */
    public JPanel_Editor_Integrator() {
      initComponents();

      /* create empty panel ?*/
      jPanel_Integrator_NULL = new JPanel_Integrator_NULL();

      /* set application data later */
      
    }

    /** Creates new form JPanel_Editor_Test1 */
    public JPanel_Editor_Integrator(application_SharedData applSharedData_in) {      
      initComponents();
      init(applSharedData_in);
    }

    public void init(application_SharedData applSharedData_in) {

      applSharedData = applSharedData_in;

      updateLists();

      /* add listener to the jTable_mainData for any changes there */
      applSharedData.jTable_MainData.getModel().addTableModelListener(new TableModelListener() {
        public void tableChanged(TableModelEvent e)  {
          jTable_MainData_tableChanged(e);
        }
      });
      
    }

    public void setApplSharedData(application_SharedData applSharedData_in) {      
      init(applSharedData_in);
    }
    
    void jTable_MainData_tableChanged(TableModelEvent e)  {
      updateLists(); /* update the comboBox and data on change */
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox_Integrator = new javax.swing.JComboBox();
        jLabel_Integrator = new javax.swing.JLabel();
        jPanel_IntegratorData = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(400, 200));
        setName("Integrator Editor"); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 200));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        jComboBox_Integrator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_Integrator.setName("jComboBox_Integrator"); // NOI18N
        jComboBox_Integrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_IntegratorActionPerformed(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getResourceMap(JPanel_Editor_Integrator.class);
        jLabel_Integrator.setText(resourceMap.getString("jLabel_Integrator.text")); // NOI18N
        jLabel_Integrator.setName("jLabel_Integrator"); // NOI18N

        jPanel_IntegratorData.setName("jPanel_IntegratorData"); // NOI18N
        jPanel_IntegratorData.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jPanel_IntegratorDataPropertyChange(evt);
            }
        });
        jPanel_IntegratorData.setLayout(new javax.swing.BoxLayout(jPanel_IntegratorData, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_Integrator)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_Integrator, 0, 228, Short.MAX_VALUE)
                .addGap(121, 121, 121))
            .addComponent(jPanel_IntegratorData, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Integrator)
                    .addComponent(jComboBox_Integrator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_IntegratorData, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

  public void updateLists() {

    String oldSelectedName = (String) jComboBox_Integrator.getSelectedItem();
    
    /* setup the model for the integrator data */
    //jTable_Integrator_build_all_table_Models();

    //if ((jTable_Integrator_ModelList != null) && (jTable_Integrator_ModelList.length > 0)) {
    //  int entryI = jComboBox_Integrator_DOF.getSelectedIndex();
     // if (entryI >= 0) {
     //   jTable_Integrator_CONTROL_PTS.setModel(jTable_Integrator_ModelList[entryI]);
     // }
    //}

    /* update the combo box to reflect all integrators */
    jComboBox_Integrator_rebuild_list();

    /* try to select the old entry before changes were made */
    jComboBox_Integrator_tryToSetSelectionByName(oldSelectedName);

  }

    private void jComboBox_IntegratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_IntegratorActionPerformed

      int entryI  = jComboBox_Integrator.getSelectedIndex();

      /* do not respond if we are only rebuilding the list */
      if (jComboBox_Integrator_flagRebuildingList == false) {

        /* == Change the panel and data to edit selected Integrator DOF */

        /* -- get the Integrator DOF data */
        SELM_Integrator[] integratorList = applSharedData.jTable_MainData.getIntegratorList();
        SELM_Integrator integratorDOF    = integratorList[entryI];

        setIntegratorData(integratorList[entryI]);

      }
      
    }//GEN-LAST:event_jComboBox_IntegratorActionPerformed

  public void setIntegratorData(SELM_Integrator integratorDOF) {

    /* -- determine the appropriate panel type to use */
    int N;
    jPanel_Integrator = null;    
    N = applSharedData.jPanel_Integrator_list.length;
    jPanel_Integrator_index = -1;
    for (int k = 0; k < N; k++) {

      if (applSharedData.jPanel_Integrator_list[k].getName().equals(integratorDOF.IntegratorTypeStr)) {
        jPanel_Integrator = (JPanel_Integrator) applSharedData.jPanel_Integrator_list[k];
        jPanel_Integrator_index = k;
      }
      
    }

    if (jPanel_Integrator == null) { /* if not found then set to NULL display */
      jPanel_Integrator = jPanel_Integrator_NULL;
    }

    /* -- setup the panel data based on SELM_Integrator data structure */
    jPanel_Integrator.setData(integratorDOF);

    /* -- set the panel to display in correct location */
    jPanel_IntegratorData.removeAll();
    jPanel_IntegratorData.add(jPanel_Integrator);
    jPanel_IntegratorData.validate();
    jPanel_IntegratorData.repaint();

  }

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
      
    }//GEN-LAST:event_formPropertyChange

    private void jPanel_IntegratorDataPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jPanel_IntegratorDataPropertyChange
      //System.out.println("JPanel_IntegratorData Property Change triggered: " + evt.getPropertyName());

      if (jPanel_Integrator != null) {
        SELM_Integrator integrator = jPanel_Integrator.getData();

        SELM_Integrator[] integratorList = applSharedData.jTable_MainData.getIntegratorList();

        //integratorList[jPanel_Integrator_index] = integrator;

        applSharedData.jTable_MainData.setIntegratorList(integratorList);
      }
      
    }//GEN-LAST:event_jPanel_IntegratorDataPropertyChange

    
    private void jComboBox_Integrator_rebuild_list() {

    SELM_Integrator[] integratorList;

    /* == Get the integrator list from the ControlPts_BASIC1 table */
    integratorList = ((JTable_MainData)applSharedData.jTable_MainData).getIntegratorList();

    if (integratorList != null) {

      /* == Rebuild the list of integrators in the combo box */
      jComboBox_Integrator_flagRebuildingList = true;  /* signal reconstructing */
      String oldSelectedName = (String)jComboBox_Integrator.getSelectedItem(); /* save for later */
      jComboBox_Integrator.removeAllItems();
      int N = integratorList.length;
      for (int k = 0; k < N; k++) {
        jComboBox_Integrator.addItem(integratorList[k].IntegratorName);
      }

      if (jComboBox_Integrator.getItemCount() > 0) {
        jComboBox_Integrator_tryToSetSelectionByName(oldSelectedName);
      } else if (jComboBox_Integrator.getItemCount() == 0) { /* no entries set to NULL panel */
        setIntegratorPanelToNULL();
      }
      jComboBox_Integrator_flagRebuildingList = false;

    } else {/* end check integratorList != null */
      setIntegratorPanelToNULL();
    }

  }

  public void setIntegratorPanelToNULL() {
    this.setIntegratorData(new SELM_Integrator_NULL());
  }

  void jComboBox_Integrator_tryToSetSelectionByName(String nameTrySelect) {

    if (jComboBox_Integrator.getItemCount() > 0) {
      int selectedIndex = 0; /* if can't find set to zero index */
      int N = jComboBox_Integrator.getItemCount();
      for (int k = 0; k < N; k++) {
        String name = (String) jComboBox_Integrator.getItemAt(k);
        if (name.equals(nameTrySelect)) {
          selectedIndex = k;
        }
      }
      jComboBox_Integrator.setSelectedIndex(selectedIndex);
    } 

  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox_Integrator;
    private javax.swing.JLabel jLabel_Integrator;
    private javax.swing.JPanel jPanel_IntegratorData;
    // End of variables declaration//GEN-END:variables

  private JPanel_Integrator jPanel_Integrator = null;
  private int jPanel_Integrator_index = -1;

}