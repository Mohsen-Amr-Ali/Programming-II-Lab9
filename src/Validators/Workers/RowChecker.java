package Validators.Workers;

import Models.Issue;
import Models.IssueType;
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

        for (int value : board[rowIndex]) {
            freq[value]++;
            if (freq[value] == 2) {
                Issue issue = new Issue(IssueType.ROW, rowIndex, value, board[rowIndex].clone());
                synchronized (sharedIssues) {
                    sharedIssues.add(issue);
                }
            }
        }
    }
}
