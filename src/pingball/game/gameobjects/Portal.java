package pingball.game.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import physics.Circle;
import physics.Vect;
import pingball.game.Board;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;
import pingball.networking.BallRoutingMessage;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import static org.junit.Assert.*;

/**
 * 
 * PingBall gadget that represents a stationary portal on the board.
 * 
 * THIS CLASS IS MUTABLE
 *
 */
public class Portal implements StationaryGameObject {

    /**
     * The circle representing the portal for the physics
     *   engine
     */
    private final Circle circle; // RI: the bounds of the circle must be within
                                 // the bounds of the playing area of the board
    /**
     * The upper-left corner of the portal
     */
    private final Vect northwestCorner; 
    
    /**
     * The board which in which the portal is contained
     */
    private final Board board; // RI: the board must refer to the board the
                               // object is located in 
    // AF: represents a stationary portal gadget with postion Vect
    // "northwestCorner" and reference "board" to the board that this circle
    // bumper is on.
    
    /**
     * The textual representation of a portal
     */
    private static final String PORTAL_REP = "o";
    
    /**
     * The event listeners which are triggered when an object collides
     *   with the portal
     */
    private List<TriggerListener> collisionTriggerListeners = new ArrayList<TriggerListener>();
    
    /**
     * The event listeners which are triggered when the special action
     *   of the portal is triggered (ie, emitting a ball)
     */
    private List<TriggerListener> specialActionTriggerListeners = new ArrayList<TriggerListener>();
    
    /**
     * The source of the ball (this portal)
     */
    private final BoardObjectInContext ballSource;
    
    /**
     * The destination of the ball (output portal)
     */
    private final BoardObjectInContext ballDestination;

    /**
     * Whether or not physics are enabled for this portal.
     * If false, then collisions will not be registered
     */
    private boolean physicsEnabled = true;

    /**
     * Creates a new portal based on the board the portal is on,
     *   its position, its name, the destination's name, and the destination
     *   board's name
     * @param board the board the portal is on
     * @param northwestCorner the location of the portal
     * @param gadgetName the name of the portal
     * @param destGadget the name of the destination gadget of the portal
     * @param destBoard the name of the destination board of the portal
     */
    public Portal(Board board, Vect northwestCorner, String gadgetName, String destGadget, String destBoard) {
        this.northwestCorner = northwestCorner;
        final double circleX = this.northwestCorner.x() + .5;
        final double circleY = this.northwestCorner.y() + .5;
        this.circle = new Circle(circleX, circleY, .5);
        this.board = board;
        this.ballSource = new BoardObjectInContext(this.board.getName(), BoardObjectType.NAMED,
                gadgetName, Wall.WallPosition.NONE);
        this.ballDestination = new BoardObjectInContext(destBoard, BoardObjectType.NAMED,
                destGadget, Wall.WallPosition.NONE);
        this.addCollisionTriggerListener(new PortalTriggerListener());
        this.addSpecialActionTriggerListener(new PortalSpecialTriggerListener());
        checkRep();
    }
    
    /**
     * Creates a localized portal based on the board the portal is on,
     *   its position, its name, the destination's name.
     * The portal is said to be localized, because it only sends balls
     *   to other portals on the board the portal is on.
     * @param board the board the portal is on
     * @param northwestCorner the location of the portal
     * @param gadgetName the name of the portal
     * @param destGadget the name of the destination gadget of the portal
     */
    public Portal(Board board, Vect northwestCorner, String gadgetName, String destGadget) {
        this(board, northwestCorner, gadgetName, destGadget, board.getName());
    }

    /**
     * Checks to see if the representation invariants as stated above are
     * maintained.
     */
    public void checkRep() {
        assertNotNull(this.board);
        assertNotNull(this.northwestCorner);
        assertNotNull(this.ballDestination);
        assertNotNull(this.ballSource);
        assert (this.circle.getCenter().x() >= 0.5 && this.circle.getCenter().x() <= this.board.getWidth() - 0.5);
        assert (this.circle.getCenter().y() >= 0.5 && this.circle.getCenter().y() <= this.board.getHeight() - 0.5);
    }
    
    /**
     * Creates a ball routing message for the ball and sends it to the board
     *   to process and send to the destination portal (potentially through
     *   the server)
     * @param ball
     */
    private void sendBall(Ball ball) {
        BallRoutingMessage brm = new BallRoutingMessage(ball, this.ballSource, this.ballDestination);
        this.board.sendOutgoingBRM(brm);
    }
    
    /**
     * Emits a ball in the direction it was headed
     * @param ball the ball to release from the portal
     */
    private void emitBall(Ball ball) {
        Vect unitDirectionOfBall = ball.getVelocity().unitSize();
        Vect offset = unitDirectionOfBall.times(this.getCircle().getRadius() + ball.getRadius());
        ball.setPosition(this.getCircle().getCenter().plus(offset));
    }

    @Override
    public void renderText(Map<Point<Integer>, String> textGraphics) {
        int roundX = (int) Math.floor(this.northwestCorner.x());
        int roundY = (int) Math.floor(this.northwestCorner.y());
        Point<Integer> myPoint = new Point<Integer>(roundX, roundY);
        textGraphics.put(myPoint, PORTAL_REP);
    }

    @Override
    public double timeUntilCollision(Ball gameBall) {
        if (! this.isPhysicsEnabled()) {
            return Double.POSITIVE_INFINITY;
        }
        return physics.Geometry.timeUntilCircleCollision(this.getCircle(),
                gameBall.getCircle(), gameBall.getVelocity());
    }
    
    /**
     * @return the physics engine's representation
     *   of the portal:  a circle.
     *   Useful in calculations concerning time until collision
     */
    private Circle getCircle() {
        return this.circle;
    }

    
    /**
     * Object-specific event handler for a Portal object.  For the event when a ball
     *   collides with this portal object, will trigger a response when a trigger is emitted
     */
    private class PortalTriggerListener implements TriggerListener {

        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            if (! Portal.this.isPhysicsEnabled()) {
                return;
            }
            Portal.this.sendBall(dispatcher);
        }

    }
    
    /**
     * Object-specific event handler for a Portal object.  For the event when a ball
     *   is to be emitted from this portal
     */
    private class PortalSpecialTriggerListener implements TriggerListener {

        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            Portal.this.emitBall(dispatcher);
        }

    }

    @Override
    public void addSpecialActionTriggerListener(TriggerListener listener) {
        specialActionTriggerListeners.add(listener);

    }
    
    @Override
    public void emitSpecialActionTrigger(Ball dispatcher,
            PhysicsConfiguration config) {
        for (TriggerListener listener : specialActionTriggerListeners) {
            listener.trigger(dispatcher, config);
        }
    }

    @Override
    public void addCollisionTriggerListener(TriggerListener listener) {
        collisionTriggerListeners.add(listener);
    }

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
        g.setPaint(Color.yellow);
        double radius  = this.circle.getRadius();
        Shape theCircle = new Ellipse2D.Double(L*(this.circle.getCenter().x() - this.circle.getRadius()), L*(this.circle.getCenter().y() - radius), 2.0 * radius * L, 2.0 * radius * L);
        g.draw(theCircle);
        g.setPaint(Color.blue);
        g.fill(theCircle);
    }

}
