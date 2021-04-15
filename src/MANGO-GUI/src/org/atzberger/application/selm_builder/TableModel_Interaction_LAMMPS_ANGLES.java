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
public class TableModel_Interaction_LAMMPS_ANGLES extends TableModel_Interaction {

  private final static boolean DEBUG = false;

  public    String paramName_angleList_ptI1           = "Point Index I1";
  protected int    paramIndex_angleList_ptI1          = -1;

  public    String paramName_angleList_ptI2           = "Point Index I2";
  protected int    paramIndex_angleList_ptI2          = -1;

  public    String paramName_angleList_ptI3           = "Point Index I3";
  protected int    paramIndex_angleList_ptI3          = -1;

  public    String paramName_angleList_lagrangianI1   = "Lagrangian I1";
  protected int    paramIndex_angleList_lagrangianI1  = -1;

  public    String paramName_angleList_lagrangianI2   = "Lagrangian I2";
  protected int    paramIndex_angleList_lagrangianI2  = -1;

  public    String paramName_angleList_lagrangianI3   = "Lagrangian I3";
  protected int    paramIndex_angleList_lagrangianI3  = -1;

  public    String paramName_angleStyle    = "Angle Style";
  protected int    paramIndex_angleStyle   = -1;

  public    String paramName_angleTypeID    = "Angle Type ID";
  protected int    paramIndex_angleTypeID   = -1;

  public    String paramName_angleCoeffs    = "Angle Coefficients";
  protected int    paramIndex_angleCoeffs   = -1;
  
  public    String paramName_plotColor             = "Color";
  protected int    paramIndex_plotColor            = -1;

  public    String paramName_flagVisible           = "Visible";
  protected int    paramIndex_flagVisible          = -1;

  public TableModel_Interaction_LAMMPS_ANGLES(SELM_Interaction_LAMMPS_ANGLES interaction) {
    setupFirstThirdColumns();

    setFromInteractionData(interaction);
  }
  
  public TableModel_Interaction_LAMMPS_ANGLES() {
    setupFirstThirdColumns();

    SELM_Interaction_LAMMPS_ANGLES interaction = new SELM_Interaction_LAMMPS_ANGLES();
    
    setFromInteractionData(interaction);
  }

  public void setupFirstThirdColumns() {

    int i = 0;

    setValueAt(paramName_InteractionName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_InteractionName = i;
    i++;

    setValueAt(paramName_InteractionTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_InteractionTypeStr = i;
    i++;

    setValueAt(paramName_angleList_lagrangianI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleList_lagrangianI1 = i;
    i++;

    setValueAt(paramName_angleList_ptI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleList_ptI1 = i;
    i++;

    setValueAt(paramName_angleList_lagrangianI2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleList_lagrangianI2 = i;
    i++;

    setValueAt(paramName_angleList_ptI2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleList_ptI2 = i;
    i++;

    setValueAt(paramName_angleList_lagrangianI3, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleList_lagrangianI3 = i;
    i++;

    setValueAt(paramName_angleList_ptI3, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleList_ptI3 = i;
    i++;

    setValueAt(paramName_angleStyle, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleStyle = i;
    i++;

    setValueAt(paramName_angleTypeID, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleTypeID = i;
    i++;

    setValueAt(paramName_angleCoeffs, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_angleCoeffs = i;
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

    SELM_Interaction_LAMMPS_ANGLES interactionData
      = (SELM_Interaction_LAMMPS_ANGLES)interaction;

    TableData_LagrangianList lagrangianI1_Names;
    TableData_LagrangianList lagrangianI2_Names;
    TableData_LagrangianList lagrangianI3_Names;

    int colValue = 1;

    if (interaction !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_InteractionName][colValue]         = interactionData.InteractionName;
      dataEditable[paramIndex_InteractionName][colValue] = EDITABLE;

      data[paramIndex_InteractionTypeStr][colValue]         = interactionData.InteractionTypeStr;
      dataEditable[paramIndex_InteractionTypeStr][colValue] = NOT_EDITABLE;

      data[paramIndex_angleList_ptI1][colValue]         = interactionData.getAngleList_ptI1();
      dataEditable[paramIndex_angleList_ptI1][colValue] = EDITABLE;

      data[paramIndex_angleList_ptI2][colValue]         = interactionData.getAngleList_ptI2();
      dataEditable[paramIndex_angleList_ptI2][colValue] = EDITABLE;

      data[paramIndex_angleList_ptI3][colValue]         = interactionData.getAngleList_ptI3();
      dataEditable[paramIndex_angleList_ptI3][colValue] = EDITABLE;

      lagrangianI1_Names = new TableData_LagrangianList(interactionData.getAngleList_lagrangianI1());
      lagrangianI1_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;
      
      data[paramIndex_angleList_lagrangianI1][colValue]         = lagrangianI1_Names;
      dataEditable[paramIndex_angleList_lagrangianI1][colValue] = EDITABLE;

      lagrangianI2_Names = new TableData_LagrangianList(interactionData.getAngleList_lagrangianI2());
      lagrangianI2_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;

      data[paramIndex_angleList_lagrangianI2][colValue]         = lagrangianI2_Names;
      dataEditable[paramIndex_angleList_lagrangianI2][colValue] = EDITABLE;

      lagrangianI3_Names = new TableData_LagrangianList(interactionData.getAngleList_lagrangianI3());
      lagrangianI3_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;

      data[paramIndex_angleList_lagrangianI3][colValue]         = lagrangianI3_Names;
      dataEditable[paramIndex_angleList_lagrangianI3][colValue] = EDITABLE;

      data[paramIndex_angleStyle][colValue]         = interactionData.getAngleStyle();
      dataEditable[paramIndex_angleStyle][colValue] = EDITABLE;

      data[paramIndex_angleTypeID][colValue]         = interactionData.getAngleTypeID();
      dataEditable[paramIndex_angleTypeID][colValue] = EDITABLE;

      data[paramIndex_angleCoeffs][colValue]         = interactionData.getAngleCoeffsStr();
      dataEditable[paramIndex_angleCoeffs][colValue] = EDITABLE;
      
      data[paramIndex_plotColor][colValue]         = new TableData_Color(interactionData.getRenderColor());
      dataEditable[paramIndex_plotColor][colValue] = EDITABLE;

      data[paramIndex_flagVisible][colValue]         = (Boolean) interactionData.getRenderVisible();
      dataEditable[paramIndex_flagVisible][colValue] = EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

      //int      numAngles = InteractionData.getNumAngles();
      /*
      setValueAt(InteractionData.InteractionName,    paramIndex_InteractionName, colValue);
      setValueAt(InteractionData.getAngleList_ptI1(), paramIndex_Angles_I1,        colValue);
      setValueAt(InteractionData.getAngleList_ptI2(), paramIndex_Angles_I2,        colValue);
      setValueAt(interactionData.getRestLength(),    paramIndex_RestLength,      colValue);
      setValueAt(interactionData.getStiffnessK(),    paramIndex_Stiffness,       colValue);
       */

    }
    
  }

  @Override
  public void setInteractionDataFromModel() {

   int colValue = 1;

   SELM_Interaction_LAMMPS_ANGLES interactionData
      = (SELM_Interaction_LAMMPS_ANGLES)interaction;

    //if (interactionData == null) { /* setup data structure, if not present yet */
    //  interactionData = new SELM_Interaction_LAMMPS_ANGLES();
    //  interaction     = (SELM_Interaction) interactionData;
    //}

    String   InteractionName = (String) getValueAt(paramIndex_InteractionName,colValue);    
    int[]    angleList_ptI1   = (int[])  getValueAt(paramIndex_angleList_ptI1,colValue);
    int[]    angleList_ptI2   = (int[])  getValueAt(paramIndex_angleList_ptI2,colValue);
    int[]    angleList_ptI3   = (int[])  getValueAt(paramIndex_angleList_ptI3,colValue);

    TableData_LagrangianList data_lagrangianI1 = (TableData_LagrangianList)getValueAt(paramIndex_angleList_lagrangianI1,colValue);
    TableData_LagrangianList data_lagrangianI2 = (TableData_LagrangianList)getValueAt(paramIndex_angleList_lagrangianI2,colValue);
    TableData_LagrangianList data_lagrangianI3 = (TableData_LagrangianList)getValueAt(paramIndex_angleList_lagrangianI3,colValue);

    SELM_Lagrangian[]    angleList_lagrangianI1   = data_lagrangianI1.lagrangianList;
    SELM_Lagrangian[]    angleList_lagrangianI2   = data_lagrangianI2.lagrangianList;
    SELM_Lagrangian[]    angleList_lagrangianI3   = data_lagrangianI3.lagrangianList;

    String   angleStyle       = (String) getValueAt(paramIndex_angleStyle,colValue);
    int      angleTypeID      = (Integer) getValueAt(paramIndex_angleTypeID,colValue);
    String   angleCoeffsStr   = (String) getValueAt(paramIndex_angleCoeffs,colValue);
    
    Color    plotColor      = ((TableData_Color) getValueAt(paramIndex_plotColor,colValue)).color;
    boolean  flagVisible    = (Boolean)  getValueAt(paramIndex_flagVisible,colValue);
       
    interactionData.InteractionName = InteractionName;
    interactionData.setAngleStyle(angleStyle);
    interactionData.setAngleTypeID(angleTypeID);
    interactionData.setAngleCoeffsStr(angleCoeffsStr);
    interactionData.setAngleData(angleList_ptI1.length,
                                 angleList_lagrangianI1, angleList_ptI1,
                                 angleList_lagrangianI2, angleList_ptI2,
                                 angleList_lagrangianI3, angleList_ptI3);
    
    interactionData.setRenderColor(plotColor);
    interactionData.setRenderVisible(flagVisible);

  }

  @Override
  public SELM_Interaction getInteractionDataFromModel() {
    setInteractionDataFromModel();
    return super.getInteractionDataFromModel();
  }

    
}

