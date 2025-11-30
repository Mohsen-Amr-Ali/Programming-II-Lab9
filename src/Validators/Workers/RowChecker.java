package Validators.Workers;

import Models.Issue;
import Models.IssueType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowChecker implements Runnable {

    private final int[][] board;
    private final int rowIndex;
    private final List<Issue> sharedIssues;

    public RowChecker(int[][] board, int rowIndex, List<Issue> sharedIssues) {
        this.board = board;
        this.rowIndex = rowIndex;
        this.sharedIssues = sharedIssues;
    }

    @Override
    public void run() {
        // Map each value to its positions (1-indexed)
        Map<Integer, List<Integer>> valuePositions = new HashMap<>();

        // Single loop: collect positions and create issues immediately upon duplicate detection
        int[] values = board[rowIndex].clone();
        int i = 1;
        for (int value : values) {
            valuePositions.putIfAbsent(value, new ArrayList<>());
            List<Integer> positions = valuePositions.get(value);
            positions.add(i++); // Store 1-indexed position

            // Create issue immediately when duplicate is detected (2nd occurrence)
            if (positions.size() == 2) {
                Issue issue = new Issue(IssueType.ROW, rowIndex + 1, value, positions);
                synchronized (sharedIssues) {
                    sharedIssues.add(issue);
                }
            }
        }
    }
}
