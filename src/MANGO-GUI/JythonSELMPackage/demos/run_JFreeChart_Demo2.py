from plotPackage1 import *
import random 

# Generate the data 
random.seed(1);

N     = 100;
mu    = 10;
sigma = 3;
X     = [];
Y1    = [];
Y2    = [];
for k in range(1,N):
  X.append(k)
  Y1.append(random.gauss(-mu, sigma));
  Y2.append(random.gauss(mu, sigma));
    
# Create the chart
chartPanel = createXYLineChart();  

# Create the series data
series1 = createXYSeries(X,Y1,"Data set 1");
series2 = createXYSeries(X,Y2,"Data set 2");
 
addXYSeriesToChart(chartPanel,series1);
addXYSeriesToChart(chartPanel,series2);
 
 
# Open the chart in display window 
openInDockableWindow(chartPanel,"Test Plot","UpperRight");

# Modify the annotation of the plot
setChartTitle(chartPanel,"Test Plot");
setChartDomainLabel(chartPanel,"x");
setChartRangeLabel(chartPanel,"y");

setChartDomainGridlinesVisible(chartPanel,True);
setChartDomainGridlineColor(chartPanel,100,100,100);

setChartRangeGridlinesVisible(chartPanel,True);
setChartRangeGridlineColor(chartPanel,100,100,100);

