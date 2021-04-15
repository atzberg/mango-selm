import org.atzberger.application.selm_builder;
from SELM_Builder import TableModel_Interaction
from SELM_Builder import TableData_EditorButton
from SELM_Builder import TableData_Color
from SELM_Builder import TableModel_Properties1_General
import java.lang.Boolean;

class TableModel_Interaction_CUSTOM1(TableModel_Interaction):
  
  #NOT_EDITABLE = TableModel_Properties1_General.NOT_EDITABLE;
  #EDITABLE     = TableModel_Properties1_General.EDITABLE;
      
  def __init__(self):
    
    super(TableModel_Interaction_CUSTOM1, self).__init__();
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
            
    self.paramName_testVariable            = "Test Variable";
    self.paramIndex_paramName_testVariable = -1;
    
    self.paramName_plotColor               = "Plot Color";
    self.paramIndex_paramName_plotColor    = -1;
    
    self.paramName_flagVisible             = "flagVisible";
    self.paramIndex_paramName_flagVisible  = -1;    
                
    i = 0;

    self.setValueAt(self.paramName_InteractionName, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_InteractionName = i;
    i += 1;

    self.setValueAt(self.paramName_InteractionTypeStr, i,0, NOT_EDITABLE);
    self.setValueAt(TableData_EditorButton(), i,2, EDITABLE);
    self.paramIndex_InteractionTypeStr = i;
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
    

  # public void setFromInteractionData(SELM_Interaction interaction_in)  
  def setFromInteractionData(self, interaction_in = None):
    self.interaction = interaction_in;
#    self.setFromInteractionData();    
  #public void setFromInteractionData()
  #def setFromInteractionData(self):
  
    colValue     = 1;
    
    NOT_EDITABLE = self.NOT_EDITABLE;
    EDITABLE     = self.EDITABLE;
    
    interactionData = self.interaction;
    
    paramIndex_InteractionName    = self.paramIndex_InteractionName;
    paramIndex_InteractionTypeStr = self.paramIndex_InteractionTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;

    if (interactionData != None):

      # set all fields at once and then fire edited event 
      self.data[paramIndex_InteractionName][colValue]            = interactionData.InteractionName;
      self.dataEditable[paramIndex_InteractionName][colValue]    = EDITABLE;

      self.data[paramIndex_InteractionTypeStr][colValue]         = interactionData.InteractionTypeStr;
      self.dataEditable[paramIndex_InteractionTypeStr][colValue] = NOT_EDITABLE;
          
      self.data[paramIndex_testVariable][colValue]               = java.lang.Double(interactionData.testVariable);
      self.dataEditable[paramIndex_testVariable][colValue]       = EDITABLE;          
          
      self.data[paramIndex_plotColor][colValue]                  = TableData_Color(interactionData.plotColor);
      self.dataEditable[paramIndex_plotColor][colValue]          = EDITABLE;

      self.data[paramIndex_flagVisible][colValue]                = java.lang.Boolean(interactionData.flagVisible);
      self.dataEditable[paramIndex_flagVisible][colValue]        = EDITABLE;

      # notify listeners that all data may have changed 
      super(TableModel_Interaction_CUSTOM1, self).fireTableDataChanged();
        
  #public void setInteractionDataFromModel()
  def setInteractionDataFromModel(self):

    colValue = 1;
    
    paramIndex_InteractionName    = self.paramIndex_InteractionName;
    paramIndex_InteractionTypeStr = self.paramIndex_InteractionTypeStr;
    paramIndex_testVariable       = self.paramIndex_testVariable;
    paramIndex_plotColor          = self.paramIndex_plotColor;
    paramIndex_flagVisible        = self.paramIndex_flagVisible;
    
    interactionData = self.interaction;
   
    interactionData.InteractionName = self.getValueAt(paramIndex_InteractionName,colValue);    
    interactionData.testVariable    = self.getValueAt(paramIndex_testVariable,colValue);
    tableData                       = self.getValueAt(paramIndex_plotColor, colValue);
    interactionData.plotColor       = tableData.color;
    interactionData.flagVisible     = self.getValueAt(paramIndex_flagVisible, colValue);
               
  #public SELM_Interaction getInteractionDataFromModel():
  def getInteractionDataFromModel(self):
    self.setInteractionDataFromModel();
    return self.interaction;
  
