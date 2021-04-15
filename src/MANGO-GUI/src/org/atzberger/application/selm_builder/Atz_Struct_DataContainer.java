package org.atzberger.application.selm_builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * Data container for the hierarchical hash map.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Struct_DataContainer extends HashMap<String,Object> {

  static final String nsTag_Name          = "_name";      /* indicates name of struct */
  static final String nsTag_Value         = "_value";     /* indicates value */
  static final String nsTag_Container     = "_container"; /* indicates container */
  
  static final String nsTag_Listeners     = "_listeners"; /* indicates listeners of struct */
  static final String nsTag_Parent        = "_parent";    /* gives parent of this structute, if any */
  
  public Atz_Struct_DataContainer() {
    init();
    put(this.nsTag_Name, "No_Name");
  }

  public Atz_Struct_DataContainer(String labelName) {
    init();
    put(this.nsTag_Name, labelName);
  }

  public Atz_Struct_DataContainer(String labelName, Object data, Atz_Struct_DataChangeListener dataListener, Atz_Struct_DataContainer parent) {
    init();

    put(nsTag_Name,   labelName);
    put(nsTag_Value,  data);
    put(nsTag_Parent, null);//parent);
    
    addDataChangeListener(dataListener);   
  }

  final public void init() {
    put(nsTag_Name,      null);
    put(nsTag_Value,     null);
    put(nsTag_Container, this);
    put(nsTag_Listeners, null);
    put(nsTag_Parent,    null);
  }

  public Object getData(String str) {

    Object data = null;

    /* Parse the string for dots to determine scoping.
     *
     * Ex: SELM_Model.name._TABLE_DATA,
           SELM_Model.description, etc...
     */
    
    /* Recursively move through the data structure to obtain
     * the field, if it exists.  This is done by scoping.
     */    
    String labelName;
    String remainStr;

    int I2 = str.length();
    int I1 = str.indexOf('.'); /* index of first dot */

    if (I1 == -1) { /* indicates no dot char found */
      labelName = str;
      remainStr = "";
    } else { /* indicates dot found so split */
      labelName = str.substring(0, I1);
      remainStr = str.substring(I1 + 1, I2);
    }

    if (remainStr.length() == 0) { /* indicates this is the last label
                                    * retrieve the data to pass back
                                    *
                                    * WARNING: change so only returns "_value" tags
                                    * This will allow optional values intermediate in
                                    * heirachy to be stored and other flexibility.
                                    */

      if (labelName.startsWith("_")) {  /* see if special field */
        data = get(labelName); /* return data type directly for special labels */
      } else {                          /* get the container for normal labels */
        Atz_Struct_DataContainer dataContainer = (Atz_Struct_DataContainer) get(labelName);
        data                                   = dataContainer.get(nsTag_Value);
      }
      
    } else { /* indicates we need to go deeper into the data structure */
      Atz_Struct_DataContainer dataIntermed = (Atz_Struct_DataContainer) get(labelName);
      if (dataIntermed != null) {
        data = dataIntermed.getData(remainStr);
      } else {
        data = null; /* indicates no key with given name */
      }
    }

    return data;

  }

  public void setData(String str, Object data, Atz_Struct_DataChangeListener dataListener, Object sourceCaller) {

    Object data_new = null;
    Object data_old = null;
  
    /* Parse the string for dots to determine scoping.
     *
     * Ex: SELM_Model.name._TABLE_DATA,
           SELM_Model.description, etc...
     */

    /* Recursively move through the data structure to obtain
     * the field, if it exists.  This is done by scoping.
     */
    String labelName;
    String remainStr;
    Atz_Struct_DataContainer dataIntermed;
    
    int I2 = str.length();
    int I1 = str.indexOf('.'); /* index of first dot */

    if (I1 == -1) { /* indicates no dot char found */
      labelName = str;
      remainStr = "";
    } else { /* indicates dot found so split */
      labelName = str.substring(0, I1);
      remainStr = str.substring(I1 + 1, I2);
    }
    
    if (remainStr.length() == 0) {

      /* indicates this is the last label
       * set the data using the current Hashtable
       */

      /* @@@ pre-processing data change */

      /* put a data container into the structure encapsulating the given data */
      Atz_Struct_DataContainer dataContainer = (Atz_Struct_DataContainer) get(labelName); /* see if already container */
      if (dataContainer == null) { /* if new then create container */
        dataContainer = new Atz_Struct_DataContainer(labelName, data, dataListener, this);        
        put(labelName, dataContainer);  /* put the new data container in the hashtable */
      }
      data_new = data;
      data_old = dataContainer.get(nsTag_Value);
      
      dataContainer.put(nsTag_Value, data_new);   /* put the value inside container */

      /* fire any data change events */
      HashMap<String,Object> extraInfo = Atz_Struct_DataChangeEvent.genEventExtraInfo_Set(str, data_old, data_new, sourceCaller);
      dataContainer.fireDataChangeEvent(Atz_Struct_DataChangeEvent.EVENT_TYPE_SET, extraInfo); /* call any data listeners */
      
    } else { /* indicates we need to go deeper into the data structure */
      
      Object intermed = get(labelName);
      if (intermed != null) { /* indicates sub-structure needs to be parsed */
        dataIntermed = (Atz_Struct_DataContainer) intermed;        
        dataIntermed.setData(remainStr, data, dataListener, sourceCaller);
      } else { /* indicates no key with given name, so create
                * data structure for further insertion
                */
        dataIntermed = new Atz_Struct_DataContainer(labelName);        
        put(labelName, dataIntermed);                                      /* put the new data container in the hashtable at the label */
        dataIntermed.setData(remainStr, data, dataListener, sourceCaller); /* set the data inside of it */
      }
    }

    /* == call listeners for post-processing a data change (on trunk) */
    HashMap<String,Object> extraInfo = Atz_Struct_DataChangeEvent.genEventExtraInfo_Set(str, data_old, data_new, sourceCaller);
    fireDataChangeEvent(Atz_Struct_DataChangeEvent.EVENT_TYPE_SET, extraInfo);

  }

  public void setData(String str, Object data, Object sourceCaller) {
    setData(str, data, null, sourceCaller);  /* pass (null) listener for this data */
  }

  public void setData(String str, Object data) {
    setData(str, data, null,null);  /* pass (null) listener for this data */
  }

  public void setData(String str, Object data, Atz_Struct_DataChangeListener dataListener) {
    setData(str, data, dataListener, null);  /* pass (null) listener for this data */
  }

  public void addDataChangeListenerAtLabel(String str, Atz_Struct_DataChangeListener dataListener) {

    /* Parse the string for dots to determine scoping.
     *
     * Ex: SELM_Model.name._TABLE_DATA,
           SELM_Model.description, etc...
     */

    /* Recursively move through the data structure to obtain
     * the field, if it exists.  This is done by scoping.
     */

    if (str.length() != 0) {
      String labelName;
      String remainStr;
      Atz_Struct_DataContainer dataIntermed;

      int I2 = str.length();
      int I1 = str.indexOf('.'); /* index of first dot */

      if (I1 == -1) { /* indicates no dot char found */
        labelName = str;
        remainStr = "";
      } else { /* indicates dot found so split */
        labelName = str.substring(0, I1);
        remainStr = str.substring(I1 + 1, I2);
      }

      if (remainStr.length() == 0) {

        /* indicates this is the last label
         * set the data using the current Hashtable
         */
        /* put a data container into the structure encapsulating the given data */
        Atz_Struct_DataContainer dataContainer = (Atz_Struct_DataContainer) get(labelName); /* see if already exists */
        if (dataContainer == null) { /* if does not exist, then create new container */
          dataContainer = new Atz_Struct_DataContainer(labelName, null, null, this); /* set label and parent only */
          put(labelName, dataContainer); /* put the new data container in the hashtable */
        }
        dataContainer.addDataChangeListener(dataListener); /* add the listener inside the container */
        
      } else { /* indicates we need to go deeper into the data structure */

        Object intermed = get(labelName);
        if (intermed != null) { /* indicates sub-structure needs to be parsed */
          dataIntermed = (Atz_Struct_DataContainer) intermed;
          dataIntermed.addDataChangeListenerAtLabel(remainStr, dataListener);
        } else {
          /* indicates no key with given name, so create
           * data structure for further insertion
           */
          dataIntermed = new Atz_Struct_DataContainer(labelName);
          put(labelName, dataIntermed); /* put new container in the structure */
          dataIntermed.addDataChangeListenerAtLabel(remainStr, dataListener);
        }
      }

    } else { /* else str is of length zero so place listener here */
      addDataChangeListener(dataListener);
    }

  }

  public String toStringFields() {
    
    String displayStr = "";

    /* recursively move through the data structure
     * and write display as string.  Do this in
     * a depth first manner.
     */

    String baseStr = "";
    displayStr     = this.toStringFields(baseStr, displayStr);

    return displayStr;

  }

  private String toStringFields(String baseStr, String displayStr) {

    String[] keyStrList = getKeysSorted();

    /* loop over the keys */
    for (int i = 0; i < keyStrList.length; i++) {
      Object data;
      String keyStr = keyStrList[i];

      //displayStr   += baseStr + keyStr + "\n";
      data          = get(keyStr);

      // if data is further container then expand the display
      if (Atz_Struct_DataContainer.class.isInstance(data)) {
        Atz_Struct_DataContainer intermed = (Atz_Struct_DataContainer) data;
        displayStr                        = intermed.toStringFields(baseStr + keyStr + ".", displayStr); // add indentation
      } else {
        displayStr   += baseStr + keyStr + "\n";
      }

    } // end while loop

    return displayStr;

  }

  public String toStringValues() {
    
    String displayStr = "";

    /* recursively move through the data structure
     * and write display as string.  Do this in
     * a depth first manner.
     */

    String baseStr = "";
    displayStr = toStringValues(baseStr, displayStr);

    return displayStr;

  }

  private String toStringValues(String baseStr, String displayStr) {

    String[] keyStrList = getKeysSorted();

    /* loop over the keys */
    for (int i = 0; i < keyStrList.length; i++) {
      Object data;
      String keyStr = keyStrList[i];

      //displayStr   += baseStr + keyStr;
      data          = get(keyStr);

      // if data is further container then expand the display
      if (Atz_Struct_DataContainer.class.isInstance(data)) {
        Atz_Struct_DataContainer intermed = (Atz_Struct_DataContainer)data;
        displayStr                        = intermed.toStringValues(baseStr + keyStr + ".", displayStr); // add indentation
      }
      //else {

        /* display value for current level */
        data          = get(this.nsTag_Value);
        String dataStr = "";

        if (data != null) {
          dataStr = data.toString();
        } else {
          dataStr = "null";
        }
        
        displayStr   += baseStr + keyStr + ": " + dataStr + "\n";
        
      //}
     
    } // end while loop

    return displayStr;

  }


  private String[] getKeysSorted() {

    /* get all label keys at the current level
     * and sort the label keys in order.
     *
     * A label key is any key not starting
     * with "_" and is used for labeling data.
     */
    Set       keys         = this.keySet();
    Object[]  keyArray     = keys.toArray();
    ArrayList keyStrArray  = new ArrayList();
    
    for (int i = 0; i < keyArray.length; i++) {
      String keyStr = (String) keyArray[i];
      if (keyStr.startsWith("_") == false) {
        keyStrArray.add(keyStr);
      }
    }
    String[]  keyStrList = new String[keyStrArray.size()];
    for (int i = 0; i < keyStrArray.size(); i++) {
      keyStrList[i] = (String)keyStrArray.get(i);
    }
    
    /* sort */
    Arrays.sort(keyStrList);

    return keyStrList;
  }

  public String toString() {
    return toStringValues();
  }

  private Atz_Struct_DataListManager getDataChangeListenerManager() {
    return (Atz_Struct_DataListManager) get(nsTag_Listeners);
  }
    
  private void addDataChangeListener(Atz_Struct_DataChangeListener dataListener) {

    /* only do something if the dataListener is non-null */
    if (dataListener != null) {

      Atz_Struct_DataListManager listManager = (Atz_Struct_DataListManager) get(nsTag_Listeners);

      if (listManager == null) {
        listManager = new Atz_Struct_DataListManager();
        put(nsTag_Listeners, listManager);
      } else { /* indicates list manager already exists */
        /* nothing */
      }

      /* add the listener */
      listManager.addDataChangeListener(dataListener);
      
    } /* end dataListener not null */
    
  }

  private void fireDataChangeEvent(String eventType, HashMap<String,Object> extraInfo) {

    Atz_Struct_DataListManager listManager = (Atz_Struct_DataListManager) get(nsTag_Listeners);

    if (listManager != null) {
      listManager.fireDataChangeEvent(this, eventType, extraInfo);
    }
    
  }


  /* Generates compound name for the current structure based on
   * the parent links 
   */
  public String generateCompoundName() {

    String compoundName = "";

    compoundName = genRecurCompoundName(compoundName);

    return compoundName;
    
  }

  private String genRecurCompoundName(String compoundName) {

    Atz_Struct_DataContainer parent;

    String curName = (String) get(this.nsTag_Name);
    
    if (compoundName.length() != 0) {
      compoundName = curName + "." + compoundName;
    } else { /* else indicates first name considered */
      compoundName = curName;
    }
    
    /* traverse parents to get rest of the name */
    parent        = (Atz_Struct_DataContainer) get(this.nsTag_Parent);
    if (parent != null) {
      compoundName = genRecurCompoundName(compoundName);
    }

    return compoundName;
    
  }

  public String getName() {
    return (String) getData(nsTag_Name);
  }

  public void setName(String name) {
    setData(nsTag_Name, name, this);
  }

  


  
}
