//Open a CSV file
//Read exactly 9 lines
//Parse exactly 9 integers in each
//Put them into int[9][9]
//Return the board
package Models;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class BoardLoader {

    public static int[][] loadCSV(String path) {
        int[][] board = new int[9][9]; // Initialize a 9x9 board

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line; // To hold each line read from the file
            int row = 0;

            while ((line = br.readLine()) != null && row < 9) { // Read up to 9 lines
                String[] values = line.split(","); // Split the line by commas

                if (values.length != 9) {
                    throw new RuntimeException("Invalid row length at row " + (row + 1)); // Ensure exactly 9 values per row
                }

                for (int col = 0; col < 9; col++) {
                    board[row][col] = Integer.parseInt(values[col].trim()); // Parse and assign the integer value
                }

                row++;
            }

            if (row != 9) {
                throw new RuntimeException("CSV file must contain exactly 9 rows."); // Ensure exactly 9 rows
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return board;
    }
}