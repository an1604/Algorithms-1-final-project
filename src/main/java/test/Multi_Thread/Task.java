package test.Multi_Thread;

import test.Results.Tests;

public class Task implements Runnable{
    private  int puzzle_size;
    private int sample_size;
    private int n;
    private Tests test;

    public Task(Tests test, int puzzle_size, int sample_size, int n) {
        this.puzzle_size = puzzle_size;
        this.sample_size = sample_size;
        this.n = n;
        this.test = test;
    }

    @Override
    public void run() {
        test.run_tests(this.puzzle_size,this.sample_size,this.n);

    }
}
