package Validators.Workers;

import Models.Issue;
import Models.IssueType;
import java.util.ArrayList;
import java.util.List;

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
        int[] freq = new int[10]; // index 1-9

        for (int c = 0; c < 9; c++) {
            int value = board[rowIndex][c];
            freq[value]++;
            if (freq[value] == 2) {
                List<Integer> duplicateIndices = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    if (board[rowIndex][i] == value) {
                        duplicateIndices.add(i + 1);
                    }
                }
                int[] indices = duplicateIndices.stream().mapToInt(i -> i).toArray();
                Issue issue = new Issue(IssueType.ROW, rowIndex + 1, value, indices);
                synchronized (sharedIssues) {
                    sharedIssues.add(issue);
                }
            }
        }
    }
}
