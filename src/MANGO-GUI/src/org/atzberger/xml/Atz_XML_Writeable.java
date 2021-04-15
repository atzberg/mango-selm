package org.atzberger.xml;

import java.io.BufferedWriter;

/**
 *
 * Data capable of being written to an XML file.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public interface Atz_XML_Writeable {

  public void exportToXML(BufferedWriter fid);

}
