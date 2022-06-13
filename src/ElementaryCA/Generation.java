package ElementaryCA;
/**
 * This class handles the generations of the CA
 *
 * @author Vincent Hilario
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Generation implements Iterable<Neighborhood>{
    //generations of the CA
    List<Cell> generation;

    public Generation(List<Cell> generation) {
        this.generation = generation;
    }

    //responsible for making the CA circular
    public Cell get(int index) {
        return generation.get(Math.floorMod(index, generation.size()));
    }

    public int size() {
        return generation.size();
    }


    /**
     * the iterator makes it so you can iterate through the
     * type/umbrella class Neighborhood the same way you would iterate
     * through a regular list in an enhanced for loop
     *
     * in this case, Neighborhood looks at the left, middle, and right
     * not just one element of the list. this makes it easier to evolve
     * for each generation
     *
     * @return
     */
    @Override
    public Iterator<Neighborhood> iterator() {
        return new Iterator<>() {
            private int curIndex = 0;

            @Override
            public boolean hasNext() {
                return curIndex < generation.size();
            }

            @Override
            public Neighborhood next() {
                final Cell left = Generation.this.get(curIndex - 1);
                final Cell middle = Generation.this.get(curIndex);
                final Cell right = Generation.this.get(curIndex + 1);

                curIndex++;

                return new Neighborhood(left, middle, right);
            }
        };

    }

    /**
     * generates next generation
     * @return Generation
     */
    public Generation evolve() {
        final List<Cell> generation = new ArrayList<>();

        for (final Neighborhood neighborhood : this) {
            generation.add(Rules.generateNextGen(neighborhood));
        }

        return new Generation(generation);
    }


}


