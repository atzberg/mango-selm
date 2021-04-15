package org.atzberger.application.selm_builder;

/**
 *
 * Creates new objects of specified type.  Useful as a wrapper and derived class
 * for Jython codes to avoid needing to introduce classes to the class loader
 * but have the capability to introduce new objects for java codes to operate on.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Object_Factory_Generic extends Atz_Object_Factory {

  String packageName = "";
  String categoryStr = "";
  String typeStr     = "";

  public Atz_Object_Factory_Generic(String packageName_in, String categoryStr_in, String typeStr_in) {
    packageName = packageName_in;
    categoryStr = categoryStr_in;
    typeStr     = typeStr_in;
  }

  public Object getNewInstance(Class partypes[], Object[] args) {
    Object obj;

    String className = packageName + "." + categoryStr + "_" + typeStr;

    obj = Atz_ClassLoader.loadAndInstantiateClass(className, partypes, args, this.getClass().getClassLoader());

    return obj;

  }

  public Object getNewInstance() {
    
    Class partypes[] = new Class[0];
    Object[] args    = new Object[0];

    return getNewInstance(partypes, args);
    
  }

//  public Object getNewInstance() {
//
//    Object obj;
//
//    //String packageName = this.getClass().getPackage().getName();//"pja_desktopapplication1";
//    //String startsWithStr = "SELM_Interaction_";
//    //String startsWithStr = categoryStr + "_";
//
//    //String className = packageName + "." + startsWithStr + jPanel_Interaction.getName();
//    String className = packageName + "." + categoryStr + "_" + typeStr;
//
//    obj = Atz_ClassLoader.loadAndInstantiateClass(className, this.getClass().getClassLoader());
//
//    return obj;
//
//  }
    
}
