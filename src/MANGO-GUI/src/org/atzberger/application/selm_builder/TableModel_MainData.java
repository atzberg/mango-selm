package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.mango.table.TableData_Units_Double;
import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.TableData_EditorButton;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_MainData extends TableModel_Properties1_Default {

  application_SharedData applSharedData;

  private final static boolean DEBUG = false;

  /* name space tags */
  static final String base_nsTag                     = "MainData";

  static final String nsTag_BaseFilename             = "BaseFilename";
  static final String nsTag_BasePathname             = "BasePathname";
  static final String nsTag_Description              = "Description";
  static final String nsTag_PhysicalUnits            = "PhysicalUnits";
  static final String nsTag_SELM_Seed                = "SELM_Seed";
  static final String nsTag_SELM_Lagrangian_DOF_List = "SELM_Lagrangian_DOF_List";
  static final String nsTag_SELM_Eulerian_DOF_List   = "SELM_Eulerian_DOF_List";
  static final String nsTag_SELM_Eulerian_selected   = "SELM_Eulerian_selected";
  static final String nsTag_SELM_Integrator_List     = "SELM_Integrator_List";
  static final String nsTag_SELM_Integrator_selected = "SELM_Integrator_selected";
  static final String nsTag_SELM_Interaction_List    = "SELM_Interaction_List";
  static final String nsTag_SELM_CouplingOp_List     = "SELM_CouplingOp_List";

  static final String nsTagFull_BaseFilename             = base_nsTag + "." + nsTag_BaseFilename;
  static final String nsTagFull_BasePathname             = base_nsTag + "." + nsTag_BasePathname;
  static final String nsTagFull_Description              = base_nsTag + "." + nsTag_Description;
  static final String nsTagFull_PhysicalUnits            = base_nsTag + "." + nsTag_PhysicalUnits;
  static final String nsTagFull_SELM_Seed                = base_nsTag + "." + nsTag_SELM_Seed;
  static final String nsTagFull_SELM_Lagrangian_DOF_List = base_nsTag + "." + nsTag_SELM_Lagrangian_DOF_List;
  static final String nsTagFull_SELM_Eulerian_DOF_List   = base_nsTag + "." + nsTag_SELM_Eulerian_DOF_List;
  static final String nsTagFull_SELM_Eulerian_selected   = base_nsTag + "." + nsTag_SELM_Eulerian_selected;
  static final String nsTagFull_SELM_Integrator_List     = base_nsTag + "." + nsTag_SELM_Integrator_List;
  static final String nsTagFull_SELM_Integrator_selected = base_nsTag + "." + nsTag_SELM_Integrator_selected;
  static final String nsTagFull_SELM_Interaction_List    = base_nsTag + "." + nsTag_SELM_Interaction_List;
  static final String nsTagFull_SELM_CouplingOp_List     = base_nsTag + "." + nsTag_SELM_CouplingOp_List;


  /* table indices */
  public   String paramName_BaseFilename    = "Base Filename";
  public   int paramIndex_BaseFilename      = -1;

  public   String paramName_BasePathname    = "Base Path";
  public   int paramIndex_BasePathname      = -1;

  public   String paramName_Description     = "Description";
  public   int paramIndex_Description       = -1;

  public   String paramName_UnitsRef        = "Physical Units";
  public   int paramIndex_UnitsRef          = -1;

  public   String paramName_SELM_Seed       = "Random Generator Seed";
  public   int paramIndex_SELM_Seed         = -1;

  public   String paramName_Integrator      = "Integrator";
  public   int paramIndex_Integrator        = -1;

  public   String paramName_Eulerian_DOF    = "Eulerian DOF";
  public   int paramIndex_Eulerian_DOF      = -1;

  public   String paramName_Lagrangian_DOF  = "Lagrangian DOF";
  public   int paramIndex_Lagrangian_DOF    = -1;

  public   String paramName_InteractionList = "Interaction List";
  public   int paramIndex_InteractionList   = -1;

  public   String paramName_CouplingOpList  = "Coupling Operators List";
  public   int paramIndex_CouplingOpList    = -1;

    
  TableModel_MainData() {
    applSharedData = null;
    setupDefault();
  }

  TableModel_MainData(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;
    setupDefault();
  }

  void setupDefault() {
    
    int      i                = 0;
    String[] choices          = null;
    double[] testDoubleArray1 = new double[3];
    double[] dbArray          = null;
    TableData_Units_Double db  = null;

    Atz_UnitsRef unitsRef = new Atz_UnitsRef();

    setValueAt(paramName_BaseFilename, i,0, NOT_EDITABLE);
    setValueAt("Model1", i,1);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_BaseFilename = i;
    i++;

    /* remove basepath from table */
    //setValueAt(paramName_BasePathname, i,0, NOT_EDITABLE);

    //TableData_Pathname dataPathName = new TableData_Pathname("./Model1/");
    //dataPathName.setPreferredEditMode(TableData_Pathname.EDIT_MODE_STRING);
    //setValueAt(dataPathName, i,1);
    
    //setValueAt(new TableData_EditorButton(), i,2);
    //paramIndex_BasePathname = i;
    //i++;


    setValueAt(paramName_Description, i,0, NOT_EDITABLE);
    setValueAt("Put description here.", i,1, EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_Description = i;
    i++;

    /* removed physical units from the table */
    /*
    setValueAt(paramName_UnitsRef, i,0, NOT_EDITABLE);
    setValueAt(new TableData_Units_Ref(), i,1, EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_UnitsRef = i;
    i++;
     */

    setValueAt(paramName_SELM_Seed, i,0, NOT_EDITABLE);
    setValueAt(-1, i,1);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_SELM_Seed = i;
    i++;

    setValueAt(paramName_Integrator, i,0, NOT_EDITABLE);    
    setValueAt(new TableData_IntegratorList(0), i,1, EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_Integrator = i;
    i++;
    
    setValueAt(paramName_Eulerian_DOF, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EulerianList(0), i,1, EDITABLE); /* multiple choice */
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_Eulerian_DOF = i;
    i++;

    setValueAt(paramName_Lagrangian_DOF, i,0, NOT_EDITABLE);
    setValueAt(new TableData_LagrangianList(), i,1, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_Lagrangian_DOF = i;
    i++;
    
    setValueAt(paramName_InteractionList, i,0, NOT_EDITABLE);
    setValueAt(new TableData_InteractionList(), i,1, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_InteractionList = i;
    i++;

    setValueAt(paramName_CouplingOpList, i,0, NOT_EDITABLE);
    setValueAt(new TableData_CouplingOperatorList(), i,1, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_CouplingOpList = i;
    i++;

  }

  
  public void setApplSharedData(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;
        
    /* update the table entries to reflect new application data now available */
    
  }


  /* build the table data */
  public void rebuildTable() {
    /* nothing to do at this time */
  }



  /* make sure value is listed in the global namespace */
  @Override
  public void setValueAt(Object value, int row, int col, boolean flagEditable) {

    super.setValueAt(value, row, col, flagEditable);

    /* == Synchronize the data structure with DataNamespace */
    syncDataWithApplNamespace(row, col, value);
    
  }



  @Override
  public Object getValueAt(int row, int col) {
    
    Object value = super.getValueAt(row, col);

    /* == Synchronize the data structure with DataNamespace */
    //syncDataWithApplNamespace(row, col, value);

    return value;
    
  }

  public void syncDataWithApplNamespaceAll() {

    for (int i = 0; i < this.getRowCount(); i++) {
      syncDataWithApplNamespace(i, 1, getValueAt(i,1));
    }

  }

  public void syncDataWithApplNamespace(int row, int col, Object value) {
    
//    /* == Synchronize the data structure with DataNamespace */
//    /* WARNING: This should be cleaned up by having MainData table
//     * actually use directly the data available in the global
//     * name space within the appropriate scope.  For now we
//     * implemented to allow progress until this proper work is done.
//     */
//
//    if ((applSharedData != null) && (applSharedData.ApplNamespace != null)) {
//
//      String key_TABLE_DATA_Str = "-TABLE_DATA";
//
//      if ((row == paramIndex_BaseFilename) && (col == 1)) {
//
//        String baseFilename = "";
//
//        /* key name */
//        String keyNameStr = nsTagFull_BaseFilename;
//
//        /* only update in known cases */
//        if (TableData_Filename.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set string value */
//          TableData_Filename tableData = (TableData_Filename) value;
//          baseFilename = tableData.getFilename();
//          applSharedData.ApplNamespace.setData(keyNameStr, baseFilename, (Object) this);
//        }
//
//        if (String.class.isInstance(value)) {
//          baseFilename = (String) value;
//          applSharedData.ApplNamespace.setData(keyNameStr, baseFilename, (Object) this);
//        }
//
//      } /* end row == */
//
//
//      if ((row == paramIndex_BasePathname) && (col == 1)) {
//
//        String basePathname = "";
//
//        /* key name */
//        String keyNameStr = nsTagFull_BasePathname;
//
//        /* only update in known cases */
//        if (TableData_Pathname.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set string value */
//          TableData_Pathname tableData = (TableData_Pathname) value;
//          basePathname = tableData.pathname;
//          applSharedData.ApplNamespace.setData(keyNameStr, basePathname, (Object) this);
//        }
//
//        if (String.class.isInstance(value)) {
//          basePathname = (String) value;
//          applSharedData.ApplNamespace.setData(keyNameStr, basePathname, (Object) this);
//        }
//
//      } /* end row == */
//
//
//      if ((row == paramIndex_Description) && (col == 1)) {
//
//        String description = "";
//
//        /* key name */
//        String keyNameStr = nsTagFull_Description;
//
//        if (String.class.isInstance(value)) {
//          description = (String) value;
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + "String", description, (Object) this);
//        }
//
//      } /* end row == */
//
//      if ((row == this.paramIndex_UnitsRef) && (col == 1)) {
//
//
//        Atz_UnitsRef unitsRef = null;
//
//        /* key name */
//        String keyNameStr = nsTagFull_PhysicalUnits;
//
//        /* only update in known cases */
//        if (TableData_Units_Ref.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set value */
//          TableData_Units_Ref tableData = (TableData_Units_Ref) value;
//          unitsRef = tableData.atz_UnitsRef;
//          applSharedData.ApplNamespace.setData(keyNameStr, unitsRef, (Object) this);
//        }
//
//      } /* end row == */
//
//
//      if ((row == paramIndex_SELM_Seed) && (col == 1)) {
//
//        int SELM_Seed = -1;
//
//        /* key name */
//        String keyNameStr = nsTagFull_SELM_Seed;
//
//        if (Integer.class.isInstance(value)) {
//          SELM_Seed = (Integer) value;
//          applSharedData.ApplNamespace.setData(keyNameStr, SELM_Seed, (Object) this);
//        }
//
//      } /* end row == */
//
//
//      if ((row == this.paramIndex_Integrator) && (col == 1)) {
//
//
//        SELM_Integrator[] integratorList     = null;
//        int               integratorSelected = -1;
//
//        /* only update in known cases */
//        if (TableData_IntegratorList.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(nsTagFull_SELM_Integrator_List + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set value */
//          TableData_IntegratorList tableData = (TableData_IntegratorList) value;
//          integratorSelected = tableData.itemSelectedIndex;
//          applSharedData.ApplNamespace.setData(nsTagFull_SELM_Integrator_List, integratorList, (Object) this);
//          applSharedData.ApplNamespace.setData(nsTagFull_SELM_Integrator_selected, integratorSelected, (Object) this);
//        }
//
//      } /* end row == */
//
//      if ((row == this.paramIndex_Eulerian_DOF) && (col == 1)) {
//
//        SELM_Eulerian[] eulerianList = null;
//
//        /* key name */
//        String keyNameStr = nsTagFull_SELM_Eulerian_DOF_List;
//
//        /* only update in known cases */
//        if (TableData_EulerianList.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set value */
//          TableData_EulerianList tableData = (TableData_EulerianList) value;
//          eulerianList = tableData.eulerianList;
//          applSharedData.ApplNamespace.setData(keyNameStr, eulerianList, (Object) this);
//        }
//
//      } /* end row == */
//
//      if ((row == this.paramIndex_Lagrangian_DOF) && (col == 1)) {
//
//        SELM_Lagrangian[] lagrangianList = null;
//
//        /* key name */
//        String keyNameStr = nsTagFull_SELM_Lagrangian_DOF_List;
//
//        /* only update in known cases */
//        if (TableData_LagrangianList.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set value */
//          TableData_LagrangianList tableData = (TableData_LagrangianList) value;
//          lagrangianList = tableData.lagrangianList;
//          applSharedData.ApplNamespace.setData(keyNameStr, lagrangianList, (Object) this);
//        }
//
//      } /* end row == */
//
//      if ((row == this.paramIndex_CouplingOpList) && (col == 1)) {
//
//        SELM_CouplingOperator[] couplingOpList = null;
//
//        /* key name */
//        String keyNameStr = nsTagFull_SELM_CouplingOp_List;
//
//        /* only update in known cases */
//        if (TableData_CouplingOperatorList.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set value */
//          TableData_CouplingOperatorList tableData = (TableData_CouplingOperatorList) value;
//          couplingOpList = tableData.couplingOpList;
//          applSharedData.ApplNamespace.setData(keyNameStr, couplingOpList, (Object) this);
//        }
//
//      } /* end row == */
//
//
//      if ((row == this.paramIndex_InteractionList) && (col == 1)) {
//
//        SELM_Interaction[] interactionList = null;
//
//        /* key name */
//        String keyNameStr = nsTagFull_SELM_Interaction_List;
//
//        /* only update in known cases */
//        if (TableData_InteractionList.class.isInstance(value)) {
//
//          /* set table data value */
//          applSharedData.ApplNamespace.setData(keyNameStr + "." + key_TABLE_DATA_Str, value, (Object) this);
//
//          /* set value */
//          TableData_InteractionList tableData = (TableData_InteractionList) value;
//          interactionList = tableData.interactionList;
//          applSharedData.ApplNamespace.setData(keyNameStr, interactionList, (Object) this);
//        }
//
//      } /* end row == */
//
//
//    } /* == end synchronization with global DataNamespace
//       * WARNING: This all should be cleaned up so not necessary in future
//       * implementations.
//       */
//
  }

   
}
