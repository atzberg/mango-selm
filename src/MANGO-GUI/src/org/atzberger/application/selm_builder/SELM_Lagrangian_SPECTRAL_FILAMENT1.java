package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.Atz3D_Model;
import java.io.BufferedWriter;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_Lagrangian_SPECTRAL_FILAMENT1 extends SELM_Lagrangian implements SELM_Lagrangian_interface {

  private int      num_dim = 3;
  private double[] ptsX    = new double[0];
  private int[]    ptsID   = new int[0];

  public void setPtsX(double[] ptsX_in) {
    ptsX = ptsX_in.clone();
  }

  public void setPtsID(int[] ptsID_in) {
    ptsID = ptsID_in.clone();
  }

  public double[] getPtsX() {
    return ptsX;
  }

  public int[] getPtsID() {
    return ptsID;
  }


  public SELM_Lagrangian_SPECTRAL_FILAMENT1() {
    super();

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    LagrangianName    = "Your Name Here";
    LagrangianTypeStr = thisClassName.replace(superClassName + "_", "");

  }

  @Override
  public Object clone() {
    SELM_Lagrangian_SPECTRAL_FILAMENT1 Lagrangian_copy = new SELM_Lagrangian_SPECTRAL_FILAMENT1();

    Lagrangian_copy.LagrangianName    = this.LagrangianName.toString();
    Lagrangian_copy.LagrangianTypeStr = this.LagrangianTypeStr.toString();

    return (Object) Lagrangian_copy;
  }

  
  @Override
  public void renderToModel3D(Atz3D_Model model3D) {

  }

  public void importData(String filename, int fileType) {

  }

  public void exportData(String filename, int fileType) {

  }

  public void exportData(BufferedWriter fid) {

  }

  
}
