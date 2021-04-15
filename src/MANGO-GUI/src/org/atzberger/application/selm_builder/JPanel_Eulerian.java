package org.atzberger.application.selm_builder;

import javax.swing.event.TableModelListener;

/**
 *
 * Panel for editing the this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_Eulerian extends javax.swing.JPanel {

  Atz_Object_Factory atz_Object_Factory = null;

  final static String atz3D_RENDER_TAG_EULERIAN_ANNOTATION = "EULERIAN_ANNOTATION";

  public application_SharedData applSharedData = null;

  public JPanel_Eulerian(application_SharedData applSharedData_in) {
    applSharedData = applSharedData_in;      
  }

  public JPanel_Eulerian() {
    
  }

  public void setData(SELM_Eulerian eulerian) {
    
  }
  
  public SELM_Eulerian getData() {
    return null;
  }

  public Atz_Object_Factory getObjectFactory() {

    if (atz_Object_Factory == null) { /* create generic factory */
      String packageName  = this.getClass().getPackage().getName();      
      String categoryName = "SELM_Eulerian"; /* based on panel name */
      String typeName     = this.getName(); /* based on panel name */

      atz_Object_Factory = new Atz_Object_Factory_Generic(packageName, categoryName, typeName);
    }
    
    return atz_Object_Factory;
  }

}