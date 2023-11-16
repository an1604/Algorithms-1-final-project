package test.Part_C;

import test.part_A_B.Graph;
import test.part_A_B.Node;

import java.util.*;

public class DFS {
    private Graph graph;

    public DFS(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void dfs() {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parent_map = new HashMap<>();
        Set<GraphEdges> cycle_edges = new HashSet<>();

        if (hasCycles(visited, parent_map, cycle_edges)) {
            // Remove the edges from the graph
            for (GraphEdges edge : cycle_edges) {
                System.out.println("Cycle found! \n Edges involved : ");
                System.out.println("(" + edge.getStart_node() + " ," + edge.getEnd_node() + ")");
                graph.remove_edge(edge.getStart_node(), edge.getEnd_node());
            }
        }
    }

    public boolean hasCycles(Set<Integer> visited, Map<Integer, Integer> parent_map, Set<GraphEdges> cycle_edges) {
        // Loop over and check cycles foreach node in part
        for (int id : graph.getConnections().keySet()) {
            if (!visited.contains(id) && cycle_detection(id, visited, parent_map, cycle_edges)) {
                return true;
            }
        }
        return false;
    }

    private boolean cycle_detection(int id, Set<Integer> visited, Map<Integer, Integer> parentMap, Set<GraphEdges> cycle_edges) {
        Deque<Integer> stack = new LinkedList<>();
        stack.push(id);

        while (!stack.isEmpty()) {
            int current_id = stack.pop();

            if (!visited.contains(current_id))
                visited.add(current_id);

            for (Node neighbor : graph.getNeighbors(current_id)) {
                int neighbor_id = neighbor.getID();
                GraphEdges edge = new GraphEdges(current_id, neighbor_id);

                if (!visited.contains(neighbor_id)) {
                    stack.push(neighbor_id);
                    parentMap.put(neighbor_id, current_id);
                } else if (parentMap.get(current_id) != neighbor_id) {
                    build_cycle_edge(parentMap, current_id, neighbor_id, cycle_edges);
                    return true;
                }
            }
        }
        return false;
    }

    private void build_cycle_edge(Map<Integer, Integer> parentMap, int start, int end, Set<GraphEdges> cycleEdges) {
        while (start != end) {
            int parent = parentMap.get(end);
            cycleEdges.add(new GraphEdges(start, end));
            end = parent;
        }
        cycleEdges.add((new GraphEdges(start, end)));
    }

    public static class GraphEdges {
        private final int start_node;
        private final int end_node;

        public GraphEdges(int start_node, int end_node) {
            this.start_node = start_node;
            this.end_node = end_node;
        }

        public int getStart_node() {
            return start_node;
        }

        public int getEnd_node() {
            return end_node;
        }
    }

    public static void main(String[] args) {
        // Generate a random graph
        Graph graph1 = Graph.generate_random_graph(5);
        graph1.print_graph();

        DFS dfs1 = new DFS(graph1);
        dfs1.dfs();

        System.out.println("***********************************\n The graph after the cycle detection : ");
        graph1.print_graph();
    }
}
