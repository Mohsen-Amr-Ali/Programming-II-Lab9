package Validators.Workers;
import Models.Issue;
import Models.IssueType;
import java.util.ArrayList;
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

        for (int r = 0; r < 9; r++) { // iterate through each row for the given column
            int value = board[r][colIndex]; // get the value in the current column
            freq[value]++; // increment frequency

            if (freq[value] == 2) {
                List<Integer> duplicateIndices = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    if (board[i][colIndex] == value) {
                        duplicateIndices.add(i + 1);
                    }
                }
                int[] indices = duplicateIndices.stream().mapToInt(i -> i).toArray();
                Issue issue = new Issue(IssueType.COLUMN, colIndex + 1, value, indices);
                synchronized (sharedIssues) {
                    sharedIssues.add(issue);
                }
            }
        }
    }
}
