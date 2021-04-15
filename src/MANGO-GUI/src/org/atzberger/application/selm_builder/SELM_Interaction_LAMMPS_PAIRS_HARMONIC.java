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
import java.util.HashMap;
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
public class SELM_Interaction_LAMMPS_PAIRS_HARMONIC extends SELM_Interaction implements Atz_DataChangeListener, Atz_XML_SAX_DataHandlerInterface {
    
  private int             numPairs        = 0;
  private int             numPairsAlloc   = 0;
  private final int       num_dim = 3;

  private SELM_Lagrangian pairList_lagrangianI1[] = null;
  private int             pairList_ptI1[]         = null;

  private SELM_Lagrangian pairList_lagrangianI2[] = null;
  private int             pairList_ptI2[]         = null;  /* assumes I2 > I1 (keeps unique,
                                                              if same lagrangian) */
  private double          restLength[]    = null;
  private double          stiffnessK[]    = null;

  private boolean         flagVisible = true;
  private Color           plotColor   = Color.blue;
  
  /* index of element types within the representation of this type */
  public final int    atz3D_Index_Lines = 0;

  Atz3D_Element_Lines atz3D_Element_Lines = new Atz3D_Element_Lines();

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public String[]  xml_pairList_lagrangianNamesI1    = null;
  public String[]  xml_pairList_lagrangianTypesStrI1 = null;

  public String[]  xml_pairList_lagrangianNamesI2    = null;
  public String[]  xml_pairList_lagrangianTypesStrI2 = null;

  public static String tagXML_numPairs              = "numPairs";

  public static String tagXML_pairList_lagrangianI1 = "pairList_lagrangianI1";
  public static String tagXML_pairList_lagrangianI2 = "pairList_lagrangianI2";

  public static String tagXML_pairList_ptI1         = "pairList_ptI1";
  public static String tagXML_pairList_ptI2         = "pairList_ptI2";

  public static String tagXML_restLength            = "restLength";
  public static String tagXML_stiffnessK            = "stiffnessK";

  public static String tagXML_flagVisible           = "flagVisible";
  public static String tagXML_plotColor             = "plotColor";
 
  SELM_Interaction_LAMMPS_PAIRS_HARMONIC() {

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    InteractionName    = "Your Name Here";
    InteractionTypeStr = thisClassName.replace(superClassName + "_", "");

    numPairsAlloc = 1;
    numPairs      = 0;

    pairList_ptI1 = new int[numPairsAlloc];
    pairList_ptI2 = new int[numPairsAlloc];

    pairList_lagrangianI1 = new SELM_Lagrangian[numPairsAlloc];
    pairList_lagrangianI2 = new SELM_Lagrangian[numPairsAlloc];

    restLength    = new double[numPairsAlloc];
    stiffnessK    = new double[numPairsAlloc];

    flagVisible = true;
    plotColor   = Color.blue;

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


  void addPair(SELM_Lagrangian lagrangianI1_in, int ptI1_in, 
               SELM_Lagrangian lagrangianI2_in, int ptI2_in,
               double stiffnessK_in) {

    double restLength;

    restLength = 0.0;
    addPair(lagrangianI1_in, ptI1_in, lagrangianI2_in, ptI2_in, stiffnessK_in, restLength);

  }


  /* add a pair to the list */
  void addPair(SELM_Lagrangian lagrangianI1_in, int ptI1_in,
               SELM_Lagrangian lagrangianI2_in, int ptI2_in,
               double stiffnessK_in, double restLength_in) {

    boolean flagNewPair = true;
    
    int             i,j,k;
    int             ptI1, ptI2;
    SELM_Lagrangian lagrangianI1, lagrangianI2;

    int    pairList_ptI1_new[];
    int    pairList_ptI2_new[];

    int    pairList_lagrangianI1_new[];
    int    pairList_lagrangianI2_new[];

    double restLength_new[];
    double stiffnessK_new[];

    /* == make sure ptI1 <= ptI2 */
    if (ptI1_in <= ptI2_in) {
      ptI1         = ptI1_in;
      ptI2         = ptI2_in;
      lagrangianI1 = lagrangianI1_in;
      lagrangianI2 = lagrangianI2_in;
    } else {
      ptI1         = ptI2_in;
      ptI2         = ptI1_in;
      lagrangianI1 = lagrangianI2_in;
      lagrangianI2 = lagrangianI1_in;
    }

    /* == check to make sure does not already exist */
    //System.out.println("numPairs = " + numPairs);
    //System.out.println("pairList_ptI1.length = " + pairList_ptI1.length);

    for (k = 0; k < numPairs; k++) {

      if ( (pairList_ptI1[k] == ptI1) && (pairList_ptI2[k] == ptI2) &&
           (pairList_lagrangianI1[k] == lagrangianI1) && (pairList_lagrangianI2[k] == lagrangianI2)
         ) {
        flagNewPair = false;
      }
    } /* end k loop */

    /* == add this entry to the list */
    if (flagNewPair) {

      if (numPairs < numPairsAlloc) {

        pairList_ptI1[numPairs]         = ptI1;
        pairList_lagrangianI1[numPairs] = lagrangianI1;
        pairList_ptI2[numPairs]         = ptI2;
        pairList_lagrangianI2[numPairs] = lagrangianI2;
        stiffnessK[numPairs]            = stiffnessK_in;
        restLength[numPairs]            = restLength_in;
        numPairs++;

      } else { /* == resize arrays is necessary, before add */
        
        int numPairsAlloc_new = 2 * numPairsAlloc;
        if (numPairsAlloc_new <= numPairs) { /* if still smaller */
          numPairsAlloc_new = numPairs + 10;
        }

        /* resize the lists to the specified size */
        resizeLists(numPairsAlloc_new);

        /* try to add again */
        addPair(lagrangianI1, ptI1,
                lagrangianI2, ptI2,
                restLength_in, stiffnessK_in); /* can @optimize checks */

      } /* end else */

      /* add a data change listener for this interaction */
      if (lagrangianI1 != null)
        lagrangianI1.addDataChangeListener(this);

      if (lagrangianI2 != null)
        lagrangianI2.addDataChangeListener(this);

    } /* end flagNewPair */

  } /* add interaction */

  /* remove pair if present in the list */
  void removePair(SELM_Lagrangian lagrangianI1_in, int ptI1_in,
                  SELM_Lagrangian lagrangianI2_in, int ptI2_in) {
    
    int ptI1, ptI2;
    SELM_Lagrangian lagrangianI1, lagrangianI2;
    
    /* == make sure ptI1 <= ptI2 */
    if (ptI1_in <= ptI2_in) {
      ptI1         = ptI1_in;
      ptI2         = ptI2_in;
      lagrangianI1 = lagrangianI1_in;
      lagrangianI2 = lagrangianI2_in;
    } else {
      ptI1 = ptI2_in;
      ptI2 = ptI1_in;
      lagrangianI1 = lagrangianI2_in;
      lagrangianI2 = lagrangianI1_in;
    }

    /* == relabel indices and remove entries for given ptI */
    for (int k = 0; k < numPairs; k++) {
      if ((pairList_ptI1[k] == ptI1) && (pairList_ptI2[k] == ptI2) &&
          (pairList_lagrangianI1[k] == lagrangianI1) && (pairList_lagrangianI2[k] == lagrangianI2)
          ) {
        pairList_ptI1[k]         = -1; /* mark for removal */
        pairList_ptI2[k]         = -1;
        pairList_lagrangianI1[k] = null;
        pairList_lagrangianI2[k] = null;
      }
    } /* end k loop */

    /* removes all entries with ptI1 or ptI2 == -1 */
    removeInteraction();
    
  }


  /* remove and re-label all of the pairs */
  void removePtI(SELM_Lagrangian lagrangianI, int ptI) {
    
    int     k;
    
    /* == relabel indices and remove entries for given ptI */
    for (k = 0; k < numPairs; k++) {

      if ( ((pairList_ptI1[k] == ptI) || (pairList_ptI2[k] == ptI)) &&
           ((pairList_lagrangianI1[k] == lagrangianI) || (pairList_lagrangianI2[k] == lagrangianI)) ) {
        pairList_ptI1[k]         = -1; /* mark for removal */
        pairList_ptI2[k]         = -1;
        pairList_lagrangianI1[k] = null;
        pairList_lagrangianI2[k] = null;
      } else { /* otherwise reduce index by one for all indices larger than ptI */
        if ((pairList_ptI1[k] > ptI) && (pairList_lagrangianI1[k] == lagrangianI)) {
          pairList_ptI1[k]--;
        }
        if ((pairList_ptI2[k] > ptI) && (pairList_lagrangianI2[k] == lagrangianI)) {
          pairList_ptI2[k]--;
        }
      }
      
    } /* end k loop */

    /* removes all entries with ptI1 or ptI2 == -1 */
    removeInteraction();

  }




  /* remove interaction */
  void removeInteraction(SELM_Lagrangian lagrangianI1_in, int ptI1_in,
                         SELM_Lagrangian lagrangianI2_in, int ptI2_in) {

    int k;
    int ptI1, ptI2;
    SELM_Lagrangian lagrangianI1, lagrangianI2;

    boolean flagFound   = false;
    int     removeIndex = -1;

    /* == make sure ptI1 <= ptI2 */
    if (ptI1_in <= ptI2_in) {
      ptI1         = ptI1_in;
      ptI2         = ptI2_in;
      lagrangianI1 = lagrangianI1_in;
      lagrangianI2 = lagrangianI2_in;
    } else {
      ptI1 = ptI2_in;
      ptI2 = ptI1_in;
      lagrangianI1 = lagrangianI2_in;
      lagrangianI2 = lagrangianI1_in;
    }

    /* == find index of entry to remove */
    for (k = 0; k < numPairs; k++) {

      if ( (pairList_ptI1[k] == ptI1) && (pairList_ptI2[k] == ptI2) &&
           (pairList_lagrangianI1[k] == lagrangianI1) && (pairList_lagrangianI2[k] == lagrangianI2)
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
    
    int             pairList_ptI1_new[];
    int             pairList_ptI2_new[];

    SELM_Lagrangian pairList_lagrangianI1_new[];
    SELM_Lagrangian pairList_lagrangianI2_new[];

    double restLength_new[];
    double stiffnessK_new[];

    /* resize array if a lot of slack */
    if (numPairs < numPairsAlloc / 2) {
      numPairsAlloc = numPairsAlloc / 2;
    }

    /* copy data to smaller array with entry deleted */
    pairList_ptI1_new         = new int[numPairsAlloc];
    pairList_ptI2_new         = new int[numPairsAlloc];

    pairList_lagrangianI1_new = new SELM_Lagrangian[numPairsAlloc];
    pairList_lagrangianI2_new = new SELM_Lagrangian[numPairsAlloc];

    restLength_new            = new double[numPairsAlloc];
    stiffnessK_new            = new double[numPairsAlloc];

    /* copy first part of the array */
    if (removeIndex >= 0) {
      System.arraycopy(pairList_ptI1, 0, pairList_ptI1_new, 0, removeIndex);
      System.arraycopy(pairList_ptI2, 0, pairList_ptI2_new, 0, removeIndex);

      System.arraycopy(pairList_lagrangianI1, 0, pairList_lagrangianI1_new, 0, removeIndex);
      System.arraycopy(pairList_lagrangianI2, 0, pairList_lagrangianI2_new, 0, removeIndex);

      System.arraycopy(restLength, 0, restLength_new, 0, removeIndex);
      System.arraycopy(stiffnessK, 0, stiffnessK_new, 0, removeIndex);
    }

    /* copy second part of the array */
    System.arraycopy(pairList_ptI1, removeIndex + 1, pairList_ptI1_new, removeIndex, numPairs - removeIndex - 1);
    System.arraycopy(pairList_ptI2, removeIndex + 1, pairList_ptI2_new, removeIndex, numPairs - removeIndex - 1);

    System.arraycopy(pairList_lagrangianI1, removeIndex + 1, pairList_lagrangianI1_new, removeIndex, numPairs - removeIndex - 1);
    System.arraycopy(pairList_lagrangianI2, removeIndex + 1, pairList_lagrangianI2_new, removeIndex, numPairs - removeIndex - 1);

    System.arraycopy(restLength, removeIndex + 1, restLength_new, removeIndex, numPairs - removeIndex - 1);
    System.arraycopy(stiffnessK, removeIndex + 1, stiffnessK_new, removeIndex, numPairs - removeIndex - 1);

    pairList_ptI1         = pairList_ptI1_new;
    pairList_ptI2         = pairList_ptI2_new;

    pairList_lagrangianI1 = pairList_lagrangianI1_new;
    pairList_lagrangianI2 = pairList_lagrangianI2_new;

    restLength            = restLength_new;
    stiffnessK            = stiffnessK_new;
    
    numPairs--; /* decrement the pair, since one removed */

  }


  /* removes all entries with ptI == -1 */
  void removeInteraction() {

    int k;    

    int numPairs_new = 0;

    boolean flagFound = false;

    int             pairList_ptI1_new[];
    int             pairList_ptI2_new[];

    SELM_Lagrangian pairList_lagrangianI1_new[];
    SELM_Lagrangian pairList_lagrangianI2_new[];

    double restLength_new[];
    double stiffnessK_new[];

    /* copy data to smaller array with entry deleted */
    pairList_ptI1_new         = new int[numPairsAlloc];
    pairList_ptI2_new         = new int[numPairsAlloc];

    pairList_lagrangianI1_new = new SELM_Lagrangian[numPairsAlloc];
    pairList_lagrangianI2_new = new SELM_Lagrangian[numPairsAlloc];

    restLength_new            = new double[numPairsAlloc];
    stiffnessK_new            = new double[numPairsAlloc];

    /* copy only the entries not having ptI == -1 and lagrangianI == null*/
    for (k = 0; k < numPairs; k++) {

      if ( (pairList_ptI1[k] != -1) && (pairList_ptI2[k] != -1) &&
           (pairList_lagrangianI1[k] != null) && (pairList_lagrangianI2[k] != null) ) {
        pairList_ptI1_new[numPairs_new]         = pairList_ptI1[k];
        pairList_ptI2_new[numPairs_new]         = pairList_ptI2[k];
        pairList_lagrangianI1_new[numPairs_new] = pairList_lagrangianI1[k];
        pairList_lagrangianI2_new[numPairs_new] = pairList_lagrangianI2[k];
        restLength_new[numPairs_new]            = restLength[k];
        stiffnessK_new[numPairs_new]            = stiffnessK[k];
        numPairs_new++;
      }

    } /* end k loop */

    numPairs              = numPairs_new; /* update the number of pairs */
    pairList_ptI1         = pairList_ptI1_new;
    pairList_ptI2         = pairList_ptI2_new;
    pairList_lagrangianI1 = pairList_lagrangianI1_new;
    pairList_lagrangianI2 = pairList_lagrangianI2_new;
    restLength            = restLength_new;
    stiffnessK            = stiffnessK_new;

  } /* removeInteraction */

  public int getNumPairs() {
    return numPairs;
  }
  
  public int[] getPairList_ptI1() {
    return makeSubArray(pairList_ptI1,numPairs);
  }

  public int[] getPairList_ptI2() {
    return makeSubArray(pairList_ptI2,numPairs);
  }

  public SELM_Lagrangian[] getPairList_lagrangianI1() {
    return makeSubArray(pairList_lagrangianI1, numPairs);
  }

  public SELM_Lagrangian[] getPairList_lagrangianI2() {
    return makeSubArray(pairList_lagrangianI2, numPairs);
  }

  public double[] getRestLength() {
    return makeSubArray(restLength,numPairs);
  }

  public double[] getStiffnessK() {
    return makeSubArray(stiffnessK,numPairs);
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

  public void setPairList_lagrangianI1(SELM_Lagrangian[] pairList_lagrangianI1_in) {

    int numPairs_in = pairList_lagrangianI1_in.length;

    if (numPairs_in > numPairsAlloc) {
      resizeLists(numPairs_in);
    }
    
    numPairs = numPairs_in;

    setSubArray(pairList_lagrangianI1_in, pairList_lagrangianI1, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (pairList_lagrangianI1_in[k] != null)
        (pairList_lagrangianI1_in[k]).addDataChangeListener(this);
    }

  }

  public void setPairList_lagrangianI2(SELM_Lagrangian[] pairList_lagrangianI2_in) {

    int numPairs_in = pairList_lagrangianI2_in.length;

    if (numPairs_in > numPairsAlloc) {
      resizeLists(numPairs_in);
    }

    numPairs = numPairs_in;

    setSubArray(pairList_lagrangianI2_in, pairList_lagrangianI2, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (pairList_lagrangianI2_in[k] != null)
        (pairList_lagrangianI2_in[k]).addDataChangeListener(this);
    }
    
  }

  public void setPairData(int numPairs_in,
                          SELM_Lagrangian[] pairList_lagrangianI1_in, int[] pairList_ptI1_in,
                          SELM_Lagrangian[] pairList_lagrangianI2_in, int[] pairList_ptI2_in,
                          double[] stiffnessK_in, double[] restLength_in) {
   
    if (numPairs_in >= numPairsAlloc) {
      resizeLists(2*numPairs_in);
    }
    
    setSubArray(pairList_ptI1_in,         pairList_ptI1,         numPairs_in);
    setSubArray(pairList_ptI2_in,         pairList_ptI2,         numPairs_in);
    setSubArray(pairList_lagrangianI1_in, pairList_lagrangianI1, numPairs_in);
    setSubArray(pairList_lagrangianI2_in, pairList_lagrangianI2, numPairs_in);
    setSubArray(stiffnessK_in,            stiffnessK,            numPairs_in);
    setSubArray(restLength_in,            restLength,            numPairs_in);

    numPairs = numPairs_in;

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (pairList_lagrangianI1_in[k] != null)
        (pairList_lagrangianI1_in[k]).addDataChangeListener(this);

      if (pairList_lagrangianI2_in[k] != null)
        (pairList_lagrangianI2_in[k]).addDataChangeListener(this);
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

  private void resizeLists(int numPairsAlloc_new) {

    int numToCopy = -1;

    numToCopy = java.lang.Math.min(numPairs, numPairsAlloc_new);
    
    if (numPairsAlloc_new <= numPairs) {
      //System.out.println("ERROR: Interaction_PAIRS_HARMONIC.resizeLists()");
      //System.out.println("numPairsAlloc_new <= numPairs");
      /* this is now allowed, since numToCopy is min */
    }
  
    int[]                 pairList_ptI1_new         = new int[numPairsAlloc_new];
    int[]                 pairList_ptI2_new         = new int[numPairsAlloc_new];

    SELM_Lagrangian[]     pairList_lagrangianI1_new = new SELM_Lagrangian[numPairsAlloc_new];
    SELM_Lagrangian[]     pairList_lagrangianI2_new = new SELM_Lagrangian[numPairsAlloc_new];

    double[]  restLength_new    = new double[numPairsAlloc_new];
    double[]  stiffnessK_new    = new double[numPairsAlloc_new];

    System.arraycopy(pairList_ptI1, 0, pairList_ptI1_new, 0, numToCopy);
    System.arraycopy(pairList_ptI2, 0, pairList_ptI2_new, 0, numToCopy);

    System.arraycopy(pairList_lagrangianI1, 0, pairList_lagrangianI1_new, 0, numToCopy);
    System.arraycopy(pairList_lagrangianI2, 0, pairList_lagrangianI2_new, 0, numToCopy);

    System.arraycopy(restLength, 0, restLength_new, 0, numToCopy);
    System.arraycopy(stiffnessK, 0, stiffnessK_new, 0, numToCopy);

    pairList_ptI1         = pairList_ptI1_new;
    pairList_ptI2         = pairList_ptI2_new;

    pairList_lagrangianI1 = pairList_lagrangianI1_new;
    pairList_lagrangianI2 = pairList_lagrangianI2_new;

    restLength            = restLength_new;
    stiffnessK            = stiffnessK_new;

    numPairsAlloc         = numPairsAlloc_new;

  }


  @Override
  public SELM_Interaction_LAMMPS_PAIRS_HARMONIC clone() {
    SELM_Interaction_LAMMPS_PAIRS_HARMONIC interaction_copy = new SELM_Interaction_LAMMPS_PAIRS_HARMONIC();

    interaction_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    interaction_copy.InteractionName                   = this.InteractionName.toString();
    interaction_copy.InteractionTypeStr                = this.InteractionTypeStr.toString();
       
    interaction_copy.numPairs                          = this.numPairs;
    interaction_copy.numPairsAlloc                     = this.numPairsAlloc;
    interaction_copy.pairList_ptI1                     = this.pairList_ptI1.clone();
    interaction_copy.pairList_ptI2                     = this.pairList_ptI2.clone();
    interaction_copy.pairList_lagrangianI1             = this.pairList_lagrangianI1.clone();
    interaction_copy.pairList_lagrangianI2             = this.pairList_lagrangianI2.clone();
    interaction_copy.restLength                        = this.restLength.clone();
    interaction_copy.stiffnessK                        = this.stiffnessK.clone();
    
    interaction_copy.xml_pairList_lagrangianNamesI1    = this.xml_pairList_lagrangianNamesI1;
    interaction_copy.xml_pairList_lagrangianTypesStrI1 = this.xml_pairList_lagrangianTypesStrI1;

    interaction_copy.xml_pairList_lagrangianNamesI2    = this.xml_pairList_lagrangianNamesI2;
    interaction_copy.xml_pairList_lagrangianTypesStrI2 = this.xml_pairList_lagrangianTypesStrI2;

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
    int             N = numPairs;
    int             I1, I2;
    double[]        X1, X2;
    double[]        ptsX1, ptsX2;
    SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangianI1, lagrangianI2;

    X1    = new double[num_dim];
    ptsX1 = new double[N*num_dim];

    X2    = new double[num_dim];
    ptsX2 = new double[N*num_dim];

    for (int k = 0; k < N; k++) {
      
      lagrangianI1 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) pairList_lagrangianI1[k];
      I1           = pairList_ptI1[k];

      lagrangianI2 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) pairList_lagrangianI2[k];
      I2           = pairList_ptI2[k];

      if ((lagrangianI1 != null) && (lagrangianI2 != null)) {

        X1 = lagrangianI1.getPtsX(I1);
        X2 = lagrangianI2.getPtsX(I2);

        for (int d = 0; d < num_dim; d++) {
          ptsX1[k*num_dim + d] = X1[d];
          ptsX2[k*num_dim + d] = X2[d];
        }
        
      }

    } /* end of k loop */

    atz3D_Element_Lines.setLines(ptsX1, ptsX2);

    //atz3D_Index_Lines       = 0;
    list[atz3D_Index_Lines] = atz3D_Element_Lines;

    return list;
  }

  public void handleDataChange(Atz_DataChangeEvent e) {
    
    System.out.println("Handle Data Change");
    System.out.println("getDataChangeType() = " + e.getDataChangeType());

    /* check which type of object generated the signal */
    if (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE.class.isInstance(e.getSource())) {

      SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE source = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) e.getSource();

      if (source.DATA_CHANGE_REMOVE_PT.equals(e.getDataChangeType())) {
        HashMap extras = (HashMap) e.getDataChangeExtraInfo();

        int                                ptsToRemoveIndex = (Integer) extras.get("ptsToRemoveIndex");
        SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangian     = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) extras.get("lagrangian");

        System.out.println("Signal to Remove Points..." + lagrangian.LagrangianName + ":" + ptsToRemoveIndex);
        System.out.println("");

        /* remove all interactions involving this particular point */
        /* also, reindex the pairs to reflect the delection of this point */
        removePtI(lagrangian, ptsToRemoveIndex);
        
      }

    } /* SELM_Lagrangian_LAMMPS_ATOM_angle_style */

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
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_numPairs, numPairs);
      
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_pairList_lagrangianI1);
      SELM_Lagrangian[] listLagrangianI1 = this.getPairList_lagrangianI1();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        if (listLagrangianI1[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI1[k].LagrangianName, listLagrangianI1[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_pairList_lagrangianI1);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_pairList_ptI1, this.getPairList_ptI1());

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_pairList_lagrangianI2);      
      SELM_Lagrangian[] listLagrangianI2 = this.getPairList_lagrangianI2();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        if (listLagrangianI2[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI2[k].LagrangianName, listLagrangianI2[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }      
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_pairList_lagrangianI2);

      Atz_XML_Helper.writeXMLData(fid, tagXML_pairList_ptI2, getPairList_ptI2());

      Atz_XML_Helper.writeXMLData(fid, tagXML_restLength,  getRestLength());
      Atz_XML_Helper.writeXMLData(fid, tagXML_stiffnessK,  getStiffnessK());
      
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
      //sp.parse("test1.SELM_Interaction_LAMMPS_ATOM_angle_style", new Atz_XML_DataHandlerWrapper(this));

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
    } else if (qName.equals(tagXML_pairList_lagrangianI1)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
    } else if (qName.equals(tagXML_pairList_lagrangianI2)) {
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
      
    } else if (qName.equals(tagXML_numPairs)) {
      int numPairs_new = Integer.parseInt(xmlAttributes.getValue("value"));
      resizeLists(numPairs_new);
      numPairs = numPairs_new;
    } else if (qName.equals(this.tagXML_pairList_lagrangianI1)) {

      /* only process data from this tag if it is non-empty */
      if (numPairs != 0) {
        Atz_XML_Helper_SAX_ListDataHandler handler
          = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
        HashMap tagDataLists = handler.getTagDataLists();
        Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
        ArrayList lagrangianRefList       = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
        xml_pairList_lagrangianNamesI1    = new String[lagrangianRefList.size()];
        xml_pairList_lagrangianTypesStrI1 = new String[lagrangianRefList.size()];
        for (int k = 0; k < lagrangianRefList.size(); k++) {
          lagrangianRef                        = (Atz_XML_Helper_Handler_LagrangianRef)lagrangianRefList.get(k);
          xml_pairList_lagrangianNamesI1[k]    = lagrangianRef.LagrangianName;
          xml_pairList_lagrangianTypesStrI1[k] = lagrangianRef.LagrangianTypeStr;
        }
      } else {
        xml_pairList_lagrangianNamesI1    = new String[0];
        xml_pairList_lagrangianTypesStrI1 = new String[0];
      }

    } else if (qName.equals(this.tagXML_pairList_lagrangianI2)) {

      /* only process data from this tag if it is non-empty */
      if (numPairs != 0) {
        Atz_XML_Helper_SAX_ListDataHandler handler = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
        HashMap tagDataLists = handler.getTagDataLists();
        Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
        ArrayList lagrangianRefList = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
        xml_pairList_lagrangianNamesI2    = new String[lagrangianRefList.size()];
        xml_pairList_lagrangianTypesStrI2 = new String[lagrangianRefList.size()];
        for (int k = 0; k < lagrangianRefList.size(); k++) {
          lagrangianRef = (Atz_XML_Helper_Handler_LagrangianRef) lagrangianRefList.get(k);
          xml_pairList_lagrangianNamesI2[k] = lagrangianRef.LagrangianName;
          xml_pairList_lagrangianTypesStrI2[k] = lagrangianRef.LagrangianTypeStr;
        }

      } else {
        xml_pairList_lagrangianNamesI2    = new String[0];
        xml_pairList_lagrangianTypesStrI2 = new String[0];
      }

    } else if (qName.equals(tagXML_pairList_ptI1)) {
      pairList_ptI1 = Atz_XML_Helper.parseIntArrayFromString(xmlString);      
    } else if (qName.equals(tagXML_pairList_ptI2)) {
      pairList_ptI2 = Atz_XML_Helper.parseIntArrayFromString(xmlString);
    } else if (qName.equals(tagXML_restLength)) {
      restLength    = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    } else if (qName.equals(tagXML_stiffnessK)) {
      stiffnessK    = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);
    } else if (qName.equals(tagXML_flagVisible)) {
      flagVisible = Boolean.parseBoolean(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_plotColor)) {
      plotColor = ((Atz_XML_Helper_Handler_Color)sourceHandler.getLastUsedDataHandler()).color;
    }

  }

  @Override
  public void setupLagrangianFromList(SELM_Lagrangian[] lagrangianList) {
    /* assumes XML data was recently read and should be used */
    setupLagrangianListsFromNames(xml_pairList_lagrangianNamesI1, 
                                  xml_pairList_lagrangianNamesI2,
                                  lagrangianList);
  }

  public void setupLagrangianListsFromNames(String[] lagrangianNamesI1, String[] lagrangianNamesI2, SELM_Lagrangian[] lagrangianList) {

    int N = lagrangianNamesI1.length;

    SELM_Lagrangian[] listLagrangianI1 = new SELM_Lagrangian[N];
    SELM_Lagrangian[] listLagrangianI2 = new SELM_Lagrangian[N];

    for (int k = 0; k < N; k++) {
      /* find appropriate lagrangian */
      listLagrangianI1[k] = null;
      listLagrangianI2[k] = null;
      for (int j = 0; j < lagrangianList.length; j++) {
        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI1[k])) {
          listLagrangianI1[k] = lagrangianList[j];
        }
        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI2[k])) {
          listLagrangianI2[k] = lagrangianList[j];
        }
      } /* end of j loop */
    } /* end of k loop */

    /* setup the pairs */
    setPairList_lagrangianI1(listLagrangianI1);
    setPairList_lagrangianI2(listLagrangianI2);

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */




}
