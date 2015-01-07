package pingball.game.gameobjects.bumpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.util.PhysicsConfiguration;

/**
 * SqaureBumper tests for methods which are only package-accessible   
 */
public class SquareBumperWhiteboxTest {
    
    /*
     * SEE ALSO:  pingball.game.gameobjects.tests.SquareBumperTest
     * 
     * partitions for getClosestWall
     *  - directly above the bumper
     *  - directly below the bumper
     *  - directly to the right of bumper
     *  - directly to the left of bumper
     *  - ball at around 1pi/8 with respect to x axis
     *  - ball at around 3pi/8 with respect to x axis
     *  - ball at around 5pi/8 with respect to x axis
     *  - ball at around 7pi/8 with respect to x axis
     *  - ball at around 9pi/8 with respect to x axis
     *  - ball at around 11pi/8 with respect to x axis
     *  - ball at around 13pi/8 with respect to x axis
     *  - ball at around 15pi/8 with respect to x axis
     * partitions for getClosestCorner and collision tests
     *  - ball at around 1pi/4
     *  - ball at around 3pi/4
     *  - ball at around 5pi/4
     *  - ball at around 7pi/4
     */
    
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    private static final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);

    @Test
    public void testFindClosestWallDirectlyAboveBumper(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment topBorder = new LineSegment(10.0, 10.0, 11.0, 10.0);
        Ball ball = new Ball(new Vect(10.5, 8), 0.5, new Vect(0, 2.5), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(topBorder, ((SquareBumper)squareBumper).getClosestWall(ball));      
    }

    @Test
    public void testFindClosestWallDirectlyBelowBumper(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment bottomBorder = new LineSegment(10.0, 11.0, 11.0, 11.0);
        Ball ball = new Ball(new Vect(10.5, 13), 0.5, new Vect(0, -2.5), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(bottomBorder, ((SquareBumper)squareBumper).getClosestWall(ball));        
    }

    @Test
    public void testFindClosestWallDirectlyToLeftOfBumper(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment leftBorder = new LineSegment(10.0, 10.0, 10.0, 11.0);
        Ball ball = new Ball(new Vect(8, 10.5), 0.5, new Vect(2.5, 0), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(leftBorder, ((SquareBumper)squareBumper).getClosestWall(ball));       
    }

    @Test
    public void testFindClosestWallDirectlyToRightOfBumper(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment rightBorder = new LineSegment(11.0, 10.0, 11.0, 11.0);
        Ball ball = new Ball(new Vect(13, 10.5), 0.5, new Vect(-2.5, 0), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(rightBorder, ((SquareBumper)squareBumper).getClosestWall(ball));       
    }

    @Test
    public void testFindClosestWallPiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment rightBorder = new LineSegment(11.0, 10.0, 11.0, 11.0);
        Ball ball = new Ball(new Vect(12.35, 11.26), 0.5, new Vect(-1.85, -.76), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(rightBorder, ((SquareBumper)squareBumper).getClosestWall(ball));
    }

    @Test
    public void testFindClosestWallThreePiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment bottomBorder = new LineSegment(10.0, 11.0, 11.0, 11.0);
        Ball ball = new Ball(new Vect(11.27, 12.35), 0.5, new Vect(-.77, -1.85), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(bottomBorder, ((SquareBumper)squareBumper).getClosestWall(ball));        
    }

    @Test
    public void testFindClosestWallFivePiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment bottomBorder = new LineSegment(10.0, 11.0, 11.0, 11.0);
        Ball ball = new Ball(new Vect(9.74, 12.35), 0.5, new Vect(.76, -1.85), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(bottomBorder, ((SquareBumper)squareBumper).getClosestWall(ball));        
    }

    @Test
    public void testFindClosestWallSevenPiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment leftBorder = new LineSegment(10.0, 10.0, 10.0, 11.0);
        Ball ball = new Ball(new Vect(8.65, 11.27), 0.5, new Vect(1.85, -.77), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(leftBorder, ((SquareBumper)squareBumper).getClosestWall(ball));         
    }

    @Test
    public void testFindClosestWallNinePiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment leftBorder = new LineSegment(10.0, 10.0, 10.0, 11.0);
        Ball ball = new Ball(new Vect(8.65, 9.74), 0.5, new Vect(1.85, .76), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(leftBorder, ((SquareBumper)squareBumper).getClosestWall(ball));       
    }

    @Test
    public void testFindClosestWallElevenPiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment topBorder = new LineSegment(10.0, 10.0, 11.0, 10.0);
        Ball ball = new Ball(new Vect(9.73, 8.65), 0.5, new Vect(.77, 1.85), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(topBorder, ((SquareBumper)squareBumper).getClosestWall(ball));        
    }

    @Test
    public void testFindClosestWallThirteenPiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment topBorder = new LineSegment(10.0, 10.0, 11.0, 10.0);
        Ball ball = new Ball(new Vect(11.26, 8.65), 0.5, new Vect(-.76, 1.85), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(topBorder, ((SquareBumper)squareBumper).getClosestWall(ball));        
    }

    @Test
    public void testFindClosestWallFifteenPiOverEight(){
        final Board realBoard = new Board(20, 20, physicsConfig);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        LineSegment rightBorder = new LineSegment(11.0, 10.0, 11.0, 11.0);
        Ball ball = new Ball(new Vect(12.35, 9.73), 0.5, new Vect(-1.85, .77), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(rightBorder, ((SquareBumper)squareBumper).getClosestWall(ball));       
    }
    
    @Test
    public void testFindClosestCornerPiOverFourAndCollision(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Circle topRightCorner = new Circle(new Vect(11,10), 0);
        Ball ball = new Ball(new Vect(16, 5), 0.5, new Vect(-1, 1), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(topRightCorner, ((SquareBumper)squareBumper).getClosestCorner(ball));
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().x() - 1) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().y() + 1) < .000000001);
    }
    
    
    @Test
    public void testFindClosestCornerThreePiOverFourAndCollision(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Circle topLeftCorner = new Circle(new Vect(10,10), 0);
        Ball ball = new Ball(new Vect(5, 5), 0.5, new Vect(1, 1), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(topLeftCorner, ((SquareBumper)squareBumper).getClosestCorner(ball));
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().x() + 1) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().y() + 1) < .000000001);
    }
    
    @Test
    public void testFindClosestCornerFivePiOverFourAndCollision(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Circle bottomLeftCorner= new Circle(new Vect(10,11), 0);
        Ball ball = new Ball(new Vect(5, 16), 0.5, new Vect(1, -1), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(bottomLeftCorner, ((SquareBumper)squareBumper).getClosestCorner(ball));
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().x() + 1) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().y() - 1) < .000000001);
    }
    
    @Test
    public void testFindClosestCornerSevenPiOverFourAndCollision(){
        final Board realBoard = new Board(20, 20, spacePhysics);
        final StationaryGameObject squareBumper = new SquareBumper(realBoard, new Vect(10,10));
        Circle bottomRightBorder = new Circle(new Vect(11,11), 0);
        Ball ball = new Ball(new Vect(15, 15), 0.5, new Vect(-1, -1), 1.0);
        realBoard.addBall(ball);
        realBoard.addStationaryObject(squareBumper);
        assertEquals(bottomRightBorder, ((SquareBumper)squareBumper).getClosestCorner(ball));
        assertNotEquals(squareBumper.timeUntilCollision(ball), Double.POSITIVE_INFINITY);
        squareBumper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(Math.abs(ball.getVelocity().x() - 1) < .000000001);
        assertTrue(Math.abs(ball.getVelocity().y() - 1) < .000000001);
    }
    
}
