package pingball.game.gameobjects.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;

import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;

/**
 * Tests for Portal
 */
public class PortalTest {

    /*
     * Testing strategy:
     * 
     * - Render text
     * - Collision detection
     * - Routing intra-board
     *     - 3 way portal cycle (pairwise is implicit in this test,
     *         allowing for testing multiple partitions in one test)
     * - Routing iter-board is tested in server tests
     */
    
    /**
     * No gravity and no friction
     */
    private static final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0, 0);
    
    /**
     * Tests that a portal is rendered correctly
     */
    @Test
    public void testRenderText() {
        Board board = new Board("default", 20, 20, spacePhysics, false);
        StationaryGameObject portal = StationaryGameObject.portal(board, new Vect(3, 2), "portal1", "portal2");
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        
        portal.renderText(graphics);
        
        assertFalse(graphics.isEmpty());
        assertEquals(1, graphics.size());
        assertTrue(graphics.containsKey(new Point<Integer>(3, 2)));
        assertEquals("o", graphics.get(new Point<Integer>(3, 2)));
    }
    
    /**
     * Tests that a portal's time until collision works as expected
     *   when a collision will occur
     */
    @Test
    public void testTimeUntilCollisionImpendingCollision() {
        Board board = new Board("default", 20, 20, spacePhysics, false);
        StationaryGameObject portal = StationaryGameObject.portal(board, new Vect(3, 2), "portal1", "portal2");
        Ball ball = new Ball(new Vect(2.45, 2.5), 0.25, new Vect(1, 0), 1);
        board.addBall(ball);
        board.addStationaryObject(portal);
        
        assertTrue(portal.timeUntilCollision(ball) < 1);
    }
    
    /**
     * Tests that a portal's time until collision works as expected
     *   when a collision will not occur
     */
    @Test
    public void testTimeUntilCollisionNoImpendingCollision() {
        Board board = new Board("default", 20, 20, spacePhysics, false);
        StationaryGameObject portal = StationaryGameObject.portal(board, new Vect(3, 2), "portal1", "portal2");
        Ball ball = new Ball(new Vect(2.45, 2.5), 0.25, new Vect(0, 1), 1);
        board.addBall(ball);
        board.addStationaryObject(portal);
        
        assertTrue(Double.isInfinite(portal.timeUntilCollision(ball)));
    }
    
    /**
     * Tests that a ball is not rerouted when the connected portal intra-board
     *   is not present
     */
    @Test
    public void testBallRoutingIntraNotPresent() {
        Board board = new Board("default", 20, 20, spacePhysics, false);
        StationaryGameObject portal = StationaryGameObject.portal(board, new Vect(3, 2), "portal1", "portal2");
        Ball ball = new Ball(new Vect(2.45, 2.5), 0.25, new Vect(1, 0), 1);
        board.addBall(ball);
        board.addStationaryObject("portal1", portal);
        
        assertTrue(portal.timeUntilCollision(ball) < 1);
        portal.emitCollisionTrigger(ball, spacePhysics);
        
        assertEquals(3.5, ball.getCircle().getCenter().x(), 1);
        assertEquals(2.5, ball.getCircle().getCenter().y(), 1);
        assertEquals(1, ball.getVelocity().x(), 0.001);
        assertEquals(0, ball.getVelocity().y(), 0.001);
    }
    
    /**
     * Tests that a ball is not rerouted when the connected portal inter-board
     *   is not present
     */
    @Test
    public void testBallRoutingInterNotPresent() {
        Board board = new Board("default", 20, 20, spacePhysics, false);
        StationaryGameObject portal = StationaryGameObject.portal(board, new Vect(3, 2), "portal1", "portal2", "board2");
        Ball ball = new Ball(new Vect(2.45, 2.5), 0.25, new Vect(1, 0), 1);
        board.addBall(ball);
        board.addStationaryObject("portal1", portal);
        
        assertTrue(portal.timeUntilCollision(ball) < 1);
        portal.emitCollisionTrigger(ball, spacePhysics);
        
        assertEquals(3.5, ball.getCircle().getCenter().x(), 1);
        assertEquals(2.5, ball.getCircle().getCenter().y(), 1);
        assertEquals(1, ball.getVelocity().x(), 0.001);
        assertEquals(0, ball.getVelocity().y(), 0.001);
    }
    
    /**
     * Tests intra-board portal routing with 3 portals
     */
    @Test
    public void testBallRoutingIntraPresent() {
        Board board = new Board("default", 20, 20, spacePhysics, false);
        StationaryGameObject portal1 = StationaryGameObject.portal(board, new Vect(3, 2), "portal1", "portal2");
        StationaryGameObject portal2 = StationaryGameObject.portal(board, new Vect(15, 17), "portal2", "portal3");
        StationaryGameObject portal3 = StationaryGameObject.portal(board, new Vect(1, 13), "portal3", "portal1");
        Ball ball = new Ball(new Vect(2.45, 2.5), 0.25, new Vect(1, 0), 1);
        board.addBall(ball);
        board.addStationaryObject("portal1", portal1);
        board.addStationaryObject("portal2", portal2);
        board.addStationaryObject("portal3", portal3);
        
        assertTrue(portal1.timeUntilCollision(ball) < 1);
        portal1.emitCollisionTrigger(ball, spacePhysics);
        
        assertEquals(15.5, ball.getCircle().getCenter().x(), 1);
        assertEquals(17.5, ball.getCircle().getCenter().y(), 1);
        assertEquals(1, ball.getVelocity().x(), 0.001);
        assertEquals(0, ball.getVelocity().y(), 0.001);
        
        assertTrue(Double.isInfinite(portal2.timeUntilCollision(ball)));
        
        // Turn the ball around
        ball.setVelocity(new Vect(-1, 0));
        
        assertTrue(portal2.timeUntilCollision(ball) < 1);
        portal2.emitCollisionTrigger(ball, spacePhysics);
        
        assertEquals(1.5, ball.getCircle().getCenter().x(), 1);
        assertEquals(13.5, ball.getCircle().getCenter().y(), 1);
        assertEquals(-1, ball.getVelocity().x(), 0.001);
        assertEquals(0, ball.getVelocity().y(), 0.001);
    }
    
}
