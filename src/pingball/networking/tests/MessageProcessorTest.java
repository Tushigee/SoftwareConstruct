package pingball.networking.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.json.JsonObject;

import org.junit.*;

import physics.Vect;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.Wall;
import pingball.networking.BallRoutingMessage;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.JsonUtils;
import pingball.networking.MessageProcessor;
import pingball.networking.MessageType;

public class MessageProcessorTest {

    /**
     * Testing strategy:
     *   serialize/deserialize:
     *     - BallRoutingMessage
     */
  
    
    /**
     * Tests to see if a BRM can be successfully wrapped in a
     *   generic json message, sent to the server, and unwrapped
     */
    @Test
    public void testMessageBallRoutingMessageBasic() {
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
        
        JsonObject jsonMessageWithBrm = 
                MessageProcessor.createMessage(MessageType.BALL_ROUTING_MESSAGE, brm);
        
        String messageToServer = jsonMessageWithBrm.toString();
        
        // SENT OVER THE SOCKET HERE
        System.out.println(messageToServer);
        
        JsonObject incomingOnServer = JsonUtils.parseObject(messageToServer);
        MessageType type = MessageProcessor.getMessageType(incomingOnServer);
        if (! type.equals(MessageType.BALL_ROUTING_MESSAGE)) {
            fail("Unrecognized type.");
        }
        BallRoutingMessage brmOnServer = MessageProcessor.extractBallRoutingMessage(incomingOnServer);

        assertEquals(brm.getSource(), brmOnServer.getSource());
        assertEquals(brm.getDestination(), brmOnServer.getDestination());
    }
}
