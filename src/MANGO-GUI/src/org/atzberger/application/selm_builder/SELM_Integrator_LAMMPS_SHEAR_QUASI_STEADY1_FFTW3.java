package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
import org.atzberger.mango.atz3d.Atz3D_Element_Points;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

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
public class SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3 extends SELM_Integrator
        implements SELM_IntegratorRenderView, Atz_XML_SAX_DataHandlerInterface,
                   SELM_IntegratorInterface_LAMMPS {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();

  /*
  Unity units: for pre-set default values;
  %   mass:        amu
  %   time:        ns
  %   length:      nm
  %   temperature: K
   */

  private double   deltaT              = 10;
  private int      maxTimeStepIndex    = 10000;
  private double   mu                  = 6.022141989999999e+05;
  private double   rho                 = 6.022141989999999e+02;
  private double   KB                  = 8.314472145136097e+03;
  private double   T                   = 2.981500000000000e+02;

  private String   flagShearModeStr    = "RM_SHEAR1";
  private double   shearRate           = 0;
  private int      shearDir            = 2;
  private int      shearVelDir         = 0;
  private int      saveSkip            = 1;

  private int      flagStochasticDriving   = 1;
  private int      flagIncompressibleFluid = 1;

  
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
  public   String tagXML_deltaT           = "deltaT";
  public   String tagXML_maxTimeStepIndex = "maxTimeStepIndex";

  public   String tagXML_mu               = "mu";
  public   String tagXML_rho              = "rho";
  public   String tagXML_KB               = "KB";
  public   String tagXML_T                = "T";

  //public   String tagXML_flagShearModeStr = "flagShearModeStr";
  public   String tagXML_shearData = "shearData";

  /* RM_SHEAR1 */
  public   String tagXML_shearRate    = "shearRate";
  public   String tagXML_shearDir     = "shearDir";
  public   String tagXML_shearVelDir  = "shearVelDir";

  /* other */
  public   String tagXML_saveSkip                = "saveSkip";
  public   String tagXML_flagStochasticDriving   = "flagStochasticDriving";
  public   String tagXML_flagIncompressibleFluid = "flagIncompressibleFluid";
  
  SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    IntegratorName    = "Your Name Here";
    IntegratorTypeStr = thisClassName.replace(superClassName + "_", "");
    
  }

  @Override
  public Object clone() {
    SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3 Integrator_copy = new SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3();

    Integrator_copy.IntegratorName    = this.IntegratorName.toString();
    Integrator_copy.IntegratorTypeStr = this.IntegratorTypeStr.toString();
   
    Integrator_copy.deltaT           = this.deltaT;
    Integrator_copy.maxTimeStepIndex = this.maxTimeStepIndex;
    Integrator_copy.mu               = this.mu;
    Integrator_copy.rho              = this.rho;
    Integrator_copy.KB               = this.KB;
    Integrator_copy.T                = this.T;
    
    Integrator_copy.flagStochasticDriving   = this.flagStochasticDriving;
    Integrator_copy.flagIncompressibleFluid = this.flagIncompressibleFluid;
    
    Integrator_copy.flagShearModeStr        = this.flagShearModeStr;
    Integrator_copy.shearRate               = this.shearRate;
    Integrator_copy.shearDir                = this.shearDir;
    Integrator_copy.shearVelDir             = this.shearVelDir;
    Integrator_copy.saveSkip                = this.saveSkip;

    Integrator_copy.plotColor   = new Color(plotColor.getRGB());
    Integrator_copy.flagVisible = this.flagVisible;

    return (Object) Integrator_copy;
  }

  public void setTimeStep(double value) {
    deltaT = value;
  }

  public void setNumberTimeSteps(int value) {
    maxTimeStepIndex = value;
  }

  public void setFluidViscosityMu(double value) {
    mu = value;
  }

  public void setFluidDensityRho(double value) {
    rho = value;
  }

  public void setBoltzmannKB(double value) {
    KB = value;
  }

  public void setTemperatureT(double value) {
    T = value;
  }

  public void setFlagStochasticDriving(Boolean value) {
    if (value == true) {
      flagStochasticDriving = 1;
    } else {
      flagStochasticDriving = 0;
    }
  }

  public void setFlagIncompressibleFluid(Boolean value) {
    if (value == true) {
      flagIncompressibleFluid = 1;
    } else {
      flagIncompressibleFluid = 0;
    }
  }

  public void setFlagStochasticDriving(int value) {
    flagStochasticDriving = value;
  }

  public void setFlagIncompressibleFluid(int value) {
    flagIncompressibleFluid = value;
  }

  public void setFlagShearMode(String value) {
    flagShearModeStr = value;
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

  public double getFluidViscosityMu() {
    return mu;
  }

  public double getFluidDensityRho() {
    return rho;
  }

  public double getBoltzmannKB() {
    return KB;
  }

  public double getTemperatureT() {
    return T;
  }

  public String getFlagShearModeStr() {
    return flagShearModeStr;
  }

  public int getFlagStochasticDrivingAsInt() {
    return flagStochasticDriving;
  }

  public int getFlagIncompressibleFluidAsInt() {
    return flagIncompressibleFluid;
  }

  public Boolean getFlagStochasticDrivingAsBoolean() {
    return (flagStochasticDriving != 0);
  }

  public Boolean getFlagIncompressibleFluidAsBoolean() {
    return (flagIncompressibleFluid != 0);
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

  
  /*------------------------------------------------
   * LAMMPS Interface
   * ------------------------------------------------
   */  
  public int getDumpFreq() {
    return saveSkip;
  }

  public int getNumberTimeSteps() {
    return maxTimeStepIndex;
  }

  public double getTimeStep() {
    return deltaT;
  }

  /*------------------------------------------------ */

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
      //typeStr = typeStr.replace("TableModel_Integrator_", "");

      Atz_XML_Helper.writeXMLStartTag(fid, "SELM_Integrator");

      Atz_XML_Helper.writeXMLData(fid, tagXML_IntegratorName, this.IntegratorName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_IntegratorTypeStr, this.IntegratorTypeStr);

      Atz_XML_Helper.writeXMLData(fid, tagXML_deltaT,    getTimeStep());

      Atz_XML_Helper.writeXMLData(fid, tagXML_maxTimeStepIndex, getNumberTimeSteps());

      Atz_XML_Helper.writeXMLData(fid, tagXML_mu, getFluidViscosityMu());
      Atz_XML_Helper.writeXMLData(fid, tagXML_rho, getFluidDensityRho());
      Atz_XML_Helper.writeXMLData(fid, tagXML_KB, getBoltzmannKB());
      Atz_XML_Helper.writeXMLData(fid, tagXML_T, getTemperatureT());

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_shearData, "value=" + "\"" + flagShearModeStr + "\"");
      
      /* RM_SHEAR1  */
      Atz_XML_Helper.writeXMLData(fid, tagXML_shearRate,   getShearRate());

      Atz_XML_Helper.writeXMLData(fid, tagXML_shearDir,    getShearDir());

      Atz_XML_Helper.writeXMLData(fid, tagXML_shearVelDir, getShearVelDir());

      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_shearData);
     
      /* other */
      Atz_XML_Helper.writeXMLData(fid, tagXML_flagStochasticDriving,    getFlagStochasticDrivingAsInt());
      Atz_XML_Helper.writeXMLData(fid, tagXML_flagIncompressibleFluid,  getFlagIncompressibleFluidAsInt());
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_saveSkip, getSaveSkip());

      /* end tag */
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

    if (qName.equals(tagXML_shearData)) {
      setFlagShearMode(xmlAttributes.getValue("value"));
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
    } else if (qName.equals(tagXML_deltaT)) {
      setTimeStep(Double.parseDouble(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_maxTimeStepIndex)) {
      setNumberTimeSteps(Integer.parseInt(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_mu)) {
      setFluidViscosityMu(Double.parseDouble(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_rho)) {
      setFluidDensityRho(Double.parseDouble(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_KB)) {
      setBoltzmannKB(Double.parseDouble(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_T)) {
      setTemperatureT(Double.parseDouble(xmlAttributes.getValue("value")));    
    } else if (qName.equals(tagXML_flagStochasticDriving)) {
      setFlagStochasticDriving(Integer.parseInt(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_flagIncompressibleFluid)) {
      setFlagIncompressibleFluid(Integer.parseInt(xmlAttributes.getValue("value")));
    } else if (qName.equals(tagXML_shearRate)) {
      setShearRate(Double.parseDouble(xmlAttributes.getValue("value")));
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
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */

    
}



