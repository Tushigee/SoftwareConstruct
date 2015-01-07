package pingball.networking.tests;

import static org.junit.Assert.*;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.json.JsonObject;

import org.junit.Test;

import physics.Vect;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.Wall;
import pingball.networking.BallRoutingMessage;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.MessageDispatcher;
import pingball.networking.MessageProcessor;
import pingball.networking.MessageType;

public class MessageDispatcherTest {
    /**
     * Testing Strategy:
     * 
     * Checking whether received messages are correctly printing to out stream. Also, separately test whether debug stream is correctly used 
     * @throws InterruptedException 
     */
    /**
     * Test class that is used to check for output stream
     * @author Myanganbayar
     *
     */
    private class MyOutputStream extends OutputStream {
        
        private List<String> lines;
        private String currentLine = "";
        
        public MyOutputStream() {
            this.lines = new ArrayList<String>();
        }
        
        public List<String> getLines() {
            return Collections.unmodifiableList(lines);
        }

        @Override
        public void write(int b) throws IOException {
            char character = (char)b;
            if (character == '\n') {
                this.lines.add(this.currentLine);
                this.currentLine = "";
            } else {
                this.currentLine += character;
            }
        }
        
    }
    

    @Test
    public void testOutStream() throws InterruptedException {
        // initializing a message dispatcher
        MyOutputStream outputStream = new MyOutputStream();
        OutputStream testOutputStream = outputStream;
        BlockingQueue<JsonObject> outbox = new LinkedBlockingQueue<>();
        MessageDispatcher testDispatcher = new MessageDispatcher(testOutputStream, outbox);
        Thread testThread = new Thread(testDispatcher);
        testThread.start();
        // to create a message
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
        // processing messages
        outbox.put(jsonMessageWithBrm);
        // giving time for other thread to run (may not pass if system processes testThread in more than 100ms)
        Thread.sleep(100);
        assertTrue(outputStream.getLines().get(0).substring(0, 294).equals(jsonMessageWithBrm.toString()));
    }

}
