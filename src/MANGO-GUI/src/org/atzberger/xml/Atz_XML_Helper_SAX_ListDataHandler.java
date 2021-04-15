/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.xml;

import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * Handles lists represented by a collection of XML tags.
 * <p>
 * Builds a list of each distinct tag type encountered using the specified dataHandler_in
 * at the current scope of the XML document.  This provides a generic mechanism to process
 * lists of data without having to know details about the specific data type.  The parsing of
 * the individual tags is delegated to an appropriate data handler.
 * <p>
 * Warning: It is important that XML_getData() methods return a copy of the data class used for
 * parsing.  If this is not done the list would contain a collection of references to the same
 * instantiated class object.  A common bug is that clone() does not copy all fields.  This often
 * results in only partial results appearing even though the parser has processed all XML tags.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_XML_Helper_SAX_ListDataHandler extends Atz_XML_SAX_DataHandler {

  //Atz_XML_SAX_DataHandlerInterface dataHandler  = null;
  HashMap                          tagDataLists = new HashMap();  
  String                           xmlString;
  Attributes                       xmlAttributes;

  public Atz_XML_Helper_SAX_ListDataHandler(Atz_XML_SAX_DataHandlerInterface dataHandler_in) {
    super.parseNextTagWithDataHandler(dataHandler_in); /* set up current data handler */
    scopeDepthCount = 0;
  }

  public HashMap getTagDataLists() {
    return tagDataLists;
  }

  @Override
  public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    super.XML_startDocument(sourceHandler);
  }

  @Override
  public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    super.XML_endDocument(sourceHandler);
  }

  /* use the dataHandler to create instances of the objects encountered, if class tags are repeated */
  //Event Handlers
  @Override
  public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (scopeDepthCount == 0) { /* for zero scope setup the parsing behavior for this next tag */

      if (dataHandlerStack.isEmpty()) { /* this indicates tag was recently processed and handler popped */
        /* reset to use the last parser to process the next tag */
        super.parseNextTagWithDataHandler(this.getLastUsedDataHandler());        
      } else {
        /* otherwise current data handler which should already be set will be used */
        /* this case will usually occur for the first tag */
        int a = 0;
      }

    } /* for scope of greater depth nothing special needs to be done */

    super.XML_startElement(uri, localName, qName, attributes, sourceHandler);
     
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    super.XML_characters(ch, start, length, sourceHandler);
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    
    super.XML_endElement(uri, localName, qName, sourceHandler);

    /* only record lists when the scope is zero (relative to this listDataHandler parser) */
    if (scopeDepthCount == 0) {
      ArrayList dataHandlerList = null;
      /* check if current tag type was already encountered */
      if (tagDataLists.containsKey(qName) == false) { /* indicates new data type */
        dataHandlerList = new ArrayList();    /* create new list */
      } else { /* otherwise add to the existing list */
        dataHandlerList = (ArrayList) tagDataLists.get(qName);
        //tagDataLists.remove(qName); /* remove the list, (added back below) */
      }
      dataHandlerList.add(this.getLastUsedDataHandler().XML_getData()); /* add parsed data to the list */      
      tagDataLists.put(qName, dataHandlerList);  /* save the list for later */
    }

    if (scopeDepthCount == -1) {
      int a = 1;
    }

  }
  
  @Override
  public Object XML_getData() {
    return tagDataLists.clone();
  }
  
}
