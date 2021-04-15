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
public class TableModel_Integrator extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;

  protected SELM_Integrator integrator = null; /* integrator represented by the table */

  public    String paramName_IntegratorName         = "Name";
  protected int    paramIndex_IntegratorName        = -1;

  public    String paramName_IntegratorTypeStr      = "Type";
  protected int    paramIndex_IntegratorTypeStr     = -1;
  
  public TableModel_Integrator() {
    int i = 0;

    setValueAt(paramName_IntegratorName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_IntegratorName = i;
    i++;

    setValueAt(paramName_IntegratorTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_IntegratorTypeStr = i;
    i++;

  }

  public void setFromIntegratorData(SELM_Integrator integrator_in) {
    integrator = integrator_in;
    setFromIntegratorData();
  }
  
  public void setFromIntegratorData() {

    int colValue = 1;
    Object obj = null;

    if (integrator !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_IntegratorName][colValue]         = integrator.IntegratorName;
      dataEditable[paramIndex_IntegratorName][colValue] = EDITABLE;

      data[paramIndex_IntegratorTypeStr][colValue]         = integrator.IntegratorTypeStr;
      dataEditable[paramIndex_IntegratorTypeStr][colValue] = NOT_EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

    }

  }

  public void setIntegratorDataFromModel() {

   int colValue = 1;

   if (integrator != null) {
     String   IntegratorName       = (String) getValueAt(paramIndex_IntegratorName,colValue);
     String   IntegratorTypeStr    = (String) getValueAt(paramIndex_IntegratorTypeStr,colValue);

     integrator.IntegratorName    = IntegratorName;
     integrator.IntegratorTypeStr = IntegratorTypeStr;
   }

  }

  public SELM_Integrator getIntegratorDataFromModel() {
    setIntegratorDataFromModel();
    return integrator;
  }
  
}
