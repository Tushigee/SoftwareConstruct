package pingball.networking;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *  A message sent from server to client about whether a
 *    specified wall of the client is connected or not and
 *    (if so) what board and object it is connected to 
 */
public class WallConnectionInformationMessage implements JsonSerializable {
    /*
     * WIRE PROTOCOL:
     *   {"connectedTo":<ConnectedObjectInContext>,"source":<SourceObjectInContext>,"isConnected": <BOOLEAN>}
     *   Where <SourceObjectInContext> is BoardObjectInContext representing wall on the board that message is sent, serialized into JSON. 
     *   <ConnectedObjectInContext> is a BoardObjectInContext representing a wall that is connected to 
     *   wall represented by <SourceObjectInContext>,  serialized into JSON.
     *   <BOOLEAN> is boolean variable, if <BOOLEAN> is true then, client will display <ConnectedObjectInContext>'s board name on <SourceObjectInContext>
     *                                  if it is false then, client will remove a name from <SourceObjectInContext>
     */
    
    // AF: Represents wall connection information message that is used to 
    //     send information about which Board's what wall (source) is connected 
    //       to what other Board's what wall (connectedTo) 
    
    /**
     * The wall which this WCIM concern.
     * This is the wall on the board to which this message is sent
     *   to modify.
     */
    private final BoardObjectInContext source;
    // Representation Invariant: cannot be null. 
   
    /**
     * The object to which the source wall is connected
     *   (only relevant if the source wall isConnected)
     */
    private final BoardObjectInContext connectedTo; 
    
    /**
     * Whether or not the source wall is connected
     */
    private final boolean isConnected;
    
    /**
     * Checks that the RI is upheld
     */
    private void checkrep(){
        assert(!(this.source == null));
    }
    
    /**
     * @return String that needs to be printed on the wall of board
     */
    public BoardObjectInContext getConnectedComponent() {
        return this.connectedTo;
    }
    
    /**
     * @return the wall object that is connected to other walls
     */
    public BoardObjectInContext getSource() {
        return source;
    }
    
    /**
     * @return whether or not the wall is connected to a wall on the source
     *   board
     */
    public boolean getIsConnected() {
        return this.isConnected;
    }
    
    /**
     * Creates a new WallConnectionInformationMessage, instructing a client
     *   about the connection state of one of its walls
     * @param source the wall which will be connected or disconnected by this WCIM
     * @param connectedTo the object to which source is connect (irrelevant if not
     *   connected)
     * @param isConnected whether or not the source wall is connected
     */
    public WallConnectionInformationMessage(BoardObjectInContext source, BoardObjectInContext connectedTo, boolean isConnected) {
        this.connectedTo = connectedTo;
        this.source = source;
        this.isConnected = isConnected;
    }
    
    @Override
    public String toString() {
        return this.serializeJson().toString();
    }

    @Override
    public JsonObject serializeJson() {
        JsonObjectBuilder messageBuilder = Json.createObjectBuilder();
        messageBuilder.add("connectedTo", this.connectedTo.serializeJson());
        messageBuilder.add("source", this.source.serializeJson());
        messageBuilder.add("isConnected", this.isConnected);
        return messageBuilder.build();
    }
    
    /**
     * Generates a BallRoutingMessage from a JSON object representing a BallRoutingMessage
     * @param json the JSON object representing a serialized BallRoutingMessage
     * @return the parsed BallRoutingMessage
     * @throws JsonException if the JSON was not able to be parsed
     *   into a BallRoutingMessage
     */
    public static WallConnectionInformationMessage parseJson(JsonObject json) throws JsonException{
        BoardObjectInContext source = null;
        if (! json.isNull("source")) {
             source = BoardObjectInContext.parseJson(json.getJsonObject("source"));
        }
        BoardObjectInContext connectedTo = null;
        if (! json.isNull("connectedTo")) {
             connectedTo = BoardObjectInContext.parseJson(json.getJsonObject("connectedTo"));
        }
        boolean isConnected = false;
        if (! json.isNull("isConnected")) {
             isConnected = json.getBoolean("isConnected");
        }
        WallConnectionInformationMessage parsedWCIM = new WallConnectionInformationMessage(source, connectedTo, isConnected);
        parsedWCIM.checkrep();
        return parsedWCIM;
    }
    
}
