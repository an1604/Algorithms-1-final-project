package test.agorithms;

import test.components.Node;

public interface Algorithms {
    /**The Algorithms interface.
     * This interface holds essential methods for all different traversal algorithms,
     * such as traverse, name, and in our specific issue we want to get the number of developed vertices also.
     **/
    public void traverse();
    public int num_of_vertices();
    public String Name();
    public Node getStart_node();
}
