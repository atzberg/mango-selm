from wrappedObject import *
import org.atzberger.application.selm_builder

class pySELM_Interaction(wrappedObject):
	
    def __init__(self, interaction_in=None):
    	    
    	if (interaction_in==None): # create a new object (if none specified for wrapper)        	    
            interaction_in = org.atzberger.application.selm_builder.SELM_Interaction();
    	        	    
    	super(pySELM_Interaction, self).__init__(interaction_in);            

    def setInteractionObj(self, interaction_in):
    	self.setWrappedObj(interaction_in);
    	
    def getInteractionObj(self):
      return self.getWrappedObj();

    def setName(self, val_in):
      self.wrappedObj.InteractionName = val_in;

    def getName(self):
      return self.wrappedObj.InteractionName;

    def setTypeStr(self, val_in):
      self.wrappedObj.InteractionTypeStr = val_in;

    def getTypeStr(self):
      return self.wrappedObj.InteractionTypeStr;
      
      
