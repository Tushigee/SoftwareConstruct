package pingball.game.gameobjects.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Absorber;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.GameObject;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;


/**
 *  A testing suite for the Absorber class
 */
public class AbsorberTest {
    /**
     * Testing Strategy:
     * 
     * SEE ALSO:  pingball.game.gameobjects.AbsorberWhiteboxTest
     * 
     * partitions for CheckRep:
     *  - null board
     *  - height less than 0
     *  - height greater than 20
     *  - width less than 0
     *  - width greater than 20
     *  - an absorber whose bounds go outside the board
     *      - top wall
     *      - bottom wall
     *      - left wall
     *      - right wall
     *  - an absorber that is perfect
     * partitions for collision 
     *  - TODO: all 4 sides
     *  - a ball colliding with the absorber
     *        - top border
     *        - left border
     *        - right border
     *        - bottom border
     * partitions for renderText
     *  - height of 1
     *  - height of 2
     */

    /**
     * Physics constants for certain tests
     */
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    /**
     * Default physics constants for pingball
     */
    final PhysicsConfiguration realPhysics = new PhysicsConfiguration(new Vect(0.0, 25.0), 0.025, 0.025);
    /**
     * Physics constants with 0 gravity and no friction
     */
    private static final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);
    
    /**
     * A board for checking basic getter methods
     */
    final Board realBoard = new Board("untitled", 20, 20, physicsConfig, false);
    /**
     * A vector used in multiple tests
     */
    final Vect realPosition = new Vect(0,17);
    
    /**
     * Mutates the given game-object (usually an absorber)
     *   to be self-triggering
     * 
     * @param absorber the game-object which will be made
     *   self-triggering
     */
    private static void addSelfActionTrigger(GameObject absorber) {
        absorber.addCollisionTriggerListener(new TriggerListener() {
            @Override
            public void trigger(Ball dispatcher, PhysicsConfiguration config) {
                absorber.emitSpecialActionTrigger(dispatcher, config);
            }
        });
    }
    
    
    /**
     * Tests check rep to ensure it can catches null values
     * 
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepNullBoard() {
        new Absorber(null, 1 , 20, realPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with coordinates less than 0
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepHeightLessThanZero() {
        new Absorber(realBoard, -1 , 20, realPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with coordinates greater than 20
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepHeightGreaterThanTwenty() {
        new Absorber(realBoard, 25 , 20, realPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with coordinates less than 0
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepWidthLessThanZero() {
        new Absorber(realBoard, 1 , -1, realPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with coordinates greater than 20
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepWidthGreaterThanTwenty() {
        new Absorber(realBoard, 1 , 25, realPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with position coordinates greater less than 0
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepAbsorberWhoseBoundsGoOutsideTheBoardTopWall() {
        Vect badPosition = new Vect(0,-5);
        new Absorber(realBoard, 5 , 20, badPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with position and height/width combined
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepAbsorberWhoseBoundsGoOutsideTheBoardBottomWall() {
        Vect badPosition = new Vect(0,18);
        new Absorber(realBoard, 5 , 20, badPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with position
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepAbsorberWhoseBoundsGoOutsideTheBoardLeftWall() {
        Vect badPosition = new Vect(-2,5);
        new Absorber(realBoard, 1 , 20, badPosition);
    }
    /**
     * Tests check rep to ensure it catches out of bounds
     *   errors with position and height/width combined
     *   
     * @category no_didit
     */
    @SuppressWarnings("unused")
    @Test(expected = AssertionError.class)
    public void testCheckRepAbsorberWhoseBoundsGoOutsideTheBoardRightWall() {
        Vect badPosition = new Vect(5,5);
        new Absorber(realBoard, 1 , 20, badPosition);
    }
    
    /**
     * Tests check rep to ensure it allows a
     *   valid absorber
     */
    @SuppressWarnings("unused")
    @Test
    public void testCheckRepPerfectAbsorber() {
        new Absorber(realBoard, 1, 20, new Vect(0,17));
    }
    
    
    
    @Test
    public void testRenderTextHeightOfOne(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        StationaryGameObject AbosrberWithHeightOne = new Absorber(realBoardWithAbsorber, 1, 20, new Vect(0,17));
        realBoardWithAbsorber.addStationaryObject(AbosrberWithHeightOne);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        //21 becaause width of board starts at 0
        for (int i = 0; i < 20; i++){
            textGraphicsTest.put(new Point<Integer>(i, 17), "=");
        }
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        AbosrberWithHeightOne.renderText(textGraphics);

        assertEquals(textGraphicsTest.toString(),textGraphics.toString());
    }
    
    @Test
    public void testRenderTextHeightOfTwo(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        StationaryGameObject abosrberWithHeightTwo = new Absorber(realBoardWithAbsorber, 2, 20, new Vect(0,17));
        realBoardWithAbsorber.addStationaryObject(abosrberWithHeightTwo);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        //21 becaause width of board starts at 0
        for (int j  = 0; j < 2; j++){
            for (int i = 0; i < 20; i++){
                textGraphicsTest.put(new Point<Integer>(i, 17+j), "=");
            }
        }
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        abosrberWithHeightTwo.renderText(textGraphics);
        assertEquals(textGraphicsTest.toString(),textGraphics.toString());
    }
    
    @Test
    public void testCollisionFromTop(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        StationaryGameObject absorber = new Absorber(realBoardWithAbsorber, 2, 20, new Vect(0,17));
        addSelfActionTrigger(absorber);
        Ball ball = new Ball(new Vect(12.55, 10), new Vect(0, 1));
        double collisionTime = absorber.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        absorber.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity(), new Vect(0, -50));
        assertEquals(ball.getPosition(), new Vect(19.75,18.75));
    }
    
    @Test
    public void testCollisionFromLeft(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        StationaryGameObject absorber = new Absorber(realBoardWithAbsorber, 2, 5, new Vect(5,5));
        addSelfActionTrigger(absorber);
        Ball ball = new Ball(new Vect(3, 6), new Vect(1, 0));
        double collisionTime = absorber.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        absorber.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity(), new Vect(0, -50));
        assertEquals(ball.getPosition(), new Vect(9.75,6.75));
    }
    
    @Test
    public void testCollisionFromRight(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        StationaryGameObject absorber = new Absorber(realBoardWithAbsorber, 2, 5, new Vect(5,5));
        addSelfActionTrigger(absorber);
        Ball ball = new Ball(new Vect(15, 6), new Vect(-1, 0));
        double collisionTime = absorber.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        absorber.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity(), new Vect(0, -50));
        assertEquals(ball.getPosition(), new Vect(9.75,6.75));
    }
    
    @Test
    public void testCollisionFromBottom(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        StationaryGameObject absorber = new Absorber(realBoardWithAbsorber, 2, 5, new Vect(5,5));
        addSelfActionTrigger(absorber);
        Ball ball = new Ball(new Vect(7.5, 15), new Vect(0, -1));
        double collisionTime = absorber.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        absorber.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity(), new Vect(0, -50));
        assertEquals(ball.getPosition(), new Vect(9.75,6.75));
    }
    
    // ADAPTED FROM BATTUSHIG'S PHASE 1:
    
    @Test
    public void testCollisionBallCorner() {
        final Board realBoardWithAbsorber = new Board(20, 20, realPhysics);
        // Ball ball = new Ball(1.5, 1.5, new Vect(1.0, 1.0));
        Ball ball = new Ball(new Vect(1, 1), new Vect(1.0, 1.0));
        // Gadget absorber = Gadget.absorber(10.0, 10.0, 1, 1);
        Absorber absorber = new Absorber(realBoardWithAbsorber, 1, 1, new Vect(10, 10));
        
        assertTrue(absorber.timeUntilCollision(ball)>=8);
        assertTrue(absorber.timeUntilCollision(ball)<=9);
    }
    
    @Test
    public void testCollisionBallMovedToCorrectLocationAndZeroVelocity() {
        final Board realBoardWithAbsorber = new Board(20, 20, realPhysics);
        // Ball ball = new Ball(10.0, 1.5, new Vect(0.0,1.0));
        Ball ball = new Ball(new Vect(0, 1.0), new Vect(9.5, 1.));
        // Gadget absorber = Gadget.absorber(8.0, 10.0, 4, 1);
        Absorber absorber = new Absorber(realBoardWithAbsorber, 1, 4, new Vect(8, 10));
        
        absorber.emitCollisionTrigger(ball, realPhysics);
        assertEquals(ball.getPosition(), new Vect(11.75, 10.75));
        assertTrue(ball.getVelocity().length() == 0);
        
    }
    
    @Test
    public void testCollisionAbsorberContainsBallAlready() {
        final double epsilon = 0.0000001;
        final Board realBoardWithAbsorber = new Board(20, 20, realPhysics);
        // Ball ball = new Ball(10.0, 1.5, new Vect(0.0,1.0));
        Ball ball = new Ball(new Vect(10, 1.5), new Vect(0, 1.));
        // Ball ball2 = new Ball(10.0, 5.5, new Vect(0.0,0.0));
        Ball ball2 = new Ball(new Vect(10, 5.5), new Vect(0, 0));
        // Gadget absorber = Gadget.absorber(8.0, 10.0, 4, 1);
        Absorber absorber = new Absorber(realBoardWithAbsorber, 1, 4, new Vect(8, 10));
        absorber.emitCollisionTrigger(ball2, realPhysics);
        absorber.emitCollisionTrigger(ball, realPhysics);
        assertTrue(ball.getVelocity().equals(Vect.ZERO));
        assertTrue(ball2.getVelocity().equals(Vect.ZERO));
        assertEquals(ball2.getPosition(), new Vect(11.75, 10.75));
        assertEquals(ball.getPosition().y(), 10.75, epsilon);
        assertTrue(ball.getPosition().x() <= ball2.getPosition().x());
    }
    
    @Test
    public void testSpecialActionAbsorberContainsBall() {
        final Board realBoardWithAbsorber = new Board(20, 20, realPhysics);
        // Ball ball = new Ball(10.0, 1.5, new Vect(0.0,1.0));
        Ball ball = new Ball(new Vect(10, 1.5), new Vect(0, 1.));
        // Gadget absorber = Gadget.absorber(8.0, 10.0, 4, 1);
        Absorber absorber = new Absorber(realBoardWithAbsorber, 1, 4, new Vect(8, 10));
        absorber.emitCollisionTrigger(ball, realPhysics);
        // absorber.collision(ball);
        absorber.emitSpecialActionTrigger(ball, realPhysics);
        assertTrue(ball.getVelocity().equals(new Vect(0.0,-50.0)));
        
    }
    
    @Test
    public void testDoActionAbsorberContainsNoBall() {
        final Board realBoardWithAbsorber = new Board(20, 20, realPhysics);
        // Ball ball = new Ball(10.0, 1.5, new Vect(0.0,1.0));
        Ball ball = new Ball(new Vect(10, 1.5), new Vect(0, 1.));
        // Gadget absorber = Gadget.absorber(8.0, 10.0, 4, 1);
        Absorber absorber = new Absorber(realBoardWithAbsorber, 1, 4, new Vect(8, 10));
        absorber.emitSpecialActionTrigger(ball, physicsConfig);
        assertTrue(ball.getVelocity().equals(new Vect(0.0,1.0)));
        
    }
    
}
