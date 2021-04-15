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
public class TableModel_Eulerian extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;

  protected SELM_Eulerian eulerian = null; /* eulerian represented by the table */

  public    String paramName_EulerianName         = "Name";
  protected int    paramIndex_EulerianName        = -1;

  public    String paramName_EulerianTypeStr      = "Type";
  protected int    paramIndex_EulerianTypeStr     = -1;
  
  public TableModel_Eulerian() {
    
    int i = 0;

    setValueAt(paramName_EulerianName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_EulerianName = i;
    i++;

    setValueAt(paramName_EulerianTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_EulerianTypeStr = i;
    i++;

  }

  public void setFromEulerianData(SELM_Eulerian eulerian_in) {
    eulerian = eulerian_in;
    setFromEulerianData();
  }
  
  public void setFromEulerianData() {
    
    int colValue = 1;
    Object obj = null;

    if (eulerian !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_EulerianName][colValue]         = eulerian.EulerianName;
      dataEditable[paramIndex_EulerianName][colValue] = EDITABLE;

      data[paramIndex_EulerianTypeStr][colValue]         = eulerian.EulerianTypeStr;
      dataEditable[paramIndex_EulerianTypeStr][colValue] = NOT_EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

    }


  }

  public void setEulerianDataFromModel() {

   int colValue = 1;

   if (eulerian != null) {
     String   EulerianName       = (String) getValueAt(paramIndex_EulerianName,colValue);
     String   EulerianTypeStr    = (String) getValueAt(paramIndex_EulerianTypeStr,colValue);

     eulerian.EulerianName    = EulerianName;
     eulerian.EulerianTypeStr = EulerianTypeStr;
   }

  }

  public SELM_Eulerian getEulerianDataFromModel() {
    setEulerianDataFromModel();
    return eulerian;
  }
  
}
