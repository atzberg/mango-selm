import org.atzberger.application.selm_builder
import org.atzberger.application.selm_builder.Atz_Struct_DataChangeListener_Test1
  
DataNamespace

# DataNamespace.addDataChangeListener(L)
L = org.atzberger.application.selm_builder.Atz_Struct_DataChangeListener_Test1()
DataNamespace.addDataChangeListenerAtLabel("MainData.testVal", L)
DataNamespace.setData("MainData.testVal", 10)

val = DataNamespace.getData("MainData.testVal")

val




