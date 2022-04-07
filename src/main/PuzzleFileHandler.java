package main;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

import static main.GameConstants.*;

public class PuzzleFileHandler {

    private final String filePath;
    private String fileContents;
    private int rows;
    private int columns;

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
            System.out.println(THANK_YOU);
        }
        return fileContents;
    }

    private String[] getFileLines() {
        return fileContents.split("[\r]?\n");
    }

    public int getRows() {
        rows = getFileLines().length;
        return rows;
    }

    public int getColumns() {
        columns = getFileLines()[0].length();
        return columns;
    }

    public String getFileContents() {
        return fileContents;
    }

    public void prettyPrintFile() {
        String[] lines = fileContents.split("[\r]?\n");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                System.out.print(lines[i].charAt(j) + " ");
            }
            System.out.println();
        }
    }

}
