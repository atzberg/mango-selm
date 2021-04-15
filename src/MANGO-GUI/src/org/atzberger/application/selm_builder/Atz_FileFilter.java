package org.atzberger.application.selm_builder;

import java.io.File;

/**
 *
 * Handles selective display of files when opening and closing files in chooser.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_FileFilter extends javax.swing.filechooser.FileFilter {

  String[] listExtensions  = null;
  String   fileDescription = "";

  public Atz_FileFilter(String[] listExtensions_in, String description_in) {
    listExtensions  = listExtensions_in;
    fileDescription = description_in;
  }

  public Atz_FileFilter(String listExtension_in, String description_in) {
    listExtensions    = new String[1];
    listExtensions[0] = listExtension_in;
    fileDescription   = description_in;
  }

  @Override
  public boolean accept(File file) {

    /* if is directory then accept immediately */
    if (file.isDirectory()) {
    	return true;
    }

    /* check extension is of the correct type */
    boolean flag = false;

    for (int k = 0; k < listExtensions.length; k++) {
      if (file.getAbsolutePath().endsWith(listExtensions[k])) {
        flag = true;
      }
      //flag = flag || (1 == 2);
    }

    // Allow only directories, or files with ".txt" extension
    //return file.isDirectory() || file.getAbsolutePath().endsWith(".SELM_Lagrangian_CONTROL_PTS_BASIC1");

    return flag;

  }

  @Override
  public String getDescription() {

    // This description will be displayed in the dialog,
    // hard-coded = ugly, should be done via I18N
    return fileDescription;
  }

}

