import org.atzberger.application.selm_builder;
from SELM_Builder import TableModel_CouplingOperator
from SELM_Builder import TableData_EditorButton
from SELM_Builder import TableData_Color
from SELM_Builder import TableModel_Properties1_General
import java.lang.Boolean;

class TableModel_CouplingOperator_CUSTOM1(TableModel_CouplingOperator):
  
  #NOT_EDITABLE = TableModel_Properties1_General.NOT_EDITABLE;
  #EDITABLE     = TableModel_Properties1_General.EDITABLE;
      
  def __init__(self):
    
    super(TableModel_CouplingOperator_CUSTOM1, self).__init__();
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
            
    self.paramName_testVariable            = "Test Variable";
    self.paramIndex_paramName_testVariable = -1;
    
    self.paramName_plotColor               = "Plot Color";
    self.paramIndex_paramName_plotColor    = -1;
    
    self.paramName_flagVisible             = "flagVisible";
    self.paramIndex_paramName_flagVisible  = -1;    
                
    i = 0;

    self.setValueAt(self.paramName_CouplingOpName, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_CouplingOpName = i;
    i += 1;

    self.setValueAt(self.paramName_CouplingOpTypeStr, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_CouplingOpTypeStr = i;
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
    

  # public void setFromCouplingOperatorData(SELM_CouplingOperator couplingOp_in)  
  def setFromCouplingOpData(self, couplingOp_in = None):
    self.couplingOp = couplingOp_in;
#    self.setFromCouplingOperatorData();    
  #public void setFromCouplingOperatorData()
  #def setFromCouplingOperatorData(self):
  
    colValue     = 1;
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
    
    couplingOpData = self.couplingOp;
    
    paramIndex_CouplingOpName     = self.paramIndex_CouplingOpName;
    paramIndex_CouplingOpTypeStr  = self.paramIndex_CouplingOpTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;

    if (couplingOpData != None):

      # set all fields at once and then fire edited event 
      self.data[paramIndex_CouplingOpName][colValue]             = couplingOpData.CouplingOpName;
      self.dataEditable[paramIndex_CouplingOpName][colValue]     = EDITABLE;

      self.data[paramIndex_CouplingOpTypeStr][colValue]          = couplingOpData.CouplingOpTypeStr;
      self.dataEditable[paramIndex_CouplingOpTypeStr][colValue]  = NOT_EDITABLE;
          
      self.data[paramIndex_testVariable][colValue]               = java.lang.Double(couplingOpData.testVariable);
      self.dataEditable[paramIndex_testVariable][colValue]       = EDITABLE;          
          
      self.data[paramIndex_plotColor][colValue]                  = TableData_Color(couplingOpData.plotColor);
      self.dataEditable[paramIndex_plotColor][colValue]          = EDITABLE;

      self.data[paramIndex_flagVisible][colValue]                = java.lang.Boolean(couplingOpData.flagVisible);
      self.dataEditable[paramIndex_flagVisible][colValue]        = EDITABLE;

      # notify listeners that all data may have changed 
      super(TableModel_CouplingOperator_CUSTOM1, self).fireTableDataChanged();
        
  #public void setCouplingOperatorDataFromModel()
  def setCouplingOpDataFromModel(self):

    colValue = 1;
    
    paramIndex_CouplingOpName    = self.paramIndex_CouplingOpName;
    paramIndex_CouplingOpTypeStr = self.paramIndex_CouplingOpTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;
    
    couplingOpData = self.couplingOp;
   
    couplingOpData.CouplingOpName  = self.getValueAt(paramIndex_CouplingOpName,colValue);    
    couplingOpData.testVariable    = self.getValueAt(paramIndex_testVariable,colValue);
    tableData                      = self.getValueAt(paramIndex_plotColor, colValue);
    couplingOpData.plotColor       = tableData.color;
    couplingOpData.flagVisible     = self.getValueAt(paramIndex_flagVisible, colValue);
               
  #public SELM_CouplingOperator getCouplingOperatorDataFromModel():
  def getCouplingOpDataFromModel(self):
    self.setCouplingOpDataFromModel();
    return self.couplingOp;
  
