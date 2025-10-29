## Air Quality Sensor Visualization
This project is dedicated to creating a set of air quality sensors that can be visualized on a time scale and location. 
Created by Reuben K and Elliott G

# Code
The code reads a file who's path is hard-written into **Main.java**. This will likely be changed in the future, but for further use it will need to be changed depending upon the input. 
The read file should contain data entries separated by commas (.csv) in the format yyyy-mm-dd hh:mm:ss, [analog value], [voltage]
Example:
```
2025-10-27 00:00:00,	4087,	4.0
2025-10-27 00:10:00,	4979,	4.1
```
# Visualization
The visualization is done through a new window, using paint components. The window has a graph with various latitude/longitudes, and a slider on the bottom to change the time.
