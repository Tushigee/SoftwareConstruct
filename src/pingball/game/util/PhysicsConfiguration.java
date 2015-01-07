package pingball.game.util;

import physics.Vect;

/**
 * A class for managing globals associated with the physics engine.
 * 
 * This class is immutable
 */
public class PhysicsConfiguration {

    /**
     * The acceleration due to gravity in L/s^2
     */
    private final Vect accelerationDueToGravity; // RI: can't be null
    
    /**
     * The velocity independent coefficient of friction
     */
    private final double kineticFrictionCoefficient; // RI: can't be less than
                                                     // zero and can't be null
    /**
     * The velocity dependent coefficient of friction
     */
    private final double velocityDependentFrictionCoefficient; // RI: can't be
                                                               // less than zero
                                                               // and can't be
                                                               // null

    /**
     * Sets the configuration variables
     * 
     * @param accelerationDueToGravity
     *            the acceleration due to gravity as a vector in L/sec^2:
     * @param kineticFrictionCoefficient
     *            the coefficient of kinetic friction
     * @param velocityDependentFrictionCoefficient
     *            the velocity-dependent coefficient of friction in sec/L
     */
    public PhysicsConfiguration(Vect accelerationDueToGravity,
            double kineticFrictionCoefficient,
            double velocityDependentFrictionCoefficient) {
        this.accelerationDueToGravity = accelerationDueToGravity;
        this.kineticFrictionCoefficient = kineticFrictionCoefficient;
        this.velocityDependentFrictionCoefficient = velocityDependentFrictionCoefficient;
        checkRep();
    }

    /**
     * Checks to see if the rep invariants as stated above are maintained.
     */
    private void checkRep() {
        assert this.accelerationDueToGravity != null;
        assert this.getAccelerationDueToGravity() != null;
        assert this.getKineticFrictionCoefficient() >= 0;
        assert this.getVelocityDependentFrictionCoefficient() >= 0;
    }

    /**
     * @return the acceleration due to gravity as a vector in L/sec^2
     */
    public Vect getAccelerationDueToGravity() {
        return accelerationDueToGravity;
    }

    /**
     * @return the coefficient of kinetic friction
     */
    public double getKineticFrictionCoefficient() {
        return kineticFrictionCoefficient;
    }

    /**
     * @return the velocity-dependent coefficient of friction in sec/L
     */
    public double getVelocityDependentFrictionCoefficient() {
        return velocityDependentFrictionCoefficient;
    }
}
