import org.atzberger.application.selm_builder;
from SELM_Builder import JPanel_Helper_Interaction_GenericTable
from Atz_Object_Factory_Interaction_CUSTOM1 import Atz_Object_Factory_Interaction_CUSTOM1
from TableModel_Interaction_CUSTOM1 import TableModel_Interaction_CUSTOM1

class JPanel_Interaction_CUSTOM1(JPanel_Helper_Interaction_GenericTable):
  
  def __init__(self):
    super(JPanel_Interaction_CUSTOM1, self).__init__();
    
    self.setName('CUSTOM1');
    
    self.atz_Object_Factory = Atz_Object_Factory_Interaction_CUSTOM1();
    
    self.setTableModel(TableModel_Interaction_CUSTOM1()); 
  	            
  def getObjectFactory(self):
    return self.atz_Object_Factory;
    
