package org.atzberger.application.selm_builder;

/**
 *
 * Panel for editing a specific coupling operator type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_CouplingOperator extends javax.swing.JPanel {

  public application_SharedData applSharedData = null;

  Atz_Object_Factory atz_Object_Factory = null;

  final static String atz3D_RENDER_TAG_COUPLING_OP_ANNOTATION = "COUPLING_OP_ANNOTATION";

  public JPanel_CouplingOperator(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;      
  }

  public JPanel_CouplingOperator() {
      
  }

  /* set data for displaying couplingOp DOF */
  public void setData(SELM_CouplingOperator couplingOpData) {
    
  }

  /* get data for displaying couplingOp DOF */
  public SELM_CouplingOperator getData() {
    return null;
  }

  public Atz_Object_Factory getObjectFactory() {

    if (atz_Object_Factory == null) { /* create generic factory */
      String packageName  = this.getClass().getPackage().getName();
      String categoryName = "SELM_CouplingOperator"; 
      String typeName     = this.getName(); /* based on panel name */

      atz_Object_Factory = new Atz_Object_Factory_Generic(packageName, categoryName, typeName);
    }

    return atz_Object_Factory;
  }

}