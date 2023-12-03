import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AOC2023Day3 {
    private static final String FILE_NAME = "./../../input.txt";
    private static final String GEAR = "*";
    public static void main(String[] args) {
        String [][] engine = getData();
        LinkedList<SpecialSymbol> specialSymbols = getSpecialSymbols(engine);
        LinkedList<EngineNumber> engineNumbers = getNumbers(engine);
        LinkedList<EngineNumber> partNumbers = getPartNumbers(engineNumbers, specialSymbols);
        int sumOfPartNumbers = sumEngineNumbers(partNumbers);
        System.out.println("Sum of Part Numbers: "+sumOfPartNumbers);

        LinkedList<SpecialSymbol> gears = getGears(specialSymbols);
        int sumOfAllGearRatios = sumOfGearRatios(gears);
        System.out.println("Sum of All Gear Ratios: "+sumOfAllGearRatios);
    }

    private static int sumOfGearRatios(LinkedList<SpecialSymbol> gears) {
        int sum = 0;
        for (SpecialSymbol gear: gears) {
            sum += gear.getGearRatio();
        }
        return sum;
    }

    private static LinkedList<SpecialSymbol> getGears(LinkedList<SpecialSymbol> specialSymbols) {
        LinkedList<SpecialSymbol> gears = new LinkedList<>();
        for (SpecialSymbol symbol: specialSymbols) {
            if(symbol.value.equals(GEAR) && symbol.adjacentNumbers.size()==2){
                gears.add(symbol);
            }
        }
        return gears;
    }

    private static int sumEngineNumbers(LinkedList<EngineNumber> partNumbers) {
        int sum=0;
        for (EngineNumber partNumber : partNumbers){
            sum+= partNumber.value;
        }
        return sum;
    }

    private static LinkedList<EngineNumber> getPartNumbers(LinkedList<EngineNumber> engineNumbers, LinkedList<SpecialSymbol> specialSymbols) {
        LinkedList<EngineNumber> partNumbers = new LinkedList<>();
        for (EngineNumber num: engineNumbers) {
            if(isPartNumber(num, specialSymbols)){
                partNumbers.add(num);
            }
        }

        return partNumbers;
    }

    private static boolean isPartNumber(EngineNumber num, LinkedList<SpecialSymbol> specialSymbols) {
        for (SpecialSymbol symbol : specialSymbols) {
            for (int i = num.startPositionX; i <= num.endPositionX; i++) {
                if(i==symbol.positionX-1 && num.positionY==symbol.positionY-1){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX-1 && num.positionY==symbol.positionY){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX-1 && num.positionY==symbol.positionY+1){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX && num.positionY==symbol.positionY-1){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX && num.positionY==symbol.positionY){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX && num.positionY==symbol.positionY+1){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX+1 && num.positionY==symbol.positionY-1){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX+1 && num.positionY==symbol.positionY){
                    symbol.adjacentNumbers.add(num);
                    return true;
                } else if(i==symbol.positionX+1 && num.positionY==symbol.positionY+1){
                    symbol.adjacentNumbers.add(num);
                    return true;
                }
            }
        }
        return false;
    }

    private static LinkedList<EngineNumber> getNumbers(String[][] engine) {
        LinkedList<EngineNumber> engineNumbers = new LinkedList<>();
        int startPositionX=0;
        int endPositionX = 0;
        int positionY = 0;
        boolean hasFirstDigit=false;
        for (int i = 0; i < engine.length; i++) {
            for (int j = 0; j < engine[i].length; j++) {
                if(engine[i][j].length()==1 && Character.isDigit(engine[i][j].charAt(0))){
                    if(!hasFirstDigit){
                        positionY=i;
                        startPositionX=j;
                        hasFirstDigit=true;
                    }
                } else if (hasFirstDigit) {
                    if(positionY==i){
                        endPositionX=j-1;
                    }else {
                        endPositionX = engine[i].length-1;
                    }
                    int value = getNumberFromPosition(engine, positionY,startPositionX,endPositionX);
                    EngineNumber engineNumber = new EngineNumber(value,positionY,startPositionX,endPositionX);
                    engineNumbers.add(engineNumber);
                    hasFirstDigit=false;
                }
            }
        }
        return engineNumbers;
    }

    private static int getNumberFromPosition(String[][] engine, int positionY, int startPositionX, int endPositionX) {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startPositionX; i <= endPositionX; i++) {
            valueBuilder.append(engine[positionY][i]);
        }
        int value = Integer.parseInt(valueBuilder.toString());
        return value;
    }

    private static LinkedList<SpecialSymbol> getSpecialSymbols(String[][] engine) {
        LinkedList<SpecialSymbol> specialSymbols = new LinkedList<>();
        for (int i = 0; i < engine.length; i++) {
            for (int j = 0; j < engine[i].length; j++) {
                if(!engine[i][j].equals(".") && !engine[i][j].matches("\\d+")){
                    SpecialSymbol specialSymbol = new SpecialSymbol(engine[i][j],i,j);
                    specialSymbols.add(specialSymbol);
                }
            }
        }
        return specialSymbols;
    }

    private static String[][] getData() {
        LinkedList<String> rawData = getLines();// getDefaultLines();
        return getEngineData(rawData);
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

    private static String[][] getEngineData(LinkedList<String> rawData) {
        int maxY = rawData.size();
        int maxX = rawData.element().length();
        String[][] engine = new String[maxY][maxX];
        int y=0;
        int x=0;
        for(String line : rawData){
            for (int i = 0; i < maxX; i++) {
                engine[y][x] = line.substring(i,i+1);
                x=updatePos(x,maxX);
            }
            y=updatePos(y,maxY);
        }
        return engine;
    }

    private static int updatePos(int position, int length) {
        if(position<length-1){
            position=position+1;
        }else{
            position=0;
        }
        return position;
    }

    private static LinkedList<String> getDefaultLines() {
        LinkedList<String> rawData = new LinkedList<>();
        rawData.add("467..114..");
        rawData.add("...*......");
        rawData.add("..35..633.");
        rawData.add("......#...");
        rawData.add("617*......");
        rawData.add(".....+.58.");
        rawData.add("..592.....");
        rawData.add("......755.");
        rawData.add("...$.*....");
        rawData.add(".664.598..");
        return rawData;
    }
}

class EngineNumber{
    int value;
    int positionY;
    int startPositionX;
    int endPositionX;

    public EngineNumber(int value, int positionY, int startPositionX, int endPositionX) {
        this.value = value;
        this.positionY = positionY;
        this.startPositionX = startPositionX;
        this.endPositionX = endPositionX;
    }

    @Override
    public String toString() {
        return "(" + positionY +"," + startPositionX + ")" + "(" + positionY +"," + endPositionX + "):" + value;
    }
}
class SpecialSymbol{
    int positionX;
    int positionY;
    String value;
    LinkedList<EngineNumber> adjacentNumbers;

    public SpecialSymbol(String value, int positionY, int positionX) {
        this.value = value;
        this.positionX = positionX;
        this.positionY = positionY;
        adjacentNumbers = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "(" + positionY + "," + positionX + "):" + value + "[Adjacent Numbers]:"+adjacentNumbers;
    }

    public int getGearRatio() {
        int value=1;
        for (EngineNumber num : adjacentNumbers) {
            value= num.value * value;
        }
        return value;
    }
}
