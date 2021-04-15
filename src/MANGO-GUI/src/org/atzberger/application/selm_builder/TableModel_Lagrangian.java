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
public class TableModel_Lagrangian extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;

  protected SELM_Lagrangian lagrangian = null; /* lagrangian represented by the table */

  public    String paramName_LagrangianName         = "Name";
  protected int    paramIndex_LagrangianName        = -1;

  public    String paramName_LagrangianTypeStr      = "Type";
  protected int    paramIndex_LagrangianTypeStr     = -1;
  
  public TableModel_Lagrangian() {
    
    int i = 0;

    setValueAt(paramName_LagrangianName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_LagrangianName = i;
    i++;

    setValueAt(paramName_LagrangianTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_LagrangianTypeStr = i;
    i++;

  }

  public void setFromLagrangianData(SELM_Lagrangian lagrangian_in) {
    lagrangian = lagrangian_in;
    setFromLagrangianData();
  }
  
  public void setFromLagrangianData() {

    int colValue = 1;
    Object obj = null;

    if (lagrangian !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_LagrangianName][colValue]         = lagrangian.getName();
      dataEditable[paramIndex_LagrangianName][colValue] = EDITABLE;

      data[paramIndex_LagrangianTypeStr][colValue]         = lagrangian.getTypeStr();
      dataEditable[paramIndex_LagrangianTypeStr][colValue] = NOT_EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

    }


  }

  public void setLagrangianDataFromModel() {

   int colValue = 1;

   if (lagrangian != null) {
     String   LagrangianName       = (String) getValueAt(paramIndex_LagrangianName,colValue);
     String   LagrangianTypeStr    = (String) getValueAt(paramIndex_LagrangianTypeStr,colValue);

     lagrangian.LagrangianName    = LagrangianName;
     lagrangian.LagrangianTypeStr = LagrangianTypeStr;
   }

   //System.out.println("TableModel_Lagrangian : setLagrangianDataFromModel ");

  }

  public SELM_Lagrangian getLagrangianDataFromModel() {
    //System.out.println("TableModel_Lagrangian : getLagrangianDataFromModel ");
    setLagrangianDataFromModel();
    return lagrangian;
  }
  
}
