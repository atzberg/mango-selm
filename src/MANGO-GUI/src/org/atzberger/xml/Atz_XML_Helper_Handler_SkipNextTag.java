/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.xml;

import org.atzberger.application.selm_builder.SELM_Lagrangian;
import java.awt.Color;
import java.io.BufferedWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handles skipping the parsing of next XML tag.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_XML_Helper_Handler_SkipNextTag implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {
  
  /* XML */
  Attributes xmlAttributes = null;
  String     xmlString     = "";

  public Atz_XML_Helper_Handler_SkipNextTag() { /* useful for write mode */
    
  }
  
  public void exportToXML(BufferedWriter fid) {

    /* thing to do here... */

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

    if (qName.equals(SELM_Lagrangian.class.getSimpleName())) {
      /* check all entries set */
    } 

  }

  @Override
  public Object XML_getData() {
    return null; /* WARNING: may not be list safe, see construction above to make sure. */
  }



}
