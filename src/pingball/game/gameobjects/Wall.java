package pingball.game.gameobjects;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import pingball.game.Board;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;
import pingball.networking.BallRoutingMessage;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;

/**
 *  The border walls surrounding the playfield.
 *  Trigger: collision
 *  Action: reflection or moved to another player's field
 *  Coefficient of reflection: 1.0 (but see below)
 *  
 *  Each wall may be either solid or invisible. A solid wall is reflective, 
 *  so that a ball bounces off it. An invisible wall allows a ball to pass through it, 
 *  into another playing area, although this feature has not yet been implemented.
 *  
 *  THIS CLASS IS MUTABLE
 */
public class Wall implements StationaryGameObject{

    /**
     * The magnitude by which x and y coordinates must
     *   be offset for rendering the wall (due to rounding
     *   from float coordinates to an integer grid)
     */
    private static final double OFFSET = 0.5;
    
    /**
     * Distinguishes between the different positions
     *   a wall on a board may have 
     */
    public enum WallPosition {
        /**
         * When a position is not applicable.
         * This is only used in client/server communication
         */
        NONE(Vect.ZERO, Vect.ZERO, Vect.ZERO, Vect.ZERO),
        /**
         * A wall on the left side of the board
         */
        LEFT(Vect.ZERO, Vect.Y_HAT, new Vect(-OFFSET, -OFFSET), new Vect(-OFFSET, OFFSET)),
        /**
         * A wall on the right side of the board
         */
        RIGHT(Vect.X_HAT, new Vect(1, 1), new Vect(OFFSET, -OFFSET), new Vect(OFFSET, OFFSET)),
        /**
         * A wall on the top of the board
         */
        TOP(Vect.ZERO, Vect.X_HAT, new Vect(-OFFSET, -OFFSET), new Vect(OFFSET, -OFFSET)),
        /**
         * A wall on the bottom of the board
         */
        BOTTOM(Vect.Y_HAT, new Vect(1, 1), new Vect(-OFFSET, OFFSET), new Vect(OFFSET, OFFSET));

        /**
         * One point of the line segment (of unit size)
         */
        private Vect pointA;
        /**
         * The other point of the line segment (of unit size)
         */
        private Vect pointB;
        /**
         * The offset vector for generating the line segment which
         *   will be rendered
         */
        private Vect pointARenderOffset;
        /**
         * The offset vector for generating the line segment which will
         *   be rendered
         */
        private Vect pointBRenderOffset;
        
        /**
         * Creates a new wall position based on its actual position (in unit vectors)
         *   and the vectors which handle offsetting the linesegment's point prior
         *   to rendering
         * @param pointA the unit vector pointing at the start point of the line segment
         * @param pointB the unit vector pointing at the end point of the line segment
         * @param pointARenderOffset the vector to offset pointA when generating the
         *   rendered line segment
         * @param pointBRenderOffset the vector to offset pointB when generating the
         *   rendered line segment
         */
        WallPosition(Vect pointA, Vect pointB, Vect pointARenderOffset, Vect pointBRenderOffset) {
            this.pointA = pointA;
            this.pointB = pointB;
            this.pointARenderOffset = pointARenderOffset;
            this.pointBRenderOffset = pointBRenderOffset;
        }

        /**
         * rounds the coordinate given down to a whole a number
         * @param coord the coordinate to be rounded
         * @return the floor of the given coordinate as an integer
         */
        private static int roundCoordinate(double coord) {
            return (int)Math.floor(coord);
        }

        /**
         * Generates the integer points which should be
         *   rendered in the textual representation of
         *   the board
         * @param width the width of the board
         * @param height the height of the board
         * @return a list of the points which should be rendered
         */
        public List<Point<Integer>> getRenderPoints(int width, int height) {
            Vect actualPointA = new Vect(pointA.x() * width, pointA.y() * height);
            Vect actualPointB = new Vect(pointB.x() * width, pointB.y() * height);
            Vect renderPointAFloat = actualPointA.plus(pointARenderOffset);
            Vect renderPointBFloat = actualPointB.plus(pointBRenderOffset);
            Point<Integer> renderPointA = new Point<Integer>(
                    roundCoordinate(renderPointAFloat.x()), roundCoordinate(renderPointAFloat.y()));
            Point<Integer> renderPointB = new Point<Integer>(
                    roundCoordinate(renderPointBFloat.x()), roundCoordinate(renderPointBFloat.y()));
            List<Point<Integer>> points = Point.getPointsBetween(renderPointA, renderPointB);
            return points;
        }

        /**
         * @param width the width of the board
         * @param height the height of the board
         * @return start point of the line segment
         */
        public Vect getAPoint(int width, int height) {
            return new Vect(this.pointA.x() * width, this.pointA.y() * height);
        }
        
        /**
         * @param width the width of the board
         * @param height the height of the board
         * @return end point of the line segment
         */
        public Vect getBPoint(int width, int height) {
            return new Vect(this.pointB.x() * width, this.pointB.y() * height);
        }
        /**
         * Gets the line segment representing the wall
         *   used by the physics engine
         * @param width the width of the board
         * @param height the height of the board
         * @return the line segment representing the wall which
         *   is used by the physics engine
         */
        public LineSegment getLineSegment(int width, int height) {
            Vect actualPointA = new Vect(pointA.x() * width, pointA.y() * height);
            Vect actualPointB = new Vect(pointB.x() * width, pointB.y() * height);
            return new LineSegment(actualPointA, actualPointB);
        }

        /**
         * Gets a list containing the end caps of the wall used by the
         *   physics engine
         * Post-condition:  the length of the list is 2
         * @param width the width of board
         * @param height the height of the board
         * @return a list of length 2 which contains the end
         *   caps of the wall used by the physics engine. 
         */
        public List<Circle> getEndCaps(int width, int height) {
            List<Circle> endCaps = new ArrayList<Circle>(2);
            Vect actualPointA = new Vect(pointA.x() * width, pointA.y() * height);
            Vect actualPointB = new Vect(pointB.x() * width, pointB.y() * height);
            endCaps.add(new Circle(actualPointA, 0));
            endCaps.add(new Circle(actualPointB, 0));
            return endCaps;
        }
    }
    
    /**
     * The ASCII character to use when rendering a non-connected wall
     */
    private static final String WALL_REP = ".";
    
    /**
     * THE ASCII character to use when rendering a connected wall
     *   (besides the name of the connected board)
     */
    private static final String WALL_REP_CONNECTED = ".";
    
    /**
     * The coefficient of reflection for a non-connected wall
     */
    private static final double COEFFICIENT_OF_REFLECTION = 1.0;

    /**
     * The board on which the wall is placed
     */
    private final Board board;
    
    /**
     * Which of the outer-walls this wall is (left, top, right, bottom)
     */
    private final WallPosition position;
    
    /**
     * Whether or not the board is connected
     */
    private boolean connected = false;
    
    /**
     * The object in context to which this board is connected
     *   (only relevant if connect == true)
     */
    private BoardObjectInContext connectedTo;
    
    /**
     * The cached printable name of the board this board is connected
     *   to
     */
    private String printableConnectedBoardName = "";
    
    /**
     * How many wall characters should be skipped before starting to
     *   print the printableConnectedBoardName while rendering
     */
    private int printNameOffset;
    
    /**
     * Checks to ensure the RI is upheld
     */
    private void checkRep() {
        assert this.board != null;
        assert this.position != null;
    }
    
    /**
     * Creates a new wall given the board the wall is on
     *   and which wall position this wall is at (left, top, right, bottom)
     * @param board the board which this wall is on
     * @param position the position of the wall on the board (left, top, right, bottom)
     */
    public Wall(Board board, WallPosition position) {
        if (board == null || position == null) {
            throw new IllegalArgumentException("Wall constructor called with null arguments.");
        }
        this.board = board;
        this.position = position;
        this.generatedCachedTextPoints();
        this.addCollisionTriggerListener(new WallReflectTriggerListener());
        this.addSpecialActionTriggerListener(new WallSpawnBallTriggerListener());
        checkRep();
    }

    /**
     * Connect the wall to the given object in context
     * 
     * @param connectedTo the object to connect the wall
     *   to
     */
    public void connect(BoardObjectInContext connectedTo) {
        this.connectedTo = connectedTo;
        this.connected = true;

        // Update rendering variables:
        String connectedBoardName = connectedTo.getBoardName();
        int lengthName = connectedBoardName.length();
        int lengthPoints = this.cachedPointsOnLine.size();
        printableConnectedBoardName = connectedBoardName;
        printNameOffset = 0;
        if (lengthPoints >= lengthName) {
            printNameOffset = (lengthPoints - lengthName) / 2;
        } else {
            printableConnectedBoardName = connectedBoardName.substring(0, lengthPoints);
        }
    }

    /**
     * Disconnect the wall
     */
    public void disconnect() {
        this.connected = false;
        printableConnectedBoardName = "";
    }

    /**
     * @return whether or not the wall is connected
     */
    public boolean isConnected() {
        return this.connected;
    }
    
    /**
     * Send ball to the board to be sent to the server (or to another
     *   game object on the board)
     * @param ball the ball to send to the board for processing
     */
    public void sendBallToOtherBoard(Ball ball) {
        BallRoutingMessage brm = new BallRoutingMessage(ball,
                new BoardObjectInContext(this.board.getName(), BoardObjectType.WALL,
                        "", this.position), this.connectedTo);

        this.board.sendOutgoingBRM(brm);
    }


    /**
     * Object-specific event handler for a wall object.  For the event when a ball
     *   collides with this wall object, will trigger a response when a trigger is emitted
     */
    private class WallReflectTriggerListener implements TriggerListener {

        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            if (Wall.this.isConnected()) {
                Wall.this.sendBallToOtherBoard(dispatcher);
            } else {
                Wall.this.reflectBall(dispatcher);
            }
        }

    }

    /**
     *  Called when a ball is to be spawned from the wall 
     */
    private class WallSpawnBallTriggerListener implements TriggerListener {

        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            // Balls to the wall
            int width = Wall.this.board.getWidth();
            int height = Wall.this.board.getHeight();
            switch (Wall.this.position) {
            case LEFT:
                double xOffset = dispatcher.getRadius();
                Vect newPositionHoriz = new Vect(Wall.this.position.getAPoint(width, height).x() + xOffset,
                        dispatcher.getPosition().y());
                dispatcher.setPosition(newPositionHoriz);
                break;
            case RIGHT:
                double xOffset2 = -dispatcher.getRadius();
                Vect newPositionHoriz2 = new Vect(Wall.this.position.getAPoint(width, height).x() + xOffset2,
                        dispatcher.getPosition().y());
                dispatcher.setPosition(newPositionHoriz2);
                break;
            case TOP:
                double yOffset = dispatcher.getRadius();
                Vect newPositionVert = new Vect(dispatcher.getPosition().x(), 
                        Wall.this.position.getAPoint(width, height).y() + yOffset);
                dispatcher.setPosition(newPositionVert);
                break;
            case BOTTOM:
                double yOffset2 = -dispatcher.getRadius();
                Vect newPositionVert2 = new Vect(dispatcher.getPosition().x(), 
                        Wall.this.position.getAPoint(width, height).y() + yOffset2);
                dispatcher.setPosition(newPositionVert2);
                break;
            default:
                throw new RuntimeException("Unknown wall position");
            }
        }

    }



    /**
     * A list of TriggerListeners which are to be executed
     * when a special action occurs.
     * 
     * Currently, the Wall has no special actions
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
     * The points of the wall, so that they don't have to be
     * calculated each time the board is rendered
     */
    private List<Point<Integer>> cachedPointsOnLine;

    /**
     * generates the integer points that lie along the current wallLineSegment and stores
     * them in the List of integer-points cachedPointsOnLine
     */
    private void generatedCachedTextPoints() {
        cachedPointsOnLine = 
                this.position.getRenderPoints(this.board.getWidth(), this.board.getHeight());
    }

    @Override
    public void renderText(Map<Point<Integer>, String> textGraphics) {
        if (! this.isConnected()) {
            for (Point<Integer> point : cachedPointsOnLine) {
                textGraphics.put(point, WALL_REP);
            }
        } else {
            // Printing the name on the wall if necessary
            int lengthPrintedName = printableConnectedBoardName.length();
            for (int i = 0; i < this.cachedPointsOnLine.size(); ++i) {
                if (i >= printNameOffset && i - printNameOffset < lengthPrintedName) {
                    textGraphics.put(cachedPointsOnLine.get(i), 
                            String.valueOf(printableConnectedBoardName.charAt(i - printNameOffset)));
                } else {
                    textGraphics.put(cachedPointsOnLine.get(i), WALL_REP_CONNECTED);
                }
            }
        }
    }

    /**
     * Returns the corner of this wall that the game ball's
     * trajectory is closest to. If the gameBall is equidistant from two
     * corners, one will be arbitrarily chosen and returned.
     * 
     * @param corners
     *            the 4 corners of this wall as a List<Circle>
     * @param gameBall
     *            the ball that is the reference to the corner
     * @return the closest corner to the gameBall's current trajectory. If the
     *         gameBall is equidistant from two corners, one will be arbitrarily
     *         chosen and returned.
     */
    Circle getClosestCorner(List<Circle> corners, Ball gameBall) {
        Circle minCorner = corners.get(0);
        Double minTimeCorner = Double.POSITIVE_INFINITY;
        for (Circle corner : corners) {
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
     * timeUntilCollision:
     * 
     * @param gameBall
     *            instance of ball to check to see how long until it collides
     *            with this Wall
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
        LineSegment closestWall = this.position.getLineSegment(this.board.getWidth(), 
                this.board.getHeight());
        Circle closestCorner = this.getClosestCorner(this.position.getEndCaps(this.board.getWidth(), 
                this.board.getHeight()), gameBall);
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
     * instance of wall
     * 
     * @param gameBall
     *            ball to be reflected
     */
    public void reflectBall(Ball gameBall) {
        LineSegment closestWall = this.position.getLineSegment(this.board.getWidth(), 
                this.board.getHeight());
        Circle closestCorner = this.getClosestCorner(this.position.getEndCaps(this.board.getWidth(), 
                this.board.getHeight()), gameBall);
        double timeToClosestWall = Geometry.timeUntilWallCollision(closestWall,
                gameBall.getCircle(), gameBall.getVelocity());
        double timeToClosestCorner = Geometry.timeUntilCircleCollision(
                closestCorner, gameBall.getCircle(), gameBall.getVelocity());

        if (timeToClosestWall < timeToClosestCorner) {
            gameBall.setVelocity(Geometry.reflectWall(closestWall,
                    gameBall.getVelocity(), COEFFICIENT_OF_REFLECTION));
        } else {
            gameBall.setVelocity(Geometry.reflectCircle(
                    closestCorner.getCenter(), gameBall.getPosition(),
                    gameBall.getVelocity()));
        }
    }
    
    /**
     * Whether or not physics are enabled.
     * For Wall, having physics disabled is somewhat meaningless;
     *   collisions will still occur so that balls remain inside the board
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
     * Draw text on g2d with rotated by angle, and moved by height into right hand side
     * @param g2d that name will be drawn
     * @param x   x coordinate of first character of drawn string
     * @param y   y coordinate of first character of drawn string
     * @param angle to be rotated by this method
     * @param text to be drawn by this method
     * @param height offset of text
     */
    private static void drawRotate(Graphics2D g2d, double x, double y, int angle, String text, double height) 
    {    
        g2d.translate((float)x,(float)y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text, (float) 0.0,((float) -height)/2);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float)x,-(float)y);
    } 
    /**
     * Draw name of wall along side the line segment that represents  wall. 
     * @param g2 that name will be drawn
     * @param startPointX x coordinate of start point of wall
     * @param startPointY y coordinate of start point of wall
     * @param endPointX   x coordinate of end point of wall
     * @param endPointY   y coordinate of end point of wall
     */
    private void drawNameOnWall(Graphics2D g2, float startPointX, float startPointY, float endPointX, float endPointY){
        // Setting up color of painting
        // if the wall is vertical, we have to rotate the printing text
        Font font = g2.getFont();
        g2.setFont(new Font("Times New", Font.BOLD, 14));
        FontMetrics metrics = g2.getFontMetrics(font);
        if (startPointX == endPointX){
            if (startPointX == 500.0F) {
            g2.setPaint(Color.BLUE);
            drawRotate(g2, (startPointX + endPointX)/2, (startPointY + endPointY)/2, 90, printableConnectedBoardName, metrics.getHeight());
            }
            else if (startPointX == 0.0F){
                g2.setPaint(Color.ORANGE);
                drawRotate(g2, (startPointX + endPointX)/2, (startPointY + endPointY)/2, 270, printableConnectedBoardName, (metrics.getHeight()));
            }
        }
        else{
            if (startPointY == 500.0F) {
                g2.setPaint(Color.RED);
                g2.drawString(printableConnectedBoardName, (startPointX + endPointX)/2, (startPointY + endPointY)/2 +metrics.getHeight());
            }
            else {
                g2.setPaint(Color.GREEN);
                g2.drawString(printableConnectedBoardName, (startPointX + endPointX)/2, (startPointY + endPointY)/2 - metrics.getHeight()+10);
            }
        }
    }
    @Override
    public void draw(Graphics2D g2, int L) {
        // Setting up Graphics2D elements
        g2.setPaint(Color.BLACK);
        Composite origComposite = g2.getComposite(); 
        Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f);
        
        // Making transparent text
        g2.setComposite(c);
        
        // Calculating neccessary points to draw a line
        int boardWidth = this.board.getWidth();
        int boardHeight = this.board.getHeight();
        float startPointX = (float) (L*this.position.getAPoint(boardWidth, boardHeight).x());
        float startPointY = (float) (L*this.position.getAPoint(boardWidth, boardHeight).y());
        float endPointX = (float) (L*this.position.getBPoint(boardWidth, boardHeight).x());
        float endPointY = (float) (L*this.position.getBPoint(boardWidth, boardHeight).y());
        g2.setStroke(new BasicStroke(4));
        g2.draw(new Line2D.Float(startPointX, startPointY, endPointX, endPointY));
        drawNameOnWall(g2, startPointX, startPointY, endPointX, endPointY);
        g2.setComposite(origComposite);
    }

}
