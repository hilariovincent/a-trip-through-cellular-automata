package ElementaryCA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rules {
    private static List<Character> rule;

    public Rules(List<Character> rule){
        Rules.rule = rule;

    }


    /**
     *
     * @param neighborhood
     * @return Cell coming off the rule binary
     */
    public static Cell generateNextGen(Neighborhood neighborhood) {

        if (neighborhood.equals('1','1','1')) {
            return Cell.fromChar(rule.get(0));
        }
        else if (neighborhood.equals('1','1','0')) {
            return Cell.fromChar(rule.get(1));
        }
        else if (neighborhood.equals('1','0','1')) {
            return Cell.fromChar(rule.get(2));
        }
        else if (neighborhood.equals('1','0','0')) {
            return Cell.fromChar(rule.get(3));
        }
        if (neighborhood.equals('0','1','1')) {
            return Cell.fromChar(rule.get(4));
        }
        else if (neighborhood.equals('0','1','0')) {
            return Cell.fromChar(rule.get(5));
        }
        else if (neighborhood.equals('0','0','1')) {
            return Cell.fromChar(rule.get(6));
        }
        else if (neighborhood.equals('0','0','0')) {
            return Cell.fromChar(rule.get(7));
        }
        return Cell.DEAD;
    }


}
