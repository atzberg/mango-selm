/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atzberger.mango.table;

import javax.swing.table.AbstractTableModel;


/**
 *
 * Multiple choice type of data.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_MultipleChoice1 {

  public String[] ItemPossibleValues;
  public Object   ItemSelected;
  public int      ItemSelectedIndex;

  AbstractTableModel tableModel = null;
  
  public TableData_MultipleChoice1() {
    ItemPossibleValues = new String[4];

    ItemPossibleValues[0] = "Cold";
    ItemPossibleValues[1] = "Lukewarm";
    ItemPossibleValues[2] = "Warm";
    ItemPossibleValues[3] = "Hot";

    ItemSelectedIndex     = 1;
    ItemSelected          =  ItemPossibleValues[ItemSelectedIndex];
  }

  public TableData_MultipleChoice1(String[] ItemPossibleValues_in, int indexItemSelected) {
    ItemPossibleValues    = ItemPossibleValues_in;
    ItemSelectedIndex     = indexItemSelected;
    ItemSelected          = ItemPossibleValues_in[ItemSelectedIndex];
  }

  public TableData_MultipleChoice1(String[] ItemPossibleValues_in) {
    this(ItemPossibleValues_in, 0); 
  }

  public void setTableModelToNotify(AbstractTableModel tableModel_in) {
    tableModel = tableModel_in;
  }

  public String getSelectedItem() {
    return ItemPossibleValues[ItemSelectedIndex];
  }

  public int getSelectedItemIndex() {
    return ItemSelectedIndex;
  }

  public void setItemSelectedIndex(int index) {
    
    if (ItemPossibleValues.length > index) {
      ItemSelectedIndex = index;
      ItemSelected = ItemPossibleValues[ItemSelectedIndex];
    } else {
      /* nothing changes */
      System.out.println("TableData_MultipleChoice1 : Index out of range");
    }

  }

  public void setPossibleValues(String[] possibleValues) {
    String curValue = getSelectedItem();
    ItemPossibleValues = possibleValues;
    selectMatch(curValue);
  }

  public void selectMatch(String matchStr) {

    setItemSelectedIndex(0);

    for (int k = 0; k < ItemPossibleValues.length; k++) {
      
      if (ItemPossibleValues[k].equals(matchStr)) {
        setItemSelectedIndex(k);
      }

    }
    
  }
    
}
