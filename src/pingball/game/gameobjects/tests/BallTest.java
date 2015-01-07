package pingball.game.gameobjects.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import org.junit.BeforeClass;
import org.junit.Test;

import physics.Circle;
import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.networking.JsonUtils;

public class BallTest {
    /**
     * Testing Strategy:
     * 
     * SEE ALSO:  pingball.game.gameobjects.BallWhiteboxTest
     * 
     * partitions for CheckRep:
     *  - null velocity
     *  - a ball whose bounds go outside the board
     *      - top wall
     *      - bottom wall
     *      - left wall
     *      - right wall
     *  - a ball that is perfect
     * partitions for renderText
     *  - top-left corner
     *  - top-right corner
     *  - bottom-left corner
     *  - bottom-right corner
     * ball on ball collision
     *  - equal and opposite velocities
     * json
     *  - serialization
     *  - parsing
     */

    final Board nullBoard = null;
    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(new Vect(0.0, 1.0), 0.1, 0.1);
    final PhysicsConfiguration realPhysics = new PhysicsConfiguration(new Vect(0.0, 25.0), 0.025, 0.025);
    final PhysicsConfiguration spacePhysics = new PhysicsConfiguration(Vect.ZERO, 0, 0);
    final Board realBoard = new Board(20, 20, physicsConfig);
    final Vect nullVelocity = null;
    final Vect realPosition = new Vect(0,17);
    final Vect realVelocity = new Vect(0.5,0.5);


    //public Ball(Vect position, Vect initialVelocity)
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepNullVelcity() {
        new Ball(realPosition, nullVelocity);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepBallOutsideTheBoardTopWall() {
        Vect badPosition = new Vect(5,-5);
        new Ball(badPosition, realVelocity);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepBallOutsideTheBoardBottomWall() {
        Vect badPosition = new Vect(0,21);
        new Ball(badPosition, realVelocity);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepBallOutsideTheBoardLeftWall() {
        Vect badPosition = new Vect(-2,5);
        new Ball(badPosition, realVelocity);
    }
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void testCheckRepBallOutsideTheBoardRightWall() {
        Vect badPosition = new Vect(21,5);
        new Ball(badPosition, realVelocity);
    }

    @Test
    public void testCheckRepBallPerfect() {
        Vect goodPosition = new Vect(10,10);
        new Ball(goodPosition, realVelocity);
    }

    @Test
    public void testRenderTextTopLeftCorner(){
        Vect position = new Vect(0,0);
        Ball ball = new Ball(position, realVelocity);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 0), "*");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        ball.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);
    }

    @Test
    public void testRenderTextTopRightCorner(){
        Vect position = new Vect(20,0);
        Ball ball = new Ball(position, realVelocity);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 0), "*");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        ball.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);       
    }

    @Test
    public void testRenderTextBottomLeftCorner(){
        Vect position = new Vect(0,20);
        Ball ball = new Ball(position, realVelocity);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(0, 19), "*");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        ball.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);    
    }

    @Test
    public void testRenderTextBottomRightCorner(){
        Vect position = new Vect(20,20);
        Ball ball = new Ball(position, realVelocity);
        Map<Point<Integer>, String> textGraphicsTest = new HashMap<Point<Integer>, String>();
        textGraphicsTest.put(new Point<Integer>(19, 19), "*");
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        ball.renderText(textGraphics);
        assertEquals(textGraphicsTest,textGraphics);   
    }

    /**
     * Test ball on ball collision in equal and opposite directions
     */
    @Test
    public void testCollisionBallOnBallEqualAndOpposite() {
        Ball ball1 = new Ball(new Vect(0, 0), new Vect(1, 0));
        Ball ball2 = new Ball(new Vect(1.01, 0), new Vect(-1, 0));
        double timeUntilCollision = ball1.timeUntilCollision(ball2);
        assertTrue(timeUntilCollision < Double.POSITIVE_INFINITY);
        ball2.advance(timeUntilCollision, spacePhysics);
        ball1.emitCollisionTrigger(ball2, spacePhysics);
        assertTrue(ball2.getVelocity().x() > 0);
        assertTrue(ball1.getVelocity().x() < 0);
        assertTrue(Math.abs(ball2.getVelocity().y()) < 0.0001);
        assertTrue(Math.abs(ball1.getVelocity().y()) < 0.0001);
    }

    /**
     * Test ball on ball collision with one ball stationary 
     */
    @Test
    public void testCollisionBallOneBallStationary() {
        Ball ball1 = new Ball(new Vect(0, 0), new Vect(0, 0));
        Ball ball2 = new Ball(new Vect(1.01, 0), new Vect(-1, 0));
        double timeUntilCollision = ball1.timeUntilCollision(ball2);
        assertTrue(timeUntilCollision < Double.POSITIVE_INFINITY);
        ball2.advance(timeUntilCollision, spacePhysics);
        ball1.emitCollisionTrigger(ball2, spacePhysics);
        assertTrue(Math.abs(ball2.getVelocity().x()) < 0.0001);
        assertTrue(ball1.getVelocity().x() < 0);
        assertTrue(Math.abs(ball2.getVelocity().y()) < 0.0001);
        assertTrue(Math.abs(ball1.getVelocity().y()) < 0.0001);
    }

    /**
     * Test basic serialization/parse cycle of ball
     */
    @Test
    public void testBallJsonBasic() {
        final Vect pos = new Vect(0.1, 1.2);
        final double radius = 0.3;
        final Vect vel = new Vect(2.3, 3.4);
        final double mass = 0.7;
        final double epsilon = 0.000001;
        Ball ball1 = new Ball(pos, radius, vel, mass);

        JsonObject serialized = ball1.serializeJson();
        assertEquals(pos.x(), serialized.getJsonNumber("x").doubleValue(), epsilon);
        assertEquals(pos.y(), serialized.getJsonNumber("y").doubleValue(), epsilon);
        assertEquals(radius, serialized.getJsonNumber("radius").doubleValue(), epsilon);
        assertEquals(mass, serialized.getJsonNumber("mass").doubleValue(), epsilon);
        assertEquals(vel.x(), serialized.getJsonNumber("xVel").doubleValue(), epsilon);
        assertEquals(vel.y(), serialized.getJsonNumber("yVel").doubleValue(), epsilon);

        String stringJson = serialized.toString();
        System.out.println(stringJson);

        JsonObject incomingJson = JsonUtils.parseObject(stringJson);
        /* 
        // White-box testing:
        assertEquals(pos.x(), incomingJson.getJsonNumber("x").doubleValue(), epsilon);
        assertEquals(pos.y(), incomingJson.getJsonNumber("y").doubleValue(), epsilon);
        assertEquals(radius, incomingJson.getJsonNumber("radius").doubleValue(), epsilon);
        assertEquals(mass, incomingJson.getJsonNumber("mass").doubleValue(), epsilon);
        assertEquals(vel.x(), incomingJson.getJsonNumber("xVel").doubleValue(), epsilon);
        assertEquals(vel.y(), incomingJson.getJsonNumber("yVel").doubleValue(), epsilon);
        */

        Ball ball2 = Ball.parseJson(incomingJson);

        assertEquals(ball1.getCircle(), ball2.getCircle());
        assertEquals(ball1.getPosition(), ball2.getPosition());
        assertEquals(ball1.getRadius(), ball2.getRadius(), epsilon);
        assertEquals(ball1.getVelocity(), ball2.getVelocity());
    }


    // BEGIN TESTS ADAPTED FROM BATTUSHIG'S PHASE 1

    private static Circle origin;
    private static Circle middle;

    private static Vect still;
    private static Vect upperRight;
    private static Vect right;
    private static Vect up;
    private static Vect down;
    private static Vect left;
    private static Vect lowerRight;
    private static Vect upperLeft;
    private static Vect lowerLeft;
    private static Vect high;


    @BeforeClass
    public static void setUpBeforeClass(){
        origin = new Circle(0.0, 0.0, 0.5);
        middle = new Circle(10.0, 10.0, 0.5);

        still = new Vect(0, 0);
        upperRight = new Vect(1,1);
        right = new Vect(1,0);
        up = new Vect(0,1);
        down = new Vect(0,-1);
        left = new Vect(-1,0);
        lowerRight = new Vect(1,-1);
        upperLeft = new Vect(-1,1);
        lowerLeft = new Vect(-1,-1);
        high = new Vect(0,200);
    }
    
    private static Ball stillBall() {
        return new Ball(origin.getCenter(), still);
    }
    private static Ball upperRightBall() {
        return new Ball(middle.getCenter(), upperRight);
    }
    private static Ball rightBall() {
        return new Ball(middle.getCenter(), right);
    }
    private static Ball upBall() {
        return new Ball(middle.getCenter(), up);
    }
    private static Ball downBall() {
        return new Ball(middle.getCenter(), down);
    }
    private static Ball leftBall() {
        return new Ball(middle.getCenter(), left);
    }
    private static Ball lowerRightBall() {
        return new Ball(middle.getCenter(), lowerRight);
    }
    private static Ball upperLeftBall() {
        return new Ball(middle.getCenter(), upperLeft);
    }
    private static Ball lowerLeftBall() {
        return new Ball(middle.getCenter(), lowerLeft);
    }
    private static Ball fastBall() {
        return new Ball(middle.getCenter(), high);
    }
    private static Ball setVelBall() {
        return new Ball(middle.getCenter(), lowerLeft);
    }
    
    /*
     *-Tests for updatePosition
     *          -with ball's velocity = Vect(0,0) //lower bound
     *          -with velocity = Vect(1,1)
     *          -with velocity = Vect(0,1)
     *          -with velocity = Vect(1,0)
     *          -with velocity = Vect(-1,-1) //negative
     *          -with velocity = Vect(-1,0)
     *          -with velocity = Vect(0,-1)
     *          -with velocity = Vect(0,200) //upper bound
     *          -with no time passing
     */

    @Test
    public void testUpdatePositionStillBall(){
        Ball stillBall1 = new Ball(new Vect(10.0, 10.0), new Vect(0.0, 0.0));
        Vect initialPos = stillBall1.getPosition();
        stillBall1.advance(5.0, spacePhysics);
        assertEquals(initialPos, stillBall1.getPosition());
    }

    @Test
    public void testUpdatePosUpperRight(){
        Ball ball = upperRightBall();
        ball.advance(5.0, spacePhysics);
        assertEquals(ball.getPosition(),new Vect(15.0, 15.0));
    }

    @Test
    public void testUpdatePosRightBall(){
        Ball ball = rightBall();
        ball.advance(5.0, spacePhysics);
        assertEquals(ball.getPosition(), new Vect(15.0, 10.0));
    }

    @Test
    public void testUpdatePosUpBall(){
        Ball ball = upBall();
        ball.advance(5.0, spacePhysics);
        assertEquals(ball.getPosition(), new Vect(10.0, 15.0));
    }

    @Test
    public void testUpdatePosLowerLeftBall(){
        Ball ball = lowerLeftBall();
        ball.advance(5.0, spacePhysics);
        assertEquals(ball.getPosition(), new Vect(5.0, 5.0));
    }

    @Test
    public void testUpdatePosLeftBall(){
        Ball ball = leftBall();
        ball.advance(3.0, spacePhysics);
        assertEquals(ball.getPosition(), new Vect(7.0, 10.0));
    }

    @Test
    public void testUpdatePosNoTimePass(){
        Ball notTimeBall = new Ball(origin.getCenter(), right);
        assertEquals(notTimeBall.getPosition(), origin.getCenter());
    }

    @Test
    public void testUpdatePosDownBall(){
        Ball ball = downBall();
        ball.advance(6.0, spacePhysics);
        assertEquals(ball.getPosition(), new Vect(10.0, 4.0));
    }

    @Test
    public void testUpdatePosFastBall(){
        Ball ball = fastBall();
        ball.advance(0.01, spacePhysics);
        assertEquals(ball.getPosition(), new Vect(10.0, 12.0));
    }
    
    /*
     *-Tests for timeUntilCollision
     *          -with stationary other ball
     *          -with itself stationary
     *          -both stationary
     *          -with both this and other ball moving away
     *          -with this moving towards and other ball moving away
     *          -with this moving away and other ball moving towards
     *          -with this and other ball moving towards each other
     */
    static final double EPSILON = 0.000001;
    @Test
    public void testTimeUntilCollisionOtherStationary(){
        Ball movingBall = new Ball(new Vect(5.0, 0.0), new Vect(-4.0, 0.0));
        assertEquals(1.125, movingBall.timeUntilCollision(stillBall()), EPSILON);
    }

    @Test
    public void testTimeUntilCollisionItselfStationary(){
        Ball stillBall1 = new Ball(new Vect(10.0, 10.0), new Vect(0.0, 0.0));
        Ball movingBall = new Ball(new Vect(15.0, 10.0), new Vect(-4.0, 0.0));
        assertEquals(1.125, stillBall1.timeUntilCollision(movingBall), EPSILON);
    }

    @Test
    public void testTimeUntilCollisionBothStationary(){
        Ball sittingBall = new Ball(new Vect(5.0, 0.0), new Vect(0.0, 0.0));
        assertEquals(Double.POSITIVE_INFINITY, stillBall().timeUntilCollision(sittingBall), EPSILON);    }

    @Test
    public void testTimeUntilCollisionMovingAway(){
        assertEquals(Double.POSITIVE_INFINITY, rightBall().timeUntilCollision(leftBall()), EPSILON);
    }

    @Test
    public void testTimeUntilCollisionOtherMovingAway(){
        Ball preyBall = new Ball(new Vect(5.0, 0.0), new Vect(5.0, 0.0));
        Ball predBall = new Ball(new Vect(0.0, 0.0), new Vect(10.0, 0.0));
        assertEquals(0.9, predBall.timeUntilCollision(preyBall), EPSILON);
    }

    @Test
    public void testTimeUntilCollisionThisMovingAway(){
        Ball preyBall = new Ball(new Vect(5.0, 0.0), new Vect(5.0, 0.0));
        Ball predBall = new Ball(new Vect(0.0, 0.0), new Vect(10.0, 0.0));
        assertEquals(0.9, preyBall.timeUntilCollision(predBall), EPSILON);
    }

    @Test
    public void testTimeUntilCollisionMovingTowards(){
        Ball leftBall1 = new Ball(new Vect(1.0, 0.0), new Vect(1.0, 0.0));
        Ball rightBall1 = new Ball(new Vect(5.0, 0.0), new Vect(-2.0, 0.0));
        assertEquals(1.1666666666666667, leftBall1.timeUntilCollision(rightBall1), EPSILON);
    }
    
    /*
     *      -Tests for collision
     *          -with stationary other ball
     *          -with itself stationary
     *          -with both balls moving at different velocities, direct hit
     *          -with both balls moving at same velocities, direct hit
     *          -with both balls moving at different velocities, slanting hit
     *          -with both balls moving at same velocities, slanting hit
     */
    @Test
    public void testCollisionOtherStationary(){
        Ball movingBall = new Ball(new Vect(10.0, 0.0), new Vect(-5.0, 0.0));
        Ball hitBall = new Ball(new Vect(9.0, 0.0), new Vect(0.0, 0.0));
        movingBall.emitCollisionTrigger(hitBall, spacePhysics);
        assertEquals(hitBall.getVelocity(), new Vect(-5.0, 0.0));
    }
    @Test
    public void testCollisionItselfStationary(){
        Ball movingBall = new Ball(new Vect(10.0, 0.0), new Vect(-5.0, 0.0));
        Ball hitBall = new Ball(new Vect(9.0, 0.0), new Vect(0.0, 0.0));
        hitBall.emitCollisionTrigger(movingBall, spacePhysics);
        assertEquals(movingBall.getVelocity(), new Vect(0.0, 0.0));
    }
    @Test
    public void testCollisionBothMovingDiffDirect(){
        Ball ball1 = new Ball(new Vect(10.0, 0.0), new Vect(-5.0, 0.0));
        Ball ball2 = new Ball(new Vect(9.0, 0.0), new Vect(3.0, 0.0));
        ball1.emitCollisionTrigger(ball2, spacePhysics);
        assertEquals(ball2.getVelocity(), new Vect(-5.0, 0.0));
    }

    @Test
    public void testCollisionBothMovingSameDirect(){
        Ball ball1 = new Ball(new Vect(10.0, 0.0), new Vect(-5.0, 0.0));
        Ball ball2 = new Ball(new Vect(9.0, 0.0), new Vect(5.0, 0.0));
        ball1.emitCollisionTrigger(ball2, spacePhysics);
        assertEquals(ball2.getVelocity(), new Vect(-5.0, 0.0));
    }
    @Test
    public void testCollisionBothMovingSameSlant(){
        Ball ball1 = new Ball(new Vect(10.0, 0.0), new Vect(-5.0, 1.0));
        Ball ball2 = new Ball(new Vect(9.0, 0.0), new Vect(5.0, 1.0));
        ball1.emitCollisionTrigger(ball2, spacePhysics);
        assertEquals(ball2.getVelocity(), new Vect(-5.0, 1.0));
    }
    @Test
    public void testCollisionBothMovingDiffSlant(){
        Ball ball1 = new Ball(new Vect(10.0, 0.0), new Vect(-5.0, 1.0));
        Ball ball2 = new Ball(new Vect(9.0, 0.0), new Vect(3.0, 1.0));
        ball1.emitCollisionTrigger(ball2, spacePhysics);
        assertEquals(ball2.getVelocity(), new Vect(-5.0, 1.0));

    }


}
