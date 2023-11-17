package test;

import test.agorithms.Algorithms;
import test.components.Point;

import java.util.ArrayList;

public class RunTimeTest {
    public static void test(Algorithms ... algorithms) {
        long startTime, endTime, runtime;
        ArrayList<Long> times = new ArrayList<>();
        for (Algorithms alg : algorithms) {
            System.out.println("Checking run time of :" + alg.Name() + "...");

            //Using Milliseconds as parameter
             startTime = System.currentTimeMillis();
             alg.traverse();
             endTime = System.currentTimeMillis();
             runtime = endTime - startTime;
             times.add(runtime);
        }
        System.out.println("*************** \n summary:");
        for (int i = 0; i < algorithms.length; i++) {
            System.out.println("Runtime for algorithm " + algorithms[i].Name() + " is: " + times.get(i) +" ms"+
                    " \n Num of vertices developed: " + algorithms[i].num_of_vertices() );
            System.out.println("--------------------------------");
        }
        System.out.println("Sample Done.");
    }
}
