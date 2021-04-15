from pySELM_Eulerian import * 

class pySELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3(pySELM_Eulerian):
	
  def __init__(self, eulerian_in=None):
  	  
    if (eulerian_in==None): # create a new object (if none specified for wrapper)
      import org.atzberger.application.selm_builder
      eulerian_in = org.atzberger.application.selm_builder.SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3();
      
    super(pySELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3, self).__init__(eulerian_in);

  # getters
  def getMeshDeltaX(self):
    return self.wrappedObj.getMeshDeltaX();
    
  def getNumMeshPtsPerDir(self):
    return self.wrappedObj.getNumMeshPtsPerDir();
        
  def getMeshCenterX0(self):
    return self.wrappedObj.getMeshCenterX0();
    
  def getVisible(self):
    return self.wrappedObj.flagVisible;
      
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
      
  def getFlagWriteFluidVelocityVTK(self):
    return self.wrappedObj.getFlagWriteFluidVel_VTK();
    
  def getFlagWriteFluidPressureVTK(self):
    return self.wrappedObj.getFlagWriteFluidPressure_VTK();
      
  def getFlagWriteFluidForceVTK(self):
    return self.wrappedObj.getFlagWriteFluidForce_VTK();
         
  # setters
  def setMeshDeltaX(self, val_in):
    self.wrappedObj.setMeshDeltaX(val_in);
      
  def setNumMeshPtsPerDir(self, val_in):
    self.wrappedObj.setNumMeshPtsPerDir(val_in);
    
  def setMeshCenterX0(self, val_in):
    self.wrappedObj.setMeshCenterX0(val_in);
    
  def setVisible(self, val_in):
    self.wrappedObj.flagVisible = val_in;

  def setPlotColorObj(self, plotColor):
    self.wrappedObj.setPlotColor(plotColor);
  	
  def setPlotColor(self, r,g,b):
    import java.awt.Color
    c = java.awt.Color(r,g,b);
    self.wrappedObj.setPlotColor(c);
      
  def setFlagWriteFluidVelocityVTK(self, val_in):
    self.wrappedObj.setFlagWriteFluidVel_VTK(val_in);
    
  def setFlagWriteFluidPressueVTK(self, val_in):
    self.wrappedObj.setFlagWriteFluidPressure_VTK(val_in);

  def setFlagWriteFluidForceVTK(self, val_in):
    self.wrappedObj.setFlagWriteFluidForce_VTK(val_in);

