#
# Package for generating plots of data sets based on JFreeChart.  
#
# Author: Paul J. Atzberger
#
#

# ================== Import modules =========================
print("Plot Package 1 : Authored by Paul J. Atzberger ");

# -- Determine path of current script and add to the path
import org.jfree.chart
from org.jfree.chart import *
from org.jfree.data import *
from org.jfree.data.xy import *
import java.io
import java.awt.Color

from org.jfree.chart.plot import *

def createXYLineChart(title="XY Chart", xLabel = "x-axis", yLabel = "y-axis", 
                     dataset = None, orientation = PlotOrientation.VERTICAL, 
                     flagShowLegend = True, flagUseToolTips = True, flagURLS = False):

  if (dataset == None):
    dataset = XYSeriesCollection();
    
  chart      = ChartFactory.createXYLineChart(title, xLabel, yLabel, dataset, orientation, flagShowLegend, flagUseToolTips, flagURLS);
  chart.setBackgroundPaint(java.awt.Color.white);    
  chartPanel = ChartPanel(chart);
  
  chartPanel.setFillZoomRectangle(True);
  chartPanel.setMouseWheelEnabled(True);
      
  plot = chart.getPlot();
  plot.setBackgroundPaint(java.awt.Color.white);
  #plot.setDomainGridlinePaint(java.awt.Color.black);
  #plot.setRangeGridlinePaint(java.awt.Color.black);
  #plot.setAxisOffset(RectangleInsets(5.0, 5.0, 5.0, 5.0));
  #plot.setDomainCrosshairVisible(True);
  #plot.setRangeCrosshairVisible(True);
       
  renderer = plot.getRenderer();
  #if (r instanceof XYLineAndShapeRenderer) {            
  renderer.setBaseShapesVisible(True);
  renderer.setBaseShapesFilled(True);
  renderer.setDrawSeriesLineAsPath(True);
  #}
  
  return chartPanel;
  
  
def setChartTitle(chartPanel, val):
  chartPanel.getChart().setTitle(val);
   
def setChartRangeGridlinesVisible(chartPanel, val):  
  chartPanel.getChart().getXYPlot().setRangeGridlinesVisible(val);
  
def setChartRangeGridlineColor(chartPanel, val1, val2, val3):
  c = java.awt.Color(val1,val2,val3);  
  chartPanel.getChart().getXYPlot().setRangeGridlinePaint(c);

def setChartDomainGridlinesVisible(chartPanel, val):  
  chartPanel.getChart().getXYPlot().setDomainGridlinesVisible(val);
  
def setChartDomainGridlineColor(chartPanel, val1, val2, val3):
  c = java.awt.Color(val1,val2,val3);  
  chartPanel.getChart().getXYPlot().setDomainGridlinePaint(c);
  
def setChartDomainLabel(chartPanel, val):
  v = chartPanel.getChart().getXYPlot().getDomainAxis();
  v.setLabel(val);
    
def setChartRangeLabel(chartPanel, val):
  v = chartPanel.getChart().getXYPlot().getRangeAxis();
  v.setLabel(val);

def createXYSeries(X, Y, label = ""):      
  series = XYSeries(label);
  
  N = X.__len__();  
  for k in range(1,N):
    series.add(X[k],Y[k]);
    
  return series;
  
def addXYSeriesToChart(chartPanel, series):
  dataset = chartPanel.getChart().getXYPlot().getDataset();  # assumes XYSeriesCollection returned
  dataset.addSeries(series);
  
      
  
