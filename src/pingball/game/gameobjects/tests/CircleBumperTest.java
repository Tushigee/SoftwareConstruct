package pingball.game.gameobjects.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.gameobjects.bumpers.CircleBumper;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;

public class CircleBumperTest {
    
    /**
     * Testing Strategy:
     * 
     * partitions for CheckRep:
     *  - null board
     *  - a circle bumper whose bounds go outside the board
     *      - top wall
     *      - bottom wall
     *      - left wall
     *      - right wall
     *  - a circle bumper that is perfect
     * partitions for renderText
     *  - top-left corner
     *  - top-right corner
     *  - bottom-left corner
     *  - bottom-right corner  
     *  
     *  physics tests
     *  -shoot ball directly at it 
     *  -shoot ball at angle 
     */

    final Board nullBoard = null;
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    final Board realBoard = new Board(20, 20, physicsConfig);
    final Vect nullPosition = null;
    final Vect realPosition = new Vect(0,17);
    final Point<Integer> middle = new Point<Integer>(10, 10);
    final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepNullBoard() {
        new CircleBumper(nullBoard, realPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepCircleBumperWhoseBoundsGoOutsideTheBoardTopWall() {
        Vect badPosition = new Vect(0,-5);
        new CircleBumper(realBoard, badPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepCircleBumperWhoseBoundsGoOutsideTheBoardBottomWall() {
        Vect badPosition = new Vect(0,20.1);
        new CircleBumper(realBoard, badPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepCircleBumperrWhoseBoundsGoOutsideTheBoardLeftWall() {
        Vect badPosition = new Vect(-2,5);
        new CircleBumper(realBoard, badPosition);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepCircleBumperWhoseBoundsGoOutsideTheBoardRightWall() {
        Vect badPosition = new Vect(20.1,5);
        new CircleBumper(realBoard, badPosition);
    }
    
    @Test
    public void testCheckRepCirlceBumperPerfect() {
        Vect goodPosition = new Vect(10,10);
        new CircleBumper(realBoard, goodPosition);
    }
    
    @Test
    public void renderTextTopLeftCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject circleBumper = new CircleBumper(realBoard, new Vect(0,0));
        realBoard.addStationaryObject(circleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 0), "O");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        circleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);
    }
    
    @Test
    public void renderTextTopRightCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject circleBumper = new CircleBumper(realBoard, new Vect(19,0));
        realBoard.addStationaryObject(circleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 0), "O");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        circleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);       
    }
    
    @Test
    public void renderTextBottomLeftCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject circleBumper = new CircleBumper(realBoard, new Vect(0,19));
        realBoard.addStationaryObject(circleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 19), "O");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        circleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);     
    }
    
    @Test
    public void renderTextBottomRightCorner(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject circleBumper = new CircleBumper(realBoard, new Vect(19,19));
        realBoard.addStationaryObject(circleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 19), "O");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        circleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);     
    }
    
    @Test 
    public void reflectBallTestDirectShot(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        StationaryGameObject circleBumper = new CircleBumper(realBoard, new Vect(9.5,9.5));
        Ball ball = new Ball(new Vect(10, 15), new Vect(0, -5));
        circleBumper.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(new Vect(0,5), ball.getVelocity());     
    }
    
    @Test 
    public void reflectBallTestAngledShot(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        StationaryGameObject circleBumper = new CircleBumper(realBoard, new Vect(9.5,9.5));
        Ball idealBall = new Ball(new Vect(12, 12), 0, new Vect(-2, -1.5), 1);
        circleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertEquals(new Vect(1.5,2.0), idealBall.getVelocity());;    
    }

}
