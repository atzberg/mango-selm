package org.atzberger.application.selm_builder;

/**
 *
 * Represents an element within an XML document.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class XMLElement 
{
	private XMLElement parent;
	private Object value;
	
	XMLElement(XMLElement parent, Object value) {
		this.parent = parent;
		this.value = value;
	}
	
	void setParent(XMLElement parent) {
		this.parent = parent;
	}
	
	public XMLElement parent() {
		return parent;
	}
	
	void setValue(Object value) {
		this.value = value;
	}
	
	public Object value() {
		return value;
	}
}
