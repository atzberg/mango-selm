package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Color;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
import java.awt.Color;
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
public class TableModel_Preferences_Rendering extends TableModel_Properties1_Default
  implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface, SELM_RenderView, TableModelListener {

  application_SharedData applSharedData = null;

  private final static boolean DEBUG = false;

  public static String  atz3D_RENDER_TAG_PREFERENCES_RENDERING = "PREFERENCES_RENDERING";

  /* table data */
  public static String paramName_Background1    = "Background Color 1";
  public static int    paramIndex_Background1   = -1;
  public static String tagXML_Background1       = "BackgroundColor1";

  public static String paramName_Background2    = "Background Color 2";
  public static int    paramIndex_Background2   = -1;
  public static String tagXML_Background2       = "BackgroundColor2";

  public static String paramName_AxisVisible   = "Axis Visible";
  public static int    paramIndex_AxisVisible  = -1;
  public static String tagXML_AxisVisible      = "AxisVisible";
 
  public static String paramName_AxisColor     = "Axis Color";
  public static int    paramIndex_AxisColor    = -1;
  public static String tagXML_AxisColor        = "AxisColor";

  public static String paramName_AxisLabels    = "Axis Labels";
  public static int    paramIndex_AxisLabels   = -1;
  public static String tagXML_AxisLabels       = "AxisLabels";
  
  public static String paramName_AxisLabelColor   = "Axis Label Color";
  public static int    paramIndex_AxisLabelColor  = -1;
  public static String tagXML_AxisLabelColor      = "AxisLabelColor";

 
  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public static  String tagXML_SELM_Preferences = "SELM_Preferences";
  public static  String tagXML_SELM_Preferences_Rendering = tagXML_SELM_Preferences + "_Rendering";
   
  /* constructors */
  public TableModel_Preferences_Rendering() {

    /* initialize the values */
    setToDefaultValues();
            
    /* setup data change listener */
    this.addTableModelListener(this);

    //this.fireTableDataChanged();
    //setValueAt(getValueAt(paramIndex_AxisVisible,1), paramIndex_AxisVisible,1);

  }

  public void setToDefaultValues() {

    int      i                = 0;
    
    setValueAt(paramName_Background1, i,0, NOT_EDITABLE);
    setValueAt(new TableData_Color(0,153,255), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_Background1 = i;
    i++;

    setValueAt(paramName_Background2, i,0, NOT_EDITABLE);
    setValueAt(new TableData_Color(255,255,255), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_Background2 = i;
    i++;

    setValueAt(paramName_AxisVisible, i,0, NOT_EDITABLE);
    setValueAt(new Boolean(true), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_AxisVisible = i;
    i++;

    setValueAt(paramName_AxisColor, i,0, NOT_EDITABLE);
    setValueAt(new TableData_Color(183,183,183), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_AxisColor = i;
    i++;

    setValueAt(paramName_AxisLabels, i,0, NOT_EDITABLE);
    setValueAt(new String[] {"x", "y", "z"}, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_AxisLabels = i;
    i++;

    setValueAt(paramName_AxisLabelColor, i,0, NOT_EDITABLE);
    setValueAt(new TableData_Color(70,70,226), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_AxisLabelColor = i;
    i++;

  }


 public void setApplSharedData(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;

    this.fireTableDataChanged();
    
    /* update the table entries to reflect new application data now available */
  }

  /* get / set methods */
  public Color getBackground1() {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_Background1, col);

    return (Color) data.color;
  }

  public void setBackground1(Color color_in) {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_Background1, col);

    data.color = color_in;

    setValueAt(data, paramIndex_Background1, col);
  }

  /* get / set methods */
  public Color getBackground2() {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_Background2, col);

    return (Color) data.color;
  }

  public void setBackground2(Color color_in) {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_Background2, col);

    data.color = color_in;

    setValueAt(data, paramIndex_Background2, col);
  }

  public boolean getAxisVisible() {
    int col = 1;
    return (Boolean) getValueAt(paramIndex_AxisVisible, col);
  }

  public void setAxisVisible(boolean val) {
    int col = 1;
    setValueAt((Boolean)val, paramIndex_AxisVisible, col);
  }

  public String[] getAxisLabels() {
    int col = 1;
    return (String[]) getValueAt(paramIndex_AxisLabels, col);
  }

  public void setAxisLabels(String[] newLabels) {
    
    int      col       = 1;
    String[] curLabels = (String[]) getValueAt(paramIndex_AxisLabels, col);

    curLabels[0] = newLabels[0];
    curLabels[1] = newLabels[1];
    curLabels[2] = newLabels[2];

    setValueAt(curLabels,paramIndex_AxisLabels, col);
    
  }

  
  /* get / set methods */
  public Color getAxisColor() {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_AxisColor, col);

    return (Color) data.color;
  }

  public void setAxisColor(Color color_in) {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_AxisColor, col);

    data.color = color_in;

    setValueAt(data, paramIndex_AxisColor, col);
  }

  /* get / set methods */
  public Color getAxisLabelColor() {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_AxisLabelColor, col);

    return (Color) data.color;
  }

  public void setAxisLabelColor(Color color_in) {
    int col = 1;

    TableData_Color data = (TableData_Color)getValueAt(paramIndex_AxisLabelColor, col);

    data.color = color_in;

    setValueAt(data, paramIndex_AxisLabelColor, col);
  }


  @Override
  public void tableChanged(TableModelEvent evt) {

    if ((evt.getFirstRow() <= paramIndex_AxisLabelColor) && (evt.getLastRow() >= paramIndex_Background1)) {
    
      if (applSharedData != null) {
        applSharedData.jPanel_Model_View_Composite.setRenderBackgroundColors(getBackground1(), getBackground2());
        applSharedData.jPanel_Model_View_Composite.setRenderAxisInfo(getAxisVisible(), getAxisLabels(), getAxisColor(), getAxisLabelColor());
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

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_SELM_Preferences_Rendering);

      Atz_XML_Helper.writeXMLData(fid, tagXML_Background1,    new Atz_XML_Helper_Handler_Color(getBackground1()));
      Atz_XML_Helper.writeXMLData(fid, tagXML_Background2,    new Atz_XML_Helper_Handler_Color(getBackground2()));
      Atz_XML_Helper.writeXMLData(fid, tagXML_AxisVisible,    getAxisVisible());
      Atz_XML_Helper.writeXMLData(fid, tagXML_AxisColor,      getAxisColor());
      String[] axisLabels = getAxisLabels();
      Atz_XML_Helper.writeXMLData(fid, tagXML_AxisLabels + "1", axisLabels[0]);
      Atz_XML_Helper.writeXMLData(fid, tagXML_AxisLabels + "2", axisLabels[1]);
      Atz_XML_Helper.writeXMLData(fid, tagXML_AxisLabels + "3", axisLabels[2]);
      Atz_XML_Helper.writeXMLData(fid, tagXML_AxisLabelColor, getAxisLabelColor());

      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_SELM_Preferences_Rendering);

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

    if (qName.equals(tagXML_Background1)) {
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
    }

    if (qName.equals(tagXML_Background2)) {
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
    }

    if (qName.equals(tagXML_AxisColor)) {
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
    }

    if (qName.equals(tagXML_AxisLabelColor)) {
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
    }

  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(SELM_Lagrangian.class.getSimpleName())) {
      /* check all entries set */
    } else if (qName.equals(tagXML_Background1)) {
      Atz_XML_Helper_Handler_Color colorHandler = (Atz_XML_Helper_Handler_Color) sourceHandler.getLastUsedDataHandler();
      setBackground1((Color)colorHandler.XML_getData());
    } else if (qName.equals(tagXML_Background2)) {
      Atz_XML_Helper_Handler_Color colorHandler = (Atz_XML_Helper_Handler_Color) sourceHandler.getLastUsedDataHandler();
      setBackground2((Color)colorHandler.XML_getData());
    } else if (qName.equals(tagXML_AxisVisible)) {
      setAxisVisible(Boolean.parseBoolean(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_AxisLabels + "1")) {
      
      String label        = (String) xmlAttributes.getValue("value");
      String[] axisLabels = getAxisLabels();
      
      axisLabels[0]       = label;

      setAxisLabels(axisLabels);

    } else if (qName.equals(tagXML_AxisLabels + "2")) {

      String label        = (String) xmlAttributes.getValue("value");
      String[] axisLabels = getAxisLabels();

      axisLabels[1]       = label;

      setAxisLabels(axisLabels);

    } else if (qName.equals(tagXML_AxisLabels + "3")) {

      String label        = (String) xmlAttributes.getValue("value");
      String[] axisLabels = getAxisLabels();

      axisLabels[2]       = label;

      setAxisLabels(axisLabels);

    } else if (qName.equals(tagXML_AxisColor)) {
      Atz_XML_Helper_Handler_Color colorHandler = (Atz_XML_Helper_Handler_Color) sourceHandler.getLastUsedDataHandler();
      setAxisColor((Color)colorHandler.XML_getData());
    } else if (qName.equals(tagXML_AxisLabelColor)) {
      Atz_XML_Helper_Handler_Color colorHandler = (Atz_XML_Helper_Handler_Color) sourceHandler.getLastUsedDataHandler();
      setAxisLabelColor((Color)colorHandler.XML_getData());
    }

  }

  @Override
  public Object XML_getData() {
    return this; /* WARNING: not safe for lists */
  }

  /* ==================== XML parser ====================== */
  /* ====================================================== */



  /* =========================================================== */
  /* ==================== SELM RenderView ====================== */
  public String getRenderTag() {
    return atz3D_RENDER_TAG_PREFERENCES_RENDERING;
  }

  public void renderToModel3D(Atz3D_Model model3D) {

    /* add points to represent the current lagrangian structure */
    //Color plotColor = Color.green;
    if (getAxisVisible() == true) {  /* only add if this DOF is visible */
      //model3D.addElements(getAtz3DElementRepresentation());
    }

  }

  public Atz3D_Element[] getAtz3DElementRepresentation() {
    Atz3D_Element[] list = new Atz3D_Element[1];

    //atz3D_Element_Points.setPlotColor(plotColor);
    //atz3D_Element_Points.setPoints(ptsX);

    //atz3D_Index_Points       = 0;
    //list[atz3D_Index_Points] = atz3D_Element_Points;

    return list;
  }

  /* ==================== SELM RenderView ====================== */
  /* =========================================================== */
 
}







