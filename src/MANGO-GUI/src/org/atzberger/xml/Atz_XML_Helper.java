package org.atzberger.xml;

import java.awt.Color;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Generic routines useful for XML generation and parsing.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_XML_Helper {

  public static final String xmlTag_NULL = "NULL";

  public static int getNumberTokens(String str) {

    Scanner scanner    = new Scanner(str);

    int    numTokens = 0;
    String token;
    while (scanner.hasNext()) {
      token = scanner.next();
      numTokens++;
    }

    return numTokens;
  }

  public static void writeXMLHeader(BufferedWriter fid, String version, String encoding) {
    try {
      fid.write("<?xml version=\"" + version + "\" encoding=\"" + encoding + "\"?> \n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void writeXMLNewline(BufferedWriter fid) {

    try {
      fid.write("\n");
    } catch (Exception e) { // Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }

  public static void writeXMLComment(BufferedWriter fid, String comment) {
    /* WARNING: Comment strings are not allowed to have '-','<', '>' characters,
     * line breaks and most other symbols should be alright.
     */

    if (comment.contains("-")) {
      throw new IllegalArgumentException("Comment string for XML contains '-'.  This is not allowed");
    }

    try {
      fid.write("<!--" + comment + "-->\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }

  public static void writeXMLStartTag(BufferedWriter fid, String tag) {
    String attrStr = "";
    writeXMLStartTag(fid, tag, attrStr);
  }

  public static void writeXMLStartTag(BufferedWriter fid, String tag, String attrStr) {

    String attributeStr = null;

    try {

      if (attrStr.length() == 0) {
        attributeStr = "";
      } else {
        attributeStr = " " + attrStr;
      }

      fid.write("<" + tag + attributeStr + ">\n");
      
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void writeXMLEndTag(BufferedWriter fid, String tag) {
    try {
      fid.write("</" + tag + ">\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
  }

  public static void writeXMLData(BufferedWriter fid, String tag, String val) {
    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, String val, String attrStr) {

    String attributeStr = null;

    try {

      if (attrStr.length() == 0) {
        attributeStr = "";
      } else {
        attributeStr = " " + attrStr;
      }

      fid.write("<" + tag + attributeStr + " value=\"" + val + "\"/>\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }

  public static void writeXMLData(BufferedWriter fid, String tag, Object val) {

    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);

  }

  public static void writeXMLData(BufferedWriter fid, String tag, Object val, String attrStr) {
    
    boolean flagTypeDetermined = false;

    /* try to determine type and use approprate XML writer, otherwise write
     * as string representation and issue warning.
     *
     * WARNING: need to be careful since could give recursive infinite loop
     * if specific type below is not implemented in writeXMLData().
     */

    if (Boolean.class.isInstance(val)) {
      boolean valBoolean = (boolean) ((Boolean) val);
      writeXMLData(fid, tag, valBoolean, attrStr);
      flagTypeDetermined = true;
    } else if (boolean[].class.isInstance(val)) {
      boolean[] valArray = (boolean[]) val;
      writeXMLData(fid, tag, valArray, attrStr);
      flagTypeDetermined = true;
    } else if (Integer.class.isInstance(val)) {
      int valInt = (int) ((Integer) val);
      writeXMLData(fid, tag, valInt, attrStr);
      flagTypeDetermined = true;
    } else if (int[].class.isInstance(val)) {
      int[] valArray = (int[]) val;
      writeXMLData(fid, tag, valArray, attrStr);
      flagTypeDetermined = true;
    } else if (Double.class.isInstance(val)) {
      double valDouble = (double) ((Double) val);
      writeXMLData(fid, tag, valDouble, attrStr);
      flagTypeDetermined = true;
    } else if (double[].class.isInstance(val)) {
      double[] valArray = (double[]) val;
      writeXMLData(fid, tag, valArray, attrStr);
      flagTypeDetermined = true;
    } else if (String.class.isInstance(val)) {
      String valStr = (String) val;
      writeXMLData(fid, tag, valStr, attrStr);
      flagTypeDetermined = true;    
    } else if (Atz_XML_Writeable.class.isInstance(val)) {
      Atz_XML_Writeable valWriteable = (Atz_XML_Writeable) val;
      writeXMLData(fid, tag, valWriteable, attrStr);
      flagTypeDetermined = true;
    } else if (Color.class.isInstance(val)) {
      Color valColor = (Color) val;
      writeXMLData(fid, tag, new Atz_XML_Helper_Handler_Color(valColor), attrStr);
      flagTypeDetermined = true;
    }

    /* if type was not determined then just write string representation */
    if (!flagTypeDetermined) {

      System.err.println("");
      System.err.println("WARNING: " + Atz_XML_Helper.class.getSimpleName() + ":" + "writeXMLData()");
      System.err.println("  Specific type to write to XML could not be determined.");
      System.err.println("  using generic toString() to represent the object in XML.");
      System.err.println("  To avoid this warning, write XML representation of this object");
      System.err.println("  directly as a string using writeXMLData().");
      System.err.println("");
      
      try {
        fid.write("<" + tag + " value=\"" + val.toString() + "\"/>\n");
      } catch (Exception e) {//Catch exception if any
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
      }
    }
  
  }

  public static void writeXMLData(BufferedWriter fid, String tag, int val) {
    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, int val, String attrStr) {

    String attributeStr = null;

    try {
      if (attrStr.length() == 0) {
        attributeStr = "";
      } else {
        attributeStr = " " + attrStr;
      }
      fid.write("<" + tag + attributeStr + " value=\"" + Integer.toString(val) + "\"/>\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }

  public static void writeXMLData(BufferedWriter fid, String tag, double val) {
    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, double val, String attrStr) {

    String attributeStr = null;

    try {

      if (attrStr.length() == 0) {
        attributeStr = "";
      } else {
        attributeStr = " " + attrStr;
      }

      fid.write("<" + tag + attributeStr + " value=\"" + Double.toString(val) + "\"/>\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }

  public static void writeXMLData(BufferedWriter fid, String tag, boolean val) {
    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, boolean val, String attrStr) {

    String attributeStr = null;

    try {

      String booleanStr;

      if (val) {
        booleanStr = "true";
      } else {
        booleanStr = "false";
      }

      if (attrStr.length() == 0) {
        attributeStr = "";
      } else {
        attributeStr = " " + attrStr;
      }

      fid.write("<" + tag + attributeStr + " value=\"" + booleanStr + "\"/>\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }

  public static void writeXMLData(BufferedWriter fid, String tag, double[] val) {
    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, double[] val, String attrStr) {

    String attributeStr = null;

    try {

      if (attrStr.length() == 0) {
        attributeStr = "";
      } else {
        attributeStr = " " + attrStr;
      }

      fid.write("<" + tag + attributeStr + ">");
      for (int k = 0; k < val.length; k++) {
        fid.write(Double.toString(val[k]));
        if (k != val.length) {
          fid.write(" ");
        }
      }
      fid.write("</" + tag + ">\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }

  public static void writeXMLData(BufferedWriter fid, String tag, int[] val) {
    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, int[] val, String attrStr) {

    String attributeStr = null;

    try {
      
      if (attrStr.length() == 0) {
        attributeStr = "";
      } else {
        attributeStr = " " + attrStr;
      }

      fid.write("<" + tag + attributeStr + ">");
      for (int k = 0; k < val.length; k++) {
        fid.write(Integer.toString(val[k]));
        if (k != val.length) {
          fid.write(" ");
        }
      }
      fid.write("</" + tag + ">\n");
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }





  public static void writeXMLData(BufferedWriter fid, String tag, Atz_XML_Writeable val) {
    String attrStr = "";
    writeXMLData(fid, tag, val, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, Atz_XML_Writeable val, String attrStr) {

    try {
      if (attrStr.length() == 0) {
        writeXMLStartTag(fid,tag); /* no space after tag label */
      } else {
        writeXMLStartTag(fid,tag,attrStr);
      }
      writeXMLData(fid, val);
      writeXMLEndTag(fid,tag);
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
    }

  }

  public static void writeXMLData(BufferedWriter fid, String tag, Atz_XML_Writeable[] valArray) {
    String attrStr = "";
    writeXMLData(fid, tag, valArray, attrStr);
  }

  public static void writeXMLData(BufferedWriter fid, String tag, Atz_XML_Writeable[] valArray, String attrStr) {
    Atz_XML_Writeable val;

    try {
      writeXMLStartTag(fid,tag,attrStr);
      writeXMLData(fid, valArray);
      writeXMLEndTag(fid,tag);
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }

  public static void writeXMLData(BufferedWriter fid, Atz_XML_Writeable[] valArray) {
    Atz_XML_Writeable val;

    try {
      for (int k = 0; k < valArray.length; k++) {
        val = valArray[k];
        writeXMLData(fid, val);
      }
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }
  
  public static void writeXMLData(BufferedWriter fid, Atz_XML_Writeable val) {

    try {
      if (val != null) {
        val.exportToXML(fid);
      } else {
        writeXMLData(fid, Atz_XML_Helper.xmlTag_NULL, "(null)");
      }
    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

  }

  public static double[] parseDoubleArrayFromString(String str) {

    Scanner  scanner     = new Scanner(str);
    int      numEntries  = Atz_XML_Helper.getNumberTokens(str);
    double   doubleVal;
    double[] doubleArray = new double[numEntries];

    int I = 0;
    while (scanner.hasNext()) {
      doubleArray[I] = scanner.nextDouble();
      I++;
      if (I > numEntries) {
        System.out.println("ERROR: parseDoubleArrayFromString()");
        System.out.println("Number of entries parsed exceeds initial estimate.");
        System.out.println("getNumberTokens() = " + numEntries);
        System.out.println("I = " + I);
      }
    }

    return doubleArray;

  }

  public static int[] parseIntArrayFromString(String str) {

    Scanner scanner      = new Scanner(str);
    int numEntries       = Atz_XML_Helper.getNumberTokens(str);
    int intVal;
    int[] intArray       = new int[numEntries];

    int I = 0;
    while (scanner.hasNext()) {
      intArray[I] = scanner.nextInt();
      I++;
      if (I > numEntries) {
        System.out.println("ERROR: parseIntArrayFromString()");
        System.out.println("Number of entries parsed exceeds initial estimate.");
        System.out.println("getNumberTokens() = " + numEntries);
        System.out.println("I = " + I);
      }
    }

    return intArray;

  }

  
}
