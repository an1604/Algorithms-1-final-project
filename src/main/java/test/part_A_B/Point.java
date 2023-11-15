package test.part_A_B;

import java.util.ArrayList;
import java.util.List;

public class Point{
    private int x ;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public List<Integer> getPoint() {
        java.util.List<Integer> pointList = new ArrayList<>();
        pointList.add(this.x);
        pointList.add(this.y);
        return pointList;
    }
// Given 2 points, we want to check an edge between them
    public boolean check_coordinate(Point other_point){
        //Check each state - up,down,right,left

        //Up & down
        if(Math.abs(this.x - other_point.getX()) ==1 && this.y== other_point.getY()){
            return true;
        }

        // Right & left
        if(Math.abs(this.y - other_point.getY()) ==1 && this.x == other_point.getX()){
           return true;
        }

        // Otherwise
        return false;
    }
}
