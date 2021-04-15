package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
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
public class SELM_Lagrangian_CONTROL_PTS_BASIC1 extends SELM_Lagrangian
        implements SELM_LagrangianRenderView, Atz_XML_SAX_DataHandlerInterface {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();

  private int      num_dim   = 3;
  private double[] ptsX      = new double[0];
  private int[]    ptsID     = new int[0];

  /* data change events */
  public final String DATA_CHANGE_REMOVE_PT  = "REMOVE_PT";
  public final String DATA_CHANGE_ADD_PT     = "ADD_PT";

  /* plotting related data */
  private Random randGen = new Random();
  private int color_r = 25 + (int)(200*randGen.nextDouble());
  private int color_g = 25 + (int)(200*randGen.nextDouble());
  private int color_b = 25 + (int)(200*randGen.nextDouble());

  private Color    plotColor = new Color(color_r, color_g, color_b); /* pick color at random */
  
  /* index of element types within the representation of this type */
  public final int     atz3D_Index_Points = 0;

  Atz3D_Element_Points atz3D_Element_Points = new Atz3D_Element_Points();

  /* XML */
  static public String tagXML_plotColor   = "plotColor";
  static public String tagXML_ptsID       = "ptsID";
  static public String tagXML_ptsX        = "ptsX";
  static public String tagXML_num_dim     = "num_dim";
  static public String tagXML_flagVisible = "flagVisible";
  
  public SELM_Lagrangian_CONTROL_PTS_BASIC1() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    LagrangianName    = "Your Name Here";
    LagrangianTypeStr = thisClassName.replace(superClassName + "_", "");

    debugSetCubeModel();

    /*
    int N = 1;

    ptsX  = new double[N*num_dim];
    ptsID = new int[N];

    ptsX[0]  = 1.0;
    ptsX[1]  = 1.0;
    ptsX[2]  = 1.0;
    ptsID[0] = 1;
     */

  }

  @Override
  public Object clone() {
    SELM_Lagrangian_CONTROL_PTS_BASIC1 Lagrangian_copy = new SELM_Lagrangian_CONTROL_PTS_BASIC1();

    Lagrangian_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    Lagrangian_copy.LagrangianName    = this.LagrangianName.toString();
    Lagrangian_copy.LagrangianTypeStr = this.LagrangianTypeStr.toString();
    
    Lagrangian_copy.num_dim           = this.num_dim;
    Lagrangian_copy.ptsX              = this.ptsX.clone();
    Lagrangian_copy.ptsID             = this.ptsID.clone();
    Lagrangian_copy.plotColor         = this.plotColor; /* WARNING: need to clone() */
    Lagrangian_copy.flagVisible       = this.flagVisible;
    
    return (Object) Lagrangian_copy;
  }

  public void setPtsX(double[] ptsX_in) {
    ptsX = ptsX_in.clone();
  }

  public void setPtsID(int[] ptsID_in) {
    ptsID = ptsID_in.clone();
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

  public double[] getPtsX() {
    return ptsX;
  }

  public double[] getPtsX(int I) {
    double[] X = new double[num_dim];
    for (int d = 0; d < num_dim; d++) {
      X[d] = ptsX[I*num_dim + d];
    }
    return X;
  }

  public int getNumPts() {
    return ptsX.length/num_dim;
  }

  public int[] getPtsID() {
    return ptsID;
  }

  public String getRenderTag() {
    return atz3D_RENDER_TAG_LAGRANGIAN;
  }

  public void renderToModel3D(Atz3D_Model model3D) {

    /* add points to represent the current lagrangian structure */
    //Color plotColor = Color.green;
    if (isVisible() == true) {  /* only add if this DOF is visible */
      model3D.addElements(getAtz3DElementRepresentation());
    }
      
  }

  public Atz3D_Element[] getAtz3DElementRepresentation() {
    Atz3D_Element[] list = new Atz3D_Element[1];

    atz3D_Element_Points.setPlotColor(plotColor);
    atz3D_Element_Points.setPoints(ptsX);

    //atz3D_Index_Points       = 0;
    list[atz3D_Index_Points] = atz3D_Element_Points;

    return list;
  }

  void debugSetCubeModel() {

    int i, j, k, d;
    int I;
    int I1, I2, I_pair;

    double tmp_ptsX[];

    int N;
    Random generator = new Random();
    double q = (generator.nextDouble() - 0.5);
    double ell = 0.25*(1.0 + q);

    /* --- setup test control points manually --- */
    N = 8;
    double[] controlPts_X_ref = new double[N * num_dim];
    int[]    controlPts_ID    = new int[N];

    for (k = 0; k < 8; k++) {

      I = num_dim * k;

      /* -- determine points */
      /* first bit */
      if ((k & 1) != 0) {
        controlPts_X_ref[I + 0] = -ell;
      } else {
        controlPts_X_ref[I + 0] = ell;
      }

      /* second bit */
      if ((k & 2) != 0) {
        controlPts_X_ref[I + 1] = -ell;
      } else {
        controlPts_X_ref[I + 1] = ell;
      }

      /* third bit */
      if ((k & 4) != 0) {
        controlPts_X_ref[I + 2] = -ell;
      } else {
        controlPts_X_ref[I + 2] = ell;
      }

    } /* end of k loop */

    setPtsX(controlPts_X_ref);
    setPtsID(controlPts_ID);

  }

  public void addPoint(double[] newPtsX) {
    this.addPoints(newPtsX);
  }

  public void addPoints(double[] newPtsX) {

    int numNewPts      = newPtsX.length/num_dim;
    int      k, d;
    int      N         = ptsX.length/num_dim;
    double[] ptsX_new  = new double[(N + numNewPts)*num_dim];
    int[]    ptsID_new = new int[N + numNewPts];

    /* copy old points */
    for (k = 0; k < N; k++) {
      for (d = 0; d < num_dim; d++) {
        ptsX_new[k*num_dim + d]  = ptsX[k*num_dim + d];
      }
      ptsID_new[k] = ptsID[k];
    }

    /* copy new points */
    for (k = 0; k < numNewPts; k++) {
      for (d = 0; d < num_dim; d++) {
        ptsX_new[(N + k)*num_dim + d] = newPtsX[k*num_dim + d];
      }
      ptsID_new[k] = 0;
    }

    setPtsX(ptsX_new);    
    setPtsID(ptsID_new);

    fireDataChangeEvent(this, this.DATA_CHANGE_ADD_PT);

  }

  void removePoints(int[] ptsToRemoveIndices) {

    /* remove the control points one by one and re-index the lists */
    int k;

    /* sort the order of the indices */
    java.util.Arrays.sort(ptsToRemoveIndices);

     /* important to remove in descending order to avoid invalidation of the
     * index used for reference.
     */
    for (k = ptsToRemoveIndices.length - 1; k >= 0; k--) {
      removePoint(ptsToRemoveIndices[k]);
    }

  }

  /* remove a single control point */
  void removePoint(int ptsToRemoveIndex) {

    int k, d;
    int N              = getNumPts();
    double[] ptsX_new  = new double[(N - 1)*num_dim];
    int[]    ptsID_new = new int[(N - 1)];

    /* copy all points having smaller index */
    for (k = 0; k < ptsToRemoveIndex; k++) {
      for (d = 0; d < num_dim; d++) {
        ptsX_new[k*num_dim + d] = ptsX[k*num_dim + d];
      }
      ptsID_new[k] = ptsID[k];
    }

    /* copy all points having larger index */
    for (k = (ptsToRemoveIndex + 1); k < N; k++) {
      for (d = 0; d < num_dim; d++) {
        ptsX_new[(k - 1)*num_dim + d] = ptsX[k*num_dim + d];
      }
      ptsID_new[(k - 1)] = ptsID[k];
    }

    setPtsX(ptsX_new);
    setPtsID(ptsID_new);

    HashMap extraData = new HashMap();
    extraData.put("ptsToRemoveIndex", ptsToRemoveIndex);
    extraData.put("lagrangian", this);
    
    fireDataChangeEvent(this, this.DATA_CHANGE_REMOVE_PT, extraData);

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

  @Override
  public void exportToXML(BufferedWriter fid) {
    
    try {              
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_SELM_Lagrangian);

      Atz_XML_Helper.writeXMLData(fid, tagXML_LagrangianName,    LagrangianName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_LagrangianTypeStr, LagrangianTypeStr);

      Atz_XML_Helper.writeXMLData(fid, tagXML_num_dim, num_dim);
      Atz_XML_Helper.writeXMLData(fid, tagXML_ptsX, ptsX);
      Atz_XML_Helper.writeXMLData(fid, tagXML_ptsID, ptsID);

      Atz_XML_Helper_Handler_Color handlerColor = new Atz_XML_Helper_Handler_Color(plotColor);
      Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor, handlerColor);

      Atz_XML_Helper.writeXMLData(fid, tagXML_flagVisible, flagVisible);

      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_SELM_Lagrangian);

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }

   
  @Override
  public void importData(String filename, int flagType) {

    /* open the XML file */

    /* parse the XML file to setup the data */

    //get a factory
    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {

      //get a new instance of parser
      SAXParser sp = spf.newSAXParser();

      //parse the file and also register this class for call backs
      //sp.parse("test1.SELM_Lagrangian_CONTROL_PTS_BASIC1", new Atz_XML_DataHandlerWrapper(this));
      
      sp.parse(filename, new Atz_XML_SAX_DataHandler(this));

      /* Use the local codes XMLContentHandler */
      
    } catch (SAXException se) {
      se.printStackTrace();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (IOException ie) {
      ie.printStackTrace();
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
    } else if (qName.equals(tagXML_LagrangianName)) {
      LagrangianName = xmlAttributes.getValue("value");      
    } else if (qName.equals(tagXML_LagrangianTypeStr)) {
      String typeStr = xmlAttributes.getValue("value");  /* maybe check compatible here */
      if (typeStr.equals(LagrangianTypeStr) == false) {
        System.out.println("ERROR: types are not compatible");
      }      
    } else if (qName.equals(tagXML_num_dim)) {
      num_dim = Integer.parseInt(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_ptsX)) {
      ptsX  = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    } else if (qName.equals(tagXML_ptsID)) {
      ptsID = Atz_XML_Helper.parseIntArrayFromString(xmlString);
    } else if (qName.equals(tagXML_plotColor)) {      
      plotColor = ((Atz_XML_Helper_Handler_Color)sourceHandler.getLastUsedDataHandler()).color;
    } else if (qName.equals(tagXML_flagVisible)) {
      flagVisible = Boolean.parseBoolean(xmlAttributes.getValue("value"));      
    }
    
  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */
    
}



