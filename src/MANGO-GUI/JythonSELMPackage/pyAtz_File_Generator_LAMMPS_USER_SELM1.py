from pyAtz_File_Generator import * 
import org.atzberger.application.selm_builder

class pyAtz_File_Generator_LAMMPS_USER_SELM1(pyAtz_File_Generator):
	
  def __init__(self, atzFileGenerator_in=None):
  	  
    if (atzFileGenerator_in==None): # create a new object (if none specified for wrapper)      
      atzFileGenerator_in = org.atzberger.application.selm_builder.Atz_File_Generator_LAMMPS_USER_SELM1();
      
    super(pyAtz_File_Generator_LAMMPS_USER_SELM1, self).__init__(atzFileGenerator_in);

  # getters
  def getPathName(self):        
    return self.wrappedObj.generationValues.get("pathName");
    
  # setters
  def setPathName(self, val_in):
    self.wrappedObj.generationValues.put("pathName", val_in);
    
