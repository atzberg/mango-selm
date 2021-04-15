package org.atzberger.application.selm_builder;

import java.io.CharArrayWriter;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * XMLContentHandler provides a structured approach to building a data structure
 * from XML content using SAX parser. Generally most of the data structures can
 * be built in a top down approach where a parent gets created first and then
 * children add themselves to the parent. This utility class facilitates building
 * data structures in such a top down approach.
 * 
 * The default content handler provided by SAX API is not conducive to building
 * a data structure. So a programmer ends up in storing parent context of an element
 * as a transient attribute of the handler, but this does not scale up. For every
 * level of depth, a separate attribute needs to be created.
 * 
 * This utility class provides parent element as the context while creating
 * child element and hence allows building a data structure top down.
 * 
 * This utility also provides the element context while reading CDATA and TEXT
 * sections of an element. In order to make use of this feature, certain properties
 * have to be set up as follows...
 * 
 * <p><pre><code>
 * SAXParser parser = ...
 * XMLContentHandler handler = ... //an instance of a subclass of XMLContentHandler
 * parser.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
 * </code></pre></p>
 * 
 * In order to make use of XMLContentHandler, user has to subclass this and provide
 * implementation for the following methods ...
 * 
 * <p><pre><code> 
 * createElement, processText and processCDATA.
 * </code></pre></p>
 * 
 * @author venkatm  (http://www.javalobby.org/java/forums/t104100.html)
 *
 * This code is in the public domain, see above link for details.
 */
public abstract class XMLContentHandler extends DefaultHandler2
{
	/* serves as context container while building a data structure top down */
	private Stack elementStack = new Stack();
	
	private boolean readingCDATA = false;
	private CharArrayWriter text = new CharArrayWriter();
	private CharArrayWriter cdata = new CharArrayWriter();
	
	private boolean isElementStackEmpty() {
		return elementStack.isEmpty();
	}
	
	private void pushElement(XMLElement element) {
		elementStack.push(element);
	}
	
	private XMLElement popElement() {
		return (XMLElement)elementStack.pop();
	}
	
	private XMLElement peekElement() {
		return (XMLElement)elementStack.peek();
	}
	
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
	{
		try {
			createElementHelper(name, attributes);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SAXException("", ex);
		}
	}
	
	private XMLElement createElementHelper(String name, Attributes attributes) throws Exception
	{
		/* create the new element */
		XMLElement element = null;
		if( isElementStackEmpty() )
			element = createElement(null, name, attributes);
		else
			element = createElement(peekElement(), name, attributes);
				
		/* make sure no element created is a null XMLElement */
		if( element == null )
			throw new Exception("The return value of createElement cannot be 'null'.\r\n" +
					"If the value is null, please return an null valued XMLElement by a call to 'nullXMLElement'.");
		
		/* push the newly created element onto the stack */
		pushElement(element);		
		return element;
	}
	
	/**
	 * Subclasses provide an implementation for this.
	 * 
	 * @param parent		Represents the parent element created before. It is null for the root element
	 * @param name			Represents the name of the XML element.
	 * @param attributes	Represents the attributes of the XML element
	 * @return element.
	 * @throws Exception
	 */
	abstract protected XMLElement createElement(XMLElement parent, String name, Attributes attributes) throws Exception;
	
	protected XMLElement newXMLElement(XMLElement parent, Object value) {
          return new XMLElement(parent, value);
	}
	
	/**
	 * Give a call to this to create null valued XML elements.
	 * This would be helpful when an xml element represents just a value 
	 * rather an object of its own. Very often primitive valued properties of
	 * an object are represented by an individual xml element. 
	 * 
	 * @param parent
	 * @return element
	 */
	protected XMLElement nullXMLElement(XMLElement parent) {
		return new XMLElement(parent, null);
	}

	public void endElement(String uri, String localName, String name) throws SAXException
	{		
		XMLElement element = peekElement();
		
		/* let the element store the text */
		try {			
			processText(element.parent(), element, name, text.toString());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SAXException(ex.getMessage(), ex);
		}
		text.reset();
		
		/* pop the element from the stack */
		popElement();
	}
	
	/**
	 * 
	 * @param parentElement It is null when element represents root.
	 * @param element
	 * @param name
	 * @param str
	 * @throws Exception
	 */
	abstract protected void processText(XMLElement parentElement, XMLElement element, String name, String str) throws Exception;
	
	public void endCDATA() throws SAXException 
	{	
		XMLElement element = peekElement();
				
		try {			
			processCDATA(element.parent(), element, cdata.toString());
			
		} catch(Exception ex) {
			throw new SAXException(ex.getMessage(), ex);
		}
		
		readingCDATA = false;
		cdata.reset();
	}
	
	abstract protected void processCDATA(XMLElement parentElement, XMLElement element, String str) throws Exception;
	
	public void startCDATA() throws SAXException {
		readingCDATA = true;
	}

	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		if( readingCDATA )
			cdata.write(ch, start, length);
		else
			text.write(ch, start, length);
	}
}
