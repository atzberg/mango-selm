from pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData import *
import org.atzberger.application.selm_builder

class pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_NULL(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData):
	
  def __init__(self, couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_in=None):
    super(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_NULL, self).__init__(couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_in);

  # getters           
  def getVisible(self):
    return self.wrappedObj.flagVisible;
     
  # setters        
  def setVisible(self, val_in):
    self.wrappedObj.flagVisible = val_in;                
  
