#
# Package for helping to build SELM Models using the SELM Builder GUI  
#
# Author: Paul J. Atzberger
#
#
# To Do:
#    * Create / Add Class or Find Each of the following data types 
#        - Lagrangian, Eulerian, Coupling Op, Interaction, Integrator.
#
#    * Wrappers for specific Lagrangian classes to add and remove points
#
#    * Wrappers for setting up and editing interactions between Lagrangian DOF's. 
#
#    * Wrappers for setting up other aspects of the simulations.
#
#    * Can start simulation codes running or script for this by calling shell command
#      (Linux : use "from subprocess import call" and "call(["jedit","tmpTest.txt"])".
#
#    * Add command to "refresh the GUI" (similar to Paraview)  (perhaps refreshGUI() )
#      or Render... 
#    
#    * May want to have "pulse" for the GUI where refreshes occur periodically, say every
#      couple of seconds...


# ================== Import script =========================
print("Model Build Package 1 : Authored by Paul J. Atzberger : Version 1.0");
#print("Setting up the module");


# -- Determine path of current script and add to the path
import __main__  # used to add imports to global namespace
from __main__ import applSharedData
import java.lang.String;
import org.atzberger.application.selm_builder
import sys
import os
import re

currentScript = sys.argv[0];
pathname      = os.path.dirname(sys.argv[0]);

# only add the path if not it is not already there
flagAddPath = 1;
for path in sys.path:
  if (path == pathname):
    flagAddPath = 0;

if (flagAddPath):
  sys.path.append(pathname);
  
pathJythonSELMPackage = pathname;

# list all of the files in the package directory and import wrapper classes
#print("Importing the following wrapper classes:");
listFiles = os.listdir(pathJythonSELMPackage);

for filename in listFiles:
	
  regEx          = '^pySELM_.*\.py$';  # get filenames of the form pySELM_*.py 
  matchData      = re.search(regEx, filename);  
  if (matchData is not None):
    filenameMatch  = matchData.group(0);
    regEx          = '^(pySELM_.*)\.py$';  # get filenames of the form pySELM_*.py      
    matchData      = re.search(regEx, filename);
    if (matchData is not None):
      pyWrapperClass = matchData.group(1);
      #print('  ' + pyWrapperClass);
      commandStr = 'from ' + pyWrapperClass + ' import ' + pyWrapperClass + '\n';
      #print(commandStr);
      exec(commandStr);
      
  regEx          = '^pyAtz_File_Generator.*\.py$';  # get filenames of the form pyAtz_File_Generator_*.py 
  matchData      = re.search(regEx, filename);  
  if (matchData is not None):
    filenameMatch  = matchData.group(0);
    regEx          = '^(pyAtz_File_Generator.*)\.py$';  # get filenames of the form pyAtz_File_Generator_*.py      
    matchData      = re.search(regEx, filename);
    if (matchData is not None):
      pyWrapperClass = matchData.group(1);
      #print('  ' + pyWrapperClass);
      commandStr = 'from ' + pyWrapperClass + ' import ' + pyWrapperClass + '\n';
      #print(commandStr);
      exec(commandStr);

#print("Importing follow modules:");

# -- import known lagrangian types 
#print("pySELM_Lagrangian");
#print("pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE");
#from pySELM_Lagrangian import pySELM_Lagrangian
#from pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE import pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE

# -- import known eulerian types
#from pySELM_Eulerian import pySELM_Eulerian

# -- import known coupling operator types
#from pySELM_CouplingOperator import pySELM_CouplingOperator

# -- import known interaction types
#from pySELM_Interaction import pySELM_Interaction
#from pySELM_Interaction_LAMMPS_PAIR_COEFF import pySELM_Interaction_LAMMPS_PAIR_COEFF
#from pySELM_Interaction_LAMMPS_BONDS import pySELM_Interaction_LAMMPS_BONDS

# -- import known integrator types
#from pySELM_Integrator import pySELM_Integrator

#import pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE
#__main__.pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE = pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE;

import jythonWrapper
from jythonWrapper import getJythonWrapper;
from jythonWrapper import createJythonWrappedList;

# ================== Functions =========================

print("Setup appears to have completed with no known errors.");

# -------------------------------------------------------------------------------------------

# Retrieves Lagrangian Degree of Freedom list
def getLagrangianList():  
  list = getLagrangianListObj();
  
  return createJythonWrappedList(list);
  
# Retrieves Lagrangian Degree of Freedom list
def getLagrangianListObj():
  applSharedData = __main__.applSharedData;   
  list           = applSharedData.jTable_MainData.getLagrangianList();
      
  return list;

# Set Lagrangian List
def setLagrangianListObj(list):     
  applSharedData.jTable_MainData.setLagrangianList(list);  
        
# Retrieves Lagrangian Degree of Freedom by name.
def findLagrangian(name):  
  list          = getLagrangianListObj();
  
  for lagrangian in list:
    lagrangianName = lagrangian.LagrangianName;
    if (name == lagrangianName):
      lagrangianToReturn = lagrangian; 
      
  return getJythonWrapper(lagrangianToReturn);
  
# -------------------------------------------------------------------------------------------  
  
# Retrieves Eulerian List
def getEulerianList():
	
  list = getEulerianListObj();
  
  return createJythonWrappedList(list);
  
# Retrieves Eulerian List
def getEulerianListObj():  
  applSharedData = __main__.applSharedData;   
  
  list          = applSharedData.jTable_MainData.getEulerianList();
  
  return list;

# Set Eulerian List
def setEulerianListObj(list):  
  applSharedData = __main__.applSharedData;   
  applSharedData.jTable_MainData.setEulerianList(list);
       
# Retrieves Eulerian Degree of Freedom by name.
def findEulerian(name): 
  list          = getEulerianListObj();
  
  for interaction in list:
    interactionName = interaction.EulerianName;
    if (name == interactionName):
      interactionToReturn = interaction; 
      
  return getJythonWrapper(interactionToReturn);

# -------------------------------------------------------------------------------------------

# Retrieves CouplingOperator Degree of Freedom list
def getCouplingOperatorList():
	
  list = getCouplingOperatorListObj();
  
  return createJythonWrappedList(list);
  
# Retrieves CouplingOperator Degree of Freedom list
def getCouplingOperatorListObj(): 
  applSharedData = __main__.applSharedData;   
  list          = applSharedData.jTable_MainData.getCouplingOpList();
  
  return list;

# Set CouplingOperator Degree of Freedom list
def setCouplingOperatorListObj(list):  
  applSharedData = __main__.applSharedData;   
  applSharedData.jTable_MainData.setCouplingOpList(list);
          
# Retrieves CouplingOperator Degree of Freedom by name.
def findCouplingOperator(name):  
  list          = getCouplingOperatorListObj();
  
  for couplingOperator in list:
    couplingOpName = couplingOperator.CouplingOpName;
    if (name == couplingOpName):
      couplingOperatorToReturn = couplingOperator; 
      
  return getJythonWrapper(couplingOperatorToReturn);
    
# -------------------------------------------------------------------------------------------    
    
# Retrieves Interaction List
def getInteractionList():
	
  list = getInteractionListObj();
  
  return createJythonWrappedList(list);
  
# Retrieves Interaction List
def getInteractionListObj():
  applSharedData = __main__.applSharedData;       
  list          = applSharedData.jTable_MainData.getInteractionList();
  
  return list;

# Retrieves Interaction List
def setInteractionListObj(list):  
  applSharedData = __main__.applSharedData;   
  applSharedData.jTable_MainData.setInteractionList(list);
            
# Retrieves Interaction Degree of Freedom by name.
def findInteraction(name):  
  list          = getInteractionListObj();
  
  for interaction in list:
    interactionName = interaction.InteractionName;
    if (name == interactionName):
      interactionToReturn = interaction; 
      
  return getJythonWrapper(interactionToReturn);

# -------------------------------------------------------------------------------------------

# Retrieves Integrator  list
def getIntegratorList():
	
  list = getIntegratorListObj();
  
  return createJythonWrappedList(list);
  
# Retrieves Integrator list
def getIntegratorListObj():
  applSharedData = __main__.applSharedData;   
  
  list          = applSharedData.jTable_MainData.getIntegratorList();  
  
  return list;  
  
# Set Integrator List
def setIntegratorListObj(list):
  applSharedData = __main__.applSharedData;   
  applSharedData.jTable_MainData.setIntegratorList(list);
            
# Retrieves Integrator by name.
def findIntegrator(name):  
  list          = getIntegratorListObj();
  
  for integrator in list:
    integratorName = integrator.IntegratorName;
    if (name == integratorName):
      integratorToReturn = integrator; 
      
  return getJythonWrapper(integratorToReturn);
        
# Add item to the list 
def addLagrangianToList(lagrangian):     
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getLagrangianList();
  dataList.append(lagrangian.getWrappedObj());  
  jTable_MainData.setLagrangianDOF(dataList);
  
# Add item to the list 
def addEulerianToList(eulerian):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getEulerianList();
  dataList.append(eulerian.getWrappedObj());  
  jTable_MainData.setEulerianDOF(dataList);  
  
# Add item to the list 
def addCouplingOpToList(couplingOp):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getCouplingOpList();
  dataList.append(couplingOp.getWrappedObj());  
  jTable_MainData.setCouplingOpList(dataList);

# Add item to the list 
def addInteractionToList(interaction):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getInteractionList();
  dataList.append(interaction.getWrappedObj());  
  jTable_MainData.setInteractionList(dataList);
  
# Add item to the list 
def addIntegratorToList(integrator):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getIntegratorList();
  dataList.append(integrator.getWrappedObj());  
  jTable_MainData.setIntegratorList(dataList);
    
# -------------------------------------------------------------------------------------------    
    
# Remove item from the list by name 
def removeLagrangianFromList(name):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getLagrangianList();      
  
  index = -1;
  for lagrangian in dataList:
    index = index + 1;
    lagrangianName = lagrangian.LagrangianName;
    if (name == lagrangianName):
      removeI = index;
      lagrangianToRemove = lagrangian; 
          
  if (index != -1):
    dataList.remove(lagrangianToRemove);
    
  jTable_MainData.setLagrangianDOF(dataList);
  
# Remove item from the list by name 
def removeEulerianFromList(name):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getEulerianList();      
  
  index = -1;
  for eulerian in dataList:
    index = index + 1;
    eulerianName = eulerian.EulerianName;
    if (name == eulerianName):
      removeI = index;
      eulerianToRemove = eulerian; 
          
  if (index != -1):
    dataList.remove(eulerianToRemove);
    
  jTable_MainData.setEulerianDOF(dataList);  

# Remove item from the list by name 
def removeCouplingOpFromList(name):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getCouplingOpList();      
  
  index = -1;
  for couplingOp in dataList:
    index = index + 1;
    couplingOpName = couplingOp.CouplingOpName;
    if (name == couplingOpName):
      removeI = index;
      couplingOpToRemove = couplingOp; 
          
  if (index != -1):
    dataList.remove(couplingOpToRemove);
    
  jTable_MainData.setCouplingOpList(dataList);

# Remove item from the list by name 
def removeInteractionFromList(name):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getInteractionList();      
  
  index = -1;
  for interaction in dataList:
    index = index + 1;
    interactionName = interaction.InteractionName;
    if (name == interactionName):
      removeI = index;
      interactionToRemove = interaction; 
          
  if (index != -1):
    dataList.remove(interactionToRemove);
    
  jTable_MainData.setInteractionList(dataList);
  
# Remove item from the list by name 
def removeIntegratorFromList(name):   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getIntegratorList();      
  
  index = -1;
  for integrator in dataList:
    index = index + 1;
    integratorName = integrator.IntegratorName;
    if (name == integratorName):
      removeI = index;
      integratorToRemove = integrator; 
          
  if (index != -1):
    dataList.remove(integratorToRemove);
    
  jTable_MainData.setIntegratorList(dataList);

# -------------------------------------------------------------------------------------------

# Refresh the List by getting/setting it to trigger listeners. 
def refreshLagrangianList():   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getLagrangianList();    
  jTable_MainData.setLagrangianDOF(dataList);
  
# Refresh the List by getting/setting it to trigger listeners. 
def refreshEulerianList():   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getEulerianList();    
  jTable_MainData.setEulerianDOF(dataList);    
  
# Refresh the List by getting/setting it to trigger listeners. 
def refreshCouplingOpList():   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getCouplingOpList();    
  jTable_MainData.setCouplingOpList(dataList);      

# Refresh the List by getting/setting it to trigger listeners. 
def refreshInteractionList():   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getInteractionList();    
  jTable_MainData.setInteractionList(dataList);  
  
# Refresh the List by getting/setting it to trigger listeners. 
def refreshIntegratorList():   
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  dataList        = jTable_MainData.getIntegratorList();    
  jTable_MainData.setIntegratorList(dataList);
  
# Refresh the Render View 
def refreshRenderView():   
  applSharedData = __main__.applSharedData;   
  applSharedData.jPanel_Model_View_Composite.repaint();    

# -------------------------------------------------------------------------------------------

# Refresh GUI (update the state of the GUI using object data / as if main table updated) 
def refreshGUI():
  refreshLagrangianList();
  refreshEulerianList();
  refreshCouplingOpList();
  refreshInteractionList();
  refreshIntegratorList();
  refreshRenderView();
  
# -------------------------------------------------------------------------------------------
def getEulerianSelected():
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  val             = jTable_MainData.getEulerian_selected();
  
  return val;	
	
def setEulerianSelected(val):	
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  
  if (type(val) is int):
    jTable_MainData.setEulerian_selected(val);
    
  if (type(val) is unicode):
    jTable_MainData.setEulerian_selectedByName(str(val));
    
  if (type(val) is str):
    jTable_MainData.setEulerian_selectedByName(val);
	
def getIntegratorSelected():
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  val             = jTable_MainData.getIntegrator_selected();
  
  return val;	
		
def setIntegratorSelected(val):	
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  
  if (type(val) is int):
    jTable_MainData.setIntegrator_selected(val);
    
  if (type(val) is unicode):
    jTable_MainData.setIntegrator_selectedByName(str(val));
    
  if (type(val) is str):
    jTable_MainData.setIntegrator_selectedByName(val);
  
def getModelDescription():	
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  val             = jTable_MainData.getDescription();
  
  return val;

def setModelDescription(val):	
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  jTable_MainData.setDescription(val);
  
def getModelBaseFilename():	
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  val             = jTable_MainData.getBaseFilename();
  
  return val;
  
def setModelBaseFilename(val):	
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  jTable_MainData.setBaseFilename(val);  
  
def getModelRandomSeed():	
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  val             = jTable_MainData.getRandomSeed();
  
  return val;
  
def setModelRandomSeed(val):
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  jTable_MainData.setRandomSeed(val);  
    
def getModelPhysicalUnits():
  applSharedData = __main__.applSharedData;   
  jTable_MainData = applSharedData.jTable_MainData; 
  return jTable_MainData.getUnitsRef();
   
def setModelPhysicalUnitsVisible(val):
  applSharedData = __main__.applSharedData;   
  unitsRef = getModelPhysicalUnits();
  unitsRef.setVisiblePreferred(val);

# -------------------------------------------------------------------------------------------
def getModelFileGenerator():
  applSharedData = __main__.applSharedData;   
  return getJythonWrapper(applSharedData.atz_File_Generator);
  
def setModelFileGenerator(val):  
  applSharedData = __main__.applSharedData;   
  flagSet = 0;
    
  if isinstance(val, pyAtz_File_Generator):    
    applSharedData.atz_File_Generator = val.wrappedObj;
    flagSet = 1;
    
  if isinstance(val, org.atzberger.application.selm_builder.Atz_File_Generator):
    applSharedData.atz_File_Generator = val;
    flagSet = 1;
    
  if (flagSet == 0):
    print("ERROR: modelBuildPackage1.py : setModelGenerator");
    print("Could not set the file generator.");
    print("Expected set of type pyAtz_File_Generator or Atz_File_Generator.");
    
# -------------------------------------------------------------------------------------------
def openInDockableWindow(jPanel, tabName = None, panelLoc = None):
  
  dockablePanel = None;

  applSharedData = __main__.applSharedData;   
  
  if (tabName == None):
    tabName = jPanel.getName();
    
  if (panelLoc == None):
    panelLoc = "UpperRight";
  
  if (panelLoc == "UpperRight"):
    dockablePanel = applSharedData.FrameView_Application_Main.jTabbedPane_Docksite_Windows_UpperRight;
    
  if (panelLoc == "LowerRight"):
    dockablePanel = applSharedData.FrameView_Application_Main.jTabbedPane_Docksite_Windows_LowerRight;
    
  if (panelLoc == "LowerLeft"):    
    dockablePanel = applSharedData.FrameView_Application_Main.jTabbedPane_Docksite_Windows_LowerLeft;    
    
  applSharedData.FrameView_Application_Main.openInDockableWindowPanel(tabName, jPanel, dockablePanel);
  
# -------------------------------------------------------------------------------------------  
def restartJython():
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;
  mainAppl.restartJythonEditor();
  
def newProject(flagRestartJython = False):
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;  
  mainAppl.project_makeNewProject();
  if (flagRestartJython == True):
    restartJython();
  refreshGUI();
    
def openProject(filename):
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;  
  mainAppl.openNewProject(filename, applSharedData);
  restartJython();
  
def saveProject(filename):    
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;  
  mainAppl.saveCurrentProject(filename, applSharedData);
  
def showOnlineHelp():  
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;  
  mainAppl.showOnlineHelp();

def showOnlineUpdates():
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;  
  mainAppl.showOnlineUpdates();
  
def showOnlineAbout():
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;  
  mainAppl.showOnlineAbout();

def showOnlineHome():
  applSharedData = __main__.applSharedData;   
  mainAppl = applSharedData.FrameView_Application_Main;  
  mainAppl.showOnlineHome();

# -----------------------------------------------------------------------------------------
def getJavaStringPath(pathname):  # convert path in windows to unescaped java.lang.String 
  s          = java.lang.String(pathname);
  s1         = java.lang.String('\\\\');
  s2         = java.lang.String('\\');
  r          = java.lang.String(s.replace(s1,s2));  
  return r;
