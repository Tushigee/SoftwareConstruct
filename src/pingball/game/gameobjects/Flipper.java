package pingball.game.gameobjects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;

/**
 * A flipper is a rotation line in a 2Lx2L box which rotates
 * when it is triggered.
 * 
 * It is triggered when the ball hits it
 * 
 * Size and shape: A generally-rectangular rotating shape with bounding box of size 2L × 2L
 * 
 * Orientation: For a left flipper, the default orientation (0 degrees) places the flipper’s pivot point in the northwest corner. For a right flipper, the default orientation puts the pivot point in the northeast corner.
 * 
 * Coefficient of reflection: 0.95 (but see below)
 * 
 * Trigger: generated whenever the ball hits it
 * 
 * Action: rotates 90 degrees, as described below
 * 
 * THIS CLASS IS MUTABLE
 *
 */
public class Flipper implements MoveableGameObject{

    // AF:  A Flipper object, which when triggered, rotates between
    //   vertical or horizontal positions.  It can either be a left
    //   flipper or a right flipper

    // RI:  The anchor point is not the end point.
    //   The anchor point's y value is always greater than or equal to
    //     the end point's y value
    
    /**
     * Checks that the RI is upheld
     */
    void checkRep() {
        assert anchorPoint != null;
        assert northwestCorner != null;
        assert endPoint != null;
        assert orientation != null;
        assert currentState != null;
        assert ! anchorPoint.equals(endPoint);
        final double flipperEpsilon = 0.1;
        assert (anchorPoint.y() - endPoint.y()) <= flipperEpsilon;
    }

    /**
     *  The flipper's orientation (left or right)
     */
    public static enum FlipperOrientation {
        /**
         * A left flipper starts on the left side of the box
         *   and sweeps counter-clockwise
         */
        LEFT,
        /**
         * A right flipper starts on the right side of the box
         *   and sweeps clock-wise
         */
        RIGHT;
    }

    /**
     * The states of a flipper
     * (whether it is in the up position,
     *   down position, transitioning to the up
     *   position, or transitioning to the down
     *   position)
     */
    public static enum FlipperState {
        /**
         * Flipper in the up position
         */
        UP,
        /**
         * Flipper in the down position
         */
        DOWN,
        /**
         * Flipper transitioning to the up position
         */
        ROTATING_UP,
        /**
         * Flipper transitioning to the down position
         */
        ROTATING_DOWN
    }
    
    /**
     * The upper left-hand corner of the flipper
     */
    private final Point<Integer> northwestCorner;
    
    /**
     * The counter-clockwise rotation of the gadget from its default state.
     */
    private final Angle rotation;
    
    /**
     * The anchor (or pivot) point of the flipper arm
     */
    private Vect anchorPoint;
    
    /**
     * The moving end point of the flipper arm
     */
    private Vect endPoint;
    
    /**
     * The orientation of the flipper (LEFT or RIGHT)
     */
    private FlipperOrientation orientation;
    
    /**
     * The current state of the flipper (moving up, moving down,
     *   up, or down)
     */
    private FlipperState currentState;

    /**
     * The length of the flipper arm
     */
    private static final int FLIPPER_LENGTH = 2;
    
    /**
     * The radius of the end caps of the flippers
     */
    private static final double END_CAP_RADIUS = 0.0;
    
    /**
     * The magnitude of the angular velocity of the flipper
     */
    private static final double CCW_ANGULAR_VELOCITY_MAGNITUDE = 1080.0;
    
    /**
     * The width of the containing box of the flipper
     */
    private static final int WIDTH = 2;
    
    /**
     * The height of the containing box of the flipper
     */
    @SuppressWarnings("unused")
    private static final int HEIGHT = 2;
    
    /**
     * The coefficient of reflection for the flipper
     */
    private static final double REFLECTION_COEFF = 0.95;
    
    /**
     * A conversion constant:  the number of radians per degree
     */
    private static final double RADIANS_PER_DEGREE = Math.PI / 180.;


    /**
     * Creates a new 2Lx2L flipper with its northeast corner at northeastCorner.
     * Pre-condition:  Fits in the 2Lx2L bounding box extending down and to the right
     * @param northwestCorner the northeast corner of the 2Lx2L bounding box
     * @param orientation whether the flipper is a left or right flipper
     *   - If left:  starts on left side of bounding box and sweeps right
     *   - If right:  starts on right side of bounding box and sweeps left
     * @param startingState whether the flipper is starting up or down or rotating
     *   between the two
     * @param rotation 0|90|180|270:  
     */
    public Flipper(Point<Integer> northwestCorner, FlipperOrientation orientation, FlipperState startingState, int rotation) {
        if (rotation != 0 && rotation != 90 && rotation != 180 && rotation != 270) {
            throw new IllegalArgumentException("Illegal rotation.");
        }
        if (northwestCorner == null || orientation == null || startingState == null) {
            throw new IllegalArgumentException("A null value was used when creating a Flipper.");
        }
        this.northwestCorner = northwestCorner;
        this.orientation = orientation;
        this.currentState = startingState;
        this.rotation = new Angle(RADIANS_PER_DEGREE * rotation);
        this.setDefaultAnchorPoint();
        this.setDefaultEndPoint();
        this.addCollisionTriggerListener(new FlipperCollisionTriggerListener());
        this.addSpecialActionTriggerListener(new FlipperSpecialActionTriggerListener());
        checkRep();
    }
    
    /**
     * Creates a new 2Lx2L flipper with its northeast corner at northeastCorner.
     * Pre-condition:  Fits in the 2Lx2L bounding box extending down and to the right
     * @param northwestCorner the northeast corner of the 2Lx2L bounding box
     * @param orientation whether the flipper is a left or right flipper
     *   - If left:  starts on left side of bounding box and sweeps right
     *   - If right:  starts on right side of bounding box and sweeps left
     * @param rotation 0|90|180|270:  
     */
    public Flipper(Point<Integer> northwestCorner, FlipperOrientation orientation, int rotation) {
        this(northwestCorner, orientation, FlipperState.DOWN, rotation);
    }
    
    /**
     * @deprecated
     * Creates a new 2Lx2L flipper with its northeast corner at northeastCorner.
     * Pre-condition:  Fits in the 2Lx2L bounding box extending down and to the right
     * @param northwestCorner the northeast corner of the 2Lx2L bounding box
     * @param orientation whether the flipper is a left or right flipper
     *   - If left:  starts on left side of bounding box and sweeps right
     *   - If right:  starts on right side of bounding box and sweeps left
     * @param startingState whether the flipper is starting up or down or rotating
     *   between the two
     */
    public Flipper(Point<Integer> northwestCorner, FlipperOrientation orientation, FlipperState startingState) {
        this(northwestCorner, orientation, startingState, 0);
    }

    // BEGIN HELPER METHODS:
    /**
     * Finds the minimum of the arguments (doubles)
     * @param values a variable number of doubles
     * @return the minimum of the arguments
     */
    private static double min(double... values) {
        double currentMin = Double.NaN;
        boolean first = true;
        for (double value : values) {
            if (first || value < currentMin) {
                currentMin = value;
                first = false;
            }
        }
        assert currentMin != Double.NaN;
        return currentMin;
    }
    
    /**
     * @return the segment representing the current flipper,
     *   rotated according to rotation
     */
    private LineSegment getRotatedSegment() {
        Vect center = new Vect(this.northwestCorner.getX() + 1, this.northwestCorner.getY() + 1);
        Vect centerToAnchor = this.anchorPoint.minus(center);
        Vect centerToEnd = this.endPoint.minus(center);
        Vect rotatedCenterToAnchor = centerToAnchor.rotateBy(this.rotation);
        Vect rotatedCenterToEnd = centerToEnd.rotateBy(this.rotation);
        return new LineSegment(center.plus(rotatedCenterToAnchor), center.plus(rotatedCenterToEnd));
    }
    
    /**
     * Uses the state of the flipper to judge
     *   whether or not it is finished rotating
     * @return true if the flipper is finished rotating,
     *   false otherwise
     */
    private boolean isFinishedRotation() {
        switch(currentState) {
        case UP:
        case DOWN:
            return true;
        case ROTATING_UP:
            return endPoint.y() <= anchorPoint.y();
        case ROTATING_DOWN:
            if (orientation == FlipperOrientation.LEFT) {
                return endPoint.x() <= anchorPoint.x();
            }
            return endPoint.x() >= anchorPoint.x();
        default:
            throw new IllegalStateException("Entered an unknown flipper state.");
        }
    }
    
    /**
     * Sets the anchor point based on the flipper's location
     *   and orientation
     */
    private void setDefaultAnchorPoint() {
        double y = this.northwestCorner.getY();
        double x = this.northwestCorner.getX();
        if (this.orientation == FlipperOrientation.RIGHT) {
            x += WIDTH;
        }
        this.anchorPoint = new Vect(x, y);
        // NOTE:  checkRep isn't called, because this
        //  subroutine is only used in the constructor, and
        //  checkRep is called at the end of the constructor
    }
    
    /**
     * Sets the initial end point of the flipper based on its
     *   anchor point, length, orientation, and state
     */
    private void setDefaultEndPoint() {
        if (currentState == FlipperState.DOWN || currentState == FlipperState.ROTATING_UP) {
            int yOffset = -FLIPPER_LENGTH;
            endPoint = anchorPoint.minus(new Vect(0, yOffset));
        } else if (currentState == FlipperState.UP || currentState == FlipperState.ROTATING_DOWN) {
            int xOffset = 0;
            if (orientation == FlipperOrientation.LEFT) {
                xOffset = FLIPPER_LENGTH;
            } else {
                xOffset = -FLIPPER_LENGTH;
            }
            endPoint = anchorPoint.plus(new Vect(xOffset, 0));
        }
        // NOTE:  checkRep isn't called, because this
        //  subroutine is only used in the constructor, and
        //  checkRep is called at the end of the constructor
    }
    
    /**
     * Finish the rotation, set the state of the flipper,
     *   set the end point of the flipper (so as to avoid rounding
     *   error accumulated during rotation)
     */
    private void finishRotation() {
        if (currentState == FlipperState.ROTATING_UP) {
            currentState = FlipperState.UP;
            setDefaultEndPoint();
        } else if (currentState == FlipperState.ROTATING_DOWN) {
            currentState = FlipperState.DOWN;
            setDefaultEndPoint();
        }
        checkRep();
    }
    
    /**
     * Get the current counter-clockwise angular velocity
     *   (0 if not rotating).  Positive angular velocities
     *   mean counter-clockwise.
     * @return the current CCW angular velocity of the flipper
     */
    private double getCCWAngularVelocity() {
        if (currentState == FlipperState.UP || currentState == FlipperState.DOWN) {
            return 0.;
        }
        double ccwAngularVelocity = CCW_ANGULAR_VELOCITY_MAGNITUDE;
        if ((orientation == FlipperOrientation.LEFT && currentState == FlipperState.ROTATING_UP)
                || (orientation == FlipperOrientation.RIGHT && currentState == FlipperState.ROTATING_DOWN)) {
            ccwAngularVelocity *= -1.;
        }
        return ccwAngularVelocity;
    }
    
    /**
     * Based on the flipper's current state and position,
     *   get the ASCII character used to represent the flipper
     * @return the ASCII character which is to be used to
     *   represent the flipper
     */
    private char getRepresentativeCharacter() {
        Angle angleOfFlipper = this.getRotatedSegment().angle();
        double magnetudeSin = Math.abs(angleOfFlipper.sin());

        final double maxHorizontalCutoffSin = 0.5;
        final double minVerticalCutoffSin = Math.sqrt(3) / 2.;

        if (magnetudeSin < maxHorizontalCutoffSin) {
            return '-';
        }
        if (magnetudeSin > minVerticalCutoffSin) {
            return '|';
        }
        double tanOfFlipper = angleOfFlipper.tan();
        if (tanOfFlipper >= 0) {
            return '\\';
        }
        return '/';
    }
    
    /**
     * Get a list of boxes which are to be rendered with
     *   the flipper's representativeCharacter
     * @return the boxes of the ASCII grid occupied by the
     *   flipper
     */
    private List<Point<Integer>> getOccupiedBoxes() {
        LineSegment flipperSeg = this.getRotatedSegment();
        
        final double maxHorizontalCutoffSin = 0.5;
        final double minVerticalCutoffSin = Math.sqrt(3) / 2.;
        
        double magnetudeSin = Math.abs(flipperSeg.angle().sin());
        if (magnetudeSin < maxHorizontalCutoffSin) {
            // -
            int yOffset = 0;
            if (flipperSeg.p1().y() >= this.northwestCorner.getY() + 1) {
                yOffset = 1;
            }
            return Arrays.asList(new Point<Integer>(this.northwestCorner.getX(), this.northwestCorner.getY() + yOffset),
                    new Point<Integer>(this.northwestCorner.getX() + 1, this.northwestCorner.getY() + yOffset));
        }
        if (magnetudeSin > minVerticalCutoffSin) {
            // |
            int xOffset = 0;
            if (flipperSeg.p1().x() >= this.northwestCorner.getX() + 1) {
                xOffset = 1;
            }
            return Arrays.asList(new Point<Integer>(this.northwestCorner.getX() + xOffset, this.northwestCorner.getY()),
                    new Point<Integer>(this.northwestCorner.getX() + xOffset, this.northwestCorner.getY() + 1));
        }
        double tanOfFlipper = flipperSeg.angle().tan();
        if (tanOfFlipper >= 0) {
            // \\
            return Arrays.asList(this.northwestCorner,
                    new Point<Integer>(this.northwestCorner.getX() + 1, this.northwestCorner.getY() + 1));
        }
        // /
        return Arrays.asList(new Point<Integer>(this.northwestCorner.getX(), this.northwestCorner.getY() + 1),
                new Point<Integer>(this.northwestCorner.getX() + 1, this.northwestCorner.getY()));
    }
    // END HELPER METHODS
    
    // Begin observer methods:
    
    /**
     * @return the current state of the flipper
     *  (down, up, rotating down, or rotating up)
     */
    public FlipperState getState() {
        return currentState;
    }
    
    /**
     * @return the orientation of the flipper (left or right)
     */
    public FlipperOrientation getOrientation() {
        return orientation;
    }
    
    /**
     * @return get the angle the flipper has been rotated
     */
    public Angle getRotation() {
        return this.rotation;
    }
    
    // End observer methods

    /**
     * A list of TriggerListeners which are to be executed
     * when a special action occurs.
     * 
     * Currently, the flipper's special action is flipping
     */
    private List<TriggerListener> specialActionTriggerListeners = new ArrayList<TriggerListener>();

    /**
     * A list of TriggerListeners which are to be executed
     * when another object collides with the ball (but not
     * if the ball collides will another object).
     * 
     * Although this sounds confusing, the general idea is
     * that only one object can be the event dispatcher,
     * and the other object reacts to the event.
     */
    private List<TriggerListener> collisionTriggerListeners = new ArrayList<TriggerListener>();

    @Override
    public void addSpecialActionTriggerListener(TriggerListener listener) {
        specialActionTriggerListeners.add(listener);
    }

    @Override
    public void emitSpecialActionTrigger(Ball dispatcher, PhysicsConfiguration config) {
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

    /**
     *  A TriggerListener which is listens for the special action
     *  event and proceeds to cause the flipper to flip
     *  
     *  When ROTATING_UP, being triggered should make it go to ROTATING_DOWN.
     *  When ROTATING_DOWN, being triggered should make it go to ROTATING_UP.
     */
    private class FlipperSpecialActionTriggerListener implements TriggerListener {
        @Override
        public void trigger(pingball.game.gameobjects.Ball dispatcher,
                PhysicsConfiguration config) {
            switch (currentState) {
            case UP:
                currentState = FlipperState.ROTATING_DOWN;
                break;
            case DOWN:
                currentState = FlipperState.ROTATING_UP;
                break;
            case ROTATING_UP:
                currentState = FlipperState.ROTATING_DOWN;
                break;
            case ROTATING_DOWN:
                currentState = FlipperState.ROTATING_UP;
                break;
            default:
                throw new IllegalStateException("Entered an unknown flipper state.");
            }
            checkRep();
        }
    }

    /**
     *  A TriggerListener which listens for a collision event
     *  and reflects the ball depending both on the flipper's
     *  angular velocity and the ball's translational velocity
     */
    private class FlipperCollisionTriggerListener implements TriggerListener {
        @Override
        public void trigger(pingball.game.gameobjects.Ball dispatcher,
                PhysicsConfiguration config) {
            if (! Flipper.this.isPhysicsEnabled()) {
                return;
            }
            LineSegment flipperLineSegment = Flipper.this.getRotatedSegment();
            Circle endCapAnchor = new Circle(flipperLineSegment.p1(), END_CAP_RADIUS);
            Circle endCapEnd = new Circle(flipperLineSegment.p2(), END_CAP_RADIUS);
            double collisionTimeLine = Geometry.timeUntilRotatingWallCollision(
                    flipperLineSegment, Flipper.this.anchorPoint, 
                    Flipper.this.getCCWAngularVelocity()*RADIANS_PER_DEGREE, dispatcher.getCircle(), dispatcher.getVelocity());
            double collisionTimeCapAnchor = Geometry.timeUntilCircleCollision(endCapAnchor, 
                    dispatcher.getCircle(), dispatcher.getVelocity());
            double collisionTimeCapEnd = Geometry.timeUntilRotatingCircleCollision(endCapEnd, flipperLineSegment.p1(), Flipper.this.getCCWAngularVelocity()*RADIANS_PER_DEGREE, 
                    dispatcher.getCircle(), dispatcher.getVelocity());
            //double collisionTimeCapEnd = Geometry.timeUntilCircleCollision(endCapEnd, 
            //        dispatcher.getCircle(), dispatcher.getVelocity());
            if (collisionTimeCapAnchor < collisionTimeCapEnd && collisionTimeCapAnchor < collisionTimeLine) {
                // Collide with anchor cap
                Vect newBallVelocity = Geometry.reflectCircle(endCapAnchor.getCenter(), 
                        dispatcher.getCircle().getCenter(), dispatcher.getVelocity(), REFLECTION_COEFF);
                dispatcher.setVelocity(newBallVelocity);
                return;
            }
            if (collisionTimeCapEnd < collisionTimeLine) {
                // Collide with end cap
                //Vect newBallVelocity = Geometry.reflectCircle(endCapEnd.getCenter(), 
                //        dispatcher.getCircle().getCenter(), dispatcher.getVelocity(), REFLECTION_COEFF);
                Vect newBallVelocity = Geometry.reflectRotatingCircle(endCapEnd, flipperLineSegment.p1(), 
                        Flipper.this.getCCWAngularVelocity()*RADIANS_PER_DEGREE, 
                        dispatcher.getCircle(), dispatcher.getVelocity());
                dispatcher.setVelocity(newBallVelocity);
                //System.out.println("Vel of ball: " + newBallVelocity +" end cap");
                return;
            }
            // Collide with line 
            Vect newBallVelocity = Geometry.reflectRotatingWall(flipperLineSegment, 
                    Flipper.this.anchorPoint, Flipper.this.getCCWAngularVelocity()*RADIANS_PER_DEGREE, 
                    dispatcher.getCircle(), dispatcher.getVelocity(), REFLECTION_COEFF);
            //System.out.println("Vel of ball before: " + newBallVelocity +" rot wall");
            dispatcher.setVelocity(newBallVelocity);
            //System.out.println("Vel of ball after: " + newBallVelocity +" rot wall");
            checkRep();
            
        }
    }

    @Override
    public void renderText(Map<Point<Integer>, String> textGraphics) {
        char representativeCharacter = getRepresentativeCharacter();
        List<Point<Integer>> points = getOccupiedBoxes();
        for (Point<Integer> point : points) {
            textGraphics.put(point, String.valueOf(representativeCharacter));
        }
        checkRep();
    }


    @Override
    public double timeUntilCollision(pingball.game.gameobjects.Ball gameBall) {
        if (! this.isPhysicsEnabled()) {
            return Double.POSITIVE_INFINITY;
        }
        LineSegment flipperLineSegment = Flipper.this.getRotatedSegment();
        Circle endCapAnchor = new Circle(flipperLineSegment.p1(), END_CAP_RADIUS);
        Circle endCapEnd = new Circle(flipperLineSegment.p2(), END_CAP_RADIUS);
        double collisionTimeLine = Geometry.timeUntilRotatingWallCollision(
                flipperLineSegment, this.anchorPoint, 
                this.getCCWAngularVelocity()*RADIANS_PER_DEGREE, gameBall.getCircle(), gameBall.getVelocity());
        double collisionTimeCapAnchor = Geometry.timeUntilCircleCollision(endCapAnchor, 
                gameBall.getCircle(), gameBall.getVelocity());
        double collisionTimeCapEnd = Geometry.timeUntilRotatingCircleCollision(endCapEnd, flipperLineSegment.p1(), Flipper.this.getCCWAngularVelocity()*RADIANS_PER_DEGREE, 
                gameBall.getCircle(), gameBall.getVelocity());
        checkRep();
        return min(collisionTimeLine, collisionTimeCapAnchor, collisionTimeCapEnd);
    }

    @Override
    public boolean isBall() {
        return false;
    }

    @Override
    public void advance(Double seconds, PhysicsConfiguration config) {
        if (! this.isPhysicsEnabled()) {
            return;
        }
        double ccwAngularVelocity = this.getCCWAngularVelocity();
        if (Double.compare(ccwAngularVelocity, 0) == 0) {
            return;
        }
        // Rotate:
        final double maxRotation = 90.;
        double angleOfRotationDegrees = ccwAngularVelocity * seconds;
        if (Math.abs(angleOfRotationDegrees) >= maxRotation) {
            this.finishRotation();
        } else {
            Angle angleOfRotation = new Angle(angleOfRotationDegrees * RADIANS_PER_DEGREE);
            Vect newEndPoint = Geometry.rotateAround(this.endPoint, this.anchorPoint, angleOfRotation);
            this.endPoint = newEndPoint;
            if (this.isFinishedRotation()) {
                this.finishRotation();
            }
        }
        checkRep();
    }
    
    /**
     * Whether or not physics are enabled for the flipper:
     *   if not, collisions with the flipper will not be registered
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
    public void draw(Graphics2D g2, int L) {
        LineSegment currentLine = getRotatedSegment();
        g2.setPaint(Color.RED);
        float startPointX = (float) (L*currentLine.p1().x());
        float startPointY = (float) (L*currentLine.p1().y());
        float endPointX = (float) (L*currentLine.p2().x());
        float endPointY = (float) (L*currentLine.p2().y());
        g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2.draw(new Line2D.Float(startPointX, startPointY, endPointX, endPointY));
        
        
    }


}
