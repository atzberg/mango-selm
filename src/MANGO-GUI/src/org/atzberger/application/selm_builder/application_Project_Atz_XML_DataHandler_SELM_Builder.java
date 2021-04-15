package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.JPanel_Model_View_Composite_XML_SELM_Builder;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_SkipNextTag;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
public class application_Project_Atz_XML_DataHandler_SELM_Builder implements Atz_XML_SAX_DataHandlerInterface {

  String     xmlString     = "";
  Attributes xmlAttributes = null;

  String     SELM_Builder_Version_of_File = "-1.0";

  static application_SharedData applSharedData;

  application_Project_Atz_XML_DataHandler_SELM_Builder(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;
  }

  static public void writeProjectToXLMFile(String fullFilename, application_SharedData applSharedData_in) {
    String basePath = "";
    String baseFilename = fullFilename;
    writeProjectToXLMFile(basePath, baseFilename, applSharedData_in);
  }

  static public void writeProjectToXLMFile(String fullFilename) {
    String basePath = "";
    String baseFilename = fullFilename;
    writeProjectToXLMFile(basePath, baseFilename, applSharedData);
  }

  static public void writeProjectToXLMFile(String basePath, String baseFilename) {
    writeProjectToXLMFile(basePath, baseFilename, applSharedData);
  }

  static public void writeProjectToXLMFile(String basePath, String baseFilename, application_SharedData applSharedData_in) {

    try {

      boolean status = new File(basePath).mkdir();

      String filename = basePath + baseFilename;

      FileWriter fstream = new FileWriter(filename);
      BufferedWriter fid = new BufferedWriter(fstream);

      Atz_XML_Helper.writeXMLHeader(fid, "1.0", "UTF-8");

      Atz_XML_Helper.writeXMLComment(fid, "\n"
                                        + "=======================================================\n"
                                        + "  Project Data File for SELM Builder                   \n"
                                        + "                                                       \n"
                                        + "  Author: Paul J. Atzberger,                           \n"
                                        + "                                                       \n"
                                        + "  More information: http://atzberger.org/              \n"
                                        + "=======================================================\n");

      Atz_XML_Helper.writeXMLNewline(fid);

      /* == write start tag for the project */
      Atz_XML_Helper.writeXMLStartTag(fid, "SELM_Builder_Project");

      Atz_XML_Helper.writeXMLData(fid, "SELM_Builder_Version", "1.0");

      /* == Save the data in jTable_MainData */
      Atz_XML_Helper.writeXMLNewline(fid);

      JTable_MainData_XML_SELM_Builder jTable_MainData_XML_SELM_Builder
          = new JTable_MainData_XML_SELM_Builder(applSharedData_in.jTable_MainData);
      jTable_MainData_XML_SELM_Builder.exportToXML(fid);

      Atz_XML_Helper.writeXMLNewline(fid);

      /* == Save the state of the JPanel_Model_View_Composite */
      JPanel_Model_View_Composite_XML_SELM_Builder jPanel_Model_View_Composite_XML_SELM_Builder
           = new JPanel_Model_View_Composite_XML_SELM_Builder(applSharedData_in.jPanel_Model_View_Composite);

      jPanel_Model_View_Composite_XML_SELM_Builder.exportToXML(fid);

      Atz_XML_Helper.writeXMLNewline(fid);

      /* == Save the preferences data */
      Atz_XML_Helper.writeXMLStartTag(fid, "SELM_Builder_Preferences");
     
      /* -- Save the preferences rendering */
      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLStartTag(fid, "jTable_Preferences_Rendering");

      TableModel_Preferences_Rendering xmlHandler_Preferences_Rendering 
        = (TableModel_Preferences_Rendering)applSharedData_in.jTable_Preferences_Rendering.getModel();

      xmlHandler_Preferences_Rendering.exportToXML(fid);

      Atz_XML_Helper.writeXMLEndTag(fid, "jTable_Preferences_Rendering");

      /* -- Save the preferences table display */
      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLStartTag(fid, "jTable_Preferences_TableDisplay");

      TableModel_Preferences_TableDisplay xmlHandler_Preferences_TableDisplay
        = (TableModel_Preferences_TableDisplay)applSharedData_in.jTable_Preferences_TableDisplay.getModel();

      xmlHandler_Preferences_TableDisplay.exportToXML(fid);

      Atz_XML_Helper.writeXMLEndTag(fid, "jTable_Preferences_TableDisplay");

      Atz_XML_Helper.writeXMLNewline(fid);
     
      /* -- Save the preferences other */
      Atz_XML_Helper.writeXMLNewline(fid);
      Atz_XML_Helper.writeXMLStartTag(fid, "jTable_Preferences_Other");

      TableModel_Preferences_Other xmlHandler_Preferences_Other
        = (TableModel_Preferences_Other)applSharedData_in.jTable_Preferences_Other.getModel();

      xmlHandler_Preferences_Other.exportToXML(fid);
      
      Atz_XML_Helper.writeXMLEndTag(fid, "jTable_Preferences_Other");

      Atz_XML_Helper.writeXMLNewline(fid);
            
      Atz_XML_Helper.writeXMLEndTag(fid, "SELM_Builder_Preferences");

      /* == write final tag for SELM_Builder */
      Atz_XML_Helper.writeXMLEndTag(fid, "SELM_Builder_Project");

      //Close the file
      fid.close();

    } catch (Exception e) {//Catch exception if any      
      System.out.println(e);
      e.printStackTrace();
      //System.out.println(e.getStackTrace());
      //System.err.println("Error: " + e.getMessage());
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
      sp.parse(filename, new Atz_XML_SAX_DataHandler(new application_Project_Atz_XML_DataHandler_SELM_Builder(applSharedData)));

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

      /* nothing special to do */

    } else if (qName.equals("SELM_Builder_Version")) { /* starting tag */
   
      /* get version information, although we do not store it yet */
      SELM_Builder_Version_of_File = (String) xmlAttributes.getValue("value");

    } else if (qName.equals("JTable_MainData")) { /* starting tag */
      
      /* handle the next tag using the jTable_MainData parser */
      JTable_MainData_XML_SELM_Builder jTable_MainData_XML_SELM_Builder
          = new JTable_MainData_XML_SELM_Builder(applSharedData.jTable_MainData);

      sourceHandler.parseCurrentScopeWithDataHandler(jTable_MainData_XML_SELM_Builder); /* have jTable_MainData parse itself */

    } else if (qName.equals("JPanel_Model_View_Composite")) { /* starting tag */

      /* handle the current tage using jPanel_Model_View_Composite_XML_SELM_Builder */
      JPanel_Model_View_Composite_XML_SELM_Builder jPanel_Model_View_Composite_XML_SELM_Builder
           = new JPanel_Model_View_Composite_XML_SELM_Builder(applSharedData.jPanel_Model_View_Composite);

      sourceHandler.parseCurrentScopeWithDataHandler(jPanel_Model_View_Composite_XML_SELM_Builder);
      
    } else if (qName.equals("SELM_Builder_Preferences")) { /* starting tag */
      /* nothing special to do, just do not skip the next tag */
    } else if (qName.equals("jTable_Preferences_Rendering")) { /* preference tag */
      //TableModel_Properties1_Default model = (TableModel_Properties1_Default) applSharedData.jTable_Preferences_Rendering.getModel();
      //sourceHandler.parseNextTagWithDataHandler(new TableModel_Properties1_Default_XML_Handler(model)); /* have jTable_Preferences parse itself */
      TableModel_Preferences_Rendering model = (TableModel_Preferences_Rendering)applSharedData.jTable_Preferences_Rendering.getModel();
      sourceHandler.parseNextTagWithDataHandler(model);
    } else if (qName.equals("jTable_Preferences_TableDisplay")) { /* preference tag */
      //TableModel_Properties1_Default model = (TableModel_Properties1_Default) applSharedData.jTable_Preferences_TableDisplay.getModel();
      //sourceHandler.parseNextTagWithDataHandler(new TableModel_Properties1_Default_XML_Handler(model)); /* have jTable_Preferences parse itself */
      TableModel_Preferences_TableDisplay model = (TableModel_Preferences_TableDisplay)applSharedData.jTable_Preferences_TableDisplay.getModel();
      sourceHandler.parseNextTagWithDataHandler(model);
    } else if (qName.equals("jTable_Preferences_Other")) { /* preference tag */
      //TableModel_Properties1_Default model = (TableModel_Properties1_Default) applSharedData.jTable_Preferences_Other.getModel();
      //sourceHandler.parseNextTagWithDataHandler(new TableModel_Properties1_Default_XML_Handler(model)); /* have jTable_Preferences parse itself */
      TableModel_Preferences_Other model = (TableModel_Preferences_Other)applSharedData.jTable_Preferences_Other.getModel();
      sourceHandler.parseNextTagWithDataHandler(model);
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
  
}
