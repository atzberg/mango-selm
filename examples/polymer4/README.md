<p align="left">
<img src="doc_img/view1_8_4.gif" width="20%"> 
</p>

__Polymer with Hydrodynamic Correlations and Shear Boundary Conditions__

Performs a simulation of a polymer with thermal fluctuations and hydrodynamic correlations.  
The polymer is also subjected to shearing boundary conditions.  

To run the model using selm-lammps and python interface use 

```python ./simulation_using_python.py```

To run the model using the Jupyter notebook load the file 

simulation_polymerXX.ipynb

The model geometry and simulation conditions are set in the script files.  The SELM parameters are controlled by the XML files in the ./ModelXXX directory.  

For example, the shear rate is set in the XXX.SELM_Integrator file.  The template XML files are copied by the scripts into the target simulation directory 
each time the model is run.  Adjust the output directory using codes near tag @base_dir.

Edit the ModelXXX in the script to use each of the different example models.  
* Model1: polymer subject to uniform shear.
* Model2: polymer subject to oscillating shear.

To visualize run the script ./vis_pv_XXX.sh script in the output directory which opens the model data in a Paraview session.

See the paper below for more information on the underlying hydrodynamic model, computational methods, and shearing boundary conditions.

__Incorporating Shear into Stochastic Eulerian Lagrangian Methods for Rheological Studies of Complex Fluids and Soft Materials,__ P.J. Atzberger, Physica D, Vol. 265, pg. 57–70, (2013), http://doi.org/10.1016/j.physd.2013.09.002

```
@Article{AtzbergerShear2013,
  Title                    = {Incorporating shear into stochastic Eulerian-Lagrangian methods for rheological studies of complex fluids and soft materials },
  Author                   = {Paul J. Atzberger},
  Journal                  = {Physica D: Nonlinear Phenomena },
  Year                     = {2013},
  Pages                    = {57 - 70},
  Volume                   = {265},  
  Doi                      = {http://doi.org/10.1016/j.physd.2013.09.002},
  ISSN                     = {0167-2789},
}
```
----

For additional examples see the [examples folder](https://github.com/atzberg/mango-selm/tree/master/examples) or information on http://mango-selm.org/
