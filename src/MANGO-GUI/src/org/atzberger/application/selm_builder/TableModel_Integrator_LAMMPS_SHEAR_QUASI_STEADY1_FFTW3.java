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
public class TableModel_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3 extends TableModel_Integrator {

  private final static boolean DEBUG = false;

  Atz_UnitsRef atz_unitsRef = null;

  public   String paramName_deltaT       = "Time Step";
  public   int    paramIndex_deltaT      = -1;

  public   String paramName_maxNumTimeSteps   = "Number Time Steps";
  public   int    paramIndex_maxNumTimeSteps  = -1;
  
  public   String paramName_mu             = "Fluid Dynamic Viscosity";
  public   int paramIndex_mu               = -1;

  public   String paramName_rho            = "Fluid Density ";
  public   int paramIndex_rho              = -1;

  public   String paramName_KB             = "Boltzmann constant";
  public   int paramIndex_KB               = -1;

  public   String paramName_T              = "Temperature ";
  public   int paramIndex_T                = -1;

  public   String paramName_flagStochasticDriving   = "Flag Stochastic Driving";
  public   int paramIndex_flagStochasticDriving     = -1;

  public   String paramName_flagIncompressibleFluid   = "Flag Incompressible Fluid";
  public   int paramIndex_flagIncompressibleFluid     = -1;

  public   String paramName_flagShearMode   = "Shearing Mode";
  public   int paramIndex_flagShearMode     = -1;

  public   String paramName_shearRate   = "Shear Rate";
  public   int paramIndex_shearRate     = -1;  

  public   String paramName_shearDir    = "Shear Direction";
  public   int paramIndex_shearDir      = -1;
 
  public   String paramName_shearVelDir    = "Shear Velocity Direction";
  public   int paramIndex_shearVelDir      = -1;  

  public   String paramName_saveSkip    = "Save Skip";
  public   int paramIndex_saveSkip      = -1;  

  public TableModel_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3(SELM_Integrator_LAMMPS_SHEAR1 integrator) {
    setup(integrator, null);
  }
  
  public TableModel_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3() {
    setup(null, null);
  }

  public TableModel_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3(Atz_UnitsRef atz_unitsRef_in) {
    setup(null, atz_unitsRef_in);
  }

  public final void setup(SELM_Integrator integrator_in, Atz_UnitsRef atz_unitsRef_in) {
    integrator   = integrator_in;
    atz_unitsRef = atz_unitsRef_in;

    setupFirstThirdColumns();

    setupTableDataPrototypes(); /* sets up types in table for getting/setting */

    if (integrator == null) {
      integrator = new SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3();
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

    setValueAt(paramName_deltaT, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_deltaT = i;
    i++;

    setValueAt(paramName_maxNumTimeSteps, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_maxNumTimeSteps = i;
    i++;

    setValueAt(paramName_mu, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_mu = i;
    i++;

    setValueAt(paramName_rho, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_rho = i;
    i++;

    setValueAt(paramName_KB, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_KB = i;
    i++;

    setValueAt(paramName_T, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_T = i;
    i++;

    setValueAt(paramName_flagStochasticDriving, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagStochasticDriving = i;
    i++;

    setValueAt(paramName_flagIncompressibleFluid, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagIncompressibleFluid = i;
    i++;

    setValueAt(paramName_flagShearMode, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagShearMode = i;
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

    data[paramIndex_deltaT][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("[time]", atz_unitsRef));
    dataEditable[paramIndex_deltaT][colValue]    = EDITABLE;

    data[paramIndex_maxNumTimeSteps][colValue]            = (Integer) 0;
    dataEditable[paramIndex_maxNumTimeSteps][colValue]    = EDITABLE;

    data[paramIndex_mu][colValue]             = new TableData_Units_Double(0.0, new Atz_UnitsData("[mass]/[time]\u00B7[length]", atz_unitsRef));
    dataEditable[paramIndex_mu][colValue]     = EDITABLE;

    data[paramIndex_rho][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("[mass]/[length]^3", atz_unitsRef));
    dataEditable[paramIndex_rho][colValue]    = EDITABLE;

    data[paramIndex_KB][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("[mass]\u00B7[length]^2/[time]^2\u00B7[temperature]", atz_unitsRef));
    dataEditable[paramIndex_KB][colValue]    = EDITABLE;

    data[paramIndex_T][colValue]            = new TableData_Units_Double(0.0, new Atz_UnitsData("[temperature]", atz_unitsRef));
    dataEditable[paramIndex_T][colValue]    = EDITABLE;

    data[paramIndex_flagStochasticDriving][colValue]         = new Boolean("true");
    dataEditable[paramIndex_flagStochasticDriving][colValue] = EDITABLE;

    data[paramIndex_flagIncompressibleFluid][colValue]         = new Boolean("true");
    dataEditable[paramIndex_flagIncompressibleFluid][colValue] = EDITABLE;

    data[paramIndex_flagShearMode][colValue]         = "RM_SHEAR1";
    dataEditable[paramIndex_flagShearMode][colValue] = EDITABLE;

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

    SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3 integratorData
      = (SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3)integrator;

    int colValue = 1;

    if (integrator !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_IntegratorName][colValue]         = integratorData.IntegratorName;
      dataEditable[paramIndex_IntegratorName][colValue] = EDITABLE;

      data[paramIndex_IntegratorTypeStr][colValue]         = integratorData.IntegratorTypeStr;
      dataEditable[paramIndex_IntegratorTypeStr][colValue] = NOT_EDITABLE;

      ((TableData_Units_Double)data[paramIndex_deltaT][colValue]).setValue(integratorData.getTimeStep());
      dataEditable[paramIndex_deltaT][colValue] = EDITABLE;

      data[paramIndex_maxNumTimeSteps][colValue]         = (Integer) integratorData.getNumberTimeSteps();
      dataEditable[paramIndex_maxNumTimeSteps][colValue] = EDITABLE;

      ((TableData_Units_Double)data[paramIndex_mu][colValue]).setValue(integratorData.getFluidViscosityMu());
      dataEditable[paramIndex_mu][colValue] = EDITABLE;

      ((TableData_Units_Double)data[paramIndex_rho][colValue]).setValue(integratorData.getFluidDensityRho());
      dataEditable[paramIndex_rho][colValue] = EDITABLE;

      ((TableData_Units_Double)data[paramIndex_KB][colValue]).setValue(integratorData.getBoltzmannKB());
      dataEditable[paramIndex_KB][colValue] = EDITABLE;

      ((TableData_Units_Double)data[paramIndex_T][colValue]).setValue(integratorData.getTemperatureT());
      dataEditable[paramIndex_T][colValue] = EDITABLE;

      data[paramIndex_flagStochasticDriving][colValue]         = integratorData.getFlagStochasticDrivingAsBoolean();
      dataEditable[paramIndex_flagStochasticDriving][colValue] = EDITABLE;

      data[paramIndex_flagIncompressibleFluid][colValue]         = integratorData.getFlagIncompressibleFluidAsBoolean();
      dataEditable[paramIndex_flagIncompressibleFluid][colValue] = EDITABLE;

      data[paramIndex_flagShearMode][colValue]         = integratorData.getFlagShearModeStr();
      dataEditable[paramIndex_flagShearMode][colValue] = EDITABLE;

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

   SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3 integratorData
      = (SELM_Integrator_LAMMPS_SHEAR_QUASI_STEADY1_FFTW3)integrator;
    
    integratorData.IntegratorName = (String) getValueAt(paramIndex_IntegratorName,colValue);
    integratorData.setTimeStep(((TableData_Units_Double)getValueAt(paramIndex_deltaT,colValue)).getValue());
    integratorData.setNumberTimeSteps((Integer)getValueAt(paramIndex_maxNumTimeSteps,colValue));
    
    integratorData.setFluidViscosityMu(((TableData_Units_Double)getValueAt(paramIndex_mu,colValue)).getValue());
    integratorData.setFluidDensityRho(((TableData_Units_Double)getValueAt(paramIndex_rho,colValue)).getValue());
    integratorData.setBoltzmannKB(((TableData_Units_Double)getValueAt(paramIndex_KB,colValue)).getValue());
    integratorData.setTemperatureT(((TableData_Units_Double)getValueAt(paramIndex_T,colValue)).getValue());
    integratorData.setFlagStochasticDriving((Boolean)getValueAt(paramIndex_flagStochasticDriving,colValue));
    integratorData.setFlagIncompressibleFluid((Boolean)getValueAt(paramIndex_flagIncompressibleFluid,colValue));

    integratorData.setFlagShearMode((String)getValueAt(paramIndex_flagShearMode,colValue));
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







