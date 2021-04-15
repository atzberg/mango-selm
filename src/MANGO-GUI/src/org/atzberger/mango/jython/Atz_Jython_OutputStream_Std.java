package org.atzberger.mango.jython;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * Custom buffer displaying Jython in the standard input and output streams.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_Jython_OutputStream_Std extends OutputStream {

  OutputStream out;

  public Atz_Jython_OutputStream_Std() {
    out = System.out; /* set output to standard output */
  }

  @Override
  public void write(int b) throws IOException {
    out.write(b);
  }

  @Override
  public void write(byte[] b) throws IOException {
    out.write(b);
  }

  @Override
  public void 	write(byte[] b, int off, int len) throws IOException {
    out.write(b, off, len);
  }

  @Override
  public void flush() throws IOException {
    out.flush();
  }
  
}
