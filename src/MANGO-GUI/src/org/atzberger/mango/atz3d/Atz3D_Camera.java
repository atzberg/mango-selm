package org.atzberger.mango.atz3d;

import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Writeable;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Represents camera orientation and view for 3D rendering package.
 * <p>
 *
 * The nomenclature associated with the camera model and projection operations
 * follows the book
 * <p>
 *   "Computer Graphics: Principles and Practice, Second Edition in C by
 *    Foley, van Dam, Feiner, and Hughes, Addison-Wesley, 1987.
 * <p>
 * For the particular definitions see the materials around pg. 237.
 * <p>
 * We summarise them here:
 * <p>
 * VRP = View reference point. <br>
 * VPN = View plane normal  <br>
 * VRC = View reference coodinates  <br>
 * VUP = View up vector   (note projected orthogonal to VPN)  <br>
 * DOP = Direction of projection  <br>
 * CW  = Center of window  <br>
 * PRP = Projection reference point  <br>
 * <p>
 * winBounds = bounds (ell1, ell2) of the window screen in 2D real-space.
 *             (normalized window coordinates go from 0.0 to 1.0)
 * <p>
 * The internal canonical view used for calculations (after transformation)
 * is oriented with the camera looking down the z-axis, so the view-plane
 * is in the x-y-directions (right-handed coordinate system).
 * <p>
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz3D_Camera implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  /* We document here some of the nomenclature associated with the camera model
   * and projection operations.  These follow from the book
   *   "Computer Graphics: Principles and Practice, Second Edition in C by
   *    Foley, van Dam, Feiner, and Hughes, Addison-Wesley, 1987.
   *
   * (see pg. 237 for discussion of definitions)
   * VRP = View reference point.
   * VPN = View plane normal
   * VRC = View reference coodinates
   * VUP = View up vector   (note projected orthogonal to VPN)
   * DOP = Direction of projection
   * CW  = Center of window
   * PRP = Projection reference point
   *
   * winBounds = bounds (ell1, ell2) of the window screen in 2D real-space.
   *             (normalized window coordinates go from 0.0 to 1.0)
   *
   * The internal canonical view used for calculations (after transformation)
   * is oriented with the camera looking down the z-axis, so the view-plane
   * is in the x-y-directions (right-handed coordinate system).
   *
   *
   */
  public final static int num_dim = 3;
  
  public double cameraVUP[] = new double[num_dim];
  public double cameraVRP[] = new double[num_dim];
  public double cameraVPN[] = new double[num_dim];

  public double cameraCW[]        = new double[num_dim - 1];
  public double cameraWinBounds[] = new double[2*(num_dim - 1)];

  public final static int FILE_TYPE_XML = 1;

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  static public String tagXML_Atz3D_Camera     = "Atz3D_Camera";

  static public String tagXML_cameraVUP        = "cameraVUP";
  static public String tagXML_cameraVRP        = "cameraVRP";  
  static public String tagXML_cameraVPN        = "cameraVPN";

  static public String tagXML_cameraCW         = "cameraCW";
  static public String tagXML_cameraWinBounds  = "cameraWinBounds";

  /**
   * Create the camera with a default view.
   */
  public Atz3D_Camera() {
    setDefaultValues();
  }

  /**
   * Set the default values for the camera view.
   */
  public void setDefaultValues() {

    cameraVRP[0]       = 0.0;
    cameraVRP[1]       = 0.0;
    cameraVRP[2]       = 0.0;

    cameraVUP[0]       = 0.0;
    cameraVUP[1]       = 1.0;
    cameraVUP[2]       = 0.0;

    cameraVPN[0]       = 0.0;
    cameraVPN[1]       = 0.0;
    cameraVPN[2]       = 1.0;

    cameraCW[0]        = 0;
    cameraCW[1]        = 0;

    cameraWinBounds[0] = 1.0;
    cameraWinBounds[1] = 1.0;

  }


  /**
   * Make a copy of the camera
   *
   * @return copy of this camera class
   */
  @Override
  public Atz3D_Camera clone() {

    Atz3D_Camera camera_clone    = new Atz3D_Camera();

    camera_clone.cameraVRP       = cameraVRP.clone();
    camera_clone.cameraVUP       = cameraVUP.clone();
    camera_clone.cameraVPN       = cameraVPN.clone();    
    camera_clone.cameraCW        = cameraCW.clone();

    camera_clone.cameraWinBounds = cameraWinBounds.clone();

    return camera_clone;

  }

  /**
   * Set the camera view explicitly
   * 
   * @param new_cameraVRP
   * @param new_cameraVUP
   * @param new_cameraVPN
   * @param new_cameraCW
   * @param new_cameraWinBounds
   */
  public void setCameraView(double[] new_cameraVRP, 
                            double[] new_cameraVUP,
                            double[] new_cameraVPN,
                            double[] new_cameraCW,
                            double[] new_cameraWinBounds) {
    
    cameraVRP       = new_cameraVRP;
    cameraVUP       = new_cameraVUP;
    cameraVPN       = new_cameraVPN;
    cameraCW        = new_cameraCW;
    cameraWinBounds = new_cameraWinBounds;

  }

  /**
   * Transforms the 3D points to corresponding points in the view plane.
   */
  public void transformPts_3D_To_2D(double ptsX_3D[], double ptsX_2D[]) {

    int i, j, k, d;

    int    N;
    double X[] = new double[num_dim];
    double X_p[] = new double[num_dim];
    double X_pp[] = new double[num_dim];

    double R[] = new double[num_dim * num_dim];

    /* -- generate rotation matrix for transforming points */
    R = genCameraRotationMatrix();

    /* == for each point perform the transforms */
    N = ptsX_3D.length / num_dim;
    for (k = 0; k < N; k++) {

      /* -- get the current point */
      for (d = 0; d < num_dim; d++) {
        X[d] = ptsX_3D[num_dim * k + d];
      }

      /* -- get location relative to VRP */
      for (d = 0; d < num_dim; d++) {
        X_p[d] = X[d] - cameraVRP[d];
      }

      /* -- rotate so the VRC aligns with
       * canonical description (apply same to points)
       * VPN along z-axis, VUP along y-axis,
       * remaining along x-axis (right hand system)
       */
      Atz_LinearAlgebra.matrixVecMult(num_dim, num_dim, R, X_p, X_pp);

      /* -- perform the orthogonal projection */
      for (d = 0; d < num_dim - 1; d++) {
        ptsX_2D[(num_dim - 1)*k + d] = X_pp[d];
      }

    } /* end of k loop */

  }


  /**
   * Transform the 3D model to a 2D view suitable for rendering on screen.
   *
   * Uses the camera settings to determine a normalized value for the
   * project 2D point in terms of screen coordinates.
   *
   * These coordinates are normalized to the range 0.0 to 1.0 for
   * points within the view windows screen.  Values outside can be
   * used to clip display of points.
   */
  public void transformPts_2D_To_Screen(double ptsX_2D[], double ptsX_Screen_transformed[]) {
    Dimension screenSize = new Dimension(1,1); /* set w,h to one to get 0.0 to 1.0 scaling */
    transformPts_2D_To_Screen(ptsX_2D, screenSize, ptsX_Screen_transformed);
  }

  /**
   * Use the camera settings to find scaling to screen size
   */
  public void transformPts_2D_To_Screen(double ptsX_2D[], Dimension screenSize, double ptsX_Screen_transformed[]) {
  
    int i, j, k, d;

    int w = screenSize.width;
    int h = screenSize.height;
    int m = java.lang.Math.min(w,h);

    int alpha[] = new int[2];

    int N;
    double X[]    = new double[num_dim - 1];
    double X_p[]  = new double[num_dim - 1];

    double ell;

    alpha[0] = w; /* w */
    alpha[1] = h; /* h */
    
    N = ptsX_2D.length / (num_dim - 1);

    /* transform each point */
    for (k = 0; k < N; k++) {

      /* -- transform the current point */
      for (d = 0; d < num_dim - 1; d++) {
        X[d]                                           = ptsX_2D[(num_dim - 1) * k + d];
        ell                                            = cameraWinBounds[d];
        ptsX_Screen_transformed[(num_dim - 1) * k + d] = alpha[d]*(X[d] - (cameraCW[d] - 0.5*ell))/ell;
      }

    }

  } /* end of transformPts_2D_To_Screen_transformed */


  /**
   * Generate the rotation matrix which converts from world coordinate
   * frame to the camera canonical coordinate frame.
   * The cameraVPN, cameraVUP, etc... are represented
   * in world coordinates and are used for this.
   */
  public double [] genCameraRotationMatrix() {

    int i, j, k, d;

    int N;
    double X[]   = new double[num_dim];
    double X_p[] = new double[num_dim];

    double R[]   = new double[num_dim * num_dim];
    double R2[]  = new double[num_dim * num_dim];
    double cross_VUP_VPN_norm[] = new double[num_dim];

    double normVPN;
    double norm_cross_VUP_VPN_norm;
    double norm;

    int i1, i2;
    int dd = 0;
    int ff = 0;
    int rowRx, rowRy, rowRz;

    double cross_R_z_R_x_norm[] = new double[num_dim];

    /* we construct transpose of camera to world rotation */

    /* -- R_z row is the VPN (normalized) */
    rowRz = 2;

    normVPN = Atz_LinearAlgebra.vecNorm(cameraVPN);

    for (d = 0; d < num_dim; d++) {
      R[rowRz * num_dim + d] = cameraVPN[d] / normVPN; /* changed sign */
    }

    /* -- R_x row is the VUP cross VPN/||VPN|| (normalized)
     * or R_x = (VUP x R_z)/ norm
     *
     * (this projects VUP orthogonal to VPN)
     */
    rowRx = 0; 

    cross_VUP_VPN_norm[0] = cameraVUP[1] * cameraVPN[2] - cameraVUP[2] * cameraVPN[1];
    cross_VUP_VPN_norm[1] = cameraVUP[2] * cameraVPN[0] - cameraVUP[0] * cameraVPN[2];
    cross_VUP_VPN_norm[2] = cameraVUP[0] * cameraVPN[1] - cameraVUP[1] * cameraVPN[0];

    norm_cross_VUP_VPN_norm = Atz_LinearAlgebra.vecNorm(cross_VUP_VPN_norm);
    norm                    = normVPN * norm_cross_VUP_VPN_norm;

    for (d = 0; d < num_dim; d++) {
      R[rowRx * num_dim + d] = cross_VUP_VPN_norm[d] / norm;  /* changed sign */
    }

    /* -- R_y row is the cross product of the R_z and R_x rows */
    rowRy = 1;

    i1 = rowRz;
    i2 = rowRx;
    cross_R_z_R_x_norm[0] = R[i1 * num_dim + 1] * R[i2 * num_dim + 2] - R[i1 * num_dim + 2] * R[i2 * num_dim + 1];
    cross_R_z_R_x_norm[1] = R[i1 * num_dim + 2] * R[i2 * num_dim + 0] - R[i1 * num_dim + 0] * R[i2 * num_dim + 2];
    cross_R_z_R_x_norm[2] = R[i1 * num_dim + 0] * R[i2 * num_dim + 1] - R[i1 * num_dim + 1] * R[i2 * num_dim + 0];
    
    norm = Atz_LinearAlgebra.vecNorm(cross_R_z_R_x_norm);

    for (d = 0; d < num_dim; d++) {
      R[rowRy * num_dim + d] = cross_R_z_R_x_norm[d] / norm; /* changed sign */
    }

    /* ensure right-handed coordinate system */
    for (int d1 = 0; d1 < num_dim; d1++) {
      for (int d2 = 0; d2 < num_dim; d2++) {
        R[d1 * num_dim + d2] = -R[d1 * num_dim + d2];
      }
    }

    /* copy the rotation matrix to fix the orientation */
    /*
    dd = 0;
    ff = 2;
    for (int d2 = 0; d2 < num_dim; d2++) {
      R2[d2*num_dim + dd] = R[d2*num_dim + ff];
    }

    dd = 1;
    ff = 0;
    for (int d2 = 0; d2 < num_dim; d2++) {
      R2[d2*num_dim + dd] = R[d2*num_dim + ff];
    }

    dd = 2;
    ff = 1;
    for (int d2 = 0; d2 < num_dim; d2++) {
      R2[d2*num_dim + dd] = -R[d2*num_dim + ff];
    }

    return R2;

     */

    return R;

  } /* end genCameraRotationMatrix */

  
  /* ---------------------------------------------------- */

  /* ====================================================== */
  /* ==================== XML codes ======================= */
  /**
   * Export the state of the camera to a data file.
   *
   * @param filename
   * @param flagType
   */
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
          //System.err.println("Error: " + e.getMessage());
        }

        break; /* end XML */

    } /* end switch */

  }

  /**
   * * Export the state of the camera to an XML data file.
   *
   * @param fid
   */
  public void exportToXML(BufferedWriter fid) {

    try {
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_Atz3D_Camera);
            
      Atz_XML_Helper.writeXMLData(fid, tagXML_cameraVRP,       cameraVRP);
      Atz_XML_Helper.writeXMLData(fid, tagXML_cameraVUP,       cameraVUP);
      Atz_XML_Helper.writeXMLData(fid, tagXML_cameraVPN,       cameraVPN);
      Atz_XML_Helper.writeXMLData(fid, tagXML_cameraCW,        cameraCW);
      Atz_XML_Helper.writeXMLData(fid, tagXML_cameraWinBounds, cameraWinBounds);
      
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_Atz3D_Camera);

    } catch (Exception e) {// Catch exception if any
      //System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }

  /**
   *
   * Import the camera state from a data file
   *
   * @param filename
   * @param flagType
   */
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

  /**
   *
   * Perform these operations when starting to parse the XML file.
   *
   * @param sourceHandler
   */
  public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    /* nothing to do */
  }


  /**
   *
   * Perform these operations when done parsing an XML file.
   *
   * @param sourceHandler
   */
  public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    /* nothing to do */
  }

  
  /**
   *
   * Perform this operation when starting to parse an XML tag.
   *
   * @param uri
   * @param localName
   * @param qName
   * @param attributes
   * @param sourceHandler
   * @throws SAXException
   */
  public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;
    
  }


  /**
   *
   * Collect misc. XML characters that are in-between tag regions.
   *
   * @param ch
   * @param start
   * @param length
   * @param sourceHandler
   * @throws SAXException
   */
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
  }

  /**
   *
   * Perform these operations when encountering the end of an XML tag region.
   *
   * @param uri
   * @param localName
   * @param qName
   * @param sourceHandler
   * @throws SAXException
   */
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(tagXML_Atz3D_Camera)) {
      /* check all entries set */    
    } else if (qName.equals(tagXML_cameraVRP)) {
      cameraVRP = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    } else if (qName.equals(tagXML_cameraVUP)) {
      cameraVUP = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    } else if (qName.equals(tagXML_cameraVPN)) {
      cameraVPN = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    } else if (qName.equals(tagXML_cameraCW)) {
      cameraCW = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    } else if (qName.equals(tagXML_cameraWinBounds)) {
      cameraWinBounds = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    }
    
  }

  /**
   *
   * Get data associated with the XML parsing just performed.
   *
   * @return copy of this camera class
   */
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */



} /* end Camera class */
