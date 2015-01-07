package pingball.networking;

import javax.json.JsonObject;

/**
 * An object which can be serialized into JSON
 *   and then parsed back into JSON is
 *   JSONSerializable
 *   
 * The individual classes should also have
 *   a static parseJson method for converting
 *   JSON back into their objects
 */
public interface JsonSerializable {
    
    /**
     * Serializes the current object into
     *   a representation of the object in JSON
     *   
     * This does not mutate the original object
     * 
     * @return the JSON representing the object
     */
    public JsonObject serializeJson();
    
}
