package pingball.game.gameobjects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import physics.Vect;
import pingball.game.Board;
import pingball.game.util.PhysicsConfiguration;

/**
 *  Absorber tests for methods which are only package-accessible 
 */
public class AbsorberWhiteboxTest {
    
    /**
     * @see pingball.game.gameobjects.tests.AbsorberTest
     * 
     * partitions for isBallInAbsorber
     *  - within x bounds but not y
     *  - within y bounds but not x
     *  - on top/bottom borders
     *  - right/left borders
     *  - clearly inside
     *  - clearly outside
     */

    /**
     * Physics constants for certain tests
     */
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    
    /**
     * checking to see if a ball which is not supposed
     *   to be in the absorber (different y coord) is
     *   not registered as in the absorber
     */
    @Test
    public void testIsBallInAbsorberWithinXBoundsButNotY(){
        final Board realBoardWithAbsorber = new Board("untitled", 20, 20, physicsConfig, false);
        final StationaryGameObject goodAbsorber= new Absorber(realBoardWithAbsorber, 2, 20, new Vect(0,17));
        Ball ball = new Ball(new Vect(5, 2), 0.5, new Vect(1, 1), 1.0);
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(goodAbsorber);
        assertFalse(((Absorber)goodAbsorber).isBallInAbsorber(ball));
    }
    
    /**
     * checking to see if a ball which is not supposed
     *   to be in the absorber (different x coord) is
     *   not registered as in the absorber
     */
    @Test
    public void testIsBallInAbsorberWithinYBoundsButNotX(){
        final Board realBoardWithAbsorber = new Board("untitled", 20, 20, physicsConfig, false);
        final StationaryGameObject shortAbsorber= new Absorber(realBoardWithAbsorber, 2, 10, new Vect(0,17));
        Ball ball = new Ball(new Vect(15, 18), 0.5, new Vect(1, 1), 1.0);
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(shortAbsorber);
        assertFalse(((Absorber)shortAbsorber).isBallInAbsorber(ball));       
    }
    
    @Test
    public void testIsBallInAbsorberTopBorder(){ 
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        final StationaryGameObject goodAbsorber= new Absorber(realBoardWithAbsorber, 2, 20, new Vect(0,17));
        Ball ball = new Ball(new Vect(5, 17), 0.5, new Vect(1, 1), 1.0);
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(goodAbsorber);
        assertTrue(((Absorber)goodAbsorber).isBallInAbsorber(ball));          
    }
    
    @Test
    public void testIsBallInAbsorberBottomBorder(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        final StationaryGameObject goodAbsorber= new Absorber(realBoardWithAbsorber, 2, 20, new Vect(0,17));
        Ball ball = new Ball(new Vect(4, 19), 0.5, new Vect(1, 1), 1.0);
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(goodAbsorber);
        assertTrue(((Absorber)goodAbsorber).isBallInAbsorber(ball));      
    }
    
    @Test
    public void testIsBallInAbsorberLeftBorder(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        Ball ball = new Ball(new Vect(0, 18.2), 0.5, new Vect(1, 1), 1.0);
        final StationaryGameObject shortAbsorber= new Absorber(realBoardWithAbsorber, 2, 10, new Vect(0,17)); 
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(shortAbsorber);
        assertTrue(((Absorber)shortAbsorber).isBallInAbsorber(ball));      
    }
    
    @Test
    public void testIsBallInAbsorberRightBorder(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        Ball ball = new Ball(new Vect(10, 17.9), 0.5, new Vect(1, 1), 1.0);
        final StationaryGameObject shortAbsorber= new Absorber(realBoardWithAbsorber, 2, 10, new Vect(0,17)); 
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(shortAbsorber);
        assertTrue(((Absorber)shortAbsorber).isBallInAbsorber(ball));      
    }
    
    @Test
    public void testIsBallInAbsorberClearlyInside(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        final StationaryGameObject goodAbsorber= new Absorber(realBoardWithAbsorber, 2, 20, new Vect(0,17));
        Ball ball = new Ball(new Vect(3.2, 17.6), 0.5, new Vect(1, 1), 1.0);
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(goodAbsorber);
        assertTrue(((Absorber)goodAbsorber).isBallInAbsorber(ball));      
    }
    
    @Test
    public void testIsBallInAbsorberClearlyOutside(){
        final Board realBoardWithAbsorber = new Board(20, 20, physicsConfig);
        final StationaryGameObject goodAbsorber= new Absorber(realBoardWithAbsorber, 2, 20, new Vect(0,17));
        Ball ball = new Ball(new Vect(2.2, 2.3), 0.5, new Vect(1, 1), 1.0);
        realBoardWithAbsorber.addBall(ball);
        realBoardWithAbsorber.addStationaryObject(goodAbsorber);
        assertFalse(((Absorber)goodAbsorber).isBallInAbsorber(ball));      
    }
}
