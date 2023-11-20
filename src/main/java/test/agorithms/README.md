# Algorithms Package

## AStar Class

The `AStar` class represents the A* search algorithm. It considers the initialization of the graph, heuristic function prompt, and starting node.

### Constructor

```java
public AStar(Node start_node, Graph graph, String prompt, boolean single_run)
```
1) `start_node:` The starting node for the algorithm.
2)`graph:` The graph on which the search is conducted.
3) `prompt:` The heuristic function prompt.
4) `single_run:` A boolean indicating if it's a single run.

## Methods
1)`traverse():` Performs the A* search traversal.
2)`num_of_vertices():` Returns the number of developed vertices.
3) `Name():` Returns the name of the algorithm.
4) `getStart_node():` Returns the starting node.
5) `print_path():` Prints the solution path.
6) `calc_costs_for_neighbors(Set<Node> neighbors):` Calculates costs for neighbors.
7) `generate_minimum_cost(Set<Node> neighbors):` Generates the minimum cost among neighbors.
8) `get_cost(Node neighbor):` Gets the cost for a specific node.
9) `check_for_name():` Checks for the algorithm name.

### Algorithms Interface
The Algorithms interface holds essential methods for all different traversal algorithms.

## Methods
1)`traverse():` Performs the traversal.
2)`num_of_vertices():` Returns the number of developed vertices.
3)`Name():` Returns the name of the algorithm.
4)`getStart_node():` Returns the starting node.

### BFS Class
The BFS class represents the breadth-first search algorithm.

1) `startNode:` The starting node for the algorithm.
2) `graph:` The graph on which the search is conducted.
3) `single_run:` A boolean indicating if it's a single run.

## Methods
1) `traverse():` Performs the BFS traversal.
2) `num_of_vertices():` Returns the number of developed vertices.
3) `Name()`: Returns the name of the algorithm.
4) `printPath(Node current):` Prints the solution path.

