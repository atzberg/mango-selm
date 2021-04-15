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
public abstract class Atz_Object_Factory {

  public Atz_Object_Factory() {

  }

  public abstract Object getNewInstance(); /* looks for generic constructor */
  
  public abstract Object getNewInstance(Class partypes[], Object[] args);  /* looks for constructor matching given info */
    
}
