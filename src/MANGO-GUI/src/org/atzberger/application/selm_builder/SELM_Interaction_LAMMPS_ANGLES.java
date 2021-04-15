package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_Helper_SAX_ListDataHandler;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element_Lines;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
public class SELM_Interaction_LAMMPS_ANGLES extends SELM_Interaction implements SELM_InteractionInterface_LAMMPS_ANGLES, Atz_DataChangeListener, Atz_XML_SAX_DataHandlerInterface {
    
  private int             numAngles        = 0;
  private int             numAnglesAlloc   = 0;
  private final int       num_dim         = 3;

  private SELM_Lagrangian angleList_lagrangianI1[] = null;  /* assumes I1 < I2 < I3 (keeps unique) */
  private int             angleList_ptI1[]         = null;  /* assumes I1 < I2 < I3 (keeps unique) */

  private SELM_Lagrangian angleList_lagrangianI2[] = null;
  private int             angleList_ptI2[]         = null;  /* assumes I1 < I2 < I3 (keeps unique) */

  private SELM_Lagrangian angleList_lagrangianI3[] = null;
  private int             angleList_ptI3[]         = null;  /* assumes I1 < I2 < I3 (keeps unique) */

  private int             angleTypeID        = -1;
  private String          angleStyle         = "cosine";
  private String          angleCoeffsStr     = "1.0";
      
  private boolean         flagVisible = true;
  private Color           plotColor   = Color.blue;
  
  /* index of element types within the representation of this type */
  public final int    atz3D_Index_Lines = 0;

  Atz3D_Element_Lines atz3D_Element_Lines = new Atz3D_Element_Lines();

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public String[]  xml_angleList_lagrangianNamesI1    = null;
  public String[]  xml_angleList_lagrangianTypesStrI1 = null;

  public String[]  xml_angleList_lagrangianNamesI2    = null;
  public String[]  xml_angleList_lagrangianTypesStrI2 = null;

  public String[]  xml_angleList_lagrangianNamesI3    = null;
  public String[]  xml_angleList_lagrangianTypesStrI3 = null;

  public static String tagXML_numAngles               = "numAngles";

  public static String tagXML_angleList_lagrangianI1  = "angleList_lagrangianI1";
  public static String tagXML_angleList_lagrangianI2  = "angleList_lagrangianI2";
  public static String tagXML_angleList_lagrangianI3  = "angleList_lagrangianI3";

  public static String tagXML_angleList_ptI1          = "angleList_ptI1";
  public static String tagXML_angleList_ptI2          = "angleList_ptI2";
  public static String tagXML_angleList_ptI3          = "angleList_ptI3";

  public static String tagXML_angleTypeID             = "angleTypeID";
  public static String tagXML_angleStyle              = "angleStyle";
  public static String tagXML_angleCoeffs             = "angleCoeffs";

  public static String tagXML_flagVisible             = "flagVisible";
  public static String tagXML_plotColor               = "plotColor";
 
  public SELM_Interaction_LAMMPS_ANGLES() {

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    InteractionName    = "Your Name Here";
    InteractionTypeStr = thisClassName.replace(superClassName + "_", "");

    numAnglesAlloc = 1;
    numAngles      = 0;

    angleList_ptI1 = new int[numAnglesAlloc];
    angleList_ptI2 = new int[numAnglesAlloc];
    angleList_ptI3 = new int[numAnglesAlloc];

    angleList_lagrangianI1 = new SELM_Lagrangian[numAnglesAlloc];
    angleList_lagrangianI2 = new SELM_Lagrangian[numAnglesAlloc];
    angleList_lagrangianI3 = new SELM_Lagrangian[numAnglesAlloc];
    
    flagVisible = true;
    plotColor   = Color.blue;

  }


  public void setPlotColor(Color color_in) {
    plotColor = color_in;
  }

  public Color getPlotColor() {
    return plotColor;
  }

  public int getAngleTypeID() {
    return angleTypeID;
  }

  public void setAngleStyle(String angleStyle_in) {
    angleStyle = angleStyle_in;
  }

  public void setAngleTypeID(int angleTypeID_in) {
    angleTypeID = angleTypeID_in;
  }
  
  public void setAngleCoeffsStr(String angleCoeffs_in) {
    angleCoeffsStr = angleCoeffs_in;
  }

  public String getAngleStyle() {
    return angleStyle;
  }

  public String getAngleCoeffsStr() {
    return angleCoeffsStr;
  }

  public void setVisible(boolean flagVisible_in) {
    flagVisible = flagVisible_in;
  }

  public boolean isVisible() {
    return flagVisible;
  }
 
  /* add a pair to the list */
  void addAngle(SELM_Lagrangian lagrangianI1_in, int ptI1_in,
                SELM_Lagrangian lagrangianI2_in, int ptI2_in,
                SELM_Lagrangian lagrangianI3_in, int ptI3_in) {

    boolean flagNewAngle = true;
    
    int             i,j,k;
    int             ptI1, ptI2, ptI3;
    SELM_Lagrangian lagrangianI1, lagrangianI2, lagrangianI3;

    int    angleList_ptI1_new[];
    int    angleList_ptI2_new[];
    int    angleList_ptI3_new[];

    int    angleList_lagrangianI1_new[];
    int    angleList_lagrangianI2_new[];
    int    angleList_lagrangianI3_new[];
    
    /* == make sure ptI1 < ptI2 < ptI3 */

    /* WARNING: DO NOT SORT, the order matters to define the angle */
    
//    /* sort the indices */
//    List<Integer> list = new ArrayList<Integer>(3);
//    list.add(ptI1_in);
//    list.add(ptI2_in);
//    list.add(ptI3_in);
//
//    Collections.sort(list);
//
//    ptI1         = list.get(0);
//    ptI2         = list.get(1);
//    ptI3         = list.get(2);
//
//    int[]             indices        = new int[3];
//    SELM_Lagrangian[] lagrangianList = new SELM_Lagrangian[3];
//
//    lagrangianList[0] = lagrangianI1_in;
//    lagrangianList[1] = lagrangianI2_in;
//    lagrangianList[2] = lagrangianI3_in;
//
//    for (int m = 0; m < 3; m++) {
//
//      if (list.get(m) == ptI1_in) {
//        indices[m] = 0;
//      }
//
//      if (list.get(m) == ptI2_in) {
//        indices[m] = 1;
//      }
//
//      if (list.get(m) == ptI3_in) {
//        indices[m] = 2;
//      }
//
//    } /* end of m loop */
//
//    lagrangianI1 = lagrangianList[indices[0]];
//    lagrangianI2 = lagrangianList[indices[1]];
//    lagrangianI3 = lagrangianList[indices[2]];


    /* set without sorting */
    ptI1         = ptI1_in;
    ptI2         = ptI2_in;
    ptI3         = ptI3_in;

    lagrangianI1 = lagrangianI1_in;
    lagrangianI2 = lagrangianI2_in;
    lagrangianI3 = lagrangianI3_in;

    
    /* == check to make sure does not already exist */
    //System.out.println("numAngles = " + numAngles);
    //System.out.println("angleList_ptI1.length = " + angleList_ptI1.length);

    for (k = 0; k < numAngles; k++) {

      if ( (angleList_ptI1[k] == ptI1) && 
           (angleList_ptI2[k] == ptI2) &&
           (angleList_ptI3[k] == ptI3) &&
           (angleList_lagrangianI1[k] == lagrangianI1) &&
           (angleList_lagrangianI2[k] == lagrangianI2) &&
           (angleList_lagrangianI3[k] == lagrangianI3)
         ) {
        flagNewAngle = false;
      }
    } /* end k loop */

    /* == add this entry to the list */
    if (flagNewAngle) {

      if (numAngles < numAnglesAlloc) {

        angleList_ptI1[numAngles]         = ptI1;
        angleList_lagrangianI1[numAngles] = lagrangianI1;

        angleList_ptI2[numAngles]         = ptI2;
        angleList_lagrangianI2[numAngles] = lagrangianI2;

        angleList_ptI3[numAngles]         = ptI3;
        angleList_lagrangianI3[numAngles] = lagrangianI3;
        numAngles++;

      } else { /* == resize arrays is necessary, before add */
        
        int numAnglesAlloc_new = 2 * numAnglesAlloc;

        if (numAnglesAlloc_new <= numAngles) { /* if still smaller */
          numAnglesAlloc_new = numAngles + 10;
        }

        /* resize the lists to the specified size */
        resizeLists(numAnglesAlloc_new);

        /* try to add again */
        addAngle(lagrangianI1, ptI1,
                 lagrangianI2, ptI2,
                 lagrangianI3, ptI3); /* can @optimize checks */

      } /* end else */

      /* add a data change listener for this interaction */
      if (lagrangianI1 != null)
        lagrangianI1.addDataChangeListener(this);

      if (lagrangianI2 != null)
        lagrangianI2.addDataChangeListener(this);

      if (lagrangianI3 != null)
        lagrangianI3.addDataChangeListener(this);

    } /* end flagNewAngle */

  } /* add interaction */

  /* remove pair if present in the list */
  void removeAngle(SELM_Lagrangian lagrangianI1_in, int ptI1_in,
                  SELM_Lagrangian lagrangianI2_in, int ptI2_in,
                  SELM_Lagrangian lagrangianI3_in, int ptI3_in) {
    
    int ptI1, ptI2, ptI3;
    SELM_Lagrangian lagrangianI1, lagrangianI2, lagrangianI3;

//    /* sort the indices */
//    List<Integer> list = new ArrayList<Integer>(3);
//    list.add(ptI1_in);
//    list.add(ptI2_in);
//    list.add(ptI3_in);
//
//    Collections.sort(list);
//
//    ptI1         = list.get(0);
//    ptI2         = list.get(1);
//    ptI3         = list.get(2);
//
//    int[]             indices        = new int[3];
//    SELM_Lagrangian[] lagrangianList = new SELM_Lagrangian[3];
//
//    lagrangianList[0] = lagrangianI1_in;
//    lagrangianList[1] = lagrangianI2_in;
//    lagrangianList[2] = lagrangianI3_in;
//
//    for (int m = 0; m < 3; m++) {
//
//      if (list.get(m) == ptI1_in) {
//        indices[m] = 0;
//      }
//
//      if (list.get(m) == ptI2_in) {
//        indices[m] = 1;
//      }
//
//      if (list.get(m) == ptI3_in) {
//        indices[m] = 2;
//      }
//
//    } /* end of m loop */
//
//    lagrangianI1 = lagrangianList[indices[0]];
//    lagrangianI2 = lagrangianList[indices[1]];
//    lagrangianI3 = lagrangianList[indices[2]];
    
    /* set without sorting */
    ptI1         = ptI1_in;
    ptI2         = ptI2_in;
    ptI3         = ptI3_in;

    lagrangianI1 = lagrangianI1_in;
    lagrangianI2 = lagrangianI2_in;
    lagrangianI3 = lagrangianI3_in;

    /* == relabel indices and remove entries for given ptI */
    for (int k = 0; k < numAngles; k++) {
      if ((angleList_ptI1[k] == ptI1) &&
          (angleList_ptI2[k] == ptI2) &&
          (angleList_ptI2[k] == ptI3) &&
          (angleList_lagrangianI1[k] == lagrangianI1) &&
          (angleList_lagrangianI2[k] == lagrangianI2) &&
          (angleList_lagrangianI3[k] == lagrangianI3)
          ) {
        angleList_ptI1[k]         = -1; /* mark for removal */
        angleList_ptI2[k]         = -1;
        angleList_ptI3[k]         = -1;
        angleList_lagrangianI1[k] = null;
        angleList_lagrangianI2[k] = null;
        angleList_lagrangianI3[k] = null;
      }
    } /* end k loop */

    /* removes all entries with ptI1 or ptI2 == -1 */
    removeInteraction();
    
  }


  /* remove and re-label all of the pairs */
  void removePtI(SELM_Lagrangian lagrangianI, int ptI) {
    
    int     k;
    
    /* == relabel indices and remove entries for given ptI */
    for (k = 0; k < numAngles; k++) {

      if ( ((angleList_ptI1[k] == ptI) || (angleList_ptI2[k] == ptI) || (angleList_ptI3[k] == ptI)) &&
           ((angleList_lagrangianI1[k] == lagrangianI) || (angleList_lagrangianI2[k] == lagrangianI) || (angleList_lagrangianI3[k] == lagrangianI)) ) {
        angleList_ptI1[k]         = -1; /* mark for removal */
        angleList_ptI2[k]         = -1;
        angleList_ptI3[k]         = -1;
        angleList_lagrangianI1[k] = null;
        angleList_lagrangianI2[k] = null;
        angleList_lagrangianI3[k] = null;
      } else { /* otherwise reduce index by one for all indices larger than ptI */
        if ((angleList_ptI1[k] > ptI) && (angleList_lagrangianI1[k] == lagrangianI)) {
          angleList_ptI1[k]--;
        }
        if ((angleList_ptI2[k] > ptI) && (angleList_lagrangianI2[k] == lagrangianI)) {
          angleList_ptI2[k]--;
        }
        if ((angleList_ptI3[k] > ptI) && (angleList_lagrangianI3[k] == lagrangianI)) {
          angleList_ptI3[k]--;
        }
      }
      
    } /* end k loop */

    /* removes all entries with ptI1 or ptI2 == -1 */
    removeInteraction();

  }




  /* remove interaction */
  void removeInteraction(SELM_Lagrangian lagrangianI1_in, int ptI1_in,
                         SELM_Lagrangian lagrangianI2_in, int ptI2_in,
                         SELM_Lagrangian lagrangianI3_in, int ptI3_in) {

    int k;
    int ptI1, ptI2, ptI3;
    SELM_Lagrangian lagrangianI1, lagrangianI2, lagrangianI3;

    boolean flagFound   = false;
    int     removeIndex = -1;

//    /* sort the indices */
//    List<Integer> list = new ArrayList<Integer>(3);
//    list.add(ptI1_in);
//    list.add(ptI2_in);
//    list.add(ptI3_in);
//
//    Collections.sort(list);
//
//    ptI1         = list.get(0);
//    ptI2         = list.get(1);
//    ptI3         = list.get(2);
//
//    int[]             indices        = new int[3];
//    SELM_Lagrangian[] lagrangianList = new SELM_Lagrangian[3];
//
//    lagrangianList[0] = lagrangianI1_in;
//    lagrangianList[1] = lagrangianI2_in;
//    lagrangianList[2] = lagrangianI3_in;
//
//    for (int m = 0; m < 3; m++) {
//
//      if (list.get(m) == ptI1_in) {
//        indices[m] = 0;
//      }
//
//      if (list.get(m) == ptI2_in) {
//        indices[m] = 1;
//      }
//
//      if (list.get(m) == ptI3_in) {
//        indices[m] = 2;
//      }
//
//    } /* end of m loop */
//
//    lagrangianI1 = lagrangianList[indices[0]];
//    lagrangianI2 = lagrangianList[indices[1]];
//    lagrangianI3 = lagrangianList[indices[2]];

    /* set without sorting */
    ptI1         = ptI1_in;
    ptI2         = ptI2_in;
    ptI3         = ptI3_in;

    lagrangianI1 = lagrangianI1_in;
    lagrangianI2 = lagrangianI2_in;
    lagrangianI3 = lagrangianI3_in;

    /* == find index of entry to remove */
    for (k = 0; k < numAngles; k++) {

      if ( (angleList_ptI1[k] == ptI1) &&
           (angleList_ptI2[k] == ptI2) &&
           (angleList_ptI3[k] == ptI3) &&
           (angleList_lagrangianI1[k] == lagrangianI1) &&
           (angleList_lagrangianI2[k] == lagrangianI2) &&
           (angleList_lagrangianI3[k] == lagrangianI3)
          ) {
        removeIndex = k;
        flagFound   = true;
      }
      
    } /* end k loop */

    /* == if instance found then remove this entry */
    if (flagFound) {
      removeInteraction(removeIndex);
    } /* end flagFound */
    
  } /* end removeInteraction */


  /* remove interaction */
  void removeInteraction(int removeIndex) {

    int k;
    boolean flagFound = false;
    
    int             angleList_ptI1_new[];
    int             angleList_ptI2_new[];
    int             angleList_ptI3_new[];

    SELM_Lagrangian angleList_lagrangianI1_new[];
    SELM_Lagrangian angleList_lagrangianI2_new[];
    SELM_Lagrangian angleList_lagrangianI3_new[];
    
    /* resize array if a lot of slack */
    if (numAngles < numAnglesAlloc / 2) {
      numAnglesAlloc = numAnglesAlloc / 2;
    }

    /* copy data to smaller array with entry deleted */
    angleList_ptI1_new         = new int[numAnglesAlloc];
    angleList_ptI2_new         = new int[numAnglesAlloc];
    angleList_ptI3_new         = new int[numAnglesAlloc];

    angleList_lagrangianI1_new = new SELM_Lagrangian[numAnglesAlloc];
    angleList_lagrangianI2_new = new SELM_Lagrangian[numAnglesAlloc];
    angleList_lagrangianI3_new = new SELM_Lagrangian[numAnglesAlloc];
    
    /* copy first part of the array */
    if (removeIndex >= 0) {
      System.arraycopy(angleList_ptI1, 0, angleList_ptI1_new, 0, removeIndex);
      System.arraycopy(angleList_ptI2, 0, angleList_ptI2_new, 0, removeIndex);
      System.arraycopy(angleList_ptI3, 0, angleList_ptI3_new, 0, removeIndex);

      System.arraycopy(angleList_lagrangianI1, 0, angleList_lagrangianI1_new, 0, removeIndex);
      System.arraycopy(angleList_lagrangianI2, 0, angleList_lagrangianI2_new, 0, removeIndex);
      System.arraycopy(angleList_lagrangianI3, 0, angleList_lagrangianI3_new, 0, removeIndex);
      
    }

    /* copy second part of the array */
    System.arraycopy(angleList_ptI1, removeIndex + 1, angleList_ptI1_new, removeIndex, numAngles - removeIndex - 1);
    System.arraycopy(angleList_ptI2, removeIndex + 1, angleList_ptI2_new, removeIndex, numAngles - removeIndex - 1);
    System.arraycopy(angleList_ptI3, removeIndex + 1, angleList_ptI3_new, removeIndex, numAngles - removeIndex - 1);

    System.arraycopy(angleList_lagrangianI1, removeIndex + 1, angleList_lagrangianI1_new, removeIndex, numAngles - removeIndex - 1);
    System.arraycopy(angleList_lagrangianI2, removeIndex + 1, angleList_lagrangianI2_new, removeIndex, numAngles - removeIndex - 1);
    System.arraycopy(angleList_lagrangianI3, removeIndex + 1, angleList_lagrangianI3_new, removeIndex, numAngles - removeIndex - 1);
    
    angleList_ptI1         = angleList_ptI1_new;
    angleList_ptI2         = angleList_ptI2_new;
    angleList_ptI3         = angleList_ptI3_new;

    angleList_lagrangianI1 = angleList_lagrangianI1_new;
    angleList_lagrangianI2 = angleList_lagrangianI2_new;
    angleList_lagrangianI3 = angleList_lagrangianI3_new;
    
    numAngles--; /* decrement the pair, since one removed */

  }


  /* removes all entries with ptI == -1 */
  void removeInteraction() {

    int k;    

    int numPairs_new = 0;

    boolean flagFound = false;

    int             angleList_ptI1_new[];
    int             angleList_ptI2_new[];
    int             angleList_ptI3_new[];

    SELM_Lagrangian angleList_lagrangianI1_new[];
    SELM_Lagrangian angleList_lagrangianI2_new[];
    SELM_Lagrangian angleList_lagrangianI3_new[];
    
    /* copy data to smaller array with entry deleted */
    angleList_ptI1_new         = new int[numAnglesAlloc];
    angleList_ptI2_new         = new int[numAnglesAlloc];
    angleList_ptI3_new         = new int[numAnglesAlloc];

    angleList_lagrangianI1_new = new SELM_Lagrangian[numAnglesAlloc];
    angleList_lagrangianI2_new = new SELM_Lagrangian[numAnglesAlloc];
    angleList_lagrangianI3_new = new SELM_Lagrangian[numAnglesAlloc];
    
    /* copy only the entries not having ptI == -1 and lagrangianI == null*/
    for (k = 0; k < numAngles; k++) {

      if ( (angleList_ptI1[k] != -1) &&
           (angleList_ptI2[k] != -1) &&
           (angleList_ptI3[k] != -1) &&
           (angleList_lagrangianI1[k] != null) &&
           (angleList_lagrangianI2[k] != null) &&
           (angleList_lagrangianI3[k] != null) ) {

        angleList_ptI1_new[numPairs_new]         = angleList_ptI1[k];
        angleList_ptI2_new[numPairs_new]         = angleList_ptI2[k];
        angleList_ptI3_new[numPairs_new]         = angleList_ptI3[k];

        angleList_lagrangianI1_new[numPairs_new] = angleList_lagrangianI1[k];
        angleList_lagrangianI2_new[numPairs_new] = angleList_lagrangianI2[k];
        angleList_lagrangianI3_new[numPairs_new] = angleList_lagrangianI3[k];
        
        numPairs_new++;
      }

    } /* end k loop */

    numAngles              = numPairs_new; /* update the number of pairs */

    angleList_ptI1         = angleList_ptI1_new;
    angleList_ptI2         = angleList_ptI2_new;
    angleList_ptI3         = angleList_ptI3_new;

    angleList_lagrangianI1 = angleList_lagrangianI1_new;
    angleList_lagrangianI2 = angleList_lagrangianI2_new;
    angleList_lagrangianI3 = angleList_lagrangianI3_new;
    
  } /* removeInteraction */

  public int getNumPairs() {
    return numAngles;
  }
  
  public int[] getAngleList_ptI1() {
    return makeSubArray(angleList_ptI1,numAngles);
  }

  public int[] getAngleList_ptI2() {
    return makeSubArray(angleList_ptI2,numAngles);
  }

  public int[] getAngleList_ptI3() {
    return makeSubArray(angleList_ptI3,numAngles);
  }

  public SELM_Lagrangian[] getAngleList_lagrangianI1() {
    return makeSubArray(angleList_lagrangianI1, numAngles);
  }

  public SELM_Lagrangian[] getAngleList_lagrangianI2() {
    return makeSubArray(angleList_lagrangianI2, numAngles);
  }

  public SELM_Lagrangian[] getAngleList_lagrangianI3() {
    return makeSubArray(angleList_lagrangianI3, numAngles);
  }
  
  public Color getRenderColor() {
    return plotColor;
  }

  public boolean getRenderVisible() {
    return flagVisible;
  }

  public void setRenderColor(Color renderColor_in) {
    plotColor = renderColor_in;
  }

  public void setRenderVisible(boolean renderVisible_in) {
    flagVisible = renderVisible_in;
  }

  public void setAngleList_lagrangianI1(SELM_Lagrangian[] angleList_lagrangianI1_in) {

    int numPairs_in = angleList_lagrangianI1_in.length;

    if (numPairs_in > numAnglesAlloc) {
      resizeLists(numPairs_in);
    }
    
    numAngles = numPairs_in;

    setSubArray(angleList_lagrangianI1_in, angleList_lagrangianI1, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (angleList_lagrangianI1_in[k] != null)
        (angleList_lagrangianI1_in[k]).addDataChangeListener(this);
    }

  }

  public void setAngleList_lagrangianI2(SELM_Lagrangian[] angleList_lagrangianI2_in) {

    int numPairs_in = angleList_lagrangianI2_in.length;

    if (numPairs_in > numAnglesAlloc) {
      resizeLists(numPairs_in);
    }

    numAngles = numPairs_in;

    setSubArray(angleList_lagrangianI2_in, angleList_lagrangianI2, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (angleList_lagrangianI2_in[k] != null)
        (angleList_lagrangianI2_in[k]).addDataChangeListener(this);
    }
    
  }

  public void setAngleList_lagrangianI3(SELM_Lagrangian[] angleList_lagrangianI3_in) {

    int numPairs_in = angleList_lagrangianI3_in.length;

    if (numPairs_in > numAnglesAlloc) {
      resizeLists(numPairs_in);
    }

    numAngles = numPairs_in;

    setSubArray(angleList_lagrangianI3_in, angleList_lagrangianI3, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (angleList_lagrangianI3_in[k] != null)
        (angleList_lagrangianI3_in[k]).addDataChangeListener(this);
    }

  }

  public void setAngleData(int numAngles_in,
                           SELM_Lagrangian[] angleList_lagrangianI1_in, int[] angleList_ptI1_in,
                           SELM_Lagrangian[] angleList_lagrangianI2_in, int[] angleList_ptI2_in,
                           SELM_Lagrangian[] angleList_lagrangianI3_in, int[] angleList_ptI3_in) {
   
    if (numAngles_in >= numAnglesAlloc) {
      resizeLists(2*numAngles_in);
    }
    
    setSubArray(angleList_ptI1_in,         angleList_ptI1,         numAngles_in);
    setSubArray(angleList_ptI2_in,         angleList_ptI2,         numAngles_in);
    setSubArray(angleList_ptI3_in,         angleList_ptI3,         numAngles_in);

    setSubArray(angleList_lagrangianI1_in, angleList_lagrangianI1, numAngles_in);
    setSubArray(angleList_lagrangianI2_in, angleList_lagrangianI2, numAngles_in);
    setSubArray(angleList_lagrangianI3_in, angleList_lagrangianI3, numAngles_in);
    
    numAngles = numAngles_in;

    /* add a data change listener for this interaction */
    for (int k = 0; k < numAngles_in; k++) {

      if (angleList_lagrangianI1_in[k] != null)
        (angleList_lagrangianI1_in[k]).addDataChangeListener(this);

      if (angleList_lagrangianI2_in[k] != null)
        (angleList_lagrangianI2_in[k]).addDataChangeListener(this);
      
      if (angleList_lagrangianI3_in[k] != null)
        (angleList_lagrangianI3_in[k]).addDataChangeListener(this);

    }
     
  }

  private int[] makeSubArray(int[] array, int numItems) {

    int[] subArray = new int[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;

  }

  private double[] makeSubArray(double[] array, int numItems) {

    double[] subArray = new double[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;

  }

  private SELM_Lagrangian[] makeSubArray(SELM_Lagrangian[] array, int numItems) {

    SELM_Lagrangian[] subArray = new SELM_Lagrangian[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;

  }

  private void setSubArray(int[] subArray, int[] array, int numItems) {
    System.arraycopy(subArray, 0, array, 0, numItems);
  }

  private void setSubArray(double[] subArray, double[] array, int numItems) {
    System.arraycopy(subArray, 0, array, 0, numItems);
  }

  private void setSubArray(Object[] subArray, Object[] array, int numItems) {
    System.arraycopy(subArray, 0, array, 0, numItems);
  }

  private void resizeLists(int numAnglesAlloc_new) {

    int numToCopy = -1;

    numToCopy = java.lang.Math.min(numAngles, numAnglesAlloc_new);
    
    if (numAnglesAlloc_new <= numAngles) {
      //System.out.println("ERROR: Interaction_PAIRS_HARMONIC.resizeLists()");
      //System.out.println("numAnglesAlloc_new <= numAngles");
      /* this is now allowed, since numToCopy is min */
    }
  
    int[]                 angleList_ptI1_new         = new int[numAnglesAlloc_new];
    int[]                 angleList_ptI2_new         = new int[numAnglesAlloc_new];
    int[]                 angleList_ptI3_new         = new int[numAnglesAlloc_new];

    SELM_Lagrangian[]     angleList_lagrangianI1_new = new SELM_Lagrangian[numAnglesAlloc_new];
    SELM_Lagrangian[]     angleList_lagrangianI2_new = new SELM_Lagrangian[numAnglesAlloc_new];
    SELM_Lagrangian[]     angleList_lagrangianI3_new = new SELM_Lagrangian[numAnglesAlloc_new];
    
    System.arraycopy(angleList_ptI1, 0, angleList_ptI1_new, 0, numToCopy);
    System.arraycopy(angleList_ptI2, 0, angleList_ptI2_new, 0, numToCopy);
    System.arraycopy(angleList_ptI3, 0, angleList_ptI3_new, 0, numToCopy);

    System.arraycopy(angleList_lagrangianI1, 0, angleList_lagrangianI1_new, 0, numToCopy);
    System.arraycopy(angleList_lagrangianI2, 0, angleList_lagrangianI2_new, 0, numToCopy);
    System.arraycopy(angleList_lagrangianI3, 0, angleList_lagrangianI3_new, 0, numToCopy);
   
    angleList_ptI1         = angleList_ptI1_new;
    angleList_ptI2         = angleList_ptI2_new;
    angleList_ptI3         = angleList_ptI3_new;

    angleList_lagrangianI1 = angleList_lagrangianI1_new;
    angleList_lagrangianI2 = angleList_lagrangianI2_new;
    angleList_lagrangianI3 = angleList_lagrangianI3_new;

    numAnglesAlloc         = numAnglesAlloc_new;

  }


  @Override
  public SELM_Interaction_LAMMPS_ANGLES clone() {
    SELM_Interaction_LAMMPS_ANGLES interaction_copy = new SELM_Interaction_LAMMPS_ANGLES();

    interaction_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    interaction_copy.InteractionName                   = this.InteractionName.toString();
    interaction_copy.InteractionTypeStr                = this.InteractionTypeStr.toString();
       
    interaction_copy.numAngles                          = this.numAngles;
    interaction_copy.numAnglesAlloc                     = this.numAnglesAlloc;
    interaction_copy.angleList_ptI1                     = this.angleList_ptI1.clone();
    interaction_copy.angleList_ptI2                     = this.angleList_ptI2.clone();
    interaction_copy.angleList_ptI3                     = this.angleList_ptI3.clone();

    interaction_copy.angleList_lagrangianI1             = this.angleList_lagrangianI1.clone();
    interaction_copy.angleList_lagrangianI2             = this.angleList_lagrangianI2.clone();
    interaction_copy.angleList_lagrangianI3             = this.angleList_lagrangianI3.clone();
   
    interaction_copy.xml_angleList_lagrangianNamesI1    = this.xml_angleList_lagrangianNamesI1;
    interaction_copy.xml_angleList_lagrangianTypesStrI1 = this.xml_angleList_lagrangianTypesStrI1;

    interaction_copy.xml_angleList_lagrangianNamesI2    = this.xml_angleList_lagrangianNamesI2;
    interaction_copy.xml_angleList_lagrangianTypesStrI2 = this.xml_angleList_lagrangianTypesStrI2;

    interaction_copy.xml_angleList_lagrangianNamesI3    = this.xml_angleList_lagrangianNamesI3;
    interaction_copy.xml_angleList_lagrangianTypesStrI3 = this.xml_angleList_lagrangianTypesStrI3;

    interaction_copy.angleStyle                         = angleStyle.toString();
    interaction_copy.angleTypeID                        = angleTypeID;
    interaction_copy.angleCoeffsStr                     = angleCoeffsStr.toString();
        
    interaction_copy.plotColor                         = new Color(this.plotColor.getRGB());
    interaction_copy.flagVisible                       = this.flagVisible;
  
    return interaction_copy;
    
  }

  public String getRenderTag() {
    return atz3D_RENDER_TAG_INTERACTION;
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

    atz3D_Element_Lines.setPlotColor(plotColor);

    /* loop over all of the interactions and construct
     * lines for each one.
     */
    int             N = numAngles;
    int             I = 0;
    int             I1, I2, I3;
    double[]        X1, X2, X3;
    double[]        ptsX1, ptsX2, ptsX3;
    SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangianI1, lagrangianI2, lagrangianI3;

    X1    = new double[num_dim];
    ptsX1 = new double[2*N*num_dim];

    X2    = new double[num_dim];
    ptsX2 = new double[2*N*num_dim];

    X3    = new double[num_dim];
    
    for (int k = 0; k < N; k++) {
      
      lagrangianI1 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) angleList_lagrangianI1[k];
      I1           = angleList_ptI1[k];

      lagrangianI2 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) angleList_lagrangianI2[k];
      I2           = angleList_ptI2[k];

      lagrangianI3 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) angleList_lagrangianI3[k];
      I3           = angleList_ptI3[k];

      if ((lagrangianI1 != null) && (lagrangianI2 != null) && (lagrangianI3 != null)) {

        X1 = lagrangianI1.getPtsX(I1);
        X2 = lagrangianI2.getPtsX(I2);
        X3 = lagrangianI3.getPtsX(I3);

        for (int d = 0; d < num_dim; d++) {
          ptsX1[I*num_dim + d] = X1[d];
          ptsX2[I*num_dim + d] = X2[d];
        }
        I++;

        for (int d = 0; d < num_dim; d++) {
          ptsX1[I*num_dim + d] = X3[d];
          ptsX2[I*num_dim + d] = X2[d];
        }
        I++;
        
      }

    } /* end of k loop */

    atz3D_Element_Lines.setLines(ptsX1, ptsX2);
    
    //atz3D_Index_Lines       = 0;
    list[atz3D_Index_Lines] = atz3D_Element_Lines;

    return list;
  }

  public void handleDataChange(Atz_DataChangeEvent e) {
    
    //System.out.println("Handle Data Change");
    //System.out.println("getDataChangeType() = " + e.getDataChangeType());

    /* check which type of object generated the signal */
    if (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE.class.isInstance(e.getSource())) {

      SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE source = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) e.getSource();

      if (source.DATA_CHANGE_REMOVE_PT.equals(e.getDataChangeType())) {
        HashMap extras = (HashMap) e.getDataChangeExtraInfo();

        int                                ptsToRemoveIndex = (Integer) extras.get("ptsToRemoveIndex");
        SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangian     = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) extras.get("lagrangian");

        //System.out.println("Signal to Remove Points..." + lagrangian.LagrangianName + ":" + ptsToRemoveIndex);
        //System.out.println("");

        /* remove all interactions involving this particular point */
        /* also, reindex the pairs to reflect the delection of this point */
        removePtI(lagrangian, ptsToRemoveIndex);
        
      }

    } /* SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE */

  }


  /* ====================================================== */
  /* ================= LAMMPS Interface =================== */
  @Override
  public SELM_LagrangianInterface_LAMMPS[] getAngleListLagrangian1() {
    int N = this.numAngles;
    SELM_LagrangianInterface_LAMMPS list[] = new SELM_LagrangianInterface_LAMMPS[N];
    for (int k = 0; k < N; k++) {
      list[k] = (SELM_LagrangianInterface_LAMMPS)angleList_lagrangianI1[k];
    }
    return list;
  }

  @Override
  public SELM_LagrangianInterface_LAMMPS[] getAngleListLagrangian2() {
    int N = this.numAngles;
    SELM_LagrangianInterface_LAMMPS list[] = new SELM_LagrangianInterface_LAMMPS[N];
    for (int k = 0; k < N; k++) {
      list[k] = (SELM_LagrangianInterface_LAMMPS)angleList_lagrangianI2[k];
    }
    return list;
  }

  @Override
  public SELM_LagrangianInterface_LAMMPS[] getAngleListLagrangian3() {
    int N = this.numAngles;
    SELM_LagrangianInterface_LAMMPS list[] = new SELM_LagrangianInterface_LAMMPS[N];
    for (int k = 0; k < N; k++) {
      list[k] = (SELM_LagrangianInterface_LAMMPS)angleList_lagrangianI3[k];
    }
    return list;
  }

  @Override
  public int[] getAngleListI1() {
    int N = this.numAngles;
    int[] list = new int[N];
    for (int k = 0; k < N; k++) {
      list[k] = angleList_ptI1[k];
    }
    return list;
  }

  @Override
  public int[] getAngleListI2() {
    int N = this.numAngles;
    int[] list = new int[N];
    for (int k = 0; k < N; k++) {
      list[k] = angleList_ptI2[k];
    }
    return list;    
  }

  @Override
  public int[] getAngleListI3() {
    int N = this.numAngles;
    int[] list = new int[N];
    for (int k = 0; k < N; k++) {
      list[k] = angleList_ptI3[k];
    }
    return list;
  }

  @Override
  public void setFlagGenLAMMPS_XML_Files(boolean val) {
    /* currently does nothing, but could be used to signal that XML
     * files will be used with the LAMMPS simulation so should be set
     * accordingly.
     */
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
      Atz_XML_Helper.writeXMLStartTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

      Atz_XML_Helper.writeXMLData(fid, tagXML_InteractionName,    InteractionName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_InteractionTypeStr, InteractionTypeStr);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_numAngles, numAngles);
      
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_angleList_lagrangianI1);
      SELM_Lagrangian[] listLagrangianI1 = this.getAngleList_lagrangianI1();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        if (listLagrangianI1[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI1[k].LagrangianName, listLagrangianI1[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_angleList_lagrangianI1);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_angleList_ptI1, this.getAngleList_ptI1());

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_angleList_lagrangianI2);
      SELM_Lagrangian[] listLagrangianI2 = this.getAngleList_lagrangianI2();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        if (listLagrangianI2[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI2[k].LagrangianName, listLagrangianI2[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }      
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_angleList_lagrangianI2);

      Atz_XML_Helper.writeXMLData(fid, tagXML_angleList_ptI2, getAngleList_ptI2());

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_angleList_lagrangianI3);
      SELM_Lagrangian[] listLagrangianI3 = this.getAngleList_lagrangianI3();
      for (int k = 0; k < listLagrangianI3.length; k++) {
        if (listLagrangianI3[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI3[k].LagrangianName, listLagrangianI3[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_angleList_lagrangianI3);

      Atz_XML_Helper.writeXMLData(fid, tagXML_angleList_ptI3, this.getAngleList_ptI3());

      Atz_XML_Helper.writeXMLData(fid, tagXML_angleTypeID,  getAngleTypeID());
      Atz_XML_Helper.writeXMLData(fid, tagXML_angleStyle,   getAngleStyle());
      Atz_XML_Helper.writeXMLData(fid, tagXML_angleCoeffs,  getAngleCoeffsStr());
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor,   new Atz_XML_Helper_Handler_Color(plotColor));

      Atz_XML_Helper.writeXMLData(fid, tagXML_flagVisible, flagVisible);

      Atz_XML_Helper.writeXMLEndTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
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
      //sp.parse("test1.SELM_Interaction_CONTROL_PTS_BASIC1", new Atz_XML_DataHandlerWrapper(this));

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

    if (qName.equals(SELM_Interaction.tagXML_SELM_Interaction)) {
      /* nothing special to do */
    } else if (qName.equals(tagXML_angleList_lagrangianI1)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
    } else if (qName.equals(tagXML_angleList_lagrangianI2)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
    } else if (qName.equals(tagXML_angleList_lagrangianI3)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
    } else if (qName.equals(tagXML_plotColor)) {
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
    }
    
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(SELM_Interaction.class.getSimpleName())) {
      /* check all entries set */
    } else if (qName.equals(tagXML_InteractionName)) {

    } else if (qName.equals(tagXML_InteractionTypeStr)) {
      
    } else if (qName.equals(tagXML_numAngles)) {
      int numPairs_new = Integer.parseInt(xmlAttributes.getValue("value"));
      resizeLists(numPairs_new);
      numAngles = numPairs_new;
    } else if (qName.equals(this.tagXML_angleList_lagrangianI1)) {
      Atz_XML_Helper_SAX_ListDataHandler handler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
      HashMap tagDataLists = handler.getTagDataLists();      
      Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
      ArrayList lagrangianRefList       = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
      xml_angleList_lagrangianNamesI1    = new String[lagrangianRefList.size()];
      xml_angleList_lagrangianTypesStrI1 = new String[lagrangianRefList.size()];
      for (int k = 0; k < lagrangianRefList.size(); k++) {
        lagrangianRef                        = (Atz_XML_Helper_Handler_LagrangianRef)lagrangianRefList.get(k);
        xml_angleList_lagrangianNamesI1[k]    = lagrangianRef.LagrangianName;
        xml_angleList_lagrangianTypesStrI1[k] = lagrangianRef.LagrangianTypeStr;
      }
    } else if (qName.equals(this.tagXML_angleList_lagrangianI2)) {
      Atz_XML_Helper_SAX_ListDataHandler handler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
      HashMap tagDataLists = handler.getTagDataLists();
      Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
      ArrayList lagrangianRefList       = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
      xml_angleList_lagrangianNamesI2    = new String[lagrangianRefList.size()];
      xml_angleList_lagrangianTypesStrI2 = new String[lagrangianRefList.size()];
      for (int k = 0; k < lagrangianRefList.size(); k++) {
        lagrangianRef                        = (Atz_XML_Helper_Handler_LagrangianRef)lagrangianRefList.get(k);
        xml_angleList_lagrangianNamesI2[k]    = lagrangianRef.LagrangianName;
        xml_angleList_lagrangianTypesStrI2[k] = lagrangianRef.LagrangianTypeStr;
      }
    } else if (qName.equals(this.tagXML_angleList_lagrangianI3)) {
      Atz_XML_Helper_SAX_ListDataHandler handler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
      HashMap tagDataLists = handler.getTagDataLists();
      Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
      ArrayList lagrangianRefList       = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
      xml_angleList_lagrangianNamesI3    = new String[lagrangianRefList.size()];
      xml_angleList_lagrangianTypesStrI3 = new String[lagrangianRefList.size()];
      for (int k = 0; k < lagrangianRefList.size(); k++) {
        lagrangianRef                        = (Atz_XML_Helper_Handler_LagrangianRef)lagrangianRefList.get(k);
        xml_angleList_lagrangianNamesI3[k]    = lagrangianRef.LagrangianName;
        xml_angleList_lagrangianTypesStrI3[k] = lagrangianRef.LagrangianTypeStr;
      }   
    } else if (qName.equals(tagXML_angleList_ptI1)) {
      angleList_ptI1 = Atz_XML_Helper.parseIntArrayFromString(xmlString);
    } else if (qName.equals(tagXML_angleList_ptI2)) {
      angleList_ptI2 = Atz_XML_Helper.parseIntArrayFromString(xmlString);
    } else if (qName.equals(tagXML_angleList_ptI3)) {
      angleList_ptI3 = Atz_XML_Helper.parseIntArrayFromString(xmlString);
    } else if (qName.equals(tagXML_angleStyle)) {
      angleStyle = xmlAttributes.getValue("value");
    } else if (qName.equals(tagXML_angleTypeID)) {
      angleTypeID = Integer.parseInt(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_angleCoeffs)) {
      angleCoeffsStr = xmlAttributes.getValue("value");
    } else if (qName.equals(tagXML_flagVisible)) {
      flagVisible = Boolean.parseBoolean(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_plotColor)) {
      plotColor = ((Atz_XML_Helper_Handler_Color)sourceHandler.getLastUsedDataHandler()).color;
    }

  }

  @Override
  public void setupLagrangianFromList(SELM_Lagrangian[] lagrangianList) {
    /* assumes XML data was recently read and should be used */
    setupLagrangianListsFromNames(xml_angleList_lagrangianNamesI1,
                                  xml_angleList_lagrangianNamesI2,
                                  xml_angleList_lagrangianNamesI3,
                                  lagrangianList);
  }

  public void setupLagrangianListsFromNames(String[] lagrangianNamesI1, 
                                            String[] lagrangianNamesI2, 
                                            String[] lagrangianNamesI3,
                                            SELM_Lagrangian[] lagrangianList) {

    int N = lagrangianNamesI1.length;

    SELM_Lagrangian[] listLagrangianI1 = new SELM_Lagrangian[N];
    SELM_Lagrangian[] listLagrangianI2 = new SELM_Lagrangian[N];
    SELM_Lagrangian[] listLagrangianI3 = new SELM_Lagrangian[N];

    for (int k = 0; k < N; k++) {
      /* find appropriate lagrangian */
      listLagrangianI1[k] = null;
      listLagrangianI2[k] = null;
      listLagrangianI3[k] = null;
      for (int j = 0; j < lagrangianList.length; j++) {

        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI1[k])) {
          listLagrangianI1[k] = lagrangianList[j];
        }

        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI2[k])) {
          listLagrangianI2[k] = lagrangianList[j];
        }

        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI3[k])) {
          listLagrangianI3[k] = lagrangianList[j];
        }
        
      } /* end of j loop */
    } /* end of k loop */

    /* setup the pairs */
    setAngleList_lagrangianI1(listLagrangianI1);
    setAngleList_lagrangianI2(listLagrangianI2);
    setAngleList_lagrangianI3(listLagrangianI3);

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */




}
