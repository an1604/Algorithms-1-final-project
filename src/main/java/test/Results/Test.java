package test.Results;

import test.WordDoc;
import test.main.Average;
import test.main.RunTimeTest;
import test.Table.AverageTerminalTable;
import test.Table.SampleTerminalTable;
import test.Table.Table;
import test.agorithms.AStar;
import test.agorithms.Algorithms;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Test extends Tests {
    /**
     * The test class.
     * We considered a couple of things:
     * nodes (set) - The set of initialization nodes that can be sent to sample each iteration.
     * tests(Q) - The thread safe Q to run each test safely.
     * graphs_idx (AtomicInteger) - The counter of the samples.
     */

    private final Set<String> nodes;

    private Queue<RunTimeTest> tests;

    private final AtomicInteger graphs_idx;

    private Table table;
    private int puzzle_size;
    private final WordDoc doc;

    public Test(String doc_name) {
        this.nodes = new CopyOnWriteArraySet<>();
        this.tests = new ArrayBlockingQueue<>(200);
        this.graphs_idx = new AtomicInteger(0);
        this.puzzle_size = 0;
        try {
            this.doc = new WordDoc(doc_name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Queue<RunTimeTest> getTests() {
        return tests;
    }

    public int getPuzzle_size() {
        return puzzle_size;
    }

    public boolean step(int n, int graph_size) {
        /**
         * This Function represents 1 step in the test of the project,
         We're generating 1 random graph from the final state, and then running the algorithm from
         there and sample the run time foreach one.
         Args:
         int n - The number of steps to generate from the final state.
         Graph - The graph that we want to run the sample on (will usually be empty to save resources).
         The function will return true of all the steps inside go right.
         **/
        try {
            //Clearing the graph and then generate him
            Graph graph = new Graph(graph_size);

            graph = graph.generate_n_steps_from_final_state(n);

            //Making sure we're getting a unique node each iteration
            Node rand_node = null;
            do {
                rand_node = graph.get_random_node();
            }
            while (nodes.contains(rand_node.getState().getPuzzleRepresentation()));
            nodes.add(rand_node.getState().getPuzzleRepresentation());

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

    @Override
    public void run_tests(int graph_size, int num_of_samples, int n) {
        boolean res = false;
        this.puzzle_size = graph_size;
        do {
            try {
                res = step(n, graph_size);
            } catch (NullPointerException ignored) {
            }
        } while (!res);
        System.out.println("Sample number " + (graphs_idx.getAndIncrement() + 1) + " successfully created. ");
    }


    public void clear() {
        this.table = null;
        this.tests = new ArrayBlockingQueue<>(200);
        this.graphs_idx.set(0);
        this.nodes.clear();
    }

    @Override
    public void print_results() {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        String choice = " ";
        List<RunTimeTest> tests_elements = new ArrayList<>(tests);
        while (!stop) {
            System.out.println("Choose your option : ");
            System.out.println("(1) Show results by sample id (Note: in this case, the id's is between 1-" + tests_elements.size() + ".");
            System.out.println("(2) Exit.");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    int id = 0;
                    System.out.println("Generate an id for you?");
                    choice = scanner.nextLine();
                    if (choice.equals("yes") || choice.equals("Yes")) {
                        Random r = new Random();
                        id = Math.abs(r.nextInt()) % tests_elements.size();
                        System.out.println("ID is : " + id);
                    } else {
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
    public String toString() {
        return "Test{" +
                "nodes=" + nodes +
                ", tests=" + tests +
                ", graphs_idx=" + graphs_idx +
                ", table=" + table +
                '}';
    }

    @Override
    public void get_avg_and_visualize_results() {
        Algorithms[] algorithms = get_algorithms();
        Map<String, Average> map = new HashMap<>();
        //Getting all the Algorithms and map them with average instance
        for (Algorithms algorithm : algorithms) {
            map.put(algorithm.Name(), new Average(algorithm));
        }

        //Commute the averages
        for (RunTimeTest test : tests) {
            for (Algorithms algorithm : test.getAlgorithms()) {
                try {
                    Average a = map.get(algorithm.Name());
                    //Run time
                    a.setRun_time_avg(test.getRun_times().get(algorithm));
                    //Vertices
                    a.setVertices_avg(algorithm.num_of_vertices());
                    // the amount of displacement
                    a.setAmount_of_displacement(algorithm.getAmount_of_displacement());

                } catch (NullPointerException ignored) {
                }
            }
        }

        for (Average average : map.values()) {
            average.compute(tests.size());
        }

        //Visualize part
        String[] names = get_names();
        this.table = new AverageTerminalTable(names, tests.size());
        for (Average average : map.values()) {
            this.table.parseRowString(average.toString());
        }
        this.table.generate_table();
        this.table.printTable();
    }

    private Algorithms[] get_algorithms() {
        assert tests.peek() != null;
        return tests.peek().getAlgorithms();
    }

    //Create an instance of the table class and visualize the results
    @Override
    public void visualize_results() {
        String[] headers = get_names();
        this.table = new AverageTerminalTable(headers, tests.size());

        for (RunTimeTest test : tests) {
            this.table.parseRowString(test.toString());
        }

        this.table.generate_table();

        this.table.printTable();
    }

    private String[] get_names() {
        // Getting the first element to get the algorithm names
        RunTimeTest runTimeTest = tests.peek();
        assert runTimeTest != null;
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

    //Get 5 samples and visualize the results
    @Override
    public void get_samples_table() {
        Table sample_table = new SampleTerminalTable(tests.stream().toList());
        sample_table.printTable();
    }

    @Override
    public void create_Word_doc_for_little_sample(String[] headers) {
        doc.setHeaders(headers);
        doc.create_table_for_little_sample(this);
        doc.write_table_into_document();
    }

    @Override
     public void create_Word_doc_for_big_sample(){
       String[] headers = get_headers();
        doc.setHeaders(headers);
        doc.create_table_for_big_sample(this);
        doc.write_table_into_document();
    }

    private String[] get_headers() {
        String[] headers = get_names();
        int len = headers.length + 2;
        String[] res = new String[len];
        res[0] = "Sample";
        res[1] = "Initial node";
        System.arraycopy(headers, 0, res, 2, len - 2);
        return res;
    }

    @Override
    public void menu_for_showing_results() {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        String choice = " ";
        List<RunTimeTest> tests_elements = new ArrayList<>(tests);

        while (!stop) {
            System.out.println("Choose your option : ");
            System.out.println("(1) Show results by sample id (Note: in this case, the id's is between 1-" + tests_elements.size() + ".");
            System.out.println("(2) Exit.");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    int id = 0;
                    System.out.println("Generate an id for you?");
                    choice = scanner.nextLine();
                    if (choice.equals("yes") || choice.equals("Yes")) {
                        Random r = new Random();
                        id = Math.abs(r.nextInt()) % tests_elements.size();
                        System.out.println("ID is : " + id);
                    } else {
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


}