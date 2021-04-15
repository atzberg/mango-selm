import org.atzberger.application.selm_builder
from SELM_Builder import Atz_ClassLoader_RegistryInfo
from SELM_Builder import Atz_ClassLoader
from JPanel_Integrator_CUSTOM1 import JPanel_Integrator_CUSTOM1
from Atz_Object_Factory_Integrator_CUSTOM1 import Atz_Object_Factory_Integrator_CUSTOM1
from SELM_Integrator_CUSTOM1 import SELM_Integrator_CUSTOM1

# Add custom panel
print("=======================================================================");
print("Adding the integrator panel CUSTOM1. ");

custom_panel    = JPanel_Integrator_CUSTOM1();
list_panels     = applSharedData.jPanel_Integrator_list;
list_panels.append(custom_panel);
applSharedData.jPanel_Integrator_list = list_panels; 

objFactory      = custom_panel.getObjectFactory(); 
new_integrator  = objFactory.getNewInstance();
integrator_list = getIntegratorListObj();
integrator_list.append(new_integrator);
setIntegratorListObj(integrator_list);

refreshInteractionList();

Atz_ClassLoader.addToRegistry("org.atzberger.application.selm_builder.SELM_Integrator_CUSTOM1", objFactory);
print("=======================================================================");




