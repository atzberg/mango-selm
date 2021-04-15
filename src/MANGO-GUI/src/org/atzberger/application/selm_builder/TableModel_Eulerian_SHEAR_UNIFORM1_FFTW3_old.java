package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.mango.table.TableData_Units_Double;
import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.TableData_EditorButton;
import java.io.BufferedWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Eulerian_SHEAR_UNIFORM1_FFTW3_old extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;

  public   String  paramName_meshDeltaX   = "MeshDeltaX";
  public   int     paramIndex_meshDeltaX  = -1;  

  public   String paramName_numMeshPtsPerDir    = "NumMeshPtsPerDir";
  public   int    paramIndex_numMeshPtsPerDir   = -1;  

  public   String  paramName_meshCenterX0   = "MeshCenterX0";
  public   int     paramIndex_meshCenterX0  = -1;  

  application_SharedData applSharedData;

  /* XML */
  protected String     xmlString     = "";
  protected Attributes xmlAttributes = null;

  TableModel_Eulerian_SHEAR_UNIFORM1_FFTW3_old(application_SharedData applSharedData_in) {
    
    int      i                = 0;    
    TableData_Units_Double db  = null;

    applSharedData = applSharedData_in;

    Atz_UnitsRef unitsRef = applSharedData.atz_UnitsRef;

    setValueAt(paramName_meshDeltaX, i,0, NOT_EDITABLE);
    db = new TableData_Units_Double(1.234, "[length]", unitsRef);
    setValueAt(db, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_meshDeltaX = i;
    i++;

    setValueAt(paramName_numMeshPtsPerDir, i,0, NOT_EDITABLE);
    setValueAt(new int[] {8,8,8}, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_numMeshPtsPerDir = i;
    i++;

    setValueAt(paramName_meshCenterX0, i,0, NOT_EDITABLE);
    setValueAt(new double[] {0.0,0.0,0.0}, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_meshCenterX0 = i;
    i++;
    

  }


  public double getMeshDeltaX() {
    int col = 1;

    TableData_Units_Double data = (TableData_Units_Double) getValueAt(paramIndex_meshDeltaX, col);
       
    return data.getValue();
  }

  public int[] getNumMeshPtsPerDir() {
    int col = 1;

    int[] data = (int[]) getValueAt(paramIndex_numMeshPtsPerDir, col);

    return data;
  }

  public double[] getMeshCenterX0() {
    int col = 1;

    double[] data = (double[]) getValueAt(paramIndex_meshCenterX0, col);

    return data;
  }


  public void setMeshDeltaX(double value) {
    int col = 1;

    TableData_Units_Double data = (TableData_Units_Double) getValueAt(paramIndex_meshDeltaX, col);

    data.setValue(value);

    setValueAt(data, paramIndex_meshDeltaX, col);
    
  }

  public void setNumMeshPtsPerDir(int[] valueArray) {
    int col = 1;
     
    setValueAt(valueArray.clone(), paramIndex_numMeshPtsPerDir, col);

  }

  public void setMeshCenterX0(double[] valueArray) {
    int col = 1;

    setValueAt(valueArray.clone(), paramIndex_meshCenterX0, col);

  }


}
