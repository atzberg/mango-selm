package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableEditor_EditorButton;
import org.atzberger.mango.table.TableData_EditorButton;
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
public class JTable_Preferences_Other extends JTable_Properties1_Default {

  public JTable_Preferences_Other() {
    super();

    setupEditorsAndRenderers();
  }

  public JTable_Preferences_Other(TableModel dm) {
    super(dm, null, null);

    setupEditorsAndRenderers();
  }

  public JTable_Preferences_Other(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
    super(dm, null, null);

    setupEditorsAndRenderers();
  }

  private void setupEditorsAndRenderers() {

    //super.setupDefaultEditorsAndRenderers();

    // == Set up InteractionList data type
    setDefaultRenderer(TableData_InteractionList.class,
                       new TableRenderer_InteractionList());
    setDefaultEditor(TableData_InteractionList.class,
                     new TableEditor_InteractionList());

    // == Set up Editor button behavior

    /* get button already in use in default setup (and modify) */
    TableEditor_EditorButton tableSpecialButtonEditor
            = (TableEditor_EditorButton) getDefaultEditor(TableData_EditorButton.class);

    tableSpecialButtonEditor.addDataType(TableData_InteractionList.class.getName(), new JDialog_Edit_InteractionList());

    /*
    setDefaultEditor(TableData_EditorButton.class,
                     tableSpecialButtonEditor);
     */

  } /* setupDefaultEditorsAndRenderers */


}