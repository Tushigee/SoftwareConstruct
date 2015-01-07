package pingball.networking.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.json.JsonObject;

import org.junit.Test;

import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.ClientInformationMessage;
import pingball.networking.MessageReceiver;

public class MessageReceiverTest {

    /**
     * Testing Strategy:
     * 
     * Put a message into input stream, and checks whether correct message added to queue
     * @throws InterruptedException 
     * @throws IOException 
     */
    // Adding only one message
    @Test
    public void testOne() throws InterruptedException, IOException {
        ClientInformationMessage testCIM = new ClientInformationMessage("testBoard");
        String jsonMessageString = testCIM.serializeJson().toString();
        InputStream testIn = new ByteArrayInputStream( jsonMessageString.getBytes());
        BlockingQueue<JsonObject> testInbox = new LinkedBlockingQueue<JsonObject>();
        MessageReceiver testMR = new MessageReceiver(testIn, testInbox);
        (new Thread(testMR)).start();
        JsonObject test = testInbox.take();
        assertEquals(jsonMessageString, test.toString());
        testIn.close();
   }
    
   // Adding more than one messages
    @Test
    public void testMultiple() throws InterruptedException, IOException {
        // Messages that needs to be sent
        ClientInformationMessage testCIM = new ClientInformationMessage("testBoard");
        BoardObjectInContext testBOIC =  new BoardObjectInContext("test1", BoardObjectType.WALL, "testWall");
        
        // Converting messages into JSON
        String jsonMessageStringCIM = testCIM.serializeJson().toString();
        String jsonMessageStringBOIC = testBOIC.serializeJson().toString();
        String jsonMessageString = jsonMessageStringCIM + "\n" + jsonMessageStringBOIC;
        
        // Creating Input Stream and blocking queue
        InputStream testIn = new ByteArrayInputStream( jsonMessageString.getBytes());
        BlockingQueue<JsonObject> testInbox = new LinkedBlockingQueue<JsonObject>();
        
        // Testing Message Receiver Object
        MessageReceiver testMR = new MessageReceiver(testIn, testInbox);
        (new Thread(testMR)).start();
        
        // Checking whether first message is correct
        JsonObject test = testInbox.take();
        assertEquals(jsonMessageStringCIM, test.toString());
        
        // Checking whether second message is correct
        test = testInbox.take();
        assertEquals(jsonMessageStringBOIC, test.toString());
        testIn.close();
   } 

}
