package org.atzberger.application.selm_builder;

/**
 *
 * Customised data represention for display and editing within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_EulerianList {

  SELM_Eulerian[] eulerianList;  /* list of eulerian objects */

  int itemSelectedIndex = -1; /* if any of the list items selected */

  static public final int DISPLAY_DEFAULT         = 0;
  static public final int DISPLAY_BRACKET_NAME    = 1;
  static public final int DISPLAY_NAME_LIST       = 2;
  static public final int DISPLAY_MULTIPLE_CHOICE = 3;

  int flagDisplayMode = DISPLAY_DEFAULT;

  public TableData_EulerianList() {      
    eulerianList    = new SELM_Eulerian[0];
  }

  public void setDisplayMode(int mode) {
    flagDisplayMode = mode;
  }

  public int getDisplayMode() {
    return flagDisplayMode;
  }

  public TableData_EulerianList(SELM_Eulerian[] eulerianList_in) {
    eulerianList = eulerianList_in;
  }

  public TableData_EulerianList(SELM_Eulerian[] eulerianList_in, int itemSelectedIndex_in) {
    eulerianList      = eulerianList_in;
    itemSelectedIndex = itemSelectedIndex_in;
    flagDisplayMode   = DISPLAY_MULTIPLE_CHOICE;
  }

  public TableData_EulerianList(int itemSelectedIndex_in) {
    eulerianList      = new SELM_Eulerian[2];
    eulerianList[0]   = new SELM_Eulerian_SHEAR_UNIFORM1_FFTW3();
    eulerianList[0].EulerianName = "Test 1";
    eulerianList[1]   = new SELM_Eulerian_SHEAR_UNIFORM1_FFTW3();
    eulerianList[1].EulerianName = "Test 2";
    itemSelectedIndex = itemSelectedIndex_in;
    flagDisplayMode   = DISPLAY_MULTIPLE_CHOICE;
  }
  
}
