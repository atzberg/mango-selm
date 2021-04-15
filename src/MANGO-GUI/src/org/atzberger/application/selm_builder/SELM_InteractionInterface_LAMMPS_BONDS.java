package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_InteractionInterface_LAMMPS_BONDS extends SELM_InteractionInterface_LAMMPS {

  public SELM_LagrangianInterface_LAMMPS[] getPairListLagrangian1();
  public int[]                             getPairListI1();

  public SELM_LagrangianInterface_LAMMPS[] getPairListLagrangian2(); 
  public int[]                             getPairListI2();

  public String getBondStyle();
  public String getBondCoeffsStr();
  public int    getBondTypeID();

}
