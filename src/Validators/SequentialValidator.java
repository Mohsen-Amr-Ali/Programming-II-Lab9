package Validators;

import Models.Issue;
import Models.ValidationResult;
import Validators.Workers.BoxChecker;
import Validators.Workers.ColChecker;
import Validators.Workers.RowChecker;

import java.util.ArrayList;
import java.util.List;

public class SequentialValidator implements Validator {

    @Override
    public ValidationResult validate(int[][] board) {
        List<Issue> issues = new ArrayList<>();

        // 1. Check all Rows sequentially
        for (int r = 0; r < 9; r++) {
            // We create the worker and call .run() directly.
            // This executes the logic immediately on the current thread
            // without spawning a new thread overhead.
            new RowChecker(board, r, issues).run();
        }

        // 2. Check all Columns sequentially
        for (int c = 0; c < 9; c++) {
            new ColChecker(board, c, issues).run();
        }

        // 3. Check all Boxes sequentially
        for (int b = 1; b <= 9; b++) {
            new BoxChecker(board, b, issues).run();
        }

        return new ValidationResult(issues.isEmpty(), issues);
    }
}