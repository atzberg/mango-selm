package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Determines from XML data which SELM data type to use to parse file data.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_Interaction_XML_DataDelegator implements Atz_XML_SAX_DataHandlerInterface {

  String     xmlString         = "";
  Attributes xmlAttributes = null;

  SELM_Interaction interactionDataHandler = null;

  String interactionName    = "";
  String interactionTypeStr = "";

  Boolean flagDelegateParsing = false;  /* indicates if parsing delegated to specific handler */
 
  @Override
  public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    flagDelegateParsing = false;
  }

  @Override
  public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    /* nothing to do */
  }

  //Event Handlers
  @Override
  public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    String unitType;

    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;

    if (!flagDelegateParsing) {

      if (qName.equals(SELM_Interaction.class.getSimpleName())) { /* detect SELM_Interaction tag */
        flagDelegateParsing = false; /* initialize when encountering tag */
      }

      if (qName.equals("InteractionName")) {
        //sourceHandler.parseNextTagWithDataHandler(this); /* not technically required */
      }

      if (qName.equals("InteractionTypeStr")) {
        //sourceHandler.parseNextTagWithDataHandler(this); /* not technically required */
      }

    } else { /* parse using by delegating to the specific handler */
      interactionDataHandler.XML_startElement(uri, localName, qName, attributes, sourceHandler);
    }

  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (!flagDelegateParsing) {
      xmlString = xmlString + new String(ch, start, length);
    } else { /* parse using by delegating to the specific handler */
      interactionDataHandler.XML_characters(ch, start, length, sourceHandler);
    }
    
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    
    if (!flagDelegateParsing) { /* not delegating parsing */
      
      if (qName.equals("InteractionName")) {
        interactionName = (String) xmlAttributes.getValue("value");
      }

      if (qName.equals("InteractionTypeStr")) {

        interactionTypeStr = (String) xmlAttributes.getValue("value");

        /* setup the parser to use for the remaining data */

        /* use Reflection to instantiate class for this data type */

        String packageName         = SELM_Interaction.class.getPackage().getName(); //"pja_desktopapplication1";
        String baseName            = SELM_Interaction.class.getSimpleName();
        String interactionClassName = packageName + "." + baseName + "_" + interactionTypeStr;

        interactionDataHandler = (SELM_Interaction) loadAndInstantiateClass(interactionClassName);
        interactionDataHandler.InteractionName = interactionName;
        
        flagDelegateParsing = true; /* delegate parsing to hander */
      }

    } else { /* parse using by delegating to the specific handler */
      interactionDataHandler.XML_endElement(uri, localName, qName, sourceHandler);
    }

    if (qName.equals(SELM_Interaction.class.getSimpleName())) { /* SELM_Interaction tag */
      flagDelegateParsing = false;  /* turn off delegation */
    }
       
  }

  @Override
  public Object XML_getData() {
    return interactionDataHandler.XML_getData();
    //return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }


  private Object loadAndInstantiateClass(String className) {

    Object obj_ref = null;

    /* new way to do this */
    obj_ref = Atz_ClassLoader.loadAndInstantiateClass(className);

    /* old way @@@ */
//    try {
//      ClassLoader classLoader = this.getClass().getClassLoader();
//      Class class_ref         = classLoader.loadClass(className);
//      obj_ref                 = class_ref.newInstance();
//    } catch (Exception e) {
//      System.out.println("ERROR : " + this.getClass().getSimpleName() + " : loadAndInstantiateClass()");
//      System.out.println(e.toString());
//      //System.out.println("Class name not found.");
//      System.out.println("ClassName = " + className);
//      e.printStackTrace();
//    }

    return obj_ref;

  }


}
