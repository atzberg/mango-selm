package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableEditor_EditorButton;
import org.atzberger.mango.table.TableData_EditorButton;
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
public class JTable_Interaction_LAMMPS_SPECIAL_BONDS extends JTable_Interaction {

  public JTable_Interaction_LAMMPS_SPECIAL_BONDS() {
    super();

    setupEditorsAndRenderers();
  }

  public JTable_Interaction_LAMMPS_SPECIAL_BONDS(TableModel dm) {
    super(dm, null, null);

    setupEditorsAndRenderers();
  }

  public JTable_Interaction_LAMMPS_SPECIAL_BONDS(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
    super(dm, null, null);

    setupEditorsAndRenderers();
  }


  private void setupEditorsAndRenderers() {

    //super.setupDefaultEditorsAndRenderers();

    /* perform setup for any special data types */

    // == Set up InteractionList data type    
    setDefaultRenderer(TableData_LagrangianList.class,
                       new TableRenderer_LagrangianList());
    setDefaultEditor(TableData_LagrangianList.class,
                     new TableEditor_LagrangianList());


    // == Set up InteractionList data type
    setDefaultRenderer(TableData_LAMMPS_pair_coeff_tableFilename.class,
                       new TableRenderer_LAMMPS_pair_coeff_tableFilename());
    setDefaultEditor(TableData_LAMMPS_pair_coeff_tableFilename.class,
                     new TableEditor_LAMMPS_PAIR_COEFF_tableFilename());

    // == Set up Editor button behavior for any special data types

    /* get button already in use in default setup (and modify) */    
    TableEditor_EditorButton tableSpecialButtonEditor
            = (TableEditor_EditorButton) getDefaultEditor(TableData_EditorButton.class);

    tableSpecialButtonEditor.addDataType(TableData_InteractionList.class.getName(), new JDialog_Edit_InteractionList());
    
  } /* setupDefaultEditorsAndRenderers */

}