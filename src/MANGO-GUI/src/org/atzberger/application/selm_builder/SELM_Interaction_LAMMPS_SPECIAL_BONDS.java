package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_Helper_SAX_ListDataHandler;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element_Lines;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
public class SELM_Interaction_LAMMPS_SPECIAL_BONDS extends SELM_Interaction implements SELM_InteractionInterface_LAMMPS, Atz_XML_SAX_DataHandlerInterface {
    
  public double          weight1_2        = 0.0;
  public double          weight1_3        = 0.0;
  public double          weight1_4        = 0.0;
  
  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public static String tagXML_weight1_2               = "weight1_2";
  public static String tagXML_weight1_3               = "weight1_3";
  public static String tagXML_weight1_4               = "weight1_4";
     
  public SELM_Interaction_LAMMPS_SPECIAL_BONDS() {

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    InteractionName    = "SPECIAL_BONDS";
    InteractionTypeStr = thisClassName.replace(superClassName + "_", "");
    
  }


  @Override
  public SELM_Interaction_LAMMPS_SPECIAL_BONDS clone() {
    SELM_Interaction_LAMMPS_SPECIAL_BONDS interaction_copy = new SELM_Interaction_LAMMPS_SPECIAL_BONDS();

    interaction_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    interaction_copy.InteractionName                   = this.InteractionName.toString();
    interaction_copy.InteractionTypeStr                = this.InteractionTypeStr.toString();
    
    interaction_copy.weight1_2                         = this.weight1_2;
    interaction_copy.weight1_3                         = this.weight1_3;
    interaction_copy.weight1_4                         = this.weight1_4;
    
    return interaction_copy;

  }


  /* ====================================================== */
  /* ================= LAMMPS Interface =================== */  
  @Override
  public void setFlagGenLAMMPS_XML_Files(boolean val) {
    /* currently does nothing, but could be used to signal that XML
     * files will be used with the LAMMPS simulation so should be set
     * accordingly.
     */
  }

  /* ====================================================== */
  /* ==================== XML codes ======================= */
  @Override
  public void exportData(String filename, int flagType) {

    switch (flagType) {

      case FILE_TYPE_XML:

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

  @Override
  public void exportToXML(BufferedWriter fid) {

    try {
      Atz_XML_Helper.writeXMLStartTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

      Atz_XML_Helper.writeXMLData(fid, tagXML_InteractionName,    InteractionName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_InteractionTypeStr, InteractionTypeStr);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_weight1_2, weight1_2);
      Atz_XML_Helper.writeXMLData(fid, tagXML_weight1_3, weight1_3);
      Atz_XML_Helper.writeXMLData(fid, tagXML_weight1_4, weight1_4);
      
      Atz_XML_Helper.writeXMLEndTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }


  @Override
  public void importData(String filename, int flagType) {

    /* open the XML file */

    /* parse the XML file to setup the data */

    //get a factory
    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {

      //get a new instance of parser
      SAXParser sp = spf.newSAXParser();

      //parse the file and also register this class for call backs
      //sp.parse("test1.SELM_Interaction_CONTROL_PTS_BASIC1", new Atz_XML_DataHandlerWrapper(this));

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

    if (qName.equals(SELM_Interaction.tagXML_SELM_Interaction)) {
      /* nothing special to do */    
    }
    
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(SELM_Interaction.class.getSimpleName())) {
      /* check all entries set */
    } else if (qName.equals(tagXML_InteractionName)) {

    } else if (qName.equals(tagXML_InteractionTypeStr)) {
      
    } else if (qName.equals(tagXML_weight1_2)) {
      weight1_2 = Double.parseDouble(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_weight1_3)) {
      weight1_3 = Double.parseDouble(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_weight1_4)) {
      weight1_4 = Double.parseDouble(xmlAttributes.getValue("value"));
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */

}