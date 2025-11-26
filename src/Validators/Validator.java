//Define the one and only method that every validator (sequential, 3-thread, 27-thread) must implement.
package Validators;
import Models.ValidationResult;

public interface Validator {
    ValidationResult validate(int[][] board);
}
