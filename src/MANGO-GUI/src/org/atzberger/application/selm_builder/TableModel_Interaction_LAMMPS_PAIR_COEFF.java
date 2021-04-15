package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_MultipleChoice1;
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
public class TableModel_Interaction_LAMMPS_PAIR_COEFF extends TableModel_Interaction {

  private final static boolean DEBUG = false;

  public    String paramName_tableFilename             = "Table Filename";
  protected int    paramIndex_tableFilename            = -1;

  public    String paramName_energyEntryName           = "Energy Entry Name";
  protected int    paramIndex_energyEntryName          = -1;

  public    String paramName_pairList_typeI1           = "Atom Type I1";
  protected int    paramIndex_pairList_typeI1          = -1;

  public    String paramName_pairList_typeI2           = "Atom Type I2";
  protected int    paramIndex_pairList_typeI2          = -1;

  public    String paramName_pairList_lagrangianI1   = "Lagrangian I1";
  protected int    paramIndex_pairList_lagrangianI1  = -1;

  public    String paramName_pairList_lagrangianI2   = "Lagrangian I2";
  protected int    paramIndex_pairList_lagrangianI2  = -1;

  public    String paramName_coefficient   = "Coefficient";
  protected int    paramIndex_coefficient  = -1;

  public    String paramName_plotColor             = "Color";
  protected int    paramIndex_plotColor            = -1;

  public    String paramName_flagVisible           = "Visible";
  protected int    paramIndex_flagVisible          = -1;

  TableModel_Interaction_LAMMPS_PAIR_COEFF(SELM_Interaction_LAMMPS_PAIR_COEFF interaction) {
    setupFirstThirdColumns();

    setFromInteractionData(interaction);
  }
  
  TableModel_Interaction_LAMMPS_PAIR_COEFF() {
    setupFirstThirdColumns();

    SELM_Interaction_LAMMPS_PAIR_COEFF interaction = new SELM_Interaction_LAMMPS_PAIR_COEFF();
    
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

    setValueAt(paramName_tableFilename, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_tableFilename = i;
    i++;

    setValueAt(paramName_energyEntryName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_energyEntryName = i;
    i++;

    setValueAt(paramName_pairList_lagrangianI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_lagrangianI1 = i;
    i++;

    setValueAt(paramName_pairList_typeI1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_typeI1 = i;
    i++;

    setValueAt(paramName_pairList_lagrangianI2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_lagrangianI2 = i;
    i++;

    setValueAt(paramName_pairList_typeI2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_pairList_typeI2 = i;
    i++;

    setValueAt(paramName_coefficient, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_coefficient = i;
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

    SELM_Interaction_LAMMPS_PAIR_COEFF interactionData
      = (SELM_Interaction_LAMMPS_PAIR_COEFF)interaction;

    TableData_LagrangianList lagrangianI1_Names;
    TableData_LagrangianList lagrangianI2_Names;

    int colValue = 1;
    Object obj = null;

    if (interaction !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_InteractionName][colValue]         = interactionData.InteractionName;
      dataEditable[paramIndex_InteractionName][colValue] = EDITABLE;

      data[paramIndex_InteractionTypeStr][colValue]         = interactionData.InteractionTypeStr;
      dataEditable[paramIndex_InteractionTypeStr][colValue] = NOT_EDITABLE;

      TableData_LAMMPS_pair_coeff_tableFilename data_tableFilename;
      obj = data[paramIndex_tableFilename][colValue];
      if (TableData_LAMMPS_pair_coeff_tableFilename.class.isInstance(obj)) {
        data_tableFilename = (TableData_LAMMPS_pair_coeff_tableFilename) obj;
      } else {
        data_tableFilename = new TableData_LAMMPS_pair_coeff_tableFilename();
      }
      data_tableFilename.setFilename(interactionData.getTableFilename());
      data_tableFilename.determineEnergyEntryNames();
      
      data[paramIndex_tableFilename][colValue]              = data_tableFilename;
      dataEditable[paramIndex_tableFilename][colValue]      = EDITABLE;
      
      
      /*  rebuild the energy entry list  */      
      TableData_MultipleChoice1 data_energyEntryName;
      obj = data[paramIndex_energyEntryName][colValue];
      
      if (TableData_MultipleChoice1.class.isInstance(obj)) {

        data_energyEntryName = (TableData_MultipleChoice1) obj;
        
      } else {
        
        data_energyEntryName = new TableData_MultipleChoice1();
        
      }

      data_energyEntryName.setPossibleValues(data_tableFilename.getEnergyEntryNames());
      data_energyEntryName.selectMatch(interactionData.getEnergyEntryName());
                                    
      data[paramIndex_energyEntryName][colValue]              = data_energyEntryName;
      dataEditable[paramIndex_energyEntryName][colValue]      = EDITABLE;

      data[paramIndex_pairList_typeI1][colValue]         = interactionData.getPairList_typeI1();
      dataEditable[paramIndex_pairList_typeI1][colValue] = EDITABLE;

      data[paramIndex_pairList_typeI2][colValue]         = interactionData.getPairList_typeI2();
      dataEditable[paramIndex_pairList_typeI2][colValue] = EDITABLE;

      lagrangianI1_Names = new TableData_LagrangianList(interactionData.getPairList_lagrangianI1());
      lagrangianI1_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;
      
      data[paramIndex_pairList_lagrangianI1][colValue]         = lagrangianI1_Names;
      dataEditable[paramIndex_pairList_lagrangianI1][colValue] = EDITABLE;

      lagrangianI2_Names = new TableData_LagrangianList(interactionData.getPairList_lagrangianI2());
      lagrangianI2_Names.flagDisplayMode = TableData_LagrangianList.DISPLAY_NAME_LIST;
      data[paramIndex_pairList_lagrangianI2][colValue]         = lagrangianI2_Names;
      dataEditable[paramIndex_pairList_lagrangianI2][colValue] = EDITABLE;

      data[paramIndex_coefficient][colValue]         = interactionData.getCoefficient();
      dataEditable[paramIndex_coefficient][colValue] = EDITABLE;
            
      data[paramIndex_plotColor][colValue]         = new TableData_Color(interactionData.getRenderColor());
      dataEditable[paramIndex_plotColor][colValue] = EDITABLE;

      data[paramIndex_flagVisible][colValue]         = (Boolean) interactionData.getRenderVisible();
      dataEditable[paramIndex_flagVisible][colValue] = EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

      //int      numPairs = InteractionData.getNumPairs();
      /*
      setValueAt(InteractionData.InteractionName,    paramIndex_InteractionName, colValue);
      setValueAt(InteractionData.getPairList_typeI1(), paramIndex_Pairs_I1,        colValue);
      setValueAt(InteractionData.getPairList_typeI2(), paramIndex_Pairs_I2,        colValue);
      setValueAt(interactionData.getRestLength(),    paramIndex_RestLength,      colValue);
      setValueAt(interactionData.getStiffnessK(),    paramIndex_Stiffness,       colValue);
       */

    }
    
  }

  @Override
  public void setInteractionDataFromModel() {

   int colValue = 1;

   SELM_Interaction_LAMMPS_PAIR_COEFF interactionData
      = (SELM_Interaction_LAMMPS_PAIR_COEFF)interaction;

    //if (interactionData == null) { /* setup data structure, if not present yet */
    //  interactionData = new SELM_Interaction_LAMMPS_PAIRS_HARMONIC();
    //  interaction     = (SELM_Interaction) interactionData;
    //}

    String   InteractionName   = (String) getValueAt(paramIndex_InteractionName,colValue);
    int[]    pairList_typeI1   = (int[])  getValueAt(paramIndex_pairList_typeI1,colValue);
    int[]    pairList_typeI2   = (int[])  getValueAt(paramIndex_pairList_typeI2,colValue);

    TableData_LAMMPS_pair_coeff_tableFilename data_tableFilename   = (TableData_LAMMPS_pair_coeff_tableFilename) getValueAt(paramIndex_tableFilename ,colValue);
    TableData_MultipleChoice1                 data_energyEntryName = (TableData_MultipleChoice1) getValueAt(paramIndex_energyEntryName ,colValue);

    TableData_LagrangianList data_lagrangianI1 = (TableData_LagrangianList)getValueAt(paramIndex_pairList_lagrangianI1,colValue);
    TableData_LagrangianList data_lagrangianI2 = (TableData_LagrangianList)getValueAt(paramIndex_pairList_lagrangianI2,colValue);

    SELM_Lagrangian[]    pairList_lagrangianI1   = data_lagrangianI1.lagrangianList;
    SELM_Lagrangian[]    pairList_lagrangianI2   = data_lagrangianI2.lagrangianList;
    
    double[] coefficient      = (double[]) getValueAt(paramIndex_coefficient,colValue);

    Color    plotColor      = ((TableData_Color) getValueAt(paramIndex_plotColor,colValue)).color;
    boolean  flagVisible    = (Boolean)  getValueAt(paramIndex_flagVisible,colValue);
       
    interactionData.InteractionName = InteractionName;
    interactionData.setPairData(pairList_typeI1.length,
                                pairList_lagrangianI1, pairList_typeI1,
                                pairList_lagrangianI2, pairList_typeI2,
                                coefficient);

    interactionData.setTableFilename(data_tableFilename.getFilename());
    interactionData.setEnergyEntryName(data_energyEntryName.getSelectedItem());
    
    interactionData.setRenderColor(plotColor);
    interactionData.setRenderVisible(flagVisible);

  }

  @Override
  public SELM_Interaction getInteractionDataFromModel() {
    setInteractionDataFromModel();
    return super.getInteractionDataFromModel();
  }

    
}

