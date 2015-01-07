package pingball.game.gameobjects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;
import pingball.game.Board;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;


/**
 * 
 * An absorber simulates the ball-return mechanism in a pinball game. When a ball 
 * hits an absorber, the absorber stops the ball and holds it (unmoving) in the bottom right-hand 
 * corner of the absorber. The ball’s center is .25L from the bottom of the absorber and .25L from 
 * the right side of the absorber.
 *
 *If the absorber is holding a ball, then the action of an absorber, when it is triggered, 
 * is to shoot the ball straight upwards in the direction of the top of the playing area. 
 * By default, the initial velocity of the ball should be 50L/sec. With the default gravity and
 * the default values for friction, the value of 50L/sec gives the ball enough energy to lightly 
 * collide with the top wall, if the bottom of the absorber is at y=20L. If the absorber is not
 * holding the ball, or if the previously ejected ball has not yet left the absorber, then the 
 * absorber takes no action when it receives a trigger signal.
 * 
 *An absorber can be made self-triggering by connecting its trigger to its own action. When the
 * ball hits a self-triggering absorber, it should be moved to the bottom right-hand corner 
 * as described above, and then be shot upward as described above. There may or may not be a 
 * short delay between these events, at your discretion.
 * 
 * An Absorber's special action is shooting the balls it has absorbed.
 * 
 * THIS CLASS IS MUTABLE
 * 
 */
public class Absorber implements StationaryGameObject{
    
    // Thread-safety:  Only accessed by one thread (isolated)
    
    /**
     * The character with which the absorber's walls are drawn on
     *   the board
     */
    private static final String ABSORBER_REP = "=";
    /**
     * The inital velocity of a ball fired from the absorber
     */
    private static final Vect ABSORBER_EMISSION_VELOCITY = new Vect(0, -50);
    /**
     * the x AND y offset from the bottom right of the absorber.
     * IE:  if 0.25, the first ball in the queue will be positioned
     *   (-0.25, -0.25) + the vector of the bottom-right-most point
     */
    private static final double HOLDING_OFFSET = 0.25;
    /**
     * How multiple balls are spaced while in the queue.
     *   If 0, the balls will be placed on top if each other.
     *   If -1, the balls will be spaced from right to left, 1
     *     character apart
     */
    private static final double QUEUE_X_OFFSET = -1.0;
    
    /**
     * The height of the absorber in L
     */
    private final int  height;
    //Rep Invariant: must be greater than 0 and less than or equal to 20
    /**
     * The width of the absorber in L
     */
    private final int width;
    //Rep Invariant: must be greater than 0 and less than or equal to 20
    /**
     * The position of the upper-left corner of the absorber
     */
    private final Vect position;
    //Rep Invariant: must exist within the walls of the game board, vector (x,y) where x and y are integers
    /**
     * The board the absorber is on
     */
    private final Board board;
    //Rep Invariant: must be the board that this object exists in
    //Abstraction function: represents a stationary absorber object with height height, width width,
    //    and postion Vect position and with reference to the board that the abosrber lies on
    
    /**
     * The line segments making up the edges of the absorber
     */
    private Set<LineSegment> walls;
    //Rep Invariant: must be of size 4
    /**
     * The four corners of the absorber
     */
    private Set<Circle> corners;
    //Rep Invariant: must be of size 4
    
    /**
     * The special action trigger listeners of the absorber
     */
    private List<TriggerListener> specialActionTriggerListeners = new ArrayList<TriggerListener>();
    /**
     * The collision trigger listeners of the absorber
     */
    private List<TriggerListener> collisionTriggerListeners = new ArrayList<TriggerListener>();
    
    /**
     * The position at which balls are emitted from the absorber
     */
    private final Vect absorberEmissionPosition;
    
    // It is important to have this declared as a LinkedList type so
    //   that we can use both the functionality of Queue and the functionality
    //   of List (for iteration)
    /**
     * The linked list of balls contained in the absorber
     */
    private LinkedList<Ball> balls = new LinkedList<Ball>();
    
    /**
     * whether or not physics are enabled for the absorber
     */
    private boolean physicsEnabled = true;
    
    /**
     * creates a new Absorber object with height height, width width, and position vector postion and 
     *    a reference to its respective board
     * @param board the board that this absorber is placed on
     * @param height integer representing the hight of the absorber in units of L
     * @param width integer representing the width of the absorber in units of L
     * @param position the vector refering to the location of the top-left-most corner of the absorber. Must
     *    represent an integer location
     */
    public Absorber(Board board, int height, int width, Vect position){
        this.height = height;
        this.width = width;
        this.position = position;
        this.board = board;
        this.absorberEmissionPosition = new Vect((int)position.x() + width - HOLDING_OFFSET, 
                (int)position.y() + this.height - HOLDING_OFFSET);
        this.addCollisionTriggerListener(new AbsorberCollisionTriggerListener());
        this.addSpecialActionTriggerListener(new AbsorberSpecialActionTriggerListener());
        
        this.walls = this.getEdges();
        this.corners = this.getCorners();
        checkRep();
    }
    
    /**
     * @return a set of the four corners of the absorber
     */
    private Set<Circle> getCorners() {
        Set<Circle> myCorners = new HashSet<Circle>();
        Circle topLeftCorner = new Circle(position, 0);
        Circle topRightCorner = new Circle(position.plus(new Vect(this.width, 0)), 0);
        Circle bottomLeftCorner = new Circle(position.plus(new Vect(0, this.height)), 0);
        Circle bottomRightCorner = new Circle(position.plus(new Vect(this.width, this.height)), 0);
        myCorners.add(topLeftCorner);
        myCorners.add(topRightCorner);
        myCorners.add(bottomRightCorner);
        myCorners.add(bottomLeftCorner);
        return myCorners;
    }
    
    /**
     * @return a set of the four edges of the absorber
     */
    private Set<LineSegment> getEdges() {
        Set<LineSegment> myEdges = new HashSet<LineSegment>();
        LineSegment topBorder = new LineSegment(this.position.x(), this.position.y(), 
                this.position.x() + width, this.position.y());     
        LineSegment bottomBorder = new LineSegment(this.position.x(), this.position.y() + height, 
                this.position.x() + width, this.position.y() + height);
        LineSegment leftBorder = new LineSegment(this.position.x(), this.position.y(), 
                this.position.x(), this.position.y() + height);
        LineSegment rightBorder = new LineSegment(this.position.x() + width, this.position.y(), 
                this.position.x() + width, this.position.y() + height);
        myEdges.add(topBorder);
        myEdges.add(bottomBorder);
        myEdges.add(leftBorder);
        myEdges.add(rightBorder);
        return myEdges;
    }
    
    /**
     * asserts that the rep invariants of the instance variables are held
     */
    private void checkRep(){
        assert(this.height > 0 && this.height <= 20);
        assert(this.width > 0 && this.width <= 20);
        assert(this.position != null);
        assert(this.board != null);
        Vect topLeftPoint = this.position;
        Vect bottomRightPoint = new Vect(this.position.x() + this.width, this.position.y() + this.height);
        //asserts that the rectanglular absorber is within the walls of the game board
        assert( topLeftPoint.x() >= 0 && topLeftPoint.y() >= 0 && bottomRightPoint.x() <= this.board.getWidth()
                && bottomRightPoint.y() <= this.board.getHeight());     
        assert this.walls.size() == 4;
        assert this.corners.size() == 4;
    }
      
    /**
     * checks if the ball is within the bounds of the absorber, inclusive
     * @param ball the ball that is being checked
     * @return boolean representing whether or not the ball is within the bounds of the absorber, inclusive
     */
    boolean isBallInAbsorber(Ball ball){
        return ball.getPosition().y() >= this.position.y() && ball.getPosition().y() <= this.position.y() + this.height
                && ball.getPosition().x() >= this.position.x() && ball.getPosition().x() <= this.position.x() + this.width;       
    }
    
    /**
     * Object-specific event handler for an Abosorber object.  For the event when a ball
     *   collides with this absorber object, will trigger a response when a trigger is emitted
     * 
     */
    class AbsorberCollisionTriggerListener implements TriggerListener {
        
        /**
         * Trigger that is called on when a gameBall makes contact with the absorber.  Absorbs the ball and
         *    shoots it out according to the spec
         */
        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {          
            if (! Absorber.this.isPhysicsEnabled()) {
                return;
            }
            Absorber.this.absorbBall(dispatcher);   
        }  
    }
    
    /**
     * The default special trigger listener for an absorber
     * 
     * It shoots a ball when triggered.
     */
    class AbsorberSpecialActionTriggerListener implements TriggerListener {
        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            shootBalls();
        }
    }
    
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

    @Override
    public void renderText(Map<Point<Integer>, String> textGraphics) {
        int leftMostCoordinate = (int)this.position.x();
        int rightMostCoordinate = (int)this.position.x() + this.width;
        int topMostCoordinate = (int)this.position.y();
        // iterates through each horizontal level of the absorber, getting the respective points 
        //   and inserting the Abrober_rep into the textGraphics
        for (int i = 0; i < this.height; i++){
            List<Point<Integer>> pointsOnLine = Point.getPointsBetween(new Point<Integer>(leftMostCoordinate, topMostCoordinate + i),
                    new Point<Integer>(rightMostCoordinate, topMostCoordinate + i));
            pointsOnLine.remove(pointsOnLine.size() - 1);
            for (Point<Integer> point : pointsOnLine) {
                textGraphics.put(point, ABSORBER_REP);
            }
        }
    }

    @Override
    public double timeUntilCollision(Ball gameBall) {
        if (! this.isPhysicsEnabled()) {
            return Double.POSITIVE_INFINITY;
        }
        if (isBallInAbsorber(gameBall)){
            return Double.POSITIVE_INFINITY;
        }
        LineSegment closestWall = this.getClosestWall(gameBall);
        Circle closestCorner = this.getClosestCorner(gameBall);
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
     * returns the wall of this absorber that the game ball's trajectory
     * is closest to
     * 
     * @param gameBall
     *            the ball that is the reference to the walls
     * @return The closest wall to the gameBall's current trajectory.
     */
    LineSegment getClosestWall(Ball gameBall) {
        LineSegment minWall = null;
        Double minTimeWall = Double.POSITIVE_INFINITY;
        boolean firstIteration = true;
        for (LineSegment wall : this.walls) {
            double tempTime = Geometry.timeUntilWallCollision(wall,
                    gameBall.getCircle(), gameBall.getVelocity());
            if (firstIteration || tempTime < minTimeWall) {
                minTimeWall = tempTime;
                minWall = wall;
                firstIteration = false;
            }
        }
        return minWall;
    }

    /**
     * Returns the corner of this absorber that the game ball's
     * trajectory is closest to. If the gameBall is equidistant from two
     * corners, one will be arbitrarily chosen and returned.
     * 
     * @param gameBall
     *            the ball that is the reference to the corner
     * @return the closest corner to the gameBall's current trajectory. If the
     *         gameBall is equidistant from two corners, one will be arbitrarily
     *         chosen and returned.
     */
    Circle getClosestCorner(Ball gameBall) {
        Circle minCorner = null;
        Double minTimeCorner = Double.POSITIVE_INFINITY;
        boolean firstIteration = true;
        for (Circle corner : this.corners) {
            double tempTime = Geometry.timeUntilCircleCollision(corner,
                    gameBall.getCircle(), gameBall.getVelocity());
            if (firstIteration || tempTime < minTimeCorner) {
                minTimeCorner = tempTime;
                minCorner = corner;
                firstIteration = false;
            }
        }
        return minCorner;
    }
    
    /**
     * Repositions the balls in the queue so that they are
     *   spaced at regular intervals leading up to the
     *   emission position
     */
    private void updatePositionOfBallsInQueue() {
        double currentY = this.absorberEmissionPosition.y();
        double currentX = this.absorberEmissionPosition.x();
        for (Ball ball : this.balls) {
            // This check ensures that balls are not positioned
            //   outside of the absorber:
            if (currentX < this.position.x()) {
                currentX = this.position.x();
            }
            ball.setPosition(new Vect(currentX, currentY));
            currentX += QUEUE_X_OFFSET;
        }
    }
    
    /**
     * Checks if there are balls to shoot,
     *   removes the lead ball from the queue,
     *   fires it,
     *   and then repositions the remaining balls
     *     in the queue
     */
    private void shootBalls() {
        // Check if there are balls to shoot:
        if (this.balls.isEmpty()) {
            return;
        }
        // Shoot one ball:
        shootBall(this.balls.remove());
        // Move the other balls:
        updatePositionOfBallsInQueue();
    }
    
     
    /**
     * shoots the ball being currently held at a velocity of 50L/sec
     * @param gameBall to be shot upwards
     */
     private void  shootBall(Ball gameBall){
        gameBall.setPosition(absorberEmissionPosition);
        gameBall.setVelocity(ABSORBER_EMISSION_VELOCITY);   
        gameBall.setPhysicsEnabled(true);
    }
    
    /**
     * captures and holds the ball so that its center is at .25L from the bottom of the absorber and .25L from 
     *    the right side of the absorber, should a ball collide with the absorber
     * @param gameBall the ball that collided with the absorber and that is going to be held
     */
    private void absorbBall(Ball gameBall){
        gameBall.setPosition(absorberEmissionPosition);
        gameBall.setVelocity(Vect.ZERO);
        gameBall.setPhysicsEnabled(false);
        this.balls.add(gameBall);
        updatePositionOfBallsInQueue();
    }
    
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
        GradientPaint redToBlue = new GradientPaint(0,0,Color.YELLOW,50, 0, Color.BLUE, true);
        g2.setPaint(redToBlue);
        float startPointX = (float) (lineSegment.p1().x()*L);
        float startPointY = (float) (lineSegment.p1().y()*L);
        float endPointX = (float) (lineSegment.p2().x()*L);
        float endPointY = (float) (lineSegment.p2().y()*L);
        g2.setStroke(new BasicStroke(3));
        g2.draw(new Line2D.Float(startPointX, startPointY, endPointX, endPointY));
    }
    
    @Override
    public void draw(Graphics2D g2, int L) {
        for (LineSegment lineSegment : walls){
            drawLine(g2, L, lineSegment);
        }
    }
}
