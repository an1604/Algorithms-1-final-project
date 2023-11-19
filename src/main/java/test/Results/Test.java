package test.Results;

import test.Main.Average;
import test.Main.RunTimeTest;
import test.Main.TerminalTable;
import test.agorithms.AStar;
import test.agorithms.Algorithms;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Test implements Tests{
    //Thread safe data structure
    private  Set<RunTimeTest> nodes;
    private  Queue<RunTimeTest> tests;

    private  AtomicInteger graphs_idx;
    private TerminalTable table;

    public Test() {
        this.nodes = new CopyOnWriteArraySet<>();
        this.tests = new ArrayBlockingQueue<>(200);
        this.graphs_idx  = new AtomicInteger(0);
    }

    public boolean step(int n, int graph_size) {
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
    @Override
    public void run_tests(int graph_size,int num_of_samples , int n) {
        boolean res = false;
        do {
            try {
                 res = step(n, graph_size);
            } catch (NullPointerException e) {}
        }while(!res);
        System.out.println("Graph number " + (graphs_idx.getAndIncrement() + 1) + " successfully created. ");
    }

    public void start(int graph_size , int num_of_samples , int n){
        //Clearing the test instance
        clear();
        System.out.println("Generating " + num_of_samples + " random graphs using 5000 steps from the final state, in size "
                + graph_size + "X" + graph_size + "...");
    }

    private void clear() {
        this.table=null;
        this.tests = new ArrayBlockingQueue<>(200);
        this.graphs_idx.set(0);
        this.nodes.clear();
    }
    @Override
    public  void print_results(){
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


    @Override
    public void get_avg_and_visualize_results() {
        Algorithms[] algorithms =get_algorithms();
        Map<Algorithms, Average> map = new HashMap<>();
        //Getting all the Algorithms and map them with average instance
        for(Algorithms algorithm : algorithms){
            map.put(algorithm , new Average(algorithm));
        }

        //Commute the averages
        for (RunTimeTest test : tests){
            for(Algorithms algorithm : test.getAlgorithms()){
                //Run time
                map.get(algorithm).setRun_time_avg(test.getRun_times().get(algorithm));
                //Average
                map.get(algorithm).setVertices_avg(test.getVertices().get(algorithm));

            }
        }

        for(Average average : map.values()){
            average.compute(tests.size());
        }

        //Visualize part
        String[] names = get_names();
        this.table = new TerminalTable(names,tests.size());
        for(Average average : map.values()){
            this.table.parseRowString(average.toString());
        }
        this.table.generate_table();
        this.table.printTable();
    }

    private Algorithms[] get_algorithms() {
        return tests.peek().getAlgorithms();
    }

    //Create an instance of the table class and visualize the results
    @Override
    public void visualize_results(){
            String[] headers = get_names();
            this.table = new TerminalTable(headers, tests.size());

            for(RunTimeTest test:tests){
                this.table.parseRowString(test.toString());
            }

            this.table.generate_table();
            this.table.printTable();
    }

    private String[] get_names() {
        // Getting the first element to get the algorithm names
        RunTimeTest runTimeTest = tests.peek();
        int size = runTimeTest.getAlgorithms().length;
        String[] names = new String[size];
        int i = 0;
        //Getting all the names
        for (Algorithms algorithm : runTimeTest.getAlgorithms()) {
            names[i] = algorithm.Name();
            i++;
        }
        return names;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.run_tests(4,50,5000);
        test.get_avg_and_visualize_results();
    }

}