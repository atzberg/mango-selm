package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.Atz3D_Model;
import java.awt.Graphics;

/**
 *
 * Stochastic Eulerian Lagrangian Method rendering representation.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_IntegratorRenderView extends SELM_RenderView {

  @Override
  public void renderToModel3D(Atz3D_Model model3D);

  /*
  public void getLagrangianData();
  public void setLagrangianData(SELM_Lagrangian lagrangian);
  */
}
