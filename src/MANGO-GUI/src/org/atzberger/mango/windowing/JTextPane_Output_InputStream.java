package org.atzberger.mango.windowing;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

/**
 *
 * Customised buffer for handling input and output streams.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTextPane_Output_InputStream extends InputStream {

  //String testBuffer = "Test Command";
  InputStream in;
  JTextPane_Output   jTextPane;
  Object             inputLock = new Object(); /* create obj for specific lock */

  //private boolean threadSuspended = false;

  public JTextPane_Output_InputStream() {
    in        = System.in; /* set input to standard input */
    jTextPane = null;
    
  }

  public JTextPane_Output_InputStream(JTextPane_Output jTextPane_in) {
    in        = System.in; /* set input to standard output */
    jTextPane = jTextPane_in;
  }

  @Override
  public int available() {
    int I = -1;

    try {
      I = in.available();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return I;
    
  }

  @Override
  public int read() {

    int I = -1;

    /* setup the current color for input */
    jTextPane.setTextMode(jTextPane.TEXT_MODE_INPUT);

    try {
      I = in.read();
    } catch(IOException e) {
      e.printStackTrace();
    } catch(Exception e) {
      e.printStackTrace();
    }

    return I;

  }

  @Override
  public int read(byte[] b) {
     int I = -1;

     /* setup the current color for input */
    jTextPane.setTextMode(jTextPane.TEXT_MODE_INPUT);

     try {
       I = in.read(b);
     } catch(Exception e) {
      e.printStackTrace();
    }
     
    return I;
  }

  @Override
  public int read(byte[] b, int off, int len) {
    int I = -1;
    String inputTxt;

    boolean flagTextArea = true;

    /* setup the current color for input */
    jTextPane.setTextMode(jTextPane.TEXT_MODE_INPUT);
    
    /* wait for input */
    if (flagTextArea) {
    boolean flagNotEnoughInput = false;
    
    /* if nothing to read from buffer then wait/sleep until awoken */
    flagNotEnoughInput = true;
    while (flagNotEnoughInput) {
        
      try {

        synchronized(inputLock) { /* use explicit lock object */
          //threadSuspended = true;
          //int a = 1;
          //while (threadSuspended) {
          //  a = 0;
          //} /* loop */
          inputLock.wait();
          flagNotEnoughInput = false;
        }

      } catch (InterruptedException e) {
        flagNotEnoughInput = false; /* indicates wake-up enter pressed */
        e.printStackTrace();
      }
      
    } /* end while flagNotEnoughInput */

    try {
      int I1          = jTextPane.getLastOutputCursorLoc();
      int I2          = jTextPane.getLastInputCursorLoc();
      //inputTxt        = jTextPane.getText(I1, I2 - I1); /* Optimize, but doc seems out of sync with TextPane on Windows 7 */
      //String fullTxt         = jTextPane.getText();
      //inputTxt        = fullTxt.substring(I1, I2);
      inputTxt        = jTextPane.getText(I1, I2 - I1); /* Optimize, but doc seems out of sync with TextPane on Windows 7 */      
      //System.out.println("inputTxt = " + inputTxt);
      jTextPane.setLastInputText(inputTxt);
      byte[] b2       = inputTxt.getBytes();
      for (int i = 0; i < b2.length; i++) { /* ? remove new line \n char */
        b[i] = b2[i];
      }
      I               = b2.length;
    } catch (Exception e) {
      e.printStackTrace();
      I    = 1;
      b[0] = '\n'; /* return new-line to "reset jython prompt" */
    }

    } else {
    
      try {        
        I        = in.read(b, off, len);
        inputTxt = new String(b);
      } catch(Exception e) {
        e.printStackTrace();
      }
   }
    
    return I;
    
  }

  @Override
  public long skip(long n) {
    long I = -1;

    try {
      I = in.skip(n);
    } catch(Exception e) {
      e.printStackTrace();
    }

    return I;    
  }

  public void fireInputEntered() {
    synchronized(inputLock) {
      //threadSuspended = false;
      inputLock.notifyAll();
    }
  }

}
