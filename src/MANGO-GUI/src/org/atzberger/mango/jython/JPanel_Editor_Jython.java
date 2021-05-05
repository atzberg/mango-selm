package org.atzberger.mango.jython;

import org.atzberger.mango.jython.Atz_Jython_Console_useReflection;
import org.atzberger.mango.jython.Atz_Jython_JTextPane;
import org.atzberger.application.selm_builder.Atz_Application_Data_Communication;
import org.atzberger.mango.jython.Atz_Jython_Thread;
import org.atzberger.mango.jython.Atz_Jython_JDialog_Preferences;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import org.atzberger.application.selm_builder.*;

/**
 *
 * Panel displaying the interactive Jython interpreter in the main application frame.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class JPanel_Editor_Jython extends javax.swing.JPanel implements ClipboardOwner {

  File lastDirectorySelected;

  //Atz_Jython_JTextPane atz_Jython_JTextPane;
  //Atz_Jython_Thread    atz_Jython_Thread;

  /**
   * Creates the panel for display
   */
  public JPanel_Editor_Jython() {
    initComponents();

    /* start the thread and console */
    startupThreadAndConsole();
    
    /* setup the preferences data structure */    
    createJythonPreferencesData();

  }

  /*
   * Handles the preference data for the console.
   */
  public void createJythonPreferencesData() {
    Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;

    jythonPreferencesData = new HashMap();
    
    jythonPreferencesData.put("jPanel_Editor_Jython" , this);
    jythonPreferencesData.put("atz_Jython_Thread" ,    atz_Jython_Thread);
    
    jythonPreferencesData.put("pathNameJythonJAR",     atz_Jython_Thread.console.pathNameJythonJAR);
    jythonPreferencesData.put("pathNameJythonModules", atz_Jython_Thread.console.pathNameJythonModules);
    jythonPreferencesData.put("startupJythonScript",   atz_Jython_Thread.console.startupJythonScript);
    //jythonPreferencesData.put("startupJythonCommand",  atz_Jython_Thread.console.startupJythonCommand);

  }

  /**
   * Displays the panels and starts the console interactive thread.
   */
  public void startupThreadAndConsole() {
            
    /* == PJA: Setup the console */
    Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;
    
    //atz_Jython_JTextPane.setText("");
    //atz_Jython_JTextPane.setLastInputCursorLoc(0);
    //atz_Jython_JTextPane.setLastOutputCursorLoc(0);

    atz_Jython_JTextPane.setupStyles();

    // === PJA: Set the Jython thread running...    
    atz_Jython_Thread = new Atz_Jython_Thread((Atz_Jython_JTextPane) jTextPane_Jython_Console);
    atz_Jython_Thread.start();

    
    
  }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu_TextEditor = new javax.swing.JPopupMenu();
        jMenuItem_Copy = new javax.swing.JMenuItem();
        jMenuItem_Paste = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Import = new javax.swing.JMenuItem();
        jMenuItem_SaveOutput = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Font = new javax.swing.JMenuItem();
        jMenuItem_Preferences = new javax.swing.JMenuItem();
        jMenuItem_About = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane_Jython_Console = new Atz_Jython_JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jButton_Reset = new javax.swing.JButton();
        jButton_RunScript = new javax.swing.JButton();

        jPopupMenu_TextEditor.setName("jPopupMenu_TextEditor"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getResourceMap(JPanel_Editor_Jython.class);
        jMenuItem_Copy.setText(resourceMap.getString("jMenuItem_Copy.text")); // NOI18N
        jMenuItem_Copy.setName("jMenuItem_Copy"); // NOI18N
        jMenuItem_Copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_CopyActionPerformed(evt);
            }
        });
        jPopupMenu_TextEditor.add(jMenuItem_Copy);

        jMenuItem_Paste.setText(resourceMap.getString("jMenuItem_Paste.text")); // NOI18N
        jMenuItem_Paste.setName("jMenuItem_Paste"); // NOI18N
        jMenuItem_Paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_PasteActionPerformed(evt);
            }
        });
        jPopupMenu_TextEditor.add(jMenuItem_Paste);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jPopupMenu_TextEditor.add(jSeparator1);

        jMenuItem_Import.setText(resourceMap.getString("jMenuItem_Import.text")); // NOI18N
        jMenuItem_Import.setName("jMenuItem_Import"); // NOI18N
        jMenuItem_Import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ImportActionPerformed(evt);
            }
        });
        jPopupMenu_TextEditor.add(jMenuItem_Import);

        jMenuItem_SaveOutput.setText(resourceMap.getString("jMenuItem_SaveOutput.text")); // NOI18N
        jMenuItem_SaveOutput.setName("jMenuItem_SaveOutput"); // NOI18N
        jMenuItem_SaveOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_SaveOutputActionPerformed(evt);
            }
        });
        jPopupMenu_TextEditor.add(jMenuItem_SaveOutput);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jPopupMenu_TextEditor.add(jSeparator2);

        jMenuItem_Font.setText(resourceMap.getString("jMenuItem_Font.text")); // NOI18N
        jMenuItem_Font.setName("jMenuItem_Font"); // NOI18N
        jMenuItem_Font.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_FontActionPerformed(evt);
            }
        });
        jPopupMenu_TextEditor.add(jMenuItem_Font);

        jMenuItem_Preferences.setText(resourceMap.getString("jMenuItem_Preferences.text")); // NOI18N
        jMenuItem_Preferences.setName("jMenuItem_Preferences"); // NOI18N
        jMenuItem_Preferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_PreferencesActionPerformed(evt);
            }
        });
        jPopupMenu_TextEditor.add(jMenuItem_Preferences);

        jMenuItem_About.setText(resourceMap.getString("jMenuItem_About.text")); // NOI18N
        jMenuItem_About.setName("jMenuItem_About"); // NOI18N
        jMenuItem_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_AboutActionPerformed(evt);
            }
        });
        jPopupMenu_TextEditor.add(jMenuItem_About);

        setMinimumSize(new java.awt.Dimension(400, 200));
        setName("Jython Shell"); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 200));

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextPane_Jython_Console.setComponentPopupMenu(jPopupMenu_TextEditor);
        jTextPane_Jython_Console.setName("jTextPane_Jython_Console");
        jScrollPane1.setViewportView(jTextPane_Jython_Console);
        //jTextPane_Jython_Console.addCaretListener((Atz_Jython_CaretListener_Label)jLabel_CaretPositionListenerLabel);

        jPanel1.setName("jPanel1"); // NOI18N

        jButton_Reset.setText(resourceMap.getString("jButton_Reset.text")); // NOI18N
        jButton_Reset.setName("jButton_Reset"); // NOI18N
        jButton_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(305, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton_Reset)
        );

        jButton_RunScript.setText(resourceMap.getString("jButton_RunScript.text")); // NOI18N
        jButton_RunScript.setName("jButton_RunScript"); // NOI18N
        jButton_RunScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RunScriptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(217, 217, 217))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_RunScript)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_RunScript)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_CopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_CopyActionPerformed
      Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;
      
      setClipboardContents(jTextPane_Jython_Console.getSelectedText());

    }//GEN-LAST:event_jMenuItem_CopyActionPerformed

    private void jMenuItem_PasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_PasteActionPerformed

      Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;

      atz_Jython_JTextPane.appendTextAtEnd(getClipboardContents());
      
    }//GEN-LAST:event_jMenuItem_PasteActionPerformed

    private void jMenuItem_ImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ImportActionPerformed

      Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;

      /* Use File chooser to select python script */
      JFileChooser jFileChooser = new JFileChooser();

      jFileChooser.setDialogTitle("Import Module");
      jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
      jFileChooser.resetChoosableFileFilters();
      jFileChooser.setFileFilter(new Atz_FileFilter(".py", "Python Files"));

      if (lastDirectorySelected != null) {
        jFileChooser.setCurrentDirectory(lastDirectorySelected);

      }

      int returnVal = jFileChooser.showDialog(this, "Import");
      
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();

        lastDirectorySelected = file;

        //System.out.println("File select was:" + file.getAbsolutePath());

        String moduleName = file.getName();
        String filePath = file.getPath();

        moduleName = moduleName.replace(".py", "");

        /* Open all of the data to a project file */

        /* Import the module */
        atz_Jython_JTextPane.setCaretPosition(atz_Jython_JTextPane.getLastOutputCursorLoc());
        atz_Jython_JTextPane.appendTextAtEnd("import imp; "
                                            + moduleName
                                            + " = imp.load_source('" + moduleName + "', '" + filePath + "') \n");
        
        atz_Jython_JTextPane.setLastInputCursorLoc(atz_Jython_JTextPane.getCaretPosition());

        //foo = imp.load_source('module.name', '/path/to/file.py')


        atz_Jython_Thread.inputBuffer.fireInputEntered();

      }
     
    }//GEN-LAST:event_jMenuItem_ImportActionPerformed

    private void jMenuItem_SaveOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_SaveOutputActionPerformed

      Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;

      /* Use File chooser to select python script */
      JFileChooser jFileChooser = new JFileChooser();

      jFileChooser.setDialogTitle("Save Output");
      jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
      jFileChooser.resetChoosableFileFilters();
      //jFileChooser.addChoosableFileFilter(new Atz_FileFilter(".out", "Output Files"));

      if (lastDirectorySelected != null) {
        jFileChooser.setCurrentDirectory(lastDirectorySelected);
      }

      int returnVal = jFileChooser.showDialog(this, "Save");

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();

        lastDirectorySelected = file;

        //System.out.println("File select was:" + file.getAbsolutePath());       
        String filePath = file.getPath();
        
        try {
          FileWriter outFile = new FileWriter(filePath);
          PrintWriter    out = new PrintWriter(outFile);
          
          /* write test file of the entire text buffer */
          out.println(atz_Jython_JTextPane.getText());
          out.close();

        } catch (IOException e) {
          e.printStackTrace();
        }

      }
      
    }//GEN-LAST:event_jMenuItem_SaveOutputActionPerformed

    private void jMenuItem_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_AboutActionPerformed

      Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;
      
      atz_Jython_JTextPane.setCaretPosition(atz_Jython_JTextPane.getLastOutputCursorLoc());
      atz_Jython_JTextPane.appendTextAtEnd("\n" 
                                         + "======================================================\n"
                                         + "  Jython Interactive Intepreter Editor \n"
                                         + "  Editor Version 1.0 \n"
                                         + "  Written by Paul J. Atzberger \n"
                                         + "  Copyright 2011 \n"                                         
                                         + "======================================================\n");
      atz_Jython_JTextPane.setLastOutputCursorLoc(atz_Jython_JTextPane.getCaretPosition());
      atz_Jython_JTextPane.appendTextAtEnd("\n");
      atz_Jython_JTextPane.appendTextAtEnd("import platform; platform.version(); ");
      atz_Jython_JTextPane.appendTextAtEnd("print 'Jython Version ' + platform.python_version() \n");
      atz_Jython_JTextPane.setLastInputCursorLoc(atz_Jython_JTextPane.getCaretPosition());
      atz_Jython_JTextPane.setCaretPosition(atz_Jython_JTextPane.lastInputCursorLoc);
            
      atz_Jython_Thread.inputBuffer.fireInputEntered();

    }//GEN-LAST:event_jMenuItem_AboutActionPerformed

    private void jMenuItem_PreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_PreferencesActionPerformed

    /* -- Show the simulation data dialog -- */
    Atz_Jython_JDialog_Preferences jDialog_preferences
        = new Atz_Jython_JDialog_Preferences(jythonPreferencesData);   //(generateSimulationData_LAMMPS);

    jDialog_preferences.setVisible(true);

    /* update the jython data */
    jythonPreferencesData = (HashMap) jDialog_preferences.getDataValue();

    /* setup the data using the preferences */
    // Set the user preferences for the Jython JAR distribution, paths, startup scripts, etc...
    Preferences prefs = Preferences.userNodeForPackage(Atz_Jython_Console_useReflection.class);

    Atz_Jython_Console_useReflection.updateConfigurationForFutureRuns(jythonPreferencesData);

    /* let restart handle this */
    //atz_Jython_Thread.terminateJythonInterpreter();
    //startupThreadAndConsole();

    /* save the data modifications */
    //generateSimulationData_LAMMPS
    //    = (application_GenerateSimulationData_LAMMPS) jDialog_Generate_Simulation_Data_LAMMPS.getDataValue();

    }//GEN-LAST:event_jMenuItem_PreferencesActionPerformed

    private void jButton_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ResetActionPerformed
    
      restartJythonEditor();
            
      //startupThreadAndConsole();
    }//GEN-LAST:event_jButton_ResetActionPerformed

    private void jButton_RunScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RunScriptActionPerformed

      Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;

      /* Use File chooser to select python script */
      JFileChooser jFileChooser = new JFileChooser();

      jFileChooser.setDialogTitle("Run Script");
      jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
      jFileChooser.resetChoosableFileFilters();
      jFileChooser.setFileFilter(new Atz_FileFilter(".py", "Python Files"));

      if (lastDirectorySelected != null) {
        jFileChooser.setCurrentDirectory(lastDirectorySelected);

      }

      int returnVal = jFileChooser.showDialog(this, "Open");

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();

        lastDirectorySelected = file;

        //System.out.println("File select was:" + file.getAbsolutePath());

        String moduleName = file.getName();
        String filePath = file.getPath();

        moduleName = moduleName.replace(".py", ""); 
        
        String OperatingSystem = System.getProperty("os.name");
        if (OperatingSystem.toLowerCase().contains("win")) { /* For Windows modify path for Jython */
          moduleName = moduleName.replace("\\", "\\\\");  /* for windows names */
          filePath   = filePath.replace("\\", "\\\\");  /* for windows names */ 
        }

        /* Open all of the data to a project file */

        /* Import the module */
        atz_Jython_JTextPane.setCaretPosition(atz_Jython_JTextPane.getLastOutputCursorLoc());

        /*
        atz_Jython_JTextPane.appendTextAtEnd("import imp; "
                                            + moduleName
                                            + " = imp.load_source('" + moduleName + "', '" + filePath + "') \n");
         */        
        atz_Jython_JTextPane.appendTextAtEnd("import os;" + "execfile_script = '" + filePath + "';" + "execfile(execfile_script); \n");

        atz_Jython_JTextPane.setLastInputCursorLoc(atz_Jython_JTextPane.getCaretPosition());

        //foo = imp.load_source('module.name', '/path/to/file.py')


        atz_Jython_Thread.inputBuffer.fireInputEntered();

      }

    }//GEN-LAST:event_jButton_RunScriptActionPerformed

    private void jMenuItem_FontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_FontActionPerformed
   
      /* attempt to show currently used font */
      Atz_Jython_JTextPane atz_Jython_JTextPane = (Atz_Jython_JTextPane) jTextPane_Jython_Console;

      String fontData_fontFamily_default = "DialogInput";
      int fontData_fontSize_default = 12;
      boolean fontData_fontBold_default = true;

      String fontData_fontFamily;
      int fontData_fontSize;
      boolean fontData_fontBold;

      /* use preference values */
      Preferences prefs   = Preferences.userNodeForPackage(Atz_Jython_JTextPane.class);
      fontData_fontFamily = prefs.get("Atz_Jython_JTextPane.FontFamily", fontData_fontFamily_default);
      fontData_fontSize   = Integer.parseInt(prefs.get("Atz_Jython_JTextPane.FontSize", Integer.toString(fontData_fontSize_default)));
      fontData_fontBold   = Boolean.parseBoolean(prefs.get("Atz_Jython_JTextPane.FontBold", Boolean.toString(fontData_fontBold_default)));
      
      /* -- Show font selection dialog -- */
      JDialog_FontSelector jDialog_FontSelector = new JDialog_FontSelector(null);
           
      jDialog_FontSelector.attempToSelectFont(fontData_fontFamily, fontData_fontSize, fontData_fontBold);
      jDialog_FontSelector.setVisible(true);
            
      /* update the jython data */
      //jythonPreferencesData = (HashMap) jDialog_preferences.getDataValue();

      if (jDialog_FontSelector.wasFontSelected()) {  /* set the new preferences */
        /* setup the data using the preferences */
        // Set the user preferences for the Jython JAR distribution, paths, startup scripts, etc...        
        prefs.put("Atz_Jython_JTextPane.FontFamily", jDialog_FontSelector.getSelectedName());
        prefs.put("Atz_Jython_JTextPane.FontSize", Integer.toString(jDialog_FontSelector.getSelectedSize()));
        prefs.put("Atz_Jython_JTextPane.FontBold", Boolean.toString(jDialog_FontSelector.getSelectedBold()));
      
        atz_Jython_JTextPane.setStylesFromPrefs();
      }

      //Atz_Jython_Console_useReflection.updateConfigurationForFutureRuns(jythonPreferencesData);

    }//GEN-LAST:event_jMenuItem_FontActionPerformed


  /**
   * Restarts the Jython kernal clearing all reference to internal memory of state.
   */
  public void restartJythonEditor() {
    atz_Jython_Thread.terminateJythonInterpreter();

    application_SharedData applSharedData = Atz_Application_Data_Communication.getApplSharedData();

    applSharedData.FrameView_Application_Main.recreateJythonEditorWindow();
  }

  /**
   * Empty implementation of the ClipboardOwner interface.
   */
  public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
    //do nothing
  }

  /**
  * Used to record clipboard arguments during a copy command.
  */
  public void setClipboardContents(String aString ){
    StringSelection stringSelection = new StringSelection( aString );
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, this);
  }
  
  /**
   * Get the String residing on the clipboard.
   *
   * @return any text found on the Clipboard; if none found, return an
   * empty String.
   */
  private String getClipboardContents() {
    String result = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //odd: the Object param of getContents is not currently used
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText =
            (contents != null)
            && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
    if (hasTransferableText) {
      try {
        result = (String) contents.getTransferData(DataFlavor.stringFlavor);
      } catch (UnsupportedFlavorException ex) {
        //highly unlikely since we are using a standard DataFlavor
        ex.printStackTrace();
        System.out.println(ex);
        ex.printStackTrace();
      } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println(ex);
        ex.printStackTrace();
      }
    }
    return result;
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Reset;
    private javax.swing.JButton jButton_RunScript;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem_About;
    private javax.swing.JMenuItem jMenuItem_Copy;
    private javax.swing.JMenuItem jMenuItem_Font;
    private javax.swing.JMenuItem jMenuItem_Import;
    private javax.swing.JMenuItem jMenuItem_Paste;
    private javax.swing.JMenuItem jMenuItem_Preferences;
    private javax.swing.JMenuItem jMenuItem_SaveOutput;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu_TextEditor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTextPane jTextPane_Jython_Console;
    // End of variables declaration//GEN-END:variables


  
  private Atz_Jython_Thread atz_Jython_Thread;

  private HashMap jythonPreferencesData;
  
}