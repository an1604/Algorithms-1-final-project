package test.components;

import States.FinishState;
import States.State;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Graph {
    private Map<Integer , Map<Node, Set<Node>>> connections ;
    private State final_state;
    private  int size;

    private  int id;

    private int depth;



    public  int getId() {
        return this.id;
    }

    public  int get_increase_Id() {
        return this.id ++;
    }

    public Graph(int size) {
        this.connections = new ConcurrentHashMap<>();
        this.size = size;
        this.id =1 ;
        final_state = FinishState.getFinishState(size);
        this.depth=0;
    }




    public State getFinal_state() {
        return final_state;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer , Map<Node , Set<Node>>> getConnections() {
        return connections;
    }

    public Node getNodeByID(int id){
        try {
            return connections.get(id).keySet().iterator().next();
        }catch (NullPointerException e){
            return null;
        }
    }
    public Node get_random_node(){
        Random random = new Random();
        int index;
        Node node = null;
        do {
            index = Math.abs(random.nextInt()) % connections.size() + 1;
            node = getNodeByID(index);
        } while (node == null || node.getState().isGoalState());

        return node;
    }

    public void addNode(){
        // Creating the insider map
        Map<Node , Set<Node>> nodeMap = new HashMap<>();
        Node node = new Node(size ,id ++,get_increase_depth());
        nodeMap.put(node,new HashSet<>());
        connections.put(node.getID() ,nodeMap);
        int row = node.getEmpty_point().getX();
        int col = node.getEmpty_point().getY();
        //generate_neighbors(row,col,node);
        get_states(node);
        System.out.println("Node added successfully , node id is : " + node.getID());
    }

    public void add_generated_Node(Node node){
        // Creating the insider map
        Map<Node , Set<Node>> nodeMap = new HashMap<>();
        nodeMap.put(node,new HashSet<>());
        connections.put(node.getID() ,nodeMap);
    }

    public void removeNode(int id){
        connections.remove(id);
    }
    public void addEdge(int id1, int id2) {
        // Get the Node (Key) from the insider map
        Node node1 = getNodeByID(id1);
        Node node2 = getNodeByID(id2);

        // Checking if the connection is legal and the nodes are different before creating the edge
        boolean res = check_connection(node1, node2) && (id1 != id2);
        if (res) {
            try {
                connections.get(id1).get(node1).add(node2);
                connections.get(id2).get(node2).add(node1);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            return;
        }
        System.out.println("Cannot make the edge because this step is not legal!");
    }


    private boolean check_connection(Node node1, Node node2) {
        // Getting the empty points for coordinate check
        try {
            Point p1 = node1.getEmpty_point();
            Point p2 = node2.getEmpty_point();
            return p1.check_coordinate(p2);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }


    public Set<Node> getNeighbors(int id){
        Node node = getNodeByID(id);
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
            Node node = new Node(size, g.get_increase_Id(), g.get_increase_depth());
            int[][] puzzle = new int[size][size];
            // Generating random index for the null
            Point emp_index = new Point(Math.abs(random.nextInt()) % (size), Math.abs(random.nextInt()) % (size));
            // Placing the null (-1) value
            puzzle[emp_index.getX()][emp_index.getY()] = -1;
            node.setEmpty_point(emp_index);
            // Randomly initialize the puzzle of the node
            g.initialize_puzzle(node,puzzle, nums);

            g.add_generated_Node(node);

            // Generating the states from this node (move the null up,down,left,right)
            g.get_states(node);
                nums.clear();
            }
        return g;
    }

    public Graph generate_new_graph_from_given_graph(boolean add_neighbors) {
        // Storing neighbors before clearing the graph
        Set<Node> neighbors = null;

        // Getting the size of the graph (vertices)
        int size = getConnections().size();

        // Generating a new starting node
        Node startNode = null;
        Random random = new Random();
        do {
            try {
                int index = Math.abs(random.nextInt()) % size + 1;
                startNode = getNodeByID(index);

                // Storing neighbors before clearing the graph
                if (add_neighbors) {
                    neighbors = getNeighbors(index);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }catch (ArithmeticException e){
                int index = Math.abs(random.nextInt()) % size + 1;
                startNode = getNodeByID(index);
            }
        } while (startNode == null);

        // Clearing the graph
        clear_graph();

        // Adding the start node
        startNode.setID(get_increase_Id());
        startNode.setDepth(get_increase_depth());
        add_generated_Node(startNode);

        // Setting neighbors for the new starting node
        if (neighbors != null) {
            setNeighbors(startNode.getID(), neighbors);
        }

        return this;
    }


    private void setNeighbors(int id, Set<Node> neighbors) {
        //Getting the map inside the map...
        Map<Node , Set<Node>> map = connections.get(id);

        // updating the map
        map.put(getNodeByID(id) , neighbors);
    }

    private int get_increase_depth() {
        return this.depth++;
    }
    public void increase_depth(){
        this.depth++;
    }

    //Generating the moving states for a given node
    public void get_states(Node node) {
        //If the node already exists, we return immanently without generating the states.
        if(getNodeIfExists(node) !=null && getNeighbors(node.getID()).size() >1)
            return;

        // For the clone puzzle function, to make sure that we call just when we need to
        boolean is_inside_if = false;

        Node node1 = null , temp=null;
        int row = node.getEmpty_point().getX();
        int col = node.getEmpty_point().getY();
        int [][] tmp_puzzle = clonePuzzle(node.getPuzzle());
        //Checking each one
        //Up
        int up_idx_row = row - 1;
        // If we still in bounds, start to generate
        if (up_idx_row<size && up_idx_row >=0 ){
            is_inside_if = true;
            // Generating the new puzzle state
            this.swapElements(tmp_puzzle,up_idx_row,col ,row,col);
            node1 = new Node(node,get_increase_Id(),new Point(up_idx_row,col), this.depth);
            node1.setPuzzle(tmp_puzzle);
            //Adding the node to the graph
            this.add_generated_Node(node1);
            // Adding an edge between the nodes
            this.addEdge(node.getID() , node1.getID());
        }

        //Resetting the puzzle
        if (is_inside_if) {
            tmp_puzzle = clonePuzzle(node.getPuzzle());
            is_inside_if = false;
        }

        //Down
        int down_idx_row = row +1;
        if(down_idx_row<size && down_idx_row>=0){
            is_inside_if = true;
            this.swapElements(tmp_puzzle,down_idx_row,col ,row,col);
            node1 = new Node(node,get_increase_Id(),new Point(down_idx_row,col), this.depth);
            node1.setPuzzle(tmp_puzzle);
            //Adding the node to the graph
            this.add_generated_Node(node1);
            // Adding an edge between the nodes
            this.addEdge(node.getID() , node1.getID());
        }

        //Resetting the puzzle
        if (is_inside_if) {
            tmp_puzzle = clonePuzzle(node.getPuzzle());
            is_inside_if = false;
        }

        // Right
        int right_idx_col = col +1;

        if (right_idx_col <size && right_idx_col>=0){
            is_inside_if = true;
            this.swapElements(tmp_puzzle,row,right_idx_col ,row,col);
            node1 = new Node(node,get_increase_Id(),new Point(row,right_idx_col), this.depth);
            //Setting the puzzle to the right puzzle
            node1.setPuzzle(tmp_puzzle);

            //Adding the node to the graph
            this.add_generated_Node(node1);
            // Adding an edge between the nodes
            this.addEdge(node.getID() , node1.getID());
        }

        //Resetting the puzzle
        if (is_inside_if) {
            tmp_puzzle = clonePuzzle(node.getPuzzle());
        }

        // Left
        int left_idx_col = col -1;
        if (left_idx_col <size && left_idx_col>=0 ){
            this.swapElements(tmp_puzzle,row,left_idx_col ,row,col);
            node1 = new Node(node,get_increase_Id(),new Point(row,left_idx_col), this.depth);
            node1.setPuzzle(tmp_puzzle);
            //Adding the node to the graph
            this.add_generated_Node(node1);
            // Adding an edge between the nodes
            this.addEdge(node.getID() , node1.getID());
        }
    }
    //This method checks if new state is already inside the graph
    private Node getNodeIfExists(Node targetNode) {
        for (Map.Entry<Integer, Map<Node, Set<Node>>> entry : connections.entrySet()) {
            for (Map.Entry<Node, Set<Node>> innerEntry : entry.getValue().entrySet()) {
                if (innerEntry.getKey().equals(targetNode) || innerEntry.getValue().contains(targetNode)) {
                    return innerEntry.getKey(); // Node found, return the matching node
                }
            }
        }
        return null; // Node not found in the graph
    }


    //Random initialization of the puzzle
    private void initialize_puzzle(Node node, int[][] puzzle, Set<Integer> nums) {
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

            System.out.println("Node number: " + id + " | Depth: " + node.getDepth());
            System.out.println("Puzzle:");
            node.print_puzzle();

            if (!neighbors.isEmpty()) {
                System.out.println("Edges:");
                for (Node neighbor : neighbors) {
                    System.out.println("  " + id + " -> " + neighbor.getID());
                }
            }

            System.out.println("***********************************************");
        }
    }


    public Node get_random_node_for_bfs(){
        Random r = new Random();
        int index = Math.abs(r.nextInt() % id);
        try {
            return connections.get(index).keySet().iterator().next();
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }


    public   Graph generate_n_steps_from_final_state(int n){
        //Clearing the graph
       clear_graph();
        //Creating the node and add to the graph
        Node final_state_node = new Node(size , get_increase_Id(), new Point(size-1,size-1), final_state.getPuzzleArr(),get_increase_depth());
        add_generated_Node(final_state_node);

        //Start to generate states from the final state using BFS
        Queue<Node> queue = new LinkedList<>();
        queue.add(final_state_node);
        while (n>0){
            Node node = queue.poll();
            if(!node.isVisited()) {
                node.setVisited(true);
                get_states(node);
                //Keeping the depth to update all its neighbors
                int depth_ = get_increase_depth();
                for(Node neighbor : getNeighbors(node.getID())){
                    if(!neighbor.isVisited())
                        neighbor.setDepth(depth_);
                    queue.add(neighbor);
                }
                //We're decreasing n only when we generated new state!
                n--;
            }
        }

        return this;
    }

    private void clear_graph() {
        //Making a new graph
        this.connections.clear();
        this.id =1;
        this.depth=0;
       set_visited(false);
    }

    //Make all the nodes to be unvisited.
    public void set_visited(boolean b) {
        connections.forEach((id,map)->{
            map.forEach((node,set)->{
                node.setVisited(b);
            });
        });
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
            System.out.println("Enter your choice : \n 1) Create new node\n 2) Create new edge \n 3)Remove node \n 4) Print the graph \n 5) Generate random graph\n  6) Generate n steps from the final state \n7)Exit");
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
                    System.out.println("Choose your n :");
                    int n = scanner.nextInt();
                    graph = graph.generate_n_steps_from_final_state(n);
                    break;
                case "7":
                    System.out.println("Exiting...");
                    stop =true;
                    break;
                default:
                    System.out.println("I don't understand, try again. ");
                    break;
            }
        }
        return graph;
    }
    //Disconnect the edge between given  2 nodes.
    public void remove_edge(int startNode, int endNode) {
        //Getting the set of neighbors for each node
        Set<Node> start_node = getNeighbors(startNode);
        Set<Node> end_node = getNeighbors(endNode);

        // Removing the node from each set
        start_node.remove(getNodeByID(endNode));
        end_node.remove(getNodeByID(startNode));
    }

}





