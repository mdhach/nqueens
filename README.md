# NQueens
This is a simple project that involves using the NQueens puzzle.

### The Puzzle

The NQueens puzzle involves an *n* amount of Queens on an *n*x*n* board. The idea is to place the Queens
in a way such that none of them will conflict with one another.

### The Algorithm

This program makes use of the Hill Climbing algorithm to solve the puzzle. This is an iterative algorithm
that attempts to solve a problem by incrementing multiple solution states and determining their heuristic values.
The current state will be compared to neighboring states with a lower heuristic value. If no neighboring state with
a lower heuristic can be determined, a hard reset will be initiated.

### Output

This program will output a text representation of the puzzle.
  * 1: Position of a Queen
  * 0: Empty location
