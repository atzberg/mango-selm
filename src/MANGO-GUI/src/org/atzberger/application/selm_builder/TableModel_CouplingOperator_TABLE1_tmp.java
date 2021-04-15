package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_Default;
import org.atzberger.mango.atz3d.JPanel_Model_View_RenderPanel;
import org.atzberger.mango.table.TableData_Units_Double;
import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.TableData_MultipleChoice1;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Filename;
import java.awt.Color;
import javax.swing.table.AbstractTableModel;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_CouplingOperator_TABLE1_tmp extends TableModel_Properties1_Default {

  private final static boolean DEBUG = false;
  
  public   String paramName_ControlPtsX     = "pts_X";

  public   String paramName_InteractionList = "Interaction List";
  public   int paramIndex_InteractionList   = -1;

  protected JPanel_Model_View_RenderPanel jPanel_Render_ControlPts;

  /*
  private final static String str_Type = "Type";
  private final static int I_Type             = 0;
  private final static int I_ptsX             = 1;
  private final static int I_pts_IDs          = 2;
  private final static int I_Interaction_List = 3;
   */
  
  TableModel_CouplingOperator_TABLE1_tmp(application_SharedData applSharedData) {
    
    int      i                = 0;
    String[] choices          = null;
    double[] testDoubleArray1 = new double[3];
    double[] dbArray          = null;
    TableData_Units_Double db = null;

    Atz_UnitsRef unitsRef = applSharedData.atz_UnitsRef;


    setValueAt("Type", i,0, NOT_EDITABLE);
    choices    = new String[3];
    choices[0] = "POINT_PARTICLE1";
    choices[1] = "SPHERE_TR";
    setValueAt(new TableData_MultipleChoice1(choices), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;
    
    setValueAt("Weight Table Filename", i,0, NOT_EDITABLE);
    setValueAt(new TableData_Filename(), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt(paramName_ControlPtsX, i,0, NOT_EDITABLE);
    dbArray = new double[3];
    dbArray[0] = 3.1;
    dbArray[1] = 2.2;
    dbArray[2] = 1.3;
    setValueAt(dbArray, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt("pts_IDs", i,0, NOT_EDITABLE);
    dbArray = new double[3];
    dbArray[0] = 1;
    dbArray[1] = 2;
    dbArray[2] = 3;
    setValueAt(dbArray, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt(paramName_InteractionList, i,0, NOT_EDITABLE);
    setValueAt(new TableData_InteractionList(), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    paramIndex_InteractionList = i;
    i++;
   
  }


  void setControlPtsRenderPanel(JPanel_Model_View_RenderPanel jPanel_Render_ControlPts_in) {
    jPanel_Render_ControlPts = jPanel_Render_ControlPts_in;
  }
 
  /* Here we setup triggers when values in the table change */
  public void setValueAt(Object value, int row, int col) {
    super.setValueAt(value, row, col);

    /* check for ControlPtsX changed */
    String paramName = (String)data[row][0];
    if (paramName.equals(paramName_ControlPtsX)) {

      if (jPanel_Render_ControlPts != null) {
        /* set data in the renderer */
        double[] ptsX = (double[])data[row][1];
        //jPanel_Render_ControlPts.setControlPts(ptsX);
      }
    }
  }


  /* Here we setup triggers when values in the table change */
  @Override
  public Object getValueAt(int row, int col) {

    Object dataValue;
    int    colData;

    /* check for ControlPtsX being considered */
    String paramName = (String)data[row][0];
    if ((paramName.equals(paramName_ControlPtsX) && (col > 0))) {
      if (jPanel_Render_ControlPts != null) {
        /* set data in the renderer */
        //double[] ptsX = (double[])data[row][1];
        double[] ptsX;

        /* setting controlPts data from render, let render do this explicitly */
        //ptsX = jPanel_Render_ControlPts.getControlPts();
        //colData = 1;
        //setValueAt(ptsX,row,colData);
      }
    }

    dataValue = super.getValueAt(row, col);
    
    return dataValue;

  }





}







