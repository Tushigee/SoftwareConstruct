package pingball.networking;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Class with static methods for wrapping messages of multiple types
 *   to be passed between servers and clients
 *   
 * This class provides the methods necessary to wrap, recognize, and unwrap messages
 *   sent over the socket.  For instance, if a BallRoutingMessage was serialized to JSON
 *   and passed directly to the server, the server would not be able to recognize it as
 *   a BallRoutingMessage unless it is first wrapped by the MessageProcessor.
 */
public class MessageProcessor {
    
    /*
     * Wire protocol:
     * {"type":<MESSAGE TYPE>,"msg":<MESSAGE>}
     *   where <MESSAGE TYPE> is a string representing a value of the MessageType enum,
     *     designating the type of the wrapped message
     *   and <MESSAGE> is the message itself, serialized into a JSON object
     */
    
    //AF: represents MessageProcessor that produces various types of messages 
    
    
    /**
     * Creates a new JSON message of type, type, and a serializable
     *   payload (its type must correspond to type)
     *   
     * @param type the MessageType of the message (for example,
     *   MessageType.BALL_ROUTING_MESSAGE if payload is a BallRoutingMessage)
     * @param payload the object containing message content which will be serialized
     *   to JSON
     * @return the serialized message
     */
    public static JsonObject createMessage(MessageType type, JsonSerializable payload) {
        JsonObjectBuilder messageBuilder = Json.createObjectBuilder();
        messageBuilder.add("type", type.toString());
        messageBuilder.add("msg", payload.serializeJson());
        return messageBuilder.build();
    }
    
    /**
     * Determines the MessageType from a raw incoming JSON message
     * 
     * Calling this is a necessary step before calling one of the
     *   extract methods
     * 
     * @param rawMessage the raw JSON message whose type will be determined
     * @return the type of the object as a MessageType
     * @throws JsonException if the 'type' parameter could not be found
     *   or the value of 'type' is unknown
     */
    public static MessageType getMessageType(JsonObject rawMessage) throws JsonException {
        String type = rawMessage.getString("type");
        if (type == null) {
            throw new JsonException("Mandatory 'type' parameter not found in message JSON object.");
        }
        for (MessageType knownType : MessageType.values()) {
            if (type.toString().equals(knownType.toString())) {
                return knownType;
            }
        }
        throw new JsonException("Unrecognized 'type' parameter in message JSON object.");
    }
    
    /**
     * Extracts a BallRoutingMessage from an incoming raw JSON message
     * 
     * Pre-condition:  the rawMessage MUST be of type
     *   MessageType.BALL_ROUTING_MESSAGE
     *   This can be determined using the getMessageType method
     * 
     * @param rawMessage the incoming raw JSON message
     * @return the BallRoutingMessage contained in the JSON message
     * @throws JsonException if the 'type' parameter of the message did
     *   not correspond to a BallRoutingMessage
     */
    public static BallRoutingMessage extractBallRoutingMessage(JsonObject rawMessage) throws JsonException {
        String type = rawMessage.getString("type");
        if (type == null || ! type.equals(MessageType.BALL_ROUTING_MESSAGE.toString())) {
            throw new JsonException("Cannot convert message of type " + type + " to BallRoutingMessage.");
        }
        JsonObject payload = rawMessage.getJsonObject("msg");
        return BallRoutingMessage.parseJson(payload);
    }
    
    /**
     * Extracts a WallConnectionInformationMessage from an incoming raw JSON message
     * 
     * Pre-condition:  the rawMessage MUST be of type
     *   MessageType.WALL_CONNECTION_INFORMATION_MESSAGE
     *   This can be determined using the getMessageType method
     * 
     * @param rawMessage the incoming raw JSON message
     * @return the WallConnectionInformationMessage contained in the JSON message
     * @throws JsonException if the 'type' parameter of the message did
     *   not correspond to a WallConnectionInformationMessage
     */
    public static WallConnectionInformationMessage extractWallConnectionInformationMessage(JsonObject rawMessage) throws JsonException {
        String type = rawMessage.getString("type");
        if (type == null || ! type.equals(MessageType.WALL_CONNECTION_INFORMATION_MESSAGE.toString())) {
            throw new JsonException("Cannot convert message of type " + type + " to WallConnectionInformationMessage.");
        }
        JsonObject payload = rawMessage.getJsonObject("msg");
        return WallConnectionInformationMessage.parseJson(payload);
    }
    
    /**
     * Extracts a ClientInformationMessage from an incoming raw JSON message
     * 
     * Pre-condition:  the rawMessage MUST be of type
     *   MessageType.CLIENT_INFORMATIOIN_MESSAGE
     *   This can be determined using the getMessageType method
     * 
     * @param rawMessage the incoming raw JSON message
     * @return the ClientInformationMessage contained in the JSON message
     * @throws JsonException if the 'type' parameter of the message did
     *   not correspond to a ClientInformationMessage
     */
    public static ClientInformationMessage extractClientInformationMessage(JsonObject rawMessage) throws JsonException {
        String type = rawMessage.getString("type");
        if (type == null || ! type.equals(MessageType.CLIENT_INFORMATION_MESSAGE.toString())) {
            throw new JsonException("Cannot convert message of type " + type + " to ClientInformationMessage.");
        }
        JsonObject payload = rawMessage.getJsonObject("msg");
        return ClientInformationMessage.parseJson(payload);
    }
    public static DisconnectMessage extractDisconnectMessage(JsonObject rawMessage) throws JsonException {
        String type = rawMessage.getString("type");
        if (type == null || ! type.equals(MessageType.DISCONNECT_MESSAGE.toString())) {
            throw new JsonException("Cannot convert message of type " + type + " to DisconenctMessage.");
        }
        JsonObject payload = rawMessage.getJsonObject("msg");
        return DisconnectMessage.parseJson(payload);
    }
}
