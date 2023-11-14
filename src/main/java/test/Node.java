package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Node {
    private int size;
    // copy constructor
    public Node(Node node ) {
     this.size = node.size;
     this.puzzle = node.getPuzzle();
     this.ID = node.getID() +1;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;

    public int[][] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(int[][] puzzle) {
        this.puzzle = puzzle;
    }

    private int [][] puzzle;

    public void setEmpty_point(Point empty_point) {
        this.empty_point = empty_point;
    }

    private Point empty_point;

    public Node(int s  , int[][] puzzle, int id) {
        this.size = s;
        this.puzzle = puzzle;
        this.ID =id;
    }

    public Node(int size , int id) {
        this.size = size;
        this.ID =id;
        this.puzzle = new int[size][size];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generate node for you?");
        String s = scanner.nextLine();
        if (s.equals("no"))
            initialPuzzle();
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
                if (output.equals("NULL") || output.equals("null") || output.equals("Null")){
                    num = -1 ;
                    this.empty_point = new Point(i,j);
                }
                else
                    num = Integer.parseInt(output);
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
        System.out.println("Done!");
        print_puzzle();
    }

    public void print_puzzle() {
        for (int[] nums : this.puzzle) {
            for (int num : nums) {
                System.out.print(num == -1 ? "null" : String.valueOf(num) + " ");
            }
            System.out.println();
        }
    }


    public int getID() {
        return ID;
    }

    public static void main(String[] args) {
        Node node = new Node(4 ,1);
    }
}
