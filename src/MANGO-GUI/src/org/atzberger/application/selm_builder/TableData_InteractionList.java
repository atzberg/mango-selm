package org.atzberger.application.selm_builder;

/**
 *
 * Customised data represention for display and editing within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_InteractionList {

  SELM_Interaction[] interactionList;  /* list of interaction objects */

  public TableData_InteractionList() {
    
    interactionList = new SELM_Interaction[0];
    
  }
  
}
