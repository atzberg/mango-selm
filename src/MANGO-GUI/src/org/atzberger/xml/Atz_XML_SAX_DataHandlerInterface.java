package org.atzberger.xml;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.DTDHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Handles the processing of a collection of XML tags.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public interface Atz_XML_SAX_DataHandlerInterface {

  public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler);
  
  public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler);

  public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException;

  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException;

  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException;

  public Object XML_getData(); /* gets data from parsing the XML */
  
}
