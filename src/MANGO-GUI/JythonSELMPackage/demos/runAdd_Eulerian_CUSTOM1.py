import org.atzberger.application.selm_builder
from SELM_Builder import Atz_ClassLoader_RegistryInfo
from SELM_Builder import Atz_ClassLoader
from JPanel_Eulerian_CUSTOM1 import JPanel_Eulerian_CUSTOM1
from Atz_Object_Factory_Eulerian_CUSTOM1 import Atz_Object_Factory_Eulerian_CUSTOM1
from SELM_Eulerian_CUSTOM1 import SELM_Eulerian_CUSTOM1

# Add custom panel
print("=======================================================================");
print("Adding the Eulerian panel CUSTOM1. ");

custom_panel    = JPanel_Eulerian_CUSTOM1();
list_panels     = applSharedData.jPanel_Eulerian_DOF_list;
list_panels.append(custom_panel);
applSharedData.jPanel_Eulerian_DOF_list = list_panels; 

objFactory      = custom_panel.getObjectFactory(); 
new_eulerian  = objFactory.getNewInstance();
eulerian_list = getEulerianListObj();
eulerian_list.append(new_eulerian);
setEulerianListObj(eulerian_list);

refreshEulerianList();

Atz_ClassLoader.addToRegistry("org.atzberger.application.selm_builder.SELM_Eulerian_CUSTOM1", objFactory);
print("=======================================================================");




