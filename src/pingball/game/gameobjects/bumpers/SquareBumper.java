package pingball.game.gameobjects.bumpers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
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


/**
 * PingBall gadget that represents a stationary square bumper on the board. It has a
 * reflection coefficient of 1.0, and triggers the flippers upon a collision with
 * a ball.
 * 
 * THIS CLASS IS MUTABLE
 */
public class SquareBumper implements Bumper { 
    
    /**
     * The location of the square bumper on the board.
     * It is the vector to its upper-left corner
     */
    private final Vect position;
    //Rep Invariant: must exist within the walls of the game board
    
    /**
     * The board on which the square bumper is placed
     */
    private final Board board;
    //Rep Invariant: must be the board that this object exists in
    //Abstraction function: represents a stationary square bumper object with
    //   postion Vect position and with reference to the board that the square bumper lies on
    
    /**
     * The line segments making up the edges of the square bumper
     */
    private List<LineSegment> walls;
    //Rep Invariant: must be of size 4
    
    /**
     * The circles making up the corners of the square bumper
     */
    private List<Circle> corners;
    //Rep Invariant: must be of size 4

    
    /**
     * The coefficient of reflection for the square bumper
     */
    private static final double REFLECTION_COEFFICIENT = 1.0;
    
    /**
     * The ASCII representation of the square bumper
     */
    private static final String SQUAREBUMPER_REP = "#";
    
    /**
     * The height of the square bumper (in L)
     */
    private static final int HEIGHT = 1;
    
    /**
     * The width of the square bumper (in L)
     */
    private static final int WIDTH = 1;
    
    /**
     * The list of event listeners which are triggered when a ball
     *   collides with the square bumper
     */
    private List<TriggerListener> collisionTriggerListeners = new ArrayList<TriggerListener>();
    
    /**
     * The list of event listeners which are triggered when the
     *   square bumper's special action is triggered
     */
    private List<TriggerListener> specialActionTriggerListeners = new ArrayList<TriggerListener>();
       
    /**
     * creates a new square bumper with position vector position and a reference to its respective board
     * 
     * @param board the board that contains this square bumper
     * @param position the vector refering to the location of the top-left-most corner of the absorber
     */
    public SquareBumper(Board board, Vect position){
        this.board = board;
        this.position = position;
        this.addCollisionTriggerListener(new SquareBumperTriggerListener());
        LineSegment topBorder = new LineSegment(this.position.x(), this.position.y(), this.position.x() + WIDTH, this.position.y());     
        LineSegment bottomBorder = new LineSegment(this.position.x(), this.position.y() + HEIGHT, this.position.x() + WIDTH, this.position.y() + HEIGHT);
        LineSegment leftBorder =  new LineSegment(this.position.x(), this.position.y(), this.position.x(), this.position.y() + HEIGHT);
        LineSegment rightBorder=  new LineSegment(this.position.x() + WIDTH, this.position.y(), this.position.x() + WIDTH, this.position.y() + HEIGHT); 
        Circle topLeftCorner = new Circle(position, 0);
        Circle topRightCorner = new Circle(new Vect(position.x() + 1,position.y()), 0);
        Circle bottomLeftCorner = new Circle(new Vect(position.x(), position.y() + 1),0);
        Circle bottomRightCorner = new Circle(new Vect(position.x()+1, position.y() + 1), 0);
        this.walls = Arrays.asList(topBorder, bottomBorder, leftBorder, rightBorder);
        this.corners = Arrays.asList(topLeftCorner,topRightCorner, bottomLeftCorner, bottomRightCorner);
        checkRep();                     
    }
    
    /**
     * asserts that the rep invariants of the instance variables are held
     */
    private void checkRep(){
        assert(this.position != null);
        assert(this.board != null);
        assert(this.walls.size() == 4);
        assert(this.corners.size() == 4);
        Vect topLeftPoint = this.position;
        Vect bottomRightPoint = new Vect(this.position.x() + WIDTH, this.position.y() + HEIGHT);
        //asserts that the square bumper is within the walls of the game board
        assert( topLeftPoint.x() >= 0 && topLeftPoint.y() >= 0 && bottomRightPoint.x() <= this.board.getWidth() && bottomRightPoint.y() <= this.board.getHeight());  
    }
    
    /**
     * Object-specific event handler for a SquareBumper object.  For the event when a ball
     *   collides with this bumper object, will trigger a response when a trigger is emitted
     */
    private class SquareBumperTriggerListener implements TriggerListener {

        /**
         * Trigger that is called on when a gameBall makes contact with the square bumper.  Reflects the 
         *    ball off the bumper
         * @param dispatcher the ball that the trigger is being applied to
         * @param config the physics configuration that applies to this particular action
         */
        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            if (! SquareBumper.this.isPhysicsEnabled()) {
                return;
            }
            SquareBumper.this.reflectBall(dispatcher);
        }
    }
    
    @Override
    public void renderText(Map<Point<Integer>, String> textGraphics) {
        textGraphics.put(new Point<Integer>((int)this.position.x(),(int)this.position.y()), SQUAREBUMPER_REP);
    }

    @Override
    public double timeUntilCollision(Ball gameBall) {
        if (! this.isPhysicsEnabled()) {
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
    public void emitCollisionTrigger(Ball dispatcher, PhysicsConfiguration config) {
        if (! this.isPhysicsEnabled()) {
            return;
        }
        for (TriggerListener listener : collisionTriggerListeners) {
            listener.trigger(dispatcher, config);
        }  
    }
  
    
    /**
     * returns the wall of this square bumper that the game ball's trajectory
     * is closest to
     * 
     * @param gameBall
     *            the ball that is the reference to the walls
     * @return The closest wall to the gameBall's current trajectory.
     */
    LineSegment getClosestWall(Ball gameBall) {
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
     * Returns the corner of this square bumper that the game ball's
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
     * reflects the ball off of the wall of the square bumper
     * @param gameBall the ball to be reflected
     */
    public void reflectBall(Ball gameBall) {
        LineSegment closestWall = this.getClosestWall( gameBall);
        Circle closestCorner = this.getClosestCorner( gameBall);
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
     * Whether or not physics is enabled for the square bumper.
     * If false, collisions with the square bumper will not be registered
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
        g2.setPaint(Color.MAGENTA);
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
