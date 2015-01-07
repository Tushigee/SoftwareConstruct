package pingball.game.gameobjects;

import java.awt.Graphics2D;
import java.util.Map;

import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;

/**
 *  An abstract data type for physical objects in the game, including
 *    balls, walls, bumpers, flippers, etc.
 *  Includes methods for detecting impending collisions and rendering
 *    the object to textual output. 
 */
public interface GameObject {
    /**
     * Renders the representation of this object on a map from points to
     *   strings.
     * Pre-conditions:  None
     * Post-conditions:  textGraphics will be changed and include mappings
     *   from points to the pieces of this object's string representation.
     *   If there were already strings at these points in the map, they will
     *   be overwritten.
     * @param textGraphics the map from points in the textual output to the
     *   strings which will be printed at that location
     */
    public void renderText(Map<Point<Integer>, String> textGraphics);
    
    /**
     * Draws this object representation on current JPanel that shows pingball game
     * @param g Graphics of JPanel that this object exists
     * @param L represents number of pixels per 'L' dimension of board
     */
    public void draw(Graphics2D g, int L);
    /**
     * timeUntilCollision:
     * 
     * @param gameBall
     *            instance of ball to check to see how long until it collides with this GameObject 
     * @return returns double representing the time in seconds until gameBall
     *         collides with this GameObject, otherwise returns
     *         Double.POSITIVE_INFINITY if the gameBall isn't going to collide
     *         with this object at this point in time
     */
    public double timeUntilCollision(Ball gameBall);
    
    /**
     * Subscribes a TriggerListener to the event emitter.
     * 
     * Pre-condition:  None
     * Post-condition:  whenever the emitTrigger() event is called
     *   from then on, the trigger() method of listener will be called.
     * @param listener the event listener which will be subscribed to
     *   the event
     */
    void addSpecialActionTriggerListener(TriggerListener listener);
    
    /**
     * Notifies all subscribed trigger listeners when called.
     *   Depending on the object, this might occur when a ball collides
     *   when an object 
     * For example, two subscribed trigger listeners might reflect the ball 
     *   and move it to another player's board.
     *   
     * @param dispatcher the ball which triggered the event
     * @param config the physics configuration information for the simulation
     */
    void emitSpecialActionTrigger(Ball dispatcher, PhysicsConfiguration config);
    
    /**
     * Subscribes a TriggerListener to the event emitter which is fired
     *   upon collision.
     * 
     * Pre-condition:  None
     * Post-condition:  whenever the emitTrigger() event is called
     *   from then on, the trigger() method of listener will be called.
     * @param listener the event listener which will be subscribed to
     *   the event
     */
    void addCollisionTriggerListener(TriggerListener listener);
    
    /**
     * Notifies all subscribed trigger listeners when called.
     *   This occurs when a ball collides with an object.
     * For example, two subscribed trigger listeners might reflect the ball 
     *   and move it to another player's board.
     *   
     * @param dispatcher the ball which triggered the event
     * @param config the physics configuration information for the simulation
     */
    void emitCollisionTrigger(Ball dispatcher, PhysicsConfiguration config);
    
    /**
     * Checks whether or not physics is enabled for the game object.
     *   If physics is not enabled, the object will not be advanced,
     *   and collisions will not be registered for the object.
     * @return whether or not physics is enabled for the object
     */
    boolean isPhysicsEnabled();
    
    /**
     * Sets whether or not physics is enabled for the game object.
     *    If physics is not enabled, the object will not be advanced,
     *    and collisions will not be registered for the object.
     * @param physicsEnabled whether or not physics is enabled for the
     *    object
     */
    void setPhysicsEnabled(boolean physicsEnabled);
    

}
