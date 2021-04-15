/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default_XML_Handler;
import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_SkipNextTag;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Handles reading and writing of project files.  The XML standard is used for all parameter files.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class application_Project_Atz_XML_DataHandler_LAMMPS_USER_SELM implements Atz_XML_SAX_DataHandlerInterface {

  String     xmlString     = "";
  Attributes xmlAttributes = null;

  application_SharedData applSharedData;

  application_Project_Atz_XML_DataHandler_LAMMPS_USER_SELM(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;
  }

  public static void writeProjectFiles(String basePath, String baseFilename, application_SharedData applSharedData_in) throws InvalidObjectException, Exception {

    int I;
    int count;

    /* Determine if appropriate LAMMPS data structures have been setup,
     * otherwise issue an error message if incompatible types are used.
     * This is especially relevant for the Eulerian DOF, Integrator.
     *     
     * We could enfore the LAMMPS data structure compatibility by requiring
     * any Eulerian, Lagrangian, CouplingOp, and Interaction to implement
     * a LAMMPS specific "interface."   This interface would allow us to
     * generate the required data files for LAMMPS simulations.
     *
     */


    try {
    /* == Get the current Eulerian type and check compatibility. */
    SELM_Eulerian[] eulerianDOFList        = applSharedData_in.jTable_MainData.getEulerianList();
    int             eulerianIndexSelected  = applSharedData_in.jTable_MainData.getEulerianIndexSelected();
    SELM_Eulerian   eulerian               = eulerianDOFList[eulerianIndexSelected];
    
    boolean         flagEulerianCompatible = SELM_EulerianInterface_LAMMPS.class.isInstance(eulerian);
    SELM_EulerianInterface_LAMMPS eulerian_LAMMPS = null;

    if (flagEulerianCompatible) {
      eulerian_LAMMPS = (SELM_EulerianInterface_LAMMPS) eulerian;
    } else {      
      throw new InvalidObjectException("The currently selected Eulerian type is not compatible with LAMMPS.");
    }

    /* == Get the current Integrator type and check compatibility. */
    SELM_Integrator[] integratorDOFList               = applSharedData_in.jTable_MainData.getIntegratorList();
    int               integratorIndexSelected         = applSharedData_in.jTable_MainData.getIntegratorIndexSelected();
    SELM_Integrator   integrator                      = integratorDOFList[integratorIndexSelected];

    boolean         flagIntegratorCompatible          = SELM_IntegratorInterface_LAMMPS.class.isInstance(integrator);
    SELM_IntegratorInterface_LAMMPS integrator_LAMMPS = null;

    if (flagIntegratorCompatible) {
      integrator_LAMMPS = (SELM_IntegratorInterface_LAMMPS) integrator;
    } else {
      throw new InvalidObjectException("The currently selected Integrator type is not compatible with LAMMPS.");
    }


    /* == Get the Lagrangians which are compatible with the LAMMPS type.
     * WARNING: We assume only one Lagrangian data structure is present for
     * LAMMPS data at this time.
     */
    SELM_Lagrangian[] lagrangianDOFList               = applSharedData_in.jTable_MainData.getLagrangianList();
    SELM_LagrangianInterface_LAMMPS lagrangian_LAMMPS = null;
    count = 0;
    for (int k = 0; k < lagrangianDOFList.length; k++) {
      SELM_Lagrangian   lagrangian                      = lagrangianDOFList[k];

      boolean         flagLagrangianCompatible          = SELM_LagrangianInterface_LAMMPS.class.isInstance(lagrangian);
      
      if (flagLagrangianCompatible) {
        lagrangian_LAMMPS = (SELM_LagrangianInterface_LAMMPS) lagrangian;
        count++;
      } else {
        System.out.println("LagrangianName = " + lagrangian.LagrangianName);
        throw new InvalidObjectException("Some of the Lagrangian types are not compatible with LAMMPS.");
        //System.out.println("Some Lagrangian types are not campit")
      }
      
    }

    int numLagrangians = count;
    SELM_LagrangianInterface_LAMMPS[] lagrangianList_LAMMPS = new SELM_LagrangianInterface_LAMMPS[numLagrangians];
    I = 0;
    for (int k = 0; k < lagrangianDOFList.length; k++) {
      SELM_Lagrangian   lagrangian                      = lagrangianDOFList[k];

      boolean         flagLagrangianCompatible          = SELM_LagrangianInterface_LAMMPS.class.isInstance(lagrangian);

      if (flagLagrangianCompatible) {
        lagrangian_LAMMPS = (SELM_LagrangianInterface_LAMMPS) lagrangian;
        lagrangianList_LAMMPS[I] = lagrangian_LAMMPS;
        I++;
      } else {
        System.out.println("LagrangianName = " + lagrangian.LagrangianName);
        throw new InvalidObjectException("Some of the Lagrangian types are not compatible with LAMMPS.");
      }

    }

    /* == Get the Interactions which are compatible with the LAMMPS type.
     * WARNING: We assume only one Interaction data structure is present for
     * LAMMPS data at this time.
     */
    SELM_Interaction[] interactionList                  = applSharedData_in.jTable_MainData.getInteractionList();
    SELM_InteractionInterface_LAMMPS interaction_LAMMPS = null;
    count = 0;
    for (int k = 0; k < interactionList.length; k++) {
      SELM_Interaction   interaction                     = interactionList[k];

      boolean         flagInteractionCompatible          = SELM_InteractionInterface_LAMMPS.class.isInstance(interaction);

      if (flagInteractionCompatible) {
        interaction_LAMMPS = (SELM_InteractionInterface_LAMMPS) interaction;
        count++;
      } else {
        System.out.println("InteractionName = " + interaction.InteractionName);
        System.out.println("InteractionTypeStr = " + interaction.InteractionTypeStr);
        throw new InvalidObjectException("Some of the Interaction types are not compatible with LAMMPS.");
        //System.out.println("Some Interaction types are not campit")
      }

    }

    int numInteractions = count;
    SELM_InteractionInterface_LAMMPS[] interactionList_LAMMPS = new SELM_InteractionInterface_LAMMPS[numInteractions];
    I = 0;
    for (int k = 0; k < interactionList.length; k++) {
      SELM_Interaction   interaction                      = interactionList[k];

      boolean         flagInteractionCompatible          = SELM_InteractionInterface_LAMMPS.class.isInstance(interaction);

      if (flagInteractionCompatible) {
        interaction_LAMMPS        = (SELM_InteractionInterface_LAMMPS) interaction;
        interactionList_LAMMPS[I] = interaction_LAMMPS;
        I++;
      } else {
        System.out.println("InteractionName = " + interaction.InteractionName);
        throw new InvalidObjectException("Some of the Interaction types are not compatible with LAMMPS.");
      }

    }
    
    /* == Generate the data files for the LAMMPS simulation codes */

    /* -- if the directory does not exist then create it */
    
    // makes a dir, the parent dir's must already exist.
    boolean success = (new File(basePath)).mkdir();
    if (!success) {
      /* failed to create the directory, issue error message. */
    }
    
    /* -- write the SELM XML data files */
    writeProjectToXLMFile(basePath, baseFilename, applSharedData_in);
    
    /* write atom LAMMPS specific files for data types */
    writeLAMMPS_read_data_File(basePath, baseFilename, applSharedData_in,
                               eulerian_LAMMPS, lagrangianList_LAMMPS, interactionList_LAMMPS);

    /* write the interaction data files */
    //writeLAMMPS_read_data_File(basePath, baseFilename, applSharedData_in,
    //                           eulerian_LAMMPS, lagrangian_LAMMPS);

    /* write/generate LAMMPS script for the simulation run */
    writeProjectLAMMPS_script(basePath, baseFilename, applSharedData_in, eulerian_LAMMPS, integrator_LAMMPS, interactionList_LAMMPS);

    } catch (Exception e) {
      throw e;
    }
       
  }

  public void writeProjectToXLMFile(String basePath, String baseFilename) {
    writeProjectToXLMFile(basePath, baseFilename, applSharedData);
  }

  public static void writeProjectToXLMFile(String basePath, String baseFilename, application_SharedData applSharedData_in) {

    try {

      boolean status = new File(basePath).mkdir();

      String filename = basePath + baseFilename + ".SELM_params";

      FileWriter fstream = new FileWriter(filename);
      BufferedWriter fid = new BufferedWriter(fstream);

      Atz_XML_Helper.writeXMLHeader(fid, "1.0", "UTF-8");

      Atz_XML_Helper.writeXMLNewline(fid);

      Atz_XML_Helper.writeXMLComment(fid, "\n"
                                        + "=======================================================\n"
                                        + "  Parameter file for SELM codes integrated with LAMMPS.\n"
                                        + "  These data files complement the LAMMPS scripts to    \n"
                                        + "  provide model data.                                  \n"
                                        + "                                                       \n"
                                        + "  Generated by SELM Builder                            \n"
                                        + "                                                       \n"
                                        + "  Author: Paul J. Atzberger                            \n"
                                        + "                                                       \n"
                                        + "  More information: http://atzberger.org/              \n"
                                        + "=======================================================\n");
      
      Atz_XML_Helper.writeXMLNewline(fid);

      /* == write start tag for the project */
      Atz_XML_Helper.writeXMLStartTag(fid, "FixSELM");

      Atz_XML_Helper.writeXMLNewline(fid);

      /* == Save the data in jTable_MainData */

      JTable_MainData_XML_LAMMPS_USER_SELM jTable_MainData_XML_LAMMPS_USER_SELM
          = new JTable_MainData_XML_LAMMPS_USER_SELM(applSharedData_in.jTable_MainData);
      jTable_MainData_XML_LAMMPS_USER_SELM.setFlagGenLAMMPS_XML_Files(true); /* signal this is LAMMPS export */
      jTable_MainData_XML_LAMMPS_USER_SELM.setSimulationDataPathname(basePath);
      jTable_MainData_XML_LAMMPS_USER_SELM.exportToXML(fid);

      Atz_XML_Helper.writeXMLNewline(fid);

      /* == write final tag for SELM_Builder */
      Atz_XML_Helper.writeXMLEndTag(fid, "FixSELM");

      //Close the file
      fid.close();

    } catch (Exception e) {//Catch exception if any      
      System.err.println(e);
      e.printStackTrace();
    }

  }

  public static void writeProjectLAMMPS_script(String basePath, String baseFilename, application_SharedData applSharedData_in,
                                               SELM_EulerianInterface_LAMMPS eulerian_LAMMPS, SELM_IntegratorInterface_LAMMPS integrator_LAMMPS,
                                               SELM_InteractionInterface_LAMMPS[] interactionList_LAMMPS) {

    try {
      
      boolean status = new File(basePath).mkdir();
      
      double[] domainBox = eulerian_LAMMPS.getDomainBox();
      double   bondCommLength = -1;

      String filename = basePath + baseFilename + ".LAMMPS_script";

      FileWriter fstream = new FileWriter(filename);
      BufferedWriter fid = new BufferedWriter(fstream);


      /* collect the bond information */
      HashMap bondInfo = extractBondInfo(interactionList_LAMMPS);
      ArrayList<String> bondInfoStyleList = (ArrayList<String>) bondInfo.get("bondInfoStyleList");

      /* collect the angle information */
      HashMap angleInfo = extractAngleInfo(interactionList_LAMMPS);
      ArrayList<String> angleInfoStyleList = (ArrayList<String>) angleInfo.get("angleInfoStyleList");
      
      fid.write("# =========================================================================\n"
              + "# LAMMPS main parameter file and script                                    \n"
              + "#                                                                          \n"
              + "# Generated using the SELM Builder GUI by Paul J. Atzberger.               \n"
              + "#                                                                          \n"
              + "# =========================================================================\n");
      
      fid.write("\n");
      fid.write("# == Setup variables for the script \n");
      fid.write("\n");
      fid.write("variable dumpfreq         equal    " + integrator_LAMMPS.getDumpFreq() + "\n");
      fid.write("variable restart          equal    0\n");
      fid.write("variable neighborSkinDist equal    1.0 # distance for bins beyond force cut-off (1.0 = 1.0 Ang for units = si) \n");
      fid.write("variable baseFilename     universe " + baseFilename +"\n");
      //fid.write("variable basePath     universe " + basePath +"\n");
      fid.write("\n");
      fid.write("# == Setup the log file\n");
      fid.write("log         ${baseFilename}.LAMMPS_logFile\n");
      fid.write("\n");
      
      fid.write("# == Setup style of the run\n");
      fid.write("\n");

      fid.write("# type of units to use in the simulation\n");
      fid.write("units       si\n");  /* note that si ensures that all forces, energy, etc... follow natural derived units (no conversion factors) */
      fid.write("\n");

      fid.write("# indicates possible types allowed for interactions between the atoms\n");
      fid.write("atom_style  angle \n");
      fid.write("\n");

      fid.write("# indicates possible types allowed for bonds between the atoms \n");
      if (bondInfoStyleList.size() > 0) {
        fid.write("bond_style  hybrid ");
        /* @@@ WARNING: Need to make the names that appear below unique!!! */
        Set bondTypeSet = new HashSet();
        for (int k = 0; k < bondInfoStyleList.size(); k++) {
          //fid.write(bondInfoStyleList.get(k) + " ");
          bondTypeSet.add(bondInfoStyleList.get(k));
        }
        Iterator it = bondTypeSet.iterator();
        while (it.hasNext()) {
          // Get element
          String bond_type_str = (String) it.next();
          fid.write(bond_type_str + " ");
        }
        //fid.write(bondInfoStyleList.get(k) + " ");
      } else {
        fid.write("bond_style none \n");
      }
      fid.write("\n");
      
      fid.write("\n");
      
      fid.write("# we use comm_modify for ghost atoms to handle periodic boundary conditions and bonded interactions that cross boundaries. \n");      
      // @@@ PJA: Can probably optimize the bondCommLength based on actual bond properties
      bondCommLength = java.lang.Math.sqrt(domainBox[0]*domainBox[0] + domainBox[1]*domainBox[1] + domainBox[2]*domainBox[2]);
      fid.write("comm_modify mode single cutoff " + bondCommLength + " vel yes");  
      fid.write("\n");

      fid.write("\n");
      
      fid.write("# indicates possible types allowed for bond angles between the atoms \n");
      if (angleInfoStyleList.size() > 0) {
        fid.write("angle_style  hybrid ");
        for (int k = 0; k < angleInfoStyleList.size(); k++) {
          fid.write(angleInfoStyleList.get(k) + " ");
        }
      } else {
        fid.write("angle_style none \n");
      }
      fid.write("\n");
      
      fid.write("\n");
     
      //fid.write("boundary p p p                   # indicates periodic boundary conditions in each direction\n");

      String[] boundaryTypes = eulerian_LAMMPS.getDomainBoundaryTypes();
      fid.write("# indicates type of boundary conditions in each direction (p = periodic) \n");
      fid.write("boundary ");
      for (int i = 0; i < boundaryTypes.length; i++) {
        fid.write(boundaryTypes[i] + " ");
      }
      fid.write("\n");
      fid.write("\n");
      
      fid.write("read_data ${baseFilename}.LAMMPS_read_data # file of atomic coordinates and topology\n");
      fid.write("velocity all zero linear                   # initialize all atomic velocities initially to zero\n");
      fid.write("\n");
      //fid.write("pair_coeff 1 1 ${baseFilename}_HARMONIC_BOND.LAMMPS_pair_coeff_table_linear HARMONIC_BOND 7.0000\n");

      fid.write("# == Interactions \n"); /* deal with special LAMMPS_COEFF style interactions below */

      int numInteractionsRecorded = 0;
      for (int I = 0; I < interactionList_LAMMPS.length; I++) {

        SELM_InteractionInterface_LAMMPS interaction_LAMMPS_generic = interactionList_LAMMPS[I];

        if (SELM_InteractionInterface_LAMMPS_PAIR_STYLE_TABLE.class.isInstance(interaction_LAMMPS_generic)) {

          SELM_InteractionInterface_LAMMPS_PAIR_STYLE_TABLE interaction_LAMMPS
            = (SELM_InteractionInterface_LAMMPS_PAIR_STYLE_TABLE) interaction_LAMMPS_generic;

          int[] pairTypeListI1 = interaction_LAMMPS.getPairListTypeI1();
          int[] pairTypeListI2 = interaction_LAMMPS.getPairListTypeI2();

          String tableFilename = interaction_LAMMPS.getTableFilename();

          String energyEntryName = interaction_LAMMPS.getEnergyEntryName();
          //String annotTableFilename   = "_" + interaction_LAMMPS.getInteractionName() + "_" + energyEntryName + ".LAMMPS_pair_coeff_table_linear";
          //String newTableFilename     = baseFilename + annotTableFilename;
          //String newTableFullFilename = basePath + newTableFilename;

          String newTableFilename = generateInteractionTableFilename(interaction_LAMMPS);
          String newTableFullFilename = basePath + newTableFilename;

          double[] coefficientList = interaction_LAMMPS.getCoefficient();
          double coefficient;
          if (coefficientList.length > 0) {
            coefficient = coefficientList[0]; /* assumed constant list */
          } else {
            coefficient = -1.0;
          }

          /* copy the table file to the simulation directory */
          copyTXTFile(tableFilename, newTableFullFilename);

          /* WARNING: Use pair_style hybrid to have multiple types of interactions  */
          /*          LAMMPS seems to require all of the different types to be listed "up front" */
          fid.write("pair_style table linear 10000 # indicates pair interactions stored in LAMMPS as table \n");

          for (int k = 0; k < pairTypeListI1.length; k++) {
            int typeI1 = pairTypeListI1[k];
            int typeI2 = pairTypeListI2[k];

            /* put the entry in the script file */
            fid.write("pair_coeff " + typeI1 + " " + typeI2 + " " + newTableFilename
              + " " + energyEntryName + " "
              + " # InteractionName = " + interaction_LAMMPS.getInteractionName() + "\n");
            /*
            fid.write("pair_coeff " + typeI1 + " " + typeI2 + " " + "${baseFilename}" + annotTableFilename
            + " " + energyEntryName + " "
            + " # InteractionName = " + interaction_LAMMPS.getInteractionName() + "\n");=
             */

          } /* end k loop */

          numInteractionsRecorded++;

        } /* end of TABLE style interaction */

      } /* end I loop */
      
      fid.write("\n");

      if (numInteractionsRecorded == 0) { /* indicates no LAMMPS-based interactions to compute */
        /* indicate no pair wise interactions */
        fid.write("pair_style none \n");
        /* setup sort cut-off explicitly, since no forces to use to set this */
        fid.write("atom_modify sort 1000 ${neighborSkinDist}          # setup sort data explicitly since no interactions to set this data. \n");
      }
      fid.write("\n");
      
      /* give any special bonds information */
      HashMap specialBondInfo = extractSpecialBondsInfo(interactionList_LAMMPS);
      int numSpecialBonds = (Integer) specialBondInfo.get("numSpecialBonds");
      if (numSpecialBonds > 0) {
        double weight1_2 = (Double) specialBondInfo.get("weight1_2");
        double weight1_3 = (Double) specialBondInfo.get("weight1_3");
        double weight1_4 = (Double) specialBondInfo.get("weight1_4");
        fid.write("# modification of pair interactions to reweight interactions separated by 1-2, 1-3, 1-4 bonds \n");
        fid.write("special_bonds " + weight1_2 + " " + weight1_3 + " " + weight1_4 + " \n");
        fid.write("\n");      
      }
            
      fid.write("# == Setup neighbor list distance\n");
      fid.write("neighbor ${neighborSkinDist} bin                   # first number gives a distance beyond the force cut-off ${neighborSkinDist}\n");
      fid.write("\n");
      fid.write("# == Setup the SELM integrator\n");
      fid.write("fix 1 all SELM ${baseFilename}.SELM_params\n");
      fid.write("\n");
      fid.write("# == Setup output data write to disk\n");
      fid.write("dump        1 all dcd ${dumpfreq} ${baseFilename}_LAMMPS_atomCoords.dcd\n");
      fid.write("dump_modify 1 unwrap yes                   # indicates for periodic domains that unwrapped coordinate should be given\n");
      fid.write("\n");

      fid.write("# == Run the simulation\n");    
      fid.write("timestep " + integrator_LAMMPS.getTimeStep() + "\n");
      fid.write("run      " + integrator_LAMMPS.getNumberTimeSteps() + " upto\n");
      fid.write("\n");
      
      fid.write("# == Write restart data\n");
      fid.write("write_restart ${baseFilename}.LAMMPS_restart_data\n");
      fid.write("\n");
      
      //Close the file
      fid.close();

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

    
  }


  public void readProjectFromXLMFile(String filename) {
    readProjectFromXLMFile(filename, applSharedData);
  }

  public static void readProjectFromXLMFile(String filename, application_SharedData applSharedData) {

    int N;

    /* get a factory for parsing */
    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {

      /* get a new instance of parser */
      SAXParser sp = spf.newSAXParser();

      /* parse the file and also register this class for call backs  */
      sp.parse(filename, new Atz_XML_SAX_DataHandler(new application_Project_Atz_XML_DataHandler_LAMMPS_USER_SELM(applSharedData)));

    } catch (SAXException se) {
      se.printStackTrace();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (IOException ie) {
      ie.printStackTrace();
    }

    /* once data is read, make sure panels are all in sync correctly */
    N = applSharedData.jPanel_Eulerian_DOF_list.length;
    for (int k = 0; k < N; k++) {
      SELM_Eulerian   eulerian      = applSharedData.jPanel_Eulerian_DOF_list[k].getData();
      SELM_Eulerian[] eulerianList  = applSharedData.jTable_MainData.getEulerianList();
      if (eulerian != null) {
        /* find corresponding eulerian data and set the panel */
        for (int j = 0; j < eulerianList.length; j++) {
          if (eulerianList[j] != null) {
            if (eulerianList[j].EulerianTypeStr.equals(eulerian.EulerianTypeStr)) { /* find matching types */
              applSharedData.jPanel_Eulerian_DOF_list[k].setData(eulerianList[j]);
            }
          }
        } /* end j loop */
      } /* end eulerian != null */
    } /* end k loop */

    /* once data is read, make sure panels are all in sync correctly */
    N = applSharedData.jPanel_Integrator_list.length;
    for (int k = 0; k < N; k++) {
      SELM_Integrator   integrator      = applSharedData.jPanel_Integrator_list[k].getData();
      SELM_Integrator[] integratorList  = applSharedData.jTable_MainData.getIntegratorList();
      if (integrator != null) {
        /* find corresponding integrator data and set the panel */
        for (int j = 0; j < integratorList.length; j++) {
          if (integratorList[j] != null) {
            if (integratorList[j].IntegratorTypeStr.equals(integrator.IntegratorTypeStr)) { /* find matching types */
              applSharedData.jPanel_Integrator_list[k].setData(integratorList[j]);
            }
          }
        } /* end j loop */
      } /* end integrator != null */
    } /* end k loop */

    
    /* link the Interactions by name */
    SELM_Interaction[] interactionList = applSharedData.jTable_MainData.getInteractionList();
    SELM_Interaction   interaction;
    SELM_Lagrangian[]  lagrangianList  = applSharedData.jTable_MainData.getLagrangianList();
    N = interactionList.length;
    for (int k = 0; k < N; k++) {
      interaction = interactionList[k];
      interaction.setupLagrangianFromList(lagrangianList);
    }

  }

  public static void writeLAMMPS_read_data_File(String basePath, String baseFilename, application_SharedData applSharedData_in,
                                                SELM_EulerianInterface_LAMMPS eulerian_LAMMPS, SELM_LagrangianInterface_LAMMPS[] lagrangianList_LAMMPS, SELM_InteractionInterface_LAMMPS[] interactionList_LAMMPS) throws Exception {

      boolean status = new File(basePath).mkdir();

      String filename = basePath + baseFilename + ".LAMMPS_read_data";

      FileWriter fstream = new FileWriter(filename);
      BufferedWriter fid = new BufferedWriter(fstream);
      
      /* -- Get the required information to define the model */
      SELM_LagrangianInterface_LAMMPS lagrangian_LAMMPS;

      double[] domainBox = eulerian_LAMMPS.getDomainBox();
      
      int numAtoms = 0;
      for (int k = 0; k < lagrangianList_LAMMPS.length; k++) {        
        lagrangian_LAMMPS = lagrangianList_LAMMPS[k];
        int num_dim       = lagrangian_LAMMPS.getNumDim();
        double[] ptsX     = lagrangian_LAMMPS.getPtsX();
        numAtoms         += ptsX.length/num_dim;
      }

      lagrangian_LAMMPS                   = lagrangianList_LAMMPS[0];
      int              num_dim            = lagrangian_LAMMPS.getNumDim();
      int[]            atomID_all         = new int[numAtoms];
      int[]            moleculeID_all     = new int[numAtoms];
      int[]            typeID_all         = new int[numAtoms];
      double[]         atomMass_all       = new double[numAtoms];
      double[]         ptsX_all           = new double[numAtoms*num_dim];
      HashSet<Integer> typeID_set_all     = new HashSet<Integer>();

      int I = 0;
      for (int k = 0; k < lagrangianList_LAMMPS.length; k++) {
        lagrangian_LAMMPS      = lagrangianList_LAMMPS[k];
        int[]    atomID        = lagrangian_LAMMPS.getAtomID();
        int[]    moleculeID    = lagrangian_LAMMPS.getMoleculeID();
        int[]    typeID        = lagrangian_LAMMPS.getTypeID();
        double[] atomMass      = lagrangian_LAMMPS.getAtomMass();
        double[] ptsX          = lagrangian_LAMMPS.getPtsX();
        for (int j = 0; j < atomID.length; j++) {
          atomID_all[I]     = atomID[j];
          moleculeID_all[I] = moleculeID[j];
          typeID_all[I]     = typeID[j];
          typeID_set_all.add(typeID[j]); /* add to set of types */
          atomMass_all[I]   = atomMass[j];
          for (int d = 0; d < num_dim; d++) {
            ptsX_all[I*num_dim + d] = ptsX[j*num_dim + d];
          }
          I++;
        } /* end j loop */
        
      } /* end k loop */

      /* get the bond related data */
      HashMap bondInfo = extractBondInfo(interactionList_LAMMPS);
      int numBonds = (Integer) bondInfo.get("numBonds");
            
      ArrayList<String>   bondInfoStyleList = (ArrayList<String>)bondInfo.get("bondInfoStyleList");
      ArrayList<Integer>  bondInfoTypeList  = (ArrayList<Integer>)bondInfo.get("bondInfoTypeList");
      ArrayList<String>   bondInfoCoeffList = (ArrayList<String>)bondInfo.get("bondInfoCoeffList");
      
      ArrayList<Integer>  bondTypeIDList    = (ArrayList<Integer>)bondInfo.get("bondTypeIDList");
      ArrayList<Integer>  bondAtomID1List   = (ArrayList<Integer>)bondInfo.get("bondAtomID1List");
      ArrayList<Integer>  bondAtomID2List   = (ArrayList<Integer>)bondInfo.get("bondAtomID2List");
                 
      /* get the angle related data */
      HashMap angleInfo = extractAngleInfo(interactionList_LAMMPS);
      int numAngles = (Integer) angleInfo.get("numAngles");

      ArrayList<String>   angleInfoStyleList = (ArrayList<String>)angleInfo.get("angleInfoStyleList");
      ArrayList<Integer>  angleInfoTypeList  = (ArrayList<Integer>)angleInfo.get("angleInfoTypeList");
      ArrayList<String>   angleInfoCoeffList = (ArrayList<String>)angleInfo.get("angleInfoCoeffList");

      ArrayList<Integer>  angleTypeIDList    = (ArrayList<Integer>)angleInfo.get("angleTypeIDList");
      ArrayList<Integer>  angleAtomID1List   = (ArrayList<Integer>)angleInfo.get("angleAtomID1List");
      ArrayList<Integer>  angleAtomID2List   = (ArrayList<Integer>)angleInfo.get("angleAtomID2List");
      ArrayList<Integer>  angleAtomID3List   = (ArrayList<Integer>)angleInfo.get("angleAtomID3List");
                  
      /* -- write the data */
      fid.write("# =========================================================================\n"
              + "# LAMMPS file for 'read_data' command                                      \n"
              + "#                                                                          \n"
              + "# Generated using SELM_Builder GUI by Paul J. Atzberger.                   \n"
              + "#                                                                          \n"
              + "# =========================================================================\n");

      fid.write("\n");
      fid.write("\n");

      fid.write("# =========================================================================\n");
      fid.write("# Description:\n");
      fid.write("# -------------------------------------------------------------------------\n");
      fid.write("# \n");
      for (int k = 0; k < lagrangianList_LAMMPS.length; k++) {
        lagrangian_LAMMPS = lagrangianList_LAMMPS[k];
        fid.write("# SELM_Lagrangian = " + lagrangian_LAMMPS.getClass().getSimpleName() + "\n");
        fid.write("# LagrangianName = " + lagrangian_LAMMPS.getLagrangianName() + "\n");
        fid.write("# LagrangianTypeStr = " + lagrangian_LAMMPS.getLagrangianTypeStr() + "\n");
        fid.write("# \n");
      }
      fid.write("# SELM_Eulerian   = " + eulerian_LAMMPS.getClass().getSimpleName() + "\n");
      fid.write("#\n");
      fid.write("# atom_type = angle_type\n");
      fid.write("#\n");
      fid.write("# =========================================================================\n");

      fid.write("\n");
      fid.write("\n");

      fid.write("# =========================================================================\n");
      fid.write("# Header information:\n");
      fid.write("# -------------------------------------------------------------------------\n");

      fid.write(numAtoms + " atoms\n");  ;      
      fid.write(numBonds + " bonds\n");     
      if (numAngles > 0) { // PJA: only write output if have bond angles
        fid.write(numAngles + " angles\n");
      }
      
      fid.write("\n");

      /* Assumes Lagrangian DOF each have a unique associated type */
      int numUniqueAtomTypes = typeID_set_all.size();      
      if (lagrangianList_LAMMPS.length != numUniqueAtomTypes) {
        String message = "The atom type specified for each Lagrangian DOF needs to be unique.  Assign an atom number that is different for each Lagrangian DOF. ";
        throw new Exception(message);
      }
      int numAtomTypes = lagrangianList_LAMMPS.length;
      fid.write(numAtomTypes + " atom types\n");
      
      int numBondStyles = bondInfoStyleList.size();
      fid.write(numBondStyles + " bond types\n");

      int numAngleStyles = angleInfoStyleList.size();
      if (numAngleStyles > 0) { // PJA: only write output if have bond angles      
        fid.write(numAngleStyles + " angle types\n");
      }
      
      fid.write("# =========================================================================\n");

      fid.write("\n");
      fid.write("\n");

      fid.write("# =========================================================================\n");
      fid.write("# Domain Size Specification:\n");
      fid.write("# -------------------------------------------------------------------------\n");     
      fid.write(domainBox[0] + " " + domainBox[1] + " xlo xhi\n");
      fid.write(domainBox[2] + " " + domainBox[3] + " ylo yhi\n");
      fid.write(domainBox[4] + " " + domainBox[5] + " zlo zhi\n");
      if (domainBox.length > 6) { /* tilt factors giving a triclinic box (sheared box) */
        fid.write(domainBox[6] + " " + domainBox[7] + " " + domainBox[8] + " xy xz yz\n");
      }
      fid.write("# =========================================================================\n");

      fid.write("\n");
      fid.write("\n");

      fid.write("# =========================================================================\n");
      fid.write("# Mass Specification:\n");
      fid.write("#\n");
      fid.write("# Gives for each atom the following:\n");
      fid.write("#    type-ID | mass\n");
      fid.write("# -------------------------------------------------------------------------\n");
      fid.write("# Atom Location Specification:\n");
      fid.write("#\n");
      fid.write("# Gives for atom angle_type the following:\n");
      fid.write("#    atom-ID | molecule-ID | type-ID | x | y | z\n");
      fid.write("# -------------------------------------------------------------------------\n");
      fid.write("# Bond Specification:\n");
      fid.write("#\n");
      fid.write("# Gives for atom angle_type the following:\n");
      fid.write("#    bond-ID | type-ID | atom1-ID | atom2-ID\n");
      fid.write("# -------------------------------------------------------------------------\n");
      fid.write("# Angle Specification:\n");
      fid.write("#\n");
      fid.write("# Gives for atom angle_type the following:\n");
      fid.write("#    angle-ID | type-ID | atom1-ID | atom2-ID | atom3-ID\n");
      fid.write("# -------------------------------------------------------------------------\n");
      fid.write("# WARNING: atom-ID, type-ID, molecule-ID must be between 1 - N             \n");
      fid.write("# -------------------------------------------------------------------------\n");

      fid.write("Masses\n");
      fid.write("\n");
      int last_type_ID = -1;
      for (int i = 0; i < lagrangianList_LAMMPS.length; i++) {
        int    type_ID   = lagrangianList_LAMMPS[i].getTypeID()[0];
        double atom_Mass = lagrangianList_LAMMPS[i].getAtomMass()[0];        
        fid.write(type_ID + " " + atom_Mass + "\n");
        if (type_ID <= 0) {
          System.out.println("writeLAMMPS_read_data_File() : WARNING: Atom Type-ID must be positive");
        }        
        //fid.write("1 1.000000\n");
      }

      fid.write("\n");

      fid.write("Atoms\n");
      fid.write("\n");
      for (int i = 0; i < numAtoms; i++) {

        int atom_ID      = atomID_all[i];
        int molecule_ID  = moleculeID_all[i];
        int type_ID      = typeID_all[i];

        fid.write(atom_ID + " " + molecule_ID + " " + type_ID + " "
                + ptsX_all[i*num_dim + 0] + " "
                + ptsX_all[i*num_dim + 1] + " "
                + ptsX_all[i*num_dim + 2] + " "
                + "\n");

        if (type_ID <= 0) {
          System.out.println("writeLAMMPS_read_data_File() : WARNING: Atom Type-ID must be positive");
        }

        if (atom_ID <= 0) {
          System.out.println("writeLAMMPS_read_data_File() : WARNING: Atom-ID must be positive");
        }

        if (molecule_ID <= 0) {
          System.out.println("writeLAMMPS_read_data_File() : WARNING: Molecule-ID must be positive");
        }

        //fid.write("2 2 1 2.500000 0.000000 0.000000 \n");
      }

      fid.write("\n");

      if (numBonds > 0) {
        fid.write("Bond Coeffs\n");
        fid.write("\n");

        for (int i = 0; i < numBondStyles; i++) {

          int type_ID = bondInfoTypeList.get(i);
          String bondStyleStr = bondInfoStyleList.get(i);
          String bondCoeffStr = bondInfoCoeffList.get(i);

          fid.write(type_ID + " " + bondStyleStr + " " + bondCoeffStr + "\n");

          if (type_ID <= 0) {
            System.out.println("writeLAMMPS_read_data_File() : WARNING: Bond Type-ID must be positive");
          }
        }

        fid.write("\n");
        
      }
      
      fid.write("Bonds\n");
      fid.write("\n");
      for (int i = 0; i < numBonds; i++) {

        int bond_ID      = i + 1;
        int type_ID      = bondTypeIDList.get(i);
        int atom_ID1     = bondAtomID1List.get(i);
        int atom_ID2     = bondAtomID2List.get(i);
        
        fid.write(bond_ID + " " + type_ID + " " + atom_ID1 + " " + atom_ID2 + "\n");       
      }

      fid.write("\n");

      if (numAngles > 0) {
        
        fid.write("Angle Coeffs\n");
        fid.write("\n");

        for (int i = 0; i < numAngleStyles; i++) {

          int type_ID          = angleInfoTypeList.get(i);
          String angleStyleStr = angleInfoStyleList.get(i);
          String angleCoeffStr = angleInfoCoeffList.get(i);

          fid.write(type_ID + " " + angleStyleStr + " " + angleCoeffStr + "\n");

          if (type_ID <= 0) {
            System.out.println("writeLAMMPS_read_data_File() : WARNING: Angle Type-ID must be positive");
          }
        }

        fid.write("\n");
        
      }

      if (numAngles > 0) { // PJA: only write output if have bond angles
        fid.write("Angles\n");
        fid.write("\n");
        for (int i = 0; i < numAngles; i++) {

          int angle_ID      = i + 1;
        
          int type_ID      = angleTypeIDList.get(i);

          int atom_ID1     = angleAtomID1List.get(i);
          int atom_ID2     = angleAtomID2List.get(i);
          int atom_ID3     = angleAtomID3List.get(i);

          fid.write(angle_ID + " " + type_ID + " " + atom_ID1 + " " + atom_ID2 + " " + atom_ID3 + "\n");
        }
      }

      fid.write("\n");

      //Close the file
      fid.close();
   
  }

  /* ==================== XML parser ====================== */
  @Override
  public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    /* nothing to do */
  }

  @Override
  public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    /* nothing to do */
  }

  //Event Handlers
  @Override
  public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;

    if (qName.equals("SELM_Builder_Project")) { /* starting tag */
      JTable_MainData_XML_SELM_Builder jTable_MainData_XML_SELM_Builder
          = new JTable_MainData_XML_SELM_Builder(applSharedData.jTable_MainData);
      sourceHandler.parseNextTagWithDataHandler(jTable_MainData_XML_SELM_Builder); /* have jTable_MainData parse itself */
    } else if (qName.equals("SELM_Builder_Preferences")) { /* starting tag */
      /* nothing special to do, just do not skip the next tag */
    } else if (qName.equals("jTable_Preferences_Rendering")) { /* preference tag */
      TableModel_Properties1_Default model = (TableModel_Properties1_Default) applSharedData.jTable_Preferences_Rendering.getModel();
      sourceHandler.parseNextTagWithDataHandler(new TableModel_Properties1_Default_XML_Handler(model)); /* have jTable_Preferences parse itself */
    } else if (qName.equals("jTable_Preferences_Other")) { /* preference tag */
      TableModel_Properties1_Default model = (TableModel_Properties1_Default) applSharedData.jTable_Preferences_Other.getModel();
      sourceHandler.parseNextTagWithDataHandler(new TableModel_Properties1_Default_XML_Handler(model)); /* have jTable_Preferences parse itself */
    } else { /* skip the next tag */
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_SkipNextTag()); 
    }
    
  }

  
  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
    //xmlString = new String(ch, start, length); /* WARNING: could come in indefinite chunk sizes */
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals("SELM_Builder_Project")) {
      
    }

  }

  @Override
  public Object XML_getData() {
    return applSharedData;  /* WARNING: does not perform cloning, so not list safe */
  }

  private static void copyTXTFile(String filenameIn, String filenameOut) throws FileNotFoundException, IOException {

    File inputFile  = new File(filenameIn);
    File outputFile = new File(filenameOut);

    FileReader in   = new FileReader(inputFile);
    FileWriter out  = new FileWriter(outputFile);

    int c;

    while ((c = in.read()) != -1)
      out.write(c);

    in.close();
    out.close();

  }

  public static String generateInteractionTableFilename(SELM_InteractionInterface_LAMMPS_PAIR_STYLE_TABLE interaction_LAMMPS) {
    return generateInteractionTableFilename(interaction_LAMMPS.getInteractionName(), interaction_LAMMPS.getEnergyEntryName());
  }

  public static String generateInteractionTableFilename(String interactionName, String energyEntryName) {

    String name = interactionName;

    name = name.replace(" ", "_");
    name = name.replace(".", "");
    name = name.replace("@", "");
    name = name.replace("#", "");

    String annotTableFilename   = name + "_" + energyEntryName + ".LAMMPS_pair_coeff_table_linear";
    String newTableFilename     = annotTableFilename;
    
    return newTableFilename;
  }

  public static HashMap extractBondInfo(SELM_InteractionInterface_LAMMPS interactionList_LAMMPS[]) {

    HashMap bondInfo = new HashMap();

    /* get the bond related data */
    int I = 0;

      ArrayList<String>   bondInfoStyleList = new ArrayList();
      ArrayList<Integer>  bondInfoTypeList  = new ArrayList();
      ArrayList<String>   bondInfoCoeffList = new ArrayList();

      ArrayList<Integer>  bondTypeIDList    = new ArrayList();
      ArrayList<Integer>  bondAtomID1List   = new ArrayList();
      ArrayList<Integer>  bondAtomID2List   = new ArrayList();

      for (int index = 0; index < interactionList_LAMMPS.length; index++) {
        SELM_InteractionInterface_LAMMPS interaction_LAMMPS_generic = interactionList_LAMMPS[index];

        if (SELM_InteractionInterface_LAMMPS_BONDS.class.isInstance(interaction_LAMMPS_generic)) {

          SELM_InteractionInterface_LAMMPS_BONDS interaction_LAMMPS
            = (SELM_InteractionInterface_LAMMPS_BONDS) interaction_LAMMPS_generic;

          /* record the bond information about the style */
          int bondTypeID = interaction_LAMMPS.getBondTypeID();
          bondInfoStyleList.add(interaction_LAMMPS.getBondStyle());
          bondInfoTypeList.add(bondTypeID);
          bondInfoCoeffList.add(interaction_LAMMPS.getBondCoeffsStr());

          /* collect the atom indices for this specific bond type */
          SELM_LagrangianInterface_LAMMPS[] lagrangian1 = interaction_LAMMPS.getPairListLagrangian1();
          int[]                             ptsI1       = interaction_LAMMPS.getPairListI1();

          SELM_LagrangianInterface_LAMMPS[] lagrangian2 = interaction_LAMMPS.getPairListLagrangian2();
          int[]                             ptsI2       = interaction_LAMMPS.getPairListI2();

          for (int k = 0; k < ptsI1.length; k++) {
            bondTypeIDList.add(bondTypeID);

            int atom_ID1 = lagrangian1[k].getAtomID()[ptsI1[k]]; /* get the IDs of the referenced atoms */
            int atom_ID2 = lagrangian2[k].getAtomID()[ptsI2[k]];

            bondAtomID1List.add(atom_ID1);
            bondAtomID2List.add(atom_ID2);
          }

        } /* end BOND style interaction */

      } /* end of index loop */

      int numBonds      = bondAtomID1List.size();

      /* return the data */
      bondInfo.put("numBonds", (Integer) numBonds);
      bondInfo.put("bondInfoStyleList", bondInfoStyleList);
      bondInfo.put("bondInfoTypeList", bondInfoTypeList);
      bondInfo.put("bondInfoCoeffList", bondInfoCoeffList);

      bondInfo.put("bondTypeIDList", bondTypeIDList);
      bondInfo.put("bondAtomID1List", bondAtomID1List);
      bondInfo.put("bondAtomID2List", bondAtomID2List);

      return bondInfo;

  }


  public static HashMap extractAngleInfo(SELM_InteractionInterface_LAMMPS interactionList_LAMMPS[]) {

    HashMap angleInfo = new HashMap();

    /* get the bond related data */
    int I = 0;

    I = 0;

    ArrayList<String> angleInfoStyleList = new ArrayList();
    ArrayList<Integer> angleInfoTypeList = new ArrayList();
    ArrayList<String> angleInfoCoeffList = new ArrayList();

    ArrayList<Integer> angleTypeIDList = new ArrayList();
    ArrayList<Integer> angleAtomID1List = new ArrayList();
    ArrayList<Integer> angleAtomID2List = new ArrayList();
    ArrayList<Integer> angleAtomID3List = new ArrayList();

    for (int index = 0; index < interactionList_LAMMPS.length; index++) {
      SELM_InteractionInterface_LAMMPS interaction_LAMMPS_generic = interactionList_LAMMPS[index];

      if (SELM_InteractionInterface_LAMMPS_ANGLES.class.isInstance(interaction_LAMMPS_generic)) {

        SELM_InteractionInterface_LAMMPS_ANGLES interaction_LAMMPS = (SELM_InteractionInterface_LAMMPS_ANGLES) interaction_LAMMPS_generic;

        /* record the angle information about the style */
        int angleTypeID = interaction_LAMMPS.getAngleTypeID();
        angleInfoStyleList.add(interaction_LAMMPS.getAngleStyle());
        angleInfoTypeList.add(angleTypeID);
        angleInfoCoeffList.add(interaction_LAMMPS.getAngleCoeffsStr());

        /* collect the atom indices for this specific angle type */
        SELM_LagrangianInterface_LAMMPS[] lagrangian1 = interaction_LAMMPS.getAngleListLagrangian1();
        int[] ptsI1 = interaction_LAMMPS.getAngleListI1();

        SELM_LagrangianInterface_LAMMPS[] lagrangian2 = interaction_LAMMPS.getAngleListLagrangian2();
        int[] ptsI2 = interaction_LAMMPS.getAngleListI2();

        SELM_LagrangianInterface_LAMMPS[] lagrangian3 = interaction_LAMMPS.getAngleListLagrangian3();
        int[] ptsI3 = interaction_LAMMPS.getAngleListI3();

        for (int k = 0; k < ptsI1.length; k++) {
          angleTypeIDList.add(angleTypeID);

          int atom_ID1 = lagrangian1[k].getAtomID()[ptsI1[k]]; /* get the IDs of the referenced atoms */
          int atom_ID2 = lagrangian2[k].getAtomID()[ptsI2[k]];
          int atom_ID3 = lagrangian3[k].getAtomID()[ptsI3[k]];

          angleAtomID1List.add(atom_ID1);
          angleAtomID2List.add(atom_ID2);
          angleAtomID3List.add(atom_ID3);
        }

      } /* end ANGLE style interaction */

    } /* end of index loop */

    int numAngles = angleAtomID1List.size();

    /* return the data */
    angleInfo.put("numAngles", (Integer) numAngles);
    
    angleInfo.put("angleInfoStyleList", angleInfoStyleList);
    angleInfo.put("angleInfoTypeList",  angleInfoTypeList);
    angleInfo.put("angleInfoCoeffList", angleInfoCoeffList);

    angleInfo.put("angleTypeIDList",  angleTypeIDList);
    angleInfo.put("angleAtomID1List", angleAtomID1List);
    angleInfo.put("angleAtomID2List", angleAtomID2List);
    angleInfo.put("angleAtomID3List", angleAtomID3List);

    return angleInfo;
    
  }

  public static HashMap extractSpecialBondsInfo(SELM_InteractionInterface_LAMMPS interactionList_LAMMPS[]) {

    HashMap specialBondInfo = new HashMap();

    int    I = 0;
    int    numSpecialBonds = 0;
    double weight1_2 = -1.0;
    double weight1_3 = -1.0;
    double weight1_4 = -1.0;
    
    /* assumes only one SPECIAL_BONDS present */
    for (int index = 0; index < interactionList_LAMMPS.length; index++) {
      SELM_InteractionInterface_LAMMPS interaction_LAMMPS_generic = interactionList_LAMMPS[index];

      if (SELM_Interaction_LAMMPS_SPECIAL_BONDS.class.isInstance(interaction_LAMMPS_generic)) {

        SELM_Interaction_LAMMPS_SPECIAL_BONDS interaction_LAMMPS = (SELM_Interaction_LAMMPS_SPECIAL_BONDS) interaction_LAMMPS_generic;

        /* record the specialBond information about the style */
        weight1_2 = interaction_LAMMPS.weight1_2;
        weight1_3 = interaction_LAMMPS.weight1_3;
        weight1_4 = interaction_LAMMPS.weight1_4;

        I++;
        
      } /* end ANGLE style interaction */

    } /* end of index loop */

    numSpecialBonds = I;

    if (I > 1) {
      System.out.print("WARNING: extractSpecialBondsInfo() : We assume only one SPECIAL_BONDS specified.  We only record the last one specified.");
    }
    
    /* return the data */
    specialBondInfo.put("numSpecialBonds", (Integer) numSpecialBonds);

    specialBondInfo.put("weight1_2", (Double) weight1_2);
    specialBondInfo.put("weight1_3", (Double) weight1_3);
    specialBondInfo.put("weight1_4", (Double) weight1_4);

    return specialBondInfo;

  }

  
}
