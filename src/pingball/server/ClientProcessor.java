package pingball.server;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import javax.json.JsonException;
import javax.json.JsonObject;

import pingball.game.gameobjects.Wall;
import pingball.game.gameobjects.Wall.WallPosition;
import pingball.networking.BallRoutingMessage;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.ClientInformationMessage;
import pingball.networking.DisconnectMessage;
import pingball.networking.JsonUtils;
import pingball.networking.MessageProcessor;
import pingball.networking.MessageType;
import pingball.networking.WallConnectionInformationMessage;

/**
 * Performs the processing for a client:
 *   Interpreting messages from the client registering
 *   their effects, and sending messages to other clients
 *  This class is IMMUTABLE and THREAD SAFE
 *  
 * Rep Invariant: connection not null, connectionManager not null, routingManager not null
 */

// THREAD SAFETY ARGUMENT:
//        This class is thread safe since it only communicates with other threads through two blocking queues, 
//        inbox, and outbox. 

public class ClientProcessor implements Runnable {
    
    /**
     * The connection for which this runnable is responsible
     */
    private final ServerConnection connection;
    
    /**
     * The collection of other connections
     */
    private final ConnectionManager connectionManager;
    
    /**
     * The collection of routes between boards
     */
    private final RoutingManager routingManager;
    
    /**
     * Create a new client processor
     * 
     * @param connection the connection for which this ClientProcessor will process
     *   incoming messages
     * @param connectionManager the collection of other connections
     * @param routingManager the collection of routes between
     *   boards
     */
    public ClientProcessor(ServerConnection connection, ConnectionManager connectionManager, RoutingManager routingManager) {
        this.connection = connection;
        this.connectionManager = connectionManager;
        this.routingManager = routingManager;
        checkRep();
    }

    /**
     * Asserts that rep invariants are maintained
     */
    private void checkRep() {
        assert connection != null;
        assert connectionManager != null;
        assert routingManager != null;
    }
    
    /**
     * Processes an incoming ClientInformationMessage (the client
     *   telling the server its name)
     * @param cim the ClientInformationMessage
     */
    private void processCIM(ClientInformationMessage cim) {
        connectionManager.addConnection(connection, cim.getName());
    }
    
    /**
     * Sends a BRM to the appropriate client
     * 
     * @param brm the ball routing message
     * @throws IOException if the board is not connected
     * @throws InterruptedException if interrupted while
     *   writing to the outbox of the destination board
     */
    private void sendBRM(BallRoutingMessage brm) throws IOException, InterruptedException {
        String boardName = brm.getDestination().getBoardName();
        if (! connectionManager.containsConnection(boardName)) {
            throw new IOException("Board not connected.");
        }
        ServerConnection boardConnection = connectionManager.lookupByName(boardName);
        JsonObject message = MessageProcessor.createMessage(MessageType.BALL_ROUTING_MESSAGE, brm);
        boardConnection.getOutbox().put(message);
    }
    
    /**
     * Sends a WallConnectionInformationMessage to the specified board
     * 
     * @param wcim the WallConnectionInformationMessage
     * @param toBoardName the board to which to send the message
     * @throws InterruptedException if interrupted while
     *   writing to the outbox of the destination board
     */
    private void sendWCIM(WallConnectionInformationMessage wcim, String toBoardName) throws IOException, InterruptedException {
        if (! connectionManager.containsConnection(toBoardName)) {
            // does nothing since board is already disconnected
        }
        else{
            ServerConnection boardConnection = connectionManager.lookupByName(toBoardName);
            JsonObject message = MessageProcessor.createMessage(MessageType.WALL_CONNECTION_INFORMATION_MESSAGE, wcim);
            boardConnection.getOutbox().put(message);
        }
    }
    
    /**
     * Bounce a BallRoutingMessage back to its source
     *   (if, for example, its destination board was
     *    not connected)
     * @param brm the original BallRoutingMessage
     * @throws IOException if the source board
     *   is no longer connected either
     * @throws InterruptedException if interrupted while
     *   writing to the source board's outbox
     */
    private void bounceBack(BallRoutingMessage brm) throws IOException, InterruptedException {
        BallRoutingMessage returnBrm = new BallRoutingMessage(brm.getBall(), brm.getSource(), brm.getSource());
        sendBRM(returnBrm);
    }
    
    /**
     * Process a ball routing message, re-routing the ball
     *   if necessary
     * @param brm the ball routing message to process
     * @throws InterruptedException if interrupted while
     *   writing to a queue
     */
    private void processBRM(BallRoutingMessage brm) throws InterruptedException {
        BoardObjectInContext source = brm.getSource();
        synchronized(routingManager) {
            if (routingManager.containsRouteFrom(source)) {
                BoardObjectInContext destination = routingManager.getRoute(source);
                BallRoutingMessage updatedDestBRM = new BallRoutingMessage(brm.getBall(), brm.getSource(),
                        destination);
                try {
                    sendBRM(updatedDestBRM);
                } catch (IOException e) {
                    // The ball could not be sent
                    try {
                        // Send ball back to source:
                        bounceBack(brm);
                        // Notify client of disconnect (if a wall)
                        if (source.getId() != Wall.WallPosition.NONE) {
                            WallConnectionInformationMessage wcim = new WallConnectionInformationMessage(source, source, false);
                            sendWCIM(wcim, source.getBoardName());
                        }
                    } catch (IOException e2) {
                        // Could not send back to source
                    }
                    // Remove the route
                    routingManager.removeRoute(source);
                }
            } else {
                if (brm.getDestination() == null) {
                    return;
                }
                // Trust the client:
                try {
                    sendBRM(brm);
                } catch (IOException e) {
                    // Shouldn't have trusted the client
                    try {
                        bounceBack(brm);
                    } catch (IOException e2) {
                        // Couldn't bounce back...
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        checkRep();
        BlockingQueue<JsonObject> inbox = connection.getInbox();
        try {
            while (true) {
                JsonObject receivedMessage = inbox.take();
                if (receivedMessage == null || receivedMessage.equals(JsonUtils.getEmpyJsonObject())) {
                    // Termination message
                    break;
                }
                try {
                    MessageType messageType = MessageProcessor.getMessageType(receivedMessage);
                    switch(messageType) {
                    case CLIENT_INFORMATION_MESSAGE:
                        // Received info about client
                        ClientInformationMessage cim = MessageProcessor.extractClientInformationMessage(receivedMessage);
                        this.processCIM(cim);
                        break;
                    case BALL_ROUTING_MESSAGE:
                        // Received a ball
                        BallRoutingMessage brm = MessageProcessor.extractBallRoutingMessage(receivedMessage);
                        this.processBRM(brm);
                        break;
                    case DISCONNECT_MESSAGE:
                        String boardName =MessageProcessor.extractDisconnectMessage(receivedMessage).getName();
                        this.connectionManager.removeConnection(connection);
                        for (WallPosition connectPos: WallPosition.values()){
                            if (!connectPos.equals(WallPosition.NONE)){
                                BoardObjectInContext wall = new BoardObjectInContext(boardName, BoardObjectType.WALL, connectPos);
                                BoardObjectInContext destination = routingManager.getRoute(wall);
                                if (destination != null && !destination.getBoardName().equals(wall.getBoardName())){
                                    WallConnectionInformationMessage wcim = new WallConnectionInformationMessage(destination, wall, false);
                                    sendWCIM(wcim, destination.getBoardName());
                                }
                                routingManager.removeRoute(wall);
                            }
                        }
                        connection.kill();
                        break;
                    default:
                        throw new JsonException("Unrecognized message type.");
                    }
                } catch(JsonException e) {
                    e.printStackTrace();
                    System.err.println("Unable to parse JSON message:  " + receivedMessage.toString());
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        } catch(InterruptedException e) {
            // Thread interrupted
        }
        try {
            connection.kill();
        } catch (IOException e) {
            System.err.println("Could not kill dispatcher/receiver");
        }
        connectionManager.removeConnection(connection);
    }

}
