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
public class TableModel_Integrator_SELM_SHEAR1_old extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;
   
  public   String paramName_timeStep   = "Time Step";
  public   int    paramIndex_timeStep  = -1;
  public   String paramXMLTag_timeStep = "timeStep";

  public   String paramName_shearRate   = "Shear Rate";
  public   int paramIndex_shearRate     = -1;
  public   String paramXMLTag_shearRate = "shearRate";

  public   String paramName_shearDir    = "Shear Direction";
  public   int paramIndex_shearDir      = -1;
  public   String paramXMLTag_shearDir  = "shearDir";

  public   String paramName_shearVelDir    = "Shear Velocity Direction";
  public   int paramIndex_shearVelDir      = -1;
  public   String paramXMLTag_shearVelDir  = "shearVelDir";

  public   String paramName_saveSkip    = "Save Skip";
  public   int paramIndex_saveSkip      = -1;
  public   String paramXMLTag_saveSkip  = "saveSkip";

  application_SharedData applSharedData;

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  TableModel_Integrator_SELM_SHEAR1_old(application_SharedData applSharedData_in) {

    applSharedData = applSharedData_in;

    int      i                = 0;
    int      valInt = -1;
    TableData_Units_Double db  = null;

    Atz_UnitsRef unitsRef = applSharedData.atz_UnitsRef; /* should get global reference */

    setValueAt(paramName_timeStep, i,0, NOT_EDITABLE);
    db = new TableData_Units_Double(1.234, "[time]", unitsRef);
    setValueAt(db, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_timeStep = i;
    i++;

    setValueAt(paramName_shearRate, i,0, NOT_EDITABLE);
    db = new TableData_Units_Double(1.234, "[time]^{-1}", unitsRef);
    setValueAt(db, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_shearRate = i;
    i++;

    setValueAt(paramName_shearDir, i,0, NOT_EDITABLE);
    valInt = 2;
    setValueAt(valInt, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_shearDir = i;
    i++;

    setValueAt(paramName_shearVelDir, i,0, NOT_EDITABLE);
    valInt = 0;
    setValueAt(valInt, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_shearVelDir = i;
    i++;

    setValueAt(paramName_saveSkip, i,0, NOT_EDITABLE);
    setValueAt(new Integer(1), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_saveSkip = i;
    i++;

  }

  public double getTimeStep() {

    int col = 1;

    TableData_Units_Double data = (TableData_Units_Double) getValueAt(paramIndex_timeStep, col);

    return data.getValue();

  }

  public double getShearRate() {

    int col = 1;

    TableData_Units_Double data = (TableData_Units_Double) getValueAt(paramIndex_shearRate, col);

    return data.getValue();

  }
  
  public int getShearDir() {

    int col = 1;

    int value = (Integer) getValueAt(paramIndex_shearDir, col);
    
    return value;

  }

  public int getShearVelDir() {

    int col = 1;

    int value = (Integer) getValueAt(paramIndex_shearVelDir, col);

    return value;

  }

  public int getSaveSkip() {

    int col = 1;

    int value = (Integer) getValueAt(paramIndex_saveSkip, col);

    return value;

  }


  public void setTimeStep(double value) {
    int col = 1;

    TableData_Units_Double data = (TableData_Units_Double) getValueAt(paramIndex_timeStep, col);

    data.setValue(value); /* uses default units */

    setValueAt(data, paramIndex_timeStep, col);
  }

  public void setShearRate(double value) {
    int col = 1;

    setValueAt(value, paramIndex_shearRate, col);
  }

  public void setShearDir(int value) {
    int col = 1;
        
    setValueAt(value, paramIndex_shearDir, col);
  }

  public void setShearVelDir(int value) {
    int col = 1;

    setValueAt(value, paramIndex_shearVelDir, col);
  }

  public void setSaveSkip(int value) {
    int col = 1;

    setValueAt(value, paramIndex_saveSkip, col);
  }

  
  
 
}







