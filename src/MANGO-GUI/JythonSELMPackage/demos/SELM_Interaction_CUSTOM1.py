import org.atzberger.application.selm_builder;
from SELM_Builder import Atz_XML_Helper;
from SELM_Builder import SELM_Interaction;
import java.lang;
from java.lang import Double;
import java.lang.Exception;
import java.lang.String;
import java.awt.Color;


#from SELM_Builder import SELM_Interaction;

class SELM_Interaction_CUSTOM1(org.atzberger.application.selm_builder.SELM_Interaction):
  
  # -- static variables 
  # XML Tags
  tagXML_testVariable = "testVariable";  

  # -- constructor
  def __init__(self):
    
    self.testVariable       = 0.123456789;
    self.plotColor          = java.awt.Color(0,0,100);
    self.flagVisible        = True;
    
    self.InteractionName    = "Custom interaction type";
    self.InteractionTypeStr = 'CUSTOM1';
    
    self.xmlString     = "";
    self.xmlAttributes = None;
    
    self.exception_value     = None;
    self.exception_ch        = None;
    self.exception_xmlString = None;
          	      
          	      
  # -- methods
  #def getInteractionName(self):    
  #  return "Wow, it works!!!  We overrode return of name!! ";
    
  def clone(self):
    interaction_copy                        = SELM_Interaction_CUSTOM1();
    #interaction_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    interaction_copy.InteractionName        = java.lang.String(self.InteractionName);
    interaction_copy.InteractionTypeStr     = java.lang.String(self.InteractionTypeStr);

    interaction_copy.testVariable           = self.testVariable;
  
    return interaction_copy;
      
  #public void exportToXML(BufferedWriter fid) 
  def exportToXML(self, fid):

    try:
      
      Atz_XML_Helper.writeXMLStartTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

      Atz_XML_Helper.writeXMLData(fid, self.tagXML_InteractionName,    self.InteractionName);
      Atz_XML_Helper.writeXMLData(fid, self.tagXML_InteractionTypeStr, self.InteractionTypeStr);
      
      Atz_XML_Helper.writeXMLData(fid, self.tagXML_testVariable, self.testVariable);
      
      Atz_XML_Helper.writeXMLEndTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

    except Exception, e:
      self.exception_value = e;
      
      print("Error: SELM_Interaction_CUSTOM1.py : exportToXML()");                  
      if type(e) is java.lang.Exception:
        print(e.getMessage());
        e.printStackTrace();
      else:
        print(e);  
        
  #public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler) 
  def XML_startDocument(self, sourceHandler):
    # nothing to do
    x = 0;
        
  #public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler)
  def XML_endDocument(self, sourceHandler):
    # nothing to do
    x = 0;
        
  # Event Handlers  
  #public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException
  def XML_startElement(self, uri, localName, qName, attributes, sourceHandler):

    # setup data for later parsing and processing 
    self.xmlString     = "";
    self.xmlAttributes = attributes;

    if (qName == SELM_Interaction.tagXML_SELM_Interaction):
      # nothing special to do 
      x = 0;
      
  #public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
  def XML_characters(self, ch, start, length, sourceHandler):

    try:
      
      # manual construction of the string segment
      #myStr = "";
      #for k in range(start, start + length):
      #  myStr = myStr + ch[k];
      
      # use Java method to construct string segment
      strSeg         = java.lang.String(ch, start, length);
      newStr         = unicode(strSeg);  # convert string to unicode            
      self.xmlString = self.xmlString + newStr;
  
      #print("WARNING: SELM_Interaction_CUSTOM1.py : XML_characters()");
      #print("Reporting string for debug purposes.");
      #print("start          = " + str(start));
      #print("length         = " + str(length));
      #print("myStr          = " + myStr);
      #print("strSeg         = " + str(strSeg));
      #print("newStr         = " + newStr);
      #print("self.xmlString = " + self.xmlString);      

    except Exception, e:
      self.exception_value     = e;
      self.exception_ch        = ch;
      self.exception_xmlString = self.xmlString;
            
      print("Error: SELM_Interaction_CUSTOM1.py : XML_characters()");                  
      if type(e) is java.lang.Exception:
        print(e.getMessage());
        e.printStackTrace();
      else:
        print(e);  

  # public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
  def XML_endElement(self, uri, localName, qName, sourceHandler):
    
    try:
        
      if (qName == SELM_Interaction.getSimpleName()):
        #/* check all entries set */
        x = 0;
      elif (qName == SELM_Interaction.tagXML_InteractionName):
        x = 0;
      elif (qName == SELM_Interaction.tagXML_InteractionTypeStr):
        x = 0;
      elif (qName == SELM_Interaction_CUSTOM1.tagXML_testVariable):
        self.testVariable = Double.parseDouble(self.xmlAttributes.getValue("value"));
        print("WARNING: SELM_Interaction_CUSTOM1.py : XML_endElement()");
        print("testVariable = " + str(self.testVariable));

    except Exception, e:
      self.exception_value = e;
      
      print("Error: SELM_Interaction_CUSTOM1.py : XML_endElement()");                  
      if type(e) is java.lang.Exception:
        print(e.getMessage());
        e.printStackTrace();
      else:
        print(e);  
           
  #public Object XML_getData() {
  def XML_getData(self):
    return self.clone(); # return a copy of this object, important to clone for case of lists

