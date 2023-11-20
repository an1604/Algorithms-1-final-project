package test.agorithms;

import test.components.Costs;
import test.components.Graph;
import test.components.Node;

import java.util.*;

public class AStar implements Algorithms{

    /**In this class, we consider a couple of things:
     * Initialization: We get the graph to compute the search on.
     * We get the prompt for the heuristic function.
     * And we get the starting node to move on from.
     **/
    private final String prompt; // This is the prompt about the cost function (h(n))
    private final Node start_node;
    private final Graph graph;

    private Set<Integer> visited;
    private List<Node> path;
    private boolean path_found;
    private String name; // The alg name
    private boolean single_run;

    public AStar(Node start_node, Graph graph ,String prompt, boolean single_run) {
        this.start_node = start_node;
        this.graph = graph;
        this.prompt = prompt;
        this.visited= new HashSet<>();
        this.path = new ArrayList<>();
        this.path_found= false;
        this.name = check_for_name();
        this.single_run = single_run;
    }

    private String check_for_name() {
        StringBuilder sb =new StringBuilder("A star");
        switch (prompt) {
            case "M":
                sb.append(" Manhattan heuristic");
                break;
            case "D":
                sb.append(" Dijkstra version");
                break;
            //Handle the non-heuristic function here
            default:
                sb.append(" Greedy BFS");
                break;
        }
        return sb.toString();
    }

    public String getPrompt() {
        return prompt;
    }
    @Override
    public void traverse(){
        if(single_run)
            System.out.println(this.name + " is running...");
        if(!visited.isEmpty())
            visited.clear();

        graph.set_visited(false);

        Queue<Node> queue = new LinkedList<>();
        start_node.setVisited(true);

        //Initialize g(n) to 0 in the start node (we already in the initial node)
        try {
            start_node.getCosts_for_AStar().setG_n(0);
        }catch (NullPointerException e){
            e.printStackTrace();
            Costs costs = new Costs(0,start_node.getState());
            costs.setG_n(0);
            start_node.setCosts_for_AStar(costs);
        }

        //Calculate f(n) for the start node
        get_cost(start_node);
        queue.add(start_node);
        Node current_node =null;
        if (single_run) {
            System.out.println("Initial state is: ");
            start_node.print_puzzle();
        }


        while( !this.path_found){
            //Pooling the node from the Q and append the node to the path list
            try {
                current_node = queue.poll();
            } catch (NullPointerException e){
                e.printStackTrace();
                continue;
            }

            //Handling the finish state
            if (current_node.getState().isGoalState()) {
                path.add(current_node);
                this.path_found = true;
                break;
            }

            //Handle the multiple checking
             if(visited.contains(current_node.getID()) && single_run) {
                 System.out.println("This node already exist, we go through this node again. ");
                 current_node.print_puzzle();
             }
             else
                path.add(current_node); // For printing the path at the end


                 //Get the neighbors (the states)
                 graph.get_states(current_node);
                 Set<Node> neighbors = graph.getNeighbors(current_node.getID());

                 //Calculate the cost function
                 calc_costs_for_neighbors(neighbors);

                 if(!this.path_found) {
                     // Finding the minimum cost function from the neighbors
                     LinkedList<Node> min_nodes = generate_minimum_cost(neighbors);

                         //If we don't reach the goal, we added all the minimum nodes to the Q
                         queue.addAll(min_nodes);

                         //Increasing the depth for next iteration
                         graph.increase_depth();

                         //Adding the node into the set to make sure we not visit her again
                         visited.add(current_node.getID());
                 }
        }
        if(!this.path_found)
            System.out.println("No solution found ...");
        else if(single_run)
            print_path();

    }

    @Override
    public int num_of_vertices() {
        return visited.size();
    }

    @Override
    public String Name() {
        return name;
    }

    @Override
    public Node getStart_node() {
        return start_node;
    }

    public void print_path() {
        System.out.println("Solution Found!\n Path: ");
        path.forEach(node -> node.print_puzzle());
    }

    private void calc_costs_for_neighbors(Set<Node> neighbors) {
        for(Node neighbor : neighbors){
                get_cost(neighbor);
                //Checking if we in the final state...
                if(neighbor.getCosts_for_AStar().getF_n() == 0){
                    if(neighbor.getState().isGoalState()){
                        path.add(neighbor);
                        this.path_found = true;
                        return;
                    }
                }
        }
    }

    private LinkedList<Node> generate_minimum_cost(Set<Node> neighbors) {
        int min_cost = Integer.MAX_VALUE;
        LinkedList<Node> queue = new LinkedList<>();
        Node min_node = !neighbors.isEmpty() ? neighbors.iterator().next() : null;

        for (Node neighbor : neighbors) {
            int neighbor_fn = neighbor.getCosts_for_AStar().getF_n();

            if (neighbor_fn == min_cost) {
                // Handle tie-breaking based on g_n value and check if a neighbor is not visited
                // And also the checking for Dijkstra, we compare g(n).
                if (min_node == null || (neighbor.getCosts_for_AStar().getG_n() < min_node.getCosts_for_AStar().getG_n() && !visited.contains(neighbor.getID()))) {
                    // Update min_node and add it to the queue
                    min_node = neighbor;
                    queue.add(min_node);
                }
            } else if ((neighbor_fn < min_cost) && !visited.contains(neighbor.getID())) {
                // Update min_cost and min_node
                min_cost = neighbor_fn;
                min_node = neighbor;
                queue.clear(); // Clear the queue as we found a new minimum
                queue.add(min_node);
            }
        }

        // Ensure that the queue is not empty
        if (queue.isEmpty() && min_node != null) {
            queue.add(min_node);
        }

        return queue;
    }



    private void get_cost(Node neighbor) {
        if(!neighbor.getCosts_for_AStar().isCalculated()) {
            switch (prompt) {
                case "M":
                    neighbor.getCosts_for_AStar().get_Manhattan_costs();
                    break;
                case "D":
                    neighbor.getCosts_for_AStar().get_Dijkstra_cost();
                    break;
                //Handle the non-heuristic function here
                default:
                    neighbor.getCosts_for_AStar().get_Greedy_BFS();
                    break;
            }
        }
    }

}
