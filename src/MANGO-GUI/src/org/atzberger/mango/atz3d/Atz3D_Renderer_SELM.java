package org.atzberger.mango.atz3d;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import javax.swing.*;

/**
 *
 * Renders view of the 3D objects using the specified camera view.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz3D_Renderer_SELM extends Atz3D_Renderer {
      
  protected JPanel                jPanel;
  public    Atz3D_Camera          camera;
  protected Atz3D_Model           model;

  protected Color backgroundColor1;
  protected Color backgroundColor2;

  boolean flagAxisVisible = true;
  
  protected Color axisColor;
  
  protected String[] axisLabels = new String[] {"x","y","z"};
  protected Color axisLabelColor;
  
  public Atz3D_Renderer_SELM() {
    camera = null;
    model  = null;
    jPanel = null;

    backgroundColor1 = Color.WHITE;
    backgroundColor2 = Color.WHITE;
    axisColor        = Color.BLACK;
    axisLabelColor   = Color.BLUE;
    
  }

  public void render(JPanel jPanel_in, Graphics g, Atz3D_Model model_in, Atz3D_Camera camera_in, Color bk1, Color bk2) {
    backgroundColor1 = bk1;
    backgroundColor2 = bk2;
    
    render(jPanel_in, g, model_in, camera_in);
  }

  public void render(JPanel jPanel_in, Graphics g, Atz3D_Model model_in, Atz3D_Camera camera_in) {
    /* set the panel */
    jPanel = jPanel_in;
    camera = camera_in;
    model  = model_in;

    paint_screen(g);
  }

  protected void paint_screen(Graphics g) {
    
    Dimension screenSize = jPanel.getSize();
    Graphics2D g2 = (Graphics2D) g;

    Atz3D_Element[] z3D_ElementList = model.getElementList();

    /* == Draw the background */
    GradientPaint gp = new GradientPaint(0, 0 , backgroundColor1, 0, screenSize.height, backgroundColor2);
    //g.setColor(backgroundColor1);
    g2.setPaint(gp);
    g2.fillRect(0, 0, screenSize.width, screenSize.height);

    /* == Loop over the model elements 
     * (ideally according to the z-order and paint)
     */
    for (int k = 0; k < z3D_ElementList.length; k++) {
      if (z3D_ElementList[k] != null) {
        (z3D_ElementList[k]).paint(g, screenSize, camera);
      }
    }

    /* == Draw axes, if flag set
     */   
    if (flagAxisVisible) {
      int d;
      int num_dim = 3;

      double[] XX = new double[3];
      double[] YY = new double[3];
      double[] ZZ = new double[3];

      double   normXX = 0;
      double   normYY = 0;
      double   normZZ = 0;

      double   x1,  x2,  y1,  y2;
      int      xx1, xx2, yy1, yy2;

      int      w,h;

      double[] ptsX                       = new double[12];
      double[] ptsX_2D_view               = new double[8];
      double[] ptsX_2D_screen_transformed = new double[8];

      ptsX[0]  = camera.cameraVRP[0];
      ptsX[1]  = camera.cameraVRP[1];
      ptsX[2]  = camera.cameraVRP[2];

      ptsX[3]  = camera.cameraVRP[0] + 1.0;
      ptsX[4]  = camera.cameraVRP[1] + 0.0;
      ptsX[5]  = camera.cameraVRP[2] + 0.0;

      ptsX[6]  = camera.cameraVRP[0] + 0.0;
      ptsX[7]  = camera.cameraVRP[1] + 1.0;
      ptsX[8]  = camera.cameraVRP[2] + 0.0;

      ptsX[9]  = camera.cameraVRP[0] + 0.0;
      ptsX[10] = camera.cameraVRP[1] + 0.0;
      ptsX[11] = camera.cameraVRP[2] + 1.0;
      
      /* project the points to the camera view plane */
      camera.transformPts_3D_To_2D(ptsX, ptsX_2D_view);

      /* scale 2D points so they map to the effective screen size */
      camera.transformPts_2D_To_Screen(ptsX_2D_view, screenSize, ptsX_2D_screen_transformed);

      double alpha  = 0.1;
      double beta1  = 0.125;
      double beta2  = 0.875;
      int    strokeWidth = 3;      
      BasicStroke stroke = new BasicStroke(strokeWidth);
      TextLayout tl;
      
      FontRenderContext frc = g2.getFontRenderContext();
      Font f   = new Font("Helvetica", Font.BOLD, 20);
      String s;

      w = screenSize.width;
      h = screenSize.height;

      x1 = alpha*w*ptsX_2D_view[0] + beta1*w;
      y1 = alpha*h*ptsX_2D_view[1] + beta2*h;

      x2 = alpha*w*ptsX_2D_view[2] + beta1*w;
      y2 = alpha*h*ptsX_2D_view[3] + beta2*h;

      xx1 = (int) java.lang.Math.round(x1);
      xx2 = (int) java.lang.Math.round(x2);
      yy1 = (int) java.lang.Math.round(y1);
      yy2 = (int) java.lang.Math.round(y2);

      g2.setColor(axisColor);
      g2.setStroke(stroke);
      g2.drawLine(xx1,yy1,xx2,yy2);

      //s = new String(" x");
      s = axisLabels[0];
      tl = new TextLayout(s, f, frc);
      g2.setColor(axisLabelColor);
      tl.draw(g2, (float) x2, (float) y2);



      x2 = alpha*w*ptsX_2D_view[4] + beta1*w;
      y2 = alpha*h*ptsX_2D_view[5] + beta2*h;

      xx1 = (int) java.lang.Math.round(x1);
      xx2 = (int) java.lang.Math.round(x2);
      yy1 = (int) java.lang.Math.round(y1);
      yy2 = (int) java.lang.Math.round(y2);

      g2.setColor(axisColor);
      g2.setStroke(stroke);
      g2.drawLine(xx1,yy1,xx2,yy2);

      //s = new String(" y");
      s = axisLabels[1];
      tl = new TextLayout(s, f, frc);
      g2.setColor(axisLabelColor);
      tl.draw(g2, (float) x2, (float) y2);




      x2 = alpha*w*ptsX_2D_view[6] + beta1*w;
      y2 = alpha*h*ptsX_2D_view[7] + beta2*h;

      xx1 = (int) java.lang.Math.round(x1);
      xx2 = (int) java.lang.Math.round(x2);
      yy1 = (int) java.lang.Math.round(y1);
      yy2 = (int) java.lang.Math.round(y2);

      g2.setColor(axisColor);
      g2.setStroke(stroke);
      g2.drawLine(xx1,yy1,xx2,yy2);

      //s = new String(" z");
      s = axisLabels[2];
      tl = new TextLayout(s, f, frc);
      g2.setColor(axisLabelColor);
      tl.draw(g2, (float) x2, (float) y2);
                
      /*
      g2.setColor(Color.BLACK);
      for (d = 0; d < num_dim; d++) {
        ZZ[d]   = camera.cameraVPN[d];
        YY[d]   = camera.cameraVUP[d];        
        normYY += YY[d]*YY[d];
        normZZ += ZZ[d]*ZZ[d];
      }

      XX[0] = YY[1]*ZZ[2] - YY[2]*ZZ[1];
      XX[1] = YY[2]*ZZ[0] - YY[0]*ZZ[2];
      XX[2] = YY[0]*ZZ[1] - YY[1]*ZZ[0];

      normYY = java.lang.Math.sqrt(normYY);
      normZZ = java.lang.Math.sqrt(normZZ);
      normXX = java.lang.Math.sqrt(XX[0]*XX[0] + XX[1]*XX[1] + XX[2]*XX[2]);
      for (d = 0; d < num_dim; d++) {
        XX[d] = XX[d]/normXX;
        YY[d] = YY[d]/normYY;
        ZZ[d] = ZZ[d]/normZZ;
      }

      // X-axis
      x1 = 0.8*screenSize.width;
      y1 = 0.8*screenSize.height;

      x2 = x1 + 0.1*screenSize.width*XX[0];
      y2 = x2 + 0.1*screenSize.height*XX[1];

      xx1 = (int) java.lang.Math.round(x1);
      xx2 = (int) java.lang.Math.round(x2);
      yy1 = (int) java.lang.Math.round(y1);
      yy2 = (int) java.lang.Math.round(y2);

      g2.drawLine(xx1,yy1,xx2,yy2);

      // Y-axis
      x1 = 0.8*screenSize.width;
      y1 = 0.8*screenSize.height;

      x2 = x1 + 0.1*screenSize.width*YY[0];
      y2 = x2 + 0.1*screenSize.height*YY[1];

      xx1 = (int) java.lang.Math.round(x1);
      xx2 = (int) java.lang.Math.round(x2);
      yy1 = (int) java.lang.Math.round(y1);
      yy2 = (int) java.lang.Math.round(y2);
      
      g2.drawLine(xx1,yy1,xx2,yy2);

      // Z-axis
      x1 = 0.8*screenSize.width;
      y1 = 0.8*screenSize.height;

      x2 = x1 + 0.1*screenSize.width*ZZ[0];
      y2 = x2 + 0.1*screenSize.height*ZZ[1];

      xx1 = (int) java.lang.Math.round(x1);
      xx2 = (int) java.lang.Math.round(x2);
      yy1 = (int) java.lang.Math.round(y1);
      yy2 = (int) java.lang.Math.round(y2);

      g2.drawLine(xx1,yy1,xx2,yy2);
      */
                        
    } /* flagAxesVisible */

  }

  public void setAxisInfo(boolean flagVisible, String[] axisLabels_in, Color axisColor_in, Color axisLabelColor_in) {
    flagAxisVisible  = flagVisible;
    axisLabels       = axisLabels_in;
    axisColor        = axisColor_in;
    axisLabelColor   = axisLabelColor_in;
  }

} /* end of class Atz3D_Renderer_SELM */
