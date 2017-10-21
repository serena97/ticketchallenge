
The java program accepts a user location as a pair of coordinates, and returns a list of the 5 closest events, along with the cheapest ticket price for each event. To model reality, the data of the events (id, ticket prices, location) are generated randomly if there's nothing stored, and then saved. If there's already data saved, then the program doesn't generate tickets randomly again but retrieves the saved data. 

# Setting up the program 

Prerequisites: Make sure you have java installed on your computer! :)

1. clone the files 
2. open terminal and go to file directory that contains the cloned files
3. type ```javac Main.java``` and enter
4. type ```java Main``` and enter, now you can input coordinates

# Q & A
1. How might you change your program if you needed to support multiple events at the
same location?

  A Set will not be used to allow duplication of locations for different events. The randomly generated coordinates will be directly inputted into the events object without a check of location duplication. The events with the same location thus same relative distance from the inputted coordinates will be outputted on the same line.


2. How would you change your program if you were working with a much larger world
size?

  More events would be seeded and coordinates will be generated from a bigger range

