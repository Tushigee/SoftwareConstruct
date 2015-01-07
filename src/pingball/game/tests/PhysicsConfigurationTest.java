package pingball.game.tests;

import org.junit.Test;

import physics.Vect;
import pingball.game.util.PhysicsConfiguration;

public class PhysicsConfigurationTest {

    final Vect realAcceleration = new Vect(0, 1);
    final Vect nullAcceleration = null;
    final double realKineticFrictionCoefficient = .5;
    final double badKineticFrictionCoefficient = -1.0;
    final double realVelocityDependentFrictionCoefficient = .8;
    final double badVelocityDependentFrictionCoefficient = -1.5;
    

    @Test
    public void checkRepAllValidInputs() {
        new PhysicsConfiguration(realAcceleration,
                realKineticFrictionCoefficient,
                realVelocityDependentFrictionCoefficient);
    }
    
    @Test(expected = AssertionError.class)
    public void checkRepNullAcceleration() {
        new PhysicsConfiguration(nullAcceleration,
                realKineticFrictionCoefficient,
                realVelocityDependentFrictionCoefficient);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void checkRepBadKineticFrictionCoefficient() {
        new PhysicsConfiguration(realAcceleration,
                badKineticFrictionCoefficient,
                realVelocityDependentFrictionCoefficient);
    }
    
    /**
     * @category no_didit
     */
    @Test(expected = AssertionError.class)
    public void checkRepBadVelocityDependentFrictionCoefficient() {
        new PhysicsConfiguration(realAcceleration,
                realKineticFrictionCoefficient,
                badVelocityDependentFrictionCoefficient);
    }
    
}
