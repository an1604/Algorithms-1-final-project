package test.part_A_B;

import States.RegulaerState;
import States.State;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Node {
    private int size;

    private int [][] puzzle;

    private boolean visited;

    private int ID;
    private Point empty_point;

    private State state;

    // copy constructor
    public Node(Node node, int increaseId , Point p) {
     this.size = node.size;
     this.puzzle = node.getPuzzle();
     this.ID = increaseId;
     this.visited = false;
     this.empty_point = p;
     this.state =new RegulaerState(size , puzzle);
    }
//Ctor for the final state case.
    public Node(int size, int increaseId, Point point, int[][] finalState) {
        this.size = size;
        this.ID = increaseId;
        this.empty_point = point;
        this.puzzle = finalState;
        this.state =new RegulaerState(size , puzzle);
    }




    // regular Ctor
    public Node(int size , int id) {
        this.visited = false;
        this.size = size;
        this.ID =id;
        this.puzzle = new int[size][size];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generate node for you?");
        String s = scanner.nextLine();
        if (s.equals("no"))
            initialPuzzle();

    }
    public State getState() {
        return state;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(int[][] puzzle) {
        this.puzzle = puzzle;
        //After we're setting the puzzle, we initialize the state
        this.state = new RegulaerState(size , puzzle);
    }

    public void setEmpty_point(Point empty_point) {
        this.empty_point = empty_point;
    }

    public Point getEmpty_point() {
        return empty_point;
    }

    private void initialPuzzle(){
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



    public int getID() {
        return ID;
    }

}
