<p align="center">
<img src="https://github.com/atzberg/mango-selm/blob/master/images/docs/mango_selm_software.png" width="100%"> 
</p>

### Mango-Selm: Fluid-Structure Interactions and Fluctuating Hydrodynamics Simulation Package
Now available with Jupyter Notebook and Python interfaces for setting up models for SELM-LAMMPS simulations.

**Mango-Selm** is a package for performing fluid-structure interaction simulations in LAMMPS.  The package includes methods for
* hydrodyamic coupling between particles and microstructures handled though continuum stochastic fluid equations.
* implicit-solvent coarse-grained simulations, for example colloids / polymers / membranes.
* general fluid-structure interactions subject to thermal fluctuations (selms / immersed boundary models).
* shear boundary conditions for micro-rheology studies, for example rheology of soft materials / complex fluids.
* deterministic simulations are also possible by setting temperature parameter to zero.

Allows for SELM, Immersed Boundary Methods, and related hydrodynamic solvers to be used in conjunction with LAMMPS simulations.  LAMMPS is an optimized molecular dynamics package in C/C++ providing many interaction potentials and analysis tools for modeling and simulation.  Interaction methods include particle-mesh electrostatics, common coarse-grained potentials, many-body interactions, and others.

**Quick Start**

To install in Python use

```pip install selm-lammps```

To test the package installed run 

```python -c "from selm_lammps.tests import t1; t1.test()"```

Pre-compiled binaries for (Debian 9+/Ubuntu and Centos 7+, Python 3.6+).

To update to latest version use 
```pip install --upgrade selm-lammps```

__For example notebooks__ and model scripts, see the [./examples folder](https://github.com/atzberg/mango-selm/tree/master/examples).  

**Other ways to install the package**
For more information on other ways to install or compile the package, please see the documentation page http://doc.mango-selm.org/

**Python/Juputer Notebooks for Modeling and Simulations** 

Immersed Boundary Methods and SELM Models now easily can be set up using Python or Jupyter Notebooks.  See the documentation page and tutorial video for details, http://doc.mango-selm.org/

**Downloads:** The source package and additional binaries are available at the webpage: http://mango-selm.org/

---
Please cite the paper below when referencing this package:
```
@article{atz_selm_lammps_fluct_hydro,
title = {Fluctuating Hydrodynamics Methods for Dynamic Coarse-Grained Implicit-Solvent Simulations in LAMMPS},
author = {Wang, Y. and Sigurdsson, J. K. and Atzberger, P. J.},
journal = {SIAM Journal on Scientific Computing},
volume = {38},
number = {5},
pages = {S62-S77},
year = {2016},
doi = {10.1137/15M1026390},
URL = {https://doi.org/10.1137/15M1026390},
}  
```
----

__Additional Information__ <br>
http://atzberger.org/
