package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_Units_Double;
import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Filename;
import java.awt.Color;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_CouplingOperator_IB1 extends TableModel_CouplingOperator {

  private final static boolean DEBUG = false;

  public Atz_UnitsRef atz_UnitsRef;

  public    String paramName_weightTableFilename         = "Weight Table Filename";
  protected int    paramIndex_weightTableFilename        = -1;

  public    String paramName_kernelSize        = "Kernal Size";
  protected int    paramIndex_kernelSize       = -1;

  public TableModel_CouplingOperator_IB1(SELM_CouplingOperator_IB1 couplingOp) {
    setupFirstThirdColumns();
    setFromCouplingOpData(couplingOp);
  }

  public TableModel_CouplingOperator_IB1(Atz_UnitsRef atz_UnitsRef_in) {
    atz_UnitsRef = atz_UnitsRef_in;
    setupFirstThirdColumns();
  }

  public TableModel_CouplingOperator_IB1(SELM_CouplingOperator_IB1 couplingOp, Atz_UnitsRef atz_UnitsRef_in) {
    atz_UnitsRef = atz_UnitsRef_in;
    setupFirstThirdColumns();
    setFromCouplingOpData(couplingOp);
  }
  
  public TableModel_CouplingOperator_IB1() {
    setupFirstThirdColumns();

    SELM_CouplingOperator_IB1 couplingOp = new SELM_CouplingOperator_IB1();
    
    setFromCouplingOpData(couplingOp);
  }

  public void setupFirstThirdColumns() {

    int i = 0;

    setValueAt(paramName_CouplingOpName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_CouplingOpName = i;
    i++;

    setValueAt(paramName_CouplingOpTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_CouplingOpTypeStr = i;
    i++;

    setValueAt(paramName_weightTableFilename, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_weightTableFilename = i;
    i++;

    setValueAt(paramName_kernelSize, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_kernelSize = i;
    i++;
    
  }

  @Override
  public void setFromCouplingOpData(SELM_CouplingOperator couplingOp_in) {
    couplingOp = couplingOp_in;
    setFromCouplingOpData();
  }

  @Override
  public void setFromCouplingOpData() {

    SELM_CouplingOperator_IB1 couplingOpData
      = (SELM_CouplingOperator_IB1)couplingOp;

    int colValue = 1;

    if (couplingOp !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_CouplingOpName][colValue]         = couplingOpData.CouplingOpName;
      dataEditable[paramIndex_CouplingOpName][colValue] = EDITABLE;

      data[paramIndex_CouplingOpTypeStr][colValue]         = couplingOpData.CouplingOpTypeStr;
      dataEditable[paramIndex_CouplingOpTypeStr][colValue] = NOT_EDITABLE;

      TableData_Filename tableData_filename = new TableData_Filename(couplingOpData.getWeightTableFilename());
      data[paramIndex_weightTableFilename][colValue]         = tableData_filename;
      dataEditable[paramIndex_weightTableFilename][colValue] = EDITABLE;
      
      TableData_Units_Double tableData_kernelSize = new TableData_Units_Double(couplingOpData.getKernelSize(), "[length]", atz_UnitsRef);
      data[paramIndex_kernelSize][colValue]         = tableData_kernelSize;
      dataEditable[paramIndex_kernelSize][colValue] = EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

      //int      numPairs = CouplingOpData.getNumPairs();
      /*
      setValueAt(CouplingOpData.CouplingOpName,    paramIndex_CouplingOpName, colValue);
      setValueAt(CouplingOpData.getPairList_ptI1(), paramIndex_Pairs_I1,        colValue);
      setValueAt(CouplingOpData.getPairList_ptI2(), paramIndex_Pairs_I2,        colValue);
      setValueAt(interactionData.getRestLength(),    paramIndex_RestLength,      colValue);
      setValueAt(interactionData.getStiffnessK(),    paramIndex_Stiffness,       colValue);
       */

    }
    
  }

  @Override
  public void setCouplingOpDataFromModel() {

   int colValue = 1;

   SELM_CouplingOperator_IB1 couplingOpData
      = (SELM_CouplingOperator_IB1)couplingOp;

    //if (couplingOpData == null) { /* setup data structure, if not present yet */
    //  couplingOpData = new SELM_CouplingOp_PAIRS_HARMONIC();
    //  couplingOp     = (SELM_CouplingOperator) couplingOpData;
    //}

    String                 CouplingOpName       = (String)   getValueAt(paramIndex_CouplingOpName,colValue);
    TableData_Filename     tableData_filename   = (TableData_Filename) getValueAt(paramIndex_weightTableFilename,colValue);
    String                 weightTableFilename  = tableData_filename.getFilename();
    TableData_Units_Double tableData_kernelSize = (TableData_Units_Double)getValueAt(paramIndex_kernelSize,colValue);
    double                 kernelSize           = tableData_kernelSize.value;
       
    couplingOpData.CouplingOpName = CouplingOpName;
    couplingOpData.setWeightTableFilename(weightTableFilename);
    couplingOpData.setKernelSize(kernelSize);
    
  }

  @Override
  public SELM_CouplingOperator getCouplingOpDataFromModel() {
    setCouplingOpDataFromModel();
    return super.getCouplingOpDataFromModel();
  }

    
}







