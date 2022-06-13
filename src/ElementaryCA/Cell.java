package ElementaryCA;
/**
 * This file handles the cells of this project
 * assigns numerical and life status
 *
 * @author Vincent Hilario
 */
public enum Cell {
    ALIVE(1), DEAD(0);

    private final int cellState;
    Cell(int i) {
        cellState = i;
    }
    public int getCellState() {
        return cellState;
    }

    public static Cell fromChar(final char c) {
        if (c == '1') {
            return ALIVE;
        }
        else if (c == '0') {
            return DEAD;
        }
        throw new IllegalArgumentException("Argument, " + c + ", must be either 1 or 0");
    }

}
