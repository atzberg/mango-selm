package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
import org.atzberger.mango.atz3d.Atz3D_Element_Points;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
public class SELM_Eulerian_UNIFORM1_FFTW3 extends SELM_Eulerian implements SELM_EulerianRenderView, Atz_XML_SAX_DataHandlerInterface {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();
  
  private double   meshDeltaX;
  private int[]    numMeshPtsPerDir = new int[3];
  private double[] meshCenterX0     = new double[3];

  public   String  tagXML_meshDeltaX       = "meshDeltaX";
  public   String  tagXML_numMeshPtsPerDir = "numMeshPtsPerDir";
  public   String  tagXML_meshCenterX0     = "meshCenterX0";

  /* data change events */
  public final String DATA_CHANGE_REMOVE_PT  = "REMOVE_PT";
  public final String DATA_CHANGE_ADD_PT     = "ADD_PT";

  /* plotting related data */
  private Random randGen = new Random();
  private int color_r = 25 + (int)(200*randGen.nextDouble());
  private int color_g = 25 + (int)(200*randGen.nextDouble());
  private int color_b = 25 + (int)(200*randGen.nextDouble());

  private Color    plotColor = new Color(color_r, color_g, color_b); /* pick color at random */

  private boolean flagVisible = true;

  /* index of element types within the representation of this type */
  public final int     atz3D_Index_Points = 0;

  Atz3D_Element_Points atz3D_Element_Points = new Atz3D_Element_Points();
  
  SELM_Eulerian_UNIFORM1_FFTW3() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    EulerianName    = "Your Name Here";
    EulerianTypeStr = thisClassName.replace(superClassName + "_", "");
  
  }

  @Override
  public Object clone() {
    
    SELM_Eulerian_UNIFORM1_FFTW3 Eulerian_copy = new SELM_Eulerian_UNIFORM1_FFTW3();

    Eulerian_copy.EulerianName     = this.EulerianName.toString();
    Eulerian_copy.EulerianTypeStr  = this.EulerianTypeStr.toString();

    Eulerian_copy.meshDeltaX       = meshDeltaX;
    Eulerian_copy.numMeshPtsPerDir = numMeshPtsPerDir.clone();
    Eulerian_copy.meshCenterX0     = meshCenterX0.clone();

    Eulerian_copy.plotColor        = new Color(plotColor.getRGB());
    Eulerian_copy.flagVisible      = flagVisible;
    
    return (Object) Eulerian_copy;
  }

  public double getMeshDeltaX() {
    return meshDeltaX;
  }

  public int[] getNumMeshPtsPerDir() {
    return numMeshPtsPerDir;
  }

  public double[] getMeshCenterX0() {
    return meshCenterX0;
  }

  public void setMeshCenterX0(double[] value) {
    meshCenterX0 = value.clone();
  }

  public void setNumMeshPtsPerDir(int[] value) {
    numMeshPtsPerDir = value.clone();
  }

  public void setMeshDeltaX(double value) {
    meshDeltaX = value;
  }

  public void setPlotColor(Color color_in) {
    plotColor = color_in;
  }

  public Color getPlotColor() {
    return plotColor;
  }

  public void setVisible(boolean flagVisible_in) {
    flagVisible = flagVisible_in;
  }

  public boolean isVisible() {
    return flagVisible;
  }
  
  public String getRenderTag() {
    return atz3D_RENDER_TAG_EULERIAN;
  }

  public void renderToModel3D(Atz3D_Model model3D) {

    /* add points to represent the current eulerian structure */
    //Color plotColor = Color.green;
    if (isVisible() == true) {  /* only add if this DOF is visible */
      model3D.addElements(getAtz3DElementRepresentation());
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


    /* ====================================================== */
  /* ==================== XML codes ======================= */
  public void importData(String filename, int fileType) {

  }

  public void exportData(String filename, int fileType) {

  }

  public void exportToXML(BufferedWriter fid) {

    try {
      String typeStr = this.getClass().getSimpleName();
      typeStr.replace("SELM_Eulerian_", "");

      Atz_XML_Helper.writeXMLStartTag(fid, "SELM_Eulerian");

      Atz_XML_Helper.writeXMLData(fid, tagXML_EulerianName, this.EulerianName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_EulerianTypeStr, this.EulerianTypeStr);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_meshDeltaX, getMeshDeltaX());

      Atz_XML_Helper.writeXMLData(fid, tagXML_numMeshPtsPerDir, getNumMeshPtsPerDir());

      Atz_XML_Helper.writeXMLData(fid, tagXML_meshCenterX0, getMeshCenterX0());

      Atz_XML_Helper.writeXMLEndTag(fid, "SELM_Eulerian");

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
    } else if (qName.equals(tagXML_meshDeltaX)) {
      setMeshDeltaX(Double.parseDouble(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_numMeshPtsPerDir)) {
      setNumMeshPtsPerDir(Atz_XML_Helper.parseIntArrayFromString(xmlString));
    } else if (qName.equals(tagXML_meshCenterX0)) {
      setMeshCenterX0(Atz_XML_Helper.parseDoubleArrayFromString(xmlString));
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */

  
}



