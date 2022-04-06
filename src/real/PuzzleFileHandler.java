package real;

import java.io.FileReader;
import java.util.Scanner;

public class PuzzleFileHandler {
    public PuzzleFileHandler() {
    }

    public String initializeFileContents(String filePath) {
        String fileContents = "";
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.hasNext()) {
                stringBuilder.append(reader.nextLine()).append("\n");
            }
            reader.close();
            fileContents = stringBuilder.toString();
            System.out.println("\nPUZZLE LOADED!\n");
            System.out.println(fileContents);
        } catch (Exception e) {
            System.out.println("\nFILE DOES NOT EXIST!\nTHANK YOU!");
        }
        return fileContents;
    }
}
