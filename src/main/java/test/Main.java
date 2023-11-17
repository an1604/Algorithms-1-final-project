package test;

import test.Part_C.BFS;
import test.part_A_B.Graph;
import test.part_A_B.Node;

public class Main {
    public static void main(String[] args) {
        Graph graph = Graph.menu();
        Node rand_node = null;
        while (rand_node ==null ){
            rand_node  = graph.get_random_node_for_bfs();
        }
        BFS bfs = new BFS(rand_node,graph);
        System.out.println("Searching solution via BFS...");
        bfs.traverse();
    }
}