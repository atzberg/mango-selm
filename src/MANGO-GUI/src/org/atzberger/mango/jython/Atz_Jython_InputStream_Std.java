package org.atzberger.mango.jython;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Custom buffer displaying Jython in the standard input and output streams.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_Jython_InputStream_Std extends InputStream {

  //String testBuffer = "Test Command";
  InputStream in;

  public Atz_Jython_InputStream_Std() {
    in = System.in; /* set input to standard input */
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

    try {
      I = in.read(b, off, len);
    } catch(Exception e) {
      e.printStackTrace();
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

}
