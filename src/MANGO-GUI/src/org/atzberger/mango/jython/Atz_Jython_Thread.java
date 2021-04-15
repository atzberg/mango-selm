package org.atzberger.mango.jython;

import java.awt.Font;

/**
 *
 * Thread running the Jython interactive interpreter.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_Jython_Thread extends Thread {

  protected Atz_Jython_JTextPane              jTextPane_JythonConsole;
  protected Atz_Jython_InputActionListener    atz_Jython_InputActionListener;
  protected Atz_Jython_Console_useReflection  console = null;
  //Atz_Jython_Console                console;
  protected Atz_Jython_InputStream_JTextPane  inputBuffer;
  protected Atz_Jython_OutputStream_JTextPane outputBuffer;
  protected Atz_Jython_ErrorStream_JTextPane  errorBuffer;

  //boolean flagInitialized = false;

  /**
   * Constructs a Jython interactive interpreter thread using a particular
   * display window.
   *
   * @param jTextPane_in
   */
  Atz_Jython_Thread(Atz_Jython_JTextPane jTextPane_in) {
    jTextPane_JythonConsole = jTextPane_in;
    //jTextPane_JythonConsole.setFont(new Font("DialogInput", Font.BOLD, 12));

    atz_Jython_InputActionListener = new Atz_Jython_InputActionListener(this);
    jTextPane_JythonConsole.addKeyListener(atz_Jython_InputActionListener);

    //jTextPane_JythonConsole.append("Jython Text Area \n");

    this.setName("Atz_Jython_Thread");

    /* -- setup the Jython console */
    inputBuffer  = new Atz_Jython_InputStream_JTextPane(jTextPane_JythonConsole);
    outputBuffer = new Atz_Jython_OutputStream_JTextPane(jTextPane_JythonConsole);
    errorBuffer  = new Atz_Jython_ErrorStream_JTextPane(jTextPane_JythonConsole);

    console = new Atz_Jython_Console_useReflection(inputBuffer, outputBuffer, errorBuffer);
    //console      = new Atz_Jython_Console();
    
  }

  /**
   * Terminate the interpreter.
   *
   */
  public void terminateJythonInterpreter() {
    console.terminateJythonInterpreter(jTextPane_JythonConsole, this);
  }

  /**
   * Entry point when the thread is up and running.
   */
  @Override
  public void run() {
      
    //System.out.println("Hello from a separate thread!");

    try {
      console.startConsole();
    } catch (Exception e) {
      e.printStackTrace();
      //flagInitialized = false; /* to unlock anyone waiting */
    }

  }
  
}
