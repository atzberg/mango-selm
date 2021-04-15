from wrappedObject import *
import org.atzberger.application.selm_builder

class pySELM_Integrator(wrappedObject):
	
    def __init__(self, integrator_in=None):    
    	    
        if (integrator_in==None): # create a new object (if none specified for wrapper)        	    
            integrator_in = org.atzberger.application.selm_builder.SELM_Integrator();
    	    
        super(pySELM_Integrator, self).__init__(integrator_in);

    def setIntegratorObj(self, integrator_in):
    	self.setWrappedObj(integrator_in);
    	
    def getIntegratorObj(self):
      return self.getWrappedObj();

    def setName(self, val_in):
      self.wrappedObj.IntegratorName = val_in;

    def getName(self):
      return self.wrappedObj.IntegratorName;

    def setTypeStr(self, val_in):
      self.wrappedObj.IntegratorTypeStr = val_in;

    def getTypeStr(self):
      return self.wrappedObj.IntegratorTypeStr;
      
      
