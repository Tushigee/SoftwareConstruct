package pingball.game.gameobjects.bumpers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;
import static org.junit.Assert.*;

/**
 * PingBall gadget that represents a stationary triangle bumper on the board. It
 * has a reflection coefficient of 1.0, and triggers the board's flippers upon a
 * collision with a ball.
 * 
 * THIS CLASS IS MUTABLE
 */
public class TriangleBumper implements Bumper {
    
    /**
     * The coefficient of reflection for the triangle bumper
     */
    private static final double REFLECTION_COEFFICIENT = 1.0;
    
    /**
     * The ASCII representation of the triangle bumper
     */
    private final String triangleBumperRep;
    
    /**
     * The list of line segments which make up the edges
     *   of the triangle bumper
     */
    private final List<LineSegment> walls; // must be only 3 walls
    
    /**
     * The list of circles which make up the corners of the
     *   triangle bumper
     */
    private final List<Circle> corners; // must be 3 corners
    
    /**
     * The board in which the triangle bumper is contained
     */
    private final Board board; // RI: must be the board that this instance of
                               // TriangleBumper is placed on, not null
    
    /**
     * The orientation of the triangle bumper (in degrees)
     */
    private final int orientation; // RI: only allowed values are 0, 90, 180, or
                                   // 270, not null
    
    /**
     * The upper-left hand corner of the triangle bumper
     */
    private final Vect northwestCorner; // RI: the TriangleBumper instance must
    // be entirely within the playing area of
    // the board, not null

    // AF: represents a stationary TriangleBumper gadget with postion Vect
    // "northwestCorner", orientation int "orientation" and reference "board" to
    // the board that this TriangleBumper is on.

    /**
     * A list of event listeners which are called when a ball collides with
     *   the triangle bumper
     */
    private List<TriggerListener> collisionTriggerListeners = new ArrayList<TriggerListener>();
    
    /**
     * A list of event listeners which are called when the triangle bumper's
     *   special action is emitted
     */
    private List<TriggerListener> specialActionTriggerListeners = new ArrayList<TriggerListener>();

    /**
     * creates a new instance of TriangleBumper with position vector
     * "northwestCorner" and orientation "orientation" on board "board"
     * 
     * @param board
     *            : the instance of board that this triangleBumper instance is
     *            going to be placed on
     * @param northwestCorner
     *            : Position vector that describes where the northwest corner of
     *            the bounding box of this triangleBumper instance is located.
     * @param orientation
     *            : Orientation of this triangleBumper instance. Allowed values
     *            are 0, 90, 180, 270, and nothing else. 0 represents the
     *            triangle where the intersection of the two legs of the right
     *            triangle are in the northwest corner of the bounding box, 90
     *            represents the triangle where the intersection of the two legs
     *            of the right triangle are in the northeast corner of the
     *            bounding box, 180 represents the triangle where the
     *            intersection of the two legs of the right triangle are in the
     *            southeast corner of the bounding box, 270 represents the
     *            triangle where the intersection of the two legs of the right
     *            triangle are in the southwest corner of the bounding box
     * 
     * 
     */
    public TriangleBumper(Board board, Vect northwestCorner, int orientation) {
        this.board = board;
        this.orientation = orientation;
        this.northwestCorner = northwestCorner;
        if (orientation == 0) {
            Double cornerX = northwestCorner.x();
            Double cornerY = northwestCorner.y();
            LineSegment horizontalLeg = new LineSegment(cornerX, cornerY,
                    cornerX + 1, cornerY);
            LineSegment verticalLeg = new LineSegment(cornerX, cornerY,
                    cornerX, cornerY + 1);
            LineSegment hypotenuse = new LineSegment(cornerX + 1, cornerY,
                    cornerX, cornerY + 1);
            this.walls = Collections.unmodifiableList(Arrays.asList(
                    horizontalLeg, verticalLeg, hypotenuse));
            Circle corner1 = new Circle(northwestCorner, 0);
            Circle corner2 = new Circle(new Vect(cornerX + 1, cornerY), 0);
            Circle corner3 = new Circle(new Vect(cornerX, cornerY + 1), 0);
            this.corners = Collections.unmodifiableList(Arrays.asList(corner1,
                    corner2, corner3));
            triangleBumperRep = "/";
        }

        else if (orientation == 90) {
            Double cornerX = northwestCorner.x();
            Double cornerY = northwestCorner.y();
            LineSegment horizontalLeg = new LineSegment(cornerX, cornerY,
                    cornerX + 1, cornerY);
            LineSegment verticalLeg = new LineSegment(cornerX + 1, cornerY,
                    cornerX + 1, cornerY + 1);
            LineSegment hypotenuse = new LineSegment(cornerX, cornerY,
                    cornerX + 1, cornerY + 1);
            this.walls = Collections.unmodifiableList(Arrays.asList(
                    horizontalLeg, verticalLeg, hypotenuse));
            Circle corner1 = new Circle(northwestCorner, 0);
            Circle corner2 = new Circle(new Vect(cornerX + 1, cornerY), 0);
            Circle corner3 = new Circle(new Vect(cornerX + 1, cornerY + 1), 0);
            this.corners = Collections.unmodifiableList(Arrays.asList(corner1,
                    corner2, corner3));
            triangleBumperRep = "\\";
        } else if (orientation == 180) {
            Double cornerX = northwestCorner.x();
            Double cornerY = northwestCorner.y();
            LineSegment horizontalLeg = new LineSegment(cornerX, cornerY + 1,
                    cornerX + 1, cornerY + 1);
            LineSegment verticalLeg = new LineSegment(cornerX + 1, cornerY,
                    cornerX + 1, cornerY + 1);
            LineSegment hypotenuse = new LineSegment(cornerX, cornerY + 1,
                    cornerX + 1, cornerY);
            this.walls = Collections.unmodifiableList(Arrays.asList(
                    horizontalLeg, verticalLeg, hypotenuse));
            Circle corner1 = new Circle(new Vect(cornerX + 1, cornerY), 0);
            Circle corner2 = new Circle(new Vect(cornerX + 1, cornerY + 1), 0);
            Circle corner3 = new Circle(new Vect(cornerX, cornerY + 1), 0);
            this.corners = Collections.unmodifiableList(Arrays.asList(corner1,
                    corner2, corner3));
            triangleBumperRep = "/";
        } else {
            Double cornerX = northwestCorner.x();
            Double cornerY = northwestCorner.y();
            LineSegment horizontalLeg = new LineSegment(cornerX, cornerY + 1,
                    cornerX + 1, cornerY + 1);
            LineSegment verticalLeg = new LineSegment(cornerX, cornerY,
                    cornerX, cornerY + 1);
            LineSegment hypotenuse = new LineSegment(cornerX, cornerY,
                    cornerX + 1, cornerY + 1);
            this.walls = Collections.unmodifiableList(Arrays.asList(
                    horizontalLeg, verticalLeg, hypotenuse));
            Circle corner1 = new Circle(northwestCorner, 0);
            Circle corner2 = new Circle(new Vect(cornerX + 1, cornerY + 1), 0);
            Circle corner3 = new Circle(new Vect(cornerX, cornerY + 1), 0);
            this.corners = Collections.unmodifiableList(Arrays.asList(corner1,
                    corner2, corner3));
            triangleBumperRep = "\\";

        }
        this.addCollisionTriggerListener(new TriangleBumperTriggerListener());
        checkRep();
    }

    /**
     * Checks to see if the rep invariants (as stated above) are maintained.
     */
    private void checkRep() {
        assertNotNull(this.board);
        assertNotNull(this.northwestCorner);
        assertNotNull(this.orientation);
        assert ((this.orientation == 0) || (this.orientation == 90)
                || (this.orientation == 180) || (this.orientation == 270));
        assert (this.walls.size() == 3);
        for (LineSegment wall : this.walls) {
            assert (wall.p1().x() >= -0.5 && wall.p1().x() <= this.board
                    .getWidth() + 0.5);
            assert (wall.p1().y() >= -0.5 && wall.p1().y() <= this.board
                    .getHeight() + 0.5);
            assert (wall.p2().x() >= -0.5 && wall.p2().x() <= this.board
                    .getWidth() + 0.5);
            assert (wall.p2().y() >= -0.5 && wall.p2().y() <= this.board
                    .getHeight() + 0.5);
        }
        assert (this.triangleBumperRep.equals("\\") || this.triangleBumperRep
                .equals("/"));

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
        int xPos = (int) Math.round(this.northwestCorner.x());
        int yPos = (int) Math.round(this.northwestCorner.y());
        textGraphics.put(new Point<Integer>(xPos, yPos),
                this.triangleBumperRep);
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
        LineSegment closestWall = this.getClosestWall(this.walls, gameBall);
        Circle closestCorner = this.getClosestCorner(this.corners, gameBall);
        double timeToClosestWall = Geometry.timeUntilWallCollision(closestWall,
                gameBall.getCircle(), gameBall.getVelocity());
        double timeToClosestCorner = Geometry.timeUntilCircleCollision(
                closestCorner, gameBall.getCircle(), gameBall.getVelocity());
        if (timeToClosestWall < timeToClosestCorner) {
            return timeToClosestWall;
        }
        return timeToClosestCorner;

    }

    /**
     * 
     * Calculates the new velocity of a gameBall after a collision with this
     * instance of TriangleBumper
     * 
     * @param gameBall
     *            ball to be reflected
     */
    public void reflectBall(Ball gameBall) {
        LineSegment closestWall = this.getClosestWall(this.walls, gameBall);
        Circle closestCorner = this.getClosestCorner(this.corners, gameBall);
        double timeToClosestWall = Geometry.timeUntilWallCollision(closestWall,
                gameBall.getCircle(), gameBall.getVelocity());
        double timeToClosestCorner = Geometry.timeUntilCircleCollision(
                closestCorner, gameBall.getCircle(), gameBall.getVelocity());

        if (timeToClosestWall < timeToClosestCorner) {
            gameBall.setVelocity(Geometry.reflectWall(closestWall,
                    gameBall.getVelocity(), REFLECTION_COEFFICIENT));
        } else {
            gameBall.setVelocity(Geometry.reflectCircle(
                    closestCorner.getCenter(), gameBall.getPosition(),
                    gameBall.getVelocity()));
        }
    }

    /**
     * returns the wall of this triangle bumper that the game ball's trajectory
     * is closest to
     * 
     * @param walls
     *            the 3 walls of this circle bumper as a List<LineSegment>
     * @param gameBall
     *            the ball that is the reference to the walls
     * @return The closest wall to the gameBall's current trajectory.
     */
    LineSegment getClosestWall(List<LineSegment> walls, Ball gameBall) {
        LineSegment minWall = this.walls.get(0);
        Double minTimeWall = Double.POSITIVE_INFINITY;
        for (LineSegment wall : this.walls) {
            double tempTime = Geometry.timeUntilWallCollision(wall,
                    gameBall.getCircle(), gameBall.getVelocity());
            if (tempTime < minTimeWall) {
                minTimeWall = tempTime;
                minWall = wall;
            }
        }
        return minWall;
    }

    /**
     * Returns the corner of this triangle bumper that the game ball's
     * trajectory is closest to. If the gameBall is equidistant from two
     * corners, one will be arbitrarily chosen and returned.
     * 
     * @param corners
     *            the 3 corners of this triangle bumper as a List<Circle>
     * @param gameBall
     *            the ball that is the reference to the corner
     * @return the closest corner to the gameBall's current trajectory. If the
     *         gameBall is equidistant from two corners, one will be arbitrarily
     *         chosen and returned.
     */
    Circle getClosestCorner(List<Circle> corners, Ball gameBall) {
        Circle minCorner = this.corners.get(0);
        Double minTimeCorner = Double.POSITIVE_INFINITY;
        for (Circle corner : this.corners) {
            double tempTime = Geometry.timeUntilCircleCollision(corner,
                    gameBall.getCircle(), gameBall.getVelocity());
            if (tempTime < minTimeCorner) {
                minTimeCorner = tempTime;
                minCorner = corner;
            }
        }
        return minCorner;
    }

    /**
     * Object-specific event handler for a TriangleBumper object. For the event
     * when a ball collides with this bumper object, will trigger a response
     * when a trigger is emitted
     */
    private class TriangleBumperTriggerListener implements TriggerListener {

        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            if (! TriangleBumper.this.isPhysicsEnabled()) {
                return;
            }
            TriangleBumper.this.reflectBall(dispatcher);
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
     * Whether or not physics is enabled for the triangle bumper.
     * If false, then collisions will not be registered.
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

    /**
     * Draws line on the 2D graphics panel
     * @param g graphics that this line will be drawn
     * @param L number of pixels in per default size in L 
     * @param lineSegment represents line segment that will be drawn on graphics g
     */
    private void drawLine(Graphics2D g2, int L, LineSegment lineSegment){
        float hue = .792F;
        float saturation = .865F;
        float brightness = .702F;

        g2.setPaint(Color.getHSBColor(hue, saturation, brightness));
        float startPointX = (float) (lineSegment.p1().x()*L);
        float startPointY = (float) (lineSegment.p1().y()*L);
        float endPointX = (float) (lineSegment.p2().x()*L);
        float endPointY = (float) (lineSegment.p2().y()*L);
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2.draw(new Line2D.Float(startPointX, startPointY, endPointX, endPointY));
    }
    
    @Override
    public void draw(Graphics2D g2, int L) {
        for (LineSegment lineSegment : walls){
            drawLine(g2, L, lineSegment);
        }
    }

}
