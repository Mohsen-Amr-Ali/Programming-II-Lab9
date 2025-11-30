package Validators.Workers;
import Models.Issue;
import Models.IssueType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColChecker implements Runnable {

    private final int[][] board;
    private final int colIndex;
    private final List<Issue> sharedIssues;

    public ColChecker(int[][] board, int colIndex, List<Issue> sharedIssues) {
        this.board = board;
        this.colIndex = colIndex;
        this.sharedIssues = sharedIssues;
    }

    @Override
    public void run() {
        // Map each value to its positions (1-indexed)
        Map<Integer, List<Integer>> valuePositions = new HashMap<>();

        // Single loop: collect positions and create issues immediately upon duplicate detection
        for (int i = 0; i < 9; i++) {
            int value = board[i][colIndex];
            valuePositions.putIfAbsent(value, new ArrayList<>());
            List<Integer> positions = valuePositions.get(value);
            positions.add(i + 1); // Store 1-indexed position

            // Create issue immediately when duplicate is detected (2nd occurrence)
            if (positions.size() == 2) {
                Issue issue = new Issue(IssueType.COLUMN, colIndex + 1, value, positions);
                synchronized (sharedIssues) {
                    sharedIssues.add(issue);
                }
            }
        }
    }
}
