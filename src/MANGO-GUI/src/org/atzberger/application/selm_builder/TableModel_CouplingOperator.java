/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class TableModel_CouplingOperator extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;

  protected SELM_CouplingOperator couplingOp = null; /* couplingOp represented by the table */

  public    String paramName_CouplingOpName         = "Name";
  protected int    paramIndex_CouplingOpName        = -1;

  public    String paramName_CouplingOpTypeStr      = "Type";
  protected int    paramIndex_CouplingOpTypeStr     = -1;
  
  public TableModel_CouplingOperator() {
    int i = 0;

    setValueAt(paramName_CouplingOpName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_CouplingOpName = i;
    i++;

    setValueAt(paramName_CouplingOpTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, NOT_EDITABLE);
    paramIndex_CouplingOpTypeStr = i;
    i++;


  }

  public void setFromCouplingOpData(SELM_CouplingOperator couplingOp_in) {
    couplingOp = couplingOp_in;
    setFromCouplingOpData();
  }
  
  public void setFromCouplingOpData() {

    int colValue = 1;
    Object obj = null;

    if (couplingOp !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_CouplingOpName][colValue]         = couplingOp.getName();
      dataEditable[paramIndex_CouplingOpName][colValue] = EDITABLE;

      data[paramIndex_CouplingOpTypeStr][colValue]         = couplingOp.getTypeStr();
      dataEditable[paramIndex_CouplingOpTypeStr][colValue] = NOT_EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

    }
       
  }

  public void setCouplingOpDataFromModel() {

   int colValue = 1;

   if (couplingOp != null) {
     String   CouplingOperatorName       = (String) getValueAt(paramIndex_CouplingOpName,colValue);
     String   CouplingOperatorTypeStr    = (String) getValueAt(paramIndex_CouplingOpTypeStr,colValue);

     couplingOp.CouplingOpName    = CouplingOperatorName;
     couplingOp.CouplingOpTypeStr = CouplingOperatorTypeStr;
   }

  }

  public SELM_CouplingOperator getCouplingOpDataFromModel() {
    setCouplingOpDataFromModel();
    return couplingOp;
  }
  
}
