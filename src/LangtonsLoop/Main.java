package LangtonsLoop;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();


        root.setMinWidth(1000);
        root.setMinHeight(700);

        Grid initGrid = handleInput();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        final AnimationTimer timer = new AnimationTimer() {
            private Grid currGrid = initGrid;
            private final int rowAndColLength = currGrid.size();
            private long prevUpdate = 0;



            @Override
            public void handle(long now) {
                    root.getChildren().clear();
                    for (Neighborhood neighborhood : currGrid) {
                        int midRow = neighborhood.row();
                        int midCol = neighborhood.col();
                        Cell middle = neighborhood.middle();
                        final Rectangle rectangle = new Rectangle();

                        rectangle.setFill(Cell.toColor(middle));


                        rectangle.widthProperty().bind(primaryStage.widthProperty().divide(rowAndColLength));
                        rectangle.heightProperty().bind(rectangle.widthProperty());

                        root.add(rectangle, midCol, midRow);

                    }

                    currGrid = currGrid.evolve();

                    prevUpdate = now;
                }
        };

        timer.start();

    }

    /*
    This function handles the init_config.txt file and passes it into the Grid class
     */
    public Grid handleInput() throws FileNotFoundException {
        List<List<Cell>> initGrid = new ArrayList<>();
        List<String> stringRow = new ArrayList<>();

        File initConfig = new File("resources/langtonsLoop/init_config.txt");

        //See explanation below
        parseRuleFile();

        Scanner inFs = new Scanner(initConfig);


        int numRow = inFs.nextInt();
        int numCol = inFs.nextInt();

        for (int i = 0; i < numRow; i++) {
            String fullStr = inFs.next();

            stringRow.add(fullStr);
        }

        for (String fullStr : stringRow) {
            List<Character> characterList = parseToListOfChar(fullStr);
            List<Cell> cellList = parseToListOfCell(characterList);

            initGrid.add(cellList);
        }

        return new Grid(initGrid);
    }

    /*
    Helper function to parse a String to a List of char
     */
    public List<Character> parseToListOfChar(String str) {
        List<Character> list = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            list.add(str.charAt(i));
        }

        return list;
    }


    /*
    Helper function to parse list of char into list of Cell
     */
    public List<Cell> parseToListOfCell(List<Character> characterList) {
        List<Cell> list = new ArrayList<>();

        for (char i: characterList) {

            list.add(Cell.fromChar(i));
        }

        return list;
    }


    /*
    this function reads in rule_table.txt file into a Map<Cell, Map<String, Cell>> data structure
     */
    public void parseRuleFile() throws FileNotFoundException {
        Map<Cell, Map<String, Cell>> ruleMap = new HashMap<>();
        Map<String, Cell> key = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file path to Langton's Loop rule table");
        String stringFile = scanner.nextLine();
        File ruleFile = new File(stringFile);

        Scanner inFs = new Scanner(ruleFile);

        //use this to check if we move to a new portion on the rule file
        Cell prevState = Cell.BLACK;

        while (inFs.hasNext()) {

            String fullStr = inFs.next();

            Cell presentState = Cell.fromChar(fullStr.charAt(0));
            Cell nextState = Cell.fromChar(fullStr.charAt(fullStr.length() - 1));
            String neighborhood = fullStr.substring(1, 5);

            List<String> allNeighborhoods = allNeighborhoods(neighborhood);

            //all possible neighborhood rotations
            String str1 = allNeighborhoods.get(0);
            String str2 = allNeighborhoods.get(1);
            String str3 = allNeighborhoods.get(2);
            String str4 = allNeighborhoods.get(3);

            //creates a new inner map when current state value changes in the rule file
            if (!prevState.equals(presentState)) {
                key = new HashMap<>();
            }

            //feeds in all possible rotation into the inner map
            key.put(str1, nextState);
            key.put(str2, nextState);
            key.put(str3, nextState);
            key.put(str4, nextState);

            ruleMap.put(presentState, key);



            prevState = presentState;
        }

        new Rule(ruleMap);

    }

    /*
    this function produces all possible rotational neighborhoods for the given neighborhood string
     */
    public static List<String> allNeighborhoods(String str) {
        List<String> allNeighborhoods = new ArrayList<>();
        allNeighborhoods.add(str);
        for (int i = 1; i < str.length(); i++) {
            String front = str.substring(0, 1);
            String back = str.substring(1);
            str = back + front;
            allNeighborhoods.add(str);
        }
        return allNeighborhoods;
    }


}
