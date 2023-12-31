# Results Package

## Results Class

The `Results` class represents a single cell in the table for a specific algorithm.
It contains a string representation of the runtime and the developed vertices for a specific sample.

`data`: A string representation of the runtime and developed vertices for a specific sample.
### Methods
`getData()`: Returns the data string.
`setData(String data)`: Sets the data string.

## Test Class
The Test class extends the abstract Tests class and holds key methods for running tests and collecting results.

### Methods
1) `run_tests(int graph_size, int num_of_samples, int n)`: Runs tests for a specified graph size, number of samples, and number of steps from the final state.
2) `step(int n, int graph_size)`: Represents one step in the test, generating a random graph and running the algorithm.
3) `clear()`: Clears the test data.
4) `print_results()`: Prints the results based on user input.
5) `get_avg_and_visualize_results()`: Computes and visualizes the average results.
6) `visualize_results()`: Visualizes the results.
7) `get_samples_table()`: Gets 5 samples and visualizes the results.


## Tests Class (Abstract)
The Tests class is an abstract class that holds key methods for running tests and collecting results.

### Methods
1) `clear()`: Clears the test data.
2) `run_tests(int graph_size, int num_of_samples, int n)`: Runs tests for a specified graph size, number of samples, and number of steps from the final state (to be implemented by subclasses).
3) `get_avg_and_visualize_results()`: Computes and visualizes the average results (to be implemented by subclasses).
4) `print_results()`: Prints the results (to be implemented by subclasses).
5) `visualize_results()`: Visualizes the results (to be implemented by subclasses).
6) `get_samples_table()`: Gets 5 samples and visualizes the results (to be implemented by subclasses).

