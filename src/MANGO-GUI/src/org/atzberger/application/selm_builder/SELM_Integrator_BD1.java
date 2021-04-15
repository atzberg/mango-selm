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
public class SELM_Integrator_BD1 extends SELM_Integrator
        implements SELM_IntegratorRenderView, Atz_XML_SAX_DataHandlerInterface {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();

  private double   timeStep    = -1;
  private double   shearRate   = -1;
  private int      shearDir    = 2;
  private int      shearVelDir = 0;
  private int      saveSkip    = 1;
  
  /* data change events */
  //public final String DATA_CHANGE_REMOVE_PT  = "REMOVE_PT";
  
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

  /* XML */
  public   String tagXML_timeStep    = "timeStep";
  public   String tagXML_shearRate   = "shearRate";
  public   String tagXML_shearDir    = "shearDir";
  public   String tagXML_shearVelDir = "shearVelDir";
  public   String tagXML_saveSkip    = "saveSkip";
  
  public SELM_Integrator_BD1() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    IntegratorName    = "Your Name Here";
    IntegratorTypeStr = thisClassName.replace(superClassName + "_", "");

    
  }

  @Override
  public Object clone() {
    SELM_Integrator_BD1 Integrator_copy = new SELM_Integrator_BD1();

    Integrator_copy.IntegratorName    = this.IntegratorName.toString();
    Integrator_copy.IntegratorTypeStr = this.IntegratorTypeStr.toString();
   
    Integrator_copy.timeStep    = this.timeStep;
    Integrator_copy.shearDir    = this.shearDir;
    Integrator_copy.shearVelDir = this.shearVelDir;
    Integrator_copy.saveSkip    = this.saveSkip;
    Integrator_copy.plotColor   = this.plotColor; /* WARNING: need to clone() */

    return (Object) Integrator_copy;
  }

  public void setTimeStep(double value) {
    timeStep = value;
  }

  public void setShearRate(double value) {
    shearRate = value;
  }

  public void setShearDir(int value) {
    shearDir = value;
  }

  public void setShearVelDir(int value) {
    shearVelDir = value;
  }

  public void setSaveSkip(int value) {
    saveSkip = value;
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

  public double getTimeStep() {
    return timeStep;
  }

  public double getShearRate() {
    return shearRate;
  }

  public int getShearDir() {
    return shearDir;
  }

  public int getShearVelDir() {
    return shearVelDir;
  }

  public int getSaveSkip() {
    return saveSkip;
  }

  public String getRenderTag() {
    return atz3D_RENDER_TAG_INTEGRATOR;
  }

  public void renderToModel3D(Atz3D_Model model3D) {

    /* add points to represent the current integrator structure */
    //Color plotColor = Color.green;
    if (isVisible() == true) {  /* only add if this DOF is visible */
      model3D.addElements(getAtz3DElementRepresentation());
    }
      
  }

  public Atz3D_Element[] getAtz3DElementRepresentation() {
    Atz3D_Element[] list = new Atz3D_Element[1];

//    atz3D_Element_Points.setPlotColor(plotColor);
//    atz3D_Element_Points.setPoints(ptsX);

    //atz3D_Index_Points       = 0;
//    list[atz3D_Index_Points] = atz3D_Element_Points;
    
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
      //String typeStr = this.getClass().getSimpleName();
      //typeStr = typeStr.replace("TableModel_Integrator_", "");

      Atz_XML_Helper.writeXMLStartTag(fid, "SELM_Integrator");

      Atz_XML_Helper.writeXMLData(fid, tagXML_IntegratorName, this.IntegratorName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_IntegratorTypeStr, this.IntegratorTypeStr);

      Atz_XML_Helper.writeXMLData(fid, tagXML_timeStep,    getTimeStep());

      Atz_XML_Helper.writeXMLData(fid, tagXML_shearRate,   getShearRate());

      Atz_XML_Helper.writeXMLData(fid, tagXML_shearDir,    getShearDir());

      Atz_XML_Helper.writeXMLData(fid, tagXML_shearVelDir, getShearVelDir());

      Atz_XML_Helper.writeXMLData(fid, tagXML_saveSkip,    getSaveSkip());

      Atz_XML_Helper.writeXMLEndTag(fid, "SELM_Integrator");

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
    } else if (qName.equals(tagXML_timeStep)) {
      setTimeStep(Double.parseDouble(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_shearDir)) {
      setShearDir(Integer.parseInt(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_shearVelDir)) {
      setShearVelDir(Integer.parseInt(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_saveSkip)) {
      setSaveSkip(Integer.parseInt(xmlAttributes.getValue("value")));
    }

  }

  @Override
  public Object XML_getData() {
    return this; /* WARNING: not list safe */ /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */

    
}



