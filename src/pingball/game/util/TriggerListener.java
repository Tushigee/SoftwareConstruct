package pingball.game.util;

import pingball.game.gameobjects.Ball;

/**
 *  An event listener which is called when a TriggerEmitter
 *    emitts an event.
 *  This usually will occur when a ball collides with an object,
 *    setting off a trigger which will execute the trigger method
 *    specified in the TriggerListener instance.
 */
public interface TriggerListener {
    /**
     * The callback to be executed when the trigger is emitted
     * @param dispatcher the ball which dispatched the event
     *   (usually the ball colliding with the object)
     * @param config the physics configuration information for the simulation
     */
    void trigger(Ball dispatcher, PhysicsConfiguration config);
}
