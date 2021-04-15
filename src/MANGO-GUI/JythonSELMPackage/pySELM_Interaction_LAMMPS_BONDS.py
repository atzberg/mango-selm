from pySELM_Interaction import pySELM_Interaction

from modelBuildPackage1 import *
from jythonWrapper import *
import org.atzberger.application.selm_builder

class pySELM_Interaction_LAMMPS_BONDS(pySELM_Interaction):
	
  def __init__(self, interaction_in=None):
  	  
    if (interaction_in==None): # create a new object (if none specified for wrapper)      
      interaction_in = org.atzberger.application.selm_builder.SELM_Interaction_LAMMPS_BONDS();
      
    super(pySELM_Interaction_LAMMPS_BONDS, self).__init__(interaction_in);

  # getters
  def getBondTypeID(self):
    return self.wrappedObj.getBondTypeID();

  def getBondStyle(self):
    return self.wrappedObj.getBondStyle();

  def getBondCoeffsStr(self):
    return self.wrappedObj.getBondCoeffsStr();
            
  def getPairListTypeI1(self):
    return self.wrappedObj.getPairListTypeI1();
    
  def getPairListTypeI2(self):
    return self.wrappedObj.getPairListTypeI2();    
      
  def getNumPairs(self):
    return self.wrappedObj.getNumPairs();
    
  def getPairList_typeI1(self):
    return self.wrappedObj.getPairList_typeI1();
    
  def getPairList_typeI2(self):
    return self.wrappedObj.getPairList_typeI2();
      
  def getPairList_lagrangianI1(self):        	  
    return createJythonWrappedList(self.wrappedObj.getPairList_lagrangianI1());
    
  def getPairList_lagrangianI2(self):
    return createJythonWrappedList(self.wrappedObj.getPairList_lagrangianI2());
          
  def getVisible(self):
    return self.wrappedObj.flagVisible;
  
  def getPlotColorObj(self):
  	plotColor = self.wrappedObj.getPlotColor();
  	return plotColor;

  def getPlotColor(self):
  	plotColor = self.wrappedObj.getPlotColor();
  	x = [];
  	x.append(plotColor.red);
  	x.append(plotColor.green);
  	x.append(plotColor.blue);
  	return x;
      
  # setters
  def setBondTypeID(self, val_in):
    self.wrappedObj.setBondTypeID(val_in);

  def setBondStyle(self, val_in):
    self.wrappedObj.setBondStyle(val_in);

  def setBondCoeffsStr(self, val_in):
    self.wrappedObj.setBondCoeffsStr(val_in);
         
  def setPairListTypeI1(self, val_in):
    self.wrappedObj.setPairListTypeI1(val_in);
    
  def setPairListTypeI2(self, val_in):
    self.wrappedObj.setPairListTypeI2(val_in);
      
  def setPairList_lagrangianI1(self, val_in):      
    self.wrappedObj.setPairList_lagrangianI1(createJythonUnwrappedList(val_in));
    
  def setPairList_lagrangianI2(self, val_in):    
    self.wrappedObj.setPairList_lagrangianI2(createJythonUnwrappedList(val_in));
               
  def setPairData(self, numPairs_in, pairList_lagrangianI1_in, pairList_typeI1_in, pairList_lagrangianI2_in, pairList_typeI2_in):
    self.wrappedObj.setPairData(numPairs_in, createJythonUnwrappedList(pairList_lagrangianI1_in), pairList_typeI1_in, createJythonUnwrappedList(pairList_lagrangianI2_in), pairList_typeI2_in);    

  def setVisible(self, val_in):
    self.wrappedObj.flagVisible = val_in;
          
  def setPlotColorObj(self, plotColor):
  	self.wrappedObj.setPlotColor(plotColor);
  	
  def setPlotColor(self, r,g,b):
  	import java.awt.Color
  	c = java.awt.Color(r,g,b);
  	self.wrappedObj.setPlotColor(c);
  	
  #def setPlotColor(self, x):
  #	self.setPlotColor(x[0],x[1],x[2])
    
  # misc.   
  def addPair(self, lagrangianI1_in, ptI1_in, lagrangianI2_in, ptI2_in):
    self.wrappedObj.addPair(lagrangianI1_in.getLagrangianObj(), ptI1_in, lagrangianI2_in.getLagrangianObj(), ptI2_in);
    
  def removePair(self, lagrangianI1_in, ptI1_in, lagrangianI2_in, ptI2_in):
    self.wrappedObj.removePair(lagrangianI1_in.getLagrangianObj(), ptI1_in, lagrangianI2_in.getLagrangianObj(), ptI2_in);

