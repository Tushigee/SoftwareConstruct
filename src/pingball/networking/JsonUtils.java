package pingball.networking;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *  Utility class for common JSON functions 
 */
public class JsonUtils {
    
    /**
     * The UTF-8 character set
     */
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    /**
     * Gets an empty json object;
     * @return an empty JSON object
     */
    public static JsonObject getEmpyJsonObject() {
        return Json.createObjectBuilder().build();
    }
    
    /**
     * Parses a String into a JSON object
     * 
     * Pre-condition:  jsonObject must not be null
     * Post-condition:  a parsed JsonObject will be returned
     *   if the string was successfully parsed, or a
     *   JsonException will be thrown if the string could
     *   not be parsed
     *   
     * @param jsonObject the string to parse
     * @return a parsed JsonObject
     * @throws JsonException if the string could not be
     *   parsed into JSON
     */
    public static JsonObject parseObject(String jsonObject) throws JsonException {
        JsonReader reader = Json.createReader(new ByteArrayInputStream(jsonObject.getBytes(UTF8)));
        JsonObject obj = reader.readObject();
        reader.close();
        return obj;
    }
    
}
