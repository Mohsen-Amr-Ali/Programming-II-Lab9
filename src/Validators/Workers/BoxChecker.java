package Validators.Workers;
import Models.Issue;
import Models.IssueType;
import java.util.ArrayList;
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

        // convert boxIndex (1–9) → start row/col
        int startRow = ((boxIndex - 1) / 3) * 3;
        int startCol = ((boxIndex - 1) % 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                int value = board[r][c];
                freq[value]++;
                if (freq[value] == 2) {
                    List<Integer> duplicateIndices = new ArrayList<>();
                    for (int br = startRow; br < startRow + 3; br++) {
                        for (int bc = startCol; bc < startCol + 3; bc++) {
                            if (board[br][bc] == value) {
                                duplicateIndices.add((br % 3) * 3 + (bc % 3) + 1);
                            }
                        }
                    }
                    int[] indices = duplicateIndices.stream().mapToInt(i -> i).toArray();
                    Issue issue = new Issue(IssueType.BOX, boxIndex, value, indices);
                    synchronized (sharedIssues) {
                        sharedIssues.add(issue);
                    }
                }
            }
        }
    }
}
