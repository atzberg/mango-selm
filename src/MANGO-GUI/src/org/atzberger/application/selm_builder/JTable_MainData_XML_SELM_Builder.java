package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_Units_Ref;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_SAX_ListDataHandler;
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
public class JTable_MainData_XML_SELM_Builder         
        implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  JTable_MainData jTable_MainData; /* object to export to XML */

  String      xmlString     = "";
  Attributes  xmlAttributes = null;

  String xmlTag_BaseFilename               = "BaseFilename";
  String xmlTag_BasePathname               = "BasePath";
  String xmlTag_Description                = "Description";
  String xmlTag_PhysicalUnits              = "PhysicalUnits";
  String xmlTag_SELM_Seed                  = "SELM_Seed";  /* random numbers */
  String xmlTag_SELM_Lagrangian_DOF_List   = "SELM_Lagrangian_DOF_List";
  String xmlTag_SELM_Eulerian_DOF_List     = "SELM_Eulerian_DOF_List";
  String xmlTag_SELM_Eulerian_selected     = "SELM_Eulerian_selected";
  String xmlTag_SELM_Integrator_List       = "SELM_Integrator_List";
  String xmlTag_SELM_Integrator_selected   = "SELM_Integrator_selected";
  String xmlTag_SELM_Interaction_List      = "SELM_Interaction_List";
  String xmlTag_SELM_CouplingOperator_List = "SELM_CouplingOperator_List";
  
  public JTable_MainData_XML_SELM_Builder(JTable_MainData jTable_MainData_in) {
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

      /* -- start tag for this class */
      Atz_XML_Helper.writeXMLStartTag(fid, className);

      /* write the leading parameters */
      /* remove basepath from data type */
      //TableData_Pathname dataPathname = (TableData_Pathname) model.getValueAt(model.paramIndex_BasePathname, col);
      //Atz_XML_Helper.writeXMLData(fid, xmlTag_BasePathname, dataPathname);
      
      String dataFilename = (String) model.getValueAt(model.paramIndex_BaseFilename, col);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_BaseFilename, dataFilename);

      String description = (String) model.getValueAt(model.paramIndex_Description, col);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_Description, description);

      if (model.paramIndex_UnitsRef != -1) { /* indicates stored in table, otherwise use default values */
        TableData_Units_Ref dataUnitsRef = (TableData_Units_Ref) model.getValueAt(model.paramIndex_UnitsRef, col);
        Atz_XML_Helper.writeXMLData(fid, xmlTag_PhysicalUnits, dataUnitsRef);
      } else {
        Atz_XML_Helper.writeXMLData(fid, xmlTag_PhysicalUnits, jTable_MainData.tableData_Units_Ref_default);
      }

      Integer dataSELM_Seed = (Integer) model.getValueAt(model.paramIndex_SELM_Seed, col);
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Seed, dataSELM_Seed);

      /* -- write integrator data */
      SELM_Integrator[] integratorList = jTable_MainData.getIntegratorList();
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Integrator_List, integratorList);

      /* -- write integrator name currently selected */
      String integratorNameSelected = jTable_MainData.getIntegratorNameSelected();
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Integrator_selected, integratorNameSelected);
      
      /* -- write eulerian DOF data */
      SELM_Eulerian[] eulerianList = jTable_MainData.getEulerianList();
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Eulerian_DOF_List, eulerianList);

      /* -- write eulerian name currently selected */
      String eulerianNameSelected = jTable_MainData.getEulerianNameSelected();
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Eulerian_selected, eulerianNameSelected);

      /* -- write lagrangian DOF data */
      SELM_Lagrangian[] lagrangianList = jTable_MainData.getLagrangianList();
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Lagrangian_DOF_List, lagrangianList);

      /* -- write interaction list data */
      SELM_Interaction[] interactionList = jTable_MainData.getInteractionList();
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_Interaction_List, interactionList);

      /* -- write coupling operator data */
      SELM_CouplingOperator[] couplingOpList = jTable_MainData.getCouplingOpList();
      Atz_XML_Helper.writeXMLData(fid, xmlTag_SELM_CouplingOperator_List, couplingOpList);
                        
      /* -- end tag for this class */
      Atz_XML_Helper.writeXMLEndTag(fid, className);
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

    if (qName.equals(xmlTag_BaseFilename)) {      
      sourceHandler.parseNextTagWithDataHandler(this); /* use current, technically statement not needed */      
    }

    /* removed basepath from data type */
    //if (qName.equals(xmlTag_BasePathname)) {
    //  TableData_Pathname dataPathname = (TableData_Pathname) model.getValueAt(model.paramIndex_BasePathname, col);
    //  sourceHandler.parseNextTagWithDataHandler(dataPathname); /* have dataPathname parse itself */
    //}

    if (qName.equals(xmlTag_Description)) {
      sourceHandler.parseNextTagWithDataHandler(this); /* use current, technically statement not needed */
    }

    if (qName.equals(xmlTag_PhysicalUnits)) {
      if (model.paramIndex_UnitsRef != -1) { /* indicates stored in table, otherwise use default values */
        TableData_Units_Ref dataUnitsRef = (TableData_Units_Ref) model.getValueAt(model.paramIndex_UnitsRef, col);
        sourceHandler.parseNextTagWithDataHandler(dataUnitsRef); /* have dataFilename parse itself */
      } else {
        sourceHandler.parseNextTagWithDataHandler(jTable_MainData.tableData_Units_Ref_default); /* use default */
      }
    }

    if (qName.equals(xmlTag_SELM_Seed)) {
      sourceHandler.parseNextTagWithDataHandler(this); /* use current, technically statement not needed */
    }

    if (qName.equals(xmlTag_SELM_Lagrangian_DOF_List)) {

      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Lagrangian_XML_DataDelegator());
      //Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Lagrangian_CONTROL_PTS_BASIC1());
      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);
      
    }

    if (qName.equals(xmlTag_SELM_Eulerian_DOF_List)) {

      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Eulerian_XML_DataDelegator());
      //Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Eulerian_CONTROL_PTS_BASIC1());
      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);

    }

    if (qName.equals(xmlTag_SELM_Integrator_List)) {

      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Integrator_XML_DataDelegator());
      //Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Integrator_CONTROL_PTS_BASIC1());
      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);

    }


    if (qName.equals(xmlTag_SELM_CouplingOperator_List)) {

      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_CouplingOperator_XML_DataDelegator());
      //Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_CouplingOp_CONTROL_PTS_BASIC1());
      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);

    }

    if (qName.equals(xmlTag_SELM_Interaction_List)) {

      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Interaction_XML_DataDelegator());
      //Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new SELM_Interaction_CONTROL_PTS_BASIC1());
      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);

    }

    
    
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

    if (qName.equals(xmlTag_BaseFilename)) {
      String dataFilename = (String) xmlAttributes.getValue("value");
      model.setValueAt(dataFilename, model.paramIndex_BaseFilename, col);
    }

    /* removed basepath from data type */
    /*
    if (qName.equals(xmlTag_BasePathname)) {
      TableData_Pathname dataPathname = (TableData_Pathname) sourceHandler.getLastUsedDataHandler();
      model.setValueAt(dataPathname, model.paramIndex_BasePathname, col);
    }
     */

    if (qName.equals(xmlTag_Description)) {
      String description = (String) xmlAttributes.getValue("value");
      model.setValueAt(description, model.paramIndex_Description, col);
    }

    if (qName.equals(xmlTag_PhysicalUnits)) {
      TableData_Units_Ref dataUnitsRef = (TableData_Units_Ref) sourceHandler.getLastUsedDataHandler();
      if (model.paramIndex_UnitsRef != -1) { /* indicates stored in table, otherwise use default values */
        model.setValueAt(dataUnitsRef, model.paramIndex_UnitsRef, col);
      } else {
        jTable_MainData.tableData_Units_Ref_default.atz_UnitsRef = dataUnitsRef.atz_UnitsRef;
      }
    }

    if (qName.equals(xmlTag_SELM_Seed)) {
      Integer SELM_Seed = Integer.parseInt(xmlAttributes.getValue("value"));
      model.setValueAt(SELM_Seed, model.paramIndex_SELM_Seed, col);
    }

    if (qName.equals(xmlTag_SELM_Lagrangian_DOF_List)) {
      
      /* get the list data handler, which was last used to parse this tag data */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();

      /* get the list data and add it to the current data structure */
      HashMap tagDataLists       = listDataHandler.getTagDataLists();
      ArrayList listOfLagrangian = (ArrayList) tagDataLists.get(SELM_Lagrangian.class.getSimpleName()); /* get list for particular class */
      SELM_Lagrangian[] lagrangianList = null;

      if (listOfLagrangian != null) {
        lagrangianList = new SELM_Lagrangian[listOfLagrangian.size()];
        for (int k = 0; k < lagrangianList.length; k++) {
          SELM_Lagrangian lagrangian = (SELM_Lagrangian) listOfLagrangian.get(k);
          lagrangianList[k]          = lagrangian;
        }

      } else {
        lagrangianList = new SELM_Lagrangian[0];
      }

      /* set the lagrangian list */
      jTable_MainData.setLagrangianDOF(lagrangianList);
      
    }

    if (qName.equals(xmlTag_SELM_Eulerian_DOF_List)) {

      /* get the list data handler, which was last used to parse this tag data */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();

      /* get the list data and add it to the current data structure */
      HashMap tagDataLists     = listDataHandler.getTagDataLists();
      ArrayList listOfEulerian = (ArrayList) tagDataLists.get(SELM_Eulerian.class.getSimpleName()); /* get list for particular class */

      SELM_Eulerian[] eulerianList = new SELM_Eulerian[listOfEulerian.size()];
      for (int k = 0; k < eulerianList.length; k++) {
        SELM_Eulerian eulerian = (SELM_Eulerian) listOfEulerian.get(k);
        eulerianList[k]          = eulerian;
      }

      /* set the eulerian list */
      jTable_MainData.setEulerianDOF(eulerianList);

    }

    if (qName.equals(xmlTag_SELM_Integrator_List)) {

      /* get the list data handler, which was last used to parse this tag data */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();

      /* get the list data and add it to the current data structure */
      HashMap tagDataLists       = listDataHandler.getTagDataLists();
      ArrayList listOfIntegrator = (ArrayList) tagDataLists.get(SELM_Integrator.class.getSimpleName()); /* get list for particular class */

      SELM_Integrator[] integratorList = new SELM_Integrator[listOfIntegrator.size()];
      for (int k = 0; k < integratorList.length; k++) {
        SELM_Integrator integrator = (SELM_Integrator) listOfIntegrator.get(k);
        integratorList[k]          = integrator;
      }

      /* set the integrator list */
      jTable_MainData.setIntegratorList(integratorList);

    }


    if (qName.equals(xmlTag_SELM_CouplingOperator_List)) {

      /* get the list data handler, which was last used to parse this tag data */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();

      /* get the list data and add it to the current data structure */
      HashMap tagDataLists       = listDataHandler.getTagDataLists();
      ArrayList listOfCouplingOp = (ArrayList) tagDataLists.get(SELM_CouplingOperator.class.getSimpleName()); /* get list for particular class */
      SELM_CouplingOperator[] couplingOpList = null;

      if (listOfCouplingOp != null) {

        couplingOpList = new SELM_CouplingOperator[listOfCouplingOp.size()];
        for (int k = 0; k < couplingOpList.length; k++) {
          SELM_CouplingOperator couplingOp = (SELM_CouplingOperator) listOfCouplingOp.get(k);
          couplingOpList[k]          = couplingOp;
        }

      } else { /* no couplingOP */
        couplingOpList = new SELM_CouplingOperator[0];
      }

      /* set the couplingOp list */
      jTable_MainData.setCouplingOpList(couplingOpList);
      
    }

    if (qName.equals(xmlTag_SELM_Interaction_List)) {

      /* get the list data handler, which was last used to parse this tag data */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();

      /* get the list data and add it to the current data structure */
      HashMap tagDataLists       = listDataHandler.getTagDataLists();
      ArrayList listOfInteraction = (ArrayList) tagDataLists.get(SELM_Interaction.class.getSimpleName()); /* get list for particular class */
      SELM_Interaction[] interactionList = null;

      if (listOfInteraction != null) {
        interactionList = new SELM_Interaction[listOfInteraction.size()];
        SELM_Lagrangian[] lagrangianList   = jTable_MainData.getLagrangianList(); /* get Lagrangian list to use in linking */
        for (int k = 0; k < interactionList.length; k++) {
          SELM_Interaction interaction = (SELM_Interaction) listOfInteraction.get(k);
          interaction.setupLagrangianFromList(lagrangianList); /* link to specified list */
          interactionList[k]           = interaction;
        }
      } else {
        interactionList = new SELM_Interaction[0];
      }

      /* set the interaction list */
      jTable_MainData.setInteractionList(interactionList);

    }


    if (qName.equals(xmlTag_SELM_Eulerian_selected)) {
      
      /* set the selected Eulerian DOF using name */
      String EulerianName = xmlAttributes.getValue("value");

      TableData_EulerianList data = (TableData_EulerianList) model.getValueAt(model.paramIndex_Eulerian_DOF, col);
      int N = data.eulerianList.length;
      for (int k = 0; k < N; k++) {
        if (data.eulerianList[k].EulerianName.equals(EulerianName)) {
          data.itemSelectedIndex = k;
        }
      }

    }

    if (qName.equals(xmlTag_SELM_Integrator_selected)) {
      /* set the selected Eulerian DOF using name */
      String IntegratorName = xmlAttributes.getValue("value");
      
      TableData_IntegratorList data = (TableData_IntegratorList) model.getValueAt(model.paramIndex_Integrator, col);
      int N = data.integratorList.length;
      for (int k = 0; k < N; k++) {
        if (data.integratorList[k].IntegratorName.equals(IntegratorName)) {
          data.itemSelectedIndex = k;
        }
      }

    }

  }

  @Override
  public Object XML_getData() {
    return jTable_MainData; /* WARNING: this is direct object, so not safe for lists */
  }

  
  /* ========================= XML codes ==================== */
  /* ======================================================== */

  
}