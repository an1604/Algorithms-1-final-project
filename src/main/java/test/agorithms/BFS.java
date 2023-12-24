package test.agorithms;

import test.components.Graph;
import test.components.Node;

import java.util.*;

public class BFS implements Algorithms {
    /**
     * The BFS class.
     * This class represents the breadth-first search algorithm.
     * We considered a couple of things:
     * start_node (Node) - The initial node to move on from.
     * graph (Graph) - The Graph that we searched on.
     * parentMap (Map<Integer, Integer>) -  Map to store parent-child relationship.
     * num_of_vertices (int) - Number of developed vertices.
     */
    private final Node start_node;
    private final boolean single_run;

    private final Graph graph;

    private final Map<Integer, Integer> parentMap;
    private List<Integer> final_path;

    public BFS(Node startNode, Graph graph, boolean single_run) {
        this.single_run = single_run;
        this.start_node = startNode;
        this.graph = graph;

        this.parentMap = new HashMap<>();
        this.final_path = new ArrayList<>();
    }

    @Override
    public Node getStart_node() {
        return start_node;
    }

    @Override
    public int getAmount_of_displacement() {
        return parentMap.size();
    }

    @Override
    public void traverse() {
        if (single_run)
            System.out.println("BFS is running...");
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
                        this.final_path = get_path(current);
                        if (single_run)
                            printPath(current);
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
            } catch (NullPointerException ignored) {
            }
        }

        System.out.println("No solution found!");
    }

    @Override
    public int num_of_vertices() {
        return final_path.size() + 1;
    }

    @Override
    public String Name() {
        return "BFS";
    }


    private void printPath(Node current) {
        System.out.println("Solution found! Printing the path:");
        final_path = get_path(current);
        // Print the path in reverse order (from start to end)
        Collections.reverse(final_path);
        for (Integer nodeID : final_path) {
            graph.getNodeByID(nodeID).print_puzzle();
        }
    }

    private List<Integer> get_path(Node current) {
        List<Integer> path = new ArrayList<>();
        path.add(current.getID());

        // Traverse back through the parent-child relationship to construct the path
        Integer parentID = parentMap.get(current.getID());
        while (parentID != null) {
            path.add(parentID);
            parentID = parentMap.get(parentID);
        }
        return path;
    }

}
