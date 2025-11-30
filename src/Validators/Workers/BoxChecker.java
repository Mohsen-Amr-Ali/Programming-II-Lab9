package Validators.Workers;
import Models.Issue;
import Models.IssueType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxChecker implements Runnable {

    private final int[][] board;
    private final int boxIndex;     // 1–9
    private final List<Issue> sharedIssues;

    public BoxChecker(int[][] board, int boxIndex, List<Issue> sharedIssues) {
        this.board = board;
        this.boxIndex = boxIndex;
        this.sharedIssues = sharedIssues;
    }

    @Override
    public void run() {
        // Map each value to its positions (1-indexed, within the box)
        Map<Integer, List<Integer>> valuePositions = new HashMap<>();

        // convert boxIndex (1–9) → start row/col
        int startRow = ((boxIndex - 1) / 3) * 3;
        int startCol = ((boxIndex - 1) % 3) * 3;

        // Single loop: collect positions and create issues immediately upon duplicate detection
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int value = board[i][j];
                valuePositions.putIfAbsent(value, new ArrayList<>());
                List<Integer> positions = valuePositions.get(value);
                // Calculate 1-indexed position within the 3x3 box
                int positionInBox = (i % 3) * 3 + (j % 3) + 1;
                positions.add(positionInBox);

                // Create issue immediately when duplicate is detected (2nd occurrence)
                if (positions.size() == 2) {
                    Issue issue = new Issue(IssueType.BOX, boxIndex, value, positions);
                    synchronized (sharedIssues) {
                        sharedIssues.add(issue);
                    }
                }
            }
        }
    }
}
