print "======================================================"
print "Startup Script for SELM Jython Intepreter"
print " "
print "Written by Paul J. Atzberger"
print "Date: March, 2011."
print "======================================================"
print " "

# == Place any commands required to initialize the interpreter environment 
# in this script.

def addPathUnique(pathname):
  # only add the path if not it is not already there
  flagAddPath = 1;
  for path in sys.path:
    if (path == pathname):
      flagAddPath = 0;

  if (flagAddPath):
    sys.path.append(pathname);

# == Determine path of current script and add to the path
import sys
import os

currentScript = sys.argv[0];
pathname      = os.path.dirname(sys.argv[0]);
addPathUnique(pathname);

pathnameDemos  = os.path.join(pathname,'demos');
addPathUnique(pathnameDemos);

pathnameDemos  = os.path.join(pathname,'JythonSELMPackage');
addPathUnique(pathnameDemos);


flagSetupJFreeChart = True;
if flagSetupJFreeChart:
  # add the jfreechart path 
  pathnameJFreeChart = os.path.join(pathname,'jfreechart-1.0.13');

  addPathUnique(pathnameJFreeChart);

  jarPath = pathnameJFreeChart;
  jarPath = os.path.join(jarPath,'lib');
  jarPath = os.path.join(jarPath,'jcommon-1.0.16.jar');
  addPathUnique(jarPath);

  jarPath = pathnameJFreeChart;
  jarPath = os.path.join(jarPath,'lib');
  jarPath = os.path.join(jarPath,'jfreechart-1.0.13.jar');
  addPathUnique(jarPath);

  jarPath = pathnameJFreeChart;
  jarPath = os.path.join(jarPath,'jfreechart-1.0.13-demo.jar');
  addPathUnique(jarPath);

# == Get the commmon shared application data from GUI and load in interpreter
import org.atzberger.application.selm_builder
import __main__

applSharedData = org.atzberger.application.selm_builder.Atz_Application_Data_Communication.getApplSharedData();

# -- Make the applSharedData accessible from the main namespace of the interpreter
__main__.applSharedData = applSharedData

# == Setup help for the interactive interpreter
#from pydoc import help   # enable later, since takes some time to load

# == Import standard packages we would like to use
import __builtin__
__main__.__builtin__ = __builtin__

import sys
__main__.sys = sys

import os
__main__.os = os

import random
__main__.random = random

import math
__main__.math = math

import fileinput
__main__.fileinput = fileinput

# utility commands for working with java based arrays
import jarray
__main__.jarray = jarray

# script to print our object contents for display (from Python recipes)
import printobj
__main__.disp     = printobj.disp
__main__.printobj = printobj.printobj

# import a structure type for storing generic data (just empty python class)
# call this data type "struct" in the global namespace.
from myStruct import myStruct		  
__main__.struct = myStruct

# import the model build package 
import modelBuildPackage1
__main__.modelBuildPackage1 = modelBuildPackage1

from modelBuildPackage1 import *


  
