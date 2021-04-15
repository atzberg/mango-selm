package org.atzberger.application.selm_builder;

/**
 *
 * Customised data represention for display and editing within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_CouplingOperatorList {

  public SELM_CouplingOperator[] couplingOpList;  /* list of CouplingOp objects */

  public TableData_CouplingOperatorList() {
    
    couplingOpList            = new SELM_CouplingOperator[0];
    
  }
  
}
