package test.main;

import test.agorithms.Algorithms;

import java.util.HashMap;
import java.util.Map;

public class RunTimeTest {
    Algorithms[] algorithms;
    Map<Algorithms, Long> run_times;
    Map<Algorithms, Integer> vertices;
    Map<Algorithms, Integer> amount_of_displacement;

    public RunTimeTest(Algorithms ... algorithms) {
    this.algorithms = algorithms;
    this.run_times = new HashMap<>();
    this.vertices = new HashMap<>();
    this.amount_of_displacement = new HashMap<>();
    }

    public Map<Algorithms, Integer> getAmount_of_displacement() {
        return amount_of_displacement;
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
            // Using Nanoseconds as parameter
            startTime = System.nanoTime();
            alg.traverse(); // running the algorithm
            endTime = System.nanoTime();
            runtime = endTime - startTime; // sample the result

            // We're keeping the run time and the num of vertices in an array and then put into the map
            run_times.put(alg, runtime);
            vertices.put(alg, alg.num_of_vertices());
            amount_of_displacement.put(alg, alg.getAmount_of_displacement());

            System.out.println();
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Algorithms alg : run_times.keySet()) {
            sb.append("Alg name : ").append(alg.Name());
            sb.append("\nInitial node:\n ").append(alg.getStart_node().toString());
            sb.append("Params: \n  Runtime: ").append(run_times.get(alg)).append(" nanoseconds").append("\n vertices: ").
                    append(alg.num_of_vertices()).append("\n The amount of displacements: ").append(alg.getAmount_of_displacement());
            sb.append("\n--------------------------\n ");
        }
        return sb.toString();
    }


}
