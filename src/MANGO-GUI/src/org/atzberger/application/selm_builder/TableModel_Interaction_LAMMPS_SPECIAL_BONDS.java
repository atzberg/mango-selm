package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Units_Double;
import org.atzberger.mango.units.Atz_UnitsData;
import org.atzberger.mango.units.Atz_UnitsRef;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Interaction_LAMMPS_SPECIAL_BONDS extends TableModel_Interaction {

  private final static boolean DEBUG = false;

  Atz_UnitsRef atz_unitsRef = null;
  
  public    String paramName_weight1_2    = "Weight Pair 1-2 Bond ";
  protected int    paramIndex_weight1_2   = -1;

  public    String paramName_weight1_3    = "Weight Pair 1-3 Bond";
  protected int    paramIndex_weight1_3   = -1;

  public    String paramName_weight1_4    = "Weight Pair 1-4 Bond";
  protected int    paramIndex_weight1_4   = -1;
  
  public TableModel_Interaction_LAMMPS_SPECIAL_BONDS() {
    this(null,null);
  }
  
  public TableModel_Interaction_LAMMPS_SPECIAL_BONDS(SELM_Interaction_LAMMPS_SPECIAL_BONDS interaction) {
    this(interaction,null);
  }
    
  public TableModel_Interaction_LAMMPS_SPECIAL_BONDS(SELM_Interaction_LAMMPS_SPECIAL_BONDS interaction_in, Atz_UnitsRef atz_unitsRef_in) {

    atz_unitsRef = atz_unitsRef_in;

    setupFirstThirdColumns();
    setupTableDataPrototypes();

    if (interaction_in == null) {
      interaction = new SELM_Interaction_LAMMPS_SPECIAL_BONDS();
    } else {
      interaction = interaction_in;
    }

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

    setValueAt(paramName_weight1_2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_weight1_2 = i;
    i++;

    setValueAt(paramName_weight1_3, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_weight1_3 = i;
    i++;

    setValueAt(paramName_weight1_4, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_weight1_4 = i;
    i++;
        
  }


  public void setupTableDataPrototypes() {
    int colValue = 1;

    /* set all fields at once and then fire edited event */
    data[paramIndex_InteractionName][colValue]         = "";
    dataEditable[paramIndex_InteractionName][colValue] = NOT_EDITABLE;

    data[paramIndex_InteractionTypeStr][colValue]         = "";
    dataEditable[paramIndex_InteractionTypeStr][colValue] = EDITABLE;

    data[paramIndex_weight1_2][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("", atz_unitsRef));
    dataEditable[paramIndex_weight1_2][colValue]    = EDITABLE;

    data[paramIndex_weight1_3][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("", atz_unitsRef));
    dataEditable[paramIndex_weight1_2][colValue]    = EDITABLE;

    data[paramIndex_weight1_4][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("", atz_unitsRef));
    dataEditable[paramIndex_weight1_2][colValue]    = EDITABLE;
    
  }


  @Override
  public void setFromInteractionData(SELM_Interaction interaction_in) {
    interaction = interaction_in;
    setFromInteractionData();
  }

  @Override
  public void setFromInteractionData() {

    SELM_Interaction_LAMMPS_SPECIAL_BONDS interactionData
      = (SELM_Interaction_LAMMPS_SPECIAL_BONDS)interaction;
    
    int colValue = 1;

    if (interaction !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_InteractionName][colValue]         = interactionData.InteractionName;
      dataEditable[paramIndex_InteractionName][colValue] = EDITABLE;

      data[paramIndex_InteractionTypeStr][colValue]         = interactionData.InteractionTypeStr;
      dataEditable[paramIndex_InteractionTypeStr][colValue] = NOT_EDITABLE;

      ((TableData_Units_Double)data[paramIndex_weight1_2][colValue]).setValue(interactionData.weight1_2);
      dataEditable[paramIndex_weight1_2][colValue] = EDITABLE;

      ((TableData_Units_Double)data[paramIndex_weight1_3][colValue]).setValue(interactionData.weight1_3);
      dataEditable[paramIndex_weight1_3][colValue] = EDITABLE;

      ((TableData_Units_Double)data[paramIndex_weight1_4][colValue]).setValue(interactionData.weight1_4);
      dataEditable[paramIndex_weight1_4][colValue] = EDITABLE;
      
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

   SELM_Interaction_LAMMPS_SPECIAL_BONDS interactionData
      = (SELM_Interaction_LAMMPS_SPECIAL_BONDS)interaction;

    //if (interactionData == null) { /* setup data structure, if not present yet */
    //  interactionData = new SELM_Interaction_LAMMPS_SPECIAL_BONDS();
    //  interaction     = (SELM_Interaction) interactionData;
    //}

    interactionData.InteractionName  = (String) getValueAt(paramIndex_InteractionName, colValue);
    interactionData.weight1_2        = ((TableData_Units_Double)getValueAt(paramIndex_weight1_2, colValue)).getValue();
    interactionData.weight1_3        = ((TableData_Units_Double)getValueAt(paramIndex_weight1_3, colValue)).getValue();
    interactionData.weight1_4        = ((TableData_Units_Double)getValueAt(paramIndex_weight1_4, colValue)).getValue();
    
  }

  @Override
  public SELM_Interaction getInteractionDataFromModel() {
    setInteractionDataFromModel();
    return super.getInteractionDataFromModel();
  }

    
}

