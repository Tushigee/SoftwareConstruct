package pingball.game.gameobjects.bumpers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import physics.Circle;
import physics.Geometry;
import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;
import static org.junit.Assert.*;

/**
 * 
 * PingBall gadget that represents a stationary circle on the board. It has a
 * reflection coefficient of 1.0, and triggers the flippers upon a collision with
 * a ball.
 * 
 * THIS CLASS IS MUTABLE
 *
 */
public class CircleBumper implements Bumper {

    /**
     * The circle representing the circle bumper for
     *   the physics engine
     */
    private final Circle circle; // RI: the bounds of the circle must be within
                                 // the bounds of the playing area of the board
    /**
     * The upper-left corner of the circle bumper
     */
    private final Vect northwestCorner; 
    
    /**
     * The board the circle bumper is contained in
     */
    private final Board board; // RI: the board must refer to the board the
                               // object is located in 
    // AF: represents a stationary circle bumper gadget with postion Vect
    // "northwestCorner" and reference "board" to the board that this circle
    // bumper is on.
    
    /**
     * The textual representation of the circle bumper
     */
    private static final String CIRCLEBUMPER_REP = "O";
    
    /**
     * The coefficient of reflection for the circle bumper
     */
    private static final double REFLECTION_COEFFICIENT = 1.0;
    
    /**
     * The list of event listeners which listen for collisions
     */
    private List<TriggerListener> collisionTriggerListeners = new ArrayList<TriggerListener>();
    
    /**
     * The list of event listeners which listen for special actions
     */
    private List<TriggerListener> specialActionTriggerListeners = new ArrayList<TriggerListener>();

    /**
     * Creates a new CircleBumper instance with the center of it at
     *public class CircleBumper implements Bumper {
     * @param board
     *            : the instance of board that this CircleBumper instance is
     *            going to be placed on
     * @param northwestCorner
     *            : a position vector representing the northwest corner of the
     *            LxL bounding box that contains this instance of CircleBumper
     *            is going to be placed. The CircleBumper must entirely fit
     *            within the bounds of the board instance that is passed in.
     * return: a new CircleBumper 
     */
    public CircleBumper(Board board, Vect northwestCorner) {
        this.northwestCorner = northwestCorner;
        final double circleX = this.northwestCorner.x() + .5;
        final double circleY = this.northwestCorner.y() + .5;
        this.circle = new Circle(circleX, circleY, .5);
        this.board = board;
        this.addCollisionTriggerListener(new CircleBumperTriggerListener());
        checkRep();
    }

    /**
     * Checks to see if the representation invariants as stated above are
     * maintained.
     */
    public void checkRep() {
        assertNotNull(this.board);
        assertNotNull(this.northwestCorner);
        assert (this.circle.getCenter().x() >= 0.5 && this.circle.getCenter().x() <= this.board.getWidth() - 0.5);
        assert (this.circle.getCenter().y() >= 0.5 && this.circle.getCenter().y() <= this.board.getHeight() - 0.5);
    }

    /**
    * Renders the representation of this object on a map from points to
    * strings. Pre-conditions: None Post-conditions: textGraphics will be
    * changed and include mappings from points to the pieces of this object's
    * string representation. If there were already strings at these points in
    * the map, they will be overwritten.
    * 
    * @param textGraphics
    *            the map from points in the textual output to the strings which
    *            will be printed at that location
    */
    @Override
    public void renderText(Map<Point<Integer>, String> textGraphics) {
        int roundX = (int) Math.floor(this.northwestCorner.x());
        int roundY = (int) Math.floor(this.northwestCorner.y());
        Point<Integer> myPoint = new Point<Integer>(roundX, roundY);
        textGraphics.put(myPoint, CIRCLEBUMPER_REP);
    }

    /**
    * timeUntilCollision:
    * 
    * @param gameBall
    *            instance of ball to check to see how long until it collides
    *            with this GameObject
    * @return returns double representing the time in seconds until gameBall
    *         collides with this GameObject, otherwise returns
    *         Double.POSITIVE_INFINITY if the gameBall isn't going to collide
    *         with this object at this point in time
    */
    @Override
    public double timeUntilCollision(Ball gameBall) {
        if (! this.isPhysicsEnabled()) {
            return Double.POSITIVE_INFINITY;
        }
        return physics.Geometry.timeUntilCircleCollision(this.getCircle(),
                gameBall.getCircle(), gameBall.getVelocity());
    }
    
    /** 
     * @return the representation of the cirlce for the physics engine
     */
    private Circle getCircle() {
        return this.circle;
    }



    
    /**
     * Object-specific event handler for an CircleBumper object.  For the event when a ball
     *   collides with this bumper object, will trigger a response when a trigger is emitted
     */
    private class CircleBumperTriggerListener implements TriggerListener {

        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            if (! CircleBumper.this.isPhysicsEnabled()) {
                return;
            }
            CircleBumper.this.reflectBall(dispatcher);

        }

    }

    
    /**
    * Subscribes a TriggerListener to the event emitter.
    * 
    * Pre-condition: None Post-condition: whenever the emitTrigger() event is
    * called from then on, the trigger() method of listener will be called.
    * 
    * @param listener
    *            the event listener which will be subscribed to the event
    */
    @Override
    public void addSpecialActionTriggerListener(TriggerListener listener) {
        specialActionTriggerListeners.add(listener);

    }

    
    /**
    * Notifies all subscribed trigger listeners when called. Depending on the
    * object, this might occur when a ball collides when an object For example,
    * two subscribed trigger listeners might reflect the ball and move it to
    * another player's board.
    * 
    * @param dispatcher
    *            the ball which triggered the event
    * @param config
    *            the physics configuration information for the simulation
    */
    @Override
    public void emitSpecialActionTrigger(Ball dispatcher,
            PhysicsConfiguration config) {
        for (TriggerListener listener : specialActionTriggerListeners) {
            listener.trigger(dispatcher, config);
        }
    }

    
    /**
    * Subscribes a TriggerListener to the event emitter which is fired upon
    * collision.
    * 
    * Pre-condition: None Post-condition: whenever the emitTrigger() event is
    * called from then on, the trigger() method of listener will be called.
    * 
    * @param listener
    *            the event listener which will be subscribed to the event
    */
    @Override
    public void addCollisionTriggerListener(TriggerListener listener) {
        collisionTriggerListeners.add(listener);
    }

    
    /**
    * Notifies all subscribed trigger listeners when called. This occurs when a
    * ball collides with an object. For example, two subscribed trigger
    * listeners might reflect the ball and move it to another player's board.
    * 
    * @param dispatcher
    *            the ball which triggered the event
    * @param config
    *            the physics configuration information for the simulation
    */
    @Override
    public void emitCollisionTrigger(Ball dispatcher,
            PhysicsConfiguration config) {
        if (! this.isPhysicsEnabled()) {
            return;
        }
        for (TriggerListener listener : collisionTriggerListeners) {
            listener.trigger(dispatcher, config);
        }

    }

    /**
     * reflects the ball after a collision with this instance of CircleBumper
     * 
     * @param gameBall
     *            ball instance to be reflected
     */
    public void reflectBall(Ball gameBall) {
        gameBall.setVelocity(Geometry.reflectCircle(this.getCircle()
                .getCenter(), gameBall.getCircle().getCenter(), gameBall
                .getVelocity(), REFLECTION_COEFFICIENT));
    }
    
    /**
     * whether or not physics are enabled for the circle bumper
     *   (if not, collisions are ignored)
     */
    private boolean physicsEnabled = true;
    
    @Override
    public boolean isPhysicsEnabled() {
        return physicsEnabled;
    }

    @Override
    public void setPhysicsEnabled(boolean physicsEnabled) {
        this.physicsEnabled = physicsEnabled;
    }

    @Override
    public void draw(Graphics2D g, int L) {
        g.setPaint(Color.ORANGE);
        double radius  = this.circle.getRadius();
        Shape theCircle = new Ellipse2D.Double(L*(this.circle.getCenter().x() - this.circle.getRadius()), L*(this.circle.getCenter().y() - radius), 2.0 * radius * L, 2.0 * radius * L);
        g.draw(theCircle);
        //g.fill(theCircle);    
    }

}
