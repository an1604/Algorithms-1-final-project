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
    public  int get_decrease_Id() {
        return this.id --;
    }
    public Graph(int size) {
        this.connections = new HashMap<>();
        this.size = size;
        this.id =1 ;
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
    public boolean addEdge(int id1 , int id2){
        // Get the Node (Key) from the insider map
        Node node1 = getNodeByID(id1).keySet().iterator().next();
        Node node2 = getNodeByID(id2).keySet().iterator().next();
        // Checking if the connection is legal before creating the edge
        boolean res = check_connection(node1,node2);
        if (res) {
            connections.get(id1).get(node1).add(node2);
            connections.get(id2).get(node2).add(node1);
            System.out.println("Done!");
            return res;
        }
        System.out.println("Cannot make the edge because this step is nol legal!");
        return true;
    }

    private boolean check_connection(Node node1, Node node2) {
        // Checking for change
        if (Arrays.deepEquals(node1.getPuzzle(), node2.getPuzzle())) {
            return false;
        }

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
            // Duplicate the node 3 times and make a little change to set edge (moving the null)
            // Duplicate the node 3 times and make a little change to set edge (moving the null)
            for (int i = 0; i < 4; i++) {
                Node duplicatedNode = new Node(node);
                // Copy the original puzzle to the duplicated node
                int[][] duplicatedPuzzle = new int[size][size];
                for (int row = 0; row < size; row++) {
                    System.arraycopy(node.getPuzzle()[row], 0, duplicatedPuzzle[row], 0, size);
                }
                // Switch the empty index with a neighboring index
                int row = emp_index.getX();
                int col = emp_index.getY();
                int[] neighbors = {-1, 0, 1};
                int newRow, newCol;
                do {
                    newRow = row + neighbors[random.nextInt(neighbors.length)];
                    newCol = col + neighbors[random.nextInt(neighbors.length)];
                } while (newRow < 0 || newRow >= size || newCol < 0 || newCol >= size || duplicatedPuzzle[newRow][newCol] == -1);

                // Swap the values
                int temp = duplicatedPuzzle[row][col];
                duplicatedPuzzle[row][col] = duplicatedPuzzle[newRow][newCol];
                duplicatedPuzzle[newRow][newCol] = temp;
                // Setting the null point in the right place
                duplicatedNode.setEmpty_point(new Point(newRow, newCol));
                // Setting the modified puzzle to the duplicated node
                duplicatedNode.setPuzzle(duplicatedPuzzle);
                //Adding the node to the graph
                g.add_generated_Node(duplicatedNode);
                // Add an edge between the original node and the duplicated node
                g.addEdge(node.getID(), duplicatedNode.getID());

                //Clearing the set to generate the new node from scratch
                nums.clear();
            }
        }
        return g;
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


    public void print_graph(){
        connections.forEach((k,v)->{
            System.out.println("Node number : " + k);
            Node node = getNodeByID(k).keySet().iterator().next();
            System.out.println("Neighbors: ");
            v.get(node).forEach(node1 -> node1.print_puzzle());
        });
        System.out.println("***********************************************");
    }

    public static void main(String[] args) {
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
                    graph.print_graph();
                    break;
                case "6":
                    System.out.println("Exiting...");
                    stop =true;
                    break;
            }
        }
    }

}





