package org.atzberger.mango.jython;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


/**
 *
 * Customised text display area allowing for interactive interpreter functionality.
 * This includes processing of input commands upon keyed returns to process by
 * the Jython kernel and the formatting of output text.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_Jython_JTextPane extends JTextPane implements DocumentListener {

  boolean flagStylesNotSet = true;

  int    lastOutputCursorLoc;
  int    lastInputCursorLoc;
  String lastInputText;
  int    textMode = 0;
  ArrayList<String> inputHistory;

  Atz_Jython_CaretListener atz_Jython_CaretListener = null;

  Object lockUsingPane = new Object();  /* used for synchronizing code access to JTextPane */

  final int TEXT_MODE_NULL    = 0;
  final int TEXT_MODE_OTHER   = 1;
  final int TEXT_MODE_INPUT   = 2;
  final int TEXT_MODE_OUTPUT  = 3;
  final int TEXT_MODE_ERROR   = 4;
  final int TEXT_MODE_MESSAGE = 5;

  boolean historySearch = false;
  boolean flagArrowLock = false;

  int     numUpDownKeyPasses = 0;
  int     numBackspacePasses = 0;
  
  int     hist_curI = -1;
  int     eventNum = 0;

  boolean flagIgnoreInput = false;

  /**
   * Constructs the text pane
   */
  public Atz_Jython_JTextPane() {
    init();
  }

  /**
   * Generic initialization 
   */
  final public void init() {

    Style defaultStyle;
    Style mainStyle;
    Style cwStyle;
    Style heading2Style;

    lastOutputCursorLoc = 0;
    lastInputCursorLoc  = 0;
    inputHistory = new ArrayList<String>();

    setupStyles();

    /* add caret listener / may be others used for label to show position, etc... */
    atz_Jython_CaretListener = new Atz_Jython_CaretListener(this);
    this.addCaretListener(atz_Jython_CaretListener);

  }

  public void stampLastOutputCursorLoc() {
    setLastOutputCursorLoc(this.atz_Jython_CaretListener.getCaretPosition());
  }

  public void setLastOutputCursorLoc(int loc) {
    lastOutputCursorLoc = loc;
  }

  public int getLastOutputCursorLoc() {
    return lastOutputCursorLoc;
  }

  public void stampLastInputCursorLoc() {
    setLastInputCursorLoc(this.atz_Jython_CaretListener.getCaretPosition());
  }


  public void setLastInputCursorLoc(int loc) {
    lastInputCursorLoc = loc;
  }

  public int getLastInputCursorLoc() {
    return lastInputCursorLoc;
  }

  public void setLastInputText(String lastInputText_in) {
    
    if (lastInputText_in.length() != 0) { /* only input if string is not empty */
      lastInputText = lastInputText_in.substring(0, lastInputText_in.length() - 1); /* do not include \n char */
      if (lastInputText.length() != 0) {
        inputHistory.add(lastInputText);
      }
    }
    
  }

  public String getLastInputText() {
    return lastInputText;
  }
  
  public void setStylesFromPrefs() {

    String fontData_fontFamily_default = "DialogInput";
    int fontData_fontSize_default = 12;
    boolean fontData_fontBold_default = true;

    String fontData_fontFamily;
    int fontData_fontSize;
    boolean fontData_fontBold;

    /* use preference values */
    Preferences prefs = Preferences.userNodeForPackage(Atz_Jython_JTextPane.class);
    fontData_fontFamily = prefs.get("Atz_Jython_JTextPane.FontFamily", fontData_fontFamily_default);
    fontData_fontSize = Integer.parseInt(prefs.get("Atz_Jython_JTextPane.FontSize", Integer.toString(fontData_fontSize_default)));
    fontData_fontBold = Boolean.parseBoolean(prefs.get("Atz_Jython_JTextPane.FontBold", Boolean.toString(fontData_fontBold_default)));

    setStyles(fontData_fontFamily, fontData_fontSize, fontData_fontBold);
  }
          
  public void setStyles(String fontData_fontFamily, int fontData_fontSize, boolean fontData_fontBold) {

    // === PJA: Setup terminal styles
    StyleContext   sc  = new StyleContext();
    StyledDocument doc = this.getStyledDocument();
    
    Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
    
    Style mainStyle = sc.getStyle("MainStyle");    
    if (mainStyle == null) {
      mainStyle = sc.addStyle("MainStyle", defaultStyle);
    }
    StyleConstants.setLeftIndent(mainStyle, 0);
    StyleConstants.setRightIndent(mainStyle, 0);
    StyleConstants.setFirstLineIndent(mainStyle, 0);
    StyleConstants.setFontFamily(mainStyle, fontData_fontFamily);
    StyleConstants.setFontSize(mainStyle, fontData_fontSize);
    StyleConstants.setBold(mainStyle, fontData_fontBold);
    StyleConstants.setForeground(mainStyle, Color.blue);
    doc.addStyle(mainStyle.getName(), mainStyle);
                    
    Style outputStyle = sc.getStyle("OutputStyle");    
    if (outputStyle == null) {
      outputStyle = sc.addStyle("OutputStyle", defaultStyle);
    }
    StyleConstants.setLeftIndent(outputStyle, 0);
    StyleConstants.setRightIndent(outputStyle, 0);
    StyleConstants.setFirstLineIndent(outputStyle, 0);
    StyleConstants.setFontFamily(outputStyle, fontData_fontFamily);
    StyleConstants.setFontSize(outputStyle, fontData_fontSize);
    StyleConstants.setBold(outputStyle, fontData_fontBold);
    StyleConstants.setForeground(outputStyle, Color.blue);
    doc.addStyle(outputStyle.getName(), outputStyle);

    Style inputStyle = sc.getStyle("InputStyle");    
    if (inputStyle == null) {
      inputStyle = sc.addStyle("InputStyle", defaultStyle);
    }
    StyleConstants.setLeftIndent(inputStyle, 0);
    StyleConstants.setRightIndent(inputStyle, 0);
    StyleConstants.setFirstLineIndent(inputStyle, 0);
    StyleConstants.setFontFamily(inputStyle, fontData_fontFamily);
    StyleConstants.setFontSize(inputStyle, fontData_fontSize);
    StyleConstants.setBold(inputStyle, fontData_fontBold);
    StyleConstants.setForeground(inputStyle, Color.magenta);
    doc.addStyle(inputStyle.getName(), inputStyle);

    Style errorStyle = sc.getStyle("ErrorStyle");    
    if (errorStyle == null) {
      errorStyle = sc.addStyle("ErrorStyle", defaultStyle);
    }
    StyleConstants.setLeftIndent(errorStyle, 0);
    StyleConstants.setRightIndent(errorStyle, 0);
    StyleConstants.setFirstLineIndent(errorStyle, 0);
    StyleConstants.setFontFamily(errorStyle, fontData_fontFamily);
    StyleConstants.setFontSize(errorStyle, fontData_fontSize);
    StyleConstants.setBold(errorStyle, fontData_fontBold);
    StyleConstants.setForeground(errorStyle, Color.red);
    doc.addStyle(errorStyle.getName(), errorStyle);

    Style grayStyle = sc.getStyle("GrayStyle");    
    if (grayStyle == null) {
      grayStyle = sc.addStyle("GrayStyle", defaultStyle);
    }    
    StyleConstants.setLeftIndent(grayStyle, 0);
    StyleConstants.setRightIndent(grayStyle, 0);
    StyleConstants.setFirstLineIndent(grayStyle, 0);
    StyleConstants.setFontFamily(grayStyle, fontData_fontFamily);
    StyleConstants.setFontSize(grayStyle, fontData_fontSize);
    StyleConstants.setBold(grayStyle, fontData_fontBold);
    StyleConstants.setForeground(grayStyle, Color.gray);
    doc.addStyle(grayStyle.getName(), grayStyle);
    
  }

  public void setupStyles() {
        
    if (flagStylesNotSet) {
                        
      StyledDocument doc = this.getStyledDocument();
      
      doc.addDocumentListener(this);

      setStylesFromPrefs();
                  
      try {
        /* change the color */
        //Style style = this.getStyle("OutputStyle");
        //setCharacterAttributes(style, true);

        setTextMode(TEXT_MODE_OUTPUT);
        
        //doc.insertString(0, text, null);
        String text = "Jython Interactive Editor 1.0 : "
                    + "Implemented by Paul J. Atzberger, Copyright 2011. \n \n";
        
        appendTextAtEnd(text);
      } catch (Exception e) {
        e.printStackTrace();
      }

      flagStylesNotSet = false;

    } else {
      /* do nothing */
    }

  }


  /* Document listener */
  public void changedUpdate(DocumentEvent e) {

//    if (textMode == 1) { /* input mode */
//
//      StyledDocument doc = this.getStyledDocument();
//      Style inputStyle   = this.getStyle("InputStyle");
//      int I2             = doc.getEndPosition().getOffset();
//      int I1             = this.getLastOutputCursorLoc();
//      try {
//        doc.setCharacterAttributes(I1, I2, inputStyle, false);
//      } catch (Exception err) {
//        System.out.println(err);
//      }
//
//    }
//
//    if (textMode == 2) { /* output mode */
//
//      StyledDocument doc = this.getStyledDocument();
//      Style outputStyle  = this.getStyle("OutputStyle");
//      int I2             = doc.getEndPosition().getOffset();
//      int I1             = this.getLastInputCursorLoc();
//      try {
//        doc.setCharacterAttributes(I1, I2, outputStyle, false);
//      } catch (Exception err) {
//        System.out.println(err);
//      }
//
//    }

    //System.out.println(e);
  }

  public void insertUpdate(DocumentEvent e) {
    changedUpdate(e);
//    if (textMode == 1) { /* input mode */
//
//      StyledDocument doc = this.getStyledDocument();
//      Style inputStyle   = this.getStyle("InputStyle");
//      int I2             = doc.getEndPosition().getOffset();
//      int I1             = this.getLastOutputCursorLoc();
//      try {
//        doc.setCharacterAttributes(I1, I2, inputStyle, false);
//      } catch (Exception err) {
//        System.out.println(err);
//      }
//
//    }
  }

  public void removeUpdate(DocumentEvent e) {
    changedUpdate(e);   
  }

  public int getTextMode() {
    return textMode;
  }
 
  public void setTextMode(int mode) {

    try {
      
      synchronized (lockUsingPane) {

        textMode = mode;

        if (textMode == TEXT_MODE_ERROR) {

          /* change the color */
          Style style = this.getStyle("ErrorStyle");
          setCharacterAttributes(style, true);

        }

        if (textMode == TEXT_MODE_INPUT) {

          /* change the color */
          Style style = this.getStyle("InputStyle");
          setCharacterAttributes(style, true);

        }

        if (textMode == TEXT_MODE_OUTPUT) {

          /* change the color */
          Style style = this.getStyle("OutputStyle");
          setCharacterAttributes(style, true);

        }

      } /* end synchronized */

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void appendTextAtEnd(String str) {

    try {

      synchronized (lockUsingPane) {

        flagIgnoreInput = true; /* ignore input while giving output */

        StyledDocument doc = this.getStyledDocument();
        int I1 = doc.getEndPosition().getOffset() - 1;
        doc.insertString(I1, str, null);
        int I2 = doc.getEndPosition().getOffset() - 1;
        if (textMode == TEXT_MODE_ERROR) {
          doc.setCharacterAttributes(I1, I2 - I1, this.getStyle("ErrorStyle"), true);
        }
        if (textMode == TEXT_MODE_OUTPUT) {
          doc.setCharacterAttributes(I1, I2 - I1, this.getStyle("OutputStyle"), true);
        }
        if (textMode == TEXT_MODE_INPUT) {
          doc.setCharacterAttributes(I1, I2 - I1, this.getStyle("InputStyle"), true);
        }

        flagIgnoreInput = false; /* restore input monitoring */

      } /* end synchronized */

    } catch (Exception e) {
      flagIgnoreInput = false; /* restore input monitoring */
      e.printStackTrace();
    }

  }

  @Override
  public void processKeyEvent(KeyEvent e) {

    if (flagIgnoreInput == true) { /* makes sure we are monitoring input */
      /* do nothing */

      /* This avoids deadlock situation where information is being output
       * to the JTextPanel while at the same time attempts are made to
       * process the key events.   Instead, we simply ignore the input
       * key strokes that occur simultaneously with any output messages
       * being printed to the editor window.  
       */

    } else {

      try {

        synchronized (lockUsingPane) {

          //System.out.println(" ");
          //System.out.println("event: e.getKeyCode() = " + e.getKeyCode());
          //System.out.println("eventNum = " + eventNum);
          //eventNum++;

          /* override up and down arrow responses */
          if ((e.getKeyCode() == 38) || (e.getKeyCode() == 40)) { /* up and down arrow */

            /* WARNING: note, the up and down arrow events occur in two
             * passes through the processKeyEvent handler,
             * only perform action every other pass!
             */
            numUpDownKeyPasses++;

            if ((numUpDownKeyPasses % 2) == 1) {
              flagArrowLock = false;
            } else {
              flagArrowLock = true;
            }

            if (flagArrowLock == false) {
              flagArrowLock = true;

              StyledDocument doc = this.getStyledDocument();
              int histDir = 0;

              if (e.getKeyCode() == 38) {
                histDir = -1;
              } else {
                histDir = 1;
              }

              int N = inputHistory.size();
              if (N != 0) {

                if (historySearch == false) {
                  hist_curI = N - 1; /* start with last entry */
                  historySearch = true;
                } else {

                  hist_curI = hist_curI + histDir;

                  if (hist_curI < 0) {
                    hist_curI = N - 1;
                  }

                  if (hist_curI > N - 1) {
                    hist_curI = 0;
                  }

                }

                //System.out.println("hist_curI2 = " + hist_curI);

                String inputStr = inputHistory.get(hist_curI);

                int I_min = this.getLastOutputCursorLoc();
                int I1 = doc.getEndPosition().getOffset() - 1;

                try {
                  doc.remove(I_min, I1 - I_min);
                  //setTextMode(getTextMode()); /* make sure formatting stays */
                  appendTextAtEnd(inputStr); /* @@@ */
                  //doc.insertString(I_min, inputStr, null);
                } catch (Exception err) {
                 err.printStackTrace();
                }

              }

              flagArrowLock = false;

            } else {
              /* do nothing still processing arrow event */
            }

          } else if (e.getKeyCode() == 36) {
            /* home key */
            int I_min = this.getLastOutputCursorLoc();
            this.setCaretPosition(I_min);
            setTextMode(getTextMode()); /* make sure formatting stays */

          } else if (e.getKeyChar() == '\n') { /* down arrow */

            /* register that the history search is over and to be reset */
            historySearch = false;
            //super.processKeyEvent(e);

            /* always append newline at the end of the buffer */
//      int loc = this.getText().length();
//
//      StyledDocument doc = this.getStyledDocument();
//      String str = "\n";
//      //str(0) = '\n';
//      try {
//        doc.insertString(loc, str, null);
//      } catch (Exception err) {
//        System.out.println(err);
//      }

            //e.consume();

            super.processKeyEvent(e);

          } else if ((e.getKeyChar() == '\b') || (e.getKeyCode() == 37)) { /* backspace or left arrow */

            /* WARNING: note, the backspace events occur in three
             * passes through the processKeyEvent handler,
             * only perform action every third pass?
             */
            numBackspacePasses++;

            if ((numBackspacePasses % 3) == 1) {
              //flagArrowLock = false;
            } else {
              //flagArrowLock = true;
            }

            /* keep the cursor from moving too far to the left */
            //System.out.println(" ");
            //System.out.println("Backspace ");
            //System.out.println("  e.isConsumed()" + e.isConsumed());

            int I = getCaretPosition();
            int I_min = this.getLastOutputCursorLoc();

            if (I <= I_min) {
              this.setCaretPosition(I_min);
              setTextMode(getTextMode()); /* make sure formatting stays */
            } else {
              super.processKeyEvent(e);
            }

          } else {
            super.processKeyEvent(e);
          }

        } /* end synchronized */

      } catch (Exception err) {
        err.printStackTrace();
      }



    } /* end flagIgnoreInput */

  }


}

