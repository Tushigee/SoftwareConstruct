package pingball.networking.tests;

import static org.junit.Assert.assertEquals;
import javax.json.JsonObject;

import org.junit.*;

import physics.Vect;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.Wall;
import pingball.networking.BallRoutingMessage;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.JsonUtils;

public class BallRoutingMessageTest {
    /**
     * Testing Strategy:
     * 
     * Serialize object into JSON, then Parse it back to check all the  properties are equal. 
     */
    
    @Test
    public void testBRMJsonBasic() {
        Vect ballPos = new Vect(1, 2);
        Vect ballVel = new Vect(3, 4);
        Ball ball = new Ball(ballPos, ballVel);
        final String sourceBoard = "myBoard";
        final String sourceGadget = "portal1";
        final String destBoard = "yourBoard";
        final String destGadget = "portal2";
        final BoardObjectInContext source = new BoardObjectInContext(sourceBoard, 
                BoardObjectType.NAMED, sourceGadget, Wall.WallPosition.NONE);
        final BoardObjectInContext destination = new BoardObjectInContext(destBoard, 
                BoardObjectType.NAMED, destGadget, Wall.WallPosition.NONE);
        BallRoutingMessage brm = new BallRoutingMessage(ball, source, destination);
        String jsonString = brm.serializeJson().toString();
        JsonObject parsedJson = JsonUtils.parseObject(jsonString);
        BallRoutingMessage incomingBrm = BallRoutingMessage.parseJson(parsedJson);
        assertEquals(brm.getSource(), incomingBrm.getSource());
        assertEquals(brm.getDestination(), incomingBrm.getDestination());
    }
}
