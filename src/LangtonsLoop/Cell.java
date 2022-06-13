package LangtonsLoop;

import javafx.scene.paint.Color;

public enum Cell {

    BLACK(0), BLUE(1), RED(2), LIME(3),
    YELLOW(4), PINK(5), WHITE(6), CYAN(7);

    private final int cellState;

    Cell(int cellState) {
        this.cellState = cellState;
    }

    public int getCellState() {
        return cellState;
    }

    public static Cell fromChar(char c) {
        switch (c) {
            case '0':
                return BLACK;
            case '1':
                return BLUE;
            case '2':
                return RED;
            case '3' :
                return LIME;
            case '4' :
                return YELLOW;
            case '5':
                return PINK;
            case '6':
                return WHITE;
            case '7':
                return CYAN;
        }

        throw new IllegalArgumentException("Argument, " + c + ", must be one of these numbers: [0,1,2,3,4,5,6,7]");
    }

    /**
     * assigns each cell with corresponding color
     * @param cell
     * @return color
     */
    public static Color toColor(Cell cell) {
        Color color;
        switch (cell) {
            case BLACK -> color = Color.BLACK;
            case RED -> color = Color.RED;
            case BLUE -> color = Color.BLUE;
            case CYAN -> color = Color.CYAN;
            case LIME -> color = Color.LIME;
            case PINK -> color = Color.FUCHSIA;
            case WHITE -> color = Color.WHITE;
            case YELLOW -> color = Color.YELLOW;


            default -> throw new IllegalStateException("Unexpected value: " + cell);
        }

        return color;
    }
}
