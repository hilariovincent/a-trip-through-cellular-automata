package LangtonsLoop;

import javafx.util.Pair;

import java.util.*;

public class Grid implements Iterable<Neighborhood> {
    private final List<List<Cell>> grid;
    private final int rowAndCol;

    public Grid(List<List<Cell>> grid) {
        this.grid = grid;
        rowAndCol = grid.size();
    }

    /*
    responsible for making the 2D Grid circular
     */
    public Cell getCell(int row, int col) {
        int circRow = Math.floorMod(row, rowAndCol);
        int cirCol = Math.floorMod(col, rowAndCol);

        return grid.get(circRow).get(cirCol);
    }

    /*
    evolves the Grid to create next states
     */
    public Grid evolve() {
        List<List<Cell>> nextGrid = new ArrayList<>();

        //This ensures that the nextGrid has the same amount of rows and cols as the previous grid
        for (int i = 0; i < rowAndCol; i++ ) {
            List<Cell> fullRow = new ArrayList<>(rowAndCol);

            nextGrid.add(i, fullRow);
        }

        Map<Cell, Map<String, Cell>> rule = Rule.getRule();

        for(Neighborhood neighborhood : this) {
            Cell middle = neighborhood.middle();

            int row = neighborhood.row();
            int col = neighborhood.col();

            String neighborhoodAsString = neighborhood.toString();

            Cell nextState = rule.get(middle).get(neighborhoodAsString);

            nextGrid.get(row).add(col, nextState);

        }

        return new Grid(nextGrid);
    }

    @Override
    public Iterator<Neighborhood> iterator() {
        return new Iterator<>() {
            int row = 0;
            int col = 0;

            @Override
            public boolean hasNext() {
                return row < rowAndCol;
            }

            @Override
            public Neighborhood next() {

                Cell north = getCell(row - 1, col);
                Cell west = getCell(row, col - 1);
                Cell south = getCell(row + 1, col);
                Cell east = getCell(row, col + 1);
                Cell middle = getCell(row, col);

                List<Cell> neighborhood = new ArrayList<>(Arrays.asList(north, east, south, west));

                int prevRow = row;
                int prevCol = col;

                if (col == rowAndCol - 1) {
                    row++;
                    col = 0;
                }
                else {
                    col++;
                }

                return new Neighborhood(neighborhood, middle, prevRow, prevCol);
            }
        };
    }

    public int size() {
        return rowAndCol;
    }
}
