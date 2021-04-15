package org.atzberger.mango.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * Customised table displaying many different types of data types.  Default setup
 * for a wide range of different data types.  This includes tables with simple
 * data types, colors, arrays of Doubles, Integers, and Strings, and multiple choice data types.
 * <p>
 * This class automatically chooses the appropriate editor to invoke on the data
 * and how to display to the user the particular data type.
 * <p>
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTable_Properties1_Default extends JTable_Properties1_General {

  private boolean flagColumnHeader = false; /* default is to show no column header */

  public JTable_Properties1_Default() {
    super();

    setupDefaults();

  }

  public JTable_Properties1_Default(boolean flagNoColumnHeader_in) {
    super();

    flagColumnHeader = flagNoColumnHeader_in;

    setupDefaults();

  }


  public JTable_Properties1_Default(TableModel dm) {
    super(dm, null, null);

    setupDefaults();

  }

  public JTable_Properties1_Default(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
    super(dm, cm, sm);

    setupDefaults();

  }


  protected void setupDefaults() {
    setupDefaultEditorsAndRenderers();

    setupColumnRendering();

  }

  protected void setupDefaultEditorsAndRenderers() {

    //Set up renderer and editor for the Favorite Color column.
    //setDefaultRenderer(Color.class,
    //                   new TableRenderer_Color(true));
    //setDefaultEditor(Color.class,
    //                 new TableEditor_Color());


    
    //Set up for Integer
    setDefaultRenderer(Integer.class,
                       new TableRenderer_Integer());

    setDefaultEditor(Integer.class,
                     new TableEditor_Integer());

    //Set up for String
    setDefaultRenderer(String.class,
                       new TableRenderer_String());

    setDefaultEditor(String.class,
                     new TableEditor_String());

    //Set up renderer for filename
    setDefaultRenderer(TableData_Filename.class,
                       new TableRenderer_Filename());

    setDefaultEditor(TableData_Filename.class,
                     new TableEditor_Filename());

    //Set up renderer for pathname
    setDefaultRenderer(TableData_Pathname.class,
                       new TableRenderer_Pathname());

    setDefaultEditor(TableData_Pathname.class,
                     new TableEditor_Pathname());

    //Set up renderer for color
    setDefaultRenderer(TableData_Color.class,
                       new TableRenderer_Color());

    setDefaultEditor(TableData_Color.class,
                     new TableEditor_Color());

    //Set up renderer for simple array types
    setDefaultRenderer(double[].class,
                       new TableRenderer_Array_Simple());

    setDefaultEditor(double[].class,
                     new TableEditor_Array_Simple());

    //Set up renderer for simple array types
    setDefaultRenderer(int[].class,
                       new TableRenderer_Array_Simple());

    setDefaultEditor(int[].class,
                     new TableEditor_Array_Simple());

    //Set up renderer for simple array types
    setDefaultRenderer(String[].class,
                       new TableRenderer_Array_Simple());

    setDefaultEditor(String[].class,
                     new TableEditor_Array_Simple());

    //Set up renderer for comboBox
    setDefaultRenderer(TableData_MultipleChoice1.class,
                       new TableRenderer_MultipleChoice1());

    setDefaultEditor(TableData_MultipleChoice1.class,
                     new TableEditor_MultipleChoice1());

    //Set up renderer for Double with units
    setDefaultRenderer(TableData_Units_Double.class,
                       new TableRenderer_Units_Double());

    setDefaultEditor(TableData_Units_Double.class,
                     new TableEditor_Units_Double());

    //Set up renderer and editor for special edit button 
    setDefaultRenderer(TableData_EditorButton.class,
                       new TableRenderer_EditorButton(true));

    TableEditor_EditorButton tableSpecialButtonEditor = new TableEditor_EditorButton();
    
    tableSpecialButtonEditor.addDataType(String.class.getName(),              new JDialog_Edit_SimpleValue());
    tableSpecialButtonEditor.addDataType(Double.class.getName(),              new JDialog_Edit_SimpleValue());
    tableSpecialButtonEditor.addDataType(Integer.class.getName(),             new JDialog_Edit_SimpleValue());
    tableSpecialButtonEditor.addDataType(Color.class.getName(),               new JDialog_Edit_Color());
    tableSpecialButtonEditor.addDataType(TableData_Filename.class.getName(),  new JDialog_Edit_Filename());
    tableSpecialButtonEditor.addDataType(TableData_Pathname.class.getName(),  new JDialog_Edit_Pathname());
    tableSpecialButtonEditor.addDataType(double[].class.getName(),            new JDialog_Edit_Array_Simple_New(true));
    tableSpecialButtonEditor.addDataType(int[].class.getName(),               new JDialog_Edit_Array_Simple_New(true));
    tableSpecialButtonEditor.addDataType(String[].class.getName(),            new JDialog_Edit_Array_Simple_New(true));

    setDefaultEditor(TableData_EditorButton.class,
                     tableSpecialButtonEditor);
    
  } /* setupDefaultEditorsAndRenderers */


  void setupColumnRendering() {

    /* setup the column rendering behavior */

    /* for no header at all just set it to null */
    if (flagColumnHeader == false) {
      setTableHeader(null); /* WARNING: make sure NetBeans does not set anything related to headers */
    }

    /*
    Enumeration enumeration = getTableHeader().getColumnModel().getColumns();
    while (enumeration.hasMoreElements()) {
      TableColumn aColumn = (TableColumn)enumeration.nextElement();
      aColumn.setHeaderRenderer(new JTableHeaderRender_Default1());
    }
     */

  }
  






}