package org.atzberger.application.selm_builder;

import java.util.EventListener;

/**
 *
 * Handles response to data change events for hierarchical hash map.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface Atz_Struct_DataChangeListener extends EventListener {

  void handleDataChange(Atz_Struct_DataChangeEvent e);

}
