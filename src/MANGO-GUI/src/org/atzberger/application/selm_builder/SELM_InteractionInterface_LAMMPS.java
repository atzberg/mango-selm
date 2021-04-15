package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_InteractionInterface_LAMMPS {

  public String   getInteractionName(); 
  public void     setFlagGenLAMMPS_XML_Files(boolean val);

}
