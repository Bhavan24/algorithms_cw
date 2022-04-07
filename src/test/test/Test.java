package test.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String filePath = "src/test.txt";
        ArrayList<String[]> maps = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            int i = Integer.parseInt(line);
            for (int j = 0; j < i; j++) {
                line = br.readLine();
                String[] smallMap = line.split("");
                maps.add(j, smallMap);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        ArrayList<int[]> positions = new ArrayList<>();
        ArrayList<int[]> openList = new ArrayList<>();
        ArrayList<int[]> closedList = new ArrayList<>();
        ArrayList<int[]> visitedList = new ArrayList<>();

        int[] startingPos = new int[0];
        int[] finishingPos = new int[0];

        for (int j = 0; j < maps.size(); j++) {
            String[] newArr = maps.get(j);
            for (int i = 0; i < newArr.length; i++) {
                if (newArr[i].equals("S")) {
                    startingPos = new int[]{i, j};
                }
                if (newArr[i].equals("F")) {
                    finishingPos = new int[]{i, j};
                }
            }
        }

        if (startingPos.length != 0 && finishingPos.length != 0) {
            positions = getPositions(startingPos);

            for (int[] position : positions) {
                try {
                    int x = position[1];
                    int y = position[0];
                    if (maps.get(x)[y].equals(".")) {
                        openList.add(position);
                    }
                    if (maps.get(x)[y].equals("0")) {
                        closedList.add(position);
                    }
                } catch (Exception e) {
                    closedList.add(position);
                }
            }
        }


        for (int j = 0; j < maps.size(); j++) {
            positions = getPositions(openList.get(j));

            for (int[] position : positions) {
                try {
                    int x = position[1];
                    int y = position[0];
                    if (!closedList.contains(position) && !openList.contains(position)) {
                        if (maps.get(x)[y].equals(".")) {
                             openList.add(position);
                        }
                        if (maps.get(x)[y].equals("0")) {
                            closedList.add(position);
                        }
                    }
                } catch (Exception e) {
                    closedList.add(position);
                }
            }

            System.out.println(Arrays.deepToString(openList.toArray()));
            System.out.println("-----------");
        }
    }

    private static ArrayList<int[]> getPositions(int[] array) {
        ArrayList<int[]> positions = new ArrayList<>();
        try {
            positions.add(new int[]{array[0], array[1] - 1});
            positions.add(new int[]{array[0] + 1, array[1]});
            positions.add(new int[]{array[0], array[1] + 1});
            positions.add(new int[]{array[0] - 1, array[1]});
        } catch (Exception e) {
            positions = null;
        }
        return positions;
    }

}
