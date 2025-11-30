import Factory.ValidatorFactory;
import Models.BoardLoader;
import Models.Issue;
import Models.IssueType;
import Models.ValidationResult;
import Validators.Validator;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Expected args: [csvPath] [mode]
        if (args.length < 2) {
            System.out.println("Usage: java Main <csv_path> <mode>");
            System.out.println("Modes: 0 (Sequential), 3 (3-Threads), 27 (27-Threads)");
            return;
        }


        String csvPath = args[0];
        int mode;

        try {
            mode = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Mode must be an integer (0, 3, or 27).");
            return;
        }

        // Validate mode is one of the allowed values
        if (mode != 0 && mode != 3 && mode != 27) {
            System.out.println("Invalid mode: " + mode);
            System.out.println("Mode must be 0 (Sequential), 3 (3-Threads), or 27 (27-Threads).");
            return;
        }

        // 1. Load Board
        System.out.println("Loading board from: " + csvPath);
        int[][] board = BoardLoader.loadCSV(csvPath);

        // 2. Get Validator
        Validator validator = ValidatorFactory.getValidator(mode);
        System.out.println("Running validation with mode: " + mode);
        System.out.println();

        // 3. Validate
        long startTime = System.nanoTime();
        ValidationResult result = validator.validate(board);
        long endTime = System.nanoTime();

        // 4. Print Results
        System.out.println("═══════════════════════════════════════════════════════");
        if (result.isValid()) {
            System.out.println("VALID - The Sudoku board is valid!");
        } else {
            System.out.println("INVALID - The Sudoku board has errors:");
            System.out.println();

            // Separate issues by type
            List<Issue> rowIssues = new ArrayList<>();
            List<Issue> colIssues = new ArrayList<>();
            List<Issue> boxIssues = new ArrayList<>();

            for (Issue issue : result.getIssues()) {
                if (issue.getType() == IssueType.ROW) {
                    rowIssues.add(issue);
                } else if (issue.getType() == IssueType.COLUMN) {
                    colIssues.add(issue);
                } else if (issue.getType() == IssueType.BOX) {
                    boxIssues.add(issue);
                }
            }

            // Print ROW issues
            if (!rowIssues.isEmpty()) {
                System.out.println("Row Errors:");
                for (Issue issue : rowIssues) {
                    System.out.println("  " + issue);
                }
                System.out.println();
            }

            // Print separator and COL issues
            if (!colIssues.isEmpty()) {
                if (!rowIssues.isEmpty()) {
                    System.out.println("------------------------------------------");
                    System.out.println();
                }
                System.out.println("Column Errors:");
                for (Issue issue : colIssues) {
                    System.out.println("  " + issue);
                }
                System.out.println();
            }

            // Print separator and BOX issues
            if (!boxIssues.isEmpty()) {
                if (!rowIssues.isEmpty() || !colIssues.isEmpty()) {
                    System.out.println("------------------------------------------");
                    System.out.println();
                }
                System.out.println("Box Errors:");
                for (Issue issue : boxIssues) {
                    System.out.println("  " + issue);
                }
                System.out.println();
            }
        }
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Validation took: " + (endTime - startTime) / 1_000 + " μs");
        System.out.println();
    }
}