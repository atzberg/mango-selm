# -------------------------------------------------------------------------------------------
    
# Attempts to wrap the object in a Jython class to handle manipulations
def getJythonWrapper(obj):
  import re;
  
  flagHandled = 0;
           
  # get the name and attempt to wrap (call below avoid inheritance over-ride)
  className   = obj.__class__.__class__.getName(obj.__class__); 
            
  # handle by special name --> pyWrapper... mappping
  if (className == 'org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1$operatorDataType_NULL'):
    wrapperName = 'pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_NULL'; 
    wrapper     = createJythonWrapper(wrapperName);
    wrapper.setWrappedObj(obj);    
    flagHandled = 1;
    
  if (className == 'org.atzberger.application.selm_builder.SELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1$operatorDataType_T_KERNEL_1'):
    wrapperName = 'pySELM_CouplingOperator_LAMMPS_SHEAR_UNIFORM1_FFTW3_TABLE1_opData_T_KERNEL_1'; 
    wrapper     = createJythonWrapper(wrapperName);
    wrapper.setWrappedObj(obj);
    flagHandled = 1;
       
  # try to handle by a generic method that parses the simple name
  if (flagHandled == 0):
    flagParsed      = 0;
    classSimpleName = obj.__class__.__class__.getSimpleName(obj.__class__); 
    
    SELMprefix  = 'SELM_';    
    regEx       = '(?<=' + SELMprefix + ')';
    matchData   = re.search(regEx, classSimpleName);          
    if matchData is not None:
                    
      regEx       = '(?<=' + SELMprefix + ')[a-zA-Z0-9]+';
      matchData   = re.search(regEx, classSimpleName);  
      categoryStr = matchData.group(0);
  
      regEx       = '(?<=' + SELMprefix + categoryStr + '_)\w+';  
      matchData   = re.search(regEx, classSimpleName);
      typeStr     = matchData.group(0);
      
      wrapperName        = 'py' + SELMprefix + categoryStr + "_" + typeStr;
      wrapperGenericName = 'py' + SELMprefix + categoryStr;
      
      flagParsed  = 1;
      
      #print("Object Category = " + categoryStr);
      #print("Object Type     = " + typeStr);
      
    SELMprefix  = 'Atz_File_Generator';    
    regEx       = '(?<=' + SELMprefix + ')';
    matchData   = re.search(regEx, classSimpleName);          
    if matchData is not None:
                            
      regEx       = '(?<=' + SELMprefix + '_)\w+';  
      matchData   = re.search(regEx, classSimpleName);
      typeStr     = matchData.group(0);
      
      wrapperName        = 'py' + SELMprefix + "_" + typeStr;
      wrapperGenericName = 'py' + SELMprefix;
      
      flagParsed  = 1;
        
      #print("Object Category = " + categoryStr);
      #print("Object Type     = " + typeStr);
  
    if (flagParsed == 1):
      # Try to create wrapper for the specific object type
      try:        
        wrapper     = createJythonWrapper(obj,wrapperName);   
      except Exception, value:
        # Try to create wrapper just for the generic object type
        print("WARNING: jythonWrapper.py : getJythonWrapper(obj):");
        print("Specific object type not recognized attempting to create generic wrapper.");
        print("Full Type    = " + wrapperName);
        print("Generic Type = " + wrapperGenericName);
            
        wrapper     = createJythonWrapper(obj,wrapperGenericName);
    else:
        print("WARNING: jythonWrapper.py : getJythonWrapper(obj):");
        print("Object type was not recognized and no wrapper could be created.");
        wrapper = None;
       
  return wrapper;

# Attempts to wrap the object in a Jython class to handle manipulations
def createJythonWrapper(val1_in = None, val2_in = None):
	
  if (type(val1_in) is str):
	  wrapperName = val1_in;		
	  obj         = None;
  else:
	  obj         = val1_in;
	  wrapperName = val2_in;
	
  commandStr = 'from ' + wrapperName + ' import ' + wrapperName + "\n";
  #print("commandStr     = " + commandStr + "\n");
  exec(commandStr);
  
  commandStr = 'wrapper = ' + wrapperName + '(obj)' + "\n";
  #print("commandStr     = " + commandStr);
  exec(commandStr);

  return wrapper;
  
# Attempts to wrap each object in the specified list
def createJythonWrappedList(list):	  
  wrapperList   = [];
  
  for item in list:
    wrapper = getJythonWrapper(item); 
    wrapperList.append(wrapper);
  
  return wrapperList;
  
# Attempts to wrap each object in the specified list
def createJythonUnwrappedList(list):	  
  unwrappedList   = [];
  
  for itemWrapper in list:     
    unwrappedList.append(itemWrapper.getWrappedObj());
  
  return unwrappedList;  
    
# -------------------------------------------------------------------------------------------
