package test.Main;

import test.agorithms.AStar;
import test.agorithms.Algorithms;
import test.components.Graph;

public class Step {
    private Graph graph;
    private Algorithms alg1;
    private Algorithms alg2;
    private Algorithms alg3;
    private Algorithms alg4;
    private RunTimeTest runTimeTest;

    public Step(Graph graph, Algorithms alg1, Algorithms alg2, Algorithms alg3, AStar alg4, RunTimeTest runTimeTest) {
        this.graph = graph;
        this.alg1 = alg1;
        this.alg2 = alg2;
        this.alg3 = alg3;
        this.alg4=alg4;
        this.runTimeTest = runTimeTest;
    }

    public Algorithms getAlg4() {
        return alg4;
    }

    public void setAlg4(Algorithms alg4) {
        this.alg4 = alg4;
    }

    public RunTimeTest getRunTimeTest() {
        return runTimeTest;
    }

    public void setRunTimeTest(RunTimeTest runTimeTest) {
        this.runTimeTest = runTimeTest;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Algorithms getAlg1() {
        return alg1;
    }

    public void setAlg1(Algorithms alg1) {
        this.alg1 = alg1;
    }

    public Algorithms getAlg2() {
        return alg2;
    }

    public void setAlg2(Algorithms alg2) {
        this.alg2 = alg2;
    }

    public Algorithms getAlg3() {
        return alg3;
    }

    public void setAlg3(Algorithms alg3) {
        this.alg3 = alg3;
    }

    public String[] get_data(){
        String[] data = new String[4];
        //Values
        data[0] = " Run time : "+ String.valueOf(runTimeTest.getTimes().get(alg1)) + "\nNumber of processes vertices: " + String.valueOf(alg1.num_of_vertices());
        data[1] = " Run time : "+String.valueOf(runTimeTest.getTimes().get(alg2)) + "\nNumber of processes vertices: " + String.valueOf(alg2.num_of_vertices());
        data[2] = " Run time : "+String.valueOf(runTimeTest.getTimes().get(alg3)) + "\nNumber of processes vertices: " + String.valueOf(alg3.num_of_vertices());
        data[3] = " Run time : "+String.valueOf(runTimeTest.getTimes().get(alg4)) + "\nNumber of processes vertices: " + String.valueOf(alg1.num_of_vertices());

        return data;
    }
}
