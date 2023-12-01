package test.States;

public class RegulaerState extends State{


    public RegulaerState(int size, int[][] puzzleArr) {
        super(size, puzzleArr);
        setPuzzleRepresentation(initialize_state());
    }

    @Override
    public void setPuzzleRepresentation(String puzzleRepresentation) {
        this.puzzleRepresentation = puzzleRepresentation;
    }

    private String initialize_state() {
        StringBuilder sb = new StringBuilder();
        for (int[] nums : this.puzzleArr) {
            for (int num : nums) {
                sb.append(num == -1 ? "null" : String.format("%2d", num) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    @Override
    public boolean isGoalState() {
        return FinishState.getFinishState(this.size).puzzleRepresentation.
                replaceAll("\\s", "").equals(this.puzzleRepresentation.
                        replaceAll("\\s", ""));
    }
}
