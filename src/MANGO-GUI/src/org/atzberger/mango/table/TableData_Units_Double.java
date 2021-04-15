package org.atzberger.mango.table;

import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.units.Atz_UnitsData;
import java.text.DecimalFormat;

/**
 *
 * Double value data type.  Allows for physical units to be associated with this data value.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 * @see Atz_UnitsData
 */
public class TableData_Units_Double {

  public double         value;
  public Atz_UnitsData  unitsData;
  public boolean        flagShowUnits = true;

  //protected DecimalFormat  formatRender_default = null;
  //protected DecimalFormat  formatEdit_default   = null;

  public TableData_Units_Double() {
    value     = 0.0;
    unitsData = null;
  }

  public TableData_Units_Double(double value_in) {
    setup(value_in, null, true);
  }

  public TableData_Units_Double(double value_in, Atz_UnitsData unitsData_in) {
    setup(value_in, unitsData_in, true);
  }

  public TableData_Units_Double(double value_in, Atz_UnitsData unitsData_in, boolean flagShowUnits_in) {
    setup(value_in, unitsData_in, flagShowUnits_in);
  }

  void setup(double value_in, Atz_UnitsData unitsData_in, boolean flagShowUnits_in) {
    value         = value_in;
    unitsData     = unitsData_in;
    flagShowUnits = flagShowUnits_in;    
  }
  
  public TableData_Units_Double(double value_in, String unitStr_in, Atz_UnitsRef units_ref_in) {
    value     = value_in;    
    unitsData = new Atz_UnitsData(unitStr_in, units_ref_in);    
  }

  public DecimalFormat getFormatRender() {
    return unitsData.units_ref.getFormatRender();
  }

  public DecimalFormat getFormatEdit() {
    return unitsData.units_ref.getFormatEdit();
  }

  public String getDisplayStr() {
    String output;
    String unitsStr;

    DecimalFormat  formatRender = unitsData.units_ref.getFormatRender();
    DecimalFormat  formatEdit   = unitsData.units_ref.getFormatEdit();

    if (unitsData.units_ref.getVisiblePreferred()) {
      unitsStr = " " + unitsData.genDisplayStr();
    } else {
      unitsStr = "";
    }
    
    output = formatRender.format(value) + unitsStr;
    
    return output;
  }

  public double getValue() {  /* gets the value in terms of the current units */
    return value;
  }

  public void setValue(double value_in) {  /* sets value using the current units */
    value = value_in;
  }


  

}
