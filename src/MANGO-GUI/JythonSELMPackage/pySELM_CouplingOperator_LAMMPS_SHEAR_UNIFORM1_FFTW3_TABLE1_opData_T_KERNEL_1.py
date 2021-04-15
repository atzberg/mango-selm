from wrappedObject import *
from pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData import *
import org.atzberger.application.selm_builder

class pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_T_KERNEL_1(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData):

  def __init__(self, couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1=None, couplingOpData_in=None):

    if (couplingOpData_in == None): # create a new object (if none specified for wrapper)
    	    
      data_table1 = None;
      
      if type(couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1) is pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1:
        data_table1 = couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.getWrappedObj();
      if type(couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1) is org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1:
      	data_table1 = couplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1;

      couplingOpData_in = org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_T_KERNEL_1(data_table1, data_table1);
      
    super(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_T_KERNEL_1, self).__init__(data_table1, couplingOpData_in);

  # getters
  def getWeightTableFilename(self):
    return self.wrappedObj.weightTableFilename;
    
  def getPlotColorObj(self):
    plotColor = self.wrappedObj.getPlotColor();
    return plotColor;
  	
  def getPlotColor(self):
    plotColor = self.wrappedObj.getPlotColor();
    x = [];
    x.append(plotColor.red);
    x.append(plotColor.green);
    x.append(plotColor.blue);
    return x;  	
                  
  # setters
  def setWeightTableFilename(self, val_in):
    self.wrappedObj.weightTableFilename = val_in;
      
  def setPlotColorObj(self, plotColor):
    self.wrappedObj.setPlotColor(plotColor);
  	
  def setPlotColor(self, r,g,b):
    import java.awt.Color
    c = java.awt.Color(r,g,b);
    self.wrappedObj.setPlotColor(c);
    
