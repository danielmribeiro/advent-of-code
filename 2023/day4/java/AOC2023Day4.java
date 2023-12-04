import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AOC2023Day4 {
    private static final String FILE_NAME = "./../../input.txt";
    public static void main(String[] args) {
        ArrayList<Scratchcard> scratchcards = getData();
        int sumOfScratchcardsPoints = getSumOfScratchcardsPoints(scratchcards);
        System.out.println("Sum of Scratchcard points: "+sumOfScratchcardsPoints);

        getScratchcardsCopies(scratchcards);
        int sumOfScratchcardsCopies = sumScratchcardsCopies(scratchcards);
        System.out.println("Sum of Scratchcard copies: "+sumOfScratchcardsCopies);
    }

    private static int sumScratchcardsCopies(ArrayList<Scratchcard> scratchcards) {
        int sum=0;
        for (Scratchcard scratchcard: scratchcards) {
            sum += scratchcard.copies;
        }
        return sum;
    }

    private static ArrayList<Scratchcard> getScratchcardsCopies(ArrayList<Scratchcard> scratchcards) {
        for(Scratchcard scratchcard : scratchcards){
            addScratchcardCopies(scratchcard, scratchcards);
        }
        return scratchcards;
    }

    private static void addScratchcardCopies(Scratchcard scratchcard, ArrayList<Scratchcard> scratchcards) {
        for (int j = 0; j < scratchcard.copies; j++) {
            if(scratchcard.matchingNumbers>0){
                for (int i = scratchcard.gameID; i < scratchcard.gameID + scratchcard.matchingNumbers; i++) {
                    scratchcards.get(i).addCopies(1);
                }
            }
        }
    }

    private static int getSumOfScratchcardsPoints(ArrayList<Scratchcard> scratchcards) {
        int sum = 0;
        for (Scratchcard scratchcard : scratchcards) {
            sum += getScratchcardPoints(scratchcard);
        }
        return sum;
    }

    private static int getScratchcardPoints(Scratchcard scratchcard){
        scratchcard.setPoints();
        return scratchcard.getPoints();
    }

    private static ArrayList<Scratchcard> getData() {
        LinkedList<String> rawData = getLines();// getDefaultLines();
        return getScratchcardsData(rawData);
    }

    private static LinkedList<String> getDefaultLines() {
        LinkedList<String> rawData = new LinkedList<>();
        rawData.add("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53");
        rawData.add("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19");
        rawData.add("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1");
        rawData.add("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83");
        rawData.add("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36");
        rawData.add("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11");
        return rawData;
    }

    private static ArrayList<Scratchcard> getScratchcardsData(LinkedList<String> rawData) {
        LinkedList<Scratchcard> scratchcards = new LinkedList<>();
        for (String line : rawData) {
            String[] parts = line.split(":");
            if (parts.length >= 2) {

                // Extract the index of the game
                String gameIndex = parts[0].trim().replaceAll("\\s+", "").replace("Card", "");
                Scratchcard scratchcard = new Scratchcard(Integer.parseInt(gameIndex));

                // Extract the numbers
                String[] groupOfNumbers = parts[1].split("\\|");
                String[] winningNumbers = groupOfNumbers[0].replaceAll("\\s+",":").split(":");
                String[] numbersYouHave = groupOfNumbers[1].replaceAll("\\s+",":").split(":");
                for (int i = 1; i < winningNumbers.length; i++) {
                    scratchcard.winningNumbers.add(Integer.parseInt(winningNumbers[i]));
                }
                for (int i = 1; i < numbersYouHave.length; i++) {
                    scratchcard.numbersYouHave.add(Integer.parseInt(numbersYouHave[i]));
                }
                scratchcards.add(scratchcard);
            }
        }
        ArrayList<Scratchcard> scratchcardsArray = new ArrayList<>(scratchcards);
        return scratchcardsArray;
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

class Scratchcard{
    int gameID;
    LinkedList<Integer> winningNumbers;
    LinkedList<Integer> numbersYouHave;
    int points;
    int matchingNumbers;
    int copies=0;

    public Scratchcard(int gameID) {
        this.gameID = gameID;
        this.winningNumbers = new LinkedList<>();
        this.numbersYouHave = new LinkedList<>();
        this.points=0;
        this.matchingNumbers=0;
        addCopies(1);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints() {
        int equalNumbers=0;
        for (int winningNumber : winningNumbers) {
            for (int numberYouHave: numbersYouHave) {
                if(winningNumber==numberYouHave){
                    equalNumbers++;
                }
            }
        }
        if(equalNumbers==0){
            this.points = 0;
        } else{
            this.points = 1;
            for (int i = 2; i <= equalNumbers; i++) {
                this.points = this.points * 2;
            }
        }
        this.matchingNumbers=equalNumbers;
    }

    public int getGameID() {
        return gameID;
    }

    @Override
    public String toString() {
        return "Scratchcard{" +
                "gameID=" + gameID +
                ", winningNumbers=" + winningNumbers +
                ", numbersYouHave=" + numbersYouHave +
                ", points=" + points +
                ", matchingNumbers=" + matchingNumbers +
                ", copies=" + copies +
                '}';
    }

    public int getMatchingNumbers() {
        return matchingNumbers;
    }

    public void addCopies(int i) {
        this.copies=copies+i;
    }
}
