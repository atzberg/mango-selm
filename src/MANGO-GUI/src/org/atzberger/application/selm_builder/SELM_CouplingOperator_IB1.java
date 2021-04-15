package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
public class SELM_CouplingOperator_IB1 extends SELM_CouplingOperator {

  String weightTableFilename;
  double kernelSize;

  /* XML */
  String tagXML_weightTableFilename = "weightTableFilename";
  String tagXML_kernelSize          = "kernelSize";

  /* get and set methods */
  public double getKernelSize() {
    return kernelSize;
  }

  public void setKernelSize(double k) {
    kernelSize = k;
  }

  public String getWeightTableFilename() {
    return weightTableFilename;
  }

  public void setWeightTableFilename(String filename) {
    weightTableFilename = filename;
  }

  /* constructors */
  SELM_CouplingOperator_IB1() {
    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    CouplingOpName    = "Your Name Here";
    CouplingOpTypeStr = thisClassName.replace(superClassName + "_", "");
  }

  @Override
  public Object clone() {
    SELM_CouplingOperator_IB1 CouplingOp_copy = new SELM_CouplingOperator_IB1();

    CouplingOp_copy.CouplingOpName      = this.CouplingOpName.toString();
    CouplingOp_copy.CouplingOpTypeStr   = this.CouplingOpTypeStr.toString();

    CouplingOp_copy.weightTableFilename = weightTableFilename.toString();
    CouplingOp_copy.kernelSize          = kernelSize;
    CouplingOp_copy.flagVisible         = flagVisible;

    return (Object) CouplingOp_copy;
  }

  
  /* ====================================================== */
  /* ==================== XML codes ====================== */
  public void importData(String filename, int fileType) {

  }

  @Override
  public void exportData(String filename, int flagType) {

    switch (flagType) {

      case FILE_TYPE_XML:

        try {

          // Create file
          FileWriter fstream = new FileWriter(filename);
          BufferedWriter fid = new BufferedWriter(fstream);

          Atz_XML_Helper.writeXMLHeader(fid, "1.0", "UTF-8");

          exportToXML(fid);

          //Close the fidput stream
          fid.close();

        } catch (Exception e) {//Catch exception if any
          e.printStackTrace();
          System.out.println(e);
          //System.err.println("Error: " + e.getMessage());
        }

        break; /* end XML */

    } /* end switch */

  }

  @Override
  public void exportToXML(BufferedWriter fid) {

    try {
      
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_SELM_CouplingOP);

      Atz_XML_Helper.writeXMLData(fid, tagXML_CouplingOpName,    CouplingOpName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_CouplingOpTypeStr, CouplingOpTypeStr);

      Atz_XML_Helper.writeXMLData(fid, tagXML_weightTableFilename, weightTableFilename);
      Atz_XML_Helper.writeXMLData(fid, tagXML_kernelSize, kernelSize);

      /*
      Atz_XML_Helper.writeXMLStartTag(fid, "pairList_lagrangianI1");
      SELM_Lagrangian[] listLagrangianI1 = this.getPairList_lagrangianI1();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        Atz_XML_Helper.writeXMLData(fid, "LagrangianName", listLagrangianI1[k].LagrangianName);
      }
      Atz_XML_Helper.writeXMLEndTag(fid, "pairList_lagrangianI1");
       */
      
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_SELM_CouplingOP);

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
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
    } else if (qName.equals(tagXML_weightTableFilename)) {
      weightTableFilename = xmlAttributes.getValue("value");
    } else if (qName.equals(tagXML_kernelSize)) {
      kernelSize = Double.parseDouble(xmlAttributes.getValue("value"));
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML parser ====================== */
  /* ====================================================== */


}
