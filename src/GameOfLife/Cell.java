package GameOfLife;
/**
 * This file handles the cells of this project
 * assigns numerical and life status
 *
 * This is the same as the Cell in Elementary CA
 * one thing to do for optimization is to make this file
 * the same as the other one: util file
 *
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
