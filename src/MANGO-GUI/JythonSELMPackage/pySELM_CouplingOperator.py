from wrappedObject import *
import org.atzberger.application.selm_builder

class pySELM_CouplingOperator(wrappedObject):
	
    def __init__(self, couplingOperator_in=None):   

        if (couplingOperator_in==None): # create a new object (if none specified for wrapper)        	    
            couplingOperator_in = org.atzberger.application.selm_builder.SELM_CouplingOperator();
            
        super(pySELM_CouplingOperator, self).__init__(couplingOperator_in);

    def setName(self, val_in):
      self.wrappedObj.CouplingOpName = val_in;

    def getName(self):
      return self.wrappedObj.CouplingOpName;

    def setTypeStr(self, val_in):
      self.wrappedObj.CouplingOpTypeStr = val_in;

    def getTypeStr(self):
      return self.wrappedObj.CouplingOperatorTypeStr;
      
      
