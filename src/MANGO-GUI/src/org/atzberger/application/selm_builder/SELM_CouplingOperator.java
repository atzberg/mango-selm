package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import org.xml.sax.Attributes;

import org.xml.sax.SAXException;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_CouplingOperator implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  static final int FILE_TYPE_XML = 1;
  public static String  atz3D_RENDER_TAG_COUPLING_OPERATOR = "COUPLING_OPERATOR";

  String  CouplingOpTypeStr = "NO TYPE ASSIGNED YET";
  String  CouplingOpName    = "Your Name Here";
   
  boolean flagVisible = true;

  /* XML */
  protected String     xmlString     = "";
  protected Attributes xmlAttributes = null;

  static public String     tagXML_SELM_CouplingOP    = "SELM_CouplingOperator";
  static public String     tagXML_CouplingOpName     = "CouplingOperatorName";
  static public String     tagXML_CouplingOpTypeStr  = "CouplingOperatorTypeStr";
  static public String     tagXML_renderVisible      = "renderVisible";

  public boolean flag_Gen_LAMMPS_XML_Files = false;
  public String  gen_LAMMPS_XML_Files_BasePath = "";
    
  public SELM_CouplingOperator() {

  }

  @Override
  public Object clone() {
    SELM_CouplingOperator CouplingOp_copy = new SELM_CouplingOperator();

    CouplingOp_copy.CouplingOpName    = this.CouplingOpName.toString();
    CouplingOp_copy.CouplingOpTypeStr = this.CouplingOpTypeStr.toString();

    return (Object) CouplingOp_copy;
  }


  public void setTypeStr(String typeStr) {
    CouplingOpTypeStr = typeStr;
  }

  public String getTypeStr() {
    return CouplingOpTypeStr;
  }

  public void setName(String name) {
    CouplingOpName = name;
  }

  public String getName() {
    return CouplingOpName;
  }


  /* ====================================================== */
  /* ==================== XML codes ====================== */  
  public void importData(String filename, int fileType) {

  }
  
  public void exportData(String filename, int fileType) {
    
  }

  @Override
  public void exportToXML(BufferedWriter fid) {

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
    } else if (qName.equals(tagXML_CouplingOpName)) {
      //LagrangianName = xmlAttributes.getValue("value");
    } else if (qName.equals(tagXML_CouplingOpTypeStr)) {
      //String typeStr = xmlAttributes.getValue("value");  /* maybe check compatible here */
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  public void setFlagGenLAMMPS_XML_Files(boolean val, String basePath) {
    flag_Gen_LAMMPS_XML_Files     = val;
    gen_LAMMPS_XML_Files_BasePath = basePath;
  }

  public boolean getFlagGenLAMMPS_XML_Files() {
    return flag_Gen_LAMMPS_XML_Files;
  }

  /* ==================== XML parser ====================== */
  /* ====================================================== */


}
