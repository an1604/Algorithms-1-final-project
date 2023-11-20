package test.States;

public abstract class State {
    /**The State class, an abstract class.
     * This class represents the current state of the node compared to the final node.
     * We considered a couple of things:
     * puzzleArr (init[][]) - The puzzle representation.
     *puzzleRepresentation (String) - The string representation.
     * size (int) - The size of the puzzle. */
    protected int[][] puzzleArr;

    public abstract void setPuzzleRepresentation(String puzzleRepresentation);

    protected String puzzleRepresentation;

    protected static int size;

    public State(int size, int[][] puzzleArr) {
        this.size = size;
        this.puzzleArr = puzzleArr;
    }

    public abstract boolean isGoalState(); //Checking if the state is the final state (if its regular state)

    public int[][] getPuzzleArr() {
        return puzzleArr;
    }

    public String getPuzzleRepresentation() {
        return puzzleRepresentation;
    }

    public static int getSize() {
        return size;
    }

    public void print(){
        System.out.println(puzzleRepresentation);
    }
}
