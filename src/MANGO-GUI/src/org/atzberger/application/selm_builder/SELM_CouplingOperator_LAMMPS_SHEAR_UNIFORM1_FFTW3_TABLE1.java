package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.atzberger.xml.Atz_XML_Helper_SAX_ListDataHandler;
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
public class SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 extends SELM_CouplingOperator
        implements Atz_XML_SAX_DataHandlerInterface {
    
  private operatorDataType operatorData = new operatorDataType_NULL(this); /* specific data for op type */
  private boolean flagVisible = true;

  /* XML */
  public String tagXML_operatorTypeStr                  = "operatorTypeStr";
  public String tagXML_operatorData                     = "operatorData";

  public String tagXML_numCoupleList        = "numCoupleList";

  public String tagXML_lagrangianList       = "lagrangianList";
  public String tagXML_eulerianList         = "eulerianList";

  protected String[] xml_lagrangianNames    = null;
  protected String[] xml_lagrangianTypesStr = null;

  protected String[] xml_eulerianNames      = null;
  protected String[] xml_eulerianTypesStr   = null;

  protected static final int    OP_TYPE_NULL            = 0;
  protected static final String OP_TYPE_STR_NULL        = "NULL";

  protected static final int    OP_TYPE_T_KERNEL_1      = 1;
  protected static final String OP_TYPE_STR_T_KERNEL_1  = "T_KERNEL_1";

  protected static final int    OP_TYPE_T_FAXEN_1       = 2;
  protected static final String OP_TYPE_STR_T_FAXEN_1   = "T_FAXEN_1";

  protected static final int    OP_TYPE_TR_FAXEN_1      = 3;
  protected static final String OP_TYPE_STR_TR_FAXEN_1  = "TR_FAXEN_1";
 
  public   String tagXML_flagVisible                    = "flagVisible";

  ArrayList<SELM_Lagrangian> lagrangianList = new ArrayList();
  ArrayList<SELM_Eulerian>   eulerianList   = new ArrayList();
  
  SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1() {
    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    CouplingOpName    = "Your Name Here";
    CouplingOpTypeStr = thisClassName.replace(superClassName + "_", "");
  }

  @Override
  public Object clone() {
    SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 CouplingOp_copy = new SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1();

    CouplingOp_copy.CouplingOpName      = this.CouplingOpName.toString();
    CouplingOp_copy.CouplingOpTypeStr   = this.CouplingOpTypeStr.toString();

    CouplingOp_copy.lagrangianList      = (ArrayList<SELM_Lagrangian>)this.lagrangianList.clone();
    CouplingOp_copy.eulerianList        = (ArrayList<SELM_Eulerian>)this.eulerianList.clone();
        
    CouplingOp_copy.operatorData        = (operatorDataType) this.operatorData.clone();
       
    CouplingOp_copy.flagVisible         = this.flagVisible;

    return (Object) CouplingOp_copy;
  }

  public SELM_Eulerian[] getEulerianList() {
    Object[] list = eulerianList.toArray();
    int N = list.length;
    SELM_Eulerian[] listEulerian;

    listEulerian = new SELM_Eulerian[N];
    for (int k = 0; k < list.length; k++) {
      listEulerian[k] = (SELM_Eulerian)list[k];
    }

    return listEulerian;
  }

  public void setEulerianList(SELM_Eulerian[] listEulerian) {
    eulerianList.clear();
    for (int k = 0; k < listEulerian.length; k++) {
      eulerianList.add(listEulerian[k]);
    }
  }

  public SELM_Lagrangian[] getLagrangianList() {
    Object[] list = lagrangianList.toArray();
    int N = list.length;
    SELM_Lagrangian[] listLagrangian;

    listLagrangian = new SELM_Lagrangian[N];
    for (int k = 0; k < list.length; k++) {
      listLagrangian[k] = (SELM_Lagrangian)list[k];
    }

    return listLagrangian;
  }

  public void setLagrangianList(SELM_Lagrangian[] listLagrangian) {
    lagrangianList.clear();
    for (int k = 0; k < listLagrangian.length; k++) {
      lagrangianList.add(listLagrangian[k]);
    }
  }

  public boolean isVisible() {
    return flagVisible;
  }

  public void setVisible(boolean flagVisible_in) {
    flagVisible = flagVisible_in;
  }

  public void setOperatorData(operatorDataType operatorData_in) {
    operatorData = operatorData_in;
  }

  public operatorDataType getOperatorData() {
    return operatorData;
  }

  public String getOperatorTypeStr() {
    return operatorData.typeStr;
  }

  public int getOperatorType() {
    return operatorData.type;
  }

  @Override
  public String toString() {
    String str = "";
    str += "CouplingOpName    = " + CouplingOpName + "\n";
    str += "CouplingOpTypeStr = " + CouplingOpTypeStr + "\n";
    //str += "operatorTypeStr   = " + getOperatorTypeStr() + "\n";
    str += "operatorData      = " + operatorData.toString() + "\n";
    return str;
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
      //typeStr = typeStr.replace("TableModel_CouplingOperator_", "");

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_SELM_CouplingOP);

      Atz_XML_Helper.writeXMLData(fid, tagXML_CouplingOpName, this.CouplingOpName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_CouplingOpTypeStr, this.CouplingOpTypeStr);

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_operatorData, "value=\"" + operatorData.typeStr + "\"");
      
      operatorData.setCouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(this); /* ensure the operatorData reference proper coupling Op */
      operatorData.exportToXML(fid);
      
      Atz_XML_Helper.writeXMLEndTag(fid,  tagXML_operatorData);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_flagVisible, isVisible());

      Atz_XML_Helper.writeXMLData(fid, tagXML_numCoupleList, lagrangianList.size());

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_lagrangianList);

        for (int k = 0; k < lagrangianList.size(); k++) {
          SELM_Lagrangian lagrangian = lagrangianList.get(k);
          if (lagrangian != null) {
            Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
               lagrangian.LagrangianName, lagrangian.LagrangianTypeStr);
          } else {
            Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
          }
        }

        Atz_XML_Helper.writeXMLEndTag(fid, tagXML_lagrangianList);

        Atz_XML_Helper.writeXMLStartTag(fid, tagXML_eulerianList);

        for (int k = 0; k < eulerianList.size(); k++) {
          SELM_Eulerian eulerian = eulerianList.get(k);
          if (eulerian != null) {
            Atz_XML_Helper_Handler_EulerianRef.exportToXML(fid,
               eulerian.EulerianName, eulerian.EulerianTypeStr);
          } else {
            Atz_XML_Helper_Handler_EulerianRef.exportToXML(fid,
            "NULL", "NULL");
          }
        }

        Atz_XML_Helper.writeXMLEndTag(fid, tagXML_eulerianList);
            
      /* end tag */
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_SELM_CouplingOP);

      /* set he modification flag to false at the end of export */
      flag_Gen_LAMMPS_XML_Files = false;

    } catch (Exception e) {//Catch exception if any
      flag_Gen_LAMMPS_XML_Files = false;
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

    if (qName.equals(tagXML_operatorData)) {

      String typeStr = (String) xmlAttributes.getValue("value");
            
      if (typeStr.equals(OP_TYPE_STR_T_KERNEL_1)) {
        operatorData = new operatorDataType_T_KERNEL_1(this);
        sourceHandler.parseCurrentScopeWithDataHandler(operatorData);
      } else if (typeStr.equals(OP_TYPE_STR_T_FAXEN_1)) {
        operatorData = new operatorDataType_T_FAXEN_1(this);
        sourceHandler.parseCurrentScopeWithDataHandler(operatorData);
      } else if (typeStr.equals(OP_TYPE_STR_TR_FAXEN_1)) {
        operatorData = new operatorDataType_TR_FAXEN_1(this);
        sourceHandler.parseCurrentScopeWithDataHandler(operatorData);
      } else {
        /* treat the data type as default "NULL" type */
        operatorData = new operatorDataType_NULL(this);
        sourceHandler.parseCurrentScopeWithDataHandler(operatorData);
      }
      
    } else if (qName.equals(this.tagXML_lagrangianList)) {
      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef());
      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);
    } else if (qName.equals(this.tagXML_eulerianList)) {
      /* use list constructor to collect repeated tags of each distinct type */
      Atz_XML_Helper_SAX_ListDataHandler listDataHandler = new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_EulerianRef());
      sourceHandler.parseCurrentScopeWithDataHandler(listDataHandler);
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
    } else if (qName.equals(tagXML_operatorData)) {
      //setOperatorTypeStr((String)xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_flagVisible)) {
      setVisible(Boolean.parseBoolean(xmlAttributes.getValue("value")));    
    } else if (qName.equals(this.tagXML_lagrangianList)) {

        /* only process data from this tag if it is non-empty */
        Atz_XML_Helper_SAX_ListDataHandler handler = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
        HashMap tagDataLists = handler.getTagDataLists();
        Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
        ArrayList lagrangianRefList = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
        if (lagrangianRefList != null) { /* indicates some members found */
          xml_lagrangianNames    = new String[lagrangianRefList.size()];
          xml_lagrangianTypesStr = new String[lagrangianRefList.size()];
          for (int k = 0; k < lagrangianRefList.size(); k++) {
            lagrangianRef = (Atz_XML_Helper_Handler_LagrangianRef) lagrangianRefList.get(k);
            xml_lagrangianNames[k]    = lagrangianRef.LagrangianName;
            xml_lagrangianTypesStr[k] = lagrangianRef.LagrangianTypeStr;
          }
        } else {
          xml_lagrangianNames    = new String[0];
          xml_lagrangianTypesStr = new String[0];
        }

        /* -- Get the global application data to resolve the data types */
        application_SharedData applSharedData = Atz_Application_Data_Communication.getApplSharedData();

        SELM_Lagrangian[] masterLagrangianList = applSharedData.jTable_MainData.getLagrangianList();

        /* loop over the lagrangian list and resolve references to the master list */
        /* (if no reference in the master list, then skip the reference, issue warning) */
        lagrangianList.clear();
        for (int I = 0; I < xml_lagrangianNames.length; I++) {
          String lagrangianName    = xml_lagrangianNames[I];
          String lagrangianTypeStr = xml_lagrangianTypesStr[I];
          boolean flagFound = false;
          for (int k = 0; k < masterLagrangianList.length; k++) {
            if ( (masterLagrangianList[k].LagrangianName.equals(lagrangianName)) &&
                 (masterLagrangianList[k].LagrangianTypeStr.equals(lagrangianTypeStr))  ) {
              lagrangianList.add(masterLagrangianList[k]);              
              flagFound = true;
            } /* end comparison check */

          } /* end of k loop */

          if (flagFound == false) {
            System.out.println(this.getClass().getSimpleName());
            System.out.println("WARNING: Could not resolve the lagrangian list reference to known lagrangian DOF.");
            System.out.println("  LagrangianName    = " + lagrangianName);
            System.out.println("  LagrangianTypeStr = " + lagrangianTypeStr);
            /* issue warning message */
          }
          
        } /* end of I loop */

      } else if (qName.equals(this.tagXML_eulerianList)) {

        /* only process data from this tag if it is non-empty */
        Atz_XML_Helper_SAX_ListDataHandler handler = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
        HashMap tagDataLists = handler.getTagDataLists();
        Atz_XML_Helper_Handler_EulerianRef eulerianRef;
        ArrayList eulerianRefList = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_EulerianRef.tagXML_EulerianRef);
        if (eulerianRefList != null) { /* indicates some members found */
          xml_eulerianNames    = new String[eulerianRefList.size()];
          xml_eulerianTypesStr = new String[eulerianRefList.size()];
          for (int k = 0; k < eulerianRefList.size(); k++) {
            eulerianRef = (Atz_XML_Helper_Handler_EulerianRef) eulerianRefList.get(k);
            xml_eulerianNames[k]    = eulerianRef.EulerianName;
            xml_eulerianTypesStr[k] = eulerianRef.EulerianTypeStr;
          }
        } else {
          xml_eulerianNames    = new String[0];
          xml_eulerianTypesStr = new String[0];
        }

        /* -- Get the global application data to resolve the data types */
        application_SharedData applSharedData = Atz_Application_Data_Communication.getApplSharedData();

        SELM_Eulerian[] masterEulerianList = applSharedData.jTable_MainData.getEulerianList();

        /* loop over the eulerian list and resolve references to the master list */
        /* (if no reference in the master list, then skip the reference, issue warning) */
        eulerianList.clear();
        for (int I = 0; I < xml_eulerianNames.length; I++) {
          String eulerianName    = xml_eulerianNames[I];
          String eulerianTypeStr = xml_eulerianTypesStr[I];
          boolean flagFound = false;
          for (int k = 0; k < masterEulerianList.length; k++) {
            if ( (masterEulerianList[k].EulerianName.equals(eulerianName)) &&
                 (masterEulerianList[k].EulerianTypeStr.equals(eulerianTypeStr))  ) {
              eulerianList.add(masterEulerianList[k]);
              flagFound = true;
            } /* end comparison check */

          } /* end of k loop */

          if (flagFound == false) {
            System.out.println(this.getClass().getSimpleName());
            System.out.println("WARNING: Could not resolve the eulerian list reference to known eulerian DOF.");
            System.out.println("  EulerianName    = " + eulerianName);
            System.out.println("  EulerianTypeStr = " + eulerianTypeStr);
            /* issue warning message */
          }

        } /* end of I loop */

      }

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */


  public static void copyTXTFile(String filenameIn, String filenameOut) throws FileNotFoundException, IOException {

    File inputFile  = new File(filenameIn);
    File outputFile = new File(filenameOut);

    FileReader in   = new FileReader(inputFile);
    FileWriter out  = new FileWriter(outputFile);

    int c;

    while ((c = in.read()) != -1)
      out.write(c);

    in.close();
    out.close();

  }


  
  /*======================================================================================== */
  /*---------------------------------------------------------------------------------------- */
  /*======================================================================================== */
  public abstract class operatorDataType implements Atz_XML_SAX_DataHandlerInterface {

    String typeStr = "";
    int    type    = OP_TYPE_NULL;

    public String tagXML_operatorData = "operatorData";

    protected SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOperator_TABLE1 = null;
        
    operatorDataType(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOperator_TABLE_in) {
      couplingOperator_TABLE1 = couplingOperator_TABLE_in;
    }

    public void setCouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOperator_TABLE_in) {
      couplingOperator_TABLE1 = couplingOperator_TABLE_in;    
    }

    public SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 getCouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1() {
      return couplingOperator_TABLE1;
    }

    @Override
    public String toString() {
      String str = "";

      str += "typeStr = " + typeStr + "\n";

      return str;
    }

    @Override
    abstract public Object clone();

    public String getTypeStr() {
      return this.typeStr;
    }
      
    public void setTypeFromStr(String str) {

      type = OP_TYPE_NULL;

      if (OP_TYPE_STR_NULL.equals(str)) {
        type = OP_TYPE_NULL;
      }

      if (OP_TYPE_STR_T_KERNEL_1.equals(str)) {
        type = OP_TYPE_T_KERNEL_1;
      }

      if (OP_TYPE_STR_T_FAXEN_1.equals(str)) {
        type = OP_TYPE_T_FAXEN_1;
      }

      if (OP_TYPE_STR_TR_FAXEN_1.equals(str)) {
        type = OP_TYPE_TR_FAXEN_1;
      }

    }

    public void importData(String filename, int fileType) {
    }

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

    abstract public void exportToXML(BufferedWriter fid);

    public void exportToXML_start(BufferedWriter fid) {

      try {

        /* start tag */
        //Atz_XML_Helper.writeXMLStartTag(fid, tagXML_operatorData, "value = " + typeStr);
        
      } catch (Exception e) {//Catch exception if any
        e.printStackTrace();        
      }

    }

    public void exportToXML_end(BufferedWriter fid) {

      try {
        
        /* end tag */
        //Atz_XML_Helper.writeXMLEndTag(fid, tagXML_operatorData);

      } catch (Exception e) {//Catch exception if any
        e.printStackTrace();
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

    @Override
    public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
      xmlString = xmlString + new String(ch, start, length);
    }

    //Event Handlers
    @Override
    public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      /* setup data for later parsing and processing */
      xmlString = "";
      xmlAttributes = attributes;

      if (qName.equals(tagXML_operatorData)) {
        /* nothing */
      }

    }

    @Override
    public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      if (qName.equals(tagXML_operatorData)) {
        /* nothing */
      } 

    }

    @Override
    public Object XML_getData() {

      Object returnData = null;

      try {
        returnData = this.clone(); /* return a copy of this object, important to clone for case of lists */
      } catch (Exception e) {
        e.printStackTrace();
      }

      return returnData;
      
    }

  } /* end class operatorDataType */


  
  /*======================================================================================== */
  /*---------------------------------------------------------------------------------------- */
  /*======================================================================================== */
  public class operatorDataType_NULL extends operatorDataType {
    
    operatorDataType_NULL(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOperator_TABLE_in) {
      super(couplingOperator_TABLE_in);
      typeStr = OP_TYPE_STR_NULL;
      type    = OP_TYPE_NULL;
    }

    @Override
    public Object clone() {
      operatorDataType_NULL data_copy = new operatorDataType_NULL(couplingOperator_TABLE1);

      data_copy.type                        = this.type;
      data_copy.typeStr                     = this.typeStr;

      return (Object) data_copy;
    }

    /* ====================================================== */
    /* ==================== XML codes ======================= */
    public void exportToXML(BufferedWriter fid) {

      try {

        /* call general data export */
        super.exportToXML_start(fid);
        
        /* call general data export */
        super.exportToXML_end(fid);

      } catch (Exception e) {//Catch exception if any
        e.printStackTrace();
        System.err.println("Error: " + e.getMessage());
      }

    }

    //Event Handlers
    @Override
    public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      /* setup data for later parsing and processing */
      xmlString = "";
      xmlAttributes = attributes;

      if (qName.equals("")) {
        /* nothing */
      }

    }

    @Override
    public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      if (qName.equals("")) {
        /* nothing */
      }

    }

    @Override
    public Object XML_getData() {
      return this.clone(); /* return a copy of this object, important to clone for case of lists */
    }

    /* ==================== XML codes ======================= */
    /* ====================================================== */

  } /* end operatorDataType_NULL */


  /*======================================================================================== */
  /*---------------------------------------------------------------------------------------- */
  /*======================================================================================== */
  public class operatorDataType_T_KERNEL_1 extends operatorDataType {
    
    String weightTableFilename                = "Select File";
    Color  plotColor                          = Color.red;
    
    public String tagXML_weightTableFilename  = "weightTableFilename";
    public String tagXML_plotColor            = "plotColor";
    
    operatorDataType_T_KERNEL_1(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOperator_TABLE1_in) {
      super(couplingOperator_TABLE1_in);
      typeStr = OP_TYPE_STR_T_KERNEL_1;
      type    = OP_TYPE_T_KERNEL_1;
      
    }

    @Override
    public String toString() {
      String str = "";

      str += super.toString();
      str += "weightTableFilename = " + weightTableFilename + "\n";
      str += "plotColor = " + plotColor.toString() + "\n";

      return str;
    }

    @Override
    public Object clone() {
      operatorDataType_T_KERNEL_1 data_copy = new operatorDataType_T_KERNEL_1(couplingOperator_TABLE1);

      data_copy.type                        = this.type;
      data_copy.typeStr                     = this.typeStr;
            
      data_copy.weightTableFilename         = this.weightTableFilename;
      data_copy.plotColor                   = this.plotColor;
      
      return (Object) data_copy;
    }

    public Color getPlotColor() {
      return plotColor;
    }

    public void setPlotColor(Color plotColor_in) {
      plotColor = plotColor_in;
    }

    /* ====================================================== */
    /* ==================== XML codes ======================= */    
    public void exportToXML(BufferedWriter fid) {

      try {

        /* call general data export */
        super.exportToXML_start(fid);

        /* write specific data for this class type */
        if (!couplingOperator_TABLE1.flag_Gen_LAMMPS_XML_Files) {
          Atz_XML_Helper.writeXMLData(fid, tagXML_weightTableFilename, weightTableFilename);
        } else { /* copy the weight table to be local and assign modified name */

          String name = couplingOperator_TABLE1.CouplingOpName;
          name        = name.replace(" ", "_");
          name        = name.replace(".", "");
          name        = name.replace("#", "");
          name        = name.replace("@", "");
          
          String localWeightTableFilename = name + "_" + this.typeStr + "." + SELM_CouplingOperator.tagXML_SELM_CouplingOP + "_weightTable";

          /* copy the weight table to local directory and reference */
          SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.copyTXTFile(weightTableFilename, couplingOperator_TABLE1.gen_LAMMPS_XML_Files_BasePath + localWeightTableFilename);

          /* reference local copy */
          Atz_XML_Helper.writeXMLData(fid, tagXML_weightTableFilename, localWeightTableFilename);          
        }
                                        
        Atz_XML_Helper_Handler_Color handlerColor = new Atz_XML_Helper_Handler_Color(plotColor);
        Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor, handlerColor);
        
        /* call general data export */
        super.exportToXML_end(fid);
       
      } catch (Exception e) {//Catch exception if any
        e.printStackTrace();
        System.err.println("Error: " + e.getMessage());
      }

    }
        
    //Event Handlers
    @Override
    public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      /* setup data for later parsing and processing */
      xmlString = "";
      xmlAttributes = attributes;

      if (qName.equals(tagXML_plotColor)) {
        sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());      
      } else if (qName.equals(tagXML_lagrangianList)) {
        sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
      } else if (qName.equals(tagXML_eulerianList)) {
        sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_EulerianRef()));
      }

    }
    
    @Override
    public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      if (qName.equals(tagXML_weightTableFilename)) {
        weightTableFilename = (String) xmlAttributes.getValue("value");
      } else if (qName.equals(tagXML_plotColor)) {
        plotColor = ((Atz_XML_Helper_Handler_Color) sourceHandler.getLastUsedDataHandler()).color;
      }

    }

    @Override
    public Object XML_getData() {
      return this.clone(); /* return a copy of this object, important to clone for case of lists */
    }

    public void setupLists(SELM_Lagrangian[] lagrangianRefList, SELM_Eulerian[] eulerianRefList) {
      /* assumes XML data was recently read and should be used */
      setupListsFromNames(xml_lagrangianNames,
                          xml_eulerianNames,
                          lagrangianRefList,
                          eulerianRefList);
    }

    public void setupListsFromNames(String[] lagrangianNames, String[] eulerianNames, SELM_Lagrangian[] lagrangianRefList, SELM_Eulerian[] eulerianRefList) {
      setupLagrangianListFromNames(lagrangianNames, lagrangianRefList);
      setupEulerianListFromNames(eulerianNames, eulerianRefList);
    }
   
    public void setupLagrangianListFromNames(String[] lagrangianNames, SELM_Lagrangian[] lagrangianRefList) {

      int N = lagrangianNames.length;

      SELM_Lagrangian[] listLagrangian = new SELM_Lagrangian[N];
      
      for (int k = 0; k < N; k++) {
        /* find appropriate lagrangian */
        listLagrangian[k] = null;        
        for (int j = 0; j < lagrangianRefList.length; j++) {
          if (lagrangianRefList[j].LagrangianName.equals(lagrangianNames[k])) {
            listLagrangian[k] = lagrangianRefList[j];
          }          
        } /* end of j loop */
      } /* end of k loop */

      /* setup the pairs */
      lagrangianList = new ArrayList();
      for (int k = 0; k < N; k++) {
        lagrangianList.add(listLagrangian[k]);
      }
      
    }

    public void setupEulerianListFromNames(String[] eulerianNames, SELM_Eulerian[] eulerianRefList) {

      int N = eulerianNames.length;

      SELM_Eulerian[] listEulerian = new SELM_Eulerian[N];

      for (int k = 0; k < N; k++) {
        /* find appropriate eulerian */
        listEulerian[k] = null;
        for (int j = 0; j < eulerianRefList.length; j++) {
          if (eulerianRefList[j].EulerianName.equals(eulerianNames[k])) {
            listEulerian[k] = eulerianRefList[j];
          }
        } /* end of j loop */
      } /* end of k loop */

      /* setup the pairs */
      eulerianList = new ArrayList();
      for (int k = 0; k < N; k++) {
        eulerianList.add(listEulerian[k]);
      }

    }

    /* ==================== XML codes ======================= */
    /* ====================================================== */
    
  } /* end operatorDataType_T_KERNEL_1 */


  /*======================================================================================== */
  /*---------------------------------------------------------------------------------------- */
  /*======================================================================================== */
  public class operatorDataType_T_FAXEN_1 extends operatorDataType {

    String weightTableFilename = "Select File";
    Color  plotColor           = Color.red;

    public String tagXML_weightTableFilename = "weightTableFilename";
    public String tagXML_plotColor           = "plotColor";

    operatorDataType_T_FAXEN_1(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOperator_TABLE1_in) {
      super(couplingOperator_TABLE1_in);
      typeStr = OP_TYPE_STR_T_FAXEN_1;
      type    = OP_TYPE_T_FAXEN_1;
    }

    @Override
    public String toString() {
      String str = "";

      str += super.toString();
      str += "weightTableFilename = " + weightTableFilename + "\n";
      str += "plotColor = " + plotColor.toString() + "\n";

      return str;
    }

    @Override
    public Object clone() {
      operatorDataType_T_FAXEN_1 data_copy = new operatorDataType_T_FAXEN_1(this.couplingOperator_TABLE1);

      data_copy.type                        = this.type;
      data_copy.typeStr                     = this.typeStr;

      data_copy.weightTableFilename         = this.weightTableFilename;
      data_copy.plotColor                   = this.plotColor;

      return (Object) data_copy;
    }

    public Color getPlotColor() {
      return plotColor;
    }

    public void setPlotColor(Color plotColor_in) {
      plotColor = plotColor_in;
    }

    /* ====================================================== */
    /* ==================== XML codes ======================= */
    public void exportToXML(BufferedWriter fid) {

      try {

        /* call general data export */
        super.exportToXML_start(fid);

        /* write specific data for this class type */
        if (!couplingOperator_TABLE1.flag_Gen_LAMMPS_XML_Files) {
          Atz_XML_Helper.writeXMLData(fid, tagXML_weightTableFilename, weightTableFilename);
        } else {
          /* @@@@ */
        }

        Atz_XML_Helper_Handler_Color handlerColor = new Atz_XML_Helper_Handler_Color(plotColor);
        Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor, handlerColor);

        /* call general data export */
        super.exportToXML_end(fid);

      } catch (Exception e) {//Catch exception if any
        e.printStackTrace();
        System.err.println("Error: " + e.getMessage());
      }

    }

    //Event Handlers
    @Override
    public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      /* setup data for later parsing and processing */
      xmlString = "";
      xmlAttributes = attributes;

      if (qName.equals(tagXML_plotColor)) {
        sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
      }

    }

    @Override
    public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      if (qName.equals(tagXML_weightTableFilename)) {
        weightTableFilename = (String) xmlAttributes.getValue("value");
      } else if (qName.equals(tagXML_plotColor)) {
        plotColor = ((Atz_XML_Helper_Handler_Color) sourceHandler.getLastUsedDataHandler()).color;
      }

    }

    @Override
    public Object XML_getData() {
      return this.clone(); /* return a copy of this object, important to clone for case of lists */
    }

    /* ==================== XML codes ======================= */
    /* ====================================================== */

  } /* end operatorDataType_T_FAXEN_1 */


  /*======================================================================================== */
  /*---------------------------------------------------------------------------------------- */
  /*======================================================================================== */
  public class operatorDataType_TR_FAXEN_1 extends operatorDataType {

    String weightTableFilename = "Select File";
    Color  plotColor           = Color.red;

    public String tagXML_weightTableFilename = "weightTableFilename";
    public String tagXML_plotColor           = "plotColor";

    operatorDataType_TR_FAXEN_1(SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 couplingOperator_TABLE1_in) {
      super(couplingOperator_TABLE1_in);
      typeStr = OP_TYPE_STR_TR_FAXEN_1;
      type    = OP_TYPE_TR_FAXEN_1;
    }

    @Override
    public String toString() {
      String str = "";

      str += super.toString();
      str += "weightTableFilename = " + weightTableFilename + "\n";
      str += "plotColor = " + plotColor.toString() + "\n";

      return str;
    }

    @Override
    public Object clone() {
      operatorDataType_TR_FAXEN_1 data_copy = new operatorDataType_TR_FAXEN_1(this.couplingOperator_TABLE1);

      data_copy.type                        = this.type;
      data_copy.typeStr                     = this.typeStr;

      data_copy.weightTableFilename         = this.weightTableFilename;
      data_copy.plotColor                   = this.plotColor;

      return (Object) data_copy;
    }

    public Color getPlotColor() {
      return plotColor;
    }

    public void setPlotColor(Color plotColor_in) {
      plotColor = plotColor_in;
    }

    /* ====================================================== */
    /* ==================== XML codes ======================= */
    public void exportToXML(BufferedWriter fid) {

      try {

        /* call general data export */
        super.exportToXML_start(fid);

        /* write specific data for this class type */
        if (!couplingOperator_TABLE1.flag_Gen_LAMMPS_XML_Files) {
          Atz_XML_Helper.writeXMLData(fid, tagXML_weightTableFilename, weightTableFilename);
        } else {
          /* @@@ */
        }

        Atz_XML_Helper_Handler_Color handlerColor = new Atz_XML_Helper_Handler_Color(plotColor);
        Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor, handlerColor);

        /* call general data export */
        super.exportToXML_end(fid);

      } catch (Exception e) {//Catch exception if any
        e.printStackTrace();
        System.err.println("Error: " + e.getMessage());
      }

    }

    //Event Handlers
    @Override
    public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      /* setup data for later parsing and processing */
      xmlString = "";
      xmlAttributes = attributes;

      if (qName.equals(tagXML_plotColor)) {
        sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
      }

    }

    @Override
    public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

      if (qName.equals(tagXML_weightTableFilename)) {
        weightTableFilename = (String) xmlAttributes.getValue("value");
      } else if (qName.equals(tagXML_plotColor)) {
        plotColor = ((Atz_XML_Helper_Handler_Color) sourceHandler.getLastUsedDataHandler()).color;
      }

    }

    @Override
    public Object XML_getData() {
      return this.clone(); /* return a copy of this object, important to clone for case of lists */
    }

    /* ==================== XML codes ======================= */
    /* ====================================================== */

  } /* end operatorDataType_TR_FAXEN_1 */


}
