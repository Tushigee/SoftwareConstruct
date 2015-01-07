package pingball.game.gameobjects.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.gameobjects.bumpers.TriangleBumper;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;

/**
 * TriangleBumper tests for methods which are only package-accessible 
 */
public class TriangleBumperTest {
    
    /**
     * Testing strategy:
     * 
     * SEE ALSO:  pingball.game.gameobjects.bumpers.TriangleBumperWhiteboxTest
     * 
     * CheckRep
     * -good inputs
     * -bad position
     * -null board
     * -bad orientation 
     * 
     * RenderText
     * -All 4 corners 0 degree orientation 
     * -90/180/270 degree rotations 
     * 
     * Reflect Ball
     * -hit all three sides head on 
     * -bank ball off of hypotenuse 
     * -hit all 3 corners dead on 
     * 
     *
     */
    final Board nullBoard = null;
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(
            new Vect(0.0, 1.0), 0.1, 0.1);
    final Board realBoard = new Board(20, 20, physicsConfig);
    final Vect nullPosition = null;
    final Vect realPosition = new Vect(0, 17);
    final Vect badPosition = new Vect(0, 100);
    final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);
    
    /**
     * @category no_didit
     */
    @Test
    public void checkRepGoodPosition() {
        new TriangleBumper(realBoard, realPosition, 0);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void checkRepBadPostion() {
        new TriangleBumper(realBoard, badPosition, 0);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepNullBoard() {
        new TriangleBumper(nullBoard, realPosition, 0);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepBadOrientation() {
        new TriangleBumper(realBoard, realPosition, 120);
    }

    @Test
    public void renderTextTopLeftCorner() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject triangleBumper = new TriangleBumper(realBoard,
                new Vect(0, 0), 0);
        realBoard.addStationaryObject(triangleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 0), "/");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        triangleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest, textGraphics);
    }

    @Test
    public void renderTextTopRightCorner() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject triangleBumper = new TriangleBumper(realBoard,
                new Vect(19, 0), 0);
        realBoard.addStationaryObject(triangleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 0), "/");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        triangleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest, textGraphics);
    }

    @Test
    public void renderTextBottomLeftCorner() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject triangleBumper = new TriangleBumper(realBoard,
                new Vect(0, 19), 0);
        realBoard.addStationaryObject(triangleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 19), "/");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        triangleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest, textGraphics);
    }

    @Test
    public void renderTextBottomRightCorner() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject triangleBumper = new TriangleBumper(realBoard,
                new Vect(19, 19), 0);
        realBoard.addStationaryObject(triangleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 19), "/");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        triangleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest, textGraphics);
    }

    @Test
    public void renderTextBottomRightCornerOrientationNinety() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject triangleBumper = new TriangleBumper(realBoard,
                new Vect(19, 19), 90);
        realBoard.addStationaryObject(triangleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 19), "\\");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        triangleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest, textGraphics);
    }

    @Test
    public void renderTextBottomRightCornerOrientation180() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject triangleBumper = new TriangleBumper(realBoard,
                new Vect(19, 19), 180);
        realBoard.addStationaryObject(triangleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 19), "/");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        triangleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest, textGraphics);
    }

    @Test
    public void renderTextBottomRightCornerOrientation270() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        StationaryGameObject triangleBumper = new TriangleBumper(realBoard,
                new Vect(19, 19), 270);
        realBoard.addStationaryObject(triangleBumper);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 19), "\\");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        triangleBumper.renderText(textGraphics);
        assertEquals(textGraphicsTest, textGraphics);
    }
    
    @Test 
    public void testReflectBallHorizontalLeg(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Ball idealBall = new Ball(new Vect(10.5, 8), 0, new Vect(0, 1), 1);
        triangleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertEquals(new Vect(0,-1), idealBall.getVelocity());   
    }
    
    @Test 
    public void testReflectBallVerticalLeg(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Ball idealBall = new Ball(new Vect(9, 10.5), 0, new Vect(1, 0), 1);
        triangleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertEquals(new Vect(-1,0), idealBall.getVelocity());
    }
    
    @Test 
    public void testReflectBallHypotenusePerfectShot(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Ball idealBall = new Ball(new Vect(11, 11), 0, new Vect(-1, -1), 1);
        triangleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertTrue(idealBall.getVelocity().x() > 0);
        assertTrue(idealBall.getVelocity().y() > 0);
        assertTrue(Math.abs(idealBall.getVelocity().x()) -1 < .00000001);
        assertTrue(Math.abs(idealBall.getVelocity().y()) -1 < .00000001);
    }
    
    @Test 
    public void testReflectBallHypotenuseBankedShot(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Ball idealBall = new Ball(new Vect(10.5, 11), 0, new Vect(0, -1), 1);
        triangleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertTrue(idealBall.getVelocity().x() > 0);
        assertTrue(idealBall.getVelocity().y() >= 0);
        assertTrue(Math.abs(idealBall.getVelocity().x()) -1 < .00000001);
        assertTrue(Math.abs(idealBall.getVelocity().y()) < .00000001);
        
    }
    
    @Test 
    public void testReflectBallVerticalHypotenuseIntesection(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Ball idealBall = new Ball(new Vect(9, 11), 0, new Vect(1, -1), 1);
        triangleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertTrue(idealBall.getVelocity().x() < 0);
        assertTrue(idealBall.getVelocity().y() > 0);
        assertTrue(Math.abs(idealBall.getVelocity().x()) -1 < .00000001);
        assertTrue(Math.abs(idealBall.getVelocity().y()) -1 < .00000001);
    }
    
    @Test 
    public void testReflectBallVerticalHorizontalIntesection(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Ball idealBall = new Ball(new Vect(9, 9), 0, new Vect(1, 1), 1);
        triangleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertTrue(idealBall.getVelocity().x() < 0);
        assertTrue(idealBall.getVelocity().y() < 0);
        assertTrue(Math.abs(idealBall.getVelocity().x()) -1 < .00000001);
        assertTrue(Math.abs(idealBall.getVelocity().y()) -1 < .00000001);
    }
    
    @Test 
    public void testReflectBallHorizontalHypotenuseIntesection(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Ball idealBall = new Ball(new Vect(12, 9), 0, new Vect(-1, 1), 1);
        triangleBumper.emitCollisionTrigger(idealBall, spacePhysics);
        assertTrue(idealBall.getVelocity().x() > 0);
        assertTrue(idealBall.getVelocity().y() < 0);
        assertTrue(Math.abs(idealBall.getVelocity().x()) -1 < .00000001);
        assertTrue(Math.abs(idealBall.getVelocity().y()) -1 < .00000001);
    }
    
}
