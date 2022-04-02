package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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


    public String[][] readFromFile(String filePath, int length) {
        String[][] graph = new String[length][length];
        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            int i = 0;
            while (scanner.hasNext()) {
                graph[i] = scanner.nextLine().split("");
                i++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return graph;
    }


    public void readAllStrings(String filePath) {
        ArrayList<ArrayList<String>> maps = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] smallMap = line.split("");
                maps.add(new ArrayList<>(Arrays.asList(smallMap)));
            }
            System.out.println(maps);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String basePath = "src/inputs/";
        File[] files = new File(basePath).listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                int length = Integer.parseInt(fileName.split("_")[1].replace(".txt", ""));
                String[][] result = new ReadInputData().readFromFile(basePath + fileName, length);

            }
        }
    }

}
