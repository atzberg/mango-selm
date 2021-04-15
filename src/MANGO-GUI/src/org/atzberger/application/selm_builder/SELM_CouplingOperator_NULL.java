package org.atzberger.application.selm_builder;

import java.io.BufferedWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_CouplingOperator_NULL extends SELM_CouplingOperator {

  /* constructors */
  SELM_CouplingOperator_NULL() {
    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    CouplingOpName    = "NULL";
    CouplingOpTypeStr = thisClassName.replace(superClassName + "_", "");
  }

  @Override
  public Object clone() {
    SELM_CouplingOperator_NULL CouplingOp_copy = new SELM_CouplingOperator_NULL();

    CouplingOp_copy.CouplingOpName      = this.CouplingOpName.toString();
    CouplingOp_copy.CouplingOpTypeStr   = this.CouplingOpTypeStr.toString();

    return (Object) CouplingOp_copy;
  }

}
