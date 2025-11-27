import Factory.ValidatorFactory;
import Models.BoardLoader;
import Models.Issue;
import Models.ValidationResult;
import Validators.Validator;

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

        // 1. Load Board
        System.out.println("Loading board from: " + csvPath);
        int[][] board = BoardLoader.loadCSV(csvPath);

        // 2. Get Validator
        Validator validator = ValidatorFactory.getValidator(mode);
        System.out.println("Running validation with mode: " + mode);

        // 3. Validate
        long startTime = System.nanoTime();
        ValidationResult result = validator.validate(board);
        long endTime = System.nanoTime();

        // 4. Print Results
        if (result.isValid()) {
            System.out.println("The Sudoku board is VALID.");
        } else {
            System.out.println("The Sudoku board is INVALID.");
            System.out.println("Issues found:");
            for (Issue issue : result.getIssues()) {
                System.out.println(" - " + issue);
            }
        }

        System.out.println("Validation took: " + (endTime - startTime) / 1_000_000 + " ms");
    }
}