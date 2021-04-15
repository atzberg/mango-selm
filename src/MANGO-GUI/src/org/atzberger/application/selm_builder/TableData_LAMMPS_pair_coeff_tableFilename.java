package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Customised data represention for display and editing within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_LAMMPS_pair_coeff_tableFilename implements Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  String     xmlString     = "";
  Attributes xmlAttributes = null;
  
  String     filename_last = "";

  Date date = new Date();
  private long   lastChangeTime;
  private long   lastEditorChangeTime;

  private String   filename = "";
  private String[] energyEntryNames = new String[0];

  TableData_LAMMPS_pair_coeff_tableFilename() {
    filename            = "energyFile.LAMMPS_table";
    energyEntryNames    = new String[2];
    energyEntryNames[0] = "potential 1";
    energyEntryNames[1] = "potential 2";
  }

  TableData_LAMMPS_pair_coeff_tableFilename(String str) {
    setFilename(str);
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename_in) {
    filename       = filename_in;
    lastChangeTime = date.getTime();
    determineEnergyEntryNames();
  }

  public long getLastChangeTime() {
    return lastChangeTime;
  }

  public void recordLastEditorChangeTime() {
    lastEditorChangeTime = date.getTime();
  }

  public long getLastEditorChangeTime() {
    return lastEditorChangeTime;
  }

  public String[] getEnergyEntryNames() {
    return energyEntryNames;
  }


  void determineEnergyEntryNames() {
        
    /* WARNING: Only allows 100 entry names */
    int MAX_NUM_NAMES = 100;  /* just in case bad parsing */
    int numNames      = 0;

    int valueN = -1;
    ArrayList entryNameList = new ArrayList();

    File info          = new File(filename);
    boolean fileExists = info.exists();

    if ((fileExists) && !(filename_last.equals(filename))) {

      /* use the current filename to determine energy entry names
       * by parsing the file.
       */
      try {

        FileReader fstream = new FileReader(filename);
        BufferedReader fid = new BufferedReader(fstream);

        boolean flagEntryNameSearch = true;
        boolean flagContinue = true;
        while (fid.ready() && flagContinue) {

          String line = fid.readLine();

          line = line.trim();

          /* scan the line */
          if (line.length() > 0) {
            
            char c = line.charAt(0);

            if (c == '#') {
              /* ignore comment lines */
            } else if (isValidNameChar(c) && !Character.isDigit(c)) { /* detect labels or entry names */

              StringTokenizer st = new StringTokenizer(line);

              if (flagEntryNameSearch) { /* we are looking for entry names */

                String entryNameCand = st.nextToken();

                //System.out.println("TableData_LAMMPS_pair_coeff: entryCand = " + entryNameCand);

                if (isValidNameChar(st.toString().charAt(0))) {
                  entryNameList.add(entryNameCand);
                  numNames++;
                  if (numNames > MAX_NUM_NAMES) { /* check not too many names,
                                                   * might occur in parsing error
                                                   */
                    flagContinue = false;
                  }
                  flagEntryNameSearch = false;
                } else { /* continue to search */

                }

              } else { /* we expect these to be labels */

                String labelNameCand = st.nextToken();

                //System.out.println("TableData_LAMMPS_pair_coeff: labelCand = " + labelNameCand);

                if (isValidNameChar(st.toString().charAt(0))) {
                  flagEntryNameSearch = true;
                }

              }

            } /* end else if */

          } /* end string length > 0 */
          
        } /* while file ready */

        //Close the file
        fid.close();

        /* get the entry names */
        Object[] list = entryNameList.toArray();
        energyEntryNames = new String[list.length];
        for (int k = 0; k < list.length; k++) {
          energyEntryNames[k] = (String)list[k];
        }

        /* make copy of the filename string */
        filename_last = filename.toString();

      } catch (Exception e) {//Catch exception if any
        System.err.println(e);
        e.printStackTrace();
      }


    } else {
      /* file does not exist nothing to do */
      int a = 0;
      //System.out.println("File does not exist : filename = " + filename);
    }

  }

  private boolean isValidNameChar(char c) {

    boolean valid = false;

    if (java.lang.Character.isLetter(c) || java.lang.Character.isDigit(c) || (c == '_')) {
      valid = true;
    }

    return valid;
    
  }

  private String parseName(String subLine) {
    String name = "";
    int I = 0;
    char cc = subLine.charAt(I);
    while (isValidNameChar(cc)) {
      name += cc;
      cc    = subLine.charAt(I);
      I++;
    }
    return name;
  }

  @Override
  public Object clone() {
    return new TableData_LAMMPS_pair_coeff_tableFilename(filename);
  }

  public void exportToXML(BufferedWriter fid) {
    Atz_XML_Helper.writeXMLStartTag(fid, this.getClass().getSimpleName());
    Atz_XML_Helper.writeXMLData(fid, "filename", filename);
    Atz_XML_Helper.writeXMLData(fid, "energyEntryNames", energyEntryNames);
    Atz_XML_Helper.writeXMLEndTag(fid, this.getClass().getSimpleName());
  }
  
  /* ==================== XML parser ====================== */
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

    if (qName.equals(this.getClass().getSimpleName())) {
     /* nothing special to do */
    }

  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
    //xmlString = new String(ch, start, length); /* WARNING: could come in indefinite chunk sizes */
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(this.getClass().getSimpleName())) {
      /* done with parsing this object */
    } else if (qName.equals("filename")) {
      filename = xmlAttributes.getValue("value");
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone();
  }

  
}
