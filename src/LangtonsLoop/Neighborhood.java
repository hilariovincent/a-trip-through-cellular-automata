package LangtonsLoop;

import java.util.List;

public record Neighborhood(List<Cell> neighborhood, Cell middle, int row, int col) {

    //used to return the neighborhood of the given middle cell
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Cell cell : neighborhood) {

            sb.append(cell.getCellState());

        }
        return sb.toString();
    }


}
