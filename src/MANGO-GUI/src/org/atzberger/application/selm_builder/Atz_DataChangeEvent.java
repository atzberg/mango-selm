package org.atzberger.application.selm_builder;

import java.util.EventObject;

/**
 *
 * Data wrapper event for hierarchical hash map data structure.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_DataChangeEvent extends EventObject {

  
  private String dataChangeTypeStr   = null;
  private Object dataChangeExtraInfo = null;

  public Atz_DataChangeEvent(Object source) {
    super(source);
  }

  public Atz_DataChangeEvent(Object source, String dataChangeTypeStr_in) {
    this(source);
    setDataChangeType(dataChangeTypeStr_in);
  }

  public Atz_DataChangeEvent(Object source, String dataChangeTypeStr_in, Object dataChangeExtraInfo_in) {
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
  
}
