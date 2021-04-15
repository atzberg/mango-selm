from wrappedObject import *
import org.atzberger.application.selm_builder

class pySELM_Eulerian(wrappedObject):
	
    def __init__(self, eulerian_in=None):
    	    
        if (eulerian_in==None): # create a new object (if none specified for wrapper)        	    
            eulerian_in = org.atzberger.application.selm_builder.SELM_Eulerian();
            
        super(pySELM_Eulerian, self).__init__(eulerian_in);

    def setEulerianObj(self, eulerian_in):
    	self.setWrappedObj(eulerian_in);
    	
    def getEulerianObj(self):
      return self.getWrappedObj();

    def setName(self, val_in):
      self.wrappedObj.EulerianName = val_in;

    def getName(self):
      return self.wrappedObj.EulerianName;

    def setTypeStr(self, val_in):
      self.wrappedObj.EulerianTypeStr = val_in;

    def getTypeStr(self):
      return self.wrappedObj.EulerianTypeStr;
      
      
