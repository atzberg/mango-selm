package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Preferences_TableDisplay extends TableModel_Properties1_Default 
  implements TableModelListener, Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  private final static boolean DEBUG = false;

  boolean internalSetFormatStr = false;

  application_SharedData applSharedData = null;

  public static String paramName_PhysUnitsVisible   = "Physical Units Visible";
  public static int    paramIndex_PhysUnitsVisible  = -1;
  public static String tagXML_PhysUnitsVisible      = "PhysicalUnitsVisible";

  public static String paramName_DisplayDoubleVals  = "Display for Double Values";
  public static int    paramIndex_DisplayDoubleVals = -1;
  public static String tagXML_DisplayDoubleVals     = "DisplayForDoubleValues";

  public static  String tagXML_SELM_Preferences = "SELM_Preferences";
  public static  String tagXML_SELM_Preferences_TableDisplay = tagXML_SELM_Preferences + "_TableDisplay";

  String     xmlString     = "";
  Attributes xmlAttributes = null;
  
  TableModel_Preferences_TableDisplay() {
    
    setToDefaultValues();

    /* setup data change listener */
    this.addTableModelListener(this);

  }

  public void setToDefaultValues() {

    int      i                = 0;

    setValueAt(paramName_PhysUnitsVisible, i,0, NOT_EDITABLE);
    setValueAt(new Boolean(false), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_PhysUnitsVisible = i;
    i++;

    setValueAt(paramName_DisplayDoubleVals, i,0, NOT_EDITABLE);
    setValueAt(new String("0.####E0"), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_DisplayDoubleVals = i;
    i++;

  }


  public void setApplSharedData(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;

    /* update the boolean value for the state of visibility */
    Boolean flagVisible = new Boolean(applSharedData.atz_UnitsRef.getVisiblePreferred());
    setValueAt(flagVisible, paramIndex_PhysUnitsVisible, 1);
            
    /* update the table entries to reflect new application data now available */
  }

  
 @Override
  public void tableChanged(TableModelEvent evt) {

    if ((evt.getFirstRow() <= paramIndex_PhysUnitsVisible) && (evt.getLastRow() >= paramIndex_PhysUnitsVisible)) {

      if (applSharedData != null) {
        Boolean flagVisible = (Boolean) getValueAt(paramIndex_PhysUnitsVisible, 1);
        applSharedData.atz_UnitsRef.setVisiblePreferred(flagVisible);
      }

    }
    
    if ((evt.getFirstRow() <= paramIndex_DisplayDoubleVals) && (evt.getLastRow() >= paramIndex_DisplayDoubleVals)) {

     if (internalSetFormatStr == false) { /* only listen to change, if we did not just trigger the change internally (below) */

       if (applSharedData != null) {
         String formatStr = (String) getValueAt(paramIndex_DisplayDoubleVals, 1);

         try {
           applSharedData.atz_UnitsRef.setFormatRender(formatStr);
           internalSetFormatStr = false; /* if we were successful then no internal issues */
         } catch (Exception e) {
           String oldFormatStr = applSharedData.atz_UnitsRef.getFormatRenderStr();
           setValueAt(oldFormatStr, paramIndex_DisplayDoubleVals, 1);
           internalSetFormatStr = true;
         }

       }

     } else {
       internalSetFormatStr = false; /* internal trigger only one loop, so disable the flag */
     }

   }

  }

   /* ====================================================== */
  /* ==================== XML codes ====================== */
  public void importData(String filename, int fileType) {

  }

  public void exportData(String filename, int fileType) {

  }

  @Override
  public void exportToXML(BufferedWriter fid) {

    try {

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_SELM_Preferences_TableDisplay);

      String formatStr = (String) getValueAt(paramIndex_DisplayDoubleVals, 1);
      Atz_XML_Helper.writeXMLData(fid, tagXML_DisplayDoubleVals, formatStr);

      Boolean flagVisible = (Boolean) getValueAt(paramIndex_PhysUnitsVisible, 1);
      Atz_XML_Helper.writeXMLData(fid, tagXML_PhysUnitsVisible, flagVisible);
           
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_SELM_Preferences_TableDisplay);

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
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
    } else if (qName.equals(tagXML_PhysUnitsVisible)) {
      boolean val = Boolean.parseBoolean(xmlAttributes.getValue("value"));
      Boolean flagVisible = (Boolean) getValueAt(paramIndex_PhysUnitsVisible, 1);
      flagVisible = val;
      setValueAt(flagVisible, paramIndex_PhysUnitsVisible, 1);
    } else if (qName.equals(tagXML_DisplayDoubleVals)) {
      String newFormatStr = (String) xmlAttributes.getValue("value");
      String formatStr    = (String) getValueAt(paramIndex_DisplayDoubleVals, 1);
      formatStr           = newFormatStr;
      setValueAt(formatStr,paramIndex_DisplayDoubleVals, 1);    
    }

  }

  @Override
  public Object XML_getData() {
    return this; /* WARNING: not safe for lists */
  }

  /* ==================== XML parser ====================== */
  /* ====================================================== */


 
}







