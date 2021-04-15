package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_EulerianInterface_LAMMPS {

  public double[] getDomainBox();
  public String[] getDomainBoundaryTypes();
  
}
