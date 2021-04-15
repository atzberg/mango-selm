import org.atzberger.application.selm_builder
from SELM_Builder import Atz_ClassLoader_RegistryInfo
from SELM_Builder import Atz_ClassLoader
from JPanel_Lagrangian_CUSTOM1 import JPanel_Lagrangian_CUSTOM1
from Atz_Object_Factory_Lagrangian_CUSTOM1 import Atz_Object_Factory_Lagrangian_CUSTOM1

# Add custom panel
print("================================================================================");
print("Adding the lagrangian panel CUSTOM1. ");

custom_panel = JPanel_Lagrangian_CUSTOM1();

# add to the pull-down menu of known types and add the panel to the list
list_edit_panel = applSharedData.FrameView_Application_Main.jPanel_Edit_LagrangianList;

knownTypes      = list_edit_panel.getLagrangianKnownTypes();
knownTypes.append(custom_panel.getName());
 
list = applSharedData.jPanel_Lagrangian_DOF_list;
list.append(custom_panel);
applSharedData.jPanel_Lagrangian_DOF_list = list;

list_edit_panel.setLagrangianKnownTypes(knownTypes);

# add this class and object factory to the registry of classes
objFactory   = Atz_Object_Factory_Lagrangian_CUSTOM1();
#registryInfo = Atz_ClassLoader_RegistryInfo(objFactory);
Atz_ClassLoader.addToRegistry("org.atzberger.application.selm_builder.SELM_Lagrangian_CUSTOM1",objFactory); 

print("Done");
print("================================================================================");

