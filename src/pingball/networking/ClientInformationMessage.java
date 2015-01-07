package pingball.networking;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * A message passed from the client to the server
 *   upon connecting
 */
public class ClientInformationMessage implements JsonSerializable {
    
    /*
     * WIRE PROTOCOL:
     *   {"name":<CLIENT NAME>}
     *   where <CLIENT NAME> is a string representing the name of the client's
     *     board
     */
    
    // AF: Represents client information message that is used to transfer information about Board with "name"
    //                      across the network
    
    /**
     * The name of the client's board
     */
    private final String name;
    // Rep Invariant: should be valid board name with following specified grammar - [A-Za-z_][A-Za-z_0-9]*
    
    /**
     * Checks that the Representation Invariants is upheld
     */
    private void checkrep(){
        String grammarOfBoardName = "[A-Za-z_][A-Za-z_0-9]*";
        assert(this.name.matches(grammarOfBoardName));
    }
    
    /**
     * Creates a new ClientInformationMessage with the
     *   given name of the client's board
     *   
     * @param name the name of the client's board
     */
    public ClientInformationMessage(String name) {
        this.name = name;
    }
    
    /**
     * @return the name of the client's board
     */
    public String getName() {
        return this.name;
    }

    @Override
    public JsonObject serializeJson() {
        JsonObjectBuilder boardBuilder = Json.createObjectBuilder();
        boardBuilder.add("name", this.name);
        return boardBuilder.build();
    }
    
    /**
     * Parses a JSONObject into a ClientInformationMessage
     * 
     * @param object the JSON object to parse
     * @return the parsed ClientInformationMessage
     * @throws JsonException if the JSON could not be parsed
     *   into a ClientInformationMessage
     */
    public static ClientInformationMessage parseJson(JsonObject object) throws JsonException {
        if (! object.containsKey("name")) {
            throw new JsonException("Key 'name' expected in ClientInformationMessage.");
        }
        ClientInformationMessage parsedCIM = new ClientInformationMessage(object.getString("name"));
        parsedCIM.checkrep();
        return parsedCIM;
    }
}
