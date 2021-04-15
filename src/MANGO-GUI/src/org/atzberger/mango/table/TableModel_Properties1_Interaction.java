/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.atzberger.mango.table;

import org.atzberger.mango.table.TableData_EditorButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.atzberger.mango.atz3d.JPanel_Model_View_RenderPanel;
import org.atzberger.application.selm_builder.SELM_Interaction;

/**
 *
 * Handles table behaviors in response to user attempts to edit and in response to requests to display the data.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Properties1_Interaction extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;

  protected SELM_Interaction interaction = null; /* interaction represented by the table */

  public    String paramName_InteractionName  = "Interaction Name";
  protected int    paramIndex_InteractionName = -1;

  public    String paramName_InteractionTypeStr  = "Interaction Type";
  protected int    paramIndex_InteractionTypeStr = -1;
  
  public TableModel_Properties1_Interaction() {
    int      i                = 0;
    String[] choices          = null;
    double[] testDoubleArray1 = new double[3];
    double[] dbArray          = null;
    int[]    intArray         = null;
    
    setValueAt("Test 1", i,0);
    setValueAt(new String(), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

  }

  public void setFromInteractionData(SELM_Interaction interaction_in) {
    interaction = interaction_in;
    setFromInteractionData();
  }
  
  public void setFromInteractionData() {

    int colValue = 1;

    if (interaction !=null) {
      /*
      data[paramIndex_InteractionName][colValue] = interaction.InteractionName;
      data[paramIndex_Pairs_I1][colValue]        = interaction.pairList_ptI1;
      data[paramIndex_Pairs_I2][colValue]        = interaction.pairList_ptI2;
      data[paramIndex_RestLength][colValue]      = interaction.restLength;
      data[paramIndex_Stiffness][colValue]       = interaction.stiffnessK;
       */
    }
   
    fireTableDataChanged();

  }

  public void setInteractionDataFromModel() {

    int colValue = 1;

    if (interaction != null) {
      /*
      interaction.InteractionName = (String) data[paramIndex_InteractionName][colValue];
      interaction.pairList_ptI1   = (int[]) data[paramIndex_Pairs_I1][colValue];
      interaction.pairList_ptI2   = (int[]) data[paramIndex_Pairs_I2][colValue];
      interaction.restLength      = (double[]) data[paramIndex_RestLength][colValue];
      interaction.stiffnessK      = (double[]) data[paramIndex_Stiffness][colValue];
       */
    }

  }

  public void setControlPtsRenderPanel(JPanel_Model_View_RenderPanel jPanel_Render_ControlPts_in) {
    //jPanel_Render_ControlPts = jPanel_Render_ControlPts_in;
  }

  public SELM_Interaction getInteractionDataFromModel() {
    return interaction;
  }
  
}
