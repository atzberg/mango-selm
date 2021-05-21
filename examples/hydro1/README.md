<p align="left">
<img src="doc_img/hydro1_view1_1.gif" width="20%"> 
</p>

<!-- ![Image](doc_img/ex1.png) -->

__Particles and Hydrodynamic Flow__

Performs a basic simulation of two particles moving through the fluid and associated hydrodynamic flow.

To run the model using selm-lammps and python interface use 

```python ./simulation_using_python.py```

To run the model using the Jupyter notebook load the file 

simulation_hydroXX.ipynb

The model geometry and simulation conditions are set in the script files.  The SELM parameters are controlled by the XML files in the ./ModelXXX directory.  

Edit the ModelXXX in the script to use each of the different example models.  
* Model1: smaller resolution fluid mesh.
* Model2: larger resolution fluid mesh.

See the paper below for more information on the underlying hydrodynamic model and computational methods.

__Stochastic Reductions for Inertial Fluid-Structure Interactions Subject to Thermal Fluctuations,__ G. Tabak and P.J. Atzberger, SIAM J. Appl. Math., 75(4), 1884–1914, (2015), https://doi.org/10.1137/15M1019088

```
@Article{AtzbergerSELM2015,
  Title                    = {Stochastic Reductions for Inertial Fluid-Structure Interactions Subject to Thermal Fluctuations}, 
  Author                   = {G. Tabak and P.J. Atzberger},
  Journal                  = {SIAM Journal on Applied Mathematics},
  Year                     = {2015},
  Pages                    = {1884--1914},
  Volume                   = {75},  
  Number                   = {4},
  Doi                      = {https://doi.org/10.1137/15M1019088},
}
```

---

For additional examples see the [examples folder](https://github.com/atzberg/mango-selm/tree/master/examples) or information on http://mango-selm.org/
