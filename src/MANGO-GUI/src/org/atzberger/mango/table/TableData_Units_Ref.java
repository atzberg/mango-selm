package org.atzberger.mango.table;

import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Physical units reference data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 * @see Atz_UnitsRef
 */
public class TableData_Units_Ref implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  public Atz_UnitsRef atz_UnitsRef = null;

  String     xmlString = "";
  Attributes xmlAttributes = null;

  public TableData_Units_Ref() {
    atz_UnitsRef = new Atz_UnitsRef();
  }

  public TableData_Units_Ref(Atz_UnitsRef atz_UnitsRef_in) {
    atz_UnitsRef = atz_UnitsRef_in;
  }

  @Override
  public Object clone() {
    return new TableData_Units_Ref((Atz_UnitsRef) atz_UnitsRef.clone());
  }

  /* ======================================================== */
  /* ========================= XML codes ==================== */

   public void exportToXML(BufferedWriter fid) {
    Atz_XML_Helper.writeXMLStartTag(fid, this.getClass().getSimpleName());
    Atz_XML_Helper.writeXMLData(fid, "atz_UnitsRef", atz_UnitsRef);
    Atz_XML_Helper.writeXMLEndTag(fid, this.getClass().getSimpleName());
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
    if (qName.equals("atz_UnitsRef")) {
      sourceHandler.parseNextTagWithDataHandler(atz_UnitsRef); /* have dataFilename parse itself */
    }
    
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
    //xmlString = new String(ch, start, length); /* WARNING: could come in indefinite chunk sizes */
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    
    if (qName.equals("atz_UnitsRef")) {
      atz_UnitsRef = (Atz_UnitsRef) sourceHandler.getLastUsedDataHandler();
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone();
  }

  /* ========================= XML codes ==================== */
  /* ======================================================== */
  
}
