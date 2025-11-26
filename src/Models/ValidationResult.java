package Models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final boolean isValid;
    private final List<Issue> issues;

    public ValidationResult(boolean isValid, List<Issue> issues) {
        this.isValid = isValid;
        this.issues = new ArrayList<>(issues);
    }

    public boolean isValid() {
        return isValid;
    }

    public List<Issue> getIssues() {
        return Collections.unmodifiableList(issues);
    }
}