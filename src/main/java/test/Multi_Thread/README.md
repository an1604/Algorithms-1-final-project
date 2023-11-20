# Task Class

The `Task` class in the `test.Multi_Thread` package represents a `Runnable` task designed to be executed by an `ExecutorService` object. This task is responsible for running tests on a specified `Tests` object, sampling algorithms on a given puzzle size, sample size, and number of steps from the final state.

## Constructor

```java
public Task(Tests test, int puzzle_size, int sample_size, int n)
