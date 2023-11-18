package test.Main;

import test.agorithms.AStar;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    //Thread safe data structures
    private static Set<RunTimeTest> nodes = new CopyOnWriteArraySet<>();
    private static Queue<RunTimeTest> tests = new ArrayBlockingQueue<>(200);
    private static ExecutorService es = Executors.newCachedThreadPool();
    private static AtomicInteger graphs_idx = new AtomicInteger(0);

    private   boolean step(int n, int graph_size) {
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
            BFS bfs = new BFS(rand_node, graph);
            AStar manhattan = new AStar(rand_node, graph, "M");
            AStar dijkstra = new AStar(rand_node, graph, "D");
            AStar greedy_bfs = new AStar(rand_node, graph, " ");
            //Sample the run time foreach
            RunTimeTest runTimeTest = new RunTimeTest(manhattan, dijkstra, bfs, greedy_bfs);
            runTimeTest.test();
            tests.add(runTimeTest);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private void run_tests(int graph_size,int num_of_samples , int n) {
        boolean res = false;
        do {
            try {
                 res = step(n, graph_size);
            } catch (NullPointerException e) {}
        }while(!res);
        System.out.println("Graph number " + (graphs_idx.getAndIncrement() + 1) + " successfully created. ");
    }

    public void start(int graph_size , int num_of_samples , int n ){
        System.out.println("Generating " + num_of_samples + " random graphs using 5000 steps from the final state, in size "
                + graph_size + "X" + graph_size + "...");
        AtomicInteger index = new AtomicInteger(0);
        while (index.getAndIncrement() <num_of_samples){
            es.execute(()->run_tests(graph_size,num_of_samples,n));
        }
    }

    public static void print_results(){
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        String choice = " ";
        List<RunTimeTest> tests_elements = new ArrayList<>(tests);
        while (!stop){
            System.out.println("Choose your option : ");
            System.out.println("(1) Show results by sample id (Note: in this case, the id's is between 1-"+tests_elements.size()+"." );
            System.out.println("(2) Exit.");
            choice = scanner.nextLine();
            switch (choice){
                case "1":
                    int id=0;
                    System.out.println("Generate an id for you?");
                    choice= scanner.nextLine();
                    if(choice.equals("yes") || choice.equals("Yes")){
                        Random r = new Random();
                        id = Math.abs(r.nextInt())%tests_elements.size();
                        System.out.println("ID is : " + id);
                    }
                    else{
                        System.out.println("Choose your id: ");
                        id = scanner.nextInt();
                    }
                    System.out.println("Result: ");
                    System.out.println(tests_elements.get(id).toString());
                    break;
                case "2":
                    stop = true;
                    break;
                default:
                    System.out.println("Try again");
                    break;
            }
        }

    }

    private static RunTimeTest[] get_arr() {
        RunTimeTest[] arr = new RunTimeTest[tests.size()];
        for(int i=0;i<tests.size();i++){
            arr[i] = tests.poll();
        }
        return arr ;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start(5,50,5000);

        es.shutdown();

        try {
            // Wait for all tasks to complete or until timeout
            if (es.awaitTermination(10, TimeUnit.MINUTES)) {
                System.out.println("Generation Done.");
                print_results();
            } else {
                System.out.println("Timeout occurred while waiting for tasks to complete.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
