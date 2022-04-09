package sub_main;

import java.io.FileReader;
import java.util.Scanner;

import static main.GameConstants.*;

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

    public String getFileContents() {
        return fileContents;
    }

    public void prettyPrintFile() {
        String[] lines = fileContents.split("[\r]?\n");
        for (String line : lines) {
            for (int j = 0; j < line.length(); j++) {
                System.out.print(line.charAt(j) + " ");
            }
            System.out.println();
        }
    }

}
