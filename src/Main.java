public class Main {

    public static void main(String[] args) {
        String data;
        ReadInputData readFromFile = new ReadInputData();
        data = readFromFile.readFromFile("src/test.txt");
        System.out.println(data);
        data = readFromFile.readFromCommandLine("Enter data: ");
        System.out.println(data);
    }
}
