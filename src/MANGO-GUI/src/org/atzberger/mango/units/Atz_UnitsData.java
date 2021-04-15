package org.atzberger.mango.units;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * Represents a particular type of physical unit and how to display it.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_UnitsData {

  public String       unitStr = "[mass]\u00B7[length]/[time]^2"; /*\u00B7 center dot */
  public Atz_UnitsRef units_ref; /* determine specific units to use */

  public Atz_UnitsData() {
    unitStr   = "";
    units_ref = null;
  }

  public Atz_UnitsData(String unitStr_in, Atz_UnitsRef units_ref_in) {
    unitStr   = unitStr_in.toString();
    units_ref = units_ref_in;
  }

  public String genDisplayStr() { /* generates display string */
    
    String output = unitStr;

    /* parse the string and replace [mass] -> unit name, etc... */

    Set keyVals = units_ref.unitsSelected.keySet();

    Iterator keyIterator = keyVals.iterator();
    
    //Enumeration keyVals = units_ref.unitsSelected.keys();  /* do replacement for all keys */
    while (keyIterator.hasNext()) {

      String    unitTypeStr               = (String) keyIterator.next();
      Atz_Unit  unit                      = (Atz_Unit) units_ref.unitsSelected.get(unitTypeStr);
      String    findExp                   = "\\[" + unitTypeStr + "\\]";
      String    replaceExp                = unit.unitShortName;

      output = output.replaceAll(findExp, replaceExp);

    }

    /*
    output = output.replaceAll("\\[length\\]", "nm");
    output = output.replaceAll("\\[mass\\]", "kg");
    output = output.replaceAll("\\[time\\]", "s");
     */
    
    return output;
  }
    
}
