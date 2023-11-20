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
`run_tests(int graph_size, int num_of_samples, int n)`: Runs tests for a specified graph size, number of samples, and number of steps from the final state.
`step(int n, int graph_size)`: Represents one step in the test, generating a random graph and running the algorithm.
`clear()`: Clears the test data.
`print_results()`: Prints the results based on user input.
`get_avg_and_visualize_results()`: Computes and visualizes the average results.
`visualize_results()`: Visualizes the results.
`get_samples_table()`: Gets 5 samples and visualizes the results.


## Tests Class (Abstract)
The Tests class is an abstract class that holds key methods for running tests and collecting results.

### Methods
`clear()`: Clears the test data.
`run_tests(int graph_size, int num_of_samples, int n)`: Runs tests for a specified graph size, number of samples, and number of steps from the final state (to be implemented by subclasses).
`get_avg_and_visualize_results()`: Computes and visualizes the average results (to be implemented by subclasses).
`print_results()`: Prints the results (to be implemented by subclasses).
`visualize_results()`: Visualizes the results (to be implemented by subclasses).
`get_samples_table()`: Gets 5 samples and visualizes the results (to be implemented by subclasses).

