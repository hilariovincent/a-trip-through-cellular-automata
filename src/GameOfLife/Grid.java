package GameOfLife;
/**
 * This file is similar to the Generation class in
 * the Elementary CA but as a 2D Grid instead of 1D
 *
 */

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Grid implements Iterable<Neighborhood> {
    //keeps track of current state of the grid
    private final List<List<Cell>> currGrid;
    //length of row and column given by user
    private final int rowLength;
    private final int colLength;
    //tracks current row of cell
    private int currRow;
    //tracks current column of cell
    private int currCol;

    public Grid(List<List<Cell>> currGrid) {
        this.currGrid = currGrid;
        rowLength = currGrid.size();
        colLength = rowLength;
    }

    public Pair<Integer, Integer> getDimensions() {
        return new Pair<>(rowLength, colLength);
    }

    public Grid evolve() {
        List<List<Cell>> nextGrid = new ArrayList<>();

        for (int i = 0; i < rowLength; i++ ) {
            List<Cell> fullRow = new ArrayList<>(colLength);

            nextGrid.add(i, fullRow);
        }

        for(Neighborhood neighborhood : this) {
            int sum = neighborhood.sum();
            int row = neighborhood.midRow();
            int col = neighborhood.midCol();
            Cell middle = neighborhood.middle();

            //Implements the rules of the game
            if (middle == Cell.ALIVE) {
                if (sum == 2 || sum == 3) {
                    nextGrid.get(row).add(col, Cell.ALIVE);
                }
                else {
                    nextGrid.get(row).add(col, Cell.DEAD);
                }
            }
            else if (middle == Cell.DEAD) {
                if (sum == 3) {
                    nextGrid.get(row).add(col, Cell.ALIVE);
                }
                else {
                    nextGrid.get(row).add(col, Cell.DEAD);
                }
            }
        }


        return new Grid(nextGrid);
    }

    /**
     * I used the Math library to make the Grid circular
     * @param row
     * @param col
     * @return
     */
    public Cell getCell(int row, int col) {
        int circRow = Math.floorMod(row, rowLength);
        int circCol = Math.floorMod(col, colLength);

        return currGrid.get(circRow).get(circCol);

    }

    /**
     * The iterator makes it os we can iterate through the grid
     * through the type Neighborhood similar to ElementaryCA
     *
     * In this case, the neighborhood is the von neumann neighborhood
     * specified in the rules of Game of Life
     * @return
     */
    @Override
    public Iterator<Neighborhood> iterator() {
        return new Iterator<>(){
            int row = 0;
            int col = 0;

            @Override
            public boolean hasNext() {
                return row < rowLength;
            }

            @Override
            public Neighborhood next() {

                final Cell northWest = getCell(row - 1, col - 1);
                final Cell north = getCell(row - 1, col);
                final Cell northEast = getCell(row - 1, col + 1);
                final Cell west = getCell(row, col - 1);
                final Cell middle = getCell(row, col);
                final Cell east = getCell(row, col + 1);
                final Cell southWest = getCell(row + 1, col - 1);
                final Cell south = getCell(row + 1, col);
                final Cell southEast = getCell(row + 1, col + 1);

                List<Cell> neighborhood = new ArrayList<>(Arrays.asList(northWest, north, northEast,
                        west, east, southWest, south, southEast));

                int prevRow = row;
                int prevCol = col;

                //increments col and row
                if (col == colLength - 1) {
                    row++;
                    col = 0;
                }
                else {
                    col++;
                }

                return new Neighborhood(neighborhood , middle, prevRow, prevCol);
            }
        };
    }




}
