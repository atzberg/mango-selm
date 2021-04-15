/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.application.selm_builder;

import java.awt.Font;

/**
 * Branched thread allowing for splash screen to monitor setup progress when loading the application.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class application_Window_Main_SetupThread extends Thread {

  application_Window_Main app_Window_Main;
    
  application_Window_Main_SetupThread(application_Window_Main app_Window_Main_in) {
    app_Window_Main = app_Window_Main_in;    
    this.setName("application_Window_Main_SetupThread");           
  }

  @Override
  public void run() {  /* thread sets up the main window */
    app_Window_Main.setupApplicationWindowMain();    
  }
  
}
