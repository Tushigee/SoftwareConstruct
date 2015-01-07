package pingball.game.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import pingball.game.BenchmarkBoards;
import pingball.game.Board;
import pingball.game.util.BoardNotFoundException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test the printout of the standard boards
 *
 */
public class PrintTest {
    
    /**
     * Testing strategy:
     * 
     * Ensure that the three benchmark boards are printed correctly:
     *   - default
     *   - absorber
     *   - flippers
     *   
     * This test suite ensures that the textual representation is
     *   never unintentionally altered
     */
    
    /**
     * Helper method for converting a board to a string
     * @param board
     * @return
     */
    private static String boardToString(Board board) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        board.renderText(ps);
        return baos.toString();
    }
    
    /**
     * Tests rendering of default board
     */
    @Test
    public void testPrintDefaultBoard(){
        String defaultBoardModel = "......................\n" +
        ".                    .\n" +
        ". *                  .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ". O                  .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".                    .\n" +
        ".            /       .\n" +
        ".                    .\n" +
        ".###                 .\n" +
        ".       OOO          .\n" +
        ".                    .\n" +
        "......................";

        Board defaultBoard = null;
        try {
            defaultBoard = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.DEFAULT);
        } catch (BoardNotFoundException e) {
            e.printStackTrace();
            fail();
            return;
        }
        assertEquals(defaultBoardModel, boardToString(defaultBoard));
    }
    
    /**
     * Tests rendering of absorber board
     */
    @Test
    public void testPrintAbsorberBoard(){
        String absorberBoardModel = "......................\n" +
                ".                   \\.\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".                   *.\n" +
                ".                    .\n" +
                ". *                  .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ". OOOOO              .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".          *         .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".====================.\n" +
                ".====================.\n" +
                "......................";
        Board absorberBoard = null;
        try {
            absorberBoard = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.ABSORBER);
        } catch (BoardNotFoundException e) {
            e.printStackTrace();
            fail();
            return;
        }
        assertEquals(absorberBoardModel, boardToString(absorberBoard));
    }
    
    /**
     * Tests rendering of flippers board
     */
    @Test
    public void testPrintFlippersBoard(){
        String flipperBoardModel = "......................\n" +
                ".                   \\.\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".*    *    *    *   *.\n" +
                ".                    .\n" +
                ".O    O    O    O    .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".--       --    --   .\n" +
                ".                    .\n" +
                ".    --              .\n" +
                ".                    .\n" +
                ".                    .\n" +
                ".       O            .\n" +
                ".                    .\n" +
                ".   |              | .\n" +
                ".   |              | .\n" +
                ".                    .\n" +
                ".     O    /         .\n" +
                ".====================.\n" +
                "......................";
        Board flipperBoard = null;
        try {
            flipperBoard = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.FLIPPERS);
        } catch (BoardNotFoundException e) {
            e.printStackTrace();
            fail();
            return;
        }
        assertEquals(flipperBoardModel, boardToString(flipperBoard));

    }
    
}