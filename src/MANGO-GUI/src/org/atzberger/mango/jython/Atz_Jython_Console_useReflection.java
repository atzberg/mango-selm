package org.atzberger.mango.jython;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Class;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Properties;
import java.util.prefs.Preferences;
//import org.python.util.InteractiveConsole;

/**
 * Jython interactive interpreter.  Allows for linking to an external installation of Jython by using
 * the Java languages "reflection" capabilities.  This allows for new classes and functions to be
 * called after compilation of the application to byte code.  This provides a level of modularity in
 * decoupling the application codes from the specific implementation of Jython used.  However, this
 * approach assumes a specific API to the Jython libraries.  This class was implemented specifically
 * with Jython 2.5.2 in mind and works well for that distribution.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_Jython_Console_useReflection {

  //protected InteractiveConsole interpreter;
  Object interpreter;
  Class<?> interpreter_class;

  InputStream  interpreter_input  = null;
  OutputStream interpreter_output = null;
  OutputStream interpreter_error  = null;

  String OperatingSystem = System.getProperty("os.name");
  
  /* linux defaults */
  protected String pathNameJythonJAR_default_linux     = "/usr/share/jython2.5.2/jython.jar";
  protected String pathNameJythonModules_default_linux = "/usr/share/jython2.5.2/";
  protected String startupJythonScript_default_linux   = "/home/atzberg/research/javaExamples/netBeansProjects/SELM_Builder/pjaTest1.py";
  //protected String startupJythonCommand_default_linux  = "import jythonStartup";

  Method method = null;
 
  //protected String jythonJarURL_default_linux          = "jar:file:" + pathNameJythonJAR_default_linux + "!/";

  /* interpreter information */
  protected String pathNameJythonJAR     = pathNameJythonJAR_default_linux;
  protected String pathNameJythonModules = pathNameJythonModules_default_linux;
  protected String startupJythonScript   = startupJythonScript_default_linux;
  //protected String startupJythonCommand  = startupJythonCommand_default_linux;
  //protected String jythonJarURL          = jythonJarURL_default_linux;

  /* windows 7 default */
//  protected String pathNameJythonModules = "C:\\jython2.5.2\\";
//  protected String startupJythonScript   = "";
//  protected String startupJythonCommand  = "";

//  public Atz_Jython_Console_useReflection(String pathNameJythonModules_in, String startupJythonScript_in, String startupJythonCommand_in) {
//    pathNameJythonModules = pathNameJythonModules_in;
//    startupJythonScript   = startupJythonScript_in;
//    startupJythonCommand  = startupJythonCommand_in;
//  }



  /**
   * Finds the location of a given class file on the file system.
   * Throws an IOException if the class cannot be found.
   * <br>
   * If the class is in an archive (JAR, ZIP), then the returned object
   * will point to the archive file.
   * <br>
   * If the class is in a directory, the base directory will be returned
   * with the package directory removed.
   * <br>
   * The <code>File.isDirectory()</code> method can be used to
   * determine which is the case.
   * <br>
   * This sub-routine is from
   * http://illegalargumentexception.blogspot.com/2008/04/java-finding-application-directory.html
   * <br>
   * @param c    a given class
   * @return    a File object
   * @throws IOException
   */
  public static File getClassLocation(Class c) throws IOException, FileNotFoundException {
    if(c==null) {
      throw new NullPointerException();
    }

    String className = c.getName();
    String resourceName = className.replace('.', '/') + ".class";
    ClassLoader classLoader = c.getClassLoader();
    if(classLoader==null) {
      classLoader = ClassLoader.getSystemClassLoader();
    }
    URL url = classLoader.getResource(resourceName);

    String szUrl = url.toString();
    if(szUrl.startsWith("jar:file:")) {
      try {
        szUrl = szUrl.substring("jar:".length(), szUrl.lastIndexOf("!"));
        URI uri = new URI(szUrl);
        return new File(uri);
      } catch(URISyntaxException e) {
        throw new IOException(e.toString());
      }
    } else if(szUrl.startsWith("file:")) {
      try {
        szUrl = szUrl.substring(0, szUrl.length() - resourceName.length());
        URI uri = new URI(szUrl);
        return new File(uri);
      } catch(URISyntaxException e) {
        throw new IOException(e.toString());
      }
    }

    throw new FileNotFoundException(szUrl);
  }


  /**
   * Tries to find the folder containing the base codes for the application.
   * We attempt this for both the case of codes in a jar file and for regular
   * directories.
   *
   * @return
   */
  private String getAttemptAtBaseFolder() {
    
    String folderName = "";

    String name = this.getClass().getName().replace('.', '/');
    String s    = this.getClass().getResource("/" + name + ".class").toString();

    /* jar: case */
    if (s.contains("jar:")) {
      s = s.replace('/', File.separatorChar);
      s = s.substring(0, s.indexOf(".jar")+4);
      s = s.substring(s.lastIndexOf(':')-1);
      folderName = s;

      return folderName;
    }

    /* file: */
    if (s.contains("file:")) {
      
      String str = "";

      /* if we found build directory for (linux) then we know base*/
      str = "build/classes/org/atzberger/mango/jython/Atz_Jython_Console_useReflection.class";
      if (s.contains(str)) {
        folderName = s.replace(str,"");
        return folderName;
      }

      /* if we found misc. class directory for (linux) then we assume one up from base */
      str = "org/atzberger/mango/jython/Atz_Jython_Console_useReflection.class";
      if (s.contains(str)) {
        folderName = s.replace(str,"");
        folderName = folderName + "../";
        return folderName;
      }

      /* use that this should be in org.atzberger.jython to find base name */
      
    }

    return folderName;
  }


  /**
   * Links the Jython console input and output to customised buffers.  This allows for display within the graphical interface.
   * 
   * @param inputBuffer
   * @param outputBuffer
   * @param errorBuffer
   */
  public Atz_Jython_Console_useReflection(InputStream inputBuffer, OutputStream outputBuffer, OutputStream errorBuffer) {

    /* determine the operating system */
    String OperatingSystem = System.getProperty("os.name");

    /* try to find the base folder for application */
    String baseFolderApp = "";
    try {
      File classLocation = getClassLocation(this.getClass());
      baseFolderApp = classLocation.getAbsolutePath();
      if (baseFolderApp.endsWith("/build/classes")) {
        baseFolderApp = classLocation.getParentFile().getParent();
      }                  
    } catch (Exception e) {
      baseFolderApp = getAttemptAtBaseFolder();  /* Our attempt at base location for Application */
    }
    
    /* setup default values for jython installation */
    if (OperatingSystem.toLowerCase().contains("win")) { /* For Windows modify path for URL construction */

      /* windows defaults */
      pathNameJythonJAR_default_linux     = baseFolderApp + "\\jython2.5.2\\jython.jar";
      pathNameJythonModules_default_linux = baseFolderApp + "\\jython2.5.2\\";
      startupJythonScript_default_linux   = baseFolderApp + "\\JythonSELMPackage\\jythonStartup.py";
      //startupJythonCommand_default_linux  = "";
      
    } else { /* else assumed Unix or Mac */

      /* linux defaults */
      pathNameJythonJAR_default_linux     = baseFolderApp + "/jython2.5.2/jython.jar";
      pathNameJythonModules_default_linux = baseFolderApp + "/jython2.5.2/";
      startupJythonScript_default_linux   = baseFolderApp + "/JythonSELMPackage/jythonStartup.py";
      //startupJythonCommand_default_linux  = "";
    }

    // Get the user preferences for the Jython JAR distribution, paths, startup scripts, etc...
    Preferences prefs      = Preferences.userNodeForPackage(Atz_Jython_Console_useReflection.class);

    pathNameJythonJAR      = pathNameJythonJAR_default_linux;
    pathNameJythonModules  = pathNameJythonModules_default_linux;
    startupJythonScript    = startupJythonScript_default_linux;

    pathNameJythonJAR      = prefs.get("pathNameJythonJAR",       pathNameJythonJAR_default_linux);
    pathNameJythonModules  = prefs.get("pathNameJythonModules",   pathNameJythonModules_default_linux);
    startupJythonScript    = prefs.get("startupJythonScript",     startupJythonScript_default_linux);
    //startupJythonCommand   = prefs.get("startupJythonCommand",    startupJythonCommand_default_linux);

    // Properties
    if (System.getProperty("python.home") == null) {

      System.out.println("Atz_Jython_Console; pathNameJythonModules = " + pathNameJythonModules);

      System.setProperty("python.home", pathNameJythonModules);

      /* Equivalent to CPython's PYTHONPATH environment variable */
      System.setProperty("python.path", " "); /* setup pre-pend to path (ensure current path used) */
      
      /* Extensions to the standard java.class.path property for use 
         with jythonc. This is useful if you use Jikes as your compiler.        
       */
      System.setProperty("python.jythonc.classpath", " ");
      
      //System.setProperty("PYTHONSTARTUP", "pjaTest1.py");
      String startupJythonScript_formatted = formatJythonScriptName(startupJythonScript);
      System.out.println("Atz_Jython_Console; Start-up script = " + startupJythonScript_formatted);
      System.setProperty("python.startup", startupJythonScript_formatted);

      /* Normally, Jython can only provide access to public members of classes.
       However if this property is set to false and you are using Java 1.2 then
       Jython can access non-public fields, methods, and constructors.
       */
      System.setProperty("python.security.respectJavaAccessibility", "false");

    }

    /* initialize the console */
    String startupJythonScript_formatted = formatJythonScriptName(startupJythonScript);
    String[] argv = new String[2];
    argv[0]       = startupJythonScript_formatted;
    argv[1]       = startupJythonScript_formatted;

    Properties properties = System.getProperties();

    /* old way */
//    //InteractiveConsole.initialize(System.getProperties(), null, new String[0]);
//    InteractiveConsole.initialize(System.getProperties(), null, argv);
//
//    interpreter = new InteractiveConsole();

    try {
      
      String jythonJarURL = formatJythonJarURL(pathNameJythonJAR);
      URL[] urls          = new URL[1];
      URL urlJython       = new URL(jythonJarURL);
      
      urls[0]             = urlJython;
      ClassLoader loader  = URLClassLoader.newInstance(urls, getClass().getClassLoader());

      Class<?> clazz      = Class.forName("org.python.util.InteractiveConsole", true, loader);
      Constructor<?> ctor = clazz.getConstructor();
      
      /* invoke the initialization (static call) */
      // InteractiveConsole.initialize(System.getProperties(), null, argv);      
      Method meth = clazz.getMethod("initialize", properties.getClass(), properties.getClass(), argv.getClass());
      meth.invoke(null, properties, null, argv);

      /* create new instance of the interpreter */
      interpreter_class   = clazz;
      interpreter         = ctor.newInstance();

      setIn(inputBuffer);
      setOut(outputBuffer);
      setErr(errorBuffer);
                  
    } catch (Exception e) {
      /* need to put message in the console window */
      try {
        
        errorBuffer.write("It appears that Jython has not yet been installed.  ".getBytes());
        errorBuffer.write("This can be completed by the following easy steps: \n  ".getBytes());
        errorBuffer.write("\n".getBytes());
        errorBuffer.write("1. Download the distribution of Jython version 2.5.2 from www.jython.org.\n".getBytes());
        errorBuffer.write("\n".getBytes());
        errorBuffer.write("2. Install using the provided Jython Installer.\n".getBytes());
        errorBuffer.write("\n".getBytes());        
        errorBuffer.write("3. Configure this Jython console by right-clicking to obtain the popup menu and select Configuration. \n".getBytes());
        errorBuffer.write("\n".getBytes());        
        errorBuffer.write("4. Configure the JAR filepath to point to the directory and jython.jar file from the installation. \n".getBytes());
        errorBuffer.write("\n".getBytes());        
        errorBuffer.write("5. Configure the modules directory to point to the same location at the jython.jar file.\n".getBytes());
        errorBuffer.write("\n".getBytes());        
        errorBuffer.write("6. Configure the start-up script to point to jythonStartup.py in the directory JythonSELMPackage of the MANGO-SELM installation. \n".getBytes());
        errorBuffer.write("\n".getBytes());
        errorBuffer.write("7. To activate the Jython interpreter after configuration use the restart button on the lower left. \n".getBytes());
        errorBuffer.write("\n".getBytes());
        errorBuffer.write("\n".getBytes());
        errorBuffer.write("If the above has been followed and issues persist, check the file paths are read/write accessible on your machine.  ".getBytes());
        errorBuffer.write("For some platforms the entire software application needs to be closed and restarted for the changes to take effect.  ".getBytes());
        errorBuffer.write("If problems persist further information can be found and a bug report can be submitted on the MANGO-SELM hompage at www.atzberger.org.  ".getBytes());
        errorBuffer.write("\n".getBytes());
        errorBuffer.write("\n".getBytes());
        errorBuffer.flush();

      } catch (Exception e2) {
        e2.printStackTrace();
      }

      e.printStackTrace();

    }

    //InteractiveConsole.initialize(System.getProperties(), null, argv);
    //interpreter = new InteractiveConsole();
    
  }

  /**
   * Entry point for the Jython thread to start the console.
   */
  public void startConsole() {

    String OperatingSystem = System.getProperty("os.name");
    
    //interpreter.eval("import pjaTest1");    
    //interpreter.interact(interpreter.eval("x = 1.0"));
    //interpreter.exec("import jythonStartup.py");

    try {
      //interpreter.exec(startupJythonCommand);

      /* comment out startup command */
      //method = interpreter_class.getMethod("exec", String.class);
      //method.invoke(interpreter, startupJythonCommand);
                  
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
    }

    try {
      //interpreter.exec(startupJythonCommand);
      String startupJythonScript_formatted = formatJythonScriptName(startupJythonScript);
      method = interpreter_class.getMethod("exec", String.class);
      method.invoke(interpreter, "import os;" + "execfile(" + "'" + startupJythonScript_formatted + "'); \n");

    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e);
    }
    
    try {
      //interpreter.interact();
      Class[] pvec = new Class[0];
      Object[] arg = new Object[0];
      method = interpreter_class.getMethod("interact", pvec);
      method.invoke(interpreter, arg);
    } catch(Exception e) {
      e.printStackTrace();
    }

    //interpreter_out
    System.out.println("Jython interactive interpreter was terminated.");

    //interpreter.exec("import pjaTest1.py");
    
  }

  /**
   * Sets the Jython input buffer.
   *
   * @param inputBuffer
   */
  void setIn(InputStream inputBuffer) {

    interpreter_input = inputBuffer;

    //interpreter.setIn(inputBuffer);
    try {
      method = interpreter_class.getMethod("setIn", InputStream.class);
      method.invoke(interpreter, inputBuffer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  
  /**
   * Sets the Jython output buffer.
   *
   * @param outputBuffer
   */
  void setOut(OutputStream outputBuffer) {

    interpreter_output = outputBuffer;

    //interpreter.setOut(outputBuffer);
    try {
      method = interpreter_class.getMethod("setOut", OutputStream.class);
      method.invoke(interpreter, outputBuffer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the Jython error buffer.
   *
   * @param errorBuffer
   */
  void setErr(OutputStream errorBuffer) {

    interpreter_error = errorBuffer;

    //interpreter.setErr(errorBuffer);
    try {
      method = interpreter_class.getMethod("setErr", OutputStream.class);
      method.invoke(interpreter, errorBuffer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Terminates the Jython interpreter by issuing an explicit exit command.
   *
   * @param atz_Jython_JTextPane
   * @param atz_Jython_Thread
   */
  void terminateJythonInterpreter(Atz_Jython_JTextPane atz_Jython_JTextPane, Atz_Jython_Thread atz_Jython_Thread) {
    
    try {
      
      //method = interpreter_class.getMethod("exec", String.class);
      //method.invoke(interpreter, "sys.exit(); \n");
      
      //method = interpreter_class.getMethod("push", String.class);
      //method.invoke(interpreter, "sys.exit(); \n");
      //method.invoke(interpreter, "dir() \n");

      atz_Jython_JTextPane.setCaretPosition(atz_Jython_JTextPane.getLastOutputCursorLoc());
      atz_Jython_JTextPane.appendTextAtEnd("sys.exit(); \n \n");
      atz_Jython_JTextPane.setLastInputCursorLoc(atz_Jython_JTextPane.getCaretPosition());
      atz_Jython_Thread.inputBuffer.fireInputEntered();
      
    } catch (Exception e) {
      e.printStackTrace();
      //System.err.println(e);
    }
    
  }


  /**
   * Saves state of the preferences and configuration so it is persistent in future uses of the interface.
   *
   * @param values
   */
  public static void updateConfigurationForFutureRuns(HashMap values) {
    //jythonJarURL           = (String) values.get("jythonJarURL");
    //pathNameJythonModules  = (String) values.get("pathNameJythonModules");
    //startupJythonScript    = (String) values.get("startupJythonScript");

    // Set the preferences for the jython configuration (persistence between runs of the application)
    Preferences prefs = Preferences.userNodeForPackage(Atz_Jython_Console_useReflection.class);

    prefs.put("pathNameJythonJAR",     (String) values.get("pathNameJythonJAR"));
    prefs.put("pathNameJythonModules", (String) values.get("pathNameJythonModules"));
    prefs.put("startupJythonScript",   (String) values.get("startupJythonScript"));
    prefs.put("startupJythonCommand",  (String) values.get("startupJythonCommand"));

    try {
      prefs.flush(); // ensure the updates are recored on disk.
    } catch(Exception e) {
      e.printStackTrace();
    }
    
  }

  /**
   * Performs some path demangling and conversion to adjust for the conventions of different platforms
   * (Windows, Linux, Mac OS X, etc...)
   *
   * @param scriptName
   * @return formatted name
   */
  public String formatJythonScriptName(String scriptName) {
  String startupJythonScript_formatted = "";

    if (OperatingSystem.toLowerCase().contains("win")) { /* For Windows modify path for URL construction */
      /* replace back-slash with double-back-slashes */
      startupJythonScript_formatted = scriptName.replace("\\", "\\\\");
    } else { /* else assumed Unix or Mac */
      startupJythonScript_formatted = scriptName;
    }

    return startupJythonScript_formatted;
  }

  /**
   *
   * Performs some path demangling and conversion to adjust for the conventions of different platforms
   * (Windows, Linux, Mac OS X, etc...)
   *
   * @param pathNameJythonJAR
   * @return formatted name
   */
  public String formatJythonJarURL(String pathNameJythonJAR) {
      String jythonJarURL;

      if (OperatingSystem.toLowerCase().contains("win")) { /* For Windows modify path for URL construction */
        String path   = pathNameJythonJAR;
        path          = "/" + path.replace("\\", "/");
        jythonJarURL  = "jar:file:" + path + "!/";

      } else { /* else assumed Unix or Mac */
        jythonJarURL = "jar:file:" + pathNameJythonJAR + "!/";
      }

      return jythonJarURL;
      
  }


}
