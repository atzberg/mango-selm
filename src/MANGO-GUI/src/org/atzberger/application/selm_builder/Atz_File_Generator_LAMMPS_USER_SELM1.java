package org.atzberger.application.selm_builder;

import java.util.HashMap;

/**
 *
 * Generates parameter and simulation files for LAMMPS and the USER-SELM package.
 * This includes a script file for running the LAMMPS simulation and XML data files
 * for the SELM packages.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_File_Generator_LAMMPS_USER_SELM1 extends Atz_File_Generator {
  
  application_SharedData applSharedData = null;
    
  HashMap generationValues = null;
              
  public Atz_File_Generator_LAMMPS_USER_SELM1() {
    
  }
  
  public Atz_File_Generator_LAMMPS_USER_SELM1(application_SharedData applSharedData_in) {
    applSharedData                = applSharedData_in;  
    generationValues = new HashMap();
    generationValues.put("pathName","");
    generationValues.put("flag1",new Boolean(false));
  }
    
  public void generateFilesWithUserInput() {
    
    //    /* -- Show message -- */
//    JDialog_Message_Generate_LAMMPS_USER_SELM jDialog
//      = new JDialog_Message_Generate_LAMMPS_USER_SELM(applSharedData.FrameView_Application_Main.getFrame(), false);
//
//    /* setup the responses to the dialog */
//    jDialog.addWindowListener(new WindowAdapter() {
//
//      @Override
//      public void windowClosing(WindowEvent we) {
//        JDialog jDialog = (JDialog) we.getSource();
//        jDialog.setVisible(false);
//      }
//    });
    
    //jDialog.setVisible(true);

    //JDialog_Generate_Simulation_Data_LAMMPS jDialog_Generate_Simulation_Data_LAMMPS;

    /* -- Show the simulation data dialog -- */    
    JDialog_Generate_Simulation_Data_LAMMPS jDialog_Generate_Simulation_Data_LAMMPS
        = new JDialog_Generate_Simulation_Data_LAMMPS(generationValues);

    jDialog_Generate_Simulation_Data_LAMMPS.setVisible(true);

    /* save the data modifications */
    generationValues
        = (HashMap) jDialog_Generate_Simulation_Data_LAMMPS.getDataValue();

    if (jDialog_Generate_Simulation_Data_LAMMPS.wasActivatedToGenerateData()) {
      generateFiles();    
    }

    /* -- Close the message dialog -- */
    //jDialog.setVisible(false);
    
  }

  public void generateFiles() {

    /* get file related information */
    String BaseFilename = applSharedData.jTable_MainData.getBaseFilename();
    //String BasePathname = applSharedData.jTable_MainData.getBasePathname();
    String BasePathname = (String) generationValues.get("pathName");

    /* write a message concerning the generation */

    applSharedData.FrameView_Application_Main.issueOutputMessage("\n");
    applSharedData.FrameView_Application_Main.issueOutputMessage("Generating LAMMPS-USER-SELM simulation files. \n");
    applSharedData.FrameView_Application_Main.issueOutputMessage("  Simulation Data Base Path = " + BasePathname + "\n");
    applSharedData.FrameView_Application_Main.issueOutputMessage("  Base Filename = " + BaseFilename + "\n");

    /* -- Write the XML files to disk */
    /* Save all of the data to a project file */
    try {
      /* generate ther simulation data */
      application_Project_Atz_XML_DataHandler_LAMMPS_USER_SELM.writeProjectFiles(BasePathname, BaseFilename, applSharedData);

      applSharedData.FrameView_Application_Main.issueOutputMessage("Completed without any known errors. \n");

    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);

      String message = "An error occured while attempting to generate the parameter files. \n" + e.getMessage();
      applSharedData.FrameView_Application_Main.issueOutputErrorMessage(message);

      applSharedData.FrameView_Application_Main.issueUserErrorMessageWindow(applSharedData.FrameView_Application_Main.getFrame(), message, "Error");

      //throw new InvalidObjectException("The currently selected Eulerian type is not compatible with LAMMPS.");
    }

  }
  
}
