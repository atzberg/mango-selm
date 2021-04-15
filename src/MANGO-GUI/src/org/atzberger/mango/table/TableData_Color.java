/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.mango.table;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.awt.Color;
import java.io.BufferedWriter;
import org.atzberger.application.selm_builder.SELM_Lagrangian;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Color data type wrapper.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_Color implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public Color color;

  public TableData_Color() {
    color = new Color(255,255,255);
  }

  public TableData_Color(Color color_in) {
    color = color_in;
  }

  public TableData_Color(int red, int green, int blue) {
    color = new Color(red, green, blue);
  }

  @Override
  public Object clone() {
    return new TableData_Color(color);
  }

  public void exportToXML(BufferedWriter fid) {

    int[] colorArray = new int[3];
    colorArray[0] = color.getRed();
    colorArray[1] = color.getGreen();
    colorArray[2] = color.getBlue();

    Atz_XML_Helper.writeXMLStartTag(fid, this.getClass().getSimpleName());
    Atz_XML_Helper.writeXMLData(fid, "colorArray", colorArray);
    Atz_XML_Helper.writeXMLEndTag(fid, this.getClass().getSimpleName());
          
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
    return this.clone(); /* WARNING: may not be list safe, see construction above to make sure. */
  }
  
}
