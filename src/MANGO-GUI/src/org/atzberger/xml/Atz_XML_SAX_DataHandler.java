package org.atzberger.xml;


import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Handles the processing of a collection of XML tags.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_XML_SAX_DataHandler extends DefaultHandler implements Atz_XML_SAX_DataHandlerInterface {

  protected Stack dataHandlerStack = new Stack();
  protected Stack scopeDepthStack  = new Stack();
  protected Stack parseModeStack   = new Stack();

  protected static final int PARSE_MODE_NULL          = 0;
  protected static final int PARSE_MODE_NEXT_TAG      = 1;
  protected static final int PARSE_MODE_CURRENT_SCOPE = 2;
    
  protected Atz_XML_SAX_DataHandlerInterface lastPoppedHandler = null;

  int scopeDepthCount = 0;

  public Atz_XML_SAX_DataHandler() {
    scopeDepthCount = 0;
  }

  public Atz_XML_SAX_DataHandler(Atz_XML_SAX_DataHandlerInterface currentHandler_in) {
    setDataHandler(currentHandler_in);
    scopeDepthCount = 0;
  }

  public void setDataHandler(Atz_XML_SAX_DataHandlerInterface currentHandler_in) {
    dataHandlerStack.clear();
    scopeDepthStack.clear();
    parseModeStack.clear();
    pushDataHandler(currentHandler_in);
  }

  public void changeCurrentDataHandler(Atz_XML_SAX_DataHandlerInterface newCurrentHandler_in) {
    popDataHandler();
    pushDataHandler(newCurrentHandler_in);    
  }
  
  public Atz_XML_SAX_DataHandlerInterface getCurrentDataHandler() {
    return (Atz_XML_SAX_DataHandlerInterface) dataHandlerStack.peek();
  }

  public Stack getDataHandlerStack() {
    return dataHandlerStack;
  }

  public int getCurrentParseMode() {
    return (Integer) parseModeStack.peek();
  }

  /* Returns the last data handler used to parse data */
  public Atz_XML_SAX_DataHandlerInterface getLastUsedDataHandler() {
    return getLastPoppedDataHandler();
  }
 
  public Atz_XML_SAX_DataHandlerInterface getLastPoppedDataHandler() {
    return lastPoppedHandler;
  }

  /* Parses the next tag encountered at the current scope with the specified handler.
   */
  public void parseNextTagWithDataHandler(Atz_XML_SAX_DataHandlerInterface currentHandler) {
    pushDataHandler(currentHandler, this.PARSE_MODE_NEXT_TAG);
  }

  /* Parses all data at the current scope with the specified handler.
   */
  public void parseCurrentScopeWithDataHandler(Atz_XML_SAX_DataHandlerInterface currentHandler) {
    pushDataHandler(currentHandler, this.PARSE_MODE_CURRENT_SCOPE);
  }

  public void pushDataHandler(Atz_XML_SAX_DataHandlerInterface currentHandler) {
    pushDataHandler(currentHandler, this.PARSE_MODE_NEXT_TAG); /* default parse mode used */
  }

  public void pushDataHandler(Atz_XML_SAX_DataHandlerInterface currentHandler, int parseMode) {
    dataHandlerStack.push(currentHandler);
    scopeDepthStack.push(scopeDepthCount);
    parseModeStack.push(parseMode);
  }

  public Atz_XML_SAX_DataHandlerInterface popDataHandler() {

    if (dataHandlerStack.isEmpty() == false) {
      parseModeStack.pop();
      //scopeDepthCount   = (Integer) scopeDepthStack.pop();
      scopeDepthStack.pop();
      lastPoppedHandler = (Atz_XML_SAX_DataHandlerInterface) dataHandlerStack.pop();
    } else {
      scopeDepthCount   = -1;
      lastPoppedHandler = null;
    }

    return lastPoppedHandler;
  }

  public boolean isEmptyDataHandlerStack() {
    return dataHandlerStack.isEmpty();
  }

  public Atz_XML_SAX_DataHandlerInterface peekDataHandler() {
    return (Atz_XML_SAX_DataHandlerInterface) dataHandlerStack.peek();
  }

  /* ================================================================================= */
  /* ================================ SAX Event Handlers ============================= */

  @Override
  public void startDocument() {
    scopeDepthCount = 0;
    this.getCurrentDataHandler().XML_startDocument(this);
  }

  @Override
  public void endDocument() {
    if (!isEmptyDataHandlerStack()) {
      this.getCurrentDataHandler().XML_endDocument(this);
    }
    if (scopeDepthCount != 0) {
      System.err.println("");
      System.err.println("WARNING: " + this.getClass().getSimpleName() + "scope depth count not zero at end of file.");
      System.err.println("scopeDepthCount = " + scopeDepthCount);
      if (!isEmptyDataHandlerStack()) {
        System.err.println("currentHandler = " + this.getCurrentDataHandler().getClass().getName());
      }
      System.err.println("");
    }
  }

  //Event Handlers
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {    
    scopeDepthCount++;    
    this.getCurrentDataHandler().XML_startElement(uri, localName, qName, attributes, this);
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (!isEmptyDataHandlerStack()) { /* pass characters along if data handler is specified */
      this.getCurrentDataHandler().XML_characters(ch, start, length, this);
    } else {
      /* ignore these XML characters possibly between tags */
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {

    int parseMode        = getCurrentParseMode();
    int lastScopeStacked = -1;

    /* process tag depending on mode */
    switch (parseMode) {

      case PARSE_MODE_NEXT_TAG:
        
        /* Parse until completion of the tag occuring just after the push.
         * This results in the pushed handler only operating on one data tag
         * and then returning control when the tag is completed.
         */
        
        if (scopeDepthStack.isEmpty() == false) {
          lastScopeStacked = (Integer) (scopeDepthStack.peek());
        }

        /* process the tag with the current data handler */        
        this.getCurrentDataHandler().XML_endElement(uri, localName, qName, this);
       
        /* check if we are about to return to the reference scope just before the push of dataHandler */
        if (scopeDepthCount <= lastScopeStacked + 1) { /* scope is about to return from child processing */
          popDataHandler(); /* pop the handler back to the original one before the push */
        }

        break; /* end PARSE_MODE_NEXT_TAG */

      case PARSE_MODE_CURRENT_SCOPE:

        /* Parse until an attempt is made to exit the current scope.
         * This allows for the pushed data handler to parse multiple tags
         * within the current scope.  When the reference scope is about to
         * be exited, control is returned to the previous data handler just
         * before the push.
         */
        
        if (scopeDepthStack.isEmpty() == false) {
          lastScopeStacked = (Integer) (scopeDepthStack.peek());
        }

        /* check if we are about to exit the scope just before the push of dataHandler */
        if (scopeDepthCount <= lastScopeStacked) { /* scope has returned from child processing */
          popDataHandler(); /* pop the handler back to the original one before the push */
        }

        /* use the data handler before the push to process the end tag */
        this.getCurrentDataHandler().XML_endElement(uri, localName, qName, this);

        break; /* end PARSE_MODE_CURRENT_SCOPE */

    } /* end parseMode switch */

    scopeDepthCount--; /* just processed tag so decrease scope by one */
    
  }
  
  /* ================================ SAX Event Handlers ============================= */
  /* ================================================================================= */


  /* ================================================================================= */
  /* ================== Atz_XML_SAX_DataHandlerInterface wrapper ===================== */
  @Override
  public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    startDocument();
  }

  @Override
  public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    endDocument();
  }

  @Override
  public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    startElement(uri, localName, qName, attributes);
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    endElement(uri, localName, qName);
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    characters(ch, start, length);
  }
    
  @Override
  public Object XML_getData() {
    return this;
  }

  /* ================== Atz_XML_SAX_DataHandlerInterface wrapper ===================== */
  /* ================================================================================= */
  
}