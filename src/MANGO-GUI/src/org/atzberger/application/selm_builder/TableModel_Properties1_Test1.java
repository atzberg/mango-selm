package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_MultipleChoice1;
import org.atzberger.mango.table.TableData_Filename;
import java.awt.Color;
import javax.swing.table.AbstractTableModel;
import org.atzberger.mango.table.TableData_EditorButton;
import org.atzberger.mango.table.TableData_Filename;
import org.atzberger.mango.table.TableData_MultipleChoice1;
import org.atzberger.mango.table.TableModel_Properties1_General;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_Properties1_Test1 extends TableModel_Properties1_General {

  private final static boolean DEBUG = false;
  
  public TableModel_Properties1_Test1() {
    int i;
    
    double[] testDoubleArray1 = new double[3];

    testDoubleArray1[0] = 1.3;
    testDoubleArray1[1] = 2.2;
    testDoubleArray1[2] = 3.1;

    i          = 0;
    setValueAt("City", i,0);
    setValueAt("New York", i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;
    
    setValueAt("Filename", i,0);
    setValueAt(new TableData_Filename(), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt("Filename1", i,0);
    setValueAt(new TableData_Filename(), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    /*
    setValueAt("Filename2", i,3);
    setValueAt(new TableData_Filename(), i,4);
    setValueAt(new TableData_EditorButton(), i,5);
     */
    i++;

    setValueAt("Number Days", i,0);
    setValueAt(new Integer(20), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt("Temperature", i,0);
    setValueAt(new TableData_MultipleChoice1(), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt("Music", i,0);
    String[] choices = {"Classical", "Jazz", "Rock"};
    setValueAt(new TableData_MultipleChoice1(choices,2), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt("XYZ", i,0);
    double[] dbArray = new double[3];
    dbArray[0] = 3.1;
    dbArray[1] = 2.2;
    dbArray[2] = 1.3;
    setValueAt(dbArray, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt("Flag Sunny", i,0);
    setValueAt(new Boolean(true), i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;

    setValueAt("Plot Color", i,0);
    setValueAt(Color.blue, i,1);
    setValueAt(new TableData_EditorButton(), i,2);
    i++;
    
  }

}







