package pingball.networking;

/**
 * Types of messages which can be exchanged
 *   between the client and the server
 */
public enum MessageType {
    /**
     * A message concerning routing balls
     * 
     * Sent in both directions
     */
    BALL_ROUTING_MESSAGE,
    
    /**
     * A message concerning a client's board
     *   configuration
     *   
     * Sent from client to server
     */
    CLIENT_INFORMATION_MESSAGE,
    
    /**
     * A message with information about whether a given
     *   wall of a client is connected and to what board
     *   and object it is connect.
     *   
     * Sent from server to client
     */
    WALL_CONNECTION_INFORMATION_MESSAGE,
    
    /**
     * A message telling the server that the client would like to disconnect
     * from the server.
     * 
     * Sent from client to server.
     */
    DISCONNECT_MESSAGE;
}
