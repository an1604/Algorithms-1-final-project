# Task Class

The `Task` class in the `test.Multi_Thread` package represents a `Runnable` task designed to be executed by an `ExecutorService` object. This task is responsible for running tests on a specified `Tests` object, sampling algorithms on a given puzzle size, sample size, and number of steps from the final state.

## Constructor

```java
public Task(Tests test, int puzzle_size, int sample_size, int n)
```
test: The Tests object to sample the algorithms on.
puzzle_size: The size of the puzzle.
sample_size: The size of the sample.
n: The number of steps from the final state.

## Run Method
The run method is overridden from the Runnable interface and is executed when the Task is submitted to an ExecutorService.
 It calls the run_tests method on the specified Tests object with the provided puzzle size, sample size, and number of steps.

```java
public void run() {
    test.run_tests(this.puzzle_size, this.sample_size, this.n);
}
```
## Note
Ensure that you have proper synchronization mechanisms if the run_tests method or any shared resources within the Tests object are not thread-safe.
