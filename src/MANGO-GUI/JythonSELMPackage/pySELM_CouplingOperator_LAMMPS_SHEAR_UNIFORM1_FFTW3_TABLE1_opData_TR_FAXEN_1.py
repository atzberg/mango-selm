from pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData import *
import org.atzberger.application.selm_builder

class pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_TR_FAXEN_1(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData):
	
  def __init__(self, opData_in=None):
  	  
    if (opData_in==None): # create a new object (if none specified for wrapper)    	    
      opData_in = org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1.operatorDataType_TR_FAXEN_1();
      
    super(pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_TR_FAXEN_1, self).__init__(opData_in);

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

