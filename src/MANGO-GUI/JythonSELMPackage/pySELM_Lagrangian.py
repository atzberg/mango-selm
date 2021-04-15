from wrappedObject import *
import org.atzberger.application.selm_builder

class pySELM_Lagrangian(wrappedObject):
	
    def __init__(self, lagrangian_in=None):   
    	    
      #print("executing pySELM_Lagrangian init()");
      if (lagrangian_in==None): # create a new object (if none specified for wrapper)        
        lagrangian_in = org.atzberger.application.selm_builder.SELM_Lagrangian();
        
      super(pySELM_Lagrangian, self).__init__(lagrangian_in);
        
    def setLagrangianObj(self, lagrangian_in):
      self.setWrappedObj(lagrangian_in);
    	
    def getLagrangianObj(self):
      return self.getWrappedObj();

    def setName(self, val_in):
      self.wrappedObj.LagrangianName = val_in;

    def getName(self):
      return self.wrappedObj.LagrangianName;

    def setTypeStr(self, val_in):
      self.wrappedObj.LagrangianTypeStr = val_in;

    def getTypeStr(self):
      return self.wrappedObj.LagrangianTypeStr;

