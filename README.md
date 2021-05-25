<p align="center">
<img src="https://github.com/atzberg/mango-selm/blob/master/images/docs/mango_selm_software.png" width="100%"> 
</p>

### Mango-Selm: Fluid-Structure Interactions and Fluctuating Hydrodynamics Simulation Package
Now available with Jupyter notebooks and Python scripts for readily setting up models and simulations.

**Mango-Selm** is a Python/C++ package for performing fluid-structure interaction simulations in LAMMPS.  The package includes methods for
* hydrodyamic coupling between particles and microstructures handled though continuum stochastic fluid equations.
* implicit-solvent coarse-grained simulations, for example colloids / polymers / membranes.
* general fluid-structure interactions subject to thermal fluctuations (selms / immersed boundary models).
* shear boundary conditions for micro-rheology studies, for example rheology of soft materials / complex fluids.
* deterministic simulations are also possible by setting temperature parameter to zero.

Allows for SELM, Immersed Boundary Methods, and related hydrodynamic solvers to be used in conjunction with LAMMPS simulations.  LAMMPS is an optimized molecular dynamics package in C/C++ providing many interaction potentials and analysis tools for modeling and simulation.  Interaction methods include particle-mesh electrostatics, common coarse-grained potentials, many-body interactions, and others.

**Quick Start**

To install pre-compiled package for Python use

```pip install selm-lammps```

To test the package installed run 

```python -c "from selm_lammps.tests import t1; t1.test()"```

Pre-compiled binaries for (Debian 9+/Ubuntu and Centos 7+, Python 3.6+).

To update to latest version use 
```pip install --upgrade selm-lammps```

__For example models, notebooks,__ and scripts, see the [examples folder](https://github.com/atzberg/mango-selm/tree/master/examples).  

**Other ways to install the package**
For running prototype models and simulations on a desktop, such as Windows and MacOS, we recommend using Docker container.  For example, install [Docker Desktop](https://docs.docker.com/desktop/), or docker for linux, and then load a standard ubuntu container by using in the terminal ```docker run -it ubuntu:20.04 /bin/bash```  You can then use ```apt update; apt install python3-pip```, and can then pip install and run the simulation package as above.  Note use command ```python3``` in place of ```python``` when calling scripts.  

For more information on other ways to install or compile the package, please see the documentation page http://doc.mango-selm.org/

**Python/Juputer Notebooks for Modeling and Simulations** 

Immersed Boundary Methods and SELM Models now easily can be set up using Python or Jupyter Notebooks.  See the documentation page and tutorial video for details, http://doc.mango-selm.org/

**Downloads:** The source package and additional binaries are available at the webpage: http://mango-selm.org/

---
Please cite the paper below when referencing this package:
```
@article{atz_selm_lammps_fluct_hydro,
title = {Fluctuating Hydrodynamics Methods for Dynamic Coarse-Grained 
Implicit-Solvent Simulations in LAMMPS},
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

__Mailing List for Future Updates and Releases__

Please join the mailing list for future updates and releases [here](https://forms.gle/R1sQCPkUcbtVRb5Q7).

__Bugs or Issues__

If you encounter any bugs or issues please let us know by providing information [here](https://forms.gle/432eGjn2UYFVodqo8).

__Please submit usage and citation information__

If you use this package or related methods, please let us know by submitting information [here](https://forms.gle/CukiUvkqpszLuCgU6).  
This helps us with reporting and with further development of the package.  Thanks.

__Acknowledgements__
We gratefully acknowledge support from NSF Career Grant DMS-0956210, NSF Grant DMS-1616353, DOE ASCR CM4 DE-SC0009254, and DOE Grant ASCR PHILMS DE-SC0019246.

__Additional Information__ <br>
http://atzberger.org/
