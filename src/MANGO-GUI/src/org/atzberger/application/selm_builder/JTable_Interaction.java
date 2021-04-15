package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.JTable_Properties1_Default;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * Table represention of this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTable_Interaction extends JTable_Properties1_Default {

  public JTable_Interaction() {
    super();

    setupEditorsAndRenderers();
  }

  public JTable_Interaction(TableModel dm) {
    super(dm, null, null);

    setupEditorsAndRenderers();
  }

  public JTable_Interaction(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
    super(dm, null, null);

    setupEditorsAndRenderers();
  }


  private void setupEditorsAndRenderers() {

    //super.setupDefaultEditorsAndRenderers();

    /* perform setup for any special data types */

    // == Set up Editor button behavior for any special data types

    /* get button already in use in default setup (and modify) */
    //TableEditor_EditorButton tableSpecialButtonEditor
//            = (TableEditor_EditorButton) getDefaultEditor(TableData_EditorButton.class);

//    tableSpecialButtonEditor.addDataType(TableData_LagrangianList_Names.class.getName(), new JDialog_Edit_LagrangianList_Names());

  } /* setupDefaultEditorsAndRenderers */

}