package org.atzberger.mango.atz3d;


import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

/**
 *
 * Save state of the panel in XML data file.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_Model_View_Composite_XML_SELM_Builder
        implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  JPanel_Model_View_Composite   jPanel_Model_View_Composite; /* object to export to XML */
  JPanel_Model_View_RenderPanel jPanel_Model_View_RenderPanel;
  
  JPanel_Model_View_RenderPanel_XML_SELM_Builder jPanel_Model_View_RenderPanel_XML_SELM_Builder;

  String      xmlString     = "";
  Attributes  xmlAttributes = null;

  String xmlTag_JPanel_Model_View_Composite = "JPanel_Model_View_Composite";
  
  public JPanel_Model_View_Composite_XML_SELM_Builder(JPanel_Model_View_Composite jPanel_Model_View_Composite_in) {
    setJPanel_Model_View_Composite(jPanel_Model_View_Composite_in);
  }
        
  /* ====================================================================*/
  /* ========================== XML Codes ===============================*/
  public void setJPanel_Model_View_Composite(JPanel_Model_View_Composite jPanel_Model_View_Composite_in) {
    jPanel_Model_View_Composite   = jPanel_Model_View_Composite_in;
    jPanel_Model_View_RenderPanel = jPanel_Model_View_Composite.getJPanel_Model_View_RenderPanel();
    jPanel_Model_View_RenderPanel_XML_SELM_Builder = new JPanel_Model_View_RenderPanel_XML_SELM_Builder(jPanel_Model_View_RenderPanel);
  }
  
  public void exportToXML(BufferedWriter fid) {
      
      /* -- start tag for this class */
      Atz_XML_Helper.writeXMLStartTag(fid, xmlTag_JPanel_Model_View_Composite);
      
      jPanel_Model_View_RenderPanel_XML_SELM_Builder.exportToXML(fid);
                                                      
      /* -- end tag for this class */
      Atz_XML_Helper.writeXMLEndTag(fid, xmlTag_JPanel_Model_View_Composite);
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
    
    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;
    
    if (qName.equals(xmlTag_JPanel_Model_View_Composite)) {
      
    }
    
    if (qName.equals(jPanel_Model_View_RenderPanel_XML_SELM_Builder.xmlTag_JPanel_Model_View_RenderPanel)) {
      /* delegate parsing to this class */
      sourceHandler.parseCurrentScopeWithDataHandler(jPanel_Model_View_RenderPanel_XML_SELM_Builder);
    }
     
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
    //xmlString = new String(ch, start, length); /* WARNING: could come in indefinite chunk sizes */
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(xmlTag_JPanel_Model_View_Composite)) {
      /* make sure all fields set */
    }
    
  }

  @Override
  public Object XML_getData() {
    return jPanel_Model_View_Composite; /* WARNING: this is direct object, so not safe for lists */
  }

  
  /* ========================= XML codes ==================== */
  /* ======================================================== */

  
}