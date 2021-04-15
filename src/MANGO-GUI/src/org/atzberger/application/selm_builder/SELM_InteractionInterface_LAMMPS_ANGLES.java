package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_InteractionInterface_LAMMPS_ANGLES extends SELM_InteractionInterface_LAMMPS {

  public SELM_LagrangianInterface_LAMMPS[] getAngleListLagrangian1();
  public int[]                             getAngleListI1();

  public SELM_LagrangianInterface_LAMMPS[] getAngleListLagrangian2();
  public int[]                             getAngleListI2();

  public SELM_LagrangianInterface_LAMMPS[] getAngleListLagrangian3();
  public int[]                             getAngleListI3();

  public String getAngleStyle();
  public String getAngleCoeffsStr();
  public int    getAngleTypeID();

}
