package Validators;

import Models.Issue;
import Models.ValidationResult;
import Validators.Workers.BoxChecker;
import Validators.Workers.ColChecker;
import Validators.Workers.RowChecker;

import java.util.ArrayList;
import java.util.List;

public class TwentySevenThreadValidator implements Validator {

    @Override
    public ValidationResult validate(int[][] board) {
        List<Issue> issues = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        // 1. Create 9 Row Threads
        for (int r = 0; r < 9; r++) {
            threads.add(new Thread(new RowChecker(board, r, issues)));
        }

        // 2. Create 9 Column Threads
        for (int c = 0; c < 9; c++) {
            threads.add(new Thread(new ColChecker(board, c, issues)));
        }

        // 3. Create 9 Box Threads
        for (int b = 1; b <= 9; b++) {
            threads.add(new Thread(new BoxChecker(board, b, issues)));
        }

        // 4. Start all 27 threads
        for (Thread t : threads) {
            t.start();
        }

        // 5. Join all threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ValidationResult(issues.isEmpty(), issues);
    }
}