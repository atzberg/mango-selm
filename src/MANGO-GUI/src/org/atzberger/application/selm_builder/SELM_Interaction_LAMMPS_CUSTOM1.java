package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_Helper_SAX_ListDataHandler;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
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
import org.atzberger.mango.atz3d.Atz3D_Element_Points;
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
public class SELM_Interaction_LAMMPS_CUSTOM1 extends SELM_Interaction implements SELM_InteractionInterface_LAMMPS, Atz_DataChangeListener, Atz_XML_SAX_DataHandlerInterface {
    
  private int             numMembers        = 0;
  private int             numMembersAlloc   = 0;

  private SELM_Lagrangian memberList_lagrangianI1[] = null;
  private int             memberList_ptI1[]         = null;
  
  private ArrayList       parameterDataList = new ArrayList();
  
  private boolean         flagVisible = true;
  private Color           plotColor   = Color.blue;
  
  /* index of element types within the representation of this type */
  public final int    atz3D_Index_Lines = 0;

  Atz3D_Element_Points atz3D_Element_Points = new Atz3D_Element_Points();

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public String[]  xml_memberList_lagrangianNamesI1    = null;
  public String[]  xml_memberList_lagrangianTypesStrI1 = null;
  
  public static String tagXML_numMembers               = "numMembers";

  public static String tagXML_memberList_lagrangianI1  = "memberList_lagrangianI1";

  public static String tagXML_memberList_ptI1          = "memberList_ptI1";
  
  public static String tagXML_parameterDataList        = "parameterDataList";

  public static String tagXML_flagVisible              = "flagVisible";
  public static String tagXML_plotColor                = "plotColor";
 
  public SELM_Interaction_LAMMPS_CUSTOM1() {

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    InteractionName    = "Your Name Here";
    InteractionTypeStr = thisClassName.replace(superClassName + "_", "");

    numMembersAlloc = 1;
    numMembers      = 0;

    memberList_ptI1 = new int[numMembersAlloc];

    memberList_lagrangianI1 = new SELM_Lagrangian[numMembersAlloc];
    
    flagVisible = true;
    plotColor   = Color.blue;
    
  }


  public void setPlotColor(Color color_in) {
    plotColor = color_in;
  }

  public Color getPlotColor() {
    return plotColor;
  }
  
  public void setParameterDataList(ArrayList parameterDataList_in) {
    parameterDataList = parameterDataList_in;
  }

  public ArrayList getParameterDataList() {
    return parameterDataList;
  }

  public void setVisible(boolean flagVisible_in) {
    flagVisible = flagVisible_in;
  }

  public boolean isVisible() {
    return flagVisible;
  }
 
  /* add a pair to the list */
  void addMember(SELM_Lagrangian lagrangianI1_in, int ptI1_in) {

    boolean flagNewPair = true;
    
    int             i,j,k;
    int             ptI1;
    SELM_Lagrangian lagrangianI1;

    int    memberList_ptI1_new[];

    int    memberList_lagrangianI1_new[];

    /* == check to make sure does not already exist */
    //System.out.println("numPairs = " + numPairs);
    //System.out.println("memberList_ptI1.length = " + memberList_ptI1.length);

    ptI1 = ptI1_in;
    lagrangianI1 = lagrangianI1_in;

    for (k = 0; k < numMembers; k++) {

      if ( (memberList_ptI1[k] == ptI1) &&
           (memberList_lagrangianI1[k] == lagrangianI1)
         ) {
        flagNewPair = false;
      }
    } /* end k loop */

    /* == add this entry to the list */
    if (flagNewPair) {

      if (numMembers < numMembersAlloc) {

        memberList_ptI1[numMembers]         = ptI1;
        memberList_lagrangianI1[numMembers] = lagrangianI1;
        numMembers++;

      } else { /* == resize arrays is necessary, before add */
        
        int numPairsAlloc_new = 2 * numMembersAlloc;
        if (numPairsAlloc_new <= numMembers) { /* if still smaller */
          numPairsAlloc_new = numMembers + 10;
        }

        /* resize the lists to the specified size */
        resizeLists(numPairsAlloc_new);

        /* try to add again */
        addMember(lagrangianI1, ptI1);

      } /* end else */

      /* add a data change listener for this interaction */
      if (lagrangianI1 != null)
        lagrangianI1.addDataChangeListener(this);

    } /* end flagNewPair */

  } /* add interaction */

  /* remove pair if present in the list */
  void removeMember(SELM_Lagrangian lagrangianI1, int ptI1) {

    /* == relabel indices and remove entries for given ptI */
    for (int k = 0; k < numMembers; k++) {
      if ((memberList_ptI1[k] == ptI1) &&
          (memberList_lagrangianI1[k] == lagrangianI1)
          ) {
        memberList_ptI1[k]         = -1; /* mark for removal */  
        memberList_lagrangianI1[k] = null;
      }
    } /* end k loop */

    /* removes all entries with ptI1 or ptI2 == -1 */
    removeInteraction();
    
  }


  /* remove and re-label all of the pairs */
  void removePtI(SELM_Lagrangian lagrangianI, int ptI) {
    
    int     k;
    
    /* == relabel indices and remove entries for given ptI */
    for (k = 0; k < numMembers; k++) {

      if ( ((memberList_ptI1[k] == ptI)) &&
           ((memberList_lagrangianI1[k] == lagrangianI) ) ) {
        memberList_ptI1[k]         = -1; /* mark for removal */        
        memberList_lagrangianI1[k] = null;        
      } else { /* otherwise reduce index by one for all indices larger than ptI */
        if ((memberList_ptI1[k] > ptI) && (memberList_lagrangianI1[k] == lagrangianI)) {
          memberList_ptI1[k]--;
        }
      }
      
    } /* end k loop */

    /* removes all entries with ptI1 or ptI2 == -1 */
    removeInteraction();

  }




  /* remove interaction */
  void removeInteraction(SELM_Lagrangian lagrangianI1, int ptI1) {

    int k;

    boolean flagFound   = false;
    int     removeIndex = -1;

    /* == find index of entry to remove */
    for (k = 0; k < numMembers; k++) {

      if ( (memberList_ptI1[k] == ptI1) &&
           (memberList_lagrangianI1[k] == lagrangianI1)
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
    
    int             memberList_ptI1_new[];

    SELM_Lagrangian memberList_lagrangianI1_new[];

    /* resize array if a lot of slack */
    if (numMembers < numMembersAlloc / 2) {
      numMembersAlloc = numMembersAlloc / 2;
    }

    /* copy data to smaller array with entry deleted */
    memberList_ptI1_new         = new int[numMembersAlloc];

    memberList_lagrangianI1_new = new SELM_Lagrangian[numMembersAlloc];

    /* copy first part of the array */
    if (removeIndex >= 0) {
      System.arraycopy(memberList_ptI1, 0, memberList_ptI1_new, 0, removeIndex);
      System.arraycopy(memberList_lagrangianI1, 0, memberList_lagrangianI1_new, 0, removeIndex);      
    }

    /* copy second part of the array */
    System.arraycopy(memberList_ptI1, removeIndex + 1, memberList_ptI1_new, removeIndex, numMembers - removeIndex - 1);
    System.arraycopy(memberList_lagrangianI1, removeIndex + 1, memberList_lagrangianI1_new, removeIndex, numMembers - removeIndex - 1);
    
    memberList_ptI1         = memberList_ptI1_new;
    memberList_lagrangianI1 = memberList_lagrangianI1_new;
    
    numMembers--; /* decrement the pair, since one removed */

  }


  /* removes all entries with ptI == -1 */
  void removeInteraction() {

    int k;    

    int numPairs_new = 0;

    boolean flagFound = false;

    int             memberList_ptI1_new[];
    SELM_Lagrangian memberList_lagrangianI1_new[];

    /* copy data to smaller array with entry deleted */
    memberList_ptI1_new         = new int[numMembersAlloc];
    memberList_lagrangianI1_new = new SELM_Lagrangian[numMembersAlloc];

    /* copy only the entries not having ptI == -1 and lagrangianI == null*/
    for (k = 0; k < numMembers; k++) {

      if ( (memberList_ptI1[k] != -1)  &&
           (memberList_lagrangianI1[k] != null)  ) {
        memberList_ptI1_new[numPairs_new]         = memberList_ptI1[k];
        memberList_lagrangianI1_new[numPairs_new] = memberList_lagrangianI1[k];
        
        numPairs_new++;
      }

    } /* end k loop */

    numMembers              = numPairs_new; /* update the number of pairs */
    memberList_ptI1         = memberList_ptI1_new;
    memberList_lagrangianI1 = memberList_lagrangianI1_new;
    
  } /* removeInteraction */

  public int getNumPairs() {
    return numMembers;
  }
  
  public int[] getMemberList_ptI1() {
    return makeSubArray(memberList_ptI1,numMembers);
  }

  public SELM_Lagrangian[] getMemberList_lagrangianI1() {
    return makeSubArray(memberList_lagrangianI1, numMembers);
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

  public void setMemberList_lagrangianI1(SELM_Lagrangian[] memberList_lagrangianI1_in) {

    int numPairs_in = memberList_lagrangianI1_in.length;

    if (numPairs_in > numMembersAlloc) {
      resizeLists(numPairs_in);
    }
    
    numMembers = numPairs_in;

    /* @@ WARNING not sure how to fix edits corrupted accidentally */
    setSubArray(memberList_lagrangianI1_in, memberList_lagrangianI1, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (memberList_lagrangianI1_in[k] != null)
        (memberList_lagrangianI1_in[k]).addDataChangeListener(this);
    }

  }

  public void setMemberData(int numPairs_in,
                            SELM_Lagrangian[] memberList_lagrangianI1_in, int[] memberList_ptI1_in) {
   
    if (numPairs_in >= numMembersAlloc) {
      resizeLists(2*numPairs_in);
    }
    
    setSubArray(memberList_ptI1_in,         memberList_ptI1,         numPairs_in);
    setSubArray(memberList_lagrangianI1_in, memberList_lagrangianI1, numPairs_in);
    
    numMembers = numPairs_in;

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (memberList_lagrangianI1_in[k] != null)
        (memberList_lagrangianI1_in[k]).addDataChangeListener(this);
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

    numToCopy = java.lang.Math.min(numMembers, numPairsAlloc_new);
    
    if (numPairsAlloc_new <= numMembers) {
      //System.out.println("ERROR: Interaction_PAIRS_HARMONIC.resizeLists()");
      //System.out.println("numPairsAlloc_new <= numPairs");
      /* this is now allowed, since numToCopy is min */
    }
  
    int[]                 memberList_ptI1_new         = new int[numPairsAlloc_new];
    SELM_Lagrangian[]     memberList_lagrangianI1_new = new SELM_Lagrangian[numPairsAlloc_new];

    System.arraycopy(memberList_ptI1, 0, memberList_ptI1_new, 0, numToCopy);
    System.arraycopy(memberList_lagrangianI1, 0, memberList_lagrangianI1_new, 0, numToCopy);
   
    memberList_ptI1         = memberList_ptI1_new;

    memberList_lagrangianI1 = memberList_lagrangianI1_new;

    numMembersAlloc         = numPairsAlloc_new;

  }


  @Override
  public SELM_Interaction_LAMMPS_CUSTOM1 clone() {
    SELM_Interaction_LAMMPS_CUSTOM1 interaction_copy = new SELM_Interaction_LAMMPS_CUSTOM1();

    interaction_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    interaction_copy.InteractionName                   = this.InteractionName.toString();
    interaction_copy.InteractionTypeStr                = this.InteractionTypeStr.toString();
       
    interaction_copy.numMembers                          = this.numMembers;
    interaction_copy.numMembersAlloc                     = this.numMembersAlloc;
    interaction_copy.memberList_ptI1                     = this.memberList_ptI1.clone();
    interaction_copy.memberList_lagrangianI1             = this.memberList_lagrangianI1.clone();
   
    interaction_copy.xml_memberList_lagrangianNamesI1    = this.xml_memberList_lagrangianNamesI1;
    interaction_copy.xml_memberList_lagrangianTypesStrI1 = this.xml_memberList_lagrangianTypesStrI1;

    interaction_copy.parameterDataList                   = (ArrayList) this.parameterDataList.clone();
            
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
    int num_dim = 3;
    Atz3D_Element[] list = new Atz3D_Element[1];

    atz3D_Element_Points.setPlotColor(plotColor);

    /* loop over all of the interactions and construct
     * lines for each one.
     */
    int             N = numMembers;
    int             I1;
    double[]        X1;
    double[]        ptsX1;
    SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangianI1;

    X1    = new double[num_dim];
    ptsX1 = new double[N*num_dim];

    for (int k = 0; k < N; k++) {
      
      lagrangianI1 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) memberList_lagrangianI1[k];
      I1           = memberList_ptI1[k];

      if ((lagrangianI1 != null)) {

        X1 = lagrangianI1.getPtsX(I1);

        for (int d = 0; d < num_dim; d++) {
          ptsX1[k*num_dim + d] = X1[d];
        }
        
      }

    } /* end of k loop */

    atz3D_Element_Points.setPoints(ptsX1);
    atz3D_Element_Points.setPlotSize(20);

    //atz3D_Index_Lines       = 0;
    list[atz3D_Index_Lines] = atz3D_Element_Points;

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

    } /* SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE */

  }


  /* ====================================================== */
  /* ================= LAMMPS Interface =================== */
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
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_numMembers, numMembers);
      
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_memberList_lagrangianI1);
      SELM_Lagrangian[] listLagrangianI1 = this.getMemberList_lagrangianI1();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        if (listLagrangianI1[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI1[k].LagrangianName, listLagrangianI1[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_memberList_lagrangianI1);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_memberList_ptI1, this.getMemberList_ptI1());
      
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_parameterDataList);

      parameterData_XML_Handler paramData_XML = new parameterData_XML_Handler();

      for (int k = 0; k < parameterDataList.size(); k++) {
        parameterData paramData = (parameterData)parameterDataList.get(k);
        paramData_XML.setParamData(paramData);
        paramData_XML.exportToXML(fid);
      }

      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_parameterDataList);
                      
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
    } else if (qName.equals(tagXML_memberList_lagrangianI1)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
    } else if (qName.equals(tagXML_plotColor)) {
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
    } else if (qName.equals(this.tagXML_parameterDataList)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new parameterData_XML_Handler()));
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
      
    } else if (qName.equals(tagXML_numMembers)) {
      int numPairs_new = Integer.parseInt(xmlAttributes.getValue("value"));
      resizeLists(numPairs_new);
      numMembers = numPairs_new;
    } else if (qName.equals(this.tagXML_memberList_lagrangianI1)) {
      Atz_XML_Helper_SAX_ListDataHandler handler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
      HashMap tagDataLists = handler.getTagDataLists();      
      Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
      ArrayList lagrangianRefList       = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
      xml_memberList_lagrangianNamesI1    = new String[lagrangianRefList.size()];
      xml_memberList_lagrangianTypesStrI1 = new String[lagrangianRefList.size()];
      for (int k = 0; k < lagrangianRefList.size(); k++) {
        lagrangianRef                        = (Atz_XML_Helper_Handler_LagrangianRef)lagrangianRefList.get(k);
        xml_memberList_lagrangianNamesI1[k]    = lagrangianRef.LagrangianName;
        xml_memberList_lagrangianTypesStrI1[k] = lagrangianRef.LagrangianTypeStr;
      }
    } else if (qName.equals(tagXML_memberList_ptI1)) {
      memberList_ptI1 = Atz_XML_Helper.parseIntArrayFromString(xmlString);    
    } else if (qName.equals(tagXML_flagVisible)) {
      flagVisible = Boolean.parseBoolean(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_plotColor)) {
      plotColor = ((Atz_XML_Helper_Handler_Color)sourceHandler.getLastUsedDataHandler()).color;
    } else if (qName.equals(this.tagXML_parameterDataList)) {

      Atz_XML_Helper_SAX_ListDataHandler handler
        = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();

      HashMap tagDataLists = handler.getTagDataLists();
      parameterData_XML_Handler paramXMLHandler;
      ArrayList dataList       = (ArrayList) tagDataLists.get(parameterData_XML_Handler.tagXML_parameterData);

      this.setParameterDataList(dataList);

    }
     
  }

  @Override
  public void setupLagrangianFromList(SELM_Lagrangian[] lagrangianList) {
    /* assumes XML data was recently read and should be used */
    setupLagrangianListsFromNames(xml_memberList_lagrangianNamesI1,                                  
                                  lagrangianList);
  }

  public void setupLagrangianListsFromNames(String[] lagrangianNamesI1, SELM_Lagrangian[] lagrangianList) {

    int N = lagrangianNamesI1.length;

    SELM_Lagrangian[] listLagrangianI1 = new SELM_Lagrangian[N];

    for (int k = 0; k < N; k++) {
      /* find appropriate lagrangian */
      listLagrangianI1[k] = null;
      for (int j = 0; j < lagrangianList.length; j++) {
        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI1[k])) {
          listLagrangianI1[k] = lagrangianList[j];
        }
      } /* end of j loop */
    } /* end of k loop */

    /* setup the pairs */
    setMemberList_lagrangianI1(listLagrangianI1);

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */



  /* ====================================================== */
  /* ==================== Internal Class ================== */
  public class parameterData {
    
    String nameStr;
    String typeStr;
    Object data;

    public static final String TYPE_STRING = "STRING";
    public static final String TYPE_NULL   = "NULL";

    public parameterData() {
      nameStr = "";
      typeStr = "";
      data    = null;
    }
    
    public parameterData(String nameStr_in, String typeStr_in, Object data_in) {
      nameStr = nameStr_in;
      typeStr = typeStr_in;
      data    = data_in;
    }
    
    public Object clone() {

      parameterData param_copy = new parameterData();

      param_copy.nameStr       = this.getNameStr();
      param_copy.typeStr       = this.getTypeStr();

      if (typeStr.equals(this.TYPE_STRING)) {
        String str             = (String)this.getData();
        if (str != null) {
          param_copy.data        = str.toString();
        }
      } else {
        param_copy.data = null;  /* Type was not recognized */
      }

      return param_copy;

    }

    public String getNameStr() {
      return nameStr;
    }

    public void  setNameStr(String nameStr_in) {
      nameStr = nameStr_in;
    }

    public String getTypeStr() {
      return typeStr;
    }

    public void setTypeStr(String typeStr_in) {
      typeStr = typeStr_in;
    }

    public Object getData() {
      return data;
    }

    public void  setData(Object data_in) {
      data = data_in;
    }

  }


  public class parameterData_XML_Handler extends parameterData implements Atz_XML_SAX_DataHandlerInterface {

    public static final  String tagXML_parameterData = "parameterData";
    public static final  String tagXML_nameStr       = "nameStr";
    public static final  String tagXML_typeStr       = "typeStr";
    public static final  String tagXML_data          = "data";

    public parameterData_XML_Handler() {
      super();
    }

    public void setParamData(parameterData paramData) {      
      setNameStr(paramData.getNameStr());
      setTypeStr(paramData.getTypeStr());
      setData(paramData.getData());
    }

    /* ====================================================== */
    /* ==================== XML codes ======================= */    
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
        Atz_XML_Helper.writeXMLStartTag(fid, tagXML_parameterData);

        Atz_XML_Helper.writeXMLData(fid, tagXML_nameStr, getNameStr());
        Atz_XML_Helper.writeXMLData(fid, tagXML_typeStr, getTypeStr());
        Atz_XML_Helper.writeXMLData(fid, tagXML_data, getData());
        
        Atz_XML_Helper.writeXMLEndTag(fid, tagXML_parameterData);

      } catch (Exception e) {//Catch exception if any
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
      }

    }
    
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
      xmlString = "";
      xmlAttributes = attributes;

      if (qName.equals(tagXML_parameterData)) {
        /* nothing special to do */
      } 

    }

    @Override
    public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
      xmlString = xmlString + new String(ch, start, length);
    }

    @Override
    public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      if (qName.equals(tagXML_parameterData)) {
        /* check all entries set */
      } else if (qName.equals(tagXML_nameStr)) {
        this.setNameStr(xmlAttributes.getValue("value"));
      } else if (qName.equals(tagXML_typeStr)) {
        this.setTypeStr(xmlAttributes.getValue("value"));
      } else if (qName.equals(tagXML_data)) {
        if (getTypeStr().equals(TYPE_STRING)) {
          this.setData((Object)xmlAttributes.getValue("value"));  /* assumes data String type */
        } else {
          System.out.println("WARNING: SELM_Interaction_LAMMPS_CUSTOM1");
          System.out.println("  Parameter data type not recognized.");
          System.out.println("  typeStr = " + this.getTypeStr());
        }
      }

    }

    @Override
    public Object XML_getData() {
      return this.clone(); /* return a copy of this object, important to clone for case of lists */
    }

    /* ==================== XML codes ======================= */
    /* ====================================================== */
    
  }


}
