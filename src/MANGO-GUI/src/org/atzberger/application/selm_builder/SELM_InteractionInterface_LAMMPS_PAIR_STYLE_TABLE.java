package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_InteractionInterface_LAMMPS_PAIR_STYLE_TABLE extends SELM_InteractionInterface_LAMMPS_PAIR_STYLE {  
  public String   getTableFilename();  
  public String   getEnergyEntryName();
  public double[] getCoefficient();
}
