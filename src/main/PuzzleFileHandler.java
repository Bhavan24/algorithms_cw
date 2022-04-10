package main;

import java.io.FileReader;
import java.util.Scanner;

import static main.PuzzleConstants.*;

public class PuzzleFileHandler {

    private final String filePath;
    private String fileContents;

    public PuzzleFileHandler(String filePath) {
        this.filePath = filePath;
    }

    public String readPuzzleFile() {
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.hasNext()) {
                stringBuilder.append(reader.nextLine()).append("\n");
            }
            reader.close();
            fileContents = stringBuilder.toString();
            System.out.println(PUZZLE_LOADED);
        } catch (Exception e) {
            System.out.println(FILE_DOES_NOT_EXIST);
            System.out.println(TRY_AGAIN);
        }
        return fileContents;
    }

}
