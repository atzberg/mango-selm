package org.atzberger.mango.jython;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * Customised input listener allowing for filtering key strokes.  This allows
 * for history recall trigger linked to the up arrow, among other features.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_Jython_InputActionListener implements KeyListener {

  public Atz_Jython_Thread atz_Jython_Thread;

  public Atz_Jython_InputActionListener(Atz_Jython_Thread atz_Jython_Thread_in) {
    atz_Jython_Thread = atz_Jython_Thread_in;
  }

  public void keyTyped(KeyEvent evt) {

    /* trigger interpeter for command, when enter key is pressed */
    if (evt.getKeyChar() == '\n') {
      Atz_Jython_JTextPane jTextPane = atz_Jython_Thread.jTextPane_JythonConsole;
      //int loc = jTextPane.getCaretPosition();
      //jTextPane.getText()

      /* old way */
      /*
      int loc = jTextPane.getText().length();
      jTextPane.setLastInputCursorLoc(loc);
      */

      /* new way */
      jTextPane.stampLastInputCursorLoc();

      atz_Jython_Thread.inputBuffer.fireInputEntered();
      //System.out.println("Enter pressed!!!!");
      //System.out.println(evt);
    }
    
  }

  public void keyPressed(KeyEvent e) {

  }

  public void keyReleased(KeyEvent e) {

  }


}
