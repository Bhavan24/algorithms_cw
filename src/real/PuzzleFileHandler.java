package real;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

import static real.GameConstants.*;

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
            System.out.println(fileContents);
        } catch (Exception e) {
            System.out.println(FILE_DOES_NOT_EXIST);
            System.out.println(THANK_YOU);
        }
        return fileContents;
    }


    public PuzzleMap initializePuzzleMap() {

        if (fileContents == null || (fileContents = fileContents.trim()).equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return null;
        }

        String[] lines = getFileLines();
        rows = lines.length;
        columns = lines[0].length();

        int[][] puzzleMap = new int[rows][columns];

        System.out.println(rows + " X " + columns);

        for (int i = 0; i < rows; i++) {
            if (lines[i].length() != columns) {
                System.out.println(INVALID_DATA);
                return null;
            } else {
                for (int j = 0; j < columns; j++) {
                    switch (lines[i].charAt(j)) {
                        case ICE:
                            puzzleMap[i][j] = ICE_VALUE;
                            break;
                        case ROCK:
                            puzzleMap[i][j] = ROCK_VALUE;
                            break;
                        case START:
                            puzzleMap[i][j] = START_VALUE;
                            break;
                        case FINISH:
                            puzzleMap[i][j] = FINISH_VALUE;
                            break;
                    }
                }
            }
        }
        System.out.println(Arrays.deepToString(puzzleMap));
        return new PuzzleMap();
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
}
