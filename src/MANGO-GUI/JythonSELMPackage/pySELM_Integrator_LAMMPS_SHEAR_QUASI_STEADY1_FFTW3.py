from pySELM_Integrator import * 

class pySELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3(pySELM_Integrator):
	
  def __init__(self, integrator_in=None):
  	  
    if (integrator_in==None): # create a new object (if none specified for wrapper)
      import org.atzberger.application.selm_builder
      integrator_in = org.atzberger.application.selm_builder.SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3();
      
    super(pySELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3, self).__init__(integrator_in);

  # getters
  def getTimeStep(self):
    return self.wrappedObj.getTimeStep();
    
  def getNumberTimeSteps(self):
    return self.wrappedObj.getNumberTimeSteps();
        
  def getFluidViscosityMu(self):
    return self.wrappedObj.getFluidViscosityMu();
      
  def getFluidDensityRho(self):
    return self.wrappedObj.getFluidDensityRho();
    
  def getBoltzmannKB(self):
    return self.wrappedObj.getBoltzmannKB();
      
  def getTemperature(self):
    return self.wrappedObj.getTemperatureT();
    
  def getFlagStochasticDriving(self):
    return self.wrappedObj.getFlagStochasticDrivingAsBoolean();

  def getFlagIncompressibleFluid(self):
    return self.wrappedObj.getFlagIncompressibleFluidAsBoolean();
    
  def getFlagShearMode(self):
    return self.wrappedObj.getFlagShearModeStr();
    
  def getShearRate(self):
    return self.wrappedObj.getShearRate();

  def getShearDir(self):
    return self.wrappedObj.getShearDir();
    
  def getShearVelDir(self):
    return self.wrappedObj.getShearVelDir();
    
  def getSaveSkip(self):
    return self.wrappedObj.getSaveSkip();
    
  # setters
  def setTimeStep(self,val_in):
    self.wrappedObj.setTimeStep(val_in);
    
  def setNumberTimeSteps(self,val_in):
    self.wrappedObj.setNumberTimeSteps(val_in);
        
  def setFluidViscosityMu(self,val_in):
    self.wrappedObj.setFluidViscosityMu(val_in);
      
  def setFluidDensityRho(self,val_in):
    self.wrappedObj.setFluidDensityRho(val_in);
    
  def setBoltzmannKB(self,val_in):
    self.wrappedObj.setBoltzmannKB(val_in);
      
  def setTemperature(self,val_in):
    self.wrappedObj.setTemperatureT(val_in);
    
  def setFlagStochasticDriving(self,val_in):
    self.wrappedObj.setFlagStochasticDriving(val_in);

  def setFlagIncompressibleFluid(self,val_in):
    self.wrappedObj.setFlagIncompressibleFluid(val_in);
    
  def setFlagShearMode(self,val_in):
    self.wrappedObj.setFlagShearMode(val_in);
    
  def setShearRate(self,val_in):
    self.wrappedObj.setShearRate(val_in);

  def setShearDir(self,val_in):
    self.wrappedObj.setShearDir(val_in);
    
  def setShearVelDir(self,val_in):
    self.wrappedObj.setShearVelDir(val_in);
    
  def setSaveSkip(self,val_in):
    self.wrappedObj.setSaveSkip(val_in);
      	
