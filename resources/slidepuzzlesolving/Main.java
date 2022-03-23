public class Main {

    /**
     * Main method for Slide puzzle solving.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        int[] ints0 = new int[]{4, 2, 6, 3, 1, 5}; // 10
        int[] ints2 = new int[]{1, 2, 3, 4, 5, 6}; //  0
        int[] ints3 = new int[]{1, 2, 3, 4, 6, 5}; // -1
        int nom = SlidePuzzle.numberOfMoves(ints0);
        System.out.println("Minimum number of moves: " + nom);
    }
}
