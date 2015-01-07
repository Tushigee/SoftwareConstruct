package pingball.networking;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import pingball.game.gameobjects.Ball;

/**
 * Message for transferring a ball across the network 
 */
public class BallRoutingMessage implements JsonSerializable {
    
    /*
     * WIRE PROTOCOL:
     *   {"ball":<BALL>,"source":<SOURCE>,"destination":<DESTINATION>}
     *   Where <BALL> is a Ball serialized into a JSON object,
     *     <SOURCE> is a BoardObjectInContext representing the source of the message,
     *       serialized into JSON,
     *     and <DESTINATION> is a BoardObjectInContext representing the destination
     *       of the message serialized into JSON
     */
    
    // AF: Represents ball routing message that is used to transfer ball across the the network
    //                      including information about ball, source, and destination 
    
    
    /**
     * The ball which is contained in the ball routing message
     */
    private final Ball ball;
    // Rep Invariant: ball object cannot be null 
    
    /**
     * The source object
     */
    private final BoardObjectInContext source;
    
    /**
     * The destination object
     */
    private final BoardObjectInContext destination;
    
    /**
     * Checks that the RI is upheld
     */
    private void checkrep(){
        assert(!(this.ball == null));
    }
    
    
    /**
     * @return the ball the message concerns
     */
    public Ball getBall() {
        return ball;
    }
    
    /**
     * @return the source Board object (where the ball came from).
     *   THIS MAY BE NULL IF NOT SUPPLIED IN THE MESSAGE
     */
    public BoardObjectInContext getSource() {
        return source;
    }
    
    /**
     * @return the destination Board object (where the ball is going).
     *   THIS MAY BE NULL IF NOT SUPPLIED IN THE MESSAGE
     */
    public BoardObjectInContext getDestination() {
        return destination;
    }
    
    /**
     * Creates a new BallRoutingMessage of ball from source
     *   to destination
     * @param ball the ball to transfer
     * @param source the source board object (or null if unknown)
     * @param destination the destination board object (or null if unknown)
     */
    public BallRoutingMessage(Ball ball, BoardObjectInContext source,
            BoardObjectInContext destination) {
        this.ball = ball;
        this.source = source;
        this.destination = destination;
    }
    
    @Override
    public String toString() {
        return this.serializeJson().toString();
    }

    @Override
    public JsonObject serializeJson() {
        JsonObjectBuilder boardBuilder = Json.createObjectBuilder();
        boardBuilder.add("ball", this.ball.serializeJson());
        if (this.source == null) {
            boardBuilder.addNull("source");
        } else {
            boardBuilder.add("source", this.source.serializeJson());
        }
        if (this.destination == null) {
            boardBuilder.addNull("destination");
        } else {
            boardBuilder.add("destination", this.destination.serializeJson());
        }
        return boardBuilder.build();
    }
    
    /**
     * Generates a BallRoutingMessage from a JSON object representing a BallRoutingMessage
     * @param json the JSON object representing a serialized BallRoutingMessage
     * @return the parsed BallRoutingMessage
     * @throws JsonException if the JSON was not able to be parsed
     *   into a BallRoutingMessage
     */
    public static BallRoutingMessage parseJson(JsonObject json) throws JsonException{
        Ball ball = Ball.parseJson(json.getJsonObject("ball"));
        BoardObjectInContext source = null;
        if (! json.isNull("source")) {
            source = BoardObjectInContext.parseJson(json.getJsonObject("source"));
        }
        BoardObjectInContext destination = null;
        if (! json.isNull("destination")) {
            destination = BoardObjectInContext.parseJson(json.getJsonObject("destination"));
        }
        BallRoutingMessage parsedBRM = new BallRoutingMessage(ball, source, destination); 
        parsedBRM.checkrep();
        return parsedBRM;
    }
    
    
}
