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
public class TableModel_Interaction_LAMMPS_PAIRS_HARMONIC extends TableModel_Interaction {

  private final static boolean DEBUG = false;

  public    String paramName_pairList_ptI1           = "Point Index I1";
  protected int    paramIndex_pairList_ptI1          = -1;

  public    String paramName_pairList_ptI2           = "Point Index I2";
  protected int    paramIndex_pairList_ptI2          = -1;

  public    String paramName_pairList_lagrangianI1   = "Lagrangian I1";
  protected int    paramIndex_pairList_lagrangianI1  = -1;

  public    String paramName_pairList_lagrangianI2   = "Lagrangian I2";
  protected int    paramIndex_pairList_lagrangianI2  = -1;

  public    String paramName_restLength   = "Rest Length";
  protected int    paramIndex_restLength  = -1;

  public    String paramName_stiffnessK   = "Stiffness";
  protected int    paramIndex_stiffnessK  = -1;

  public    String paramName_plotColor             = "Color";
  protected int    paramIndex_plotColor            = -1;

  public    String paramName_flagVisible           = "Visible";
  protected int    paramIndex_flagVisible          = -1;

  TableModel_Interaction_LAMMPS_PAIRS_HARMONIC(SELM_Interaction_LAMMPS_PAIRS_HARMONIC interaction) {
    setupFirstThirdColumns();

    setFromInteractionData(interaction);
  }
  
  TableModel_Interaction_LAMMPS_PAIRS_HARMONIC() {
    setupFirstThirdColumns();

    SELM_Interaction_LAMMPS_PAIRS_HARMONIC interaction = new SELM_Interaction_LAMMPS_PAIRS_HARMONIC();
    
    setFromInteractionData(interaction);
  }

  void setupFirstThirdColumns() {

    int i = 0;

    setValueAt(paramName_InteractionName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_InteractionName = i;
    i++;

    setValueAt(paramName_InteractionTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_InteractionTypeStr = i;
    i++;

    setValueAt(paramName_pairList_lagrangianI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_lagrangianI1 = i;
    i++;

    setValueAt(paramName_pairList_ptI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_ptI1 = i;
    i++;

    setValueAt(paramName_pairList_lagrangianI2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_lagrangianI2 = i;
    i++;

    setValueAt(paramName_pairList_ptI2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_ptI2 = i;
    i++;

    setValueAt(paramName_restLength, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_restLength = i;
    i++;

    setValueAt(paramName_stiffnessK, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_stiffnessK = i;
    i++;

    setValueAt(paramName_plotColor, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_plotColor = i;
    i++;

    setValueAt(paramName_flagVisible, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagVisible = i;
    i++;
    
  }

  @Override
  public void setFromInteractionData(SELM_Interaction interaction_in) {
    interaction = interaction_in;
    setFromInteractionData();
  }

  @Override
  public void setFromInteractionData() {

    SELM_Interaction_LAMMPS_PAIRS_HARMONIC interactionData
      = (SELM_Interaction_LAMMPS_PAIRS_HARMONIC)interaction;

    TableData_LagrangianList lagrangianI1_Names;
    TableData_LagrangianList lagrangianI2_Names;

    int colValue = 1;

    if (interaction !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_InteractionName][colValue]         = interactionData.InteractionName;
      dataEditable[paramIndex_InteractionName][colValue] = EDITABLE;

      data[paramIndex_InteractionTypeStr][colValue]         = interactionData.InteractionTypeStr;
      dataEditable[paramIndex_InteractionTypeStr][colValue] = NOT_EDITABLE;

      data[paramIndex_pairList_ptI1][colValue]         = interactionData.getPairList_ptI1();
      dataEditable[paramIndex_pairList_ptI1][colValue] = EDITABLE;

      data[paramIndex_pairList_ptI2][colValue]         = interactionData.getPairList_ptI2();
      dataEditable[paramIndex_pairList_ptI2][colValue] = EDITABLE;

      lagrangianI1_Names = new TableData_LagrangianList(interactionData.getPairList_lagrangianI1());
      lagrangianI1_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;
      
      data[paramIndex_pairList_lagrangianI1][colValue]         = lagrangianI1_Names;
      dataEditable[paramIndex_pairList_lagrangianI1][colValue] = EDITABLE;

      lagrangianI2_Names = new TableData_LagrangianList(interactionData.getPairList_lagrangianI2());
      lagrangianI2_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;
      data[paramIndex_pairList_lagrangianI2][colValue]         = lagrangianI2_Names;
      dataEditable[paramIndex_pairList_lagrangianI2][colValue] = EDITABLE;

      data[paramIndex_stiffnessK][colValue]         = interactionData.getStiffnessK();
      dataEditable[paramIndex_stiffnessK][colValue] = EDITABLE;

      data[paramIndex_restLength][colValue]         = interactionData.getRestLength();
      dataEditable[paramIndex_restLength][colValue] = EDITABLE;
      
      data[paramIndex_plotColor][colValue]         = new TableData_Color(interactionData.getRenderColor());
      dataEditable[paramIndex_plotColor][colValue] = EDITABLE;

      data[paramIndex_flagVisible][colValue]         = (Boolean) interactionData.getRenderVisible();
      dataEditable[paramIndex_flagVisible][colValue] = EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

      //int      numPairs = InteractionData.getNumPairs();
      /*
      setValueAt(InteractionData.InteractionName,    paramIndex_InteractionName, colValue);
      setValueAt(InteractionData.getPairList_ptI1(), paramIndex_Pairs_I1,        colValue);
      setValueAt(InteractionData.getPairList_ptI2(), paramIndex_Pairs_I2,        colValue);
      setValueAt(interactionData.getRestLength(),    paramIndex_RestLength,      colValue);
      setValueAt(interactionData.getStiffnessK(),    paramIndex_Stiffness,       colValue);
       */

    }
    
  }

  @Override
  public void setInteractionDataFromModel() {

   int colValue = 1;

   SELM_Interaction_LAMMPS_PAIRS_HARMONIC interactionData
      = (SELM_Interaction_LAMMPS_PAIRS_HARMONIC)interaction;

    //if (interactionData == null) { /* setup data structure, if not present yet */
    //  interactionData = new SELM_Interaction_LAMMPS_PAIRS_HARMONIC();
    //  interaction     = (SELM_Interaction) interactionData;
    //}

    String   InteractionName = (String) getValueAt(paramIndex_InteractionName,colValue);    
    int[]    pairList_ptI1   = (int[])  getValueAt(paramIndex_pairList_ptI1,colValue);
    int[]    pairList_ptI2   = (int[])  getValueAt(paramIndex_pairList_ptI2,colValue);

    TableData_LagrangianList data_lagrangianI1 = (TableData_LagrangianList)getValueAt(paramIndex_pairList_lagrangianI1,colValue);
    TableData_LagrangianList data_lagrangianI2 = (TableData_LagrangianList)getValueAt(paramIndex_pairList_lagrangianI2,colValue);

    SELM_Lagrangian[]    pairList_lagrangianI1   = data_lagrangianI1.lagrangianList;
    SELM_Lagrangian[]    pairList_lagrangianI2   = data_lagrangianI2.lagrangianList;

    double[] stiffnessK      = (double[]) getValueAt(paramIndex_stiffnessK,colValue);
    double[] restLength      = (double[]) getValueAt(paramIndex_restLength,colValue);

    Color    plotColor      = ((TableData_Color) getValueAt(paramIndex_plotColor,colValue)).color;
    boolean  flagVisible    = (Boolean)  getValueAt(paramIndex_flagVisible,colValue);
       
    interactionData.InteractionName = InteractionName;
    interactionData.setPairData(pairList_ptI1.length,
                                pairList_lagrangianI1, pairList_ptI1,
                                pairList_lagrangianI2, pairList_ptI2,
                                stiffnessK, restLength);
    interactionData.setRenderColor(plotColor);
    interactionData.setRenderVisible(flagVisible);

  }

  @Override
  public SELM_Interaction getInteractionDataFromModel() {
    setInteractionDataFromModel();
    return super.getInteractionDataFromModel();
  }

    
}

