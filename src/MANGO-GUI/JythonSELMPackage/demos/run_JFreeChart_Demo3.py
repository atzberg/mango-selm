import sys
# Assumes setup in jythonStartup.py
#sys.path.append("/home/atzberg/quickAccess/dev_SELM_Builder/JFreeChart/jfreechart-1.0.13/lib/jcommon-1.0.16.jar")
#sys.path.append("/home/atzberg/quickAccess/dev_SELM_Builder/JFreeChart/jfreechart-1.0.13/lib/jfreechart-1.0.13.jar")
import org.jfree.chart
from org.jfree.chart import *
from org.jfree.data import *
from org.jfree.data.xy import *
import java.io

from org.jfree.chart.plot import *

series = XYSeries("XYGraph");

series.add(1, 1);
series.add(1, 2);
series.add(2, 1);
series.add(3, 9);
series.add(4, 10);

# Add the series to your data set
dataset = XYSeriesCollection();
dataset.addSeries(series);

#         Generate the graph
chart = ChartFactory.createXYLineChart("XY Chart", "x-axis", "y-axis", dataset, PlotOrientation.VERTICAL, True, True, False);
filePtr = java.io.File("chart.jpg");
ChartUtilities.saveChartAsJPEG(filePtr, chart, 500, 300);

# Display the chart in the GUI
p = ChartPanel(chart);

applSharedData = __main__.applSharedData;
applSharedData.FrameView_Application_Main.openInDockableWindowPanel("Plot 1", p, applSharedData.FrameView_Application_Main.jTabbedPane_Docksite_Windows_UpperRight);

#public class XYChartExample {
#    public static void main(String[] args) {
#        //         Create a simple XY chart
#        XYSeries series = new XYSeries("XYGraph");
#        series.add(1, 1);
#        series.add(1, 2);
#        series.add(2, 1);
#        series.add(3, 9);
#        series.add(4, 10);
#        //         Add the series to your data set
#        XYSeriesCollection dataset = new XYSeriesCollection();
#        dataset.addSeries(series);
#        //         Generate the graph
#        JFreeChart chart = ChartFactory.createXYLineChart("XY Chart", // Title
#                "x-axis", // x-axis Label
#                "y-axis", // y-axis Label
#                dataset, // Dataset
#                PlotOrientation.VERTICAL, // Plot Orientation
#                true, // Show Legend
#                true, // Use tooltips
#                false // Configure chart to generate URLs?
#            );
#        try {
#            ChartUtilities.saveChartAsJPEG(new File("C:chart.jpg"), chart, 500,
#                300);
#        } catch (IOException e) {
#            System.err.println("Problem occurred creating chart.");
#        }
#    }
#}
