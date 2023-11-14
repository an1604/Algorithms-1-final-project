package test.Part_C;

import test.part_A_B.Graph;
import test.part_A_B.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS {
    private final Node start_node;
    private final Graph graph;

    public BFS(Node startNode, Graph graph) {
        start_node = startNode;
        this.graph = graph;
    }

    public void traverse(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.start_node);
        while(!queue.isEmpty()) {
            Node current = queue.poll();
            // Avoiding cycles
            if (!current.isVisited()) {
                current.setVisited(true);
                System.out.println("Current node : ");
                current.print_puzzle();

                // Getting the set of neighbors to add to the Q
                Set<Node> neighbors = graph.getNeighbors(start_node.getID());
                queue.addAll(neighbors);
            }
        }
    }
}
