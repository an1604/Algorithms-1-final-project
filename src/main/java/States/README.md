## State Class
The `State` class serves as the base class for representing puzzle states. It encapsulates common properties and functionality shared by regular puzzle states and the final goal state.

### Properties:
- `puzzleArray`: Represents the puzzle state as a 2D array.
- `puzzleRepresentation`: String representation of the puzzle state.
- `size`: Size of the puzzle (e.g., 4x4 or 5x5).

### Abstract Methods:
- `initializePuzzleArray()`: Abstract method to initialize the puzzle array.
- `initializePuzzleRepresentation()`: Abstract method to initialize the puzzle representation.
- `isGoalState()`: Abstract method to check if the state is the goal state.

### Methods:
- `getSize()`: Returns the size of the puzzle.
- `getPuzzleArray()`: Returns the 2D array representing the puzzle.
- `getPuzzleRepresentation()`: Returns the string representation of the puzzle.

## RegularState Class
The `RegularState` class extends the `State` class and represents a regular puzzle state.

### Additional Properties and Methods:
- May include properties and methods specific to regular puzzle states.

### Overrides:
- `initializePuzzleArray()`: Implements puzzle array initialization for regular states.
- `initializePuzzleRepresentation()`: Implements puzzle representation initialization for regular states.
- `isGoalState()`: Implements goal state check for regular states.

## FinishState Class
The `FinishState` class extends the `State` class and represents the final goal state of the puzzle.

### Singleton Design:
- Utilizes the Singleton pattern to ensure a single instance of the final state.

### Additional Properties and Methods:
- May include properties and methods specific to the final goal state.

### Initialization:
- Initializes the puzzle array and representation to reflect the solved puzzle state.
