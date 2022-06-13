package GameOfLife;
/**
 * Umbrella class to iterate through
 */

import java.util.List;

public record Neighborhood(List<Cell> neighborhood , Cell middle, int midRow, int midCol) {

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Cell cell : neighborhood) {
            sb.append(cell);
            sb.append(" ");
        }

        return sb.toString();
    }

    //used to generate next states
    public int sum() {
        int sum = 0;

        for (Cell cell : neighborhood) {
            int toAdd = cell.getCellState();
            sum += toAdd;
        }

        return sum;
    }
}
