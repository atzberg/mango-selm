

package org.atzberger.application.selm_builder;

import org.atzberger.mango.windowing.JTextPane_Output;
import org.atzberger.mango.atz3d.JPanel_Model_View_RenderPanel;
import org.atzberger.mango.atz3d.JPanel_Model_View_Composite;
import org.atzberger.mango.units.Atz_UnitsRef;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 * Data for organizing information shared between the various classes, dialogs, sub-routines, and Jython / Python.
 */
public class application_SharedData {

  //public Atz_Struct_DataContainer            ApplNamespace;

  public application_Window_Main             FrameView_Application_Main; /* FrameView */

  public Atz_UnitsRef                        atz_UnitsRef; /* reference units for all quantities */

  public JTextPane_Output                  jTextPane_Messages;
          
  public JPanel_Lagrangian[]                 jPanel_Lagrangian_DOF_list;
  public JPanel_Eulerian[]                   jPanel_Eulerian_DOF_list;
  public JPanel_CouplingOperator[]           jPanel_CouplingOp_list;
  public JPanel_Integrator[]                 jPanel_Integrator_list;
  public JPanel_Interaction[]                jPanel_Interaction_list;

  public JTabbedPane                         jPanel_Tabbed_Main;

  public JTable_MainData                     jTable_MainData;
  public JPanel_Model_View_RenderPanel       jPanel_Model_View_RenderPanel;
  public JPanel_Model_View_Composite         jPanel_Model_View_Composite;

  public JTable                              jTable_Preferences_Rendering;
  public JTable                              jTable_Preferences_TableDisplay;
  public JTable                              jTable_Preferences_Other;
  
  public Atz_File_Generator                  atz_File_Generator;
  
  public application_SharedData() {
    
  }

}
