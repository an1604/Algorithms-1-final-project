package test.Main;

import test.agorithms.Algorithms;

public class Average {
private Algorithms algorithm;
private long run_time_avg;
private int vertices_avg;

    public Average(Algorithms algorithm) {
        this.algorithm = algorithm;
    }

    public long getRun_time_avg() {
        return run_time_avg;
    }

    public void setRun_time_avg(long run_time_avg) {
        this.run_time_avg += run_time_avg;
    }

    public int getVertices_avg() {
        return vertices_avg;
    }

    public void setVertices_avg(int vertices_avg) {
        this.vertices_avg += vertices_avg;
    }

    public void compute(int num_of_samples){
        this.run_time_avg/=num_of_samples;
        this.vertices_avg/=num_of_samples;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
            sb.append("Alg name : " + algorithm.Name());
        sb.append("\n--------------------------\n ");

            sb.append("\nInitial node:\n " +algorithm.getStart_node().toString());
        sb.append("\n--------------------------\n ");

            sb.append("Params: \n  Runtime Average: " + run_time_avg + " ms" + "\n Vertices Average: " + vertices_avg);
            sb.append("\n--------------------------\n ");
        return sb.toString();
    }
}
