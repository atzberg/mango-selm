from wrappedObject import *
import org.atzberger.application.selm_builder

class pyAtz_File_Generator(wrappedObject):
	
    def __init__(self, atzFileGenerator_in=None):   
    	    
      if (atzFileGenerator_in==None): # create a new object (if none specified for wrapper)        
        atzFileGenerator_in = org.atzberger.application.selm_builder.Atz_File_Generator();
        
      super(pyAtz_File_Generator, self).__init__(atzFileGenerator_in);
        
    def setAtzFileGeneratorObj(self, atzFileGenerator_in):
      self.setWrappedObj(atzFileGenerator_in);
    	
    def getAtzFileGeneratorObj(self):
      return self.getWrappedObj();

    def generateFiles(self):
      self.wrappedObj.generateFiles();

    def generateFilesWithUserInput(self):
      self.wrappedObj.generateFilesWithUserInput();

