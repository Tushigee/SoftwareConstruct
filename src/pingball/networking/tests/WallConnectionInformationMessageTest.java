package pingball.networking.tests;

import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.Test;

import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.JsonUtils;
import pingball.networking.WallConnectionInformationMessage;

public class WallConnectionInformationMessageTest {

    /**
     * Testing Strategy:
     * 
     * Serialize object into JSON, then Parse it back to check all the  properties are equal. 
     */
    
    @Test
    public void testWCIM() {
        BoardObjectInContext testBOIC = new BoardObjectInContext("test1", BoardObjectType.WALL, "RightWall");
        BoardObjectInContext testBOIC1 = new BoardObjectInContext("test2", BoardObjectType.WALL, "LeftWall");
        WallConnectionInformationMessage testWCIM = new WallConnectionInformationMessage(testBOIC, testBOIC1,false);
        String jsonString = testWCIM.serializeJson().toString();
        JsonObject parsedJson = JsonUtils.parseObject(jsonString);
        WallConnectionInformationMessage incomingWCIM = WallConnectionInformationMessage.parseJson(parsedJson);
        assertEquals(testWCIM.getConnectedComponent(), incomingWCIM.getConnectedComponent());
        assertEquals(testWCIM.getSource(), incomingWCIM.getSource());
        
    }
}
