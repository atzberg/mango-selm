package org.atzberger.application.selm_builder;

import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_MultipleChoice1;
import org.atzberger.mango.table.TableData_Filename;
import org.atzberger.mango.table.TableData_Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 extends TableModel_CouplingOperator implements TableModelListener {

  private final static boolean DEBUG = false;

  Atz_UnitsRef  atz_unitsRef = null;
      
  public String paramName_weightTableFilename = "Weight Table Filename";
  public int    paramIndex_weightTableFilename = -1;

  public String paramName_lagrangianList = "Lagrangian List";
  public int    paramIndex_lagrangianList = -1;

  public String paramName_eulerianList = "Eulerian List";
  public int    paramIndex_eulerianList = -1;

  public String paramName_operatorTypeStr = "Operator Type";
  public int    paramIndex_operatorTypeStr = -1;

  public String paramName_flagVisible = "Visible";
  public int    paramIndex_flagVisible = -1;

  public String paramName_plotColor = "Plot Color";
  public int    paramIndex_plotColor = -1;

  SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_NULL       operatorData_NULL;
  SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_T_KERNEL_1 operatorData_T_KERNEL_1;
  SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_T_FAXEN_1  operatorData_T_FAXEN_1;
  SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_TR_FAXEN_1 operatorData_TR_FAXEN_1;
  
  protected int flagOperatorTypeDisplayMode = SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_NULL;

  public TableModel_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOp) {

    operatorData_NULL       = couplingOp.new operatorDataType_NULL(couplingOp);
    operatorData_T_KERNEL_1 = couplingOp.new operatorDataType_T_KERNEL_1(couplingOp);
    operatorData_T_FAXEN_1  = couplingOp.new operatorDataType_T_FAXEN_1(couplingOp);
    operatorData_TR_FAXEN_1 = couplingOp.new operatorDataType_TR_FAXEN_1(couplingOp);
    
    setup(couplingOp, null);

    setupFirstThirdColumns();

    setFromCouplingOpData(couplingOp);

    addTableModelListener(this);
  
  }

  public TableModel_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1() {

    SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOp = new SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1();

    operatorData_NULL       = couplingOp.new operatorDataType_NULL(couplingOp);
    operatorData_T_KERNEL_1 = couplingOp.new operatorDataType_T_KERNEL_1(couplingOp);
    operatorData_T_FAXEN_1  = couplingOp.new operatorDataType_T_FAXEN_1(couplingOp);
    operatorData_TR_FAXEN_1 = couplingOp.new operatorDataType_TR_FAXEN_1(couplingOp);
            
    setupFirstThirdColumns();

    setFromCouplingOpData(couplingOp);

    addTableModelListener(this);
  }

  public TableModel_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(Atz_UnitsRef atz_unitsRef_in) {
    
    SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOp = new SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1();

    operatorData_NULL       = couplingOp.new operatorDataType_NULL(couplingOp);
    operatorData_T_KERNEL_1 = couplingOp.new operatorDataType_T_KERNEL_1(couplingOp);
    operatorData_T_FAXEN_1  = couplingOp.new operatorDataType_T_FAXEN_1(couplingOp);
    operatorData_TR_FAXEN_1 = couplingOp.new operatorDataType_TR_FAXEN_1(couplingOp);

    setup(null, atz_unitsRef_in);

    addTableModelListener(this);
  }

  public final void setup(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOp_in, Atz_UnitsRef atz_unitsRef_in) {

    atz_unitsRef = atz_unitsRef_in;
    couplingOp   = couplingOp_in;
    
    setupFirstThirdColumns();

    if (couplingOp == null) {
      couplingOp = new SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1();
    }

    setFromCouplingOpData(couplingOp);

  }

  public void setupFirstThirdColumns() {

    int i = 0;
    boolean flagFireCellUpdate = false;

    setValueAt(paramName_CouplingOpName, i, 0, NOT_EDITABLE, flagFireCellUpdate);
    setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
    paramIndex_CouplingOpName = i;
    i++;

    setValueAt(paramName_CouplingOpTypeStr, i, 0, NOT_EDITABLE, flagFireCellUpdate);
    setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
    paramIndex_CouplingOpTypeStr = i;
    i++;
  
    setValueAt(paramName_lagrangianList, i, 0, NOT_EDITABLE, flagFireCellUpdate);
    setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
    paramIndex_lagrangianList = i;
    i++;

    setValueAt(paramName_eulerianList, i, 0, NOT_EDITABLE, flagFireCellUpdate);
    setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
    paramIndex_eulerianList = i;
    i++;

    setValueAt(paramName_operatorTypeStr, i, 0, NOT_EDITABLE, flagFireCellUpdate);
    setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
    paramIndex_operatorTypeStr = i;
    i++;

    switch (flagOperatorTypeDisplayMode) {

      case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_T_KERNEL_1:
        
        setValueAt(paramName_weightTableFilename, i, 0, NOT_EDITABLE, flagFireCellUpdate);
        setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
        paramIndex_weightTableFilename = i;
        i++;

        setValueAt(paramName_plotColor, i, 0, NOT_EDITABLE, flagFireCellUpdate);
        setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
        paramIndex_plotColor = i;
        i++;

        break;

    } /* end flagOperatorTypeDisplayMode */

    setValueAt(paramName_flagVisible, i, 0, NOT_EDITABLE, flagFireCellUpdate);
    setValueAt(new TableData_EditorButton(), i, 2, EDITABLE, flagFireCellUpdate);
    paramIndex_flagVisible = i;
    i++;
  }

  @Override
  public void setFromCouplingOpData(SELM_CouplingOperator couplingOp_in) {
    couplingOp = couplingOp_in;
    setFromCouplingOpData();
  }

  @Override
  public void setFromCouplingOpData() {
    setFromCouplingOpData(true);
  }
  
  public void setFromCouplingOpData(boolean flagFireChangeEvent) {

    SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOpData
      = (SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1) couplingOp;

    Object obj;

    int colValue = 1;

    if (couplingOpData != null) {

      //System.out.println("TableModel_CouplingOp_TABLE1 : setFromCouplingOpData() ");
      
      TableData_MultipleChoice1 data_operatorTypes;
      obj = data[paramIndex_operatorTypeStr][colValue];

      if (TableData_MultipleChoice1.class.isInstance(obj)) {        
        data_operatorTypes     = (TableData_MultipleChoice1) obj;
      } else {
        data_operatorTypes = new TableData_MultipleChoice1();

        data_operatorTypes.setTableModelToNotify(this);
        
        String[] opTypeStrs = new String[4];
        opTypeStrs[SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_NULL]       = SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_STR_NULL;
        opTypeStrs[SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_T_KERNEL_1] = SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_STR_T_KERNEL_1;
        opTypeStrs[SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_T_FAXEN_1]  = SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_STR_T_FAXEN_1;
        opTypeStrs[SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_TR_FAXEN_1] = SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_STR_TR_FAXEN_1;

        data_operatorTypes.setPossibleValues(opTypeStrs);
       
        //System.out.println("setup the operator types : TableData_MultipleChoice1 data_operatorTypes;");
      }

      /* determine the table mode */
      String operatorTypeStr = couplingOpData.getOperatorTypeStr();
      data_operatorTypes.selectMatch(operatorTypeStr);
      flagOperatorTypeDisplayMode       = data_operatorTypes.getSelectedItemIndex();
      //System.out.println("flagOperatorTypeDisplayMode = " + flagOperatorTypeDisplayMode);

      /* clear the table and set the entries according to the presented data */
      removeAllEntries(false);
      setupFirstThirdColumns();

      /* set all fields at once and then fire edited event */
      data[paramIndex_operatorTypeStr][colValue] = data_operatorTypes;
      dataEditable[paramIndex_operatorTypeStr][colValue] = EDITABLE;

      data[paramIndex_CouplingOpName][colValue] = couplingOpData.CouplingOpName;
      dataEditable[paramIndex_CouplingOpName][colValue] = EDITABLE;

      data[paramIndex_CouplingOpTypeStr][colValue] = couplingOpData.CouplingOpTypeStr;
      dataEditable[paramIndex_CouplingOpTypeStr][colValue] = NOT_EDITABLE;
      
      TableData_LagrangianList data_lagrangianList;

      obj = data[paramIndex_lagrangianList][colValue];
      if (TableData_LagrangianList.class.isInstance(obj)) {
        data_lagrangianList = (TableData_LagrangianList) obj;
      } else {
        data_lagrangianList = new TableData_LagrangianList();
        data_lagrangianList.setDisplayMode(TableData_LagrangianList.DISPLAY_NAME_LIST);
      }
      data_lagrangianList.lagrangianList = couplingOpData.getLagrangianList();

      data[paramIndex_lagrangianList][colValue] = data_lagrangianList;
      dataEditable[paramIndex_lagrangianList][colValue] = EDITABLE;

      TableData_EulerianList data_eulerianList;

      obj = data[paramIndex_eulerianList][colValue];
      if (TableData_EulerianList.class.isInstance(obj)) {
        data_eulerianList = (TableData_EulerianList) obj;
      } else {
        data_eulerianList = new TableData_EulerianList();
        data_eulerianList.setDisplayMode(TableData_EulerianList.DISPLAY_NAME_LIST);
      }
      data_eulerianList.eulerianList = couplingOpData.getEulerianList();

      data[paramIndex_eulerianList][colValue] = data_eulerianList;
      dataEditable[paramIndex_eulerianList][colValue] = EDITABLE;

      switch (flagOperatorTypeDisplayMode) {

        case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_NULL:

          operatorData_NULL = (SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_NULL) couplingOpData.getOperatorData();

          break;

        case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_T_KERNEL_1:

          /* get the operator data from coupling operator */
          operatorData_T_KERNEL_1 = (SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_T_KERNEL_1) couplingOpData.getOperatorData();
                    
          TableData_Filename data_tableFilename;
          obj = data[paramIndex_weightTableFilename][colValue];
          if (TableData_Filename.class.isInstance(obj)) {
            data_tableFilename = (TableData_Filename) obj;
          } else {
            data_tableFilename = new TableData_Filename();
          }
          data_tableFilename.setFilename(operatorData_T_KERNEL_1.weightTableFilename);
          
          data[paramIndex_weightTableFilename][colValue] = data_tableFilename;
          dataEditable[paramIndex_weightTableFilename][colValue] = EDITABLE;
          
          data[paramIndex_plotColor][colValue] = new TableData_Color(operatorData_T_KERNEL_1.plotColor);
          dataEditable[paramIndex_plotColor][colValue] = EDITABLE;

          break;

      } /* end flagOperatorTypeDisplayMode */
     
      data[paramIndex_flagVisible][colValue]         = (Boolean) couplingOpData.isVisible();
      dataEditable[paramIndex_flagVisible][colValue] = EDITABLE;

      /* notify listeners that all data may have changed */
      if (flagFireChangeEvent) {
        this.fireTableDataChanged();
      }

    }

  }

  @Override
  public void setCouplingOpDataFromModel() {

    int colValue = 1;
    Object obj;


    //System.out.println("TableModel_CouplingOp_TABLE1 : setCouplingOpDataFromModel() ");

    SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOpData = (SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1) couplingOp;

    couplingOp.CouplingOpName = (String) getValueAt(paramIndex_CouplingOpName, colValue);

    TableData_MultipleChoice1 data_operatorType = (TableData_MultipleChoice1) getValueAt(paramIndex_operatorTypeStr, colValue);

    //System.out.println("TableModel_CouplingOp_TABLE1 : data_operatorType.getSelectedItem() = " + data_operatorType.getSelectedItem() );
    //System.out.println("TableModel_CouplingOp_TABLE1 : flagOperatorMode = " + flagOperatorTypeDisplayMode);

    /* == Set the coupling operator data (synchronize with the mesh below, if appropriate data displayed) */
    int modelOperatorType = data_operatorType.getSelectedItemIndex();
    switch (modelOperatorType) {

      case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_NULL:

        //System.out.println("TableModel_CouplingOp_TABLE1 : couplingOpData.setOperatorData(operatorData_NULL); ");
        couplingOpData.setOperatorData(operatorData_NULL);

        break;

      case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_T_KERNEL_1:

        //System.out.println("TableModel_CouplingOp_TABLE1 : couplingOpData.setOperatorData(operatorData_T_KERNEL_1) ");
        couplingOpData.setOperatorData(operatorData_T_KERNEL_1);
        
        break;

      case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_T_FAXEN_1:

        //System.out.println("TableModel_CouplingOp_TABLE1 : couplingOpData.setOperatorData(operatorData_T_FAXEN_1) ");
        couplingOpData.setOperatorData(operatorData_T_FAXEN_1);

        break;

     case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_TR_FAXEN_1:

        //System.out.println("TableModel_CouplingOp_TABLE1 : couplingOpData.setOperatorData(operatorData_TR_FAXEN_1) ");
        couplingOpData.setOperatorData(operatorData_TR_FAXEN_1);

        break;
        
    } /* set the operator data to be the last one used for this type */


    TableData_LagrangianList data_lagrangianList;
    obj = data[paramIndex_lagrangianList][colValue];
    if (TableData_LagrangianList.class.isInstance(obj)) {
      data_lagrangianList = (TableData_LagrangianList) obj;
      couplingOpData.lagrangianList.clear();
      for (int k = 0; k < data_lagrangianList.lagrangianList.length; k++) {
        couplingOpData.lagrangianList.add(data_lagrangianList.lagrangianList[k]);
      }
    } else {
      /* nothing */
    }

    TableData_EulerianList data_eulerianList;
    obj = data[paramIndex_eulerianList][colValue];
    if (TableData_EulerianList.class.isInstance(obj)) {
      data_eulerianList = (TableData_EulerianList) obj;
      couplingOpData.eulerianList.clear();
      for (int k = 0; k < data_eulerianList.eulerianList.length; k++) {
        couplingOpData.eulerianList.add(data_eulerianList.eulerianList[k]);
      }
    } else {
      /* nothing */
    }


    //flagOperatorTypeDisplayMode = data_operatorType.getSelectedItemIndex();
    //data_operatorType.setSelectedItem(flagOperatorTypeDisplayMode);

    //couplingOpData.setOperatorTypeStr(data_operatorType.getSelectedItem());
    //System.out.println("couplingOpData.setOperatorTypeStr = " + couplingOpData.getOperatorTypeStr());

    /* == Only synchronize with the table, if the currently displayed data is the same as the
     * operator type selected.
     * We only use table data if the current displayed information and the selected operator
     * type are equal.  Otherwise, we need to wait for table to refresh with new data.
     */
    if (flagOperatorTypeDisplayMode == modelOperatorType) {
      switch (flagOperatorTypeDisplayMode) {

        case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_NULL:

          /* set the coupling operator data to be consistent with the table */
          //System.out.println("TableModel_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 : couplingOpData.setOperatorData(operatorData_NULL); ");
          //couplingOpData.setOperatorData(operatorData_NULL);

          break;

        case SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.OP_TYPE_T_KERNEL_1:

          //System.out.println("flagOperatorTypeDisplayMode = " + flagOperatorTypeDisplayMode);
          TableData_Filename data_tableFilename;
          obj = data[paramIndex_weightTableFilename][colValue];
          if (TableData_Filename.class.isInstance(obj)) {
            data_tableFilename = (TableData_Filename) obj;
            operatorData_T_KERNEL_1.weightTableFilename = data_tableFilename.getFilename();
          } else {
            /* nothing */
          }
          
          TableData_Color data = (TableData_Color) getValueAt(paramIndex_plotColor, colValue);
          operatorData_T_KERNEL_1.plotColor = data.color;

          /* set the coupling operator data to be consistent with the table */
          //System.out.println("TableModel_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 : couplingOpData.setOperatorData(operatorData_T_KERNEL_1) ");
          //couplingOpData.setOperatorData(operatorData_T_KERNEL_1);

          break;

      } /* end flagOperatorTypeDisplayMode */

    } /* end if the current displayed information and the selected operator type are equal */

    couplingOpData.setVisible((Boolean) getValueAt(paramIndex_flagVisible, colValue));
    
  }

  @Override
  public SELM_CouplingOperator getCouplingOpDataFromModel() {
    setCouplingOpDataFromModel();
    return super.getCouplingOpDataFromModel();
  }

  @Override
  public Object getValueAt(int row, int col) {    
    return super.getValueAt(row, col);
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    this.setValueAt(value, row, col, true);
  }

  @Override
  public void setValueAt(Object value, int row, int col, boolean flagFireCellUpdate) {
    super.setValueAt(value, row, col, flagFireCellUpdate);
  }

  public int getFlagOperatorMode() {
    return flagOperatorTypeDisplayMode;
  }

  public void tableChanged(TableModelEvent e) {

//    System.out.println("TABLE MODEL Event: " + e);
//    System.out.println("UPDATE == " + (e.getType() == e.UPDATE));
//
//
//    //if ((e.getFirstRow() <= paramIndex_operatorTypeStr) && (e.getLastRow() >= paramIndex_operatorTypeStr)
//    //    && ((e.getColumn() == 1) || (e.getColumn() == e.ALL_COLUMNS)))  {
//
//      int colValue = 1;
//
//      Object obj   = data[paramIndex_operatorTypeStr][colValue];
//      //Object obj = value;
//
//      System.out.println("setValue obj = " + obj);
//      System.out.println("event = " + e);
//
//      if (TableData_MultipleChoice1.class.isInstance(obj)) {
//        System.out.println("TABLE MODEL Event: !!! Detected Mode change !!! " + e);
//        TableData_MultipleChoice1 data_operatorTypes = (TableData_MultipleChoice1) obj;
//        System.out.println("TABLE MODEL Event: data_operatorTypes.getSelectedItemIndex() =  " + data_operatorTypes.getSelectedItemIndex());
//        setCouplingOpDataFromModel();
//        setFlagOperatorMode(data_operatorTypes.getSelectedItemIndex(),false);
//      }
//
//    //}

  }

  

  
}
