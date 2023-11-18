package test.Main;

import test.agorithms.AStar;
import test.agorithms.Algorithms;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Test {
    //Thread safe data structure
    private  Set<RunTimeTest> nodes;
    private  Queue<RunTimeTest> tests;
    private  ExecutorService es ;
    private  AtomicInteger graphs_idx;
    private TerminalTable table;

    public Test() {
        this.nodes = new CopyOnWriteArraySet<>();
        this.tests = new ArrayBlockingQueue<>(200);
        this.es = Executors.newCachedThreadPool();
        this.graphs_idx  = new AtomicInteger(0);
    }

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

    public void start(int graph_size , int num_of_samples , int n){
        //Clearing the test instance
        clear();
        System.out.println("Generating " + num_of_samples + " random graphs using 5000 steps from the final state, in size "
                + graph_size + "X" + graph_size + "...");
        AtomicInteger index = new AtomicInteger(0);
        while (index.getAndIncrement() <num_of_samples){
            es.execute(()->run_tests(graph_size,num_of_samples,n));
        }
        es.shutdown();
        es.close();
    }

    private void clear() {
        this.table=null;
        this.tests = new ArrayBlockingQueue<>(200);
        this.graphs_idx.set(0);
        this.nodes.clear();
    }

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


    public void Menu() {
        Test test = new Test();
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        String choice = "";
        while (!stop) {
            System.out.println("Choose your choice : ");
            System.out.println("(1) Create a little sample and visualize the results.");
            System.out.println("(2) Create a big sample (up to 50) and visualize the averages between the results");
            System.out.println("(3) Getting information about a sample via id.");
            System.out.println("(4) Exit.");

            choice = scanner.nextLine();

            //Getting information from the user
            System.out.println("What is the size of the puzzle? ");
            int puzzle_size = scanner.nextInt();
            System.out.println("What is the size of the sample? ");
            int sample_size = scanner.nextInt();
            System.out.println("How many steps from the final state?");
            int n = scanner.nextInt();

            // Check his choice
            switch (choice){
                case "1":
                    if((puzzle_size==15 || puzzle_size ==24) && (sample_size <10)){
                        if(puzzle_size==15)
                            puzzle_size=4;
                        else
                            puzzle_size=5;

                        int finalPuzzle_size = puzzle_size;
                        new Thread( ()->test.start(finalPuzzle_size, sample_size,n)).start();
                        try {
                            Thread.currentThread().join();
                            visualize_results();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
//                    es.shutdown();
//                    try {
//                        // Wait for all tasks to complete or until timeout
//                        if (es.awaitTermination(10, TimeUnit.MINUTES)) {
//                            System.out.println("Generation Done.");
//                            visualize_results();
//                        } else {
//                            System.out.println("Timeout occurred while waiting for tasks to complete.");
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case "2":
                    test.start(puzzle_size, sample_size,n);
                    es.shutdown();
                    try {
                        // Wait for all tasks to complete or until timeout
                        if (es.awaitTermination(10, TimeUnit.MINUTES)) {
                            System.out.println("Generation Done.");
                            get_avg_and_visualize_results();
                        } else {
                            System.out.println("Timeout occurred while waiting for tasks to complete.");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    print_results();
                    break;
                case "4":
                    stop = true;
                    break;
            }
        }
    }

    private void get_avg_and_visualize_results() {
        Algorithms[] algorithms =get_algorithms();
        Map<Algorithms,Average> map = new HashMap<>();
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
}