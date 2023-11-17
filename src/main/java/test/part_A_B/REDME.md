# Graph Package

The `Graph` package provides classes and functionality to create and manipulate graphs for puzzle-solving algorithms. The package includes a `Graph` class, a `Node` class, and a `Point` class.

## Graph Class

The `Graph` class represents a graph structure with nodes and connections. It supports operations such as adding nodes, creating edges, removing nodes, and generating random graphs. The graph can be used for various applications, including puzzle-solving algorithms.

### Properties:

- `connections`: A map representing the connections between nodes in the graph.
- `final_state`: The final state of the puzzle.
- `size`: The size of the puzzle.
- `id`: An identifier for nodes in the graph.

### Methods:

- `getId()`: Get the current identifier for nodes.
- `get_increase_Id()`: Increment and get the new identifier for nodes.
- `getFinal_state()`: Get the final state of the puzzle.
- `getSize()`: Get the size of the puzzle.
- `getConnections()`: Get the connections between nodes.
- `getNodeByID(int id)`: Get the node by its identifier.
- `addNode()`: Add a new node to the graph.
- `add_generated_Node(Node node)`: Add a pre-generated node to the graph.
- `removeNode(int id)`: Remove a node from the graph.
- `addEdge(int id1, int id2)`: Add an edge between two nodes.
- `check_connection(Node node1, Node node2)`: Check if a connection between two nodes is valid.
- `getNeighbors(int id)`: Get the neighbors of a node.
- `generate_random_graph(int size)`: Generate a random graph with the given size.
- `get_states(Node node)`: Generate the moving states for a given node.
- `initialize_puzzle(Node node, int[][] puzzle, Set<Integer> nums)`: Initialize the puzzle of a node.
- `clonePuzzle(int[][] puzzle)`: Clone a puzzle.
- `swapElements(int[][] puzzle, int row1, int col1, int row2, int col2)`: Swap elements in a puzzle.
- `generate_random_number(int numOfElements, Set<Integer> nums)`: Generate a random number excluding those in the set.
- `print_graph()`: Print the graph and its nodes.
- `get_random_node_for_bfs()`: Get a random node from the graph for BFS.
- `generate_n_steps_from_final_state(int n)`: Generate n steps from the final state using BFS.
- `menu()`: A menu-driven method to interactively build and manipulate the graph.

## Node Class

The `Node` class represents a node in the graph. It includes information about the puzzle state, whether it has been visited, an identifier, the empty point, and the node's state.

### Properties:

- `size`: The size of the puzzle.
- `puzzle`: The puzzle state.
- `visited`: A flag indicating whether the node has been visited.
- `ID`: The identifier of the node.
- `empty_point`: The empty point in the puzzle.
- `state`: The state of the puzzle.

### Methods:

- `getState()`: Get the state of the node.
- `getSize()`: Get the size of the puzzle.
- `isVisited()`: Check if the node has been visited.
- `setVisited(boolean visited)`: Set the visited status.
- `setID(int ID)`: Set the identifier of the node.
- `getPuzzle()`: Get the puzzle state.
- `setPuzzle(int[][] puzzle)`: Set the puzzle state.
- `setEmpty_point(Point empty_point)`: Set the empty point.
- `getEmpty_point()`: Get the empty point.
- `initialPuzzle()`: Initialize the puzzle interactively.
- `print_puzzle()`: Print the puzzle state.
- `getID()`: Get the identifier of the node.

## Point Class

The `Point` class represents a point in the puzzle grid.

### Properties:

- `x`: The x-coordinate of the point.
- `y`: The y-coordinate of the point.

### Methods:

- `getX()`: Get the x-coordinate.
- `getY()`: Get the y-coordinate.
- `getPoint()`: Get the coordinates as a list.
- `check_coordinate(Point other_point)`: Check if there is an edge between two points.
