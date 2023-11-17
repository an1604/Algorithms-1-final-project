package test;

import test.agorithms.AStar;
import test.agorithms.BFS;
import test.components.Graph;
import test.components.Node;

public class Main {
    public static void main(String[] args) {
        Graph graph = Graph.menu();
        Node rand_node = null;
        rand_node  = graph.getNodeByID(1);
        BFS bfs = new BFS(rand_node,graph);
        AStar manhattan  = new AStar(rand_node, graph,"M");
        AStar dijkstra = new AStar(rand_node, graph,"D");
        AStar greedy_bfs = new AStar(rand_node, graph," ");


        RunTimeTest.test(bfs,manhattan,dijkstra,greedy_bfs);

    }
}