package test;

import test.agorithms.AStar;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

import java.util.Random;


public class Main {
    public boolean step(int n, Graph graph){
        /**This Function represents 1 step in the test of the project,
          We're generating 1 random graph from the final state, and then running the algorithm from
          there and sample the run time foreach one.
         Args:
         int n - The number of steps to generate from the final state.
         Graph - The graph that we want to run the sample on (will usually be empty to save resources).
         The function will return true of all the steps inside go right.
         **/

        graph = graph.generate_n_steps_from_final_state(n);
        Node rand_node = graph.get_random_node();
        System.out.println("Index is : " + rand_node.getID());
        if(rand_node==null) {
            System.out.println("rand_node is null");
            return false;
        }

        //Initialize the algorithms
        BFS bfs = new BFS(rand_node,graph);
        AStar manhattan  = new AStar(rand_node, graph,"M");
        AStar dijkstra = new AStar(rand_node, graph,"D");
        AStar greedy_bfs = new AStar(rand_node, graph," ");
        //Sample the run time foreach
        RunTimeTest.test(bfs,manhattan,dijkstra,greedy_bfs);

        return true;
    }
    public static void main(String[] args) {

    }
}