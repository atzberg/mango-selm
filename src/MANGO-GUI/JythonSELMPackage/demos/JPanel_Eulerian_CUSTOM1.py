import org.atzberger.application.selm_builder;
from SELM_Builder import JPanel_Helper_Eulerian_GenericTable
from Atz_Object_Factory_Eulerian_CUSTOM1 import Atz_Object_Factory_Eulerian_CUSTOM1
from TableModel_Eulerian_CUSTOM1 import TableModel_Eulerian_CUSTOM1

class JPanel_Eulerian_CUSTOM1(JPanel_Helper_Eulerian_GenericTable):
  
  def __init__(self):
    super(JPanel_Eulerian_CUSTOM1, self).__init__();
    
    self.setName('CUSTOM1');
    
    self.atz_Object_Factory = Atz_Object_Factory_Eulerian_CUSTOM1();
    
    self.setTableModel(TableModel_Eulerian_CUSTOM1()); 
  	            
  def getObjectFactory(self):
    return self.atz_Object_Factory;
    
