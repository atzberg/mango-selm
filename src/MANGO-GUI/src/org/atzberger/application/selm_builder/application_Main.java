/*
 * application_Main.java
 */

package org.atzberger.application.selm_builder;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 *
 * The main application. 
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class application_Main extends SingleFrameApplication {
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {     
      application_Window_Main window_main = new application_Window_Main(this);
      //show(window_main);
    }


    public application_Main() {
      
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of application_Main
     */
    public static application_Main getApplication() {
        return Application.getInstance(application_Main.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
      System.out.println("Launching the application.");
      application_Main.
      launch(application_Main.class, args);
    }

    public static void launchNoArgs() {
      String[] args = new String[0];
      System.out.println("Launching the application with no arguments.");
      launch(application_Main.class, args);      
    }
    
}
