package test.Main;

import test.agorithms.Algorithms;

import java.util.HashMap;
import java.util.Map;

public class RunTimeTest {
    Algorithms[] algorithms;

    public Map<Algorithms, Long> getTimes() {
        return times;
    }

    Map<Algorithms, Long> times;
    public RunTimeTest(Algorithms ... algorithms) {
    this.algorithms = algorithms;
    this.times = new HashMap<>();
    }

    public void test() {
        long startTime, endTime, runtime;
        for (Algorithms alg : algorithms) {
//            System.out.println("Checking run time of :" + alg.Name() + "...");

            //Using Milliseconds as parameter
             startTime = System.currentTimeMillis();
             alg.traverse();
             endTime = System.currentTimeMillis();
             runtime = endTime - startTime;
             //Added the result to the map
             times.put(alg,runtime);

        }
    }
    public void print(){
        for (int i = 0; i < algorithms.length; i++) {
            System.out.println("Runtime for algorithm " + algorithms[i].Name() + " is: " + times.get(i) +" ms"+
                    " \n Num of vertices developed: " + algorithms[i].num_of_vertices() );
            System.out.println("--------------------------------");
        }
    }
}
