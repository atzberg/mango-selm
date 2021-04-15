import sys
# assumes paths added to sys.path during jythonStartup.py
#sys.path.append("/home/atzberg/quickAccess/dev_SELM_Builder/JFreeChart/jfreechart-1.0.13/lib/jcommon-1.0.16.jar")
#sys.path.append("/home/atzberg/quickAccess/dev_SELM_Builder/JFreeChart/jfreechart-1.0.13/lib/jfreechart-1.0.13.jar")
import org.jfree.chart
from org.jfree.chart import *
from org.jfree.data import *
from org.jfree.data.xy import *
import java.io

from org.jfree.chart.plot import *

# Create demo chart
q = org.jfree.chart.demo.TimeSeriesChartDemo1("Test");
p = q.createDemoPanel();

applSharedData = __main__.applSharedData;
applSharedData.FrameView_Application_Main.openInDockableWindowPanel("Plot 1", p, applSharedData.FrameView_Application_Main.jTabbedPane_Docksite_Windows_UpperRight);


