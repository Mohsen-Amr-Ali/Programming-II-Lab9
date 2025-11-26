package Validators.Workers;
import Models.Issue;
import Models.IssueType;
import java.util.List;

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
        int[] freq = new int[10]; // index 1–9

        for (int row = 0; row < 9; row++) { // iterate through each row for the given column
            int value = board[row][colIndex]; // get the value in the current column
            freq[value]++; // increment frequency

            if (freq[value] == 2) {
                int[] fullColumn = new int[9];
                for (int r = 0; r < 9; r++) {
                    fullColumn[r] = board[r][colIndex]; // construct the full column array
                }
                Issue issue = new Issue(IssueType.COLUMN, colIndex, value, fullColumn);
                synchronized (sharedIssues) {
                    sharedIssues.add(issue);
                }
            }
        }
    }
}
