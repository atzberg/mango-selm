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
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz3D_Element_Lines extends Atz3D_Element {

  int     numLines;
  int     numLinesAlloc;
  
  int     num_dim = 3;

  double  ptsX1List[];
  double  ptsX2List[];
    
  int[]   keyList;
  int     lastKey = -1;
  
  Color   plotColor;

  boolean flagPlotFancy = false;

  public Atz3D_Element_Lines() {

  }

  public Atz3D_Element_Lines(double[] ptsX1_in, double[] ptsX2_in) {
    setLines(ptsX1_in, ptsX2_in);
  }

  public void setLines(double[] ptsX1_in, double[] ptsX2_in) {
    ptsX1List     = ptsX1_in.clone();
    ptsX2List     = ptsX2_in.clone();

    numLines      = ptsX1List.length/num_dim;
    numLinesAlloc = numLines;
  }

  public int addLine(double[] ptsX1, double[] ptsX2) {
    int key = -1;

    if (numLines < numLinesAlloc) {
      for (int d = 0; d < num_dim; d++) {
        ptsX1List[numLines*num_dim + d] = ptsX1[d];
        ptsX2List[numLines*num_dim + d] = ptsX2[d];
      }
      key             = lastKey + 1;
      keyList[numLines] = key;
      lastKey         = key;
      numLines++;
    } else {
      resizeLists(2*numLinesAlloc);
      addLine(ptsX1, ptsX2);
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
   
    /* setup points for plotting */
    double[] ptsX1_2D_view               = new double[numLines * (num_dim - 1)];
    double[] ptsX1_2D_screen             = new double[numLines * (num_dim - 1)];
    double[] ptsX1_2D_screen_transformed = new double[numLines * (num_dim - 1)];
    double[] tmp_ptsX1                   = ptsX1_2D_view.clone();

    double[] ptsX2_2D_view               = new double[numLines * (num_dim - 1)];
    double[] ptsX2_2D_screen             = new double[numLines * (num_dim - 1)];
    double[] ptsX2_2D_screen_transformed = new double[numLines * (num_dim - 1)];
    double[] tmp_ptsX2                   = ptsX2_2D_view.clone();

    /* project the points to the camera view plane */
    camera.transformPts_3D_To_2D(ptsX1List, ptsX1_2D_view);
    camera.transformPts_3D_To_2D(ptsX2List, ptsX2_2D_view);

    /* scale 2D points so they map to the effective screen size (range 0.0 to 1.0) */
    camera.transformPts_2D_To_Screen(ptsX1_2D_view, ptsX1_2D_screen);
    camera.transformPts_2D_To_Screen(ptsX2_2D_view, ptsX2_2D_screen);

    for (k = 0; k < ptsX1_2D_screen.length/2; k++) {
      ptsX1_2D_screen_transformed[2*k]     = w*ptsX1_2D_screen[2*k];
      ptsX1_2D_screen_transformed[2*k + 1] = h*ptsX1_2D_screen[2*k + 1];

      ptsX2_2D_screen_transformed[2*k]     = w*ptsX2_2D_screen[2*k];
      ptsX2_2D_screen_transformed[2*k + 1] = h*ptsX2_2D_screen[2*k + 1];
    }
              
    int    N    = ptsX1List.length/num_dim;
    double X1[] = new double[num_dim - 1];
    double X2[] = new double[num_dim - 1];
    for (k = 0; k < N; k++) {

      X1[0] = ptsX1_2D_screen[k*(num_dim - 1) + 0];
      X1[1] = ptsX1_2D_screen[k*(num_dim - 1) + 1];

      X2[0] = ptsX2_2D_screen[k*(num_dim - 1) + 0];
      X2[1] = ptsX2_2D_screen[k*(num_dim - 1) + 1];

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

   public void resizeLists(int numLinesAlloc_new) {

    double[] ptsX1_new = new double[numLinesAlloc_new];
    double[] ptsX2_new = new double[numLinesAlloc_new];
    int[] keyList_new = new int[numLinesAlloc_new];

    for (int k = 0; k < numLines; k++) {
      for (int d = 0; d < num_dim; d++) {
        ptsX1_new[k * num_dim + d] = ptsX1List[k * num_dim + d];
        ptsX2_new[k * num_dim + d] = ptsX2List[k * num_dim + d];
      }

      keyList_new[k] = keyList[k];
    }

    ptsX1List = ptsX1_new;
    ptsX2List = ptsX2_new;
    keyList   = keyList_new;

  }

}
