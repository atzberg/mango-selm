package org.atzberger.application.selm_builder;

import org.atzberger.mango.atz3d.Atz3D_Model;

/**
 *
 * Provide generic interface to render Stochastic Eulerian Lagrangian Method data types.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public interface SELM_RenderView {

  //public String tag;  /* tag for describing the render object */
  //public void   setRenderTag(String tag);
  public String getRenderTag();
  
  public void renderToModel3D(Atz3D_Model model3D);
  
}
