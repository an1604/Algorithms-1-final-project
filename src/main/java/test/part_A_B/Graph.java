package test.part_A_B;

import java.util.*;

public class Graph {
    private Map<Integer , Map<Node , Set<Node>>> connections ;

    private  int size;

    private  int id;

    public  int getId() {
        return this.id;
    }

    public  int get_increase_Id() {
        return this.id ++;
    }

    public Graph(int size) {
        this.connections = new HashMap<>();
        this.size = size;
        this.id =1 ;
    }
    public int getSize() {
        return size;
    }

    public Map<Integer , Map<Node , Set<Node>>> getConnections() {
        return connections;
    }

    public Map<Node,Set<Node>> getNodeByID(int id){
        return connections.get(id);
    }
    public void addNode(){
        // Creating the insider map
        Map<Node , Set<Node>> nodeMap = new HashMap<>();
        Node node = new Node(size ,id ++);
        nodeMap.put(node,new HashSet<>());
        connections.put(node.getID() ,nodeMap);
        int row = node.getEmpty_point().getX();
        int col = node.getEmpty_point().getY();
        generate_neighbors(row,col,node);
        System.out.println("Node added successfully , node id is : " + node.getID());
    }

    public void add_generated_Node(Node node){
        // Creating the insider map
        Map<Node , Set<Node>> nodeMap = new HashMap<>();
        nodeMap.put(node,new HashSet<>());
        connections.put(node.getID() ,nodeMap);
        System.out.println("Node added successfully , node id is : " + node.getID());
    }

    public void removeNode(int id){
        connections.remove(id);
    }
    public void addEdge(int id1, int id2) {
        // Get the Node (Key) from the insider map
        Node node1 = getNodeByID(id1).keySet().iterator().next();
        Node node2 = getNodeByID(id2).keySet().iterator().next();

        // Checking if the connection is legal and the nodes are different before creating the edge
        boolean res = check_connection(node1, node2) && (id1 != id2);
        if (res) {
            try {
                // add the exact node and all of his neighbors
                connections.get(id1).get(node1).add(node2);
                connections.get(id1).get(node1).addAll(getNeighbors(node2.getID()));

                connections.get(id2).get(node2).add(node1);
                connections.get(id2).get(node2).addAll(getNeighbors(node1.getID()));
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Done!");
            return;
        }
        System.out.println("Cannot make the edge because this step is not legal!");
    }


    private boolean check_connection(Node node1, Node node2) {

        // Counter of how many positions are different from node 1 to node 2
        int differences = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Checking if there is a change between 2 places
                if (node1.getPuzzle()[i][j] != node2.getPuzzle()[i][j]) {
                    // Chekcing if we are in the -1 area
                    if (node1.getPuzzle()[i][j]==-1 || node2.getPuzzle()[i][j]==-1) {
                        // Checking if the change involves the empty place and moves correctly (only in 1 place for any direction)
                        if (node1.getEmpty_point().getX() < node2.getEmpty_point().getX() && node1.getEmpty_point().getY() < node2.getEmpty_point().getY()) {
                            return false;
                        } else if (node1.getEmpty_point().getX() > node2.getEmpty_point().getX() && node1.getEmpty_point().getY() > node2.getEmpty_point().getY()) {
                            return false;
                        } else return true;
                    }


                    differences += 1;
                }

                // If there is more than 1 change in the places, the edge cannot be legal
                if (differences > 1) {
                    return false;
                }
            }
        }

        return true;
    }


    public Set<Node> getNeighbors(int id){
        Node node = getNodeByID(id).keySet().iterator().next();
        return connections.get(id).get(node);
    }

    public static Graph generate_random_graph(int size){
        Graph g = new Graph(size);
        // Generate random number foreach node
        Random random = new Random();
        // Making sure that every number shows only once
        Set<Integer> nums = new HashSet<>();
        Scanner s = new Scanner(System.in);
        System.out.println("How many nodes do you want to generate?");
        int nodes = s.nextInt();

        // Generation part
        for (int k = 0; k< nodes; k++) {
            Node node = new Node(size, g.get_increase_Id());
            int[][] puzzle = new int[size][size];
            // Generating random index for the null
            Point emp_index = new Point(Math.abs(random.nextInt()) % (size), Math.abs(random.nextInt()) % (size));
            // Placing the null (-1) value
            puzzle[emp_index.getX()][emp_index.getY()] = -1;
            node.setEmpty_point(emp_index);
            int num = 0;
            // Generating the number of elements (for size 4 -> 4*4-1 =15 , for size 5 -> 5*5-1 =24 ...)
            int num_of_elements = (size * size) - 1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (puzzle[i][j] != -1) {
                        //generating random number that not includes in the nums set...
                        num = generate_random_number(num_of_elements, nums);
                        puzzle[i][j] = num;
                    }
                }
            }
            // Setting the puzzle to the first node
            node.setPuzzle(puzzle);
            g.add_generated_Node(node);

                // Switch the empty index with a neighboring index
                int row = emp_index.getX();
                int col = emp_index.getY();
                //Generate all neighbors
                g.generate_neighbors(row,col, node);

                nums.clear();
            }
//        }
        return g;
    }

    private void generate_neighbors(int row, int col, Node node) {
        int[] neighbors = {-1, 1};

        // Looping over the "neighbors"
        for (int i = 0; i < 2; i++) {
            int new_col = neighbors[i] + col;
            int new_row = neighbors[i] + row;

            // Checking coordinates for row swap
            if (new_row >= 0 && new_row < size) {
                int[][] tmp_puzzle_row = clonePuzzle(node.getPuzzle());
                swapElements(tmp_puzzle_row, row, col, new_row, col);

                Node new_node_row = new Node(node, get_increase_Id(), new Point(new_row, col));
                new_node_row.setPuzzle(tmp_puzzle_row);
                add_generated_Node(new_node_row);
                // Exclude the original node from being added as a neighbor
                if (!new_node_row.equals(node)) {
                    addEdge(node.getID(), new_node_row.getID());
                }
            }

            // Checking coordinates for column swap
            if (new_col >= 0 && new_col < size) {
                int[][] tmp_puzzle_col = clonePuzzle(node.getPuzzle());
                swapElements(tmp_puzzle_col, row, col, row, new_col);

                Node new_node_col = new Node(node, get_increase_Id(), new Point(row, new_col));
                new_node_col.setPuzzle(tmp_puzzle_col);
                add_generated_Node(new_node_col);
                // Exclude the original node from being added as a neighbor
                if (!new_node_col.equals(node)) {
                    addEdge(node.getID(), new_node_col.getID());
                }
            }
        }
    }



    // Helper method to clone a puzzle
    private int[][] clonePuzzle(int[][] puzzle) {
        int[][] clone = new int[puzzle.length][];
        for (int i = 0; i < puzzle.length; i++) {
            clone[i] = puzzle[i].clone();
        }
        return clone;
    }

    // Helper method to swap elements in a puzzle
    private void swapElements(int[][] puzzle, int row1, int col1, int row2, int col2) {
        int temp = puzzle[row1][col1];
        puzzle[row1][col1] = puzzle[row2][col2];
        puzzle[row2][col2] = temp;
    }




    private static int generate_random_number(int numOfElements, Set<Integer> nums) {
        Random random = new Random();
        int num;
        do {
            num = Math.abs(random.nextInt()) % numOfElements + 1;
        } while (nums.contains(num));

        // After we found a unique number, we append it to the set
        nums.add(num);
        return num;
    }


    public void print_graph() {
        for (int id : connections.keySet()) {
            Map<Node, Set<Node>> value = connections.get(id);
            Node node = value.keySet().iterator().next();
            Set<Node> neighbors = value.get(node);

            System.out.println("Node number : " + id);
            node.print_puzzle();

            if (!neighbors.isEmpty()) {
                System.out.println("Neighbors:");
                for (Node neighbor : neighbors) {
                    System.out.println("Neighbor id : " + neighbor.getID());
                    neighbor.print_puzzle();
                    System.out.println("--------------------");
                }
            }
            System.out.println("***********************************************");
        }
    }

    public Node get_random_node_for_bfs(){
        Random r = new Random();
        int index = Math.abs(r.nextInt() % id);
        return connections.get(index).keySet().iterator().next();
    }
    //This function will be called from the main and generate the graph from scratch
    public static Graph menu() {
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the size of the puzzle?");
        int size = scanner.nextInt();
        String choice ="";
        Graph graph = new Graph(size);
        System.out.println("Lets build the graph...");
        while(!stop){
            System.out.println("Enter your choice : \n 1) Create new node\n 2) Create new edge \n 3)Remove node \n 4) Print the graph \n 5) Generate random graph\n 6)Exit");
            choice = scanner.nextLine();
            switch (choice){
                case "1":
                    graph.addNode();
                    break;
                case "2":
                    System.out.println("Between which nodes? write the id's...");
                    int id1 = scanner.nextInt();
                    int id2 = scanner.nextInt();
                    graph.addEdge(id1,id2);
                    break;
                case "3":
                    System.out.println("Which node?");
                    int id = scanner.nextInt();
                    graph.removeNode(id);
                    break;
                case "4":
                    graph.print_graph();
                    break;
                case "5":
                    graph = null;
                    graph = generate_random_graph(size);
                    break;
                case "6":
                    System.out.println("Exiting...");
                    stop =true;
                    break;
            }
        }
        return graph;
    }

}





