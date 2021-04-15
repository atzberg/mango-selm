package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Color;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Interaction_LAMMPS_CUSTOM1 extends TableModel_Interaction {

  private final static boolean DEBUG = false;

  public    String paramName_pairList_ptI1           = "Point Index I";
  protected int    paramIndex_pairList_ptI1          = -1;

  public    String paramName_pairList_lagrangianI1   = "Lagrangian I";
  protected int    paramIndex_pairList_lagrangianI1  = -1;
    
  public    String paramName_plotColor               = "Color";
  protected int    paramIndex_plotColor              = -1;

  public    String paramName_flagVisible             = "Visible";
  protected int    paramIndex_flagVisible            = -1;

  //public    String paramName_paramGenericBase        = "Parameter Name Here";
  protected int    paramIndex_paramGenericBase       = -1;

  public TableModel_Interaction_LAMMPS_CUSTOM1(SELM_Interaction_LAMMPS_CUSTOM1 interaction) {
    setupFirstThirdColumns();

    setFromInteractionData(interaction);
  }
  
  public TableModel_Interaction_LAMMPS_CUSTOM1() {
    setupFirstThirdColumns();

    SELM_Interaction_LAMMPS_CUSTOM1 interaction = new SELM_Interaction_LAMMPS_CUSTOM1();
    
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

    setValueAt(paramName_pairList_lagrangianI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_lagrangianI1 = i;
    i++;

    setValueAt(paramName_pairList_ptI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_ptI1 = i;
    i++;

    setValueAt(paramName_plotColor, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_plotColor = i;
    i++;

    setValueAt(paramName_flagVisible, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagVisible = i;
    i++;

    //setValueAt(paramName_paramGenericBase, i,0, EDITABLE);
    //setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_paramGenericBase = i;
    //i++;
    
  }

  @Override
  public void setFromInteractionData(SELM_Interaction interaction_in) {
    interaction = interaction_in;
    setFromInteractionData();
  }

  @Override
  public void setFromInteractionData() {

    SELM_Interaction_LAMMPS_CUSTOM1 interactionData
      = (SELM_Interaction_LAMMPS_CUSTOM1)interaction;

    TableData_LagrangianList lagrangianI1_Names;    

    int colValue = 1;

    if (interaction !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_InteractionName][colValue]         = interactionData.InteractionName;
      dataEditable[paramIndex_InteractionName][colValue] = EDITABLE;

      data[paramIndex_InteractionTypeStr][colValue]         = interactionData.InteractionTypeStr;
      dataEditable[paramIndex_InteractionTypeStr][colValue] = NOT_EDITABLE;

      data[paramIndex_pairList_ptI1][colValue]         = interactionData.getMemberList_ptI1();
      dataEditable[paramIndex_pairList_ptI1][colValue] = EDITABLE;

      lagrangianI1_Names = new TableData_LagrangianList(interactionData.getMemberList_lagrangianI1());
      lagrangianI1_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;
      
      data[paramIndex_pairList_lagrangianI1][colValue]         = lagrangianI1_Names;
      dataEditable[paramIndex_pairList_lagrangianI1][colValue] = EDITABLE;
           
      data[paramIndex_plotColor][colValue]         = new TableData_Color(interactionData.getRenderColor());
      dataEditable[paramIndex_plotColor][colValue] = EDITABLE;

      data[paramIndex_flagVisible][colValue]         = (Boolean) interactionData.getRenderVisible();
      dataEditable[paramIndex_flagVisible][colValue] = EDITABLE;

      /* setup generic parameters */
      ArrayList paramList = interactionData.getParameterDataList();

      int paramI = paramIndex_paramGenericBase;

      boolean flagFireCellUpdate = false;

      Object[] objList = paramList.toArray();

      for (int k = 0; k < objList.length; k++) {
        int col = 0;

        SELM_Interaction_LAMMPS_CUSTOM1.parameterData paramData
          = (SELM_Interaction_LAMMPS_CUSTOM1.parameterData) objList[k];

        /* display param name */
        col = 0;
        setValueAt(paramData.getNameStr(), paramI, col, EDITABLE, flagFireCellUpdate);

        /* display param value */
        if (paramData.getData() != null) {

          if (paramData.getTypeStr().equals(SELM_Interaction_LAMMPS_CUSTOM1.parameterData.TYPE_STRING)) {
            col = 1;
            setValueAt(paramData.getData(), paramI, col, EDITABLE, flagFireCellUpdate);
          } else {
            col = 1;
            setValueAt("(unknown data type)", paramI, col, NOT_EDITABLE, flagFireCellUpdate);
          }

        } else {
          col = 1;
          setValueAt("(null)", paramI, col, NOT_EDITABLE, flagFireCellUpdate);
        }
        
        /* display edit button */
        col = 2;
        setValueAt(new TableData_EditorButton(), paramI, col, EDITABLE, flagFireCellUpdate);
        
        paramI++;
      } /* end k loop */

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

   SELM_Interaction_LAMMPS_CUSTOM1 interactionData
      = (SELM_Interaction_LAMMPS_CUSTOM1)interaction;

    //if (interactionData == null) { /* setup data structure, if not present yet */
    //  interactionData = new SELM_Interaction_LAMMPS_CUSTOM1();
    //  interaction     = (SELM_Interaction) interactionData;
    //}

    String   InteractionName = (String) getValueAt(paramIndex_InteractionName,colValue);    
    int[]    pairList_ptI1   = (int[])  getValueAt(paramIndex_pairList_ptI1,colValue);

    TableData_LagrangianList data_lagrangianI1 = (TableData_LagrangianList)getValueAt(paramIndex_pairList_lagrangianI1,colValue);

    SELM_Lagrangian[]    pairList_lagrangianI1   = data_lagrangianI1.lagrangianList;
        
    Color    plotColor      = ((TableData_Color) getValueAt(paramIndex_plotColor,colValue)).color;
    boolean  flagVisible    = (Boolean)  getValueAt(paramIndex_flagVisible,colValue);
       
    interactionData.InteractionName = InteractionName;
    //interactionData.setBondStyle(paramGeneric1);    
    interactionData.setMemberData(pairList_ptI1.length,
                                  pairList_lagrangianI1, pairList_ptI1);

    interactionData.setRenderColor(plotColor);
    interactionData.setRenderVisible(flagVisible);

    /* set the parameter list */
    int paramI = this.paramIndex_paramGenericBase;
    ArrayList paramList = interactionData.getParameterDataList();
    paramList.clear(); 
    for (int k = 0; k < (maxRowUsed - paramIndex_paramGenericBase) + 1; k++) {

      int col;

      SELM_Interaction_LAMMPS_CUSTOM1.parameterData paramData = interactionData.new parameterData();
      
      col = 0;
      paramData.setNameStr((String)getValueAt(paramI,col));
            
      col = 1;
      paramData.setTypeStr(SELM_Interaction_LAMMPS_CUSTOM1.parameterData.TYPE_STRING);
      Object data = getValueAt(paramI,col);

      if (String.class.isInstance(data)) {
        paramData.setData(data);
      } else {
        paramData.setData(null); /* indicates type was unrecognized */
      }
          
      paramList.add(paramData);
      
      paramI++;
      
    } /* end of k loop */

    interactionData.setParameterDataList(paramList);
              
  }

  @Override
  public SELM_Interaction getInteractionDataFromModel() {
    setInteractionDataFromModel();
    return super.getInteractionDataFromModel();
  }


  public void deleteParamDataSelectedRows(int[] rowsSelected) {

    ArrayList paramList = ((SELM_Interaction_LAMMPS_CUSTOM1) interaction).getParameterDataList();

    for (int k = 0; k < rowsSelected.length; k++) {
      int I = rowsSelected[k] - paramIndex_paramGenericBase;
      int rowI = rowsSelected[k];
      int col;
      boolean flagFireCellUpdate = false;
      
      if (I >= 0) {
        paramList.remove(I);

        col = 0;
        setValueAt(null, rowI, col, NOT_EDITABLE, flagFireCellUpdate);

        col = 1;
        setValueAt(null, rowI, col, NOT_EDITABLE, flagFireCellUpdate);
      }

      this.maxRowUsed--;
    }
    
    ((SELM_Interaction_LAMMPS_CUSTOM1) interaction).setParameterDataList(paramList);

    /* update the table */
    setFromInteractionData();
       
  }

    
}

