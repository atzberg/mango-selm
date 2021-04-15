package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element_Lines;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
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
public class SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 extends SELM_Eulerian implements SELM_EulerianRenderView, Atz_XML_SAX_DataHandlerInterface, SELM_EulerianInterface_LAMMPS {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();

  private int      num_dim = 3;

  private double   meshDeltaX;
  private int[]    numMeshPtsPerDir = new int[3];
  private double[] meshCenterX0     = new double[3];

  public   String  tagXML_num_dim          = "num_dim";
  public   String  tagXML_meshDeltaX       = "meshDeltaX";
  public   String  tagXML_numMeshPtsPerDir = "numMeshPtsPerDir";
  public   String  tagXML_meshCenterX0     = "meshCenterX0";

  public   String  tagXML_plotColor        = "plotColor";
  public   String  tagXML_flagVisible      = "flagVisible";

  public   String  tagXML_flagWriteFluidVel_VTK      = "flagWriteFluidVel_VTK";
  public   String  tagXML_flagWriteFluidForce_VTK    = "flagWriteFluidForce_VTK";
  public   String  tagXML_flagWriteFluidPressure_VTK = "flagWriteFluidPressure_VTK";
  
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

  private boolean flagWriteFluidVel_VTK      = false;
  private boolean flagWriteFluidForce_VTK    = false;
  private boolean flagWriteFluidPressure_VTK = false;

  /* index of element types within the representation of this type */
  public final int     atz3D_Index_Lines = 0;

  //Atz3D_Element_Points atz3D_Element_Points = new Atz3D_Element_Points();
  Atz3D_Element_Lines  atz3D_Element_Lines  = new Atz3D_Element_Lines();
  double[]             boxPtsX              = new double[3*8];
  double[]             boxEdgeX1            = new double[3*12];
  double[]             boxEdgeX2            = new double[3*12];
  
  SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    EulerianName    = "Your Name Here";
    EulerianTypeStr = thisClassName.replace(superClassName + "_", "");
  
  }

  @Override
  public Object clone() {
    
    SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 Eulerian_copy = new SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3();

    Eulerian_copy.EulerianName     = this.EulerianName.toString();
    Eulerian_copy.EulerianTypeStr  = this.EulerianTypeStr.toString();

    Eulerian_copy.meshDeltaX       = meshDeltaX;
    Eulerian_copy.numMeshPtsPerDir = numMeshPtsPerDir.clone();
    Eulerian_copy.meshCenterX0     = meshCenterX0.clone();

    Eulerian_copy.plotColor        = new Color(plotColor.getRGB());
    Eulerian_copy.flagVisible      = flagVisible;

    Eulerian_copy.flagWriteFluidVel_VTK      = flagWriteFluidVel_VTK;
    Eulerian_copy.flagWriteFluidPressure_VTK = flagWriteFluidPressure_VTK;
    Eulerian_copy.flagWriteFluidForce_VTK    = flagWriteFluidForce_VTK;
    
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

  public void setFlagWriteFluidVel_VTK(boolean flag_in) {
    flagWriteFluidVel_VTK = flag_in;
  }

  public void setFlagWriteFluidForce_VTK(boolean flag_in) {
    flagWriteFluidForce_VTK = flag_in;
  }

  public void setFlagWriteFluidPressure_VTK(boolean flag_in) {
    flagWriteFluidPressure_VTK = flag_in;
  }

  public void setFlagWriteFluidVel_VTK_asInt(int flag_in) {
    if (flag_in == 0) {
      flagWriteFluidVel_VTK = false;
    } else {
      flagWriteFluidVel_VTK = true;
    }
    
  }

  public void setFlagWriteFluidForce_VTK_asInt(int flag_in) {
    if (flag_in == 0) {
      flagWriteFluidForce_VTK = false;
    } else {
      flagWriteFluidForce_VTK = true;
    }

  }

  public void setFlagWriteFluidPressure_VTK_asInt(int flag_in) {
    if (flag_in == 0) {
      flagWriteFluidPressure_VTK = false;
    } else {
      flagWriteFluidPressure_VTK = true;
    }

  }
 
  public boolean getFlagWriteFluidVel_VTK() {
    return flagWriteFluidVel_VTK;
  }

  public boolean getFlagWriteFluidForce_VTK() {
    return flagWriteFluidForce_VTK;
  }

  public boolean getFlagWriteFluidPressure_VTK() {
    return flagWriteFluidPressure_VTK;
  }
  
  public int getFlagWriteFluidVel_VTK_asInt() {
    int flag = 0;
    if (flagWriteFluidVel_VTK == true) {
     flag = 1;
    }
    return flag;
  }

  public int getFlagWriteFluidForce_VTK_asInt() {
    int flag = 0;
    if (flagWriteFluidForce_VTK == true) {
     flag = 1;
    }
    return flag;
  }

  public int getFlagWriteFluidPressure_VTK_asInt() {
    int flag = 0;
    if (flagWriteFluidPressure_VTK == true) {
     flag = 1;
    }
    return flag;
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

    double[] L;
    double[] X0;
    
    int      I;
    
    int      I1;
    int      I2;

    L  = new double[3];
    X0 = new double[3];
        
    for (int d = 0; d < num_dim; d++) {     
      L[d]  = numMeshPtsPerDir[d]*meshDeltaX;
      X0[d] = meshCenterX0[d];
    }

    /* construct the box vertices */
    for (int d1 = 0; d1 < 2; d1++) {
      for (int d2 = 0; d2 < 2; d2++) {
        for (int d3 = 0; d3 < 2; d3++) {

          int i1 = (d1 * 2 - 1);
          int i2 = (d2 * 2 - 1);
          int i3 = (d3 * 2 - 1);

          I = d1 + d2 * 2 + d3 * 4;

          boxPtsX[num_dim * I + 0] = X0[0] + i1 * (L[0]/2.0);
          boxPtsX[num_dim * I + 1] = X0[1] + i2 * (L[1]/2.0);
          boxPtsX[num_dim * I + 2] = X0[2] + i3 * (L[2]/2.0);

        }
      }
    }

    /* construct the box edges */
    I = 0;
    int[] ii1 = new int[3];
    int[] ii2 = new int[3];
    for (int d = 0; d < num_dim; d++) {
      for (int q1 = 0; q1 < 2; q1++) {
        for (int q2 = 0; q2 < 2; q2++) {
          
          ii1[(0 + d) % num_dim] = 0;
          ii1[(1 + d) % num_dim] = q1;
          ii1[(2 + d) % num_dim] = q2;

          ii2[(0 + d) % num_dim] = 1;
          ii2[(1 + d) % num_dim] = q1;
          ii2[(2 + d) % num_dim] = q2;
                          
          I1 = ii1[0] + ii1[1] * 2 + ii1[2] * 4;
          boxEdgeX1[num_dim * I + 0] = boxPtsX[num_dim * I1 + 0];
          boxEdgeX1[num_dim * I + 1] = boxPtsX[num_dim * I1 + 1];
          boxEdgeX1[num_dim * I + 2] = boxPtsX[num_dim * I1 + 2];

          I2 = ii2[0] + ii2[1] * 2 + ii2[2] * 4;
          boxEdgeX2[num_dim * I + 0] = boxPtsX[num_dim * I2 + 0];
          boxEdgeX2[num_dim * I + 1] = boxPtsX[num_dim * I2 + 1];
          boxEdgeX2[num_dim * I + 2] = boxPtsX[num_dim * I2 + 2];
          I++;

        }
      }
    }
           
    atz3D_Element_Lines.setPlotColor(plotColor);    
    atz3D_Element_Lines.setLines(boxEdgeX1, boxEdgeX2);

    //atz3D_Element_Points.setPlotColor(plotColor);
    //atz3D_Element_Points.setPoints(ptsX);

    //atz3D_Index_Points       = 0;
    list[atz3D_Index_Lines] = atz3D_Element_Lines;

    return list;
  }

  public double[] getDomainBox() {

    int      num_dim = 3;
    double[] periodL = new double[2*num_dim];
    double[] tilts   = new double[num_dim];

    double[] domainBox = new double[2*num_dim + num_dim];

    for (int i = 0; i < num_dim; i++) {
      periodL[2*i + 0] = meshCenterX0[i] - 0.5*numMeshPtsPerDir[i]*meshDeltaX;
      periodL[2*i + 1] = meshCenterX0[i] + 0.5*numMeshPtsPerDir[i]*meshDeltaX;
    }

    tilts[0] = 0.0;
    tilts[1] = 0.0;
    tilts[2] = 0.0;

    /* -- setup the domain box data for LAMMPS for triclinic box */
    for (int i = 0; i < 2*num_dim; i++) {  /* xlo xhi, ylo yhi, zlo zhi */
      domainBox[i] = periodL[i];
    }

    /* tilt factors: xy xz yz */
    domainBox[2*num_dim + 0] = tilts[0];
    domainBox[2*num_dim + 1] = tilts[1];
    domainBox[2*num_dim + 2] = tilts[2];

    return domainBox;
  }

  public String[] getDomainBoundaryTypes() {
    int      num_dim = 3;
    String[] boundaryTypeStrs = new String[num_dim];

    /* p = periodic */
    boundaryTypeStrs[0] = "p";
    boundaryTypeStrs[1] = "p";
    boundaryTypeStrs[2] = "p";

    return boundaryTypeStrs;
  }

  /* ====================================================== */
  /* ==================== XML codes ======================= */
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

  public void exportToXML(BufferedWriter fid) {

    try {
      //String typeStr = this.getClass().getSimpleName();
      //typeStr = typeStr.replace("SELM_Eulerian_", "");

      Atz_XML_Helper.writeXMLStartTag(fid, SELM_Eulerian.class.getSimpleName());

      Atz_XML_Helper.writeXMLData(fid, tagXML_EulerianName, this.EulerianName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_EulerianTypeStr, this.EulerianTypeStr);

      Atz_XML_Helper.writeXMLData(fid, tagXML_num_dim, num_dim);

      Atz_XML_Helper.writeXMLData(fid, tagXML_meshDeltaX, getMeshDeltaX());

      Atz_XML_Helper.writeXMLData(fid, tagXML_numMeshPtsPerDir, getNumMeshPtsPerDir());

      Atz_XML_Helper.writeXMLData(fid, tagXML_meshCenterX0, getMeshCenterX0());

      Atz_XML_Helper_Handler_Color handlerColor = new Atz_XML_Helper_Handler_Color(plotColor);
      Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor, handlerColor);

      /*
      int[] colorArray = new int[3];
      colorArray[0]    = plotColor.getRed();
      colorArray[1]    = plotColor.getGreen();
      colorArray[2]    = plotColor.getBlue();
      Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor, colorArray);
       */

      Atz_XML_Helper.writeXMLData(fid, tagXML_flagVisible, this.isVisible());

      Atz_XML_Helper.writeXMLData(fid, tagXML_flagWriteFluidVel_VTK,      this.getFlagWriteFluidVel_VTK_asInt());
      Atz_XML_Helper.writeXMLData(fid, tagXML_flagWriteFluidForce_VTK,    this.getFlagWriteFluidForce_VTK_asInt());
      Atz_XML_Helper.writeXMLData(fid, tagXML_flagWriteFluidPressure_VTK, this.getFlagWriteFluidPressure_VTK_asInt());

      Atz_XML_Helper.writeXMLEndTag(fid, SELM_Eulerian.class.getSimpleName());

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

    if (qName.equals(tagXML_plotColor)) {
      Atz_XML_Helper_Handler_Color handlerColor = new Atz_XML_Helper_Handler_Color();
      sourceHandler.parseNextTagWithDataHandler(handlerColor);
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
    } else if (qName.equals(tagXML_meshDeltaX)) {
      setMeshDeltaX(Double.parseDouble(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_numMeshPtsPerDir)) {
      setNumMeshPtsPerDir(Atz_XML_Helper.parseIntArrayFromString(xmlString));
    } else if (qName.equals(tagXML_meshCenterX0)) {
      setMeshCenterX0(Atz_XML_Helper.parseDoubleArrayFromString(xmlString));
    } else if (qName.equals(tagXML_plotColor)) {
      Atz_XML_Helper_Handler_Color handlerColor
        = (Atz_XML_Helper_Handler_Color)sourceHandler.getLastUsedDataHandler();
      plotColor = handlerColor.color;
    } else if (qName.equals(tagXML_flagVisible)) {
      setVisible(Boolean.parseBoolean(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_flagWriteFluidVel_VTK)) {
      setFlagWriteFluidVel_VTK_asInt(Integer.parseInt(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_flagWriteFluidForce_VTK)) {
      setFlagWriteFluidForce_VTK_asInt(Integer.parseInt(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_flagWriteFluidPressure_VTK)) {
      setFlagWriteFluidPressure_VTK_asInt(Integer.parseInt(xmlAttributes.getValue("value")));
    }
    
  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */

  
}



