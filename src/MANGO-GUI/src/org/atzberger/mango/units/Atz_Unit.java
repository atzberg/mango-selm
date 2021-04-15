package org.atzberger.mango.units;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 *
 * Handles tracking physical unit labels and the conversion of quantities.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Unit implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  public static final int    DIMLESS     = 0;
  public static final String DIMLESS_STR = "dimless";

  public static final int    LENGTH      = 1;
  public static final String LENGTH_STR  = "length";

  public static final int    TIME        = 2;
  public static final String TIME_STR    = "time";

  public static final int    MASS        = 3;
  public static final String MASS_STR    = "mass";

  public static final int    TEMPERATURE     = 4;
  public static final String TEMPERATURE_STR = "temperature";

  public String unitLongName    = "nanometer";
  public String unitShortName   = "nm";
  
  public double numberBaseUnits = 0.0;
  public String baseUnitName    = "";
  public int    unitType        = 0;

  /* XML related data */
  String     xmlString     = "";
  Attributes xmlAttributes = null;
  
  public Atz_Unit() {
    unitLongName    = "nanometer";
    unitShortName   = "nm";
    numberBaseUnits = 1.0;  /* in terms of base units (used for conversion) */
    baseUnitName    = "nm";
  }

  public Atz_Unit(String unitLongName_in, String unitShortName_in, double numberBaseUnits_in, String baseUnitName_in, int unitType_in) {
    unitLongName    = unitLongName_in;
    unitShortName   = unitShortName_in;
    numberBaseUnits = numberBaseUnits_in;
    baseUnitName    = baseUnitName_in;
    unitType        = unitType_in;
  }

  @Override
  public Object clone() {   
    return new Atz_Unit(unitLongName, unitShortName, numberBaseUnits, baseUnitName, unitType);
  }


  /* ============= XML related methods =============== */
  public void exportToXML(BufferedWriter fid) {

    Atz_XML_Helper.writeXMLStartTag(fid, this.getClass().getSimpleName());

    Atz_XML_Helper.writeXMLData(fid, "baseUnitName", baseUnitName);
    Atz_XML_Helper.writeXMLData(fid, "numberBaseUnits", numberBaseUnits);
    Atz_XML_Helper.writeXMLData(fid, "unitLongName", unitLongName);
    Atz_XML_Helper.writeXMLData(fid, "unitShortName", unitShortName);
    Atz_XML_Helper.writeXMLData(fid, "unitType", unitType);

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
    xmlString     = "";
    xmlAttributes = attributes;

    if (qName.equals(this.getClass().getSimpleName())) {
      sourceHandler.parseNextTagWithDataHandler(this); /* use current, technically this statement is not needed */
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
    
    if (qName.equals("baseUnitName")) {
      baseUnitName = (String) xmlAttributes.getValue("value");
    }

    if (qName.equals("numberBaseUnits")) {
      numberBaseUnits =  Double.parseDouble(xmlAttributes.getValue("value"));
    }

    if (qName.equals("unitLongName")) {
      unitLongName = (String) xmlAttributes.getValue("value");
    }

    if (qName.equals("unitShortName")) {
      unitShortName = (String) xmlAttributes.getValue("value");
    }

    if (qName.equals("unitType")) {
      unitType = Integer.parseInt(xmlAttributes.getValue("value"));
    }
    
  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }


}
