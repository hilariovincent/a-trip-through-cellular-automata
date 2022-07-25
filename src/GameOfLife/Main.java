package GameOfLife;
/**
 * Main file handles the GUI and all the User Interaction on the command line
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();

        gridPane.setMinHeight(700);
        gridPane.setMinWidth(900);

        Grid initGrid = handleInput();

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        final AnimationTimer timer = new AnimationTimer() {

            private Grid currGrid = initGrid;
            private final Pair<Integer, Integer> gridDimensions = currGrid.getDimensions();
            private final int rowLen = gridDimensions.getKey();
            private final int colLen = gridDimensions.getValue();
            //uncomment line below to adjust speed
            //private long prevUpdate = 0;


            @Override
            public void handle(long now) {
                //uncomment line below to adjust speed (change number)
                //if (now - prevUpdate >= TimeUnit.SECONDS.toNanos(1)) {
                    gridPane.getChildren().clear();

                    for (Neighborhood neighborhood : currGrid) {
                        int midRow = neighborhood.midRow();
                        int midCol = neighborhood.midCol();
                        Cell middle = neighborhood.middle();
                        final Rectangle rect = new Rectangle();
                        ThreadLocalRandom rand = ThreadLocalRandom.current();
                        Color color = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);

                        //colors in the rectangle based on state
                        if (middle == Cell.DEAD) {
                            rect.setFill(Color.LIGHTGRAY);
                        }
                        else {
                            rect.setFill(Color.BLACK);
                        }

                        //binds rect size to grid size
                        rect.widthProperty().bind(primaryStage.widthProperty().divide(rowLen));
                        rect.heightProperty().bind(rect.widthProperty());

                        gridPane.add(rect, midCol, midRow);

                    }

                    currGrid = currGrid.evolve();

                    //uncomment line below to adjust speed
                    //prevUpdate = now;
                }
            //}
        };
        timer.start();

    }

    //prompts user to input if they want to use a file or manually input
    public Grid handleInput() throws FileNotFoundException {
        List<List<GameOfLife.Cell>> initialGrid = new ArrayList<>();
        List<String> stringRow = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Conway's Game of Life!");
        System.out.println("File or Manual Entry [f/m]?");

        String response = scanner.nextLine();

        if (response.equals("f")) {
            System.out.println("Enter path to the file");
            String filePath = scanner.nextLine();
            File file = new File(filePath);

            //checks if file exists, if not, user gets prompted again
            while (!file.exists()) {
                System.out.println("Invalid file path. Try again.");
                filePath = scanner.nextLine();
                file = new File(filePath);
            }

            Scanner inFs = new Scanner(file);

            int numRow = inFs.nextInt();
            int numCol = inFs.nextInt();

            for (int i = 0; i < numRow; i++) {
                String fullStr = inFs.next();

                stringRow.add(fullStr);
            }
            for (String fullStr : stringRow) {
                List<Character> row = parseToListOfChar(fullStr);
                List<Cell> rowCell = parseToListOfCell(row);

                initialGrid.add(rowCell);
            }


        } else if (response.equals("m")) {
            System.out.println("Enter number of rows and columns separated by a space:");
            int numRow = scanner.nextInt();
            int numCol = scanner.nextInt();


            System.out.println("Enter initial grid line by line:");
            for (int i = 0; i < numRow; i++) {
                boolean checker = true;

                while (checker) {
                    String fullStr = scanner.next();

                    if (fullStr.length() == numCol) {
                        stringRow.add(fullStr);
                        checker = false;
                    }
                    else {
                        System.out.println("Invalid input: must be with length " + numCol);
                    }
                }

            }
            for (String fullStr : stringRow) {
                List<Character> row = parseToListOfChar(fullStr);
                List<Cell> rowCell = parseToListOfCell(row);

                initialGrid.add(rowCell);
            }


        }

        return new Grid(initialGrid);
    }

    public List<Character> parseToListOfChar(String str) {
        List<Character> list = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            list.add(str.charAt(i));
        }

        return list;
    }

    public List<GameOfLife.Cell> parseToListOfCell(List<Character> charList) {
        List<GameOfLife.Cell> list = new ArrayList<>();

        for (Character character : charList) {
            list.add(GameOfLife.Cell.fromChar(character));
        }

        return list;
    }}
