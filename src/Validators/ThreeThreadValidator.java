package Validators;

import Models.Issue;
import Models.ValidationResult;
import Validators.Workers.RowChecker;
import Validators.Workers.ColChecker;
import Validators.Workers.BoxChecker;
import java.util.ArrayList;
import java.util.List;

public class ThreeThreadValidator implements Validator {

    @Override
    public ValidationResult validate(int[][] board) {
        List<Issue> issues = new ArrayList<>();

        Thread t1 = new Thread(() -> { // Rows
            for (int r = 0; r < 9; r++)
                new RowChecker(board, r, issues).run();
        });

        Thread t2 = new Thread(() -> { // Columns
            for (int c = 0; c < 9; c++)
                new ColChecker(board, c, issues).run();
        });

        Thread t3 = new Thread(() -> { // Boxes
            for (int b = 1; b <= 9; b++)
                new BoxChecker(board, b, issues).run();
        });

        t1.start(); t2.start(); t3.start(); // Start all threads

        try {
            t1.join(); t2.join(); t3.join(); // Wait for all threads to finish
        } catch (InterruptedException ignored) {}

        return new ValidationResult(issues.isEmpty(), issues); // Return result
    }
}
