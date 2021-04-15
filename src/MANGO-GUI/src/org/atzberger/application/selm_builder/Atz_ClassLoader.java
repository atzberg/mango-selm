package org.atzberger.application.selm_builder;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 *
 * Custom class loader allowing for retrieval of object instances for both the default classes implemented with this application
 * and future extension classes supplied by users.  The loader scans the class path associated with the build to find classes.
 * <p>
 * Automatic loading of objects of a generic type are also supported, such as loading all editor panels for a particular data type
 * during initialization of the application.
 * <p>
 * The class loader allow for reflection features to be used in Java allowing for instantiation of new objects by providing a 
 * compiled class file and simply knowing the class name and constructor.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_ClassLoader {

  static public Hashtable registryOfClasses = new Hashtable();  /* stores pairs (name , Atz_ClassLoader_RegistryInfo) */
  
  public void Atz_ClassLoader() {
    /* does nothing */
  }

  static public void addToRegistry(String fullClassName, Atz_Object_Factory atz_Object_Factory) {
    Atz_ClassLoader_RegistryInfo registryInfo = new Atz_ClassLoader_RegistryInfo(atz_Object_Factory);
    registryOfClasses.put(fullClassName, registryInfo);    
  }
    
  static public JPanel[] loadAndInstantiatePanelClassesStartingWith(String startWithStr, String packageName, application_SharedData applSharedData_in) {
    return loadAndInstantiatePanelClassesStartingWith(startWithStr, packageName, Atz_ClassLoader.class.getClassLoader(), applSharedData_in);
  }

  /* WARNING: This loader makes use of a special configuration file in the same directory as the Atz_ClassLoader class file in the dist .jar */  
  static public JPanel[] loadAndInstantiatePanelClassesStartingWith(String startWithStr, String   packageName, ClassLoader classLoader, application_SharedData applSharedData_in) {
  
    int N;
    int k;
    JPanel[] jPanel_list = null;
    String baseClassPath = "./build/classes/";
    //String   packageName   = this.getClass().getPackage().getName();//"pja_desktopapplication1";

    File dir = new File(baseClassPath + packageName + "/");
    String[] children = dir.list();

    Hashtable panelHash = new Hashtable();
    int index = 0;

    /* WARNING: Only names appearing in the file listOfSources.atzConf will be used in search for a class name. */
    /* show my file within the package */
    InputStream is = Atz_ClassLoader.class.getResourceAsStream("listOfSources.atzConf");  /* WARNING: Must update this list manually for class loader to work (cat *.java > listOfSources.atzConf) works fine. */
    String classListStr = new Scanner(is).useDelimiter("\\A").next();
    //System.out.printf("Loaded string from listOfSources.atzConf, which gives \n");
    //System.out.printf(inputStr + "\n");
    String lines[] = classListStr.split("\\r?\\n");

    /* new way based on configuration file */
    index = 0;
    for (int i = 0; i < lines.length; i++) {
      String filename = lines[i];
      if ((filename.startsWith(startWithStr)) & (filename.contains("$") == false) & (filename.endsWith(".java")) ) { /* do not allow certain symbols */
        //System.out.println("filename = " + filename);
        String filename2 = filename.replace(".java", ".class");
        panelHash.put(index, filename2);
        index++;
      }      
    }

    N = index;
    jPanel_list = new JPanel[N];
    for (k = 0; k < N; k++) {
      String filename = (String) panelHash.get(k);

      String className = packageName + "." + filename.replace(".class", "");
      //System.out.println("className = " + className);
      
      try {
        Class class_ref = classLoader.loadClass(className);
        Class partypes[] = new Class[1];
        partypes[0] = application_SharedData.class;
        Constructor constr_ref = class_ref.getConstructor(partypes);
        Object arglist[] = new Object[1];
        arglist[0] = applSharedData_in;
        Object obj_ref = constr_ref.newInstance(arglist);
        jPanel_list[k] = (JPanel) obj_ref;

        /* check panel name and file in which it is contained match,
         * otherwise issue a WARNING and override the name assigned by
         * the user to instead be consistent with the file naming.
         */
        JPanel jPanel          = jPanel_list[k];
        String simpleClassName = jPanel.getClass().getSimpleName();
        String classTag = simpleClassName.replace(startWithStr, "");
        if (jPanel.getName().equals(classTag) == false) {
          System.err.println("");
          System.err.println("WARNING: application_Window_Main : loadAndInstantiatePanelClassesStartingWith() ");
          System.err.println("The Panel class name and the Panel GUI name do not match.");
          System.err.println("Make sure the jPanel.setName() is done properly or rename the class file.");
          System.err.println("jPanel.getName()  = " + jPanel.getName());
          System.err.println("Class Name Tag    = " + classTag);
          System.err.println("The name is overridden by setting the name to " + classTag + ",");
          System.err.println("since this could otherwise cause referencing errors in the codes.");
          System.err.println("");
          jPanel.setName(classTag);
        }

      } catch (Exception e) {
        e.printStackTrace();
        //System.out.println("ERROR: application_Window_Main : loadAndInstantiatePanelClassesStartingWith() ");        
      }

    } /* end of k loop for panel class loading */

    return jPanel_list;
  }

  static public Object loadAndInstantiateClass(String className) {
    return loadAndInstantiateClass(className, Atz_ClassLoader.class.getClassLoader());
  }


  static public Object loadAndInstantiateClass(String className, ClassLoader classLoader) {
    Class  partypes[] = new Class[0];
    Object args[]     = new Object[0];
        
    return loadAndInstantiateClass(className, partypes, args, classLoader);
    
  }


  static public Object loadAndInstantiateClass(String className, Object arg, ClassLoader classLoader) {

    Class partypes[] = new Class[1];
    partypes[0]      = arg.getClass();

    Object args[]    = new Object[1];
    args[0]          = arg;

    return loadAndInstantiateClass(className, partypes, args, classLoader);

  }
  
  static public Object loadAndInstantiateClass(String className, Object[] args, ClassLoader classLoader) {

    Class partypes[] = new Class[args.length];
    for (int k = 0; k < args.length; k++) {
      partypes[k] = args.getClass();
    }

    return loadAndInstantiateClass(className, partypes, args, classLoader);

  }

  static public Object loadAndInstantiateClass(String className, Class partypes[], Object[] args, ClassLoader classLoader) {

    boolean                      flagClassHandled = false;
    Atz_ClassLoader_RegistryInfo registryInfo     = null;
    Object                       obj_ref          = null;

    /* look for object factory in the registry first */
    if (flagClassHandled == false) {

      try {

        registryInfo = (Atz_ClassLoader_RegistryInfo) registryOfClasses.get(className);

        if (registryInfo != null) {
          if ((partypes.length != 0) && (args.length != 0)) {
            obj_ref = registryInfo.getObjectFactory().getNewInstance(partypes, args);
            flagClassHandled = true;
          } else {
            obj_ref = registryInfo.getObjectFactory().getNewInstance();
            flagClassHandled = true;
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR : application_Windows_Main : loadAndInstantiateClass()");
        System.out.println(e.toString());
        //System.out.println("Class name not found.");
        System.out.println("ClassName = " + className);

      }

    } /* end flagClassHandled */


    if (flagClassHandled == false) {
      /* try to perform generic class load (if class not explicitly in the registry) */

      Constructor constr_ref = null;
      Class class_ref        = null;

      try {
        //ClassLoader classLoader = this.getClass().getClassLoader();
        class_ref = classLoader.loadClass(className);

        if (partypes.length != 0) {
          constr_ref = class_ref.getDeclaredConstructor(partypes);
        } else { /* if zero parameters */
          constr_ref = class_ref.getDeclaredConstructor();
        }

        if (args.length != 0) {
          obj_ref = constr_ref.newInstance(args);
          flagClassHandled = true;
        } else { /* if zero parameters */
          obj_ref = constr_ref.newInstance();
          flagClassHandled = true;
        }

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR : application_Windows_Main : loadAndInstantiateClass()");
        System.out.println(e.toString());
        //System.out.println("Class name not found.");
        System.out.println("ClassName = " + className);

      }

    }

    return obj_ref;
  }

}
