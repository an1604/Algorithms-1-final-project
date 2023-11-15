package test.Part_C;

import test.part_A_B.Graph;
import test.part_A_B.Node;

import java.util.*;

public class BFS {
    private final Node start_node;
    private final Graph graph;
    private final String final_state;
    private int size;
    private Map<Integer, Integer> parentMap;  // Map to store parent-child relationship

    public BFS(Node startNode, Graph graph) {
        this.start_node = startNode;
        this.graph = graph;
        this.size = graph.getSize();
        this.parentMap = new HashMap<>();
        this.final_state = initialize_Final_State();
    }


    private String initialize_Final_State() {
        StringBuilder sb = new StringBuilder();
        int count = 1;

        // Append the elements to the StringBuilder
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (count < size * size) {
                    sb.append(String.format("%2d ", count));
                    count++;
                } else {
                    // Append "null" for the empty space
                    sb.append("null ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void traverse() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.start_node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            // Avoiding cycles
            if (!current.isVisited()) {
                current.setVisited(true);
                // If the current node matches the final state, print the path and exit
                if (isFinalState(current)) {
                    printPath(current);
                    return;
                }
                // We want to generate the states of the exact node and append them to the Q
                graph.get_states(current);
                // Enqueue unvisited neighbors and update parentMap
                for (Node neighbor : graph.getNeighbors(current.getID())){
                    //Checking if the node is visited or not
                    if(!neighbor.isVisited()){
                        //Updating the Q and the Parent Map to make sure where this node came from
                        queue.add(neighbor);
                        parentMap.put(neighbor.getID() , current.getID());
                    }

                }
            }
        }

        System.out.println("No solution found!");
    }

    private boolean isFinalState(Node node) {
        return final_state.replaceAll("\\s", "").equals(node.getState().replaceAll("\\s", ""));

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

        // Print the path in reverse order (from start to end)
        Collections.reverse(path);
        for (Integer nodeID : path) {
            graph.getNodeByID(nodeID).keySet().iterator().next().print_puzzle();
        }
    }
}
