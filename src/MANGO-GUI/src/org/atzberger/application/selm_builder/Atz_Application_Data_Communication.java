package org.atzberger.application.selm_builder;

import org.atzberger.application.selm_builder.application_SharedData;

/**
 *
 * Serves as a repository for the application data needed by various classes.
 * Also serves to pass data between the application and Jython and Python routines.
 * <p>
 * Warning: Assumes only one instance of the application is running on a given instance of the Java Virtual Machine.
 * <p>
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Application_Data_Communication {

  static public application_SharedData applSharedData;

  static public Object getApplData(String name) {

    Object applData = null;

    /* put list of checks on name and decide on data to return */
    if (name.equals("applSharedData")) {
      applData = (Object) applSharedData;
    }

    return applData;
  }

  static public void setApplData(String name, Object applData) {

    /* set data based on name */
    if (name.equals("applSharedData")) {
      applSharedData = (application_SharedData) applData;
    }
    
  }

  static public application_SharedData getApplSharedData() {
    return (application_SharedData) getApplData("applSharedData");
  }

  static public void setApplSharedData(application_SharedData applSharedData_in) {
    setApplData("applSharedData",applSharedData_in);
  }

}
