package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.Atz3D_Model;
import java.io.BufferedWriter;
import java.lang.reflect.Method;

/**
 *
 * This class wraps a dynamically loaded class and makes calls to the
 * appropriate sub-routines using reflection.  This allows for the codes
 * to treat the object generically like one that implements an interface
 * or inherts behaviors rather than needing to call reflection routines
 * directly.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_Lagrangian_wrapper extends SELM_Lagrangian implements SELM_Lagrangian_interface {

  Class  class_ref;
  Object obj_ref;  /* dynamically loaded object */

  Method getName_ref = null;
  Method setName_ref = null;

  SELM_Lagrangian_wrapper(Object obj_in) {
    obj_ref = obj_in;

    checkRoutinesPresent();
  }

  /* loads dynamically the class and wraps the required call
   * routines.
   */
  SELM_Lagrangian_wrapper(String className) {

    //className = "pja_desktopapplication1.SELM_Lagrangian_CONTROLPTS_BASIC1";
    //className = "SELM_Lagrangian_CONTROLPTS_BASIC1";
    //className = SELM_Lagrangian_CONTROLPTS_BASIC1.class.getName();
    try {

      ClassLoader classLoader = this.getClass().getClassLoader();
      class_ref = classLoader.loadClass(className);

      obj_ref = class_ref.newInstance();

    } catch (Exception e) {
      System.out.println("ERROR : SELM_Lagrangian_wrapper");
      System.out.println(e.toString());
      //System.out.println("Class name not found.");
      System.out.println("ClassName = " + className);
    }

    /* create instance of the appropriate class */
    /*
    Class cls = Class.forName("constructor2");
    Class partypes[] = new Class[2];
    partypes[0] = Integer.TYPE;
    partypes[1] = Integer.TYPE;
    Constructor ct
    = cls.getConstructor(partypes);
    Object arglist[] = new Object[2];
    arglist[0] = new Integer(37);
    arglist[1] = new Integer(47);
    Object retobj = ct.newInstance(arglist);
     */

    checkRoutinesPresent();
  }

  /* checks the object implements the needed routines
   * otherwise issues an error.
   */
  void checkRoutinesPresent() {

    Method[] allMethods = class_ref.getDeclaredMethods();

    /* loop over all of the methods */
    for (Method m : allMethods) {

      if (m.getName().equals("getName")) {
        getName_ref = m;
        getName_ref.setAccessible(true);
      }

      if (m.getName().equals("setName")) {
        setName_ref = m;
        setName_ref.setAccessible(true);
      }

    } /* end of method loop */

    if (setName_ref == null) {
      System.out.println("ERROR: SELM_Lagrangian_wrapper : setName()");
      System.out.println("setName_ref not set!");
    }

  }


  public void setName(String name_in) {
    try {
      setName_ref.invoke(obj_ref, name_in);
    } catch (Exception e) {
      System.out.println("ERROR: SELM_Lagrangian_wrapper : setName()");
      System.out.println(e);
    }
  }

  public String getName() {
    String name_out = null;
    
    try {
     name_out = (String) getName_ref.invoke(obj_ref);
    } catch (Exception e) {
      System.out.println("ERROR: SELM_Lagrangian_wrapper : setName()");
      System.out.println(e);
    }

    return name_out;
  }


  @Override
  public Object clone() {
    return null;
  }

  @Override
  public void renderToModel3D(Atz3D_Model model3D) {

  }

  public void importData(String filename, int fileType) {

  }

  public void exportData(String filename, int fileType) {

  }
 
  public void exportData(BufferedWriter fid) {

  }

}
