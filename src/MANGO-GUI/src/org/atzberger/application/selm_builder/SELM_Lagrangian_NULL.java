package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.Atz3D_Model;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_Lagrangian_NULL extends SELM_Lagrangian
        implements SELM_LagrangianRenderView {

  /* point related data */
  private final String thisClassSimpleName = this.getClass().getSimpleName();
  
  private int      num_dim   = 3;
  private double[] ptsX      = new double[0];
  private int[]    ptsID     = new int[0];
  
  public SELM_Lagrangian_NULL() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    LagrangianName    = "NULL";
    LagrangianTypeStr = thisClassName.replace(superClassName + "_", "");

  }

  @Override
  public Object clone() {
    SELM_Lagrangian_NULL Lagrangian_copy = new SELM_Lagrangian_NULL();

    Lagrangian_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    Lagrangian_copy.LagrangianName    = this.LagrangianName.toString();
    Lagrangian_copy.LagrangianTypeStr = this.LagrangianTypeStr.toString();
    
    return (Object) Lagrangian_copy;
  }


  @Override
  public void exportData(String filename, int flagType) {

  }

  @Override
  public void importData(String filename, int flagType) {

  }

  @Override
  public String getRenderTag() {
    return atz3D_RENDER_TAG_LAGRANGIAN;
  }

  @Override
  public void renderToModel3D(Atz3D_Model model3D) {

  }
    
}



