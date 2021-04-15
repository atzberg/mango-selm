package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_EditorButton;
import java.awt.Color;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Lagrangian_SPECTRAL_FILAMENT1 extends TableModel_Lagrangian {

  private final static boolean DEBUG = false;

  public    String paramName_ptsX         = "Point Location X";
  protected int    paramIndex_ptsX        = -1;

  public    String paramName_ptsID        = "Point IDs";
  protected int    paramIndex_ptsID       = -1;

  public TableModel_Lagrangian_SPECTRAL_FILAMENT1(SELM_Lagrangian_SPECTRAL_FILAMENT1 lagrangian) {
    setupFirstThirdColumns();

    setFromLagrangianData(lagrangian);
  }
  
  public TableModel_Lagrangian_SPECTRAL_FILAMENT1() {
    setupFirstThirdColumns();

    SELM_Lagrangian_SPECTRAL_FILAMENT1 lagrangian = new SELM_Lagrangian_SPECTRAL_FILAMENT1();
    
    setFromLagrangianData(lagrangian);
  }

  public void setupFirstThirdColumns() {

    int i = 0;

    setValueAt(paramName_LagrangianName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_LagrangianName = i;
    i++;

    setValueAt(paramName_LagrangianTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_LagrangianTypeStr = i;
    i++;

    setValueAt(paramName_ptsX, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_ptsX = i;
    i++;

    setValueAt(paramName_ptsID, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_ptsID = i;
    i++;
    
  }

  @Override
  public void setFromLagrangianData(SELM_Lagrangian lagrangian_in) {
    lagrangian = lagrangian_in;
    setFromLagrangianData();
  }

  @Override
  public void setFromLagrangianData() {

    SELM_Lagrangian_SPECTRAL_FILAMENT1 lagrangianData
      = (SELM_Lagrangian_SPECTRAL_FILAMENT1)lagrangian;

    int colValue = 1;

    if (lagrangian !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_LagrangianName][colValue]         = lagrangianData.LagrangianName;
      dataEditable[paramIndex_LagrangianName][colValue] = EDITABLE;

      data[paramIndex_LagrangianTypeStr][colValue]         = lagrangianData.LagrangianTypeStr;
      dataEditable[paramIndex_LagrangianTypeStr][colValue] = NOT_EDITABLE;

      data[paramIndex_ptsX][colValue]         = lagrangianData.getPtsX();
      dataEditable[paramIndex_ptsX][colValue] = EDITABLE;

      data[paramIndex_ptsID][colValue]         = lagrangianData.getPtsID();
      dataEditable[paramIndex_ptsID][colValue] = EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

      //int      numPairs = LagrangianData.getNumPairs();
      /*
      setValueAt(LagrangianData.LagrangianName,    paramIndex_LagrangianName, colValue);
      setValueAt(LagrangianData.getPairList_ptI1(), paramIndex_Pairs_I1,        colValue);
      setValueAt(LagrangianData.getPairList_ptI2(), paramIndex_Pairs_I2,        colValue);
      setValueAt(interactionData.getRestLength(),    paramIndex_RestLength,      colValue);
      setValueAt(interactionData.getStiffnessK(),    paramIndex_Stiffness,       colValue);
       */

    }
    
  }

  @Override
  public void setLagrangianDataFromModel() {

   int colValue = 1;

   SELM_Lagrangian_SPECTRAL_FILAMENT1 lagrangianData
      = (SELM_Lagrangian_SPECTRAL_FILAMENT1)lagrangian;

    //if (lagrangianData == null) { /* setup data structure, if not present yet */
    //  lagrangianData = new SELM_Lagrangian_PAIRS_HARMONIC();
    //  lagrangian     = (SELM_Lagrangian) lagrangianData;
    //}

    String   LagrangianName = (String)   getValueAt(paramIndex_LagrangianName,colValue);
    double[] ptsX           = (double[]) getValueAt(paramIndex_ptsX,colValue);
    int[]    ptsID          = (int[])    getValueAt(paramIndex_ptsID,colValue);
       
    lagrangianData.LagrangianName = LagrangianName;
    lagrangianData.setPtsX(ptsX);
    lagrangianData.setPtsID(ptsID);
    
  }

  @Override
  public SELM_Lagrangian getLagrangianDataFromModel() {
    setLagrangianDataFromModel();
    return super.getLagrangianDataFromModel();
  }

    
}







