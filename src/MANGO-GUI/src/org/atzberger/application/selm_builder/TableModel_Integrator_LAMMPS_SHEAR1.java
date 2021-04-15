package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_Units_Double;
import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.units.Atz_UnitsData;
import org.atzberger.mango.table.TableData_EditorButton;
import java.awt.Color;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Integrator_LAMMPS_SHEAR1 extends TableModel_Integrator {

  private final static boolean DEBUG = false;

  Atz_UnitsRef atz_unitsRef = null;

  public   String paramName_timeStep   = "Time Step";
  public   int    paramIndex_timeStep  = -1;

  public   String paramName_numTimeSteps   = "Number Time Steps";
  public   int    paramIndex_numTimeSteps  = -1;
  
  public   String paramName_shearRate   = "Shear Rate";
  public   int paramIndex_shearRate     = -1;  

  public   String paramName_shearDir    = "Shear Direction";
  public   int paramIndex_shearDir      = -1;
 
  public   String paramName_shearVelDir    = "Shear Velocity Direction";
  public   int paramIndex_shearVelDir      = -1;  

  public   String paramName_saveSkip    = "Save Skip";
  public   int paramIndex_saveSkip      = -1;  

  public TableModel_Integrator_LAMMPS_SHEAR1(SELM_Integrator_LAMMPS_SHEAR1 integrator) {
    setup(integrator, null);
  }
  
  public TableModel_Integrator_LAMMPS_SHEAR1() {
    setup(null, null);
  }

  public TableModel_Integrator_LAMMPS_SHEAR1(Atz_UnitsRef atz_unitsRef_in) {
    setup(null, atz_unitsRef_in);
  }

  public final void setup(SELM_Integrator integrator_in, Atz_UnitsRef atz_unitsRef_in) {
    integrator   = integrator_in;
    atz_unitsRef = atz_unitsRef_in;

    setupFirstThirdColumns();

    setupTableDataPrototypes(); /* sets up types in table for getting/setting */

    if (integrator == null) {
      integrator = new SELM_Integrator_LAMMPS_SHEAR1();
    }

    setFromIntegratorData(integrator);
  }

  public void setupFirstThirdColumns() {

    int i = 0;

    setValueAt(paramName_IntegratorName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_IntegratorName = i;
    i++;

    setValueAt(paramName_IntegratorTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_IntegratorTypeStr = i;
    i++;

    setValueAt(paramName_timeStep, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_timeStep = i;
    i++;

    setValueAt(paramName_numTimeSteps, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_numTimeSteps = i;
    i++;

    setValueAt(paramName_shearRate, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_shearRate = i;
    i++;

    setValueAt(paramName_shearDir, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_shearDir = i;
    i++;

    setValueAt(paramName_shearVelDir, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_shearVelDir = i;
    i++;

    setValueAt(paramName_saveSkip, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_saveSkip = i;
    i++;
    
  }

  @Override
  public void setFromIntegratorData(SELM_Integrator integrator_in) {
    integrator = integrator_in;
    setFromIntegratorData();
  }

  public void setupTableDataPrototypes() {
    int colValue = 1;

    /* set all fields at once and then fire edited event */
    data[paramIndex_IntegratorName][colValue]         = "";
    dataEditable[paramIndex_IntegratorName][colValue] = EDITABLE;

    data[paramIndex_IntegratorTypeStr][colValue]         = "";
    dataEditable[paramIndex_IntegratorTypeStr][colValue] = NOT_EDITABLE;

    data[paramIndex_timeStep][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("[time]", atz_unitsRef));
    dataEditable[paramIndex_timeStep][colValue]    = EDITABLE;

    data[paramIndex_numTimeSteps][colValue]            = (Integer) 0;
    dataEditable[paramIndex_numTimeSteps][colValue]    = EDITABLE;

    data[paramIndex_shearRate][colValue]           = new TableData_Units_Double(0.0, new Atz_UnitsData("[time]^{-1}", atz_unitsRef));
    dataEditable[paramIndex_shearRate][colValue]   = EDITABLE;
    
    data[paramIndex_shearDir][colValue]            = (Integer) 0;
    dataEditable[paramIndex_shearDir][colValue]    = EDITABLE;

    data[paramIndex_shearVelDir][colValue]         = (Integer) 0;
    dataEditable[paramIndex_shearVelDir][colValue] = EDITABLE;

    data[paramIndex_saveSkip][colValue]            = (Integer) 0;
    dataEditable[paramIndex_saveSkip][colValue]    = EDITABLE;

  }

  @Override
  public void setFromIntegratorData() {

    SELM_Integrator_LAMMPS_SHEAR1 integratorData
      = (SELM_Integrator_LAMMPS_SHEAR1)integrator;

    int colValue = 1;

    if (integrator !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_IntegratorName][colValue]         = integratorData.IntegratorName;
      dataEditable[paramIndex_IntegratorName][colValue] = EDITABLE;

      data[paramIndex_IntegratorTypeStr][colValue]         = integratorData.IntegratorTypeStr;
      dataEditable[paramIndex_IntegratorTypeStr][colValue] = NOT_EDITABLE;

      ((TableData_Units_Double)data[paramIndex_timeStep][colValue]).setValue(integratorData.getTimeStep());
      dataEditable[paramIndex_timeStep][colValue] = EDITABLE;

      data[paramIndex_numTimeSteps][colValue]         = (Integer) integratorData.getNumberTimeSteps();
      dataEditable[paramIndex_numTimeSteps][colValue] = EDITABLE;
      
      ((TableData_Units_Double)data[paramIndex_shearRate][colValue]).setValue(integratorData.getShearRate());
      dataEditable[paramIndex_shearRate][colValue] = EDITABLE;

      data[paramIndex_shearDir][colValue]         = (Integer) integratorData.getShearDir();
      dataEditable[paramIndex_shearDir][colValue] = EDITABLE;

      data[paramIndex_shearVelDir][colValue]         = (Integer)integratorData.getShearVelDir();
      dataEditable[paramIndex_shearVelDir][colValue] = EDITABLE;

      data[paramIndex_saveSkip][colValue]         = (Integer)integratorData.getSaveSkip();
      dataEditable[paramIndex_saveSkip][colValue] = EDITABLE;
      
      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

    }
    
  }

  @Override
  public void setIntegratorDataFromModel() {

   int colValue = 1;

   SELM_Integrator_LAMMPS_SHEAR1 integratorData
      = (SELM_Integrator_LAMMPS_SHEAR1)integrator;
    
    integratorData.IntegratorName = (String) getValueAt(paramIndex_IntegratorName,colValue);
    integratorData.setTimeStep(((TableData_Units_Double)getValueAt(paramIndex_timeStep,colValue)).getValue());
    integratorData.setNumberTimeSteps((Integer)getValueAt(paramIndex_numTimeSteps,colValue));
    integratorData.setShearRate(((TableData_Units_Double)getValueAt(paramIndex_shearRate,colValue)).getValue());    
    integratorData.setShearDir((Integer)getValueAt(paramIndex_shearDir,colValue));
    integratorData.setShearVelDir((Integer)getValueAt(paramIndex_shearVelDir,colValue));
    integratorData.setSaveSkip((Integer)getValueAt(paramIndex_saveSkip,colValue));

  }

  @Override
  public SELM_Integrator getIntegratorDataFromModel() {
    setIntegratorDataFromModel(); 
    return super.getIntegratorDataFromModel();
  }
    
}







