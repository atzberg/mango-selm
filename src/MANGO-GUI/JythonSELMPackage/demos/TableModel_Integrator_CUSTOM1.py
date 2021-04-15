import org.atzberger.application.selm_builder;
from SELM_Builder import TableModel_Integrator
from SELM_Builder import TableData_EditorButton
from SELM_Builder import TableData_Color
from SELM_Builder import TableModel_Properties1_General
import java.lang.Boolean;

class TableModel_Integrator_CUSTOM1(TableModel_Integrator):
  
  #NOT_EDITABLE = TableModel_Properties1_General.NOT_EDITABLE;
  #EDITABLE     = TableModel_Properties1_General.EDITABLE;
      
  def __init__(self):
    
    super(TableModel_Integrator_CUSTOM1, self).__init__();
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
            
    self.paramName_testVariable            = "Test Variable";
    self.paramIndex_paramName_testVariable = -1;
    
    self.paramName_plotColor               = "Plot Color";
    self.paramIndex_paramName_plotColor    = -1;
    
    self.paramName_flagVisible             = "flagVisible";
    self.paramIndex_paramName_flagVisible  = -1;    
                
    i = 0;

    self.setValueAt(self.paramName_IntegratorName, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_IntegratorName = i;
    i += 1;

    self.setValueAt(self.paramName_IntegratorTypeStr, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_IntegratorTypeStr = i;
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
    

  # public void setFromIntegratorData(SELM_Integrator Integrator_in)  
  def setFromIntegratorData(self, Integrator_in = None):
    self.Integrator = Integrator_in;
#    self.setFromIntegratorData();    
  #public void setFromIntegratorData()
  #def setFromIntegratorData(self):
  
    colValue     = 1;
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
    
    IntegratorData = self.Integrator;
    
    paramIndex_IntegratorName    = self.paramIndex_IntegratorName;
    paramIndex_IntegratorTypeStr = self.paramIndex_IntegratorTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;

    if (IntegratorData != None):

      # set all fields at once and then fire edited event 
      self.data[paramIndex_IntegratorName][colValue]            = IntegratorData.IntegratorName;
      self.dataEditable[paramIndex_IntegratorName][colValue]    = EDITABLE;

      self.data[paramIndex_IntegratorTypeStr][colValue]         = IntegratorData.IntegratorTypeStr;
      self.dataEditable[paramIndex_IntegratorTypeStr][colValue] = NOT_EDITABLE;
          
      self.data[paramIndex_testVariable][colValue]               = java.lang.Double(IntegratorData.testVariable);
      self.dataEditable[paramIndex_testVariable][colValue]       = EDITABLE;          
          
      self.data[paramIndex_plotColor][colValue]                  = TableData_Color(IntegratorData.plotColor);
      self.dataEditable[paramIndex_plotColor][colValue]          = EDITABLE;

      self.data[paramIndex_flagVisible][colValue]                = java.lang.Boolean(IntegratorData.flagVisible);
      self.dataEditable[paramIndex_flagVisible][colValue]        = EDITABLE;

      # notify listeners that all data may have changed 
      super(TableModel_Integrator_CUSTOM1, self).fireTableDataChanged();
        
  #public void setIntegratorDataFromModel()
  def setIntegratorDataFromModel(self):

    colValue = 1;
    
    paramIndex_IntegratorName    = self.paramIndex_IntegratorName;
    paramIndex_IntegratorTypeStr = self.paramIndex_IntegratorTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;
    
    IntegratorData = self.Integrator;
   
    IntegratorData.IntegratorName = self.getValueAt(paramIndex_IntegratorName,colValue);    
    IntegratorData.testVariable    = self.getValueAt(paramIndex_testVariable,colValue);
    tableData                       = self.getValueAt(paramIndex_plotColor, colValue);
    IntegratorData.plotColor       = tableData.color;
    IntegratorData.flagVisible     = self.getValueAt(paramIndex_flagVisible, colValue);
               
  #public SELM_Integrator getIntegratorDataFromModel():
  def getIntegratorDataFromModel(self):
    self.setIntegratorDataFromModel();
    return self.Integrator;
  
