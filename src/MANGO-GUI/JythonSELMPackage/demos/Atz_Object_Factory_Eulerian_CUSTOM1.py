import org.atzberger.application.selm_builder;
from SELM_Builder import Atz_Object_Factory;
from SELM_Eulerian_CUSTOM1 import SELM_Eulerian_CUSTOM1;

class Atz_Object_Factory_Eulerian_CUSTOM1(Atz_Object_Factory):
  
  def __init__(self):
    # nothing to do here
    testVariable = 1.0;

  def getNewInstance(self):
    return SELM_Eulerian_CUSTOM1();
    

