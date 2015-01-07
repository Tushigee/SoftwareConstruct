package pingball.game.gameobjects.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import physics.LineSegment;
import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.gameobjects.Wall;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;

public class WallTest {
    
    /**
     * Testing Strategy:
     * 
     * partitions for CheckRep:
     *  - null board  
     *  - a wall that doesn't surround:
     *      - top wall
     *      - bottom wall
     *      - left wall
     *      - right wall
     *  - a wall that is perfect
     * partitions for renderText
     *  - horizontal line segment
     *  - verticle line segment
     *  - length 1 line segment
     * partitions for collision trials
     *  - collision with top
     *  - collision with bottom
     *  - collision with right
     *  - collision with left
     *  
     */

    final Board nullBoard = null;
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    private static final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);
    final Board realBoard = new Board(20, 20, physicsConfig);
    final Vect realPosition = new Vect(0,17);
    
    @Test(expected = IllegalArgumentException.class)
    public void testCheckRepNullBoard() {
        LineSegment goodLineSegment = new LineSegment(-0.5,0,20.5,0);
        new Wall(null, Wall.WallPosition.TOP);      
    }
    
    @Test
    public void testCheckRepWallPerfect() {
        LineSegment badLineSegment = new LineSegment(-0.5,-0.5,20.5,-0.5);
        new Wall(realBoard, Wall.WallPosition.TOP); 
    }
    
    @Test 
    public void testRenderTextHorizontalLineSegment(){
        Wall shortWall = new Wall(realBoard, Wall.WallPosition.TOP);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        for (int i = -1; i <=20; i++){
            textGraphicsTest.put(new Point<Integer>(i, -1), ".");
        }
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        shortWall.renderText(textGraphics);
        assertEquals(textGraphicsTest.toString(),textGraphics.toString());       
    }
    
    @Test
    public void testRenderTextVerticleLineSegment(){
        LineSegment wallLineSegment = new LineSegment(-0.5,-0.5,-0.5,10.5);
        Wall shortWall = new Wall(realBoard, Wall.WallPosition.LEFT);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        for (int i = -1; i <=20; i++){
            textGraphicsTest.put(new Point<Integer>(-1, i), ".");
        }
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        shortWall.renderText(textGraphics);
        assertEquals(textGraphicsTest.toString(),textGraphics.toString());       
    }
   
    
    @Test
    public void testCollisionWithTop(){
        final StationaryGameObject topWall = new Wall(realBoard, Wall.WallPosition.TOP);
        Ball ball = new Ball(new Vect(5, 5), 0.5, new Vect(0, -1), 1.0);
        assertNotEquals(topWall.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        topWall.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity().x(), 0, .000000001);
        assertEquals(ball.getVelocity().y(), 1, .000000001);  
    }
    
    @Test
    public void testCollisionWithBottom(){
        final StationaryGameObject topWall = new Wall(realBoard, Wall.WallPosition.BOTTOM);
        Ball ball = new Ball(new Vect(15, 15), 0.5, new Vect(0, 1), 1.0);
        assertNotEquals(topWall.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        topWall.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity().x(), 0, .000000001);
        assertEquals(ball.getVelocity().y(), -1, .000000001);   
    }
    
    @Test
    public void testCollisionWithLeft(){
        final StationaryGameObject topWall = new Wall(realBoard, Wall.WallPosition.LEFT);
        Ball ball = new Ball(new Vect(5, 5), 0.5, new Vect(-1, 0), 1.0);
        assertNotEquals(topWall.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        topWall.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity().x(), 1, .000000001);
        assertEquals(ball.getVelocity().y(), 0, .000000001);
    }
    
    @Test
    public void testCollisionWithRight(){
        final StationaryGameObject topWall = new Wall(realBoard, Wall.WallPosition.RIGHT);
        Ball ball = new Ball(new Vect(15, 15), 0.5, new Vect(1, 0), 1.0);
        assertNotEquals(topWall.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        topWall.emitCollisionTrigger(ball, spacePhysics);
        assertEquals(ball.getVelocity().x(), -1, .000000001);
        assertEquals(ball.getVelocity().y(), 0, .000000001);  
    }

}
