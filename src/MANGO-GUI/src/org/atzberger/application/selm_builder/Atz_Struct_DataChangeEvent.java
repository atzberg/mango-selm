package org.atzberger.application.selm_builder;

import java.util.EventObject;
import java.util.HashMap;

/**
 *
 * Hierarchical hash map associated data change event.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Struct_DataChangeEvent extends EventObject {
  
  static final String EVENT_TYPE_NULL      = "NULL";
  static final String EVENT_TYPE_SET       = "SET";
  
  private String dataChangeTypeStr   = null;
  private Object dataChangeExtraInfo = null;

  Atz_Struct_DataChangeEvent(Object source) {
    super(source);
  }

  Atz_Struct_DataChangeEvent(Object source, String dataChangeTypeStr_in) {
    this(source);
    setDataChangeType(dataChangeTypeStr_in);
  }

  Atz_Struct_DataChangeEvent(Object source, String dataChangeTypeStr_in, Object dataChangeExtraInfo_in) {
    this(source, dataChangeTypeStr_in);
    setDataChangeExtraInfo(dataChangeExtraInfo_in);
  }

  public void setDataChangeType(String dataChangeTypeStr_in) {
    dataChangeTypeStr = dataChangeTypeStr_in;
  }

  public void setDataChangeExtraInfo(Object dataChangeExtraInfo_in) {
    dataChangeExtraInfo = dataChangeExtraInfo_in;
  }

  public String getDataChangeType() {
    return dataChangeTypeStr;
  }

  public Object getDataChangeExtraInfo() {
    return dataChangeExtraInfo;
  }

  static public HashMap<String, Object> genEventExtraInfo_Set(String str, Object data_old, Object data_new, Object sourceCaller) {
    HashMap<String, Object> extraInfo = new HashMap<String, Object>();
    extraInfo.put("relativeSearchStr", str);
    extraInfo.put("data_old", data_old);
    extraInfo.put("data_new", data_new);
    extraInfo.put("sourceCaller", sourceCaller); /* caller to setData */

    return extraInfo;
  }
  
}
