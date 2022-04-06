package real;

import java.io.FileReader;
import java.util.Scanner;

import static real.GameConstants.*;

public class PuzzleFileHandler {
    public PuzzleFileHandler() {
    }

    public String readPuzzleFile(String filePath) {
        String fileContents = "";
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.hasNext()) {
                stringBuilder.append(reader.nextLine()).append("\n");
            }
            reader.close();
            fileContents = stringBuilder.toString();
            System.out.println(PUZZLE_LOADED);
            System.out.println(fileContents);
        } catch (Exception e) {
            System.out.println(FILE_DOES_NOT_EXIST);
            System.out.println(THANK_YOU);
        }
        return fileContents;
    }


    public PuzzleMap initializePuzzleMap(String fileContents) {

        PuzzleMap puzzleMap = new PuzzleMap();

        if (fileContents == null || (fileContents = fileContents.trim()).equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return null;
        }

        String[] lines = fileContents.split("[\r]?\n");
        int rows = lines.length;
        int columns = lines[0].length();

        System.out.println(rows + " X " + columns);


        for (int i = 0; i < rows; i++) {
            if (lines[i].length() != columns) {
                System.out.println(INVALID_DATA);
                return null;
            } else {
                for (int j = 0; j < columns; j++) {
                    switch (lines[i].charAt(j)){
                        case ICE:
                            System.out.println("ICE");
                            break;
                        case ROCK:
                            System.out.println("ROCK");
                            break;
                        case START:
                            System.out.println("START");
                            System.out.println(i + " " + j);
                            break;
                        case FINISH:
                            System.out.println("FINISH");
                            System.out.println(i + " " + j);
                            break;
                    }
                }
            }
        }

        return puzzleMap;
    }

}
