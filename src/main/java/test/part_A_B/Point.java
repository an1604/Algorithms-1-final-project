package test.part_A_B;

import java.util.ArrayList;
import java.util.List;

public class Point{
    private int x ;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public List<Integer> getPoint() {
        java.util.List<Integer> pointList = new ArrayList<>();
        pointList.add(this.x);
        pointList.add(this.y);
        return pointList;
    }
}
