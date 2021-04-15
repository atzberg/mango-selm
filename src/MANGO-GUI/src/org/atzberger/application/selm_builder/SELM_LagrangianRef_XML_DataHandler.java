package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
import org.atzberger.mango.atz3d.Atz3D_Element_Points;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_LagrangianRef_XML_DataHandler implements Atz_XML_SAX_DataHandlerInterface {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();

  /* XML */
  static public String tagXML_SELM_Lagrangian_Ref  = "SELM_Lagrangian_Ref";
  static public String tagXML_LagrangianName       = "LagrangianName";
  static public String tagXML_LagrangianTypeStr    = "LagrangianTypeStr";

  public String LagrangianName    = "";
  public String LagrangianTypeStr = "";

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;
   
  public SELM_LagrangianRef_XML_DataHandler() {
      
    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();
    
  }

  @Override
  public Object clone() {
    SELM_LagrangianRef_XML_DataHandler Lagrangian_copy = new SELM_LagrangianRef_XML_DataHandler();

    Lagrangian_copy.LagrangianName    = this.LagrangianName.toString();
    Lagrangian_copy.LagrangianTypeStr = this.LagrangianTypeStr.toString();
        
    return (Object) Lagrangian_copy;
  }

  public String getLagrangianName() {
    return LagrangianName;
  }

  public String getLagrangianTypeStr() {
    return LagrangianTypeStr;
  }

  /* ---------------------------------------------------- */

  /* ====================================================== */
  /* ==================== XML codes ======================= */  
  public void exportData(String filename, int flagType) {

    switch (flagType) {

      case SELM_Lagrangian.FILE_TYPE_XML:

        try {
          
          // Create file
          FileWriter fstream = new FileWriter(filename);
          BufferedWriter fid = new BufferedWriter(fstream);

          Atz_XML_Helper.writeXMLHeader(fid, "1.0", "UTF-8");

          exportToXML(fid);

          //Close the fidput stream
          fid.close();

        } catch (Exception e) {//Catch exception if any
          e.printStackTrace();
          System.out.println(e);
          //System.err.println("Error: " + e.getMessage());
        }

        break; /* end XML */

    } /* end switch */

  }
  
  public void exportToXML(BufferedWriter fid) {
    
    try {              
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_SELM_Lagrangian_Ref);

      Atz_XML_Helper.writeXMLData(fid, tagXML_LagrangianName,    LagrangianName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_LagrangianTypeStr, LagrangianTypeStr);

      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_SELM_Lagrangian_Ref);

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }

   
  public void importData(String filename, int flagType) {

    /* open the XML file */

    /* parse the XML file to setup the data */

    //get a factory
    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {

      //get a new instance of parser
      SAXParser sp = spf.newSAXParser();

      //parse the file and also register this class for call backs
      //sp.parse("test1.SELM_Lagrangian_CONTROL_PTS_BASIC1", new Atz_XML_DataHandlerWrapper(this));
      
      sp.parse(filename, new Atz_XML_SAX_DataHandler(this));

      /* Use the local codes XMLContentHandler */
      
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
   
    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;

  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);     
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(SELM_Lagrangian.class.getSimpleName())) {
      /* check all entries set */
    } else if (qName.equals(tagXML_LagrangianName)) {
      LagrangianName = xmlAttributes.getValue("value");
    } else if (qName.equals(tagXML_LagrangianTypeStr)) {
      LagrangianTypeStr = xmlAttributes.getValue("value");      
    }
    
  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */
    
}



