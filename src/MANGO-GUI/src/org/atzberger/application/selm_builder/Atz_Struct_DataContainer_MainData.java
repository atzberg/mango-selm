package org.atzberger.application.selm_builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * Hierarchical hash map.
 * <p>
 * Allows for data to be inserted in a hash map and referenced
 * by dot reference labeling, such as Main.Integrator.timeStep.  This allows for a
 * data structure providing a unified presentation of shared data.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Struct_DataContainer_MainData extends Atz_Struct_DataContainer {

 
  public Atz_Struct_DataContainer_MainData() {
    super();
  }

  public Atz_Struct_DataContainer_MainData(String name) {
    super(name);
  }
  
}
