/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.mango.jython;

import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

/**
 * Tracks the caret location for interactive console functionality.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class Atz_Jython_CaretListener_Label extends JLabel implements CaretListener {

    JTextPane textPane;
    String newline = "\n";

    int dot_last  = -1;
    int mark_last = -1;

        public Atz_Jython_CaretListener_Label(String label, JTextPane textPane_in) {
            super(label);
            textPane = textPane_in;
        }

        public int getCaretPosition() {
            return dot_last;
        }

        public int getCaretMark() {
            return mark_last;
        }


        //Might not be invoked from the event dispatch thread.
        public void caretUpdate(CaretEvent e) {
            displaySelectionInfo(e.getDot(), e.getMark());
        }

        //This method can be invoked from any thread.  It
        //invokes the setText and modelToView methods, which
        //must run on the event dispatch thread. We use
        //invokeLater to schedule the code for execution
        //on the event dispatch thread.
        protected void displaySelectionInfo(final int dot,
                                            final int mark) {

            dot_last = dot;
            mark_last = mark;

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (dot == mark) {  // no selection
                        try {
                            Rectangle caretCoords = textPane.modelToView(dot);
                            //Convert it to view coordinates.
                            setText("caret: text position: " + dot
                                    + ", view location = ["
                                    + caretCoords.x + ", "
                                    + caretCoords.y + "]"
                                    + newline);
                        } catch (BadLocationException ble) {
                            setText("caret: text position: " + dot + newline);
                        }
                    } else if (dot < mark) {
                        setText("selection from: " + dot
                                + " to " + mark + newline);
                    } else {
                        setText("selection from: " + mark
                                + " to " + dot + newline);
                    }
                }
            });
        }
    }
