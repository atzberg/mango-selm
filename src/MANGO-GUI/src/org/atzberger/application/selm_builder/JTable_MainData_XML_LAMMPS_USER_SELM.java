package org.atzberger.application.selm_builder;


import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

/**
 *
 * Table represention of this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTable_MainData_XML_LAMMPS_USER_SELM         
        implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  JTable_MainData jTable_MainData; /* object to export to XML */
  String          simulationDataPathname = null;

  String      xmlString     = "";
  Attributes  xmlAttributes = null;
  
  String xmlTag_SELM_Run_Description     = "SELM_Run_Description";
  String xmlTag_SELM_Version             = "SELM_Version";
  String xmlTag_SELM_BasePath            = "SELM_BasePath";
  String xmlTag_SELM_BaseFilename        = "SELM_BaseFilename";    
  String xmlTag_SELM_Seed                = "SELM_Seed";

  String xmlTag_SELM_Lagrangian_List     = "SELM_Lagrangian_List";
  String xmlTag_SELM_Lagrangian          = "SELM_Lagrangian";
  String xmlTag_SELM_LagrangianName      = "SELM_LagrangianName";
  String xmlTag_SELM_LagrangianTypeStr   = "SELM_LagrangianTypeStr";
  
  String xmlTag_SELM_Eulerian_List       = "SELM_Eulerian_List";
  String xmlTag_SELM_Eulerian            = "SELM_Eulerian";
  String xmlTag_SELM_EulerianName        = "SELM_EulerianName";
  String xmlTag_SELM_EulerianTypeStr     = "SELM_EulerianTypeStr";

  String xmlTag_SELM_CouplingOperator_List     = "SELM_CouplingOperator_List";
  String xmlTag_SELM_CouplingOperator          = "SELM_CouplingOperator";
  String xmlTag_SELM_CouplingOperatorName      = "SELM_CouplingOperatorName";
  String xmlTag_SELM_CouplingOperatorTypeStr   = "SELM_CouplingOperatorTypeStr";

  String xmlTag_SELM_Interaction_List       = "SELM_Interaction_List";
  String xmlTag_SELM_Interaction            = "SELM_Interaction";
  String xmlTag_SELM_InteractionName        = "SELM_InteractionName";
  String xmlTag_SELM_InteractionTypeStr     = "SELM_InteractionTypeStr";

  String xmlTag_SELM_Integrator          = "SELM_Integrator";
  String xmlTag_SELM_IntegratorName      = "SELM_IntegratorName";
  String xmlTag_SELM_IntegratorTypeStr   = "SELM_IntegratorTypeStr";

  public boolean flag_Gen_LAMMPS_XML_Files = false;
  
  public JTable_MainData_XML_LAMMPS_USER_SELM(JTable_MainData jTable_MainData_in) {
    setObjectJTable_MainData(jTable_MainData_in);
  }

  /* ====================================================================*/
  /* ========================== XML Codes ===============================*/
  public void setObjectJTable_MainData(JTable_MainData jTable_MainData_in) {
    jTable_MainData = jTable_MainData_in;
  }
  
  public void exportToXML(BufferedWriter fid) {

      String className          = jTable_MainData.getClass().getSimpleName();
      TableModel_MainData model = (TableModel_MainData) jTable_MainData.getModel();
      int                   col = 1;
      String attrStr;
      String filename;
            
      /* write the leading parameters */
      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      Integer SELM_Version = 1;
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Version, SELM_Version);
      Atz_XML_Helper.writeXMLNewline(fid);

      String description = (String) model.getValueAt(model.paramIndex_Description, col);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Run_Description, description);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      /* remove base path from data type */
      //TableData_Pathname dataPathname = (TableData_Pathname) model.getValueAt(model.paramIndex_BasePathname, col);
      //Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_BasePath, dataPathname.pathname);

      //if (simulationDataPathname == null) {  /* set by default to basePath if nothing else specified */
      //  simulationDataPathname = dataPathname.pathname;
      //}
      
      String baseFilename = (String) model.getValueAt(model.paramIndex_BaseFilename, col);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_BaseFilename, baseFilename);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      Integer dataSELM_Seed = (Integer) model.getValueAt(model.paramIndex_SELM_Seed, col);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Seed, dataSELM_Seed);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);
            
      /* -- write lagrangian DOF data */
      SELM_Lagrangian[] lagrangianList = jTable_MainData.getLagrangianList();
      attrStr = "numEntries=" + "\"" + lagrangianList.length + "\"";
      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Lagrangian_List, attrStr);  /* @@@ number of entries as attribute */
      Atz_XML_Helper.writeXMLNewline(fid);
      for (int i = 0; i < lagrangianList.length; i++) {
        Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Lagrangian);
        String name    = lagrangianList[i].LagrangianName;
        String typeStr = lagrangianList[i].LagrangianTypeStr;
        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_LagrangianName, name);
        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_LagrangianTypeStr, typeStr);
        Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Lagrangian);
        Atz_XML_Helper.writeXMLNewline(fid);

        /* need to write Lagrangian DOF to separate file */
        filename = simulationDataPathname + name + "." + xmlTag_SELM_Lagrangian;
        lagrangianList[i].exportData(filename, SELM_Lagrangian.FILE_TYPE_XML);
      }      
      Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Lagrangian_List);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      /* -- write eulerian DOF data (only allow one entry for now in the list) */
      SELM_Eulerian[] eulerianList  = jTable_MainData.getEulerianList();
      int numEulerianEntriesToWrite = 1; /* only selected */
      int eulerianSelected          = jTable_MainData.getEulerianIndexSelected();
      SELM_Eulerian eulerian        = eulerianList[eulerianSelected];
      attrStr                       = "numEntries=" + "\"" + numEulerianEntriesToWrite + "\"";
      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Eulerian_List, attrStr);  /* @@@ number of entries as attribute */
      
      Atz_XML_Helper.writeXMLNewline(fid);

      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Eulerian);
      String nameEulerian    = eulerian.EulerianName;
      String typeStrEulerian = eulerian.EulerianTypeStr;
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_EulerianName, nameEulerian);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_EulerianTypeStr, typeStrEulerian);
      Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Eulerian);

      Atz_XML_Helper.writeXMLNewline(fid);

      /* need to write Eulerian DOF to separate file */
      filename = simulationDataPathname + nameEulerian + "." + xmlTag_SELM_Eulerian;
      eulerian.exportData(filename, SELM_Eulerian.FILE_TYPE_XML);
      
      Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Eulerian_List);

//      /* -- write eulerian DOF data */
//      SELM_Eulerian[] eulerianList = jTable_MainData.getEulerianList();
//      attrStr = "numEntries=" + "\"" + eulerianList.length + "\"";
//      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Eulerian_List, attrStr);  /* @@@ number of entries as attribute */
//      Atz_XML_Helper.writeXMLNewline(fid);
//      for (int i = 0; i < eulerianList.length; i++) {
//        Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Eulerian);
//        String name    = eulerianList[i].EulerianName;
//        String typeStr = eulerianList[i].EulerianTypeStr;
//        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_EulerianName, name);
//        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_EulerianTypeStr, typeStr);
//        Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Eulerian);
//        Atz_XML_Helper.writeXMLNewline(fid);
//
//        /* need to write Eulerian DOF to separate file */
//        String filename = simulationDataPathname + name + "." + xmlTag_SELM_Eulerian;
//        eulerianList[i].exportData(filename, SELM_Eulerian.FILE_TYPE_XML);
//      }
//      Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Eulerian_List);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      /* -- write couplingOp DOF data */
      SELM_CouplingOperator[] couplingOpList = jTable_MainData.getCouplingOpList();
      attrStr = "numEntries=" + "\"" + couplingOpList.length + "\"";
      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_CouplingOperator_List, attrStr);  /* @@@ number of entries as attribute */
      Atz_XML_Helper.writeXMLNewline(fid);
      for (int i = 0; i < couplingOpList.length; i++) {
        Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_CouplingOperator);
        String name    = couplingOpList[i].CouplingOpName;
        String typeStr = couplingOpList[i].CouplingOpTypeStr;
        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_CouplingOperatorName, name);
        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_CouplingOperatorTypeStr, typeStr);
        Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_CouplingOperator);
        Atz_XML_Helper.writeXMLNewline(fid);
        /* need to write CouplingOperator DOF to separate file */
        filename = simulationDataPathname + name + "." + xmlTag_SELM_CouplingOperator;
        couplingOpList[i].setFlagGenLAMMPS_XML_Files(flag_Gen_LAMMPS_XML_Files, simulationDataPathname);
        couplingOpList[i].exportData(filename, SELM_CouplingOperator.FILE_TYPE_XML);
      }
      Atz_XML_Helper.writeXMLEndTag(fid, this.xmlTag_SELM_CouplingOperator_List);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      /* -- write interaction DOF data */
      SELM_Interaction[] interactionList = jTable_MainData.getInteractionList();
      attrStr = "numEntries=" + "\"" + interactionList.length + "\"";
      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Interaction_List, attrStr);  /* @@@ number of entries as attribute */
      Atz_XML_Helper.writeXMLNewline(fid);
      for (int i = 0; i < interactionList.length; i++) {
        Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Interaction);
        String name    = interactionList[i].InteractionName;
        String typeStr = interactionList[i].InteractionTypeStr;
        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_InteractionName, name);
        Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_InteractionTypeStr, typeStr);
        Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Interaction);
        Atz_XML_Helper.writeXMLNewline(fid);

        /* need to write Interaction DOF to separate file */
        filename = simulationDataPathname + name + "." + xmlTag_SELM_Interaction;
        if (flag_Gen_LAMMPS_XML_Files = true) { /* signal special behaviors for LAMMPS export when writing XML */
          if (SELM_InteractionInterface_LAMMPS.class.isInstance(interactionList[i])) {
            ((SELM_InteractionInterface_LAMMPS)interactionList[i]).setFlagGenLAMMPS_XML_Files(flag_Gen_LAMMPS_XML_Files);
          }
        }
        interactionList[i].exportData(filename, SELM_Interaction.FILE_TYPE_XML);
      }
      Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Interaction_List);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      /* -- write integrator data (only allow one entry for now in the list) */
      SELM_Integrator[] integratorList  = jTable_MainData.getIntegratorList();
      int numIntegratorEntriesToWrite = 1; /* only selected */
      int integratorSelected          = jTable_MainData.getIntegratorIndexSelected();
      SELM_Integrator integrator        = integratorList[integratorSelected];
      attrStr                       = "numEntries=" + "\"" + numIntegratorEntriesToWrite + "\"";
      //Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Integrator_List, attrStr);  /* @@@ number of entries as attribute */

      Atz_XML_Helper.writeXMLNewline(fid);

      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_SELM_Integrator);
      String nameIntegrator    = integrator.IntegratorName;
      String typeStrIntegrator = integrator.IntegratorTypeStr;
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_IntegratorName, nameIntegrator);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_IntegratorTypeStr, typeStrIntegrator);
      Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Integrator);

      Atz_XML_Helper.writeXMLNewline(fid);

      /* need to write Integrator DOF to separate file */
      filename = simulationDataPathname + nameIntegrator + "." + xmlTag_SELM_Integrator;
      integrator.exportData(filename, SELM_Integrator.FILE_TYPE_XML);

      //Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_SELM_Integrator_List);

      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLComment(fid, "=======================================================");
      Atz_XML_Helper.writeXMLNewline(fid);

      flag_Gen_LAMMPS_XML_Files = false; /* WARNING: reset LAMMPS export flag so next run not effected */
                                                    
  }
  
  public void importDataFromXMLFile(String filename, int flagType) {

    //get a factory
    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {

      /* get a new instance of parser */
      SAXParser sp = spf.newSAXParser();

      /* use local codes to parse content */
      sp.parse(filename, new Atz_XML_SAX_DataHandler(this));
      
    } catch (SAXException se) {
      se.printStackTrace();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }

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

    TableModel_MainData model = (TableModel_MainData) jTable_MainData.getModel();
    int                 col   = 1;

    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;
    
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
    //xmlString = new String(ch, start, length); /* WARNING: could come in indefinite chunk sizes */
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    TableModel_MainData model = (TableModel_MainData) jTable_MainData.getModel();
    int                 col   = 1;

  }

  @Override
  public Object XML_getData() {
    return jTable_MainData; /* WARNING: this is direct object, so not safe for lists */
  }

  public void setSimulationDataPathname(String path) {
    simulationDataPathname = path;
  }

  public String getSimulationDataPathname() {
    String path;

    if (simulationDataPathname == null) {
      path = "";
    } else {
      path = simulationDataPathname;
    }
    
    return simulationDataPathname;
  }

  public void setFlagGenLAMMPS_XML_Files(boolean val) {
    flag_Gen_LAMMPS_XML_Files = val;   /* this value expires after one XML export (turned to false there) */
  }
  
  /* ========================= XML codes ==================== */
  /* ======================================================== */

  
}