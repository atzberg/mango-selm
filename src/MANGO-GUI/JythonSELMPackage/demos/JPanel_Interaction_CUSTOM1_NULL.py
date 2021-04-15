import org.atzberger.application.selm_builder;

JPanel_Interaction_CUSTOM1(org.atzberger.application.selm_builder.JPanel_Interaction):
  
  def __init__(self):
    
    self.atz_Object_Instantiator = Atz_Object_Instantiator_Interaction_CUSTOM1();
  	  
    #if (lagrangian_in==None): # create a new object (if none specified for wrapper)      
    #  lagrangian_in = org.atzberger.application.selm_builder.SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE();
      
    #super(pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE, self).__init__(lagrangian_in);

  # getters
  def setData(self,interactionData):
    self.interactionData = interactionData;
    
  def getData(self,interactionData):
    return interactionData;
    
  def getObjectInstantiator(self):
    return self.atz_Object_Instantiator;
    
