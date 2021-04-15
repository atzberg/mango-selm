package org.atzberger.mango.jython;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.text.StyledDocument;

/**
 *
 * Customised buffer for input and output handling embedded interface display and
 * text formatting.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 * 
 */
public class Atz_Jython_ErrorStream_JTextPane extends OutputStream {

  OutputStream out;
  Atz_Jython_JTextPane    jTextPane;

  public Atz_Jython_ErrorStream_JTextPane() {
  //out = System.out; /* set output to standard output */
    out = null;
    jTextPane = null;
  }

  public Atz_Jython_ErrorStream_JTextPane(Atz_Jython_JTextPane jTextPane_in) {
      //out       = System.out; /* set output to standard output */
    out = null;
    jTextPane = jTextPane_in;
  }

  
  public void write(int b) throws IOException {

    char c = (char)b;
    String str = Character.toString(c);

    /* setup the current color for input */
    jTextPane.setTextMode(jTextPane.TEXT_MODE_ERROR);

    if (jTextPane != null) {
      jTextPane.appendTextAtEnd(str);
      //jTextPane.append(str);
    }

    //System.out.print(b);
    //out.write(b);
  }
 
  public void write(byte[] b) throws IOException {
    String str = new String(b);

    /* setup the current color for input */
    jTextPane.setTextMode(jTextPane.TEXT_MODE_ERROR);

    if (jTextPane != null) {
      jTextPane.appendTextAtEnd(str);
      //jTextPane.append(str);
    }

    //System.out.print(b);
    //out.write(b);
  }
  
  public void write(byte[] b, int off, int len) throws IOException {

    /* construct sub-array for writing */
    byte[] b2 = new byte[len];
    for (int i = 0; i < len; i++) {
      b2[i] = b[off + i];
    }

    String str = new String(b2);

    /* setup the current color for input */
    jTextPane.setTextMode(jTextPane.TEXT_MODE_ERROR);

    if (jTextPane != null) {

      //Font font = new Font("Verdana", Font.BOLD, 12);
      //jTextArea.setFont(font);
      //jTextArea.setForeground(Color.BLUE);

      //jTextPane.append(str);
      jTextPane.appendTextAtEnd(str);

      jTextPane.setTextMode(jTextPane.TEXT_MODE_INPUT);

      //jTextArea.setForeground(Color.BLACK);

      //int N = jTextPane.getText().length();

      StyledDocument doc = jTextPane.getStyledDocument();  /* @@@ new way */
      int N = doc.getEndPosition().getOffset() - 1;

      jTextPane.setCaretPosition(N);
      //jTextPane.setLastOutputCursorLoc(N);
      jTextPane.stampLastInputCursorLoc();
      
    }

    /* write to system buffer */
    //out.write(b, off, len);
  }
  
  public void flush() throws IOException {
    //out.flush();
  }
  
}