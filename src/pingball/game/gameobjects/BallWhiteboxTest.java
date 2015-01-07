package pingball.game.gameobjects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import physics.Vect;
import pingball.game.util.PhysicsConfiguration;

/**
 * Ball tests for methods which are only package-accessible  
 */
public class BallWhiteboxTest {
    
    /**
     * @see pingball.game.gameobjects.tests.BallTest
     * 
     * partitions for applyFriction
     *  - 0 speed
     *  - 1 millisecond w/ regular velocity
     *  - more than 1 millisecond w/ regular velocity
     */
    
    @Test
    public void testApplyFrictionZeroSpeed(){
        PhysicsConfiguration DEFAULT_CONFIG = new PhysicsConfiguration(new Vect(0, 25), 0.025, 0.0025);
        double secondsPassed = 10;
        Vect goodPosition = new Vect(10,10);
        Vect zeroVelocity = new Vect(0,0);
        Ball ball = new Ball(goodPosition, zeroVelocity);
        Vect dampedVelocity = ball.applyFriction(ball.getVelocity(), secondsPassed, DEFAULT_CONFIG);
        assertEquals(new Vect(0,0) , dampedVelocity);

    }

    @Test
    public void testApplyFrictionOneMillisecondRegularVelocity(){
        PhysicsConfiguration DEFAULT_CONFIG = new PhysicsConfiguration(new Vect(0, 25), 0.025, 0.0025);
        double secondsPassed = 1;
        Vect goodPosition = new Vect(10,10);
        Vect goodVelocity = new Vect(1,1);
        Ball ball = new Ball(goodPosition, goodVelocity);
        Vect dampedVelocity = ball.applyFriction(ball.getVelocity(), secondsPassed, DEFAULT_CONFIG);
        double velXOrY = .975 - 0.0025 * Math.sqrt(2.0);
        assertEquals(new Vect(velXOrY, velXOrY) , dampedVelocity);

    }

    @Test
    public void testApplyFrictionTwentyMillisecondsRegularVelocity(){
        PhysicsConfiguration DEFAULT_CONFIG = new PhysicsConfiguration(new Vect(0, 25), 0.025, 0.0025);
        double secondsPassed = 20;
        Vect goodPosition = new Vect(10,10);
        Vect goodVelocity = new Vect(1,1);
        Ball ball = new Ball(goodPosition, goodVelocity);
        Vect dampedVelocity = ball.applyFriction(ball.getVelocity(), secondsPassed, DEFAULT_CONFIG);
        double velXOrY = .5 - 0.0025 * 20 * Math.sqrt(2.0);
        assertEquals(new Vect(velXOrY, velXOrY) , dampedVelocity);

    }
}
