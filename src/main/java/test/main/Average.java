package test.main;

import test.agorithms.Algorithms;

public class Average {
    /**
     * The Average class.
     * This class holds the average values for each sample; for each algorithm we create an
     * instance of the average class to keep the values.
     * We considered a couple of things:
     * algorithm (Algorithms) - The algorithm instance.
     * run_time_avg (long) - The run time value for this specific algorithm.
     * vertices_avg (int) - The amount of developed vertices value for this specific algorithm.
     **/
    private final Algorithms algorithm;
    private long run_time_avg;
    private int vertices_avg;
    private int amount_of_displacement;

    public Average(Algorithms algorithm) {
        this.algorithm = algorithm;
        this.amount_of_displacement = 0;
        this.vertices_avg = 0;
        this.run_time_avg = 0;
    }

    public long getRun_time_avg() {
        return run_time_avg;
    }

    public void setRun_time_avg(long run_time_avg) {
        this.run_time_avg += run_time_avg;
    }

    public void setAmount_of_displacement(int amount_of_displacement) {
        this.amount_of_displacement += amount_of_displacement;
    }

    public int getVertices_avg() {
        return vertices_avg;
    }

    public void setVertices_avg(int vertices_avg) {
        this.vertices_avg += vertices_avg;
    }

    public void compute(int num_of_samples) {
        this.run_time_avg /= num_of_samples;
        this.vertices_avg /= num_of_samples;
        this.amount_of_displacement /= num_of_samples;
    }


    @Override
    public String toString() {

        return "Alg name : " + algorithm.Name() +
                "\n--------------------------\n " +
                "\nInitial node:\n " + algorithm.getStart_node().toString() +
                "\n--------------------------\n " +
                "Params: \n  Runtime Average: " + run_time_avg + " nanoseconds" + "\n Vertices Average: " + vertices_avg +
                "\n The amount of displacement: " + amount_of_displacement +
                "\n--------------------------\n ";
    }
}
