package ElementaryCA;

/**
 * Class for the iterator
 *
 * @author Vincent Hilario
 */
public record Neighborhood(Cell left, Cell middle, Cell right) {
    @Override
    public String toString() {
        return left + " <- " + middle + " -> " + right;
    }

    /**
     * use for rules
     * @param leftCell
     * @param middleCell
     * @param rightCell
     * @return boolean
     */
    public boolean equals(final char leftCell, final char middleCell, final char rightCell) {
        return this.equals(new Neighborhood(
                Cell.fromChar(leftCell),
                Cell.fromChar(middleCell),
                Cell.fromChar(rightCell)));
    }


}
