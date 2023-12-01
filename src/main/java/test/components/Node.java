package test.components;

import test.States.RegulaerState;
import test.States.State;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Node {
    /**The Node class, This class represent a single node in the graph.
     * We considered a couple of things:
     * size (int) - The size of the node (in this case, the size of the puzzle).
     * puzzle (int[][]) - The puzzle elements.
     * visited (bool) - A boolean value that checks if the node has been visited in the traverse.
     * ID(int) - This id integrate with the graph's id.
     * state (State) - Element that represents the puzzle state compared to the final state puzzle.
     * costs_for_AStar (Costs) -Element that represents the costs of A star functions (f(n),g(n),h(n)).
     * depth (int) - The depth from the start node.
     * empty_point (Point) - Element that represents the coordinates of the empty block in the puzzle.
     **/
    private int size;

    private int [][] puzzle;

    private boolean visited;

    private int ID;
    private Point empty_point;

    private State state;

    private Costs costs_for_AStar;
    private int depth;

    // copy constructor

    public Node(Node node, int increaseId , Point p, int depth) {
     this.size = node.size;
     this.puzzle = node.getPuzzle();
     this.ID = increaseId;
     this.visited = false;
     this.empty_point = p;
     this.state =new RegulaerState(size , puzzle);
     this.depth = depth;
     //This part is for A star...
     this.costs_for_AStar = new Costs(depth, state);
    }


    public Node(int size , int id, int depth) {
        this.visited = false;
        this.size = size;
        this.ID =id;
        this.puzzle = new int[size][size];
        this.depth = depth;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generate node for you?");
        String s = scanner.nextLine();
        if (s.equals("no"))
            initial_Random_Puzzle();
        else
            this.state =new RegulaerState(size , puzzle);

    }


    //Ctor for the final state case.

    public Node(int size, int increaseId, Point point, int[][] finalState,int depth) {
        this.size = size;
        this.ID = increaseId;
        this.empty_point = point;
        this.puzzle = finalState;
        this.state =new RegulaerState(size , puzzle);
        this.depth =depth;
    }
    /**Getters---------------------------------------------------------*/
    public int getID() {
        return ID;
    }

    public int getDepth() {
        return depth;
    }
    public State getState() {
        return state;
    }
    public int getSize() {
        return size;
    }

    public boolean isVisited() {
        return !visited;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public Costs getCosts_for_AStar() {
        return costs_for_AStar;
    }

    public Point getEmpty_point() {
        return empty_point;
    }
    /**--------------------------------------------------------------------------*/
    /**Setters---------------------------------------------------------*/

    public void setSize(int size) {
        this.size = size;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCosts_for_AStar(Costs costs_for_AStar) {
        this.costs_for_AStar = costs_for_AStar;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPuzzle(int[][] puzzle) {
        this.puzzle = puzzle;
        //After we're setting the puzzle, we initialize the state and the costs
        this.state = new RegulaerState(size , puzzle);
        this.costs_for_AStar.setNode_state(state);
    }

    public void setEmpty_point(Point empty_point) {
        this.empty_point = empty_point;
    }
    /**--------------------------------------------------------------------------*/

    private void initial_Random_Puzzle(){
        Scanner scanner = new Scanner(System.in);
        Set<Integer> numSet = new HashSet();
        String output ="" ;
        int num =0;
        System.out.println("Enter the numbers that you want to append according to the size  " + this.size + ":");
        System.out.println("If you want to append null (empty place) , just enter null");
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.println("The next number  for place  (" + i + "," + j +"): ");
                output = scanner.nextLine();
                if (output.equals("NULL") || output.equals("null") || output.equals("Null") || output.equals("-1")){
                    num = -1 ;
                    this.empty_point = new Point(i,j);
                    System.out.println("Getting the null point!");
                }
                else {
                    try {
                        num = Integer.parseInt(output);
                    }
                    catch (NumberFormatException e){
                        e.printStackTrace();
                        System.out.println("try again...");
                        j-=1;
                        continue;
                    }
                }
                    if (numSet.add( num)){
                   this.puzzle[i][j]= num;
                   System.out.println("The number " +  output + " added successfully!");
               }
               else {
                   System.out.println("This number already appended! , try again..");
                    j-=1;
               }
            }
        }
        // Initialize the new state of the puzzle
        this.state =new RegulaerState(size , puzzle);
        System.out.println("Done!");
        print_puzzle();
    }

    public void print_puzzle() {
        state.print();
    }

    @Override
    public String toString() {
        return state.getPuzzleRepresentation();
    }

}
