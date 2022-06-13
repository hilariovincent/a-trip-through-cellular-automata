package ElementaryCA;
/**
 * Main has all the GUI implementation
 * This file also contains functions that reads in the rule table
 * and passes it to the other classes
 *
 * @author Vincent Hilario
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        double screenSize = 700;
        GridPane grid = new GridPane();
        grid.setMinHeight(screenSize);
        grid.setMinWidth(screenSize);


        Pair<List<Cell>, List<Character>> inputValues = handleInput();

        List<Cell> initialState = inputValues.getKey();
        List<Character> ruleNum = inputValues.getValue();

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

        final Generation initGen = new Generation(initialState);
        final Rules rule = new Rules(ruleNum);

        final AnimationTimer timer = new AnimationTimer() {
            private int curGenIndex = 0;
            private Generation curGen = initGen;
            private final int genSize = curGen.size();
            private long prevUpdate = 0;
            @Override
            public void handle(final long now) {
                if (now - prevUpdate >= TimeUnit.SECONDS.toNanos(1)) {

                    int curCol = 0;
                    for (final Neighborhood neighborhood : curGen) {
                        final Rectangle rect = new Rectangle();
                        if (neighborhood.middle() == Cell.ALIVE) {
                            rect.setFill(Color.BLACK);
                        } else {
                            rect.setFill(Color.WHITE);
                        }
                        rect.widthProperty().bind(primaryStage.widthProperty().divide(genSize));
                        rect.heightProperty().bind(rect.widthProperty());
                        grid.add(rect, curCol, curGenIndex);

                        curCol++;
                    }
                    curGen = curGen.evolve();
                    curGenIndex++;
                    prevUpdate = now;
                }
            }
        };
        timer.start();
    }

    /*
    this function prompts the user to input if they want to use a file or generate the CA themselves
     */
    public Pair<List<Cell>, List<Character>> handleInput() throws FileNotFoundException {
        String ruleNum = null;
        String initialState = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to read in a file or generate [f/g]?");
        String response = scanner.nextLine();

        if (response.equals("f")) {

            System.out.println("Enter path to the file");
            String filePath = scanner.nextLine();
            File file = new File(filePath);


            //checks if file exists, if not, user gets prompted again
            while (!file.exists()) {
                System.out.println("File does not exist. Try again.");
                filePath = scanner.nextLine();
                file = new File(filePath);
            }

            Scanner inFs = new Scanner(file);

            ruleNum = inFs.nextLine();
            initialState = inFs.nextLine();


        } else if (response.equals("g")) {

            int ruleNumAsInt = 0;
            boolean checker = true;


                System.out.println("Enter rule number:");
                while (checker) {
                    ruleNumAsInt = scanner.nextInt();

                    if (ruleNumAsInt >= 0 && ruleNumAsInt <= 255) {
                        checker = false;
                    } else {
                        System.out.println("Rule number must be in this range [0, 255]");
                    }
                }
                checker = true;

                StringBuilder reverseBinary = new StringBuilder();

                for (int i = 0; i < 8; i++) {
                    reverseBinary.insert(0, ruleNumAsInt % 2);

                    ruleNumAsInt /= 2;
                }

                System.out.println("Enter initial state:");
                while (checker) {
                    initialState = scanner.next();

                    for (int i = 0; i < initialState.length(); i++) {
                        if (initialState.charAt(i) != '1' && initialState.charAt(i) != '0') {
                            System.out.println("Error: String must only consist of 1's and 0's");
                            break;
                        } else {
                            checker = false;
                        }
                    }
                }
                ruleNum = reverseBinary.toString();
        }


        return new Pair<>(parseToListOfCell(parseToListOfChar(initialState)), parseToListOfChar(ruleNum));
    }

    //Helper function to turn a string to a List<Character>
    public List<Character> parseToListOfChar(String str) {
        List<Character> list = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            list.add(str.charAt(i));
        }

        return list;
    }

    //helper function to turn List<Character> to List<Cell>
    public List<Cell> parseToListOfCell(List<Character> charList) {
        List<Cell> list = new ArrayList<>();

        for (int i = 0; i < charList.size(); i++) {
            list.add(Cell.fromChar(charList.get(i)));
        }

        return list;
    }
}
