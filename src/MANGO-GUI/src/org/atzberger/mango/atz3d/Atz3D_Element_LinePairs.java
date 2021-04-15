package org.atzberger.mango.atz3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 *
 * Provides 3D representation of a collection of lines.
 *
 *  !!!!!!!!!!!!!!!!!! NOT IMPLEMENTED YET!!!!!
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz3D_Element_LinePairs extends Atz3D_Element {

  int     numLines;
  int     numLinesAlloc;
  
  int     num_dim = 3;

  double  ptsX[];
  
  int     ptsI1_list[];
  int     ptsI2_list[];
  int[]   keyList;
  int     lastKey;
  
  Color   plotColor;

  boolean flagPlotFancy = false;

  public Atz3D_Element_LinePairs() {

  }

  public Atz3D_Element_LinePairs(double[] ptsX_in, int[] ptsI1_list_in, int[] ptsI2_list_in) {

    /* !!!!!!!!!!!!!!!!!! NOT IMPLEMENTED YET!!!!! */

    ptsX          = ptsX_in.clone();
    ptsI1_list    = ptsI1_list_in.clone();
    ptsI2_list    = ptsI2_list_in.clone();

    numLines      = ptsI1_list.length;
    numLinesAlloc = numLines;

  }


  public int addPoint(double[] pts) {
    int key = -1;

    if (numLines < numLinesAlloc) {
      for (int d = 0; d < num_dim; d++) {
        ptsX[numLines*num_dim + d] = pts[d];
      }
      key             = lastKey + 1;
      keyList[numLines] = key;
      lastKey         = key;
      numLines++;
    } else {
      //resizeLists(2*numLinesAlloc); /* @@@ */
      addPoint(pts);
    }

    return key;

  }


  public void setPlotColor(Color color_in) {
     plotColor = color_in;
  }
  
  @Override
  public void paint(Graphics g, Dimension screenSize, Atz3D_Camera camera) {

    Graphics2D g2 = (Graphics2D) g;

    int k;
    int w = screenSize.width;
    int h = screenSize.height;

    /* draw the background */
    g.setColor(Color.WHITE);
    g2.fillRect(0, 0, w, h);

    /* -- plots a few items to test rendering */
    double[] ptsX_2D_view               = new double[numLines * (num_dim - 1)];
    double[] ptsX_2D_screen             = new double[numLines * (num_dim - 1)];
    double[] ptsX_2D_screen_transformed = new double[numLines * (num_dim - 1)];
    double[] tmp_ptsX                   = ptsX_2D_view.clone();

    /* project the points to the camera view plane */
    camera.transformPts_3D_To_2D(ptsX, ptsX_2D_view);

    /* scale 2D points so they map to the effective screen size (range 0.0 to 1.0) */
    camera.transformPts_2D_To_Screen(ptsX_2D_view, ptsX_2D_screen);

    for (k = 0; k < ptsX_2D_screen.length/2; k++) {
      ptsX_2D_screen_transformed[2*k]     = w*ptsX_2D_screen[2*k];
      ptsX_2D_screen_transformed[2*k + 1] = h*ptsX_2D_screen[2*k + 1];
    }
          
    plotLines2D(g, screenSize, ptsX_2D_screen, ptsI1_list, ptsI2_list);
    
  }

  public void plotLines2D(Graphics g, Dimension screenSize, double ptsX_2D[], int I2_list[], int I1_list[]) {

    double X1[];
    double X2[];

    int N, N1, N2;
    int I, I1, I2;
    int i, j, k;

    N1 = I1_list.length;
    N2 = I2_list.length;
    N = N1;

    X1 = new double[3];
    X2 = new double[3];

    for (k = 0; k < N; k++) {
      I1 = I1_list[k];
      I2 = I2_list[k];

      X1[0] = ptsX_2D[I1 * 2 + 0];
      X1[1] = ptsX_2D[I1 * 2 + 1];

      X2[0] = ptsX_2D[I2 * 2 + 0];
      X2[1] = ptsX_2D[I2 * 2 + 1];

      plotLines2D(g, screenSize, X1, X2);

    } /* end of k loop */

  }

  public void plotLines2D(Graphics g, Dimension screenSize, double ptsX1_2D[], double ptsX2_2D[]) {

      Graphics2D g2  = (Graphics2D) g;
      
      int    k;
      int    N, N1, N2;
      int    x1, y1, x2, y2;
      int    r1;
      int    r2;

      int    w;
      int    h;

      GradientPaint p2;
      Line2D.Double line;

      /* determine size of the view port */
      w = screenSize.width;
      h = screenSize.height;

      N1 = ptsX1_2D.length/2;
      N2 = ptsX2_2D.length/2;
      N  = N1;
      for (k = 0; k < N; k++) {
        x1 = (int) (ptsX1_2D[2*k]*w);
        y1 = (int) (ptsX1_2D[2*k + 1]*h);

        x2 = (int) (ptsX2_2D[2*k]*w);
        y2 = (int) (ptsX2_2D[2*k + 1]*h);

        line = new Line2D.Double(x1, y1, x2, y2);
        g2.draw(line);

        /* gradient paint */
        /*
        if (flagPlotFancy == 1) {
          p2     = new GradientPaint(x, y, Color.cyan, (int) (x + 1.5 * r1), (int) (y + 1.5 * r2), Color.black, false);
          g2.setPaint(p2);
        } else {
          g2.setPaint(plotDefaultPointColor);
        }
         */

        g2.setPaint(plotColor);

        g2.draw(line);

      } /* end of k loop */

  }

}
