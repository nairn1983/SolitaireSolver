SolitaireSolver
===============

This is a solver for the standard English version of peg solitaire. See http://en.wikipedia.org/wiki/Peg_solitaire

If built and run without any changes, this will produce the first solution it can find to a puzzle with 32 pegs. The
solution found will have 31 moves and is not optimal (the shortest solution has 18). The code can be changed to run
for other standard boards and also to attempt to find all possible solutions.

Moves are given in the format (x, y, dir), meaning move the peg at row y, column x (with (1,1) the top-left of an
enclosing square grid, which itself is not a valid position) in the specified direction.

General instructions for use are:

* Run the Maven install goal, using "mvn install". This uses the assembly plugin to create two jars in the target
directory: one with dependencies included; one without.
* From the target directory, run the command "java -jar SolitaireSolver-jar-with-dependencies.jar"
