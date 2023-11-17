package test.agorithms;

import States.FinishState;
import test.components.Graph;
import test.components.Node;

import java.util.*;

public class BFS implements Algorithms{
    private final Node start_node;
    private final Graph graph;
    private final FinishState final_state;
    private int size;
    private Map<Integer, Integer> parentMap;  // Map to store parent-child relationship
    private int num_of_vertices;
    public BFS(Node startNode, Graph graph) {
        this.start_node = startNode;
        this.graph = graph;
        this.size = graph.getSize();

        this.parentMap = new HashMap<>();
        this.final_state = FinishState.getFinishState(size);
        this.num_of_vertices = Integer.MIN_VALUE;
    }


    @Override
    public void traverse() {
        //Clearing the visited area
        graph.set_visited(false);

        Queue<Node> queue = new LinkedList<>();
        queue.add(this.start_node);
        while (!queue.isEmpty()) {
            try {
                Node current = queue.poll();
                // Avoiding cycles
                if (!current.isVisited()) {
                    current.setVisited(true);
                    // If the current node matches the final state, print the path and exit
                    if (current.getState().isGoalState()) {
//                        printPath(current);
                        return;
                    }
                    // We want to generate the states of the exact node and append them to the Q
                    graph.get_states(current);
                    // Enqueue unvisited neighbors and update parentMap
                    for (Node neighbor : graph.getNeighbors(current.getID())) {
                        //Checking if the node is visited or not
                        if (!neighbor.isVisited()) {
                            //Updating the Q and the Parent Map to make sure where this node came from
                            queue.add(neighbor);
                            parentMap.put(neighbor.getID(), current.getID());
                        }

                    }
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        System.out.println("No solution found!");
    }

    @Override
    public int num_of_vertices() {
        if(num_of_vertices!=Integer.MIN_VALUE)
            return num_of_vertices;
        return parentMap.size();
    }

    @Override
    public String Name() {
        return "BFS";
    }


    private void printPath(Node current) {
        System.out.println("Solution found! Printing the path:");
        List<Integer> path = new ArrayList<>();
        path.add(current.getID());

        // Traverse back through the parent-child relationship to construct the path
        Integer parentID = parentMap.get(current.getID());
        while (parentID != null) {
            path.add(parentID);
            parentID = parentMap.get(parentID);
        }
        this.num_of_vertices = path.size();
        // Print the path in reverse order (from start to end)
        Collections.reverse(path);
        for (Integer nodeID : path) {
            graph.getNodeByID(nodeID).print_puzzle();
        }
    }
}
