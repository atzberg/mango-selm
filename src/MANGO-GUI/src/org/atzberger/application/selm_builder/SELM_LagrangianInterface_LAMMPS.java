package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_LagrangianInterface_LAMMPS {

  public String    get_LAMMPS_TypeStr();  /* gets information about type of LAMMPS particle representation this is */

  public String    getLagrangianName();     
  public String    getLagrangianTypeStr();  

  public int       getNumDim();
  public double[]  getPtsX();
  
  public int[]     getAtomID();
  public int[]     getMoleculeID();
  public int[]     getTypeID();
  public double[]  getAtomMass();

  //Object getDataByField(String fieldName);
  
}
