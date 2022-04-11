/******************************************************************************
 * Name: Loganathan Bhavaneetharan
 * UOW ID: w1810599
 * IIT ID: 20201212
 * B.Eng.Software Engineering, 2nd Year
 *
 *  Description:
 *        The programs takes the filepath, moving direction, state of the ice
 *		  as inputs and will provide the shortest path from point 'S' to
 *		  point 'F' using breadth-first search algorithm
 *
 * Run the program by running SlidingPuzzle.main()
 *
 *****************************************************************************/

package main;

import java.io.FileReader;
import java.util.Scanner;

import static main.PuzzleConstants.*;

/**
 * <p>The {@code PuzzleFileHandler} reads text from the file path using the
 * {@linkplain Scanner} object.</p>
 * <p>
 * The file will be read and converted to a string.
 * If the file reading gets failed, the user will be prompted a message.
 *
 * @author Loganathan Bhavaneetharan
 */

public class PuzzleFileHandler {

    /**
     * File path value
     */
    private final String filePath;

    /**
     * All the contents of the file will be stored in this String
     */
    private String fileContents;

    /**
     * Constructs and initializes a PuzzleFileHandler.
     *
     * @param filePath the path of the file to read
     */
    public PuzzleFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the puzzle file and stores all the content as String
     * and finally returns the string.
     *
     * The exceptions are handled by using a try catch, user will be prompted a
     * message if an exception occurred
     *
     * @return the file contents of the given file
     */
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

}
