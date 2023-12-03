import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AOC2023Day2 {
    private static final String FILE_NAME = "./../../input.txt";

    public static void main(String[] args) {
        Bag bag = new Bag();
        bag.fillWithCubesFromZero(12,13,14);
        LinkedList<Game> games = getData();
        LinkedList<Game> possibleGames = getPossibleGames(bag,games);
        int sumOfThePossibleGamesIndexes = getSumOfGamesIndexes(possibleGames);
        int sumOfThePowerOfTheSets = getSumOfThePowerOfTheSets(games);

        System.out.println("Sum of the Possible Games Indexes: " + sumOfThePossibleGamesIndexes);
        System.out.println("Sum of the Power of the Sets: " + sumOfThePowerOfTheSets);
    }

    private static int getSumOfThePowerOfTheSets(LinkedList<Game> games) {
        int sum = 0;
        for (Game game: games) {
            int gamePower = game.calculateThePower();
            sum += gamePower;
        }
        return sum;
    }

    private static int getSumOfGamesIndexes(LinkedList<Game> games) {
        int sum = 0;
        for (Game game: games) {
            sum += game.getIndex();
        }
        return sum;
    }

    private static LinkedList<Game> getPossibleGames(Bag bag, LinkedList<Game> games) {
        LinkedList<Game> possibleGames = new LinkedList<>();
        for (Game game : games) {
            if((game.bag.getNumberOfCubes(Color.RED) <= bag.getNumberOfCubes(Color.RED)) &&
               (game.bag.getNumberOfCubes(Color.GREEN) <= bag.getNumberOfCubes(Color.GREEN)) &&
               (game.bag.getNumberOfCubes(Color.BLUE) <= bag.getNumberOfCubes(Color.BLUE)))
            {
                possibleGames.add(game);
            }
        }
        return possibleGames;
    }

    private static LinkedList<Game> getData() {
        LinkedList<String> rawData = getLines(); //getDefaultLines(); for the example
        return getGamesData(rawData);
    }

    private static LinkedList<Game> getGamesData(LinkedList<String> rawData) {
        LinkedList<Game> games = new LinkedList<>();
        for (String line : rawData) {
            String[] parts = line.split(":");
            if (parts.length >= 2) {

                // Extract the index of the game
                String gameIndex = parts[0].trim().replace("Game ", "");
                Game game = new Game(Integer.parseInt(gameIndex));

                //Extract the grabs
                String[] attempts = parts[1].split(";");
                for (String attempt : attempts) {
                    // Extract the number-color pair
                    String[] colorNumberPairs = attempt.split(",");
                    int red=0;
                    int green=0;
                    int blue=0;
                    for (String pair : colorNumberPairs) {
                        // Extract the number and the color
                        String[] colorNumber = pair.trim().split(" ");
                        if (colorNumber.length == 2) {
                            if(colorNumber[1].equals(Color.RED)){
                                red = Integer.parseInt(colorNumber[0]);
                            }else if(colorNumber[1].equals(Color.GREEN)){
                                green = Integer.parseInt(colorNumber[0]);
                            }else if(colorNumber[1].equals(Color.BLUE)){
                                blue = Integer.parseInt(colorNumber[0]);
                            }
                        }
                    }
                    game.grab(red,green,blue);
                }
                games.add(game);
            }
        }
        return games;
    }

    private static LinkedList<String> getDefaultLines() {
        LinkedList<String> rawData = new LinkedList<>();
        rawData.add("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
        rawData.add("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue");
        rawData.add("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red");
        rawData.add("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red");
        rawData.add("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");
        return rawData;
    }

    private static LinkedList<String> getLines() {
        LinkedList<String> rawData = new LinkedList<>();
        //Read File
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String element;
            while ((element = reader.readLine()) != null) {
                rawData.add(element);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rawData;
    }


}

class Bag{
    private LinkedList<Cube> cubesInsideTheBag;

    public Bag() {
        cubesInsideTheBag = new LinkedList<>();
    }

    public void fillWithCubesFromZero(int red, int green, int blue) {
        cubesInsideTheBag = new LinkedList<>();
        for (int i = 0; i < red; i++) {
            Cube redCube = new Cube(Color.RED);
            cubesInsideTheBag.add(redCube);
        }
        for (int i = 0; i < green; i++) {
            Cube greenCube = new Cube(Color.GREEN);
            cubesInsideTheBag.add(greenCube);
        }
        for (int i = 0; i < blue; i++) {
            Cube blueCube = new Cube(Color.BLUE);
            cubesInsideTheBag.add(blueCube);
        }
    }

    public int getNumberOfCubes(String color) {
        int cubes=0;
        for (Cube cube : cubesInsideTheBag) {
            if(cube.getColor().equals(color)){
                cubes++;
            }
        }
        return cubes;
    }
    public int getNumberOfCubes() {
        return cubesInsideTheBag.size();
    }

    public void addCubesInAGrabAndSaveAgainScenario(int redCubes, int greenCubes, int blueCubes) {
        int currentNumberOfRedCubes=getNumberOfCubes(Color.RED);
        int currentNumberOfGreenCubes=getNumberOfCubes(Color.GREEN);
        int currentNumberOfBlueCubes=getNumberOfCubes(Color.BLUE);

        if(redCubes>currentNumberOfRedCubes){
            for (int i = 0; i < redCubes-currentNumberOfRedCubes; i++) {
                Cube cube = new Cube(Color.RED);
                cubesInsideTheBag.add(cube);
            }
        }
        if(greenCubes>currentNumberOfGreenCubes){
            for (int i = 0; i < greenCubes-currentNumberOfGreenCubes; i++) {
                Cube cube = new Cube(Color.GREEN);
                cubesInsideTheBag.add(cube);
            }
        }
        if(blueCubes>currentNumberOfBlueCubes){
            for (int i = 0; i < blueCubes-currentNumberOfBlueCubes; i++) {
                Cube cube = new Cube(Color.BLUE);
                cubesInsideTheBag.add(cube);
            }
        }
    }
}

class Cube{
    private String color;

    public Cube(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}

class Game{
    private int index;
    Bag bag;
    ArrayList<Grab> grabList;
    public Game (int index){
        this.index = index;
        this.bag = new Bag();
        this.grabList = new ArrayList<>();
    }

    public void grab(int redCubes, int greenCubes, int blueCubes){
        Grab grab = new Grab(redCubes, greenCubes, blueCubes);
        grabList.add(grab);
        this.bag.addCubesInAGrabAndSaveAgainScenario(redCubes,greenCubes,blueCubes);
    }

    public int getIndex(){
        return index;
    }

    public int calculateThePower() {
        return bag.getNumberOfCubes(Color.RED) * bag.getNumberOfCubes(Color.GREEN) * bag.getNumberOfCubes(Color.BLUE);
    }
}

class Grab{
    private int redCubes=0;
    private int greenCubes=0;
    private int blueCubes=0;
    public Grab(int redCubes, int greenCubes, int blueCubes){
        this.redCubes=redCubes;
        this.greenCubes=greenCubes;
        this.blueCubes=blueCubes;
    }
    public int getRedCubes(){
        return redCubes;
    }
    public int getGreenCubes(){
        return greenCubes;
    }
    public int getBlueCubes(){
        return blueCubes;
    }
}

class Color {
    public static final String RED = "red";
    public static final String GREEN = "green";
    public static final String BLUE = "blue";
}