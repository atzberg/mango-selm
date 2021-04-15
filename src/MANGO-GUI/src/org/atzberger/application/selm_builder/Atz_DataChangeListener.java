package org.atzberger.application.selm_builder;

import java.util.EventListener;

/**
 *
 * Interface used to register responses to changes in data in the hierarchical hash map data structure.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface Atz_DataChangeListener extends EventListener {

  public void handleDataChange(Atz_DataChangeEvent e);

}
