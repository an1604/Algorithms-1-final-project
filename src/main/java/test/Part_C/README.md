# BFS Class

The `BFS` class provides functionality for traversing a graph using Breadth-First Search (BFS). It is specifically designed for puzzle-solving algorithms and works with a `Graph` and `Node` objects.

## Properties:

- `start_node`: The starting node for the BFS traversal.
- `graph`: The graph to be traversed.
- `size`: The size of the puzzle.
- `parentMap`: A map to store the parent-child relationship.
- `final_state`: The final state of the puzzle.

## Methods:

- `BFS(Node startNode, Graph graph)`: Constructor to initialize the BFS with a starting node and graph.
- `traverse()`: Performs the BFS traversal on the graph, finding a path to the goal state.
- `printPath(Node current)`: Prints the path from the starting node to the goal state.

### Explanation:

- The `traverse` method uses BFS to explore nodes in the graph, enqueueing unvisited neighbors and updating the `parentMap`.
- The traversal is limited to 5000 steps to prevent infinite loops.
- If the goal state is found during traversal, the `printPath` method is called to print the solution path.

### Note:
- The parentMap is utilized to construct the solution path by traversing back through the parent-child relationship.
- The solution path is then printed in reverse order, from the starting node to the goal state.
