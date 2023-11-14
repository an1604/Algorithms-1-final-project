package test;

import test.Part_C.BFS;
import test.part_A_B.Graph;
import test.part_A_B.Node;

public class Main {
    public static void main(String[] args) {
        Graph graph = Graph.menu();
        Node rand_node  = graph.get_random_node_for_bfs();
        BFS bfs = new BFS(rand_node,graph);
        bfs.traverse();
    }
}