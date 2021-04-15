# Build model
print("============================================================================================");
print("Building Model ");
print("--------------------------------------------------------------------------------------------");

import sys
import os
import os.path
import org.atzberger.application.selm_builder
from modelBuildPackage1 import *

# Clear the project space
newProject();

# Get current directory
#currentScript = sys.argv[0];
#print("currentScript = " + pathnameBase);
#pathnameBase  = os.path.dirname(sys.argv[0]);
#print("pathnameBase = " + pathnameBase);

#pathnameBase  = os.getcwd();

# Try to use execfile_script (set before call) or otherwise use the current working directory.
try:
  pathnameBase = os.path.dirname(execfile_script);
except:
  pathnameBase = os.getcwd();

print("Base pathname being used is " + pathnameBase); 

#print("__file__ = " + os.path.dirname(os.path.__file__));

# Hide display of physical units
setModelPhysicalUnitsVisible(False);

# Setup the Lagrangian degrees of freedom

print("Setting up Lagrangian Degrees of Freedom ");

lagrangian_A = pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE();
lagrangian_A.setName("Bead A");
lagrangian_A.removeAllPoints();
lagrangian_A.addPoints([1.0,0.0,0.0]);
lagrangian_A.setTypeID(1);
lagrangian_A.setAtomID([1]);
lagrangian_A.setMoleculeID([1]);
lagrangian_A.setAtomMass([1.0]);
lagrangian_A.setPlotColor(255,0,0);
lagrangian_A.setVisible(True);
lagrangian_A.setFlagWriteVTK(True);

addLagrangianToList(lagrangian_A);

lagrangian_B = pySELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE();
lagrangian_B.setName("Bead B");
lagrangian_B.removeAllPoints();
lagrangian_B.addPoints([-1.0,0.0,0.0]);
lagrangian_B.setTypeID(2);
lagrangian_B.setAtomID([1]);
lagrangian_B.setMoleculeID([1]);
lagrangian_B.setAtomMass([1.0]);
lagrangian_B.setPlotColor(0,0,255);
lagrangian_B.setVisible(True);
lagrangian_B.setFlagWriteVTK(True);

addLagrangianToList(lagrangian_B);

refreshGUI();

# Setup the Eulerian degrees of freedom

print("Setting up Eulerian Degrees of Freedom ");

eulerian = findEulerian("LAMMPS_SHEAR_UNIFORM1_FFTW3");

eulerian.setMeshDeltaX(0.2);
eulerian.setMeshCenterX0([0.0,0.0,0.0]);
eulerian.setNumMeshPtsPerDir([16,16,16]);
eulerian.setPlotColor(0,0,0);
eulerian.setVisible(True);
eulerian.setVisible(True);
eulerian.setFlagWriteFluidVelocityVTK(True);
eulerian.setFlagWriteFluidPressueVTK(False);
eulerian.setFlagWriteFluidForceVTK(True);

refreshGUI();

# Setup the Coupling Operators

print("Setting up Coupling Operators ");

couplingOp = pySELM_CouplingOperator_TABLE1("T_KERNEL_1");
couplingOp.setName("Bead-Fluid Coupling");
couplingOp.setVisible(True);
opData     = couplingOp.getOperatorData();
filename   = os.path.join(pathnameBase,'data');
filename   = os.path.join(filename,'Eta1.SELM_CouplingOperator_weightTable');
opData.setWeightTableFilename(getJavaStringPath(filename));
opData.setPlotColor(0,0,255);

addCouplingOpToList(couplingOp);

refreshGUI();

# Setup the Interactions

print("Setting up Interactions ");

interaction = pySELM_Interaction_LAMMPS_PAIR_COEFF();
interaction.setName("Bead Interaction");
filename   = os.path.join(pathnameBase,'data');
filename   = os.path.join(filename,'Hamonic_Potential.LAMMPS_pair_coeff_table_linear');
interaction.setTableFilename(getJavaStringPath(filename));
interaction.setEnergyEntryName("HARMONIC_BOND");

pairList_lagrangianI1 = [];
pairList_typeI1       = [];
pairList_lagrangianI2 = [];
pairList_typeI2       = [];
coefficient = [];

pairList_lagrangianI1.append(lagrangian_A);
pairList_typeI1.append(1);
pairList_lagrangianI2.append(lagrangian_B);
pairList_typeI2.append(2);
coefficient.append(1.0);

pairList_lagrangianI1.append(lagrangian_A);
pairList_typeI1.append(1);
pairList_lagrangianI2.append(lagrangian_A);
pairList_typeI2.append(1);
coefficient.append(1.0);

pairList_lagrangianI1.append(lagrangian_B);
pairList_typeI1.append(2);
pairList_lagrangianI2.append(lagrangian_B);
pairList_typeI2.append(2);
coefficient.append(1.0);

numPairs = pairList_lagrangianI1.__len__();
interaction.setPairData(numPairs, pairList_lagrangianI1, pairList_typeI1, pairList_lagrangianI2, pairList_typeI2, coefficient);

interaction.setVisible(True);
interaction.setPlotColor(0,100,0);

addInteractionToList(interaction);

# Setup the Integrator

print("Setting up Integrator ");

integrator = findIntegrator("LAMMPS_SHEAR_QUASI_STEADY1_FFTW3");
integrator.setTimeStep(1e-4);
integrator.setNumberTimeSteps(10000);
integrator.setFluidViscosityMu(6.02214199e5);
integrator.setFluidDensityRho(6.02214199e2);
integrator.setBoltzmannKB(8.3144721451e3);
integrator.setTemperature(2.9815e2);
integrator.setFlagStochasticDriving(True);
integrator.setFlagIncompressibleFluid(True);
integrator.setFlagShearMode("RM_SHEAR1");
integrator.setShearRate(0.0);
integrator.setShearVelDir(0);
integrator.setShearDir(2);
integrator.setSaveSkip(1);

refreshGUI();

# Setup general parameters

print("Setting up General Parameters ");

setModelBaseFilename("Dimer");
setModelDescription("Model of dimers that interact.  Generated by runBuildModel_FENE1");
setModelRandomSeed(12345);

setEulerianSelected(eulerian.getName());
setIntegratorSelected(integrator.getName());

refreshGUI();

# Setup the file generator

print("Setting up File Generator ");

fileGenerator = org.atzberger.application.selm_builder.Atz_File_Generator_LAMMPS_USER_SELM1(applSharedData);
setModelFileGenerator(fileGenerator);

# Issue brief warning so everything works

#print(" ");
#print("When running this script be sure the current working directory from os.getcwd()");
#print("is the same as the directory containing this script.  Otherwise some of the data ");
#print("files will not be found.  The current working directory can be set using ");
#print("os.chdir(path).");
#print(" ");


# Annouce we are done
print("Done");
print("============================================================================================");



