package pingball.game.gameobjects;

import physics.Vect;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;


/**
 * Moveable GameObject represents any objects in that game that are able to move and mutate
 *    throughout the pingBall game
 * 
 * 
 * CLASSES WHICH IMPLEMENT THIS INTERFACE ARE MUTABLE
 *
 */
public interface MoveableGameObject extends GameObject{
    
    /**
     * Creates a new 2Lx2L flipper with its northeast corner at northeastCorner.
     * Pre-condition:  Fits in the 2Lx2L bounding box extending down and to the right
     * @param northeastCorner the northeast corner of the 2Lx2L bounding box
     * @param orientationLeft whether the flipper is a left (or else a right) flipper
     *   - If left:  starts on left side of bounding box and sweeps right
     *   - If right:  starts on right side of bounding box and sweeps left
     * @param orientation the orientation of the flipper (0, 90, 180, or 270 degrees) 
     * @return a new Flipper
     */
    public static MoveableGameObject flipper(Point<Integer> northeastCorner, boolean orientationLeft, int orientation){
        Flipper.FlipperOrientation orientationState = Flipper.FlipperOrientation.RIGHT;
        if (orientationLeft) {
            orientationState = Flipper.FlipperOrientation.LEFT;
        }
        return new Flipper(northeastCorner, orientationState, orientation);
    }
    
    /**
     * Creates a new ball with the given position, radius
     *   initial velocity, and mass
     * @param position the position of the ball as a vector (in L)
     * @param initialVelocity the initial velocity of the ball as a vector
     *   (in L/sec)
     * @return a new Ball
     */
    public static MoveableGameObject ball(Vect position, Vect initialVelocity){
        return new Ball(position, initialVelocity);
    }
    
    
    /**
     * requires: nothing 
     * @return returns true if this MoveableGameObject is an instance of ball, false otherwise
     */
    public boolean isBall();
    
    
   /**
    * This method updates the position and velocity of this MoveableGameObject instance in the simulation 
    * @param seconds number of seconds to advance 
    * @param config the configuration information for the physics engine
    */
    public void advance(Double seconds, PhysicsConfiguration config);
    
    

}
