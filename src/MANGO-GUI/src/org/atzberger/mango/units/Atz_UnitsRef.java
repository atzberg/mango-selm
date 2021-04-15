package org.atzberger.mango.units;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_SAX_ListDataHandler;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Represents what units are to be used for each of the fundamental unit types.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_UnitsRef implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  public Atz_Unit[] unitList          = null;
  
  public HashMap   unitsSelected    = new HashMap();
  public Boolean   visiblePreferred = true;

  public DecimalFormat  formatRender;
  public DecimalFormat  formatEdit;

  /* XML data */
  String     xmlString     = "";
  Attributes xmlAttributes = null;
  
  public Atz_UnitsRef() {

    int numUnits      = 4;
    int i             = 0;
    double baseUnits  = 0.0;
    int length_I      = 0;
    int time_I        = 0;
    int mass_I        = 0;
    int temperature_I = 0;

    setDefaultFormatting(); /* number formatting to use */
    
    unitList      = new Atz_Unit[numUnits];
    unitList[i++] = new Atz_Unit("dimensionless",  "length",  1.0, "dimensionless", Atz_Unit.LENGTH);
    length_I = i - 1;    

    unitList[i++] = new Atz_Unit("dimensionless", "time",   1.0, "dimensionless", Atz_Unit.TIME);
    time_I = i - 1;

    unitList[i++] = new Atz_Unit("dimensionless",  "mass",  1.0, "dimensionless", Atz_Unit.MASS);
    mass_I = i - 1;
    
    unitList[i++] = new Atz_Unit("dimensionless", "temperature",   1.0, "dimensionless", Atz_Unit.TEMPERATURE);
    temperature_I = i - 1;
    
    /* setup the units currently selected for each category */
    unitsSelected.put(Atz_Unit.LENGTH_STR, unitList[length_I]);
    unitsSelected.put(Atz_Unit.TIME_STR,   unitList[time_I]);
    unitsSelected.put(Atz_Unit.MASS_STR,   unitList[mass_I]);
    unitsSelected.put(Atz_Unit.TEMPERATURE_STR, unitList[temperature_I]);

    setVisiblePreferred(false);
    
  }

  public void setVisiblePreferred(boolean val) {
    visiblePreferred = val;
  }

  public Boolean getVisiblePreferred() {
    return visiblePreferred;
  }

  public int getIndexUnitSelected(String unitType) {
    Atz_Unit unit_selected;
    int selectI;

    unit_selected = (Atz_Unit)unitsSelected.get(unitType);
    selectI       = -1;
    for (int k = 0; k < unitList.length; k++) {
      Atz_Unit unit = unitList[k];
      if (unit.equals(unit_selected)) {
        selectI = k;
      }
    }

    return selectI;
  }

  @Override
  public Object clone() {

    Atz_UnitsRef unitsRef = new Atz_UnitsRef();

    unitsRef.setVisiblePreferred(this.visiblePreferred);
    unitsRef.unitsSelected = (HashMap) unitsSelected.clone();
    unitsRef.unitList      = (Atz_Unit[]) unitList.clone();

    return unitsRef;
    
  }


  public void setDefaultFormatting() {
    String pattern;

    pattern      = "0.####E0##";
    formatRender = new DecimalFormat(pattern);

    pattern      = "0.########E0##";
    formatEdit   = new DecimalFormat(pattern);

  }

  public void setFormatRender(String formatRender_str) {
    /* check formatting string is valid */
    setFormatting(new DecimalFormat(formatRender_str), formatEdit);
  }

  public void setFormatting(String formatRender_str,String formatEdit_str) {
    /* check formatting string is valid */
    setFormatting(new DecimalFormat(formatRender_str), new DecimalFormat(formatEdit_str));
  }

  public void setFormatting(DecimalFormat formatRender_in, DecimalFormat formatEdit_in) {
    formatRender = formatRender_in;
    formatEdit   = formatEdit_in;
  }

  public DecimalFormat getFormatRender() {
    return formatRender;
  }

  public String getFormatRenderStr() {
    return formatRender.toPattern();
  }

  public DecimalFormat getFormatEdit() {
    return formatEdit;
  }

  /* ================================================================== */
  /* ========================= XML related codes ====================== */
  public void exportToXML(BufferedWriter fid) {
    
    Atz_XML_Helper.writeXMLStartTag(fid, this.getClass().getSimpleName());

    Atz_XML_Helper.writeXMLStartTag(fid, "unitList");

    for (int k = 0; k < unitList.length; k++) {
      Atz_Unit unit = unitList[k];
      Atz_XML_Helper.writeXMLData(fid, unit);
    }

    Atz_XML_Helper.writeXMLEndTag(fid, "unitList");

    /* determine indices of selected units */
    String unitType;

    unitType = Atz_Unit.LENGTH_STR;
    Atz_XML_Helper.writeXMLData(fid, "unitsSelected_" + unitType, getIndexUnitSelected(unitType));

    unitType = Atz_Unit.TIME_STR;
    Atz_XML_Helper.writeXMLData(fid, "unitsSelected_" + unitType, getIndexUnitSelected(unitType));

    unitType = Atz_Unit.MASS_STR;
    Atz_XML_Helper.writeXMLData(fid, "unitsSelected_" + unitType, getIndexUnitSelected(unitType));

    unitType = Atz_Unit.TEMPERATURE_STR;
    Atz_XML_Helper.writeXMLData(fid, "unitsSelected_" + unitType, getIndexUnitSelected(unitType));

    Atz_XML_Helper.writeXMLData(fid, "visiblePreferred", visiblePreferred);

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

    String unitType;

    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;

    if (qName.equals(this.getClass().getSimpleName())) {
      //sourceHandler.parseNextTagWithDataHandler(this); /* use current, technically this statement is not needed */
    }

    if (qName.equals("unitList")) {
      
      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new Atz_Unit());

      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);
      
    }

    unitType = Atz_Unit.LENGTH_STR;
    if (qName.equals("unitsSelected_" + unitType)) {
      sourceHandler.parseNextTagWithDataHandler(this); /* not technically required */
    }

    unitType = Atz_Unit.TIME_STR;
    if (qName.equals("unitsSelected_" + unitType)) {
      sourceHandler.parseNextTagWithDataHandler(this); /* not technically required */
    }

    unitType = Atz_Unit.MASS_STR;
    if (qName.equals("unitsSelected_" + unitType)) {
      sourceHandler.parseNextTagWithDataHandler(this); /* not technically required */
    }

    unitType = Atz_Unit.TEMPERATURE_STR;
    if (qName.equals("unitsSelected_" + unitType)) {
      sourceHandler.parseNextTagWithDataHandler(this); /* not technically required */
    }

    if (qName.equals("visiblePreferred")) {
      sourceHandler.parseNextTagWithDataHandler(this); /* not technically required */
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
      
    }

    if (qName.equals("unitList")) {

      /* get the list data handler, which was last used to parse this tag data */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();

      /* get the list data and add it to the current data structure */
      HashMap tagDataLists  = listDataHandler.getTagDataLists();
      ArrayList listOfUnits = (ArrayList) tagDataLists.get(Atz_Unit.class.getSimpleName()); /* get list for particular class */

      unitList = new Atz_Unit[listOfUnits.size()];
      for (int k = 0; k < unitList.length; k++) {
        Atz_Unit unit = (Atz_Unit) listOfUnits.get(k);
        unitList[k] = unit;
      }
      
    }

    if ( (qName.equals("unitsSelected_" + Atz_Unit.LENGTH_STR)) ||
         (qName.equals("unitsSelected_" + Atz_Unit.TIME_STR))   ||
         (qName.equals("unitsSelected_" + Atz_Unit.MASS_STR))   ||
         (qName.equals("unitsSelected_" + Atz_Unit.TEMPERATURE_STR))  ) {
      String unitType      = qName.replace("unitsSelected_", "");
      int    selectedIndex = Integer.parseInt(xmlAttributes.getValue("value"));
      
      unitsSelected.remove(unitType);
      unitsSelected.put(unitType, unitList[selectedIndex]);
    }

    if (qName.equals("visiblePreferred")) {
      visiblePreferred = Boolean.parseBoolean(xmlAttributes.getValue("value"));      
    }

  }
  
  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }


  /* ========================= XML related codes ====================== */
  /* ================================================================== */
 
}