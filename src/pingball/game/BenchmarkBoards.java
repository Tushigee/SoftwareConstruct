package pingball.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pingball.game.util.BoardFormatException;
import pingball.game.util.BoardNotFoundException;
import pingball.parser.BoardParser;

/**
 * A collection of the default boards used for evaluation.
 * 
 * This class contains the static method, generateBoard,
 * which generates a Board based on the Board's name
 * 
 * THIS CLASS IS IMMUTABLE
 */
public class BenchmarkBoards {
    
    /**
     * The known benchmark boards 
     */
    public enum KnownBoard {
        /**
         * The default board
         */
        DEFAULT("default"),
        /**
         * The board with absorbers
         */
        ABSORBER("absorber"),
        /**
         * The board with flippers
         */
        FLIPPERS("flippers"),
        /**
         * Empty initialized board
         */
        EMPTY("empty");
        /**
         * The sanitized name of the board
         *   (as in the file name)
         */
        private final String sanitizedName;
        
        /**
         * Creates a new known board
         * @param sanitizedName the file
         *   name (minus the path and .pb)
         */
        KnownBoard(String sanitizedName) {
            this.sanitizedName = sanitizedName;
        }
        
        /**
         * @return the name of the board (as in
         *   the file name)
         */
        public String getName() {
            return this.sanitizedName;
        }
    }
    
    
    /**
     * Generates the board associated with the given name.
     * If an unknown name is given, a BoardNotFoundException is
     *   thrown
     * @param board the board to generate
     * @return the generated board
     * @throws BoardNotFoundException if a board with the given
     *   name was not found
     */
    public static Board generateBoard(KnownBoard board) throws BoardNotFoundException {
        File file = new File("src/pingball/boards/" + board.getName() + ".pb");
        return BoardParser.createBoard(file);

    }

}
