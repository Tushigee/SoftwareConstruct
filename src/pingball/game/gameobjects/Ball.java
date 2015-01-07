package pingball.game.gameobjects;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import physics.Circle;
import physics.Geometry;
import physics.Geometry.VectPair;
import physics.Vect;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.game.util.TriggerListener;
import pingball.networking.JsonSerializable;

/**
 *  A Pingball ball
 *  
 *  THIS CLASS IS MUTABLE
 */
public class Ball implements MoveableGameObject, JsonSerializable {

    // AF:  a Pingball ball
    // RI:  within bounds of playing field
    //    -  Mass is greater than or equal to 0
    // TSA:  Balls are only ever accessed by one thread.
    //     They may be serialized into json and passed to
    //     another thread, but the ball object itself is never
    //     passed between threads
    //
    // Wire-protocal (serialized JSON):
    //
    // {"x":<X POSITION>,"y":<Y POSITION>,"xVel":<X VELOCITY>,"yVel":<Y VELOCITY>,"mass":<MASS>,"radius":<RADIUS>}
    // Where <X POSITION>, <Y POSITION>, <X VELOCITY>, <Y VELOCITY>, <MASS>, <RADIUS> are doubles

    /**
     * checks that the rep invairants of the instance variables are held
     */
    private void checkRep() {
        assert this.circle.getCenter().x() > -0.5;
        assert this.circle.getCenter().y() > -0.5;
        assert this.circle.getCenter().x() < 20.5;
        assert this.circle.getCenter().y() < 20.5;
        assert velocity != null;
        assert mass >= 0;
    }
    
    /**
     * Stores information about the Ball's position
     * and radius
     */
    private Circle circle;
    
    /**
     * ballColor that is null before drawn
     */
    private Color ballColor;

    /**
     * Stores information about the Ball's velocity
     */
    private Vect velocity;   

    /**
     * Stores information about the Ball's mass
     */
    private double mass = DEFAULT_MASS;

    /**
     * Whether or not physics is enabled for the ball
     *   (whether or not it experiences collisions/moves
     *     during advance)
     */
    private boolean physicsEnabled = true;

    /**
     * The character which is used to represent the
     * ball
     */
    private static final String BALL_TEXT_GRAPHIC = "*";

    /**
     * The default mass of the ball
     */
    private static final double DEFAULT_MASS = 1.0;

    /**
     * The default radius of the ball
     */
    private static final double DEFAULT_RADIUS = 0.25;
     
    /**
     * Creates a new ball with the given position, radius
     *   initial velocity, and mass
     * @param position the position of the ball as a vector (in L)
     * @param radius the radius of the ball in L
     * @param initialVelocity the initial velocity of the ball as a vector
     *   (in L/sec)
     * @param mass the mass of the ball
     */
    public Ball(Vect position, double radius, Vect initialVelocity, double mass) {
        List<Color> listOfColors = Arrays.asList(Color.GREEN, Color.YELLOW, 
                Color.RED, Color.ORANGE, Color.BLUE, Color.MAGENTA, Color.getHSBColor(.790F, .865F, .902F));
        Random rand = new Random();
        ballColor = listOfColors.get(rand.nextInt(7));
        this.circle = new Circle(position, radius);
        this.velocity = initialVelocity;
        this.mass = mass;
        this.addCollisionTriggerListener(new BallReflectTriggerListener());
        checkRep();
    }
    /**
     * Creates a new ball with the given position, radius and
     *   initial velocity
     * @param position the position of the ball as a vector (in L)
     * @param initialVelocity the initial velocity of the ball as a vector
     *   (in L/sec)
     */
    public Ball(Vect position, Vect initialVelocity) {
        this(position, DEFAULT_RADIUS, initialVelocity, DEFAULT_MASS);
    }

    /**
     * @return the current velocity of the ball
     */
    public Vect getVelocity() {
        return velocity;
    }

    /**
     * Sets the current velocity of the ball
     * 
     * @param newVelocity the new velocity of the ball
     */
    public void setVelocity(Vect newVelocity) {
        velocity = newVelocity;
    }

    /**
     * Gets the circle object representing the position
     *   and radius of the ball (important
     *   for the physics engine).
     * @return the circle object representing the ball's
     *   position and radius
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Gets the radius of the ball (in L)
     * @return the radius of the ball (in L)
     */
    public double getRadius() {
        return circle.getRadius();
    }

    /**
     * Gets the position of the ball (a vector in L)
     * @return the position of the ball (a vector in L)
     */
    public Vect getPosition() {
        return circle.getCenter();
    }

    /**
     * Sets the position of the ball (a vector in L)
     * 
     * Pre-condition:  the newPosition must be within the
     *   confines of the containing board and must not be
     *   on top of other objects.
     * @param newPosition the new position of the ball
     *   (a vector in L)
     */
    public void setPosition(Vect newPosition) {
        circle = new Circle(newPosition, circle.getRadius());
    }

    /**
     * A list of TriggerListeners which are to be executed
     * when a special action occurs.
     * 
     * Currently, the Ball has no special actions
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
     * Object-specific event handler for an ball object.  For the event when an other ball
     *   collides with this ball object, will trigger a response when a trigger is emitted
     *   
     */
    private class BallReflectTriggerListener implements TriggerListener {

        @Override
        public void trigger(Ball dispatcher, PhysicsConfiguration config) {
            VectPair newVelocities = Geometry.reflectBalls(Ball.this.getPosition(), Ball.this.mass, 
                    Ball.this.velocity, dispatcher.getPosition(), dispatcher.mass, dispatcher.getVelocity());
            Ball.this.setVelocity(newVelocities.v1);
            dispatcher.setVelocity(newVelocities.v2);


            checkRep();
        }

    }

    @Override
    public void renderText(Map<Point<Integer>, String> textGraphics) {
        int xInt = (int)Math.floor(this.circle.getCenter().x());
        int yInt = (int)Math.floor(this.circle.getCenter().y());
        if (this.getPosition().y() >= 20.0){
            yInt = 19;
        }
        if (this.getPosition().y() <= 0.0){
            yInt = 0;
        }
        if (this.getPosition().x() >= 20.0){
            xInt = 19;
        }
        if (this.getPosition().x() <= 0.0){
            xInt = 0;
        }

        textGraphics.put(new Point<Integer>(xInt, yInt), BALL_TEXT_GRAPHIC);
    }

    @Override
    public double timeUntilCollision(Ball gameBall) {
        if (! this.isPhysicsEnabled()) {
            return Double.POSITIVE_INFINITY;
        }
        return Geometry.timeUntilBallBallCollision(this.getCircle(), this.getVelocity(), 
                gameBall.getCircle(), gameBall.getVelocity());
    }

    @Override
    public boolean isBall() {
        return true;
    }


    /**
     * Applies friction to the ball
     * @param velocity the current velocity of this ball
     * @param seconds the number of milliseconds that have advanced since the privious advance
     * @param config the current physics configuration
     * @return a new velocity vector after the application of friction
     */
    Vect applyFriction(Vect velocity, double seconds, PhysicsConfiguration config) {
        return velocity.times(1.0 - config.getKineticFrictionCoefficient() * seconds 
                - config.getVelocityDependentFrictionCoefficient() * velocity.length() * seconds);
    }

    /**
     * The height of the board
     */
    private static final int BOARD_WIDTH = 20;
    
    /**
     * The width of the board
     */
    private static final int BOARD_HEIGHT = 20;
    
    /**
     * The buffer distance between the ball and the wall (if
     *   the ball needs to be repositioned due to precision
     *   error)
     */
    private static final double EPSILON_BUFFER = 0.05;
    
    /**
     * Ensures that the ball is within the boundaries of the board
     * 
     * Because life isn't perfect, and neither is floating
     *   point arithmetic
     */
    private void enforceBoundaries() {
        double x = this.getPosition().x();
        double y = this.getPosition().y();
        double buffer = this.getRadius() + EPSILON_BUFFER;
        if (x < 0) {
            x = buffer;
        } else if (x > BOARD_WIDTH) {
            x = BOARD_WIDTH - buffer;
        }
        if (y < 0) {
            y = buffer;
        } else if (y > BOARD_HEIGHT) {
            y = BOARD_HEIGHT - buffer;
        }
        if (x != this.getPosition().x() || y != this.getPosition().y()) {
            this.setPosition(new Vect(x, y));
        }
    }


    @Override
    public void advance(Double seconds, PhysicsConfiguration config) {
        if (! this.isPhysicsEnabled()) {
            return;
        }
        Vect g = config.getAccelerationDueToGravity();
        Vect newVelocity = applyFriction(this.velocity, seconds, config).plus(g.times(seconds));
        Vect newPosition = this.getPosition().plus(this.velocity.times(seconds)).plus(g.times(0.5 * seconds * seconds));
        this.circle = new Circle(newPosition, this.circle.getRadius());
        this.velocity = newVelocity;
        this.enforceBoundaries();
        checkRep();
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
    public JsonObject serializeJson() {
        JsonObjectBuilder ballJsonBuilder = Json.createObjectBuilder();
        ballJsonBuilder.add("x", this.circle.getCenter().x());
        ballJsonBuilder.add("y", this.getCircle().getCenter().y());
        ballJsonBuilder.add("xVel", this.velocity.x());
        ballJsonBuilder.add("yVel", this.velocity.y());
        ballJsonBuilder.add("mass", this.mass);
        ballJsonBuilder.add("radius", this.circle.getRadius());
        return ballJsonBuilder.build();
    }


    /**
     * Generates a Ball from a JSON object representing a Ball
     * @param json the JSON object representing a serialized Ball
     * @return the parsed Ball
     * @throws JsonException if the JSON was not able to be parsed
     *   into a Ball
     */
    public static Ball parseJson(JsonObject json) throws JsonException{
        Vect center = new Vect(json.getJsonNumber("x").doubleValue(), json.getJsonNumber("y").doubleValue());
        double radius = json.getJsonNumber("radius").doubleValue();
        double mass = json.getJsonNumber("mass").doubleValue();
        Vect velocity = new Vect(json.getJsonNumber("xVel").doubleValue(), json.getJsonNumber("yVel").doubleValue());
        return new Ball(center, radius, velocity, mass);
    }
    // Methods related to GUI process
    @Override
    public void draw(Graphics2D g, int L) {
        
            g.setPaint(ballColor);
            double radius  = this.getRadius();
            Shape theCircle = new Ellipse2D.Double(L*(this.circle.getCenter().x() - this.circle.getRadius()), L*(this.circle.getCenter().y() - radius), 2.0 * radius * L, 2.0 * radius * L);
            g.fill(theCircle);
    }
    
}