import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SlidePuzzle {

    private static final int NOT_USED = -1; // not used position on the board
    private static final int EMPTY = 0; // empty position on the board
    private static final int MAX_MOVES = 500; // maximum number of moves
    private static final boolean DYNAMIC = true; // dynamic programming
    private static final boolean DEBUGGING = false; // print solutions

    /**
     * Directions that can be used when sliding a puzzle piece onto an empty
     * position. With the empty position as a reference, directions in which the
     * puzzle piece in question is.
     */
    private enum Direction {

        UP('U', -1, 0), RIGHT('R', 0, 1), BOTTOM('B', 1, 0), LEFT('L', 0, -1);

        char c;
        int row;
        int col;

        Direction(char c, int row, int col) {
            this.c = c;
            this.row = row;
            this.col = col;
        }
    }

    private int[] board; // the game board
    private Map<String, Integer> states; // map board states and number of moves
    private List<String> solutions; // found solutions

    /**
     * Returns the minimum number of moves needed to complete the puzzle for the
     * given start state.
     *
     * @param start the start state; containg of six unique integers between 1
     * and 6 in arbitrary order
     * @return if the puzzle is solved the minimum number of moves need to
     * complete the puzzle, otherwize -1
     */
    public static int numberOfMoves(int[] start) {
        return new SlidePuzzle().numberOfMovesNonStatic(start);
    }

    private int numberOfMovesNonStatic(int[] start) {
        if (start.length != 6) {
            throw new RuntimeException("Invalid start state, length = "
                    + start.length);
        }
        List<Integer> startList;
        try (Stream<Integer> stream = IntStream.of(start).boxed()) {
            startList = stream.collect(Collectors.toList());
        }
        for (int i = 1; i <= 6; i++) {
            if (!startList.contains(i)) {
                throw new RuntimeException("Invalid start state,"
                        + " does not contain " + i);
            }
        }

        this.board = new int[9];
        this.states = new TreeMap();
        this.solutions = new ArrayList();
        board[0] = NOT_USED;
        board[1] = EMPTY;
        board[2] = NOT_USED;
        for (int i = 0; i < start.length; i++) {
            board[i + 3] = start[i];
        }

        solve(new String());

        if (solutions.isEmpty()) {
            return -1;
        }
        int min = Integer.MAX_VALUE;
        for (String s : solutions) {
            if (min > s.length()) {
                min = s.length();
            }
        }
        return min;
    }

    /**
     * Solves the puzzle.
     */
    private void solve(String moves) {
        if (moves.length() > MAX_MOVES) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 3; i <= 8; i++) {
            sb.append(board[i]);
        }
        String state = sb.toString(); // get current board state
        if (DYNAMIC) {
            if (states.containsKey(state) && moves.length() >= states.get(state)) {
                return; // stop branch if equal or better solution already found
            }
            states.put(state, moves.length()); // map state and number of moves
        }
        if (state.equals("123456")) {
            if (DEBUGGING) {
                System.out.println(moves + " " + moves.length());
            }
            solutions.add(moves);
            return;
        }

        for (Direction dir : Direction.values()) {
            Direction invDir = Direction.values()[(dir.ordinal() + 2) % 4];
            if (invDir.c != last(moves)) { // do not make the opposite of last
                if (move(dir)) { // make move
                    solve(moves + (dir.c)); // recursively solve
                    move(invDir); // unmake move
                }
            }
        }
    }

    /**
     * Returns the last character of a String if its length is greater than 0,
     * otherwise the null character.
     */
    private char last(String str) {
        if (str.length() == 0) {
            return '\u0000';
        } else {
            return str.charAt(str.length() - 1);
        }
    }

    /**
     * Slides a puzzle piece onto an empty position. Returns true if succeeded,
     * otherwise false.
     */
    private boolean move(Direction dir) {
        int empty = -1;
        if (board[1] == EMPTY) {
            empty = 1;
        } else {
            for (int i = 3; i <= 8; i++) {
                if (board[i] == EMPTY) {
                    empty = i; // position of empty
                    break;
                }
            }
        }
        if (empty == -1) {
            throw new RuntimeException("No empty position");
        }

        int swapRow = row(empty) + dir.row; // coordinate for the piece to move
        int swapCol = col(empty) + dir.col;

        if (validPosition(swapRow, swapCol)) {
            int swap = position(swapRow, swapCol);
            board[empty] = board[swap];
            board[swap] = EMPTY;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns row index for a position.
     */
    private int row(int pos) {
        if (pos < 0 || pos > 8) {
            throw new RuntimeException("Position outside board (" + pos + ")");
        }
        return pos / 3;
    }

    /**
     * Returns column index for a position.
     */
    private int col(int pos) {
        if (pos < 0 || pos > 8) {
            throw new RuntimeException("Position outside board (" + pos + ")");
        }
        return pos % 3;
    }

    /**
     * Returns position index for a row and a column.
     */
    private int position(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            throw new RuntimeException("Position outside board {" + row + ", "
                    + col + "}");
        }
        return row * 3 + col;
    }

    /**
     * Returns true if position is valid, otherwise false.
     */
    private boolean validPosition(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return false;
        }
        int pos = position(row, col);
        return pos != 0 && pos != 2;
    }
}
