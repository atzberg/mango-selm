package org.atzberger.application.selm_builder;

/**
 *
 * Customised data represention for display and editing within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_IntegratorList {

  SELM_Integrator[] integratorList;  /* list of integrator objects */

  int itemSelectedIndex = -1; /* if any of the list items selected */

  static public final int DISPLAY_DEFAULT         = 0;
  static public final int DISPLAY_BRACKET_NAME    = 1;
  static public final int DISPLAY_NAME_LIST       = 2;
  static public final int DISPLAY_MULTIPLE_CHOICE = 3;

  int flagDisplayType = DISPLAY_DEFAULT;

  TableData_IntegratorList() {
      
    integratorList    = new SELM_Integrator[0];
    
  }

  TableData_IntegratorList(SELM_Integrator[] integratorList_in) {
    integratorList = integratorList_in;
  }

  TableData_IntegratorList(SELM_Integrator[] integratorList_in, int itemSelectedIndex_in) {
    integratorList    = integratorList_in;
    itemSelectedIndex = itemSelectedIndex_in;
    flagDisplayType   = DISPLAY_MULTIPLE_CHOICE;
  }

  TableData_IntegratorList(int itemSelectedIndex_in) {
    integratorList                   = new SELM_Integrator[1];
    integratorList[0]                = new SELM_Integrator_SHEAR1();
    integratorList[0].IntegratorName = "SHEAR1";
    itemSelectedIndex                = itemSelectedIndex_in;
    flagDisplayType                  = DISPLAY_MULTIPLE_CHOICE;
  }
  
}
