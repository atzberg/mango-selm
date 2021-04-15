/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.application.selm_builder;

import org.atzberger.application.selm_builder.SELM_Eulerian;
import java.awt.Color;
import java.io.BufferedWriter;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Writeable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles parsing of XML representation of Eulerian data.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_XML_Helper_Handler_EulerianRef implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  public String EulerianName    = "NULL";
  public String EulerianTypeStr = "NULL";

  public static String tagXML_EulerianRef = SELM_Eulerian.tagXML_SELM_Eulerian + "_Ref";

  /* XML */
  Attributes xmlAttributes = null;
  String     xmlString     = "";

  public Atz_XML_Helper_Handler_EulerianRef(String EulerianName_in, String EulerianTypeStr_in) { /* useful for write mode */
    EulerianName    = EulerianName_in;
    EulerianTypeStr = EulerianTypeStr_in;
  }

  public Atz_XML_Helper_Handler_EulerianRef() { /* useful for read mode */
    /* nothing to do */
  }

  @Override
  public Object clone() {
    return new Atz_XML_Helper_Handler_EulerianRef(EulerianName, EulerianTypeStr);
  }

  public void exportToXML(BufferedWriter fid) {
    exportToXML(fid, EulerianName, EulerianTypeStr);
  }

  static public void exportToXML(BufferedWriter fid, String EulerianName_in, String EulerianTypeStr_in) {
        
    Atz_XML_Helper.writeXMLStartTag(fid, tagXML_EulerianRef);
    Atz_XML_Helper.writeXMLData(fid, SELM_Eulerian.tagXML_EulerianName,    EulerianName_in);
    Atz_XML_Helper.writeXMLData(fid, SELM_Eulerian.tagXML_EulerianTypeStr, EulerianTypeStr_in);
    Atz_XML_Helper.writeXMLEndTag(fid, tagXML_EulerianRef);

  }

  /* XML SAX event handlers here... */
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

    if (qName.equals(tagXML_EulerianRef)) {
      /* check all entries set */
    } else if (qName.equals(SELM_Eulerian.tagXML_EulerianName)) {
      EulerianName    = xmlAttributes.getValue("value");
    } else if (qName.equals(SELM_Eulerian.tagXML_EulerianTypeStr)) {
      EulerianTypeStr = xmlAttributes.getValue("value");
    }

  }

  

  @Override
  public Object XML_getData() {
    return this.clone(); /* WARNING: may not be list safe, see construction above to make sure. */
  }



}
