package org.atzberger.application.selm_builder;

/**
 *
 * Customised data represention for display and editing within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_LagrangianList {

  static public final int DISPLAY_DEFAULT      = 0;
  static public final int DISPLAY_BRACKET_NAME = 1;
  static public final int DISPLAY_NAME_LIST    = 2;
  
  int flagDisplayMode = DISPLAY_DEFAULT;

  SELM_Lagrangian[] lagrangianList;  /* list of lagrangian objects */

  public TableData_LagrangianList() {
 
    lagrangianList    = new SELM_Lagrangian[0];
    
  }

  public TableData_LagrangianList(SELM_Lagrangian[] lagrangianList_in) {
    lagrangianList = lagrangianList_in;
  }

  public void setDisplayMode(int mode) {
    flagDisplayMode = mode;
  }

  public int getDisplayMode() {
    return flagDisplayMode;
  }

  
}
