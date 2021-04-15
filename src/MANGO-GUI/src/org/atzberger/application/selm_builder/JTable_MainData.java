package org.atzberger.application.selm_builder;


import org.atzberger.mango.table.TableEditor_Units_Ref;
import org.atzberger.mango.table.TableData_Units_Ref;
import org.atzberger.mango.table.TableRenderer_Units_Ref;
import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.TableEditor_EditorButton;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.JTable_Properties1_Default;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

/**
 *
 * Table represention of this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTable_MainData extends JTable_Properties1_Default {

  protected int privateVar = -1;

  String      xmlString     = "";
  Attributes  xmlAttributes = null;

  String      basePathname = ""; /* no longer stored in table */

  public TableData_Units_Ref tableData_Units_Ref_default = null;
    
  public JTable_MainData() {
    super();

    commonInit();

    setupEditorsAndRenderers();
  }

  public JTable_MainData(TableModel dm) {
    super(dm, null, null);

    commonInit();

    setupEditorsAndRenderers();
  }

  public JTable_MainData(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
    super(dm, cm, sm);

    commonInit();

    setupEditorsAndRenderers();
  }

  
  private void commonInit() {
    tableData_Units_Ref_default = new TableData_Units_Ref(); /* default to use, if does not appear explicitly in the table for editing*/

    this.setRowSelectionAllowed(false);
  }

  private void setupEditorsAndRenderers() {

    //super.setupDefaultEditorsAndRenderers();

    // == Set up any new/novel data types
    setDefaultRenderer(TableData_IntegratorList.class,
                       new TableRenderer_IntegratorList());
    setDefaultEditor(TableData_IntegratorList.class,
                     new TableEditor_IntegratorList());

    setDefaultRenderer(TableData_Units_Ref.class,
                       new TableRenderer_Units_Ref());
    setDefaultEditor(TableData_Units_Ref.class,
                     new TableEditor_Units_Ref());

    setDefaultRenderer(TableData_InteractionList.class,
                       new TableRenderer_InteractionList());
    setDefaultEditor(TableData_InteractionList.class,
                     new TableEditor_InteractionList());

    setDefaultRenderer(TableData_LagrangianList.class,
                       new TableRenderer_LagrangianList());
    setDefaultEditor(TableData_LagrangianList.class,
                     new TableEditor_LagrangianList());

    setDefaultRenderer(TableData_EulerianList.class,
                       new TableRenderer_EulerianList());
    setDefaultEditor(TableData_EulerianList.class,
                     new TableEditor_EulerianList());

    setDefaultRenderer(TableData_CouplingOperatorList.class,
                       new TableRenderer_CouplingOperatorList());
    setDefaultEditor(TableData_CouplingOperatorList.class,
                     new TableEditor_CouplingOperatorList());

    // == Set up Editor special button behavior

    /* get button already in use in default setup (and modify) */
    TableEditor_EditorButton tableSpecialButtonEditor
            = (TableEditor_EditorButton) getDefaultEditor(TableData_EditorButton.class);

    tableSpecialButtonEditor.addDataType(TableData_InteractionList.class.getName(),
                                         new JDialog_Edit_InteractionList());
    
    tableSpecialButtonEditor.addDataType(TableData_LagrangianList.class.getName(),
                                         new JDialog_Edit_LagrangianList());

    tableSpecialButtonEditor.addDataType(TableData_EulerianList.class.getName(),
                                         new JDialog_Edit_EulerianList());

    tableSpecialButtonEditor.addDataType(TableData_CouplingOperatorList.class.getName(),
                                         new JDialog_Edit_CouplingOperatorList());

    
  } /* setupDefaultEditorsAndRenderers */


  /* ==== Following assume the appropriate model for the table already set */

  public Atz_UnitsRef getUnitsRef() {

    Atz_UnitsRef val;

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    if (model.paramIndex_UnitsRef != -1) { /* indicates appears in the table */
      int col = 1;
      TableData_Units_Ref data = (TableData_Units_Ref) model.getValueAt(model.paramIndex_UnitsRef, col);
      val = data.atz_UnitsRef;
    } else { /* indicates does not appear within the table, so use defaults */
      val = tableData_Units_Ref_default.atz_UnitsRef;
    }

    return val;
  }

  public SELM_Lagrangian[] getLagrangianList() {
    SELM_Lagrangian[] lagrangianList;

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_LagrangianList data = (TableData_LagrangianList) model.getValueAt(model.paramIndex_Lagrangian_DOF, col);

    return data.lagrangianList;    
  }

  public SELM_Eulerian[] getEulerianList() {
    SELM_Eulerian[] eulerianList;

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_EulerianList data = (TableData_EulerianList) model.getValueAt(model.paramIndex_Eulerian_DOF, col);

    return data.eulerianList;
  }

  
  public String getEulerianNameSelected() {    
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_EulerianList data = (TableData_EulerianList) model.getValueAt(model.paramIndex_Eulerian_DOF, col);

    return data.eulerianList[data.itemSelectedIndex].EulerianName;
  }

  public int getEulerianIndexSelected() {
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_EulerianList data = (TableData_EulerianList) model.getValueAt(model.paramIndex_Eulerian_DOF, col);

    return data.itemSelectedIndex;
  }

  public SELM_Integrator[] getIntegratorList() {
    SELM_Integrator[] integratorList;

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_IntegratorList data = (TableData_IntegratorList) model.getValueAt(model.paramIndex_Integrator, col);

    return data.integratorList;
  }

  public String getIntegratorNameSelected() {
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_IntegratorList data = (TableData_IntegratorList) model.getValueAt(model.paramIndex_Integrator, col);

    return data.integratorList[data.itemSelectedIndex].IntegratorName;
  }

  public int getIntegratorIndexSelected() {
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_IntegratorList data = (TableData_IntegratorList) model.getValueAt(model.paramIndex_Integrator, col);

    return data.itemSelectedIndex;
  }


  public SELM_CouplingOperator[] getCouplingOpList() {
    SELM_CouplingOperator[] couplingOpList;

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_CouplingOperatorList data = (TableData_CouplingOperatorList) model.getValueAt(model.paramIndex_CouplingOpList, col);

    return data.couplingOpList;
  }

  public SELM_Interaction[] getInteractionList() {
    SELM_Interaction[] interactionList;

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_InteractionList data = (TableData_InteractionList) model.getValueAt(model.paramIndex_InteractionList, col);

    return data.interactionList;
  }

  public void setInteractionList(SELM_Interaction[] interactionList) {

    String name = "";
    
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    //SELM_Interaction.class.isInstance(interactionList[0])

    if (interactionList.length != 0) {
      name = interactionList[0].getInteractionName();
    }
    
    int col = 1;
    TableData_InteractionList data = (TableData_InteractionList) model.getValueAt(model.paramIndex_InteractionList, col);

    data.interactionList = interactionList;

    model.setValueAt(data, model.paramIndex_InteractionList, col);

  }

  public void setUnitsRef(Atz_UnitsRef atz_UnitsRef) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    if (model.paramIndex_UnitsRef != -1) { /* indicates physical units not in table */
      int col = 1;
      TableData_Units_Ref data = (TableData_Units_Ref) model.getValueAt(model.paramIndex_UnitsRef, col);

      data.atz_UnitsRef = atz_UnitsRef;

      model.setValueAt(data, model.paramIndex_UnitsRef, col);
    } else { /* indicates defaults being used, so set defaults */
      this.tableData_Units_Ref_default.atz_UnitsRef = atz_UnitsRef;
      model.fireTableDataChanged(); /* fire change data, as if put in table */
    }
    
  }


  public void setCouplingOpList(SELM_CouplingOperator[] couplingOpList) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_CouplingOperatorList data = (TableData_CouplingOperatorList) model.getValueAt(model.paramIndex_CouplingOpList, col);

    data.couplingOpList = couplingOpList;

    model.setValueAt(data, model.paramIndex_CouplingOpList, col);

  }

  public void setLagrangianDOF(SELM_Lagrangian[] lagrangianList) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_LagrangianList data = (TableData_LagrangianList) model.getValueAt(model.paramIndex_Lagrangian_DOF, col);

    data.lagrangianList = lagrangianList;

    model.setValueAt(data, model.paramIndex_Lagrangian_DOF, col);

  }

  public void setEulerianDOF(SELM_Eulerian[] eulerianList) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_EulerianList data = (TableData_EulerianList) model.getValueAt(model.paramIndex_Eulerian_DOF, col);

    data.eulerianList = eulerianList;

    model.setValueAt(data, model.paramIndex_Eulerian_DOF, col);

  }

  public void setIntegratorList(SELM_Integrator[] integratorList) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_IntegratorList data = (TableData_IntegratorList) model.getValueAt(model.paramIndex_Integrator, col);

    data.integratorList = integratorList;

    model.setValueAt(data, model.paramIndex_Integrator, col);

  }

    public void setIntegrator_selectedByName(String nameToSelect) {

    int index = -1;

    /* attempt to find the integrator by name and set to selected */
    SELM_Integrator[] integratorList = getIntegratorList();

    for (int k = 0; k < integratorList.length; k++) {

      if (integratorList[k].getName().equals(nameToSelect)) {
        index = k;
      }

    } /* end k loop */

    if (index != -1) {
      setIntegrator_selected(index);
    }

  }

  public void setIntegrator_selected(int selectIndex) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_IntegratorList data = (TableData_IntegratorList) model.getValueAt(model.paramIndex_Integrator, col);

    if ((selectIndex >= 0) && (selectIndex < data.integratorList.length)) {
      data.itemSelectedIndex        = selectIndex;
    } else { /* indicates invalid choice so no change made */
      //data.itemSelectedIndex        = data.itemSelectedIndex;
      /* do nothing */
    }

    model.setValueAt(data, model.paramIndex_Integrator, col);

  }

  public void setEulerian_selectedByName(String nameToSelect) {

    int index = -1;

    /* attempt to find the eulerian by name and set to selected */
    SELM_Eulerian[] eulerianList = getEulerianList();

    for (int k = 0; k < eulerianList.length; k++) {

      if (eulerianList[k].getName().equals(nameToSelect)) {
        index = k;
      }

    } /* end k loop */

    if (index != -1) {
      setEulerian_selected(index);
    }

  }

  public void setEulerian_selected(int selectIndex) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;    
    TableData_EulerianList data = (TableData_EulerianList) model.getValueAt(model.paramIndex_Eulerian_DOF, col);

    if ((selectIndex >= 0) && (selectIndex < data.eulerianList.length)) {
      data.itemSelectedIndex        = selectIndex;
    } else { /* indicates invalid choice so no change made */
      //data.itemSelectedIndex        = data.itemSelectedIndex;
      /* do nothing */
    }

    model.setValueAt(data, model.paramIndex_Eulerian_DOF, col);

  }

  public int getIntegrator_selected() {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_IntegratorList data = (TableData_IntegratorList) model.getValueAt(model.paramIndex_Integrator, col);

    return data.itemSelectedIndex;
        
  }

  public int getEulerian_selected() {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_EulerianList data = (TableData_EulerianList) model.getValueAt(model.paramIndex_Eulerian_DOF, col);

    return data.itemSelectedIndex;

  }

  public void setApplSharedData(application_SharedData applSharedData_in) {
    TableModel_MainData model = (TableModel_MainData) this.getModel();
    model.setApplSharedData(applSharedData_in);

    //applSharedData_in.ApplNamespace.addDataChangeListenerAtLabel(TableModel_MainData.base_nsTag, new Atz_Struct_DataChangeListener_MainData(this));
  }

  public void rebuildTable() {
    TableModel_MainData model = (TableModel_MainData) this.getModel();
    model.rebuildTable();
  }


  public String getBasePathname() {

    /*
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_Pathname data = (TableData_Pathname) model.getValueAt(model.paramIndex_BasePathname, col);

    return data.pathname;
     */

    return basePathname;
    
  }


  public void setBaseFilename(String baseFilename) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    String data = (String) model.getValueAt(model.paramIndex_BaseFilename, col);

    data = baseFilename;

    model.setValueAt(data, model.paramIndex_BaseFilename, col);

  }

  public String getBaseFilename() {
    
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    String data = (String) model.getValueAt(model.paramIndex_BaseFilename, col);

    return data;
    
  }

  public String getDescription() {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    String data = (String) model.getValueAt(model.paramIndex_Description, col);

    return data;

  }

  public int getRandomSeed() {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    int data = (Integer) model.getValueAt(model.paramIndex_SELM_Seed, col);

    return data;

  }

  public void setBasePathname(String basePathname_in) {

    /*
    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    TableData_Pathname data = (TableData_Pathname) model.getValueAt(model.paramIndex_BasePathname, col);

    data.pathname = basePathname;

    model.setValueAt(data, model.paramIndex_BasePathname, col);
     */

    basePathname = basePathname_in;

  }


  public void setDescription(String description) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;

    model.setValueAt(description, model.paramIndex_Description, col);

  }

  public void setRandomSeed(Integer value) {

    TableModel_MainData model = (TableModel_MainData) this.getModel();

    int col = 1;
    
    model.setValueAt(value, model.paramIndex_SELM_Seed, col);

  }

  
  public void setValueByFieldName(String name, Object value) {

    if (name.equals(TableModel_MainData.nsTag_BaseFilename)) {
      this.setBaseFilename((String)value);
    }

    if (name.equals(TableModel_MainData.nsTag_BasePathname)) {
      this.setBasePathname((String)value);
    }

    if (name.equals(TableModel_MainData.nsTag_Description)) {
      this.setDescription((String)value);
    }

    if (name.equals(TableModel_MainData.nsTag_PhysicalUnits)) {
      this.setUnitsRef((Atz_UnitsRef)value);
    }
    
    if (name.equals(TableModel_MainData.nsTag_SELM_Lagrangian_DOF_List)) {
      this.setLagrangianDOF((SELM_Lagrangian[])value);
    }

    if (name.equals(TableModel_MainData.nsTag_SELM_Eulerian_DOF_List)) {
      this.setEulerianDOF((SELM_Eulerian[]) value);
    }

    if (name.equals(TableModel_MainData.nsTag_SELM_CouplingOp_List)) {
      this.setCouplingOpList((SELM_CouplingOperator[])value);
    }

    if (name.equals(TableModel_MainData.nsTag_SELM_Integrator_List)) {
      this.setIntegratorList((SELM_Integrator[])value);
    }

    if (name.equals(TableModel_MainData.nsTag_SELM_Integrator_selected)) {
      this.setIntegrator_selected((Integer)value);
    }
          
  }

  public void syncDataWithApplNamespaceAll() {
    TableModel_MainData model = (TableModel_MainData) this.getModel();
    model.syncDataWithApplNamespaceAll();
  }

 
  /* ========================= XML codes ==================== */
  /* ======================================================== */

  
}