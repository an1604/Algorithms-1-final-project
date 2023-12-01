package test.States;

public class FinishState extends State {
    static FinishState finishState= null; // Singleton

    public static FinishState getFinishState(int size) {
        if(finishState==null){
            finishState = new FinishState(size);
        }
        return finishState;
    }

    public FinishState(int size) {
        super(size, initializeFinalStateArray(size));
        setPuzzleRepresentation(initializeFinalStateString(size));
    }

    private static String initializeFinalStateString(int size) {
        StringBuilder sb = new StringBuilder();
        int count = 1;

        // Append the elements to the StringBuilder
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (count < size * size) {
                    sb.append(String.format("%2d ", count));
                    count++;
                } else {
                    // Append "null" for the empty space
                    sb.append("null ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private static int[][] initializeFinalStateArray(int size) {
        int stateSize = (size * size) - 1;
        int[][] finalState = new int[size][size];

        for (int i = 0; i < size; i++) {
            // Getting size elements in each row
            for (int j = 0; j < size; j++) {
                finalState[i][j] = i * size + j + 1;
            }
        }

        // The last place is the empty one.
        finalState[size - 1][size - 1] = -1;

        return finalState;
    }

    @Override
    public void setPuzzleRepresentation(String puzzleRepresentation) {
        this.puzzleRepresentation = puzzleRepresentation;
    }

    @Override
    public boolean isGoalState() {
        return true;
    }

    public static int getSize() {
        return size;
    }

}
