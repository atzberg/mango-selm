package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type interface.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_IntegratorInterface_LAMMPS {

  int    getNumberTimeSteps();
  double getTimeStep();

  int    getDumpFreq();
  
}
