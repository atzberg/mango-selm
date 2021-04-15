package org.atzberger.application.selm_builder;

/**
 *
 * Custom class loader allowing for retrieval of object instances for both the default classes implemented with this application
 * and future extension classes supplied by users.  The loader scans both the class path associated with the build to find classes
 * and looked into a custom registry to retrieve a class specification.  The registry is particularly useful for Jython where custom
 * classes may be specified using Python and called after the application has already loaded.  This allows for Java's reflection capabilities
 * to be used to work with Java classes interactively.
 * <p>
 * Automatic loading of objects of a generic type are also supported, such as loading all editor panels for a particular data type
 * during initialization of the application.
 * <p>
 * The class loader allow for reflection features to be used in Java allowing for instantiation of new objects by providing a
 * compiled class file and simply knowing the class name and constructor.
 * <p>
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_ClassLoader_RegistryInfo {

  public Atz_Object_Factory atz_Object_Factory = null;

  public Atz_ClassLoader_RegistryInfo() {
    /* must construct values later */
  }

  public Atz_ClassLoader_RegistryInfo(Atz_Object_Factory atz_Object_Factory_in) {
    atz_Object_Factory = atz_Object_Factory_in;
  }

  public void setObjectFactory(Atz_Object_Factory atz_Object_Factory_in) {
    atz_Object_Factory = atz_Object_Factory_in;
  }

  public Atz_Object_Factory getObjectFactory() {
    return atz_Object_Factory;
  }

}
