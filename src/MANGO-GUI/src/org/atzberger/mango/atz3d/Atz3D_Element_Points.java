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
 * Provides 3D representation of a collection of points.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz3D_Element_Points extends Atz3D_Element {

  int    numPts;
  int    numPtsAlloc;  
  int    num_dim = 3;
    
  Color  plotColor = Color.blue;
  int    plotSize  = 10;

  boolean flagPlotFancy = false;
 
  double[]  ptsX;
  int[]     keyList;
  int       lastKey = -1;

  public Atz3D_Element_Points() {
    numPtsAlloc = 1;
    numPts      = 0;
    ptsX        = new double[numPtsAlloc];
    keyList     = new int[numPtsAlloc];
  }

  public Atz3D_Element_Points(double[] pts) {
    setPoints(pts);     
  }

  public Atz3D_Element_Points(double[] pts, Color plotColor_in) {
    setPlotColor(plotColor_in);
    setPoints(pts);
  }

  public Atz3D_Element_Points(double[] pts, Color plotColor_in, int plotSize_in) {
    setPlotColor(plotColor_in);
    setPlotSize(plotSize_in);
    setPoints(pts);
  }

  public int addPoint(double[] pts) {
    int key = -1;

    if (numPts < numPtsAlloc) {
      for (int d = 0; d < num_dim; d++) {
        ptsX[numPts*num_dim + d] = pts[d];
      }
      key             = lastKey + 1;
      keyList[numPts] = key;
      lastKey         = key;
      numPts++;
    } else {
      resizeLists(2*numPtsAlloc);
      addPoint(pts);
    }

    return key;

  }

  public void setPlotColor(Color color_in) {
    plotColor = color_in;
  }

  public Color getPlotColor() {
    return plotColor;
  }

  public void setPlotSize(int size_in) {
    plotSize = size_in;
  }

  public int getPlotSize() {
    return plotSize;
  }

  public void setPoints(double[] pts) {
    numPts      = pts.length/num_dim;
    numPtsAlloc = numPts;
    ptsX        = pts.clone();
    keyList     = new int[numPtsAlloc];
    for (int k = 0; k < numPts; k++) {
      keyList[k] = k;
    }
  }

  public double[] getPoints() {
    return ptsX.clone();
  }

  public double[] getPoint(int key) {
    double[] X = null;
    int      index = -1;

    for (int k = 0; k < numPts; k++) {
      if (keyList[k] == key)
        index = k;      
    }

    if (index >= 0) {
      X = new double[num_dim];
      for (int d = 0; d < num_dim; d++) {
        X[d] = ptsX[index*num_dim + d];
      }
    }
    
    return X;
  }

  @Override
  public void paint(Graphics g, Dimension screenSize, Atz3D_Camera camera) {

    Graphics2D g2 = (Graphics2D) g;

    int k;
    int w = screenSize.width;
    int h = screenSize.height;
    int m = java.lang.Math.min(w,h);

    //r1 = (int) java.lang.Math.max(5, 0.02 * w);
    //r2 = (int) java.lang.Math.max(5, 0.02 * w);

    //plotSize = (int) java.lang.Math.max(5, 0.02 * m);
   
    /* setup the points for plotting */
    double[] ptsX_2D_view               = new double[numPts * (num_dim - 1)];   
    double[] ptsX_2D_screen_transformed = new double[numPts * (num_dim - 1)];
    
    /* project the points to the camera view plane */
    camera.transformPts_3D_To_2D(ptsX, ptsX_2D_view);

    /* scale 2D points so they map to the effective screen size (range 0.0 to 1.0) */
    camera.transformPts_2D_To_Screen(ptsX_2D_view, screenSize, ptsX_2D_screen_transformed);

    /*
    for (k = 0; k < ptsX_2D_screen.length/2; k++) {
      ptsX_2D_screen_transformed[2*k]     = w*ptsX_2D_screen[2*k];
      ptsX_2D_screen_transformed[2*k + 1] = h*ptsX_2D_screen[2*k + 1];
    }
     */
          
    plotPoints2D(g, ptsX_2D_screen_transformed);
    
  }

  public void plotPoints2D(Graphics g,  double ptsX_2D[]) {
    Dimension screenSize = new Dimension(1,1);

    plotPoints2D(g, screenSize, ptsX_2D);

  }

  public void plotPoints2D(Graphics g, Dimension screenSize, double ptsX_2D[]) {

    Graphics2D g2 = (Graphics2D) g;
    
    int k;
    int N;
    int x, y;
    int r1;
    int r2;
    
    GradientPaint p2;
    Ellipse2D.Double circle;

    /* determine size of the view port */
    int w = screenSize.width;
    int h = screenSize.height;
    int m = java.lang.Math.min(w,h);
    
    //r1 = (int) java.lang.Math.max(5, 0.02 * w);
    //r2 = (int) java.lang.Math.max(5, 0.02 * w);

    r1 = plotSize;
    r2 = plotSize;

    N = ptsX_2D.length / 2;
    for (k = 0; k < N; k++) {
      x = (int) (ptsX_2D[2 * k] * w - (r1 / 2.0));
      y = (int) (ptsX_2D[2 * k + 1] * h - (r2 / 2.0));

      circle = new Ellipse2D.Double(x, y, r1, r2);

      /* gradient paint */
      if (flagPlotFancy) {
        p2 = new GradientPaint(x, y, Color.cyan, (int) (x + 1.5 * r1), (int) (y + 1.5 * r2), Color.black, false);
        g2.setPaint(p2);
      } else {
        g2.setPaint(plotColor);
      }

      g2.fill(circle);

    } /* end of k loop */

   } /* end plotPoints2D */

   public void resizeLists(int numPtsAlloc_new) {

    double[] ptsX_new = new double[numPtsAlloc_new];
    int[] keyList_new = new int[numPtsAlloc_new];

    for (int k = 0; k < numPts; k++) {
      for (int d = 0; d < num_dim; d++) {
        ptsX_new[k * num_dim + d] = ptsX[k * num_dim + d];
      }

      keyList_new[k] = keyList[k];
    }

    ptsX = ptsX_new;
    keyList = keyList_new;

  }

  protected void computeCameraTransforms() {
    
  }



  /* selects point which renders closest to the screen ptX */
  /* makes use of most recent rendering of the points */
  public int selectClosestPointIndex(double[] screenPtX, Dimension screenSize, Atz3D_Camera camera) {

    Atz3D_Element_Points_DataClosest data;

    data = selectClosestPoint(screenPtX, screenSize, camera);

    return data.keyPt;
  }


  /* selects point which renders closest to the screen ptX */
  /* makes use of most recent rendering of the points */
  public Atz3D_Element_Points_DataClosest selectClosestPoint(double[] screenPtX, Dimension screenSize, Atz3D_Camera camera) {

    int    i,j,k;
    int    N;
    int    key = -1;

    double dist_sq;

    double min_dist_sq;
    int    min_dist_I;

    double x,y;
    
    double X0[] = new double[3];
    double X1[] = new double[3];

    /* get the screen point */
    X0[0] = screenPtX[0];
    X0[1] = screenPtX[1];

    double[] ptsX_2D_view               = new double[numPts * (num_dim - 1)];
    double[] ptsX_2D_screen_transformed = new double[numPts * (num_dim - 1)];

    Atz3D_Element_Points_DataClosest data = new Atz3D_Element_Points_DataClosest();

    /* project the points to the camera view plane */
    camera.transformPts_3D_To_2D(ptsX, ptsX_2D_view);

    /* scale 2D points so they map to the effective screen size (range 0.0 to 1.0) */
    camera.transformPts_2D_To_Screen(ptsX_2D_view, screenSize, ptsX_2D_screen_transformed);
   
    /* loop over the points  and compute distances */
    min_dist_sq = -1;
    min_dist_I  = -1;    
    for (k = 0; k < numPts; k++) {

      X1[0] = ptsX_2D_screen_transformed[k*(num_dim - 1) + 0];
      X1[1] = ptsX_2D_screen_transformed[k*(num_dim - 1) + 1];
      /*X1[2] = ptsX_2D_view[k*num_dim + 2]; */

      dist_sq  = 0;
      dist_sq += (X1[0] - X0[0])*(X1[0] - X0[0]);
      dist_sq += (X1[1] - X0[1])*(X1[1] - X0[1]);

      if ((dist_sq < min_dist_sq) || (min_dist_sq < 0)) {
        min_dist_sq = dist_sq;
        min_dist_I  = k;
      }

    } /* end of k loop */

    if (min_dist_I >= 0) {
      key = keyList[min_dist_I];            
    }

    data.keyPt   = key;
    data.dist_sq = min_dist_sq;
    data.X0      = X0.clone();
    data.X1      = X1.clone();

    return data;
   
  }







}
