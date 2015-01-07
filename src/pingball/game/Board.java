package pingball.game;

import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.json.JsonException;
import javax.json.JsonObject;
import javax.swing.JPanel;

import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.GameObject;
import pingball.game.gameobjects.MoveableGameObject;
import pingball.game.gameobjects.StationaryGameObject;
import pingball.game.gameobjects.Wall;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.Point;
import pingball.networking.BallRoutingMessage;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.DisconnectMessage;
import pingball.networking.MessageProcessor;
import pingball.networking.MessageType;
import pingball.networking.WallConnectionInformationMessage;

/**
 * 
 * Board represents the entire pingball game with all of its internal components, 
 * 
 * THIS CLASS IS MUTABLE
 *
 */
public class Board {
    
    /**
     * The name of the board
     */
    private final String name;
    /**
     * 
     * The numerical precision of seconds in simulation
     */
    double precision = 1e6;
    /**
     * The width of the board (in L)
     */
    private final int width;
    //Rep Invariant: must be greater than 1 and less than or equal to 20
    
    /**
     * The height of the board (in L)
     */
    private final int height;
    //Rep Invariant: must be greater than 1 and less than or equal to 20
   
    /**
     * AF:  all the balls currently on the board
     */
    private Set<Ball> balls;
    
    /**
     * The balls pending removal (in the middle of the advance loop, typically).
     *   This is an internal implementation detail
     */
    private Set<Ball> ballsPendingRemoval;
    // RI:  All balls in balls pending removal should be in balls
    
    /**
     * The map between names and balls which are named
     */
    private Map<String, Ball> namedBalls;
    // RI:  Must be the reverse map of ballNameLookup (have same length,
    //   keys/values reversed
    
    /**
     * The map between named balls and their names
     */
    private Map<Ball, String> ballNameLookup;
    // RI:  The key set must be a subset of balls.  Must be the reverse map of 
    //   namedBalls (have same length,
    //   keys/values reversed
    
    /**
     * AF:  all the moveable objects currently on the board
     *   OTHER than balls
     */
    private Set<MoveableGameObject> moveableObjects;
    
    /**
     * The map between names and moveable game objects with names
     */
    private Map<String, MoveableGameObject> namedMoveableGameObjects;
    // RI:  Must be the reverse map of moveableGameObjectNameLookup (have same length,
    //   keys/values reversed
    
    /**
     * The map between named moveable game objects and their names
     */
    private Map<MoveableGameObject, String> moveableGameObjectNameLookup;
    // RI:  The key set must be a subset of moveableObject.  Must be the reverse map of 
    //   namedMoveableGameObjects (have same length,
    //   keys/values reversed
    
    /**
     * AF:  all the stationary objects currently on the board
     */
    private Set<StationaryGameObject> stationaryGameObjects; //RI: can't be null
    
    /**
     * The map between names and stationary game objects with names
     */
    private Map<String, StationaryGameObject> namedStationaryGameObjects;
    // RI:  Must be the reverse map of stationaryGameObjectNameLookup (have same length,
    //   keys/values reversed
    
    /**
     * The map between named stationary game objects and their names
     */
    private Map<StationaryGameObject, String> stationaryGameObjectNameLookup;
    // RI:  The key set must be a subset of stationaryGameObjects.  Must be the reverse map of 
    //   namedStationaryGameObjects (have same length,
    //   keys/values reversed
    
    /**
     * the Physics configuration, including constants which will
     *   affect the physics engine, like acceleration due to gravity
     */
    private PhysicsConfiguration physicsConfig; //RI: can't be null
    
    /**
     * The inbox for the board:  messages waiting to be processed (processed when
     *   advance is called)
     */
    private final BlockingQueue<JsonObject> inbox = new LinkedBlockingQueue<JsonObject>();
    
    /**
     * The outbox for the board:  messages waiting to be sent to the server
     */
    private BlockingQueue<JsonObject> outbox = new LinkedBlockingQueue<JsonObject>();
    
    /**
     * A map between wall's position and the wall at that position
     */
    private final Map<Wall.WallPosition, Wall> outerWalls = new HashMap<Wall.WallPosition, Wall>();
    
    /**
     * Whether or not the board is connected to a server
     */
    private boolean isConnectedToServer;
    
    /**
     * key listeners (key name -> event listener) that should be registered with the UI.
     */
    private Map<String, KeyListener> keyListeners = new HashMap<String, KeyListener>();
    
    
    //Abstraction Function: Represents a game board with all components as the ones in this.objects 
    //  and a height and length of this.height and this.length respectively.

    /**
     * Checks to see if the rep invariants as stated above are maintained 
     */
    private void checkRep(){
        assert (this.getWidth() > 1) && (this.getWidth() <= 20);
        assert (this.getHeight() > 1) && (this.getHeight() <= 20);
        assert (this.stationaryGameObjects != null);
        assert (this.moveableObjects != null);
        assert (this.physicsConfig != null); 
        
        assert namedBalls != null;
        assert namedMoveableGameObjects != null;
        assert namedStationaryGameObjects != null;
        assert (balls.containsAll(namedBalls.values()));
        assert (moveableObjects.containsAll(namedMoveableGameObjects.values()));
        assert (stationaryGameObjects.containsAll(namedStationaryGameObjects.values()));
        
        assert ballNameLookup != null;
        assert moveableGameObjectNameLookup != null;
        assert stationaryGameObjectNameLookup != null;
        assert stationaryGameObjectNameLookup.size() == namedStationaryGameObjects.size();
        assert moveableGameObjectNameLookup.size() == namedMoveableGameObjects.size();
        assert this.ballNameLookup.size() == this.namedBalls.size();
    }
    
    
    /**
     * creates a new game board with height "height" and length "length"
     * 
     * @param name the name of the board
     * @param length the integer length of the board, representing the horizontal 
     * @param height the integer height of the board, representing the vertical
     * @param config the Physics configuration, including constants which will
     *   affect the physics engine, like acceleration due to gravity
     * @param isConnectedToServer whether or not the board is connected to a server
     */
    public Board(String name, int length, int height, PhysicsConfiguration config, boolean isConnectedToServer){
        this.name = name;
        this.isConnectedToServer = isConnectedToServer;
        this.width  = length;
        this.height = height;
        this.balls = new HashSet<Ball>();
        this.ballsPendingRemoval = new HashSet<Ball>();
        this.moveableObjects = new HashSet<MoveableGameObject>();
        this.stationaryGameObjects = new HashSet<StationaryGameObject>();
        this.namedBalls = new HashMap<String, Ball>();
        this.namedStationaryGameObjects = new HashMap<String, StationaryGameObject>();
        this.namedMoveableGameObjects = new HashMap<String, MoveableGameObject>();
        this.ballNameLookup = new HashMap<Ball, String>();
        this.stationaryGameObjectNameLookup = new HashMap<StationaryGameObject, String>();
        this.moveableGameObjectNameLookup = new HashMap<MoveableGameObject, String>();
        this.addWalls();
        this.physicsConfig = config;
        checkRep();
    }
    
    /**
     * @deprecated constructor before names were needed for Boards.  Used for
     * backwards compatibility with old tests.
     * 
     * creates a new game board with height "height" and length "length"
     * 
     * @param length the integer length of the board, representing the horizontal 
     * @param height the integer height of the board, representing the vertical
     * @param config the Physics configuration, including constants which will
     *   affect the physics engine, like acceleration due to gravity
     */
    public Board(int length, int height, PhysicsConfiguration config){
        this("Untitled", length, height, config, false);
    }
    
    /**
     * adds the 4 walls of the board to this board
     */
    private void addWalls() {
        Wall topWall = new Wall(this, Wall.WallPosition.TOP);
        Wall bottomWall = new Wall(this, Wall.WallPosition.BOTTOM);
        Wall leftWall = new Wall(this, Wall.WallPosition.LEFT);
        Wall rightWall = new Wall(this, Wall.WallPosition.RIGHT);
        this.addStationaryObject(topWall);
        this.addStationaryObject(bottomWall);
        this.addStationaryObject(leftWall);
        this.addStationaryObject(rightWall);
        outerWalls.put(Wall.WallPosition.TOP, topWall);
        outerWalls.put(Wall.WallPosition.BOTTOM, bottomWall);
        outerWalls.put(Wall.WallPosition.LEFT, leftWall);
        outerWalls.put(Wall.WallPosition.RIGHT, rightWall);
    }
    
    /**
     * @return gets the outbox queue for messages passed
     *   to this board
     */
    public BlockingQueue<JsonObject> getOutbox() {
        return this.outbox;
    }
    
    /**
     * @return gets the inbox queue for messages passed
     *   from this board
     */
    public BlockingQueue<JsonObject> getInbox() {
        return this.inbox;
    }
    
    /**
     * @return the board's name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * gets the width of this board
     * 
     * @return integer width of the board
     */
    public int getWidth(){
        return this.width;
    }
    
    /**
     *  gets the height of this board
     *  
     * @return integer height of the board
     */
    public int getHeight(){
        return this.height;
    }
    
    /**
     * @return the physics configuration for the board
     */
    public PhysicsConfiguration getPhysicsConfiguration() {
        return this.physicsConfig;
    }
    
    /**
     * @return whether or not the board is connected to the server
     */
    public boolean getIsConnectedToServer() {
        return isConnectedToServer;
    }

    /**
     * Sets whether or not the board is connected to the server
     * @param isConnectedToServer whether or not the board is
     *   connected to the server
     */
    public void setConnectedToServer(boolean isConnectedToServer) {
        this.isConnectedToServer = isConnectedToServer;
    }
    
    /**
     * Checks whether or not the name is available
     *   (ie, if another game object is not already using
     *   the name)
     * @param name the name to check
     * @return whether (true) or not (false) the name is available
     *   for use
     */
    public boolean isNameAvailable(String name) {
        return (! this.namedBalls.containsKey(name)
                && ! this.namedMoveableGameObjects.containsKey(name)
                && ! this.namedStationaryGameObjects.containsKey(name));
    }
    
    /**
     * Removes any game object with the specified name
     * 
     * @param name the name of the game object
     * @return true if a game object was removed, false
     *   otherwise
     */
    public boolean removeGameObjectWithName(String name) {
        if (this.namedBalls.containsKey(name)) {
            return this.removeBall(this.namedBalls.get(name));
        }
        if (this.namedMoveableGameObjects.containsKey(name)) {
            return this.removeMoveableObject(this.namedMoveableGameObjects.get(name));
        }
        if (this.namedStationaryGameObjects.containsKey(name)) {
            return this.removeStationaryObject(this.namedStationaryGameObjects.get(name));
        }
        return false;
    }
    
    /**
     * Looks up a game object by its name
     * 
     * @param name the name of the game object
     * @return the game object if found or null
     *   if not found
     */
    public GameObject lookupGameObject(String name) {
        if (this.namedBalls.containsKey(name)) {
            return this.lookupBall(name);
        }
        if (this.namedMoveableGameObjects.containsKey(name)) {
            return this.lookupMoveableObject(name);
        }
        if (this.namedStationaryGameObjects.containsKey(name)) {
            return this.lookupStationaryObject(name);
        }
        return null;
    }
    
    /**
     * adds a Ball object to the board
     * 
     * @param ball the ball to be added. Must go into the Board's objects collection
     * 
     */
    public void addBall(Ball ball){
        if (this.ballsPendingRemoval.contains(ball)) {
            this.ballsPendingRemoval.remove(ball);
            ball.setPhysicsEnabled(true);
            return;
        }
        this.balls.add(ball);
    }
    
    /**
     * adds a Ball object to the board with the given name
     * 
     * Pre-condition:  the name must not already be in use
     * @param name the name of the ball
     * @param ball the ball to be added. Must go into the Board's objects collection
     * 
     */
    public void addBall(String name, Ball ball) {
        if (! this.isNameAvailable(name)) {
            throw new IllegalArgumentException("Duplicate name exists: '" + name + "'.");
        }
        // this.balls.add(ball);
        this.addBall(ball);
        this.namedBalls.put(name, ball);
        this.ballNameLookup.put(ball, name);
    }
    
    /**
     * Trys to remove the given ball and returns a boolean
     *   representing whether or not a ball was removed
     * @param ball the ball to remove
     * @return whether (true) or not (false) a ball was removed
     */
    public boolean removeBall(Ball ball) {
        if (! this.balls.contains(ball)) {
            return false;
        }
        ball.setPhysicsEnabled(false);
        this.ballsPendingRemoval.add(ball);
        if (this.ballNameLookup.containsKey(ball)) {
            String name = this.ballNameLookup.get(ball);
            this.ballNameLookup.remove(ball);
            this.namedBalls.remove(name);
        }
        return true;
    }
    
    /**
     * Removes all the balls pending removal.
     *   This is an internal implementation detail
     *   which is used to prevent altering the set of balls
     *   while iterating through it
     */
    private void removeBallsPendingRemoval() {
        this.balls.removeAll(this.ballsPendingRemoval);
        this.ballsPendingRemoval.clear();
    }
    
    /**
     * Checks whether or not the given ball is contained
     *   in the board
     * @param ball the ball
     * @return whether or not the ball is contained in
     *   the board
     */
    public boolean containsBall(Ball ball) {
        return (this.balls.contains(ball) && ! this.ballsPendingRemoval.contains(ball));
    }
    
    /**
     * Looks up a ball by name and returns the ball object
     * @param name the name of the ball to look up
     * @return the ball object (or null if not found)
     */
    public Ball lookupBall(String name) {
        return this.namedBalls.get(name);
    }
    
    /**
     * adds a moveable game object to the board
     * 
     * @param object the moveable game object to be added. Must go into the Board's objects collection
     * 
     */
    public void addMoveableObject(MoveableGameObject object){
        this.moveableObjects.add(object);
    }
    
    /**
     * adds a moveable game object to the board with the given name
     * 
     * Pre-condition:  the name must not already be in use
     * @param name the name of the moveable game object
     * @param moveableObj the moveable game object to be added. 
     *   Must go into the Board's objects collection
     */
    public void addMoveableObject(String name, MoveableGameObject moveableObj) {
        if (! this.isNameAvailable(name)) {
            throw new IllegalArgumentException("Duplicate name exists: '" + name + "'.");
        }
        this.moveableObjects.add(moveableObj);
        this.namedMoveableGameObjects.put(name, moveableObj);
        this.moveableGameObjectNameLookup.put(moveableObj, name);
    }
    
    /**
     * Tries to remove the given moveable game object and returns a boolean
     *   representing whether or not a moveable game object was removed
     * @param moveableObj the moveable game object to remove
     * @return whether (true) or not (false) a ball was removed
     */
    public boolean removeMoveableObject(MoveableGameObject moveableObj) {
        if (! this.moveableObjects.contains(moveableObj)) {
            return false;
        }
        this.moveableObjects.remove(moveableObj);
        if (this.moveableGameObjectNameLookup.containsKey(moveableObj)) {
            String name = this.moveableGameObjectNameLookup.get(moveableObj);
            this.moveableGameObjectNameLookup.remove(moveableObj);
            this.namedMoveableGameObjects.remove(name);
        }
        return true;
    }
    
    /**
     * Looks up a moveable object by name and returns the ball object
     * @param name the name of the moveable object to look up
     * @return the moveable object (or null if not found)
     */
    public MoveableGameObject lookupMoveableObject(String name) {
        return this.namedMoveableGameObjects.get(name);
    }
    
    /**
     * adds a stationary game object to the board
     * 
     * @param object the stationary game object to be added. Must go into the Board's objects collection
     * 
     */
    public void addStationaryObject(StationaryGameObject object){
        this.stationaryGameObjects.add(object);
    }
    
    /**
     * adds a stationary game object to the board with the given name
     * 
     * Pre-condition:  the name must not already be in use
     * @param name the name of the stationary game object
     * @param stationaryObj the stationary game object to be added. 
     *   Must go into the Board's objects collection
     */
    public void addStationaryObject(String name, StationaryGameObject stationaryObj) {
        if (! this.isNameAvailable(name)) {
            throw new IllegalArgumentException("Duplicate name exists: '" + name + "'.");
        }
        this.stationaryGameObjects.add(stationaryObj);
        this.namedStationaryGameObjects.put(name, stationaryObj);
        this.stationaryGameObjectNameLookup.put(stationaryObj, name);
    }
    
    /**
     * Tries to remove the given stationary game object and returns a boolean
     *   representing whether or not a stationary game object was removed
     * @param stationaryObj the stationary game object to remove
     * @return whether (true) or not (false) a ball was removed
     */
    public boolean removeStationaryObject(StationaryGameObject stationaryObj) {
        if (! this.stationaryGameObjects.contains(stationaryObj)) {
            return false;
        }
        this.stationaryGameObjects.remove(stationaryObj);
        if (this.stationaryGameObjectNameLookup.containsKey(stationaryObj)) {
            String name = this.stationaryGameObjectNameLookup.get(stationaryObj);
            this.stationaryGameObjectNameLookup.remove(stationaryObj);
            this.namedStationaryGameObjects.remove(name);
        }
        return true;
    }
    
    /**
     * Looks up a stationary object by name and returns the ball object
     * @param name the name of the stationary object to look up
     * @return the stationary object (or null if not found)
     */
    public StationaryGameObject lookupStationaryObject(String name) {
        return this.namedStationaryGameObjects.get(name);
    }
    
    /**
     * Consumes a ball routing message, forwarding the ball onto the
     *   appropriate game object
     * @param brm the ball routing message
     */
    private void consumeBallRoutingMessage(BallRoutingMessage brm) {
        Ball ball = brm.getBall();
        BoardObjectInContext destination = brm.getDestination();
        if (destination.getBoardObjectType() == BoardObjectType.WALL
                && destination.getId() != Wall.WallPosition.NONE) {
            this.addBall(ball);
            outerWalls.get(destination.getId()).emitSpecialActionTrigger(ball, this.physicsConfig);
        } else {
            GameObject goDestination = this.lookupGameObject(destination.getName());
            if (goDestination == null) {
                GameObject goSource = this.lookupGameObject(brm.getSource().getName());
                if (goSource == null) {
                    throw new RuntimeException(brm.toString());
                    //System.err.println("Could not find destination object:  " + destination.getName());
                    //return;
                }
                this.addBall(ball);
                goSource.emitSpecialActionTrigger(ball, physicsConfig);
            } else {
                this.addBall(ball);
                goDestination.emitSpecialActionTrigger(ball, physicsConfig);
            }
        }
    }
    
    /**
     * Processes a WallConnectionInformationMessage from the server,
     *   connecting or disconnecting the appropriate wall
     *   
     * @param wcim
     */
    private void connectWall(WallConnectionInformationMessage wcim) {
        Wall wall = this.outerWalls.get(wcim.getSource().getId());
        if (wcim.getIsConnected()) {
            wall.connect(wcim.getConnectedComponent());
        } else {
            wall.disconnect();
        }
    }
    
    /**
     * Processes a message from the server, determining its type
     *   and performing the action that it requires
     *   
     * @param message the message from the server, serialized in
     *   JSON
     * @throws JsonException if the JSON could not be parsed into
     *   a semantically meaningful message
     */
    private void processMessage(JsonObject message) throws JsonException {
        if (message == null){
           // Do nothing sine connection is closed, and it was the last message
        }
        else{
            MessageType type = MessageProcessor.getMessageType(message);
            switch (type) {
            case BALL_ROUTING_MESSAGE:
                BallRoutingMessage brm = MessageProcessor.extractBallRoutingMessage(message);
                try {
                    consumeBallRoutingMessage(brm);
                } catch(RuntimeException e) {
                System.err.println("Could not process message:  " + message.toString()); 
                }
                break;
            case WALL_CONNECTION_INFORMATION_MESSAGE:
                WallConnectionInformationMessage wcim = 
                    MessageProcessor.extractWallConnectionInformationMessage(message);
                connectWall(wcim);
                break;
            default:
                throw new RuntimeException("Unrecognized message type.");
            }
        }
    }
    
    /**
     * Sends an outgoing BallRoutingMessage (if need be).
     *   The "if need be" disclosure is due to the fact that if the BallRoutingMessage
     *     is destined for the current board, there is no need to send it to the server,
     *     so it routes it itself internally
     * @param brm the BallRoutingMessaged to be sent to the server
     */
    public void sendOutgoingBRM(BallRoutingMessage brm) {
        this.removeBall(brm.getBall());
        if (brm.getDestination() != null && brm.getDestination().getBoardName().equals(this.getName())) {
            // Keep inside the board
            this.consumeBallRoutingMessage(brm);
        } else if (this.isConnectedToServer) {
            // Send to the server:
            JsonObject message = MessageProcessor.createMessage(MessageType.BALL_ROUTING_MESSAGE, brm);
            try {
                this.outbox.put(message);
            } catch (InterruptedException e) {
                // TODO:  remove print
                System.err.println("Could not send BRM to outbox.");
                e.printStackTrace();
            }
        } else {
            // Send back to source:
            BoardObjectInContext source = brm.getSource();
            if (! source.getBoardName().equals(this.getName())) {
                return; // Could not bounce back
            }
            BallRoutingMessage bounceBack = new BallRoutingMessage(brm.getBall(), source, source);
            this.consumeBallRoutingMessage(bounceBack);
        }
    }
    
    /**
     *  advances the parameters to the next state in animation
     * 
     * @param seconds the number of seconds to advance the physics engine
     */
    public void advance(double seconds) {
        // Remove balls which are pending removal:
        this.removeBallsPendingRemoval();
        // Process incoming messages:
        while (! this.inbox.isEmpty()) {
            JsonObject message = this.inbox.poll();
            if (message == null) break;
            try {
                processMessage(message);
            } catch (RuntimeException e) {
                System.err.println("Could not process incoming message: " + message.toString());
                e.printStackTrace();
            }
        }
        
        // Check every ball to see if it collides:
        for (Ball ball : this.balls) {
            if (! ball.isPhysicsEnabled()) {
                continue;
            }
            double secondsRemaining = seconds;
            boolean checkedForCollision = false;
            double collidesInSeconds = Double.POSITIVE_INFINITY;
            // While there are still collisions or the ball
            //  hasn't been completely advanced for the timestep,
            //  continue looping
            while (secondsRemaining > 0 && 
                    (!checkedForCollision || collidesInSeconds < Double.POSITIVE_INFINITY)
                    && this.containsBall(ball)) {
                // LOOK FOR MINIMUM COLLISION TIME
                collidesInSeconds = Double.POSITIVE_INFINITY;
                checkedForCollision = true;
                GameObject collidesWith = null;
                // With other balls:
                for (Ball otherBall : this.balls) {
                    if (ball == otherBall) {
                        continue;
                    }
                    if (! otherBall.isPhysicsEnabled()) {
                        continue;
                    }
                    double thisCollisionInSeconds = otherBall.timeUntilCollision(ball);
                    if (thisCollisionInSeconds < collidesInSeconds && thisCollisionInSeconds < secondsRemaining) {
                        collidesInSeconds = thisCollisionInSeconds;
                        collidesWith = otherBall;
                    }
                }
                // With moveable objects:
                for (MoveableGameObject moveable : this.moveableObjects) {
                    if (! moveable.isPhysicsEnabled()) {
                        continue;
                    }
                    double thisCollisionInSeconds = moveable.timeUntilCollision(ball);
                    if (thisCollisionInSeconds < collidesInSeconds && thisCollisionInSeconds < secondsRemaining) {
                        collidesInSeconds = thisCollisionInSeconds;
                        collidesWith = moveable;
                    }
                   
                }
                // With stationary objects:
                for (StationaryGameObject stationary : this.stationaryGameObjects) {
                    if (! stationary.isPhysicsEnabled()) {
                        continue;
                    }
                    double thisCollisionInSeconds = stationary.timeUntilCollision(ball);
                    if (thisCollisionInSeconds < collidesInSeconds && thisCollisionInSeconds < secondsRemaining) {
                        collidesInSeconds = thisCollisionInSeconds;
                        collidesWith = stationary;
                    }
                }
                // If a collision occurs:
                if (collidesInSeconds < Double.POSITIVE_INFINITY && collidesWith != null
                        && collidesInSeconds <= secondsRemaining - 1/precision) {
                    // Advance the ball to the collision:
                    int roundedCollidesInSeconds = (int) (collidesInSeconds * precision);
                    ball.advance((roundedCollidesInSeconds*1.0)/(precision), physicsConfig);
                    // Collide:
                    collidesWith.emitCollisionTrigger(ball, physicsConfig);
                    // Remove seconds off the ball's clock
                    secondsRemaining -= collidesInSeconds;
                }
            }
            // If the ball still has time remaining after
            //   all collisions have been carried out, 
            //   advance it to the end of the timestep
            if (secondsRemaining > 1/precision && this.containsBall(ball)) {
                ball.advance(((int)(secondsRemaining*precision))/(precision), physicsConfig);
            }
        }
        
        // Advance all moveable objects (ie Flippers which are
        //   rotating)
        for (MoveableGameObject moveable : this.moveableObjects) {
            moveable.advance(seconds, physicsConfig);
        }
        
        this.removeBallsPendingRemoval();
    }
    
    /**
     * Draws the board to the graphics system.
     * @param g2 the graphics panel on which to draw
     * @param L number of pixels in each L
     */
    public void draw(Graphics2D g2, int L){
        this.removeBallsPendingRemoval();
        // Draw graphics for stationary objects:
        for (StationaryGameObject stationary : this.stationaryGameObjects) {
            stationary.draw(g2, L);
        }
        // Draw graphics for movable objects:
        for (MoveableGameObject moveable : this.moveableObjects) {
            moveable.draw(g2, L);
        }
        // Draw graphics for balls:
        for (Ball ball : this.balls) {
            ball.draw(g2, L);
        }
    }
    /**
     * renders a text-based animation as a series of String in time to the specified PrintSteam
     * 
     * @param out the PrintStream that we want the text-based animation to be printed to
     */
    public void renderText(PrintStream out){
        // Remove balls which are pending removal:
        this.removeBallsPendingRemoval();
        // Generate empty graphics:
        Map<Point<Integer>, String> textGraphics = new HashMap<Point<Integer>, String>();
        // Draw graphics for stationary objects:
        for (StationaryGameObject stationary : this.stationaryGameObjects) {
            stationary.renderText(textGraphics);
        }
        // Draw graphics for moveable objects:
        for (MoveableGameObject moveable : this.moveableObjects) {
            moveable.renderText(textGraphics);
        }
        // Draw graphics for balls:
        for (Ball ball : this.balls) {
            ball.renderText(textGraphics);
        }
        final String default_output = " ";
        // Loop through the grid:
        for (int y = -1; y <= this.height; ++y) {
            if (y > -1) {
                // Go to a new line
                out.println();
            }
            // For each row...
            for (int x = -1; x <= this.width; ++x) {
                // For each column...
                // Get the current pixel
                String outputPixel = textGraphics.get(new Point<Integer>(x, y));
                if (outputPixel == null) {
                    outputPixel = default_output;
                }
                out.print(outputPixel);
            }
        }
    }
    
    /**
     * adds key listeners corresponding to the keys specified in the file from which the
     * board was loaded.
     * @param panel JPanel on which to register the key listeners
     */
    public void registerKeyListeners(JPanel panel){
        for (KeyListener listen: this.keyListeners.values()){
            panel.addKeyListener(listen);
        }
    }
    
    /**
     * sets the board's key listeners
     * @param newListeners the desired listener set
     */
    public void setKeyListeners(Map<String, KeyListener> newListeners){
        this.keyListeners = new HashMap<String, KeyListener>(newListeners);
    }
    
    /**
     * disconnects this board from the server. Removes any wall connections the
     * board has.
     */
    public void disconnect(){
        resetWalls();
        this.setConnectedToServer(false);
        try {
            this.outbox.put(MessageProcessor.createMessage(MessageType.DISCONNECT_MESSAGE, new DisconnectMessage(name)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.outbox = new LinkedBlockingQueue<JsonObject>();
    }
    
    /**
     * remove all wall connections from the board.
     */
    public void resetWalls() {
        for(Wall wall : outerWalls.values()) {
            wall.disconnect();
        }
    }
}
