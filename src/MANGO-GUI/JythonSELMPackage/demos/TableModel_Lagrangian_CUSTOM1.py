import org.atzberger.application.selm_builder;
from SELM_Builder import TableModel_Lagrangian
from SELM_Builder import TableData_EditorButton
from SELM_Builder import TableData_Color
from SELM_Builder import TableModel_Properties1_General
import java.lang.Boolean;

class TableModel_Lagrangian_CUSTOM1(TableModel_Lagrangian):
  
  #NOT_EDITABLE = TableModel_Properties1_General.NOT_EDITABLE;
  #EDITABLE     = TableModel_Properties1_General.EDITABLE;
      
  def __init__(self):
    
    super(TableModel_Lagrangian_CUSTOM1, self).__init__();
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
            
    self.paramName_testVariable            = "Test Variable";
    self.paramIndex_paramName_testVariable = -1;
    
    self.paramName_plotColor               = "Plot Color";
    self.paramIndex_paramName_plotColor    = -1;
    
    self.paramName_flagVisible             = "flagVisible";
    self.paramIndex_paramName_flagVisible  = -1;    
                
    i = 0;

    self.setValueAt(self.paramName_LagrangianName, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_LagrangianName = i;
    i += 1;

    self.setValueAt(self.paramName_LagrangianTypeStr, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_LagrangianTypeStr = i;
    i += 1;
    
    self.setValueAt(self.paramName_testVariable, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_testVariable = i;
    i += 1;

    self.setValueAt(self.paramName_plotColor, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_plotColor = i;
    i += 1;

    self.setValueAt(self.paramName_flagVisible, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_flagVisible = i;
    i += 1;
    

  # public void setFromLagrangianData(SELM_Lagrangian lagrangian_in)  
  def setFromLagrangianData(self, lagrangian_in = None):
    self.lagrangian = lagrangian_in;
  
    colValue     = 1;
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
    
    lagrangianData = self.lagrangian;
    
    paramIndex_LagrangianName     = self.paramIndex_LagrangianName;
    paramIndex_LagrangianTypeStr  = self.paramIndex_LagrangianTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;

    if (lagrangianData != None):

      # set all fields at once and then fire edited event 
      self.data[paramIndex_LagrangianName][colValue]             = lagrangianData.LagrangianName;
      self.dataEditable[paramIndex_LagrangianName][colValue]     = EDITABLE;

      self.data[paramIndex_LagrangianTypeStr][colValue]          = lagrangianData.LagrangianTypeStr;
      self.dataEditable[paramIndex_LagrangianTypeStr][colValue]  = NOT_EDITABLE;
          
      self.data[paramIndex_testVariable][colValue]               = java.lang.Double(lagrangianData.testVariable);
      self.dataEditable[paramIndex_testVariable][colValue]       = EDITABLE;          
          
      self.data[paramIndex_plotColor][colValue]                  = TableData_Color(lagrangianData.plotColor);
      self.dataEditable[paramIndex_plotColor][colValue]          = EDITABLE;

      self.data[paramIndex_flagVisible][colValue]                = java.lang.Boolean(lagrangianData.flagVisible);
      self.dataEditable[paramIndex_flagVisible][colValue]        = EDITABLE;

      # notify listeners that all data may have changed 
      super(TableModel_Lagrangian_CUSTOM1, self).fireTableDataChanged();
        
  #public void setLagrangianDataFromModel()
  def setLagrangianDataFromModel(self):

    colValue = 1;
    
    paramIndex_LagrangianName     = self.paramIndex_LagrangianName;
    paramIndex_LagrangianTypeStr  = self.paramIndex_LagrangianTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;
    
    lagrangianData = self.lagrangian;
   
    lagrangianData.LagrangianName  = self.getValueAt(paramIndex_LagrangianName,colValue);    
    lagrangianData.testVariable    = self.getValueAt(paramIndex_testVariable,colValue);
    tableData                      = self.getValueAt(paramIndex_plotColor, colValue);
    lagrangianData.plotColor       = tableData.color;
    lagrangianData.flagVisible     = self.getValueAt(paramIndex_flagVisible, colValue);
               
  #public SELM_Lagrangian getLagrangianDataFromModel():
  def getLagrangianDataFromModel(self):
    self.setLagrangianDataFromModel();
    return self.lagrangian;
  
