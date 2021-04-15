package org.atzberger.application.selm_builder;

import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Color;
import java.awt.Color;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 extends TableModel_Eulerian {

  private final static boolean DEBUG = false;

  Atz_UnitsRef     atz_unitsRef = null;

  public   String  paramName_meshDeltaX   = "MeshDeltaX";
  public   int     paramIndex_meshDeltaX  = -1;

  public   String paramName_numMeshPtsPerDir    = "NumMeshPtsPerDir";
  public   int    paramIndex_numMeshPtsPerDir   = -1;

  public   String  paramName_meshCenterX0   = "MeshCenterX0";
  public   int     paramIndex_meshCenterX0  = -1;

  public   String  paramName_flagVisible   = "Visible";
  public   int     paramIndex_flagVisible  = -1;

  public   String  paramName_plotColor   = "Plot Color";
  public   int     paramIndex_plotColor  = -1;

  public   String  paramName_flagWriteFluidVel_VTK   = "Write Fluid Velocity VTK File";
  public   int     paramIndex_flagWriteFluidVel_VTK  = -1;

  public   String  paramName_flagWriteFluidForce_VTK   = "Write Fluid Force VTK File";
  public   int     paramIndex_flagWriteFluidForce_VTK  = -1;

  public   String  paramName_flagWriteFluidPressure_VTK   = "Write Fluid Pressure VTK File";
  public   int     paramIndex_flagWriteFluidPressure_VTK  = -1;

  public TableModel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3(SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 eulerian) {

    setup(eulerian, null);

    setupFirstThirdColumns();

    setFromEulerianData(eulerian);
  }
  
  public TableModel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3() {
    setupFirstThirdColumns();

    SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 eulerian = new SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3();
    
    setFromEulerianData(eulerian);
  }


  public TableModel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3(Atz_UnitsRef atz_unitsRef_in) {
    setup(null, atz_unitsRef_in);
  }

  public final void setup(SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 eulerian_in, Atz_UnitsRef atz_unitsRef_in) {
    
    atz_unitsRef = atz_unitsRef_in;
    eulerian     = eulerian_in;

    setupFirstThirdColumns();

    if (eulerian == null) {
      eulerian = new SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3();
    }

    setFromEulerianData(eulerian);

  }

  public void setupFirstThirdColumns() {

    int i = 0;

    setValueAt(paramName_EulerianName, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_EulerianName = i;
    i++;

    setValueAt(paramName_EulerianTypeStr, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_EulerianTypeStr = i;
    i++;

    setValueAt(paramName_meshDeltaX, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_meshDeltaX = i;
    i++;

    setValueAt(paramName_numMeshPtsPerDir, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_numMeshPtsPerDir = i;
    i++;

    setValueAt(paramName_meshCenterX0, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_meshCenterX0 = i;
    i++;

    setValueAt(paramName_plotColor, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_plotColor = i;
    i++;

    setValueAt(paramName_flagVisible, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagVisible = i;
    i++;
    
    setValueAt(paramName_flagWriteFluidVel_VTK, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagWriteFluidVel_VTK = i;
    i++;
    
    setValueAt(paramName_flagWriteFluidPressure_VTK, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagWriteFluidPressure_VTK = i;
    i++;

    setValueAt(paramName_flagWriteFluidForce_VTK, i,0, NOT_EDITABLE);
    setValueAt(new TableData_EditorButton(), i,2, EDITABLE);
    paramIndex_flagWriteFluidForce_VTK = i;
    i++;
    
  }

  @Override
  public void setFromEulerianData(SELM_Eulerian eulerian_in) {
    eulerian = eulerian_in;
    setFromEulerianData();
  }

  @Override
  public void setFromEulerianData() {

    SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 eulerianData
      = (SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3)eulerian;

    int colValue = 1;

    if (eulerian !=null) {

      /* set all fields at once and then fire edited event */
      data[paramIndex_EulerianName][colValue]         = eulerianData.EulerianName;
      dataEditable[paramIndex_EulerianName][colValue] = EDITABLE;

      data[paramIndex_EulerianTypeStr][colValue]         = eulerianData.EulerianTypeStr;
      dataEditable[paramIndex_EulerianTypeStr][colValue] = NOT_EDITABLE;

      data[paramIndex_meshDeltaX][colValue]         = eulerianData.getMeshDeltaX();
      dataEditable[paramIndex_meshDeltaX][colValue] = EDITABLE;

      data[paramIndex_numMeshPtsPerDir][colValue]         = eulerianData.getNumMeshPtsPerDir();
      dataEditable[paramIndex_numMeshPtsPerDir][colValue] = EDITABLE;

      data[paramIndex_meshCenterX0][colValue]         = eulerianData.getMeshCenterX0();
      dataEditable[paramIndex_meshCenterX0][colValue] = EDITABLE;

      data[paramIndex_plotColor][colValue]         = new TableData_Color(eulerianData.getPlotColor());
      dataEditable[paramIndex_plotColor][colValue] = EDITABLE;

      data[paramIndex_flagVisible][colValue]         = (Boolean) eulerianData.isVisible();
      dataEditable[paramIndex_flagVisible][colValue] = EDITABLE;
      
      data[paramIndex_flagWriteFluidVel_VTK][colValue]              = (Boolean) eulerianData.getFlagWriteFluidVel_VTK();
      dataEditable[paramIndex_flagWriteFluidVel_VTK][colValue]      = EDITABLE;
      
      data[paramIndex_flagWriteFluidPressure_VTK][colValue]         = (Boolean) eulerianData.getFlagWriteFluidPressure_VTK();
      dataEditable[paramIndex_flagWriteFluidPressure_VTK][colValue] = EDITABLE;

      data[paramIndex_flagWriteFluidForce_VTK][colValue]            = (Boolean) eulerianData.getFlagWriteFluidForce_VTK();
      dataEditable[paramIndex_flagWriteFluidForce_VTK][colValue]    = EDITABLE;

      /* notify listeners that all data may have changed */
      this.fireTableDataChanged();

    }
    
  }

  @Override
  public void setEulerianDataFromModel() {

   int colValue = 1;

   SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 eulerianData
      = (SELM_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3)eulerian;

    eulerian.EulerianName = (String) getValueAt(paramIndex_EulerianName,colValue);
    Object obj = getValueAt(paramIndex_meshDeltaX,colValue);
    if (String.class.isInstance(obj)) {
      eulerianData.setMeshDeltaX(Double.parseDouble((String)obj));
    } else {
      eulerianData.setMeshDeltaX((Double)obj);
    }        
    eulerianData.setNumMeshPtsPerDir((int[]) getValueAt(paramIndex_numMeshPtsPerDir,colValue));
    eulerianData.setMeshCenterX0((double[]) getValueAt(paramIndex_meshCenterX0,colValue));
    
    eulerianData.setVisible((Boolean) getValueAt(paramIndex_flagVisible,colValue));

    eulerianData.setFlagWriteFluidVel_VTK((Boolean) getValueAt(paramIndex_flagWriteFluidVel_VTK,colValue));
    eulerianData.setFlagWriteFluidPressure_VTK((Boolean) getValueAt(paramIndex_flagWriteFluidPressure_VTK,colValue));
    eulerianData.setFlagWriteFluidForce_VTK((Boolean) getValueAt(paramIndex_flagWriteFluidForce_VTK,colValue));

    TableData_Color data = (TableData_Color) getValueAt(paramIndex_plotColor,colValue);
    eulerianData.setPlotColor(data.color);
    
  }

  @Override
  public SELM_Eulerian getEulerianDataFromModel() {
    setEulerianDataFromModel();
    return super.getEulerianDataFromModel();
  }

    
}
