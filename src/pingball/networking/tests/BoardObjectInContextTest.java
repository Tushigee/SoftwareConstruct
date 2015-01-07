package pingball.networking.tests;

import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.Test;

import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.JsonUtils;

public class BoardObjectInContextTest {

    /**
     * Testing Strategy:
     * 
     * Serialize object into JSON, then Parse it back to check all the  properties are equal. 
     */
    
    // Testing for wall type
    @Test
    public void testBRMJsonBasicWall() {
        BoardObjectInContext testBOIC = new BoardObjectInContext("test1", BoardObjectType.WALL, "RightWall");
        String jsonString = testBOIC.serializeJson().toString();
        JsonObject parsedJson = JsonUtils.parseObject(jsonString);
        BoardObjectInContext incomingBOIC = BoardObjectInContext.parseJson(parsedJson);
        assertEquals(testBOIC, incomingBOIC);
        
    }
    // Testing for named type
    @Test
    public void testBRMJsonBasicNAMED() {
        BoardObjectInContext testBOIC = new BoardObjectInContext("test1", BoardObjectType.NAMED, "hi");
        String jsonString = testBOIC.serializeJson().toString();
        JsonObject parsedJson = JsonUtils.parseObject(jsonString);
        BoardObjectInContext incomingBOIC = BoardObjectInContext.parseJson(parsedJson);
        assertEquals(testBOIC, incomingBOIC);
        
    }
    
}
