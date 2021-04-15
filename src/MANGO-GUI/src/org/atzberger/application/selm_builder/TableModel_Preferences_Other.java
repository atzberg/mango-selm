package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Preferences_Other extends TableModel_Properties1_Default
   implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  private final static boolean DEBUG = false;

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public static  String tagXML_SELM_Preferences = "SELM_Preferences";
  public static  String tagXML_SELM_Preferences_Other = tagXML_SELM_Preferences + "_Other";
      
  TableModel_Preferences_Other() {
    
    int      i                = 0;
    
  }

     /* ====================================================== */
  /* ==================== XML codes ====================== */
  public void importData(String filename, int fileType) {

  }

  public void exportData(String filename, int fileType) {

  }

  @Override
  public void exportToXML(BufferedWriter fid) {

    try {

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_SELM_Preferences_Other);
      
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_SELM_Preferences_Other);

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
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
    }

  }

  @Override
  public Object XML_getData() {
    return this; /* WARNING: not safe for lists */
  }

  /* ==================== XML parser ====================== */
  /* ====================================================== */

 
}







