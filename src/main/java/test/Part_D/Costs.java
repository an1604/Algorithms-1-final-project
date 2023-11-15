package test.Part_D;

import States.FinishState;
import States.State;

public class Costs {


    private int g_n;
    private int f_n;
    private int h_n;
    private State node_state;

    public Costs(int depth, State state) {
        // we can access to the depth, but need to calculate the other
        g_n=depth;
        node_state = state;
        f_n =0;
        h_n=0;
    }
    //Calculate the costs from this node to the end node
    public int calculate_Manhattan_costs(){

        /** We consider a couple of things:
         * 1) g(n) is the depth of the search from the initial node.
         * 2) h(n) is the number of elements that are not in place, that means that if h(n)=0 we accomplished the target.
         * 3) f(n) = g(n) + h(n)*/

        int counter =0;
        State final_state = FinishState.getFinishState(FinishState.getSize());
        for (int i = 0; i < node_state.getPuzzleArr().length; i++) {
            for (int j = 0; j < node_state.getPuzzleArr().length; j++) {
                if(node_state.getPuzzleArr()[i][j] != final_state.getPuzzleArr()[i][j]){}
                counter++;
            }
        }
        return counter;
    }

    public void compute(String prompt){
        // for Manhattan metric
        if(prompt.equals("M")){}
        // for Dijkstra
        else if(prompt.equals("D")){}
        else{
            System.out.println("Cannot undrestand you");
        }
    }
}
