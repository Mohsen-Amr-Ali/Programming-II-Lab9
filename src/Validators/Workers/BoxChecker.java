package Validators.Workers;
import Models.Issue;
import Models.IssueType;
import java.util.List;

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
        int[] freq = new int[10]; // index 1–9
        int[] values = new int[9]; // to store values in the box

        // convert boxIndex (1–9) → start row/col
        int startRow = ((boxIndex - 1) / 3) * 3;
        int startCol = ((boxIndex - 1) % 3) * 3;

        int k = 0;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                values[k++] = board[r][c];
            }
        }

        // PASS 2: Check for duplicates
        for (int value : values) {
            freq[value]++;
            if (freq[value] == 2) {
                // Now we pass the fully populated 'values' array
                Issue issue = new Issue(IssueType.BOX, boxIndex, value, values.clone());
                synchronized (sharedIssues) {
                    sharedIssues.add(issue);
                }
            }
        }
    }
}
