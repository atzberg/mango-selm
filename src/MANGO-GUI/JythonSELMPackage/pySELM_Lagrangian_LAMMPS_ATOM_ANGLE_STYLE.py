from pySELM_Lagrangian import * 

class pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE(pySELM_Lagrangian):
	
  def __init__(self, lagrangian_in=None):
  	  
    if (lagrangian_in==None): # create a new object (if none specified for wrapper)
      import org.atzberger.application.selm_builder
      lagrangian_in = org.atzberger.application.selm_builder.SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE();
      
    super(pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE, self).__init__(lagrangian_in);

  # getters
  def getNumPts(self):
    return self.wrappedObj.getNumPts();
    
  def getNumDim(self):
    return self.wrappedObj.getNumDim();
        
  def getPtsX(self):
    return self.wrappedObj.getPtsX();
      
  def getAtomID(self):
    return self.wrappedObj.getAtomID();
    
  def getTypeID(self):
    return self.wrappedObj.getAtomID();
      
  def getAtomMass(self):
    return self.wrappedObj.getAtomMass();
    
  def getMoleculeID(self):
    return self.wrappedObj.getMoleculeID();    

  def getVisible(self):
    return self.wrappedObj.flagVisible;

  def getNumPts(self):
    return self.wrappedObj.getNumPts();
    
  def get_LAMMPS_TypeStr(self):    
  	return self.wrappedObj.getLagrangianObj().get_LAMMPS_TypeStr();

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

  def getFlagWriteVTK(self):
    return self.wrappedObj.getFlagWriteVTK();    

  # setters
  def setPtsX(self, val_in):
    self.wrappedObj.setPtsX(val_in);
      
  def setAtomID(self, val_in):
    self.wrappedObj.setAtomID(val_in);
    
  def setTypeID(self, val_in):
  	  
    if (type(val_in) is int):
      inputVal = [];
      inputVal.append(val_in);
    else:
      inputVal = val_in;
    
    self.wrappedObj.setTypeID(inputVal);
      
  def setAtomMass(self, val_in):
    self.wrappedObj.setAtomMass(val_in);
    
  def setMoleculeID(self, val_in):
    self.wrappedObj.setMoleculeID(val_in);    

  def setVisible(self, val_in):
    self.wrappedObj.flagVisible = val_in;
    
  def setFlagWriteVTK(self, val_in):
    self.wrappedObj.setFlagWriteVTK(val_in);
    
  def setPlotColorObj(self, plotColor):
  	self.wrappedObj.setPlotColor(plotColor);
  	
  def setPlotColor(self, r,g,b):
  	import java.awt.Color
  	c = java.awt.Color(r,g,b);
  	self.wrappedObj.setPlotColor(c);
  	
  #def setPlotColor(self, x):
  #	self.setPlotColor(x[0],x[1],x[2])
    
  # misc.
  def addPoints(self, val_in):
    self.wrappedObj.addPoints(val_in);
    
  def removePoint(self, val_in):
    self.wrappedObj.removePoint(val_in);
    
  def removePoints(self, val_in):
    self.wrappedObj.removePoints(val_in);
    
  def removeAllPoints(self):
    self.wrappedObj.removeAllPoints();
  
  
