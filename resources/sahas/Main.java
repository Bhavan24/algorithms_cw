public class Main {

    public static void main(String[] args) {
        Puzzle p = new Puzzle();
        p.setFileLocation("src/test.txt");
        p.initializePuzzleArray();
        p.fillPuzzleArray();
        UndirectedGraph g = new UndirectedGraph();
        p.createGraph(g);
        p.printPathDetails(g);
    }

}
