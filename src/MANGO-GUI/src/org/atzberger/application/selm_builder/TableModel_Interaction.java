package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.mango.table.TableData_EditorButton;
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
public class TableModel_Interaction extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;

  protected SELM_Interaction interaction = null; /* interaction represented by the table */

  public    String paramName_InteractionName         = "Name";
  protected int    paramIndex_InteractionName        = -1;

  public    String paramName_InteractionTypeStr      = "Type";
  protected int    paramIndex_InteractionTypeStr     = -1;
  
  public TableModel_Interaction() {

    int i = 0;

    setValueAt(paramName_InteractionName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_InteractionName = i;
    i++;

    setValueAt(paramName_InteractionTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_InteractionTypeStr = i;
    i++;

  }

  public void setFromInteractionData(SELM_Interaction interaction_in) {
    interaction = interaction_in;
    setFromInteractionData();
  }
  
  public void setFromInteractionData() {
        
    int colValue = 1;
    Object obj = null;

    if (interaction !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_InteractionName][colValue]         = interaction.getInteractionName();
      dataEditable[paramIndex_InteractionName][colValue] = EDITABLE;

      data[paramIndex_InteractionTypeStr][colValue]         = interaction.getInteractionTypeStr();
      dataEditable[paramIndex_InteractionTypeStr][colValue] = NOT_EDITABLE;
      
      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

    }

  }

  public void setInteractionDataFromModel() {

   int colValue = 1;

   if (interaction != null) {
     String   InteractionName       = (String) getValueAt(paramIndex_InteractionName,colValue);
     String   InteractionTypeStr    = (String) getValueAt(paramIndex_InteractionTypeStr,colValue);
        
     interaction.InteractionName    = InteractionName;
     interaction.InteractionTypeStr = InteractionTypeStr;
   }

  }

  public SELM_Interaction getInteractionDataFromModel() {
    setInteractionDataFromModel();
    return interaction;
  }
  
}
