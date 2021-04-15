package org.atzberger.mango.table;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import java.util.Date;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Filename data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_Filename implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  public static final int EDIT_MODE_NULL         = 0;
  static public final int EDIT_MODE_FILE_CHOOSER = 1;
  public static final int EDIT_MODE_STRING       = 2;

  private int preferredEditMode = EDIT_MODE_FILE_CHOOSER;

  String     xmlString     = "";
  Attributes xmlAttributes = null;

  Date date = new Date();
  private long   lastChangeTime;
  private long   lastEditorChangeTime;
  private String filename;

  public TableData_Filename() {
    filename = "tmp.txt";
  }

  public TableData_Filename(String str) {
    filename = str;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename_in) {
    filename       = filename_in;
    lastChangeTime = date.getTime();
  }

  public void setPreferredEditMode(int mode) {
    preferredEditMode = mode;
  }

  public int getPreferredEditMode() {
    return preferredEditMode;
  }
  
  public long getLastChangeTime() {
    return lastChangeTime;
  }

  public void recordLastEditorChangeTime() {
    lastEditorChangeTime = date.getTime();
  }

  public long getLastEditorChangeTime() {
    return lastEditorChangeTime;
  }

  @Override
  public Object clone() {
    return new TableData_Filename(filename);
  }

  public void exportToXML(BufferedWriter fid) {
    Atz_XML_Helper.writeXMLStartTag(fid, this.getClass().getSimpleName());
    Atz_XML_Helper.writeXMLData(fid, "filename", filename);
    Atz_XML_Helper.writeXMLEndTag(fid, this.getClass().getSimpleName());
  }
  
  /* ==================== XML parser ====================== */
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

    if (qName.equals(this.getClass().getSimpleName())) {
     /* nothing special to do */
    }

  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
    //xmlString = new String(ch, start, length); /* WARNING: could come in indefinite chunk sizes */
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(this.getClass().getSimpleName())) {
      /* done with parsing this object */
    } else if (qName.equals("filename")) {
      filename = xmlAttributes.getValue("value");
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone();
  }

  
}
