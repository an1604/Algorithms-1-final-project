package test.Main;

import test.agorithms.AStar;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static List<Step> steps = new ArrayList<>();

    public static boolean step(int n, Graph graph) {
        /**This Function represents 1 step in the test of the project,
         We're generating 1 random graph from the final state, and then running the algorithm from
         there and sample the run time foreach one.
         Args:
         int n - The number of steps to generate from the final state.
         Graph - The graph that we want to run the sample on (will usually be empty to save resources).
         The function will return true of all the steps inside go right.
         **/
        try {
            graph = graph.generate_n_steps_from_final_state(n);
            Node rand_node = graph.get_random_node();
            if (rand_node == null) {
                System.out.println("rand_node is null");
                return false;
            }

            //Initialize the algorithms
            BFS bfs = new BFS(rand_node, graph);
            AStar manhattan = new AStar(rand_node, graph, "M");
            AStar dijkstra = new AStar(rand_node, graph, "D");
            AStar greedy_bfs = new AStar(rand_node, graph, " ");
            //Sample the run time foreach
            RunTimeTest runTimeTest = new RunTimeTest(manhattan, dijkstra, bfs);
            runTimeTest.test();
            steps.add(new Step(graph, bfs, manhattan, dijkstra, greedy_bfs, runTimeTest));
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean run_tests(int graph_size,int num_of_samples , int n) {
        System.out.println("Generating 50 random graphs using 5000 steps from the final state, in size 4X4... ");
        Graph graph = new Graph(graph_size);
        for (int i = 0; i < num_of_samples; i++) {
            try {
                boolean res = step(n, graph);
                if (!res)
                    i--;
                else {
                    System.out.println("Graph number " + (i + 1) + " successfully created. ");
                }
            } catch (NullPointerException e) {
                i--;
            }
        }
        //Building the data
        String[] headers = {steps.get(0).getAlg1().Name(), steps.get(0).getAlg2().Name(), steps.get(0).getAlg3().Name(), steps.get(0).getAlg4().Name()};
        String[][] data = new String[num_of_samples][4]; // 4 is the number of algorithms
        for (int i = 0; i < 50; i++) {
            data[i] = steps.get(i).get_data();
        }

        TerminalTable table = new TerminalTable(headers, data);
        table.printTable();
        return true;
    }

    public static void main(String[] args) {
        run_tests(4 , 50, 4);
    }
}