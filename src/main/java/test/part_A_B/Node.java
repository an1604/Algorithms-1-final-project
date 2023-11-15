package test.part_A_B;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Node {
    private int size;

    private int [][] puzzle;

    private boolean visited;

    private int ID;
    private Point empty_point;

    private String state;

    // copy constructor
    public Node(Node node, int increaseId , Point p) {
     this.size = node.size;
     this.puzzle = node.getPuzzle();
     this.ID = increaseId;
     this.visited = false;
     this.empty_point = p;
     this.state = initialize_state();
    }

    private String initialize_state() {
        StringBuilder sb = new StringBuilder();
        for (int[] nums : this.puzzle) {
            for (int num : nums) {
                sb.append(num == -1 ? "null" : String.format("%2d", num) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
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
    public String getState() {
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
        this.state = initialize_state();
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
        this.state = initialize_state();
        System.out.println("Done!");
        print_puzzle();
    }

    public void print_puzzle() {
        System.out.println(state);
    }



    public int getID() {
        return ID;
    }

}
