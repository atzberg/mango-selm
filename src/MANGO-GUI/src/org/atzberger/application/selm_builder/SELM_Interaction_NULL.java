package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.Atz3D_Element_Lines;
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
public class SELM_Interaction_NULL extends SELM_Interaction {
       
  /* index of element types within the representation of this type */
  public final int    atz3D_Index_Lines = 0;

  Atz3D_Element_Lines atz3D_Element_Lines = new Atz3D_Element_Lines();

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;
 
  public SELM_Interaction_NULL() {

  }

}
