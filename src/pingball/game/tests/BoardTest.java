package pingball.game.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.junit.Test;

import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.util.PhysicsConfiguration;

public class BoardTest {
    
    // Testing strategy:
    //   checkRep
    //   Advance:
    //     - Empty board
    //   Render Text:
    //     - Empty board

    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);
    final Board realBoard = new Board(20, 20, physicsConfig);
    final Vect nullVelocity = null;
    final Vect realPosition = new Vect(0,17);
    final Vect realVelocity = new Vect(0.5,0.5);
    final PhysicsConfiguration nullConfig = null;

    @Test
    public void checkRepAllValidInputs(){
        Board board = new Board(20, 20, physicsConfig);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void checkRepBadLength(){
        Board board = new Board(0, 20, physicsConfig);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void checkRepBadHeight(){
        Board board = new Board(20, 40, physicsConfig);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void checkRepNullPhysicsConfiguration(){
        Board board = new Board(20, 20, nullConfig);
    }
    
    /**
     * Test advancing the physics engine of
     * an empty board (testing for errors in
     *  the base case)
     */
    @Test
    public void testAdvanceEmptyBoard() {
        Board board = new Board(20, 20, spacePhysics);
        board.advance(0.1);
    }
    
    /**
     * Test advancing the physics engine of
     * a board with a single ball 
     */
    @Test
    public void testAdvanceBoardOneBall() {
        Board board = new Board(20, 20, spacePhysics);
        Ball ball = new Ball(new Vect(5, 5), new Vect(1, 0));
        board.addBall(ball);
        board.advance(1.0);
        Vect newPos = ball.getCircle().getCenter();
        assertTrue(Math.abs(newPos.x() - 6) <= 0.001);
        assertTrue(Math.abs(newPos.y() - 5) <= 0.001);
    }
    
    
    private static String getStringFromBAOS(ByteArrayOutputStream baos) {
        try {
            return baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    
    /**
     * Test rendering the empty board
     */
    @Test
    public void testRenderEmptyBoard() {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputBuffer);
        Board board = new Board(20, 20, spacePhysics);
        board.renderText(out);
        String[] lines = getStringFromBAOS(outputBuffer).split(System.lineSeparator());
        final Pattern topBottomWall = Pattern.compile("^\\.{22}$");
        final Pattern middle = Pattern.compile("^\\. {20}\\.$");
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.length() == 0) continue;
            assertTrue(topBottomWall.matcher(trimmedLine).matches()
                    || middle.matcher(trimmedLine).matches());
        }
        
    }
    
    /**
     * Test rendering the empty board
     */
    @Test
    public void testRenderBoardOneBall() {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputBuffer);
        Board board = new Board(20, 20, spacePhysics);
        Ball ball = new Ball(new Vect(5, 5), Vect.ZERO);
        board.renderText(out);
        String[] lines = getStringFromBAOS(outputBuffer).split(System.lineSeparator());
        final Pattern topBottomWall = Pattern.compile("^\\.{22}$");
        final Pattern middle = Pattern.compile("^\\. {20}\\.$");
        final Pattern ballLine = Pattern.compile("^\\. {4}\\* {15}\\.$");
        int linesSinceBeginning = 0;
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.length() == 0) continue;
            if (topBottomWall.matcher(trimmedLine).matches()) {
                linesSinceBeginning = 0;
                continue;
            }
            if (middle.matcher(trimmedLine).matches()) {
                linesSinceBeginning += 1;
                continue;
            }
            if (ballLine.matcher(trimmedLine).matches()) {
                assertEquals(5, linesSinceBeginning);
                continue;
            }
            fail();
        }
        
    }
}
