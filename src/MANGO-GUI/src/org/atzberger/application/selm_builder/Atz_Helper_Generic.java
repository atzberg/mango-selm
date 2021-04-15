package org.atzberger.application.selm_builder;

/**
 *
 * A collection of common generic routines used throughout the codes.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Helper_Generic {

  static public Object loadAndInstantiateClass(String className) {

    Object obj_ref = null;

    try {
      ClassLoader classLoader = Atz_Helper_Generic.class.getClassLoader();
      Class class_ref         = classLoader.loadClass(className);
      obj_ref                 = class_ref.newInstance();
    } catch (Exception e) {
      System.out.println("ERROR : application_Windows_Main : loadAndInstantiateClass()");
      System.out.println(e.toString());
      //System.out.println("Class name not found.");
      System.out.println("ClassName = " + className);
      e.printStackTrace();
    }

    return obj_ref;

  }
 
}
