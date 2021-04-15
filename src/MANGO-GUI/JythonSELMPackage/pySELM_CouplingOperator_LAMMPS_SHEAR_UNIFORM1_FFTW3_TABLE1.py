from pySELM_CouplingOperator import * 
import org.atzberger.application.selm_builder
from jythonWrapper import getJythonWrapper

class pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1(pySELM_CouplingOperator):
	
  def __init__(self, val1_in = None, val2_in = None):
      
    if (type(val1_in) is str):
      opTypeStr       = val1_in;
      
      couplingOp_in   = org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1();
      
      if (opTypeStr == "T_KERNEL_1"):
        operatorData_in = org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_T_KERNEL_1(couplingOp_in, couplingOp_in);        
                
    else:
      couplingOp_in   = val1_in; 
      operatorData_in = val2_in;
      
      if (couplingOp_in==None): # create a new object (if none specified for wrapper)      
        couplingOp_in = org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1();
      
      if (operatorData_in == None): # create a new object (if none specified for wrapper)
        operatorData_in = couplingOp_in.getOperatorData();
  	                                                           
    # initialize             
    couplingOp_in.setOperatorData(operatorData_in);
    
    super(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1, self).__init__(couplingOp_in);

  # getters
  def getOperatorDataObj(self):
    return self.wrappedObj.getOperatorData();
    
  def getOperatorData(self):
    opData = getJythonWrapper(self.getOperatorDataObj());
    opData.setCouplingOperator_Obj(self.getWrappedObj());
    return opData;
             
  def getVisible(self):
    return self.wrappedObj.flagVisible;
     
  # setters
  def setOperatorDataObj(self, val_in):
    self.wrappedObj.setOperatorData(val_in);
    
  def setOperatorData(self, val_in):
    self.wrappedObj.setOperatorDataObj(val_in.getWrappedObj());
      
  def setVisible(self, val_in):
    self.wrappedObj.flagVisible = val_in;                
  
  
