package States;

public abstract class State {
    protected int[][] puzzleArr;

    public abstract void setPuzzleRepresentation(String puzzleRepresentation);

    protected String puzzleRepresentation;

    protected static int size;

    public State(int size, int[][] puzzleArr) {
        this.size = size;
        this.puzzleArr = puzzleArr;
    }
//Checking if the state is the final state (if its regular state)
    public abstract boolean isGoalState();

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
