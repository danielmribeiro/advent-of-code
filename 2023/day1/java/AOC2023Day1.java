import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AOC2023Day1 {
    private static final String FILE_NAME = "./../../input.txt";

    public static void main(String[] args) {
        LinkedList<String> data = getData();
        System.out.println("Sum of Calibration Value: " + calculateSumOfCalibrationValue(data));
    }

    private static LinkedList<String> getData(){
        LinkedList<String> rawData = new LinkedList<>();

        //Read File
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            //Check Element
            String element;
            while ((element = reader.readLine()) != null) {
                rawData.add(element);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rawData;
    }

    private static int calculateSumOfCalibrationValue(LinkedList<String> data){
        int sum=0;
        for (int i = 0; i < data.size(); i++) {
            sum += getCalibrationValue(data.get(i));
        }

        return sum;
    }

    private static int getCalibrationValue(String str) {
        int firstDigit=0, lastDigit=0;
        boolean hasFirstDigit = false;

        Map<String, Integer> validDigits = getMap();

        int currentIndex=0;
        for(char ch: str.toCharArray()){
            //Check Original Digits
            if(Character.isDigit(ch)){
                if(!hasFirstDigit){
                    firstDigit = Character.getNumericValue(ch);
                    hasFirstDigit=true;
                }
                lastDigit = Character.getNumericValue(ch);
            } else {
                //Check Valid Digits
                for(String key : validDigits.keySet()){
                    if(str.substring(currentIndex).length() >= key.length()){
                        if(str.substring(currentIndex,currentIndex+key.length()).contains(key)){
                            if(!hasFirstDigit){
                                firstDigit = validDigits.get(key);
                                hasFirstDigit=true;
                            }
                            lastDigit = validDigits.get(key);
                        }
                    }
                }
            }
            currentIndex++;
        }
        return (firstDigit*10)+lastDigit;
    }

    private static Map<String, Integer> getMap() {
        // Creating a HashMap
        Map<String, Integer> numbersMap = new HashMap<>();

        // Adding key-value pairs to the map
        numbersMap.put("one", 1);
        numbersMap.put("two", 2);
        numbersMap.put("three", 3);
        numbersMap.put("four", 4);
        numbersMap.put("five", 5);
        numbersMap.put("six", 6);
        numbersMap.put("seven", 7);
        numbersMap.put("eight", 8);
        numbersMap.put("nine", 9);

        return numbersMap;
    }





}