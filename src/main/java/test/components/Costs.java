package test.components;

import States.FinishState;
import States.State;

public class Costs {

    /**In this class we considered a couple of things:
 * The heuristics' functions are the Manhattan and the zero function.
 * We use Greedy-BFS as a non-heuristic function in part 3.
 **/

    private int g_n;
    private int f_n;
    private int h_n;
    private State node_state;
    private boolean calculated;

    public Costs(int depth, State state) {
        // we can access to the depth, but need to calculate the other
        g_n=depth; // That means the distance from the initial node
        node_state = state; // The state of the node
        this.calculated = false;
        f_n =0;
        h_n=0;
    }

    public int getG_n() {
        return g_n;
    }

    public void setG_n(int g_n) {
        this.g_n = g_n;
    }

    public int getF_n() {
        return f_n;
    }

    public void setF_n(int f_n) {
        this.f_n = f_n;
    }

    public int getH_n() {
        return h_n;
    }

    public void setH_n(int h_n) {
        this.h_n = h_n;
    }

    public State getNode_state() {
        return node_state;
    }

    public void setNode_state(State node_state) {
        this.node_state = node_state;
    }
    //Calculate the costs from this node to the end node
    public void get_Manhattan_costs(){
        int distance = 0;
        State final_state = FinishState.getFinishState(FinishState.getSize());
        int[][] current_state = node_state.getPuzzleArr();

        for (int i = 0; i < current_state.length; i++) {
            for (int j = 0; j < current_state[i].length; j++) {
                int value = current_state[i][j];

                if(value!=-1) {
                    try {
                        Point correct_position = get_correct_position(value, final_state);
                        distance += Math.abs(i - correct_position.getX()) + Math.abs(j - correct_position.getY());
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        this.h_n = distance;
        //After we compute h(n)' we can set f(n)
        compute_f();
    }
    //Method to find the current position of the value, to compute the Manhattan distance
    private Point get_correct_position(int value, State finalState) {
        int[][] puzzle_arr = finalState.getPuzzleArr();
        for (int i = 0; i < puzzle_arr.length; i++) {
            for (int j = 0; j < puzzle_arr[i].length; j++) {
                if(puzzle_arr[i][j]==value)
                    return new Point(i,j);
            }
        }

        return null;
    }

    public void get_Dijkstra_cost(){
        this.h_n=0;
        compute_f();
    }

    public boolean isCalculated() {
        return calculated;
    }
    public void get_Greedy_BFS(){
    /**
     A non-heuristic function that calculates f(n) as only the h(n) --> f(n) = h(n).
     It only considers how close a node is to the goal based on the heuristic.
     **/
    this.g_n=0;
    get_Manhattan_costs();

    }
    private void compute_f(){
        //Checking if we accomplish the mission (h(n)=0) is the finish line) and even for Dijkstra part (compare only g(n))
        if(this.h_n==0)
            this.f_n =0;
        else
            this.f_n = this.g_n + this.h_n;
        this.calculated = true;
    }
}
