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

        // Initialize the final state
        int state_size = (size * size) - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= state_size; i++) {
            // Getting size elements in each row
            for (int j = 0; j < size; j++) {
                sb.append(i + " , ");
            }
            sb.append("\n");
        }

        this.final_state = sb.toString();
    }

    public void traverse() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.start_node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            // Avoiding cycles
            if (!current.isVisited()) {
                current.setVisited(true);
                System.out.println("Current node : ");
                current.print_puzzle();

                // If the current node matches the final state, print the path and exit
                if (isFinalState(current)) {
                    printPath(current);
                    return;
                }

                // Getting the set of neighbors to add to the queue
                Set<Node> neighbors = graph.getNeighbors(current.getID());
                for (Node neighbor : neighbors) {
                    if (!neighbor.isVisited()) {
                        queue.add(neighbor);
                        parentMap.put(neighbor.getID(), current.getID());  // Update parent-child relationship
                    }
                }
            }
        }

        System.out.println("No solution found!");
    }

    private boolean isFinalState(Node node) {
        return Arrays.deepToString(node.getPuzzle()).equals(final_state);
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
