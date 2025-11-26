//Represents one specific duplication problem in a row, column, or box.
package models;
import java.util.Arrays;

public class Issue {

    private final IssueType type; // ROW, COL, BOX
    private final int index; // 1 to 9
    private final int duplicatedValue; // The repeated digit
    private final int[] fullArray; // The full row/col/box values
    //all the variables are final because they should not change after creation

    public Issue(String type, int index, int duplicatedValue, int[] fullArray) {
        this.type = type;
        this.index = index;
        this.duplicatedValue = duplicatedValue;
        this.fullArray = Arrays.copyOf(fullArray, fullArray.length);
    }
    public enum IssueType {
        ROW,
        COL,
        BOX
    }
    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getDuplicatedValue() {
        return duplicatedValue;
    }

    public int[] getFullArray(){
        return Arrays.copyOf(fullArray, fullArray.length);
    }
    @Override
    public String toString() {
        return type + " " + index + ", #" + duplicatedValue + ", " + Arrays.toString(fullArray);
    }
}
