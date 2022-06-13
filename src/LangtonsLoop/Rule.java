package LangtonsLoop;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class Rule{
    private static Map<Cell, Map<String, Cell>> rule;

    public Rule(Map<Cell, Map<String, Cell>> rule) {
        Rule.rule = rule;

    }

    public static Map<Cell, Map<String, Cell>> getRule() {
        return rule;
    }

}
