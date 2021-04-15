package org.atzberger.application.selm_builder;

/**
 *
 * Panel for editing this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_Lagrangian extends javax.swing.JPanel {

  Atz_Object_Factory atz_Object_Factory = null;

  final static String atz3D_RENDER_TAG_LAGRANGIAN_ANNOTATION = "LAGRANGIAN_ANNOTATION";

  public application_SharedData applSharedData = null;

  public JPanel_Lagrangian(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;      
  }

  public JPanel_Lagrangian() {
      
  }

  /* set data for displaying lagrangian DOF */
  public void setData(SELM_Lagrangian lagrangianData) {
    
  }

  /* get data for displaying lagrangian DOF */
  public SELM_Lagrangian getData() {
    return null;
  }

  public Atz_Object_Factory getObjectFactory() {

    if (atz_Object_Factory == null) { /* create generic factory */
      String packageName  = this.getClass().getPackage().getName();
      String categoryName = "SELM_Lagrangian"; /* based on panel name */
      String typeName     = this.getName(); /* based on panel name */

      atz_Object_Factory = new Atz_Object_Factory_Generic(packageName, categoryName, typeName);
    }

    return atz_Object_Factory;
  }


}