import org.atzberger.application.selm_builder;
from SELM_Builder import TableModel_Eulerian
from SELM_Builder import TableData_EditorButton
from SELM_Builder import TableData_Color
from SELM_Builder import TableModel_Properties1_General
import java.lang.Boolean;

class TableModel_Eulerian_CUSTOM1(TableModel_Eulerian):
  
  #NOT_EDITABLE = TableModel_Properties1_General.NOT_EDITABLE;
  #EDITABLE     = TableModel_Properties1_General.EDITABLE;
      
  def __init__(self):
    
    super(TableModel_Eulerian_CUSTOM1, self).__init__();
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
            
    self.paramName_testVariable            = "Test Variable";
    self.paramIndex_paramName_testVariable = -1;
    
    self.paramName_plotColor               = "Plot Color";
    self.paramIndex_paramName_plotColor    = -1;
    
    self.paramName_flagVisible             = "flagVisible";
    self.paramIndex_paramName_flagVisible  = -1;    
                
    i = 0;

    self.setValueAt(self.paramName_EulerianName, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_EulerianName = i;
    i += 1;

    self.setValueAt(self.paramName_EulerianTypeStr, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_EulerianTypeStr = i;
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
    

  # public void setFromeulerianData(SELM_Eulerian Eulerian_in)  
  def setFromeulerianData(self, eulerian_in = None):
    self.eulerian = eulerian_in;
  
    colValue     = 1;
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
    
    eulerianData = self.eulerian;
    
    paramIndex_EulerianName    = self.paramIndex_EulerianName;
    paramIndex_EulerianTypeStr = self.paramIndex_EulerianTypeStr;
    paramIndex_testVariable    = self.paramIndex_testVariable;
    paramIndex_plotColor       = self.paramIndex_plotColor;
    paramIndex_flagVisible     = self.paramIndex_flagVisible;

    if (eulerianData != None):

      # set all fields at once and then fire edited event 
      self.data[paramIndex_EulerianName][colValue]            = eulerianData.EulerianName;
      self.dataEditable[paramIndex_EulerianName][colValue]    = EDITABLE;

      self.data[paramIndex_EulerianTypeStr][colValue]         = eulerianData.EulerianTypeStr;
      self.dataEditable[paramIndex_EulerianTypeStr][colValue] = NOT_EDITABLE;
          
      self.data[paramIndex_testVariable][colValue]            = java.lang.Double(eulerianData.testVariable);
      self.dataEditable[paramIndex_testVariable][colValue]    = EDITABLE;          
          
      self.data[paramIndex_plotColor][colValue]               = TableData_Color(eulerianData.plotColor);
      self.dataEditable[paramIndex_plotColor][colValue]       = EDITABLE;

      self.data[paramIndex_flagVisible][colValue]             = java.lang.Boolean(eulerianData.flagVisible);
      self.dataEditable[paramIndex_flagVisible][colValue]     = EDITABLE;

      # notify listeners that all data may have changed 
      super(TableModel_Eulerian_CUSTOM1, self).fireTableDataChanged();
        
  #public void seteulerianDataFromModel()
  def setEulerianDataFromModel(self):

    colValue = 1;
    
    paramIndex_EulerianName       = self.paramIndex_EulerianName;
    paramIndex_EulerianTypeStr    = self.paramIndex_EulerianTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;
    
    eulerianData = self.eulerian;
   
    eulerianData.EulerianName    = self.getValueAt(paramIndex_EulerianName,colValue);    
    eulerianData.testVariable    = self.getValueAt(paramIndex_testVariable,colValue);
    tableData                    = self.getValueAt(paramIndex_plotColor, colValue);
    eulerianData.plotColor       = tableData.color;
    eulerianData.flagVisible     = self.getValueAt(paramIndex_flagVisible, colValue);
               
  #public SELM_Eulerian geteulerianDataFromModel():
  def getEulerianDataFromModel(self):
    self.setEulerianDataFromModel();
    return self.eulerian;
  
