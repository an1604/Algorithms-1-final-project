package test.Part_D;

import test.part_A_B.Graph;
import test.part_A_B.Node;

import java.util.*;

public class AStar {
    String prompt; // This is the prompt about the cost function (h(n))
    private final Node start_node;

    private final Graph graph;

    public AStar(Node start_node, Graph graph ,String prompt) {
        this.start_node = start_node;
        this.graph = graph;
        this.prompt = prompt;
    }

    public void traverse(){
        //Clear the visited value to run it from scratch.
        graph.set_visited(false);

        Queue<Node> queue = new LinkedList<>();
        int counter = 0 ; //Counting 5000 steps
        start_node.setVisited(true);
        queue.add(start_node);
        Node current_node =null;
        ArrayList<Node> path = new ArrayList<>();
        while(counter <=5000){
            //Pooling the node from the Q and append the node to the path list
             current_node = queue.poll();
             path.add(current_node);
            //Handling the finish line
            if(current_node.getState().isGoalState()){
                print_path(path);
                return;
            }
                //Get the neighbors (the states)
                graph.get_states(current_node);
                Set<Node> neighbors = graph.getNeighbors(current_node.getID());
                //Calculate the cost function
                calc_costs_for_neighbors(neighbors);

                // Finding the minimum cost function from the neighbors
                Node min_node = generate_minimum_cost(neighbors);
                min_node.setVisited(true);
                queue.add(min_node);
                //Increasing the counter each iteration
                counter++;
                //Increasing the depth for next iteration
                graph.increase_depth();

        }
        System.out.println("No solution found ...");
    }

    public void print_path(ArrayList<Node> list) {
        System.out.println("Solution Found!\n Path: ");
        list.forEach(node -> node.print_puzzle());
    }

    private void calc_costs_for_neighbors(Set<Node> neighbors) {
        for(Node neighbor : neighbors){
            //Function to handle the prompt and calculate the right cost
            if(!neighbor.isVisited())
                get_cost(neighbor);
        }
    }

    private Node generate_minimum_cost(Set<Node> neighbors) {
        int min_cost = Integer.MAX_VALUE;
        Node min_node =null;
        for(Node neighbor : neighbors){
            if(neighbor.getCosts_for_AStar().getF_n() < min_cost){
                min_cost = neighbor.getCosts_for_AStar().getF_n();
                min_node = neighbor;
            }
        }
        return min_node;
    }

    private void get_cost(Node neighbor) {
        switch (prompt){
            case "M":
                neighbor.getCosts_for_AStar().get_Manhattan_costs();
                break;
            case "D":
                neighbor.getCosts_for_AStar().get_Dijkstra_cost();
                break;
                //Handle the non-heuristic function here
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Graph graph1 = new Graph(4);
        graph1.generate_n_steps_from_final_state(4);
        graph1 = graph1.generate_new_graph_from_given_graph(false);
        AStar a_star = new AStar(graph1.getNodeByID(1),graph1,"M");
        a_star.traverse();
    }
}
