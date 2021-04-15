package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.Atz3D_Model;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;

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
public class SELM_Eulerian_NULL extends SELM_Eulerian implements SELM_EulerianRenderView {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();
  
  private int      num_dim   = 3;
  private double[] ptsX      = new double[0];
  private int[]    ptsID     = new int[0];
  
  SELM_Eulerian_NULL() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    EulerianName    = "NULL";
    EulerianTypeStr = thisClassName.replace(superClassName + "_", "");


  }

  @Override
  public Object clone() {
    SELM_Eulerian_NULL Eulerian_copy = new SELM_Eulerian_NULL();

    Eulerian_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    Eulerian_copy.EulerianName    = this.EulerianName.toString();
    Eulerian_copy.EulerianTypeStr = this.EulerianTypeStr.toString();
    
    return (Object) Eulerian_copy;
  }
 
  @Override
  public String getRenderTag() {
    return atz3D_RENDER_TAG_EULERIAN;
  }

  @Override
  public void renderToModel3D(Atz3D_Model model3D) {

  }

  /* ====================================================== */
  /* ==================== XML codes ======================= */
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

  public void exportToXML(BufferedWriter fid) {

    try {
      //String typeStr = this.getClass().getSimpleName();
      //typeStr = typeStr.replace("TableModel_Eulerian_", "");

      Atz_XML_Helper.writeXMLStartTag(fid, "SELM_Eulerian");

      Atz_XML_Helper.writeXMLData(fid, tagXML_EulerianName, this.EulerianName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_EulerianTypeStr, this.EulerianTypeStr);

      /* end tag */
      Atz_XML_Helper.writeXMLEndTag(fid, "SELM_Eulerian");

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }

  @Override
  public void importData(String filename, int flagType) {

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
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

    
}



