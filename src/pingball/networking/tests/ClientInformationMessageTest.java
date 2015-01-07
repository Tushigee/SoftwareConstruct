package pingball.networking.tests;

import static org.junit.Assert.*;

import javax.json.JsonObject;

import org.junit.Test;

import pingball.networking.ClientInformationMessage;
import pingball.networking.JsonUtils;

public class ClientInformationMessageTest {
    /**
     * Testing Strategy:
     * 
     * Serialize object into JSON, then Parse it back to check all the  properties are equal. 
     */
    
    @Test
    public void testBRMJsonBasicWall() {
        ClientInformationMessage testCIM = new ClientInformationMessage("testBoard");
        String jsonString = testCIM.serializeJson().toString();
        JsonObject parsedJson = JsonUtils.parseObject(jsonString);
        ClientInformationMessage incomingCIM = ClientInformationMessage.parseJson(parsedJson);
        assertEquals(testCIM.getName(), incomingCIM.getName());
        
    }

}
