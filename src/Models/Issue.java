package Models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Issue {

    private final IssueType type; // ROW, COL, BOX
    private final int locationIndex; // The row/col/box number (1-9)
    private final Map<Integer, List<Integer>> indicesMap; // Maps duplicate value to list of indices where it appears
    //all the variables are final because they should not change after creation

    public Issue(IssueType type, int locationIndex, int duplicatedValue, List<Integer> duplicateIndices) {
        this.type = type;
        this.locationIndex = locationIndex;
        this.indicesMap = new HashMap<>();

        // Store a defensive copy of the list
        this.indicesMap.put(duplicatedValue, new ArrayList<>(duplicateIndices));
    }

    public IssueType getType() {
        return type;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public Map<Integer, List<Integer>> getIndicesMap() {
        // Return a defensive copy
        Map<Integer, List<Integer>> copy = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : indicesMap.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    @Override
    public String toString() {
        // Get the first (and only) entry from the map
        Map.Entry<Integer, List<Integer>> entry = indicesMap.entrySet().iterator().next();
        int duplicatedValue = entry.getKey();
        List<Integer> duplicateIndices = entry.getValue();
        return type + " " + locationIndex + ", #" + duplicatedValue + ", " + duplicateIndices;
    }
}
