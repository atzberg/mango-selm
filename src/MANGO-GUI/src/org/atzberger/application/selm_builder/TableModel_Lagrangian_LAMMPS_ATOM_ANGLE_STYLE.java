package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Color;
import java.awt.Color;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE extends TableModel_Lagrangian {

  private final static boolean DEBUG = false;

  public    String paramName_ptsX         = "Point Location X";
  protected int    paramIndex_ptsX        = -1;

  public    String paramName_atomID        = "Atom ID";
  protected int    paramIndex_atomID      = -1;

  public    String paramName_moleculeID   = "Molecule ID";
  protected int    paramIndex_moleculeID  = -1;

  public    String paramName_typeID       = "Type ID";
  protected int    paramIndex_typeID      = -1;

  public    String paramName_atomMass     = "Atom Mass";
  protected int    paramIndex_atomMass    = -1;

  public    String paramName_plotColor    = "Color";
  protected int    paramIndex_plotColor   = -1;

  public    String paramName_flagVisible  = "Visible";
  protected int    paramIndex_flagVisible = -1;

  //public    String paramName_flagWriteVTK  = "Write VTK File";
  //protected int    paramIndex_flagWriteVTK = -1;
  
  public    String paramName_outputSimulationData  = "Output Simulation Data";
  protected int paramIndex_outputSimulationData = -1;

  public TableModel_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE(SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangian) {
    setupFirstThirdColumns();

    setFromLagrangianData(lagrangian);
  }
  
  public TableModel_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE() {
    setupFirstThirdColumns();

    SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangian = new SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE();
    
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

    setValueAt(paramName_atomID, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_atomID = i;
    i++;

    setValueAt(paramName_moleculeID, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_moleculeID = i;
    i++;

    setValueAt(paramName_typeID, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_typeID = i;
    i++;

    setValueAt(paramName_atomMass, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_atomMass = i;
    i++;

    setValueAt(paramName_plotColor, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_plotColor = i;
    i++;

    setValueAt(paramName_flagVisible, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagVisible = i;
    i++;

    //setValueAt(paramName_flagWriteVTK, i,0, NOT_EDITABLE);
    //setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    //paramIndex_flagWriteVTK = i;
    //i++;
    
    setValueAt(paramName_outputSimulationData, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_outputSimulationData = i;
    i++;
    
  }

  @Override
  public void setFromLagrangianData(SELM_Lagrangian lagrangian_in) {
    lagrangian = lagrangian_in;
    setFromLagrangianData();
  }

  @Override
  public void setFromLagrangianData() {

    SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangianData
      = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE)lagrangian;

    int colValue = 1;

    if (lagrangian !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_LagrangianName][colValue]         = lagrangianData.LagrangianName;
      dataEditable[paramIndex_LagrangianName][colValue] = EDITABLE;

      data[paramIndex_LagrangianTypeStr][colValue]         = lagrangianData.LagrangianTypeStr;
      dataEditable[paramIndex_LagrangianTypeStr][colValue] = NOT_EDITABLE;

      data[paramIndex_ptsX][colValue]         = lagrangianData.getPtsX();
      dataEditable[paramIndex_ptsX][colValue] = EDITABLE;

      data[paramIndex_atomID][colValue]         = lagrangianData.getAtomID();
      dataEditable[paramIndex_atomID][colValue] = EDITABLE;
      
      data[paramIndex_moleculeID][colValue]         = lagrangianData.getMoleculeID();
      dataEditable[paramIndex_moleculeID][colValue] = EDITABLE;

      /* only allow one value for the typeID for this object */
      int typeID = -1;
      int[] typeIDs = lagrangianData.getTypeID();
      if ((typeIDs == null) || (typeIDs.length == 0)) {
        typeID = -1;
      } else {
        typeID = typeIDs[0];  /* assume the list is constant */
      }
      
      data[paramIndex_typeID][colValue]         = typeID;
      dataEditable[paramIndex_typeID][colValue] = EDITABLE;

      data[paramIndex_atomMass][colValue]         = lagrangianData.getAtomMass();
      dataEditable[paramIndex_atomMass][colValue] = EDITABLE;

      data[paramIndex_plotColor][colValue]         = new TableData_Color(lagrangianData.getPlotColor());
      dataEditable[paramIndex_plotColor][colValue] = EDITABLE;

      data[paramIndex_flagVisible][colValue]         = (Boolean) lagrangianData.isVisible();
      dataEditable[paramIndex_flagVisible][colValue] = EDITABLE;

      //data[paramIndex_flagWriteVTK][colValue]         = (Boolean) lagrangianData.getFlagWriteVTK();
      //dataEditable[paramIndex_flagWriteVTK][colValue] = EDITABLE;

      data[paramIndex_outputSimulationData][colValue]         = (String) lagrangianData.getOutputSimulationData();
      dataEditable[paramIndex_outputSimulationData][colValue] = EDITABLE;    
      
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

   SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangianData
      = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE)lagrangian;

    //if (lagrangianData == null) { /* setup data structure, if not present yet */
    //  lagrangianData = new SELM_Lagrangian_PAIRS_HARMONIC();
    //  lagrangian     = (SELM_Lagrangian) lagrangianData;
    //}

    String   LagrangianName = (String)   getValueAt(paramIndex_LagrangianName,colValue);
    double[] ptsX           = (double[]) getValueAt(paramIndex_ptsX,colValue);
    int[]    atomID         = (int[])    getValueAt(paramIndex_atomID,colValue);
    int[]    moleculeID     = (int[])    getValueAt(paramIndex_moleculeID,colValue);
    int      typeID         = (Integer)  getValueAt(paramIndex_typeID,colValue); /* only allow one value for all atoms */
    int[]    typeIDs        = lagrangianData.getTypeID();
    for (int k = 0; k < typeIDs.length; k++) {
      typeIDs[k] = typeID;
    }

    double[] atomMass       = (double[]) getValueAt(paramIndex_atomMass,colValue);
    Color    plotColor      = ((TableData_Color)getValueAt(paramIndex_plotColor,colValue)).color;
    boolean  flagVisible    = (Boolean)  getValueAt(paramIndex_flagVisible,colValue);
    //boolean  flagWriteVTK   = (Boolean)  getValueAt(paramIndex_flagWriteVTK,colValue);       
    String   outputSimulationData = (String) getValueAt(paramIndex_outputSimulationData,colValue);
    
    lagrangianData.LagrangianName = LagrangianName;
    lagrangianData.setPtsX(ptsX);
    lagrangianData.setAtomID(atomID);
    lagrangianData.setMoleculeID(moleculeID);
    lagrangianData.setTypeID(typeIDs);
    lagrangianData.setAtomMass(atomMass);
    lagrangianData.setPlotColor(plotColor);
    lagrangianData.setVisible(flagVisible);
    //lagrangianData.setFlagWriteVTK(flagWriteVTK);
    lagrangianData.setOutputSimulationData(outputSimulationData);
    
  }

  @Override
  public SELM_Lagrangian getLagrangianDataFromModel() {
    setLagrangianDataFromModel();
    return super.getLagrangianDataFromModel();
  }

    
}







