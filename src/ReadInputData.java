import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadInputData {

    public ReadInputData() {

    }

    public String readFromFile(String filePath) {
        String text = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            text = sb.toString();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return text;
    }

    public String readFromCommandLine(String inputMessage) {
        StringBuilder text = new StringBuilder();
        System.out.print(inputMessage);
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        for (int i = 0; i < x; i++) {
            text.append(scanner.next()).append("\n");
        }
        scanner.close();
        return text.toString();
    }
}
