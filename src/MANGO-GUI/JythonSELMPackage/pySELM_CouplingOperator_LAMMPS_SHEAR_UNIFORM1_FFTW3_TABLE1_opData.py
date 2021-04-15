from wrappedObject import *
from pySELM_CouplingOperator import *
from pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1 import *
import org.atzberger.application.selm_builder
from jythonWrapper import getJythonWrapper

class pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData(wrappedObject):
	
  def __init__(self, couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_in=None, couplingOpData_in=None):
  	  
    super(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData, self).__init__(couplingOpData_in);
    
    self.setCouplingOperator_Obj(couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_in);

  # getters
  def getCouplingOperator_Obj(self):
    return self.wrappedObj.getCouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1();
    
  def getCouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(self):
    return getJythonWrapper(self.getCouplingOperator_Obj());
            
  def getTypeStr(self):
    return self.wrappedObj.getTypeStr();
    
  # setter    
  def setCouplingOperator_Obj(self, val_in):
  	  
    data_table1 = None;
    
    if type(val_in) is pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1:
      data_table1 = val_in.getWrappedObj();
    if type(val_in) is org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1:
      data_table1 = val_in;
      
    self.wrappedObj.setCouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(data_table1);

  def setCouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(self, val_in):
    setCouplingOperator_Obj(val_in.getWrappedObj());
    
    
