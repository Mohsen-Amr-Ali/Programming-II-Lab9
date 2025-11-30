package Models;
import java.util.List;

public class Issue {

    private final IssueType type; // ROW, COL, BOX
    private final int locationIndex; // The row/col/box number (1-9)
    private final int duplicateValue; // The value that is duplicated
    private final List<Integer> indices; // List of indices where the duplicate value appears
    //all the variables are final because they should not change after creation

    public Issue(IssueType type, int locationIndex, int duplicateValue, List<Integer> indices) {
        this.type = type;
        this.locationIndex = locationIndex;
        this.duplicateValue = duplicateValue;
        this.indices = indices; // Pass by reference, no new ArrayList created
    }

    public IssueType getType() {
        return type;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public int getDuplicateValue() {
        return duplicateValue;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    @Override
    public String toString() {
        return type + " " + locationIndex + ", #" + duplicateValue + ", " + indices;
    }
}
