package Validators;

import models.ValidationResult;

public interface Validator {
    ValidationResult validate(int[][] board);
}
