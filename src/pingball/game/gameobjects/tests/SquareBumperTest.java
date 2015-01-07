package pingball.game.gameobjects.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.gameobjects.bumpers.SquareBumper;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;

public class SquareBumperTest {
    
    /**
     * Testing Strategy:
     * 
     * SEE ALSO:  pingball.game.gameobjects.bumpers.SquareBumperWhiteboxTest
     * 
     * partitions for CheckRep:
     *  - null board  
     *  - a square bumper whose bounds go outside the board
     *      - top wall
     *      - bottom wall
     *      - left wall
     *      - right wall
     *  - a square bumper that is perfect
     *  partitions for collisions
     *  - top-right corner
     *  - top-left corner
     *  - bottom-left corner
     *  - bottom-right corner
     *  - (all corner collisions are tested in same tests as getClosestCorner
     *  - top border
     *  - bottom border
     *  - left border
     *  - right border
     * partitions for renderText
     *  - top-left corner
     *  - top-right corner
     *  - bottom-left corner
     *  - bottom-right corner
     */

    final Board nullBoard = null;
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    private static final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);

    final Board realBoard = new Board(20, 20, physicsConfig);
    final Vect nullPosition = null;
    final Vect realPosition = new Vect(0,17);
    
   
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepNullBoard() {
        new SquareBumper(nullBoard, realPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepSquareBumperWhoseBoundsGoOutsideTheBoardTopWall() {
        Vect badPosition = new Vect(0,-5);
        new SquareBumper(realBoard, badPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepSquareBumperWhoseBoundsGoOutsideTheBoardBottomWall() {
        Vect badPosition = new Vect(0,19.5);
        new SquareBumper(realBoard, badPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepSquareBumperWhoseBoundsGoOutsideTheBoardLeftWall() {
        Vect badPosition = new Vect(-2,5);
        new SquareBumper(realBoard, badPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepSquareBumperWhoseBoundsGoOutsideTheBoardRightWall() {
        Vect badPosition = new Vect(19.5,5);
        new SquareBumper(realBoard, badPosition);
    }
    
    @Test
    public void testCheckRepSquareBumperPerfect() {
        Vect goodPosition = new Vect(10,10);
        new SquareBumper(realBoard, goodPosition);
    }
    
    
    @Test
    public void renderTextTopLeftCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(0,0));
        realBoard.addStationaryObject(squareBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 0), "#");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        squareBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest.toString(),textGraphics.toString());
    }
    
    @Test
    public void renderTextTopRightCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(19,0));
        realBoard.addStationaryObject(squareBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 0), "#");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        squareBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest.toString(),textGraphics.toString());       
    }
    
    @Test
    public void renderTextBottomLeftCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(0,19));
        realBoard.addStationaryObject(squareBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 19), "#");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        squareBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest.toString(),textGraphics.toString());     
    }
    
    @Test
    public void renderTextBottomRightCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(19,19));
        realBoard.addStationaryObject(squareBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 19), "#");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        squareBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest.toString(),textGraphics.toString());     
    }
   
    @Test
    public void testCollisionWithTop(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Ball ball = new Ball(new Vect(10.5, 5), 0.5, new Vect(0, 1), 1.0);
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().x()) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().y() + 1) < .000000001);  
    }
    
    @Test
    public void testCollisionWithBottom(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Ball ball = new Ball(new Vect(10.5, 15), 0.5, new Vect(0, -1), 1.0);
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().x()) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().y() - 1) < .000000001); 
        
    }
    
    @Test
    public void testCollisionWithLeft(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Ball ball = new Ball(new Vect(5, 10.5), 0.5, new Vect(1, 0), 1.0);
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().y()) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().x() + 1) < .000000001); 
        
    }
    
    @Test
    public void testCollisionWithRight(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Ball ball = new Ball(new Vect(15, 10.5), 0.5, new Vect(-1, 0), 1.0);
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().y()) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().x() - 1) < .000000001); 
        
    }
}
