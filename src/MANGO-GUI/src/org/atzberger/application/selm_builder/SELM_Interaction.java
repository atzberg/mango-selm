package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Writeable;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Model;
import java.io.BufferedWriter;
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
public class SELM_Interaction extends Atz_DataChangeable
        implements SELM_RenderView, Atz_XML_Writeable, Atz_XML_SAX_DataHandlerInterface {

  static final int FILE_TYPE_XML = 1;

  String  InteractionTypeStr = "NULL";
  String  InteractionName    = "NULL";

  public static String  atz3D_RENDER_TAG_INTERACTION = "INTERACTION";
   
  boolean renderVisible = true;
  
  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  static public String tagXML_SELM_Interaction   = SELM_Interaction.class.getSimpleName();
  static public String tagXML_InteractionName    = "InteractionName";
  static public String tagXML_InteractionTypeStr = "InteractionTypeStr";

  
  public SELM_Interaction() {

  }

  public String getInteractionName() {
    return InteractionName;
  }

  public String getInteractionTypeStr() {
    return InteractionTypeStr;
  }

  @Override
  public Object clone() {
    SELM_Interaction interaction_copy = new SELM_Interaction();

    interaction_copy.InteractionName    = this.InteractionName.toString();
    interaction_copy.InteractionTypeStr = this.InteractionTypeStr.toString();

    interaction_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    return (Object) interaction_copy;
  }

  @Override
  public String getRenderTag() {
    return atz3D_RENDER_TAG_INTERACTION;
  }

  @Override
  public void renderToModel3D(Atz3D_Model model3D) {

  }

  public void setupLagrangianFromList(SELM_Lagrangian[] lagrangianList) {
    /* uses the given list to setup the Lagrangian lists */
    /* this is used for example to link names to objects
     * instantiated after XML parse, etc...
     */
  }



  /* ====================================================== */
  /* ==================== XML codes ====================== */  
  public void importData(String filename, int fileType) {

  }
  
  public void exportData(String filename, int fileType) {

  }
  
  public void exportToXML(BufferedWriter fid) {
    
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

  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(SELM_Lagrangian.class.getSimpleName())) {
      /* check all entries set */
    } else if (qName.equals("InteractionName")) {
      InteractionName = xmlAttributes.getValue("value");
    } else if (qName.equals("InteractionTypeStr")) {
      String typeStr = xmlAttributes.getValue("value");  /* maybe check compatible here */
    }

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }


}
