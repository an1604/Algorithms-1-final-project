package test.Results;

import test.main.RunTimeTest;
import test.Table.Table;
import test.agorithms.AStar;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Tests {
    /**The Tests class, an abstract class.
     * This class holds some key methods that every test needs to have.
     **/
    private Set<RunTimeTest> nodes;
    private Queue<RunTimeTest> tests;

    private AtomicInteger graphs_idx;

    private Table table;

    public void clear() {
        this.table=null;
        this.tests = new ArrayBlockingQueue<>(200);
        this.graphs_idx.set(0);
        this.nodes.clear();
    }
    public void run_tests(int graph_size,int num_of_samples , int n){
        boolean res = false;
        do {
            try {
                res = single_test(n, graph_size);
            } catch (NullPointerException e) {}
        }while(!res);
        System.out.println("Graph number " + (graphs_idx.getAndIncrement() + 1) + " successfully created. ");
    }

    public boolean single_test(int n, int graph_size) {
        /**This Function represents 1 step in the test of the project,
         We're generating 1 random graph from the final state, and then running the algorithm from
         there and sample the run time foreach one.
         Args:
         int n - The number of steps to generate from the final state.
         Graph - The graph that we want to run the sample on (will usually be empty to save resources).
         The function will return true of all the steps inside go right.
         **/
        try {
            boolean stop = false;
            //Clearing the graph and then generate him
            Graph graph = new Graph(graph_size);

            graph = graph.generate_n_steps_from_final_state(n);

            //Making sure we're getting a unique node each iteration
            Node rand_node = null;
            do {
                rand_node = graph.get_random_node();
            }
            while(nodes.contains(rand_node) && rand_node==null);

            //Initialize the algorithms
            BFS bfs = new BFS(rand_node, graph, false);
            AStar manhattan = new AStar(rand_node, graph, "M", false);
            AStar dijkstra = new AStar(rand_node, graph, "D", false);
            AStar greedy_bfs = new AStar(rand_node, graph, " ", false);
            //Sample the run time foreach
            RunTimeTest runTimeTest = new RunTimeTest(manhattan, dijkstra, bfs, greedy_bfs);
            runTimeTest.test();
            tests.add(runTimeTest);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
    public void setTable(Table table) {
        this.table = table;
    }
    public void print_table(){
        this.table.printTable();
    }

    public abstract void genearge_avarege_run_time_table();
    public abstract void menu_for_showing_results();
    public abstract void visualize_results();

    //Get 5 samples and visualize the results
    public abstract void get_samples_table();
}
