package test.Main;

import test.agorithms.Algorithms;

import java.util.HashMap;
import java.util.Map;

public class RunTimeTest {
    Algorithms[] algorithms;
    Map<Algorithms, Long> run_times;
    Map<Algorithms, Integer> vertices;

    public RunTimeTest(Algorithms ... algorithms) {
    this.algorithms = algorithms;
    this.run_times = new HashMap<>();
    this.vertices = new HashMap<>();
    }


    public Algorithms[] getAlgorithms() {
        return algorithms;
    }

    public Map<Algorithms, Long> getRun_times() {
        return run_times;
    }

    public Map<Algorithms, Integer> getVertices() {
        return vertices;
    }

    public void test() {
        long startTime, endTime, runtime;
        for (Algorithms alg : algorithms) {
            //Using Milliseconds as parameter
             startTime = System.currentTimeMillis();
             alg.traverse(); // running the algorithm
             endTime = System.currentTimeMillis();
             runtime = endTime - startTime; //sample the result

             //We're keeping the run time and the num of vertices in an array and then put into the map
             run_times.put(alg,runtime);
             vertices.put(alg,alg.num_of_vertices());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Algorithms alg : run_times.keySet()) {
            sb.append("Alg name : " + alg.Name());
            sb.append("\nInitial node:\n " +alg.getStart_node().toString());
            sb.append("Params: \n  Runtime: " + run_times.get(alg) + " ms" + "\n vertices: " + alg.num_of_vertices());
            sb.append("\n--------------------------\n ");
        }
        return sb.toString();
    }


}
