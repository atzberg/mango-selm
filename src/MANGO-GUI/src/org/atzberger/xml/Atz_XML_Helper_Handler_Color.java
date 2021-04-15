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
 * Handles parsing of XML representation of color data.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_XML_Helper_Handler_Color implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  public Color color;

  /* XML */
  protected Attributes xmlAttributes = null;
  protected String     xmlString     = "";

  public Atz_XML_Helper_Handler_Color(Color color_in) { /* useful for write mode */
    color = color_in;
  }

  public Atz_XML_Helper_Handler_Color() { /* useful for read mode */
    color = new Color(255,255,255);
  }

  public void exportToXML(BufferedWriter fid) {
    exportToXML(fid, color);
  }

  static public void exportToXML(BufferedWriter fid, Color color_in) {
        
    int[] colorArray = new int[3];
    colorArray[0] = color_in.getRed();
    colorArray[1] = color_in.getGreen();
    colorArray[2] = color_in.getBlue();

    String tagName = Color.class.getSimpleName();
    Atz_XML_Helper.writeXMLStartTag(fid, tagName);
    Atz_XML_Helper.writeXMLData(fid, "colorArray", colorArray);
    Atz_XML_Helper.writeXMLEndTag(fid, tagName);

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
    } else if (qName.equals("colorArray")) {
      int[] colorArray;
      colorArray = Atz_XML_Helper.parseIntArrayFromString(xmlString);
      color      = new Color(colorArray[0],colorArray[1],colorArray[2]);
    }

  }

  @Override
  public Object XML_getData() {
    return color; /* WARNING: may not be list safe, see construction above to make sure. */
  }



}
