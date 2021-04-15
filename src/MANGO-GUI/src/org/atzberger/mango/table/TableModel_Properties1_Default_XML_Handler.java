package org.atzberger.mango.table;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_SkipNextTag;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.atzberger.application.selm_builder.Atz_Helper_Generic;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Handles writing a table to XML data format.  Requires that all data entries be Atz_XML_Writeable or of type handled by
 * the helper class, which includes String, int, double, boolean.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Properties1_Default_XML_Handler extends TableModel_Properties1_Default
      implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  String      xmlString     = "";
  Attributes  xmlAttributes = null;
  String      xmlDataClassName = "";
  int         xmlDataRow       = -1;
  int         xmlDataColumn    = -1;
  Object      xmlData          = null;

  static final String xmlTag_DataEntry = "DATA_ENTRY";

  TableModel_Properties1_Default model = null;

  public TableModel_Properties1_Default_XML_Handler(TableModel_Properties1_Default model_in) {
    model = model_in;
  }

  /* ====================================================================*/
  /* ========================== XML Codes ===============================*/
  public void exportToXML(BufferedWriter fid) {

      String className              = this.getClass().getSimpleName();
      int                   colData = 1;
      int                   colName = 0;

      /* -- start tag for this class */
      Atz_XML_Helper.writeXMLStartTag(fid, className);

      Object dataVal = null;

      /* loop over all of the data entries and write them to XML */      
      for (int col = 0; col < model.maxColumnUsed; col++) {
        for (int row = 0; row < model.maxRowUsed; row++) {
          dataVal        = model.data[row][col];    
          String attrStr = "";
          attrStr        = attrStr + "class=\"" + dataVal.getClass().getName() + "\"";
          attrStr        = attrStr + " row=\"" + row + "\"";
          attrStr        = attrStr + " column=\"" + col + "\"";
          Atz_XML_Helper.writeXMLData(fid, xmlTag_DataEntry, dataVal, attrStr);                                                    
        }
      }

      /* -- end tag for this class */
      Atz_XML_Helper.writeXMLEndTag(fid, className);

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

    int                 col   = 1;

    boolean flagHandlerSetup = false;

    /* setup data for later parsing and processing */
    xmlString        = "";
    xmlAttributes    = attributes;
    xmlDataClassName = "";
    xmlDataRow       = -1;
   
    /* this indicates start of data for this model */
    if (qName.equals(this.getClass().getSimpleName())) {
      /* initialize for reading tag information */

    } else if (qName.equals(xmlTag_DataEntry)) {
      /* see if a class has been specified for the given type */
      xmlDataClassName = xmlAttributes.getValue("class");
      xmlDataRow       = Integer.parseInt(xmlAttributes.getValue("row"));
      xmlDataColumn    = Integer.parseInt(xmlAttributes.getValue("column"));
      if (xmlDataClassName != null) {  /* null indicates not attribute "class" */

        if (xmlDataClassName.equals(String.class.getName())) {
          /* recursive loop if do "this" */
          //sourceHandler.parseNextTagWithDataHandler(this); /* use this class to parse tag */
          flagHandlerSetup = true; /* but claim handler setup */
        } else if(xmlDataClassName.equals(Boolean.class.getName())) {
          sourceHandler.parseNextTagWithDataHandler(this); /* use this class to parse tag */
          flagHandlerSetup = true; /* but claim handler setup */
        } else if (xmlDataClassName.equals(Integer.class.getName())) {
          sourceHandler.parseNextTagWithDataHandler(this); /* use this class to parse tag */
          flagHandlerSetup = true; /* but claim handler setup */
        } else if (xmlDataClassName.equals(Double.class.getName())) {
          sourceHandler.parseNextTagWithDataHandler(this); /* use this class to parse tag */
          flagHandlerSetup = true; /* but claim handler setup */
        } else {
          /* load class instance of the specified type */
          xmlData = Atz_Helper_Generic.loadAndInstantiateClass(xmlDataClassName);
          if (xmlData != null) {
            /* use this class to read the next tag and related data */
            if (Atz_XML_SAX_DataHandlerInterface.class.isInstance(xmlData)) {
              sourceHandler.parseNextTagWithDataHandler((Atz_XML_SAX_DataHandlerInterface) xmlData);
              flagHandlerSetup = true;
            }
          } /* end obj_ref != null */

          if (!flagHandlerSetup) {/* if not known case, ignore next tag (and sub-tags) */
            sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_SkipNextTag());
          }

        } /* end else for Atz_XML_Writeable */

      } /* end className non-null*/

    } /* end xmlTag_DataEntry */
    
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);   
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    
    /* see if a class has been specified for the given type */    
    if (xmlDataClassName != null) {  /* null indicates not attribute "class" */
      /* set the data based on the class */
      if (String.class.getName().equals(xmlDataClassName)) {
        String val = xmlAttributes.getValue("value");
        model.setValueAt(new String(val), xmlDataRow, xmlDataColumn);
      } else if (Boolean.class.getName().equals(xmlDataClassName)) {
        boolean val = Boolean.parseBoolean(xmlAttributes.getValue("value"));
        model.setValueAt(new Boolean(val), xmlDataRow, xmlDataColumn);
      } else if (Integer.class.getName().equals(xmlDataClassName)) {
        int val = Integer.parseInt(xmlAttributes.getValue("value"));
        model.setValueAt(new Integer(val), xmlDataRow, xmlDataColumn);
      } else if (Double.class.getName().equals(xmlDataClassName)) {
        double val = Double.parseDouble(xmlAttributes.getValue("value"));
        model.setValueAt(new Double(val), xmlDataRow, xmlDataColumn);
      } else if (Atz_XML_SAX_DataHandlerInterface.class.isAssignableFrom(xmlData.getClass())) {
        Atz_XML_SAX_DataHandlerInterface handler = sourceHandler.getLastUsedDataHandler();
        model.setValueAt(handler.XML_getData(), xmlDataRow, xmlDataColumn);
      }
    } /* end xmlDataClassName not null */
    
  }

  @Override
  public Object XML_getData() {
    return this; /* WARNING: this is direct object, so not safe for lists */
  }

  /* ========================= XML codes ==================== */
  /* ======================================================== */

}
