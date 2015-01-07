package pingball.game.gameobjects.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;

import physics.Vect;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.Flipper;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;

/**
 * Test suite for the Flipper game object
 */
public class FlipperTest {
    
    // Testing Strategy:
    // RI:
    //   - null passed to constructor
    // Trigger Special Event:
    //   - While down
    //   - While up
    //   - While moving up
    //   - While moving down
    //   - Time steps:
    //       - Large time steps
    //       - Small time steps
    // Collision Timing:
    //   - While down
    //   - While up
    //   - While moving up
    //   - While moving down
    // Collision location:
    //   - Line segment (from above and below)
    //   - Anchor end cap
    //   - Moving end end cap
    // Render:
    //   - While Down
    //   - While Rotating
    //   - while Up
    
    private static final Point<Integer> middle = new Point<Integer>(10, 10);
    private static final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0., 0.);
    
    /**
     * null passed to constructor
     */
    @SuppressWarnings("unused")
    @Test(expected = IllegalArgumentException.class)
    public void testBasicConstructorFlipper() {
        new Flipper(null, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.DOWN, 0);
    }
    
    /**
     * Calling special event when down & large time steps
     * left flipper
     */
    @Test
    public void testSpecialEventWhileDown() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.DOWN, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.advance(5., spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
    }
    
    /**
     * Calling special event when up & small time steps
     * left flipper
     */
    @Test
    public void testSpecialEventWhileUp() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.UP, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        for (int i = 0; i < 5000; ++i) {
            flipper.advance(0.001, spacePhysics);
        }
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
    }
    
    /**
     * Calling special event when rotating up & small time steps
     * left flipper
     */
    @Test
    public void testSpecialEventWhileRotatingUp() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.DOWN, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.advance(0.00002, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        for (int i = 0; i < 5000; ++i) {
            flipper.advance(0.001, spacePhysics);
        }
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
    }
    
    /**
     * Calling special event when rotating down & large time steps
     * left flipper
     */
    @Test
    public void testSpecialEventWhileRotatingDown() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.UP, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        flipper.advance(0.00002, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.advance(5., spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
    }
    
    /**
     * Calling special event when down & large time steps
     * right flipper
     */
    @Test
    public void testSpecialEventWhileDownRightFlipper() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.DOWN, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.advance(5., spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
    }
    
    /**
     * Calling special event when up & small time steps
     * right flipper
     */
    @Test
    public void testSpecialEventWhileUpRightFlipper() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.UP, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        for (int i = 0; i < 5000; ++i) {
            flipper.advance(0.001, spacePhysics);
        }
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
    }
    
    /**
     * Calling special event when rotating up & small time steps
     * right flipper
     */
    @Test
    public void testSpecialEventWhileRotatingUpRightFlipper() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.DOWN, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.advance(0.00002, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        for (int i = 0; i < 5000; ++i) {
            flipper.advance(0.001, spacePhysics);
        }
        assertEquals(flipper.getState(), Flipper.FlipperState.DOWN);
    }
    
    /**
     * Calling special event when rotating down & large time steps
     * right flipper
     */
    @Test
    public void testSpecialEventWhileRotatingDownRightFlipper() {
        Flipper flipper = new Flipper(middle, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.UP, 0);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        flipper.advance(0.00002, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_DOWN);
        flipper.emitSpecialActionTrigger(null, spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.ROTATING_UP);
        flipper.advance(5., spacePhysics);
        assertEquals(flipper.getState(), Flipper.FlipperState.UP);
    }
    
    /**
     * Collision while down from above
     * left flipper
     */
    @Test
    public void testCollisionDownAbove() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.UP, 0);
        double initialVelocity = 1.;
        Ball ball = new Ball(new Vect(11, 9.45), new Vect(0, initialVelocity));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().y() < 0);
        assertTrue(Math.abs(ball.getVelocity().x()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().y() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while down from below
     * right flipper
     */
    @Test
    public void testCollisionDownBelow() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.UP, 0);
        double initialVelocity = -1.;
        Ball ball = new Ball(new Vect(11, 10.55), new Vect(0, initialVelocity));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().y() > 0);
        assertTrue(Math.abs(ball.getVelocity().x()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().y() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while down from left 
     * left flipper
     */
    @Test
    public void testCollisionUpLeftLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.DOWN, 0);
        double initialVelocity = 1.;
        Ball ball = new Ball(new Vect(9.45, 11), new Vect(initialVelocity, 0));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() < 0);
        assertTrue(Math.abs(ball.getVelocity().y()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().x() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while down from left
     * right flipper
     */
    @Test
    public void testCollisionUpLeftRightFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.DOWN, 0);
        double initialVelocity = 1.;
        Ball ball = new Ball(new Vect(11.45, 11), new Vect(initialVelocity, 0));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() < 0);
        assertTrue(Math.abs(ball.getVelocity().y()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().x() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while down from right
     * left flipper
     */
    @Test
    public void testCollisionUpRightLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.DOWN, 0);
        double initialVelocity = -1.;
        Ball ball = new Ball(new Vect(10.55, 11), new Vect(initialVelocity, 0));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() > 0);
        assertTrue(Math.abs(ball.getVelocity().y()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().x() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while down from right
     * right flipper
     */
    @Test
    public void testCollisionUpRightRightFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.DOWN, 0);
        double initialVelocity = -1.;
        Ball ball = new Ball(new Vect(12.55, 11), new Vect(initialVelocity, 0));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() > 0);
        assertTrue(Math.abs(ball.getVelocity().y()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().x() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while down from right
     * right flipper
     * orientation:  180
     */
    @Test
    public void testCollisionUpRightRightFlipperOrientation180() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.DOWN, 180);
        double initialVelocity = -1.;
        Ball ball = new Ball(new Vect(10.55, 11), new Vect(initialVelocity, 0));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() > 0);
        assertTrue(Math.abs(ball.getVelocity().y()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().x() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while up from top
     * right flipper
     * orientation:  180
     */
    @Test
    public void testCollisionUpDownTopFlipperOrientation180() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.UP, 180);
        double initialVelocity = 1.;
        Ball ball = new Ball(new Vect(11, 11.55), new Vect(0, initialVelocity));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().y() < 0);
        assertTrue(Math.abs(ball.getVelocity().x()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().y() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while down from top
     * right flipper
     * orientation:  270
     */
    @Test
    public void testCollisionUpDownTopFlipperOrientation270() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.DOWN, 90);
        double initialVelocity = 1.;
        Ball ball = new Ball(new Vect(11, 11.55), new Vect(0, initialVelocity));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().y() < 0);
        assertTrue(Math.abs(ball.getVelocity().x()) <= 0.00001);
        assertTrue(Math.abs(ball.getVelocity().y() + 0.95 * initialVelocity) < 0.005);
    }
    
    /**
     * Collision while moving up
     * right flipper
     */
    @Test
    public void testCollisionMovingUp() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.ROTATING_UP, 0);
        Ball ball = new Ball(new Vect(11, 11), new Vect(0, 0));
        flipper.advance(0.03, spacePhysics);
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() < 0);
        assertTrue(ball.getVelocity().y() < 0);
    }
    
    /**
     * Collision while moving down
     * right flipper
     */
    @Test
    public void testCollisionMovingDown() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.ROTATING_DOWN, 0);
        Ball ball = new Ball(new Vect(11, 11), new Vect(0, 0));
        flipper.advance(0.03, spacePhysics);
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() > 0);
        assertTrue(ball.getVelocity().y() > 0);
    }
    
    /**
     * Collision while moving up
     * left flipper
     */
    @Test
    public void testCollisionMovingUpLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.ROTATING_UP, 0);
        Ball ball = new Ball(new Vect(11, 11), new Vect(0, 0));
        flipper.advance(0.03, spacePhysics);
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() > 0);
        assertTrue(ball.getVelocity().y() < 0);
    }
    
    /**
     * Collision while moving down
     * left flipper
     */
    @Test
    public void testCollisionMovingDownLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.ROTATING_DOWN, 0);
        Ball ball = new Ball(new Vect(11, 11), new Vect(0, 0));
        flipper.advance(0.03, spacePhysics);
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() < 0);
        assertTrue(ball.getVelocity().y() > 0);
    }
    
    /**
     * Left end cap collision
     */
    @Test
    public void testCollisionLeftEndCapCollision() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.UP, 0);
        double initialVelocity = 1.;
        Ball ball = new Ball(new Vect(9.45, 10), new Vect(initialVelocity, 0));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() < 0);
        assertTrue(Math.abs(ball.getVelocity().y()) < 0.00005);
        assertTrue(Math.abs(ball.getVelocity().x() + 0.95 * initialVelocity) < 0.0005);
    }
    
    /**
     * Right end cap collision
     */
    @Test
    public void testCollisionRightEndCapCollision() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.UP, 0);
        double initialVelocity = -1.;
        Ball ball = new Ball(new Vect(12.55, 10), new Vect(initialVelocity, 0));
        double collisionTime = flipper.timeUntilCollision(ball);
        assertNotEquals(collisionTime, Double.POSITIVE_INFINITY);
        ball.advance(collisionTime, spacePhysics);
        flipper.emitCollisionTrigger(ball, spacePhysics);
        assertTrue(ball.getVelocity().x() > 0);
        assertTrue(Math.abs(ball.getVelocity().y()) < 0.00005);
        assertTrue(Math.abs(ball.getVelocity().x() + 0.95 * initialVelocity) < 0.055);
    }
    
    /**
     * Render down
     * left flipper
     */
    @Test
    public void testRenderDownLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.DOWN, 0);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertEquals(2, graphics.size());
        assertTrue(graphics.containsKey(new Point<Integer>(10, 10)));
        assertTrue(graphics.containsKey(new Point<Integer>(10, 11)));
        assertEquals("|", graphics.get(new Point<Integer>(10, 10)));
        assertEquals("|", graphics.get(new Point<Integer>(10, 11)));
    }
    
    /**
     * Render down
     * left flipper
     */
    @Test
    public void testRenderUpLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.UP, 0);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertEquals(2, graphics.size());
        assertTrue(graphics.containsKey(new Point<Integer>(10, 10)));
        assertTrue(graphics.containsKey(new Point<Integer>(11, 10)));
        assertEquals("-", graphics.get(new Point<Integer>(10, 10)));
        assertEquals("-", graphics.get(new Point<Integer>(11, 10)));
    }
    
    /**
     * Render down
     * right flipper
     */
    @Test
    public void testRenderDownRightFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.DOWN, 0);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertEquals(2, graphics.size());
        assertTrue(graphics.containsKey(new Point<Integer>(11, 10)));
        assertTrue(graphics.containsKey(new Point<Integer>(11, 11)));
        assertEquals("|", graphics.get(new Point<Integer>(11, 10)));
        assertEquals("|", graphics.get(new Point<Integer>(11, 11)));
    }
    
    /**
     * Render up
     * right flipper
     */
    @Test
    public void testRenderUpRightFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.UP, 0);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertEquals(2, graphics.size());
        assertTrue(graphics.containsKey(new Point<Integer>(10, 10)));
        assertTrue(graphics.containsKey(new Point<Integer>(11, 10)));
        assertEquals("-", graphics.get(new Point<Integer>(10, 10)));
        assertEquals("-", graphics.get(new Point<Integer>(11, 10)));
    }
    
    /**
     * Render rotating up
     * right flipper
     */
    @Test
    public void testRenderRotatingUpRightFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.ROTATING_UP, 0);
        flipper.advance(0.04, spacePhysics);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertTrue(graphics.containsKey(new Point<Integer>(11, 10)));
        assertEquals("/", graphics.get(new Point<Integer>(11, 10)));
    }
    
    /**
     * Render rotating down
     * right flipper
     */
    @Test
    public void testRenderRotatingDownRightFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.RIGHT, Flipper.FlipperState.ROTATING_DOWN, 0);
        flipper.advance(0.04, spacePhysics);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertTrue(graphics.containsKey(new Point<Integer>(11, 10)));
        assertEquals("/", graphics.get(new Point<Integer>(11, 10)));
    }
    
    /**
     * Render rotating up
     * left flipper
     */
    @Test
    public void testRenderRotatingUpLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.ROTATING_UP, 0);
        flipper.advance(0.04, spacePhysics);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertTrue(graphics.containsKey(new Point<Integer>(10, 10)));
        assertEquals("\\", graphics.get(new Point<Integer>(10, 10)));
    }
    
    /**
     * Render rotating down
     * left flipper
     */
    @Test
    public void testRenderRotatingDownLeftFlipper() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.ROTATING_DOWN, 0);
        flipper.advance(0.04, spacePhysics);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertTrue(graphics.containsKey(new Point<Integer>(10, 10)));
        assertEquals("\\", graphics.get(new Point<Integer>(10, 10)));
    }
    
    /**
     * Render left orientation 270 down
     */
    @Test
    public void testRenderLeftRotation270Down() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.DOWN, 270);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertTrue(graphics.containsKey(new Point<Integer>(10, 11)));
        assertTrue(graphics.containsKey(new Point<Integer>(11, 11)));
        assertEquals("-", graphics.get(new Point<Integer>(10, 11)));
    }
    
    /**
     * Render left orientation 270 up
     */
    @Test
    public void testRenderLeftRotation270Up() {
        Point<Integer> flipperLocation = new Point<Integer>(10, 10);
        Flipper flipper = new Flipper(flipperLocation, Flipper.FlipperOrientation.LEFT, Flipper.FlipperState.UP, 270);
        Map<Point<Integer>, String> graphics = new HashMap<Point<Integer>, String>();
        flipper.renderText(graphics);
        assertTrue(graphics.containsKey(new Point<Integer>(10, 11)));
        assertTrue(graphics.containsKey(new Point<Integer>(10, 10)));
        assertEquals("|", graphics.get(new Point<Integer>(10, 10)));
    }
    
}
