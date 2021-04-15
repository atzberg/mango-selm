/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java 
 * language and environment is gratefully acknowledged.
 * 
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

package org.atzberger.application.selm_builder;


import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * A font selection dialog.
 * <p>
 * Note: can take a long time to start up on systems with (literally) hundreds
 * of fonts. TODO change list to JList, add a SelectionChangedListener to
 * preview.
 * 
 * @author Ian Darwin
 * @version $Id: FontChooser.java,v 1.19 2004/03/20 20:44:56 ian Exp $
 */
public class JDialog_FontSelector extends JDialog {

  // Results:

  /** The font the user has chosen */
  protected Font resultFont;

  /** The resulting font name */
  protected String resultName;

  /** The resulting font size */
  protected int resultSize;

  /** The resulting boldness */
  protected boolean isBold;

  /** The resulting italicness */
  //protected boolean isItalic;
  
  protected boolean wasSelected = false;

  // Working fields

  /** Display text */
  protected String displayText = "Font Preview";

  /** The list of Fonts */
  protected String fontList[];

  /** The font name chooser */
  protected List fontNameChoice;

  /** The font size chooser */
  protected List fontSizeChoice;

  /** The bold and italic choosers */
  Checkbox bold;// italic;

  /** The list of font sizes */
  protected String fontSizes[] = { "8", "10", "11", "12", "14", "16", "18",
      "20", "24", "30", "36", "40", "48", "60", "72" };

  /** The index of the default size (e.g., 14 point == 4) */
  protected static final int DEFAULT_SIZE = 4;

  /**
   * The display area. Use a JLabel as the AWT label doesn't always honor
   * setFont() in a timely fashion :-)
   */
  protected JLabel previewArea;

  /**
   * Construct a FontChooser -- Sets title and gets array of fonts on the
   * system. Builds a GUI to let the user choose one font at one size.
   */
  public JDialog_FontSelector(Frame f) {
    super(f, "Font Chooser", true);

    Container cp = getContentPane();

    Panel top = new Panel();
    top.setLayout(new FlowLayout());
    
    fontNameChoice = new List(8);
    top.add(fontNameChoice);
    
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    // For JDK 1.1: returns about 10 names (Serif, SansSerif, etc.)
    // fontList = toolkit.getFontList();
    // For JDK 1.2: a much longer list; most of the names that come
    // with your OS (e.g., Arial), plus the Sun/Java ones (Lucida,
    // Lucida Bright, Lucida Sans...)
    fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getAvailableFontFamilyNames();

    for (int i = 0; i < fontList.length; i++)
      fontNameChoice.add(fontList[i]);
    fontNameChoice.select(0);

    fontSizeChoice = new List(8);
    top.add(fontSizeChoice);

    for (int i = 0; i < fontSizes.length; i++)
      fontSizeChoice.add(fontSizes[i]);
    fontSizeChoice.select(DEFAULT_SIZE);

    cp.add(top, BorderLayout.NORTH);

    Panel attrs = new Panel();
    top.add(attrs);
    attrs.setLayout(new GridLayout(0, 1));
    attrs.add(bold = new Checkbox("Bold", false));
    //attrs.add(italic = new Checkbox("Italic", false));

    previewArea = new JLabel(displayText, JLabel.CENTER);
    previewArea.setSize(200, 50);
    cp.add(previewArea, BorderLayout.CENTER);

    Panel bot = new Panel();

    JButton okButton = new JButton("OK");
    bot.add(okButton);
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        previewFont();
        dispose();
        setVisible(false);
        wasSelected = true;
      }
    });

//    JButton pvButton = new JButton("Preview");
//    bot.add(pvButton);
//    pvButton.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        previewFont();
//      }
//    });

    JButton canButton = new JButton("Cancel");
    bot.add(canButton);
    canButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Set all values to null. Better: restore previous.
        resultFont = null;
        resultName = null;
        resultSize = 0;
        isBold = false;
        //isItalic = false;

        dispose();
        setVisible(false);
        wasSelected = false;
      }
    });

    cp.add(bot, BorderLayout.SOUTH);

    previewFont(); // ensure view is up to date!

    pack();
    setLocation(100, 100);
    
    
    fontNameChoice.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        previewFont();        
      }
    });
    
    fontSizeChoice.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        previewFont();        
      }
    });
            
    bold.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        previewFont();        
      }
    });
        
  }
  
  
  public void attempToSelectFont(String fontFamily, int fontSize, boolean isBold) {    
    String list[];
    String fontSizeStr = Integer.toString(fontSize);
    
    list      = fontNameChoice.getItems();
    for (int k = 0; k < list.length; k++) {
      if (fontFamily.equals(list[k])) {
        fontNameChoice.select(k);
      }
    }
    
    list      = fontSizeChoice.getItems();
    for (int k = 0; k < list.length; k++) {
      if (fontSizeStr.equals(list[k])) {
        fontSizeChoice.select(k);
      }
    }
    
    bold.setState(isBold);
    
    this.repaint();
    
    this.previewFont();
        
  }

  /**
   * Called from the action handlers to get the font info, build a font, and
   * set it.
   */
  protected void previewFont() {
    resultName = fontNameChoice.getSelectedItem();
    String resultSizeName = fontSizeChoice.getSelectedItem();
    resultSize = Integer.parseInt(resultSizeName);
    isBold = bold.getState();
    //isItalic = italic.getState();
    int attrs = Font.PLAIN;
    if (isBold)
      attrs = Font.BOLD;
    //if (isItalic)
    //  attrs |= Font.ITALIC;
    resultFont = new Font(resultName, attrs, resultSize);
    // System.out.println("resultName = " + resultName + "; " +
    //     "resultFont = " + resultFont);
    previewArea.setFont(resultFont);
    pack(); // ensure Dialog is big enough.
  }

  /** Retrieve the selected font name. */
  public String getSelectedName() {
    return resultName;
  }

  /** Retrieve the selected size */
  public int getSelectedSize() {
    return resultSize;
  }
  
  /** Retrieve the selected bold */
  public boolean getSelectedBold() {
    return bold.getState();
  }
    
  /** Retrieve the selected font, or null */
  public Font getSelectedFont() {
    return resultFont;
  }
  
  public boolean wasFontSelected() {
    return wasSelected;
  }
  
  
}