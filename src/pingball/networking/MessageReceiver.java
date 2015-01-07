package pingball.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;

import javax.json.JsonException;
import javax.json.JsonObject;

/**
 *  Receives messages from the input stream, parses
 *    them to JSON, and puts them in the queue (inbox) 
 *  This class is mutable, and thread safe. 
 */

//THREAD SAFETY ARGUMENT:
//      This object communicates with other threads only through its blocking queue, inbox.
//      This object writes messages to input stream, in, in deterministic order 
//      since there is only one MessageReceiver for per client, and only one thread will run it.

public class MessageReceiver implements Runnable {
    
    //AF: Represents MessageRecevier with InputStream "in" to read messages
    //               and Blocking Queue inbox to place received messages
    
    /**
     * The input stream from which to read incoming messages
     */
    private final InputStream in;
    // Representation Invariant: cannot be null
    
    /**
     * The inbox in which incoming messages are placed
     */
    private final BlockingQueue<JsonObject> inbox;
    // Representation Invariant: cannot be null
    
    /**
     * A stream to be used to print what messages are received
     *   whenever they are received
     */
    private final PrintStream debugStream;
    
    /**
     * Checks whether RI is upheld
     */
    private void checkRep(){
        assert this.in != null;
        assert this.inbox != null;
    }
    /**
     * Creates a new ClientMessageReceiver given the input
     *   stream from which messages will be read and the
     *   queue to which messages will be added
     * @param in the input stream from which messages are read 
     * @param inbox the queue to which parsed messages are added 
     * @param printMessagesToDebugStream a stream used for debugging purposes:  if provided,
     *   all incoming messages will also be printed to this stream for debugging
     *   purposes before being placed in the inbox
     */
    public MessageReceiver(InputStream in, BlockingQueue<JsonObject> inbox, PrintStream printMessagesToDebugStream) {
        this.in = in;
        this.inbox = inbox;
        this.debugStream = printMessagesToDebugStream;
    }
    
    /**
     * Creates a new ClientMessageReceiver given the input
     *   stream from which messages will be read and the
     *   queue to which messages will be added
     * @param in the input stream from which messages are read 
     * @param inbox the queue to which parsed messages are added 
     */
    public MessageReceiver(InputStream in, BlockingQueue<JsonObject> inbox) {
        this(in, inbox, null);
    }
    
    @Override
    public void run() {
        BufferedReader bufferedIn = new BufferedReader(new InputStreamReader(in));
        checkRep();
        try {
            while (true) {
                String inputString = bufferedIn.readLine();
                JsonObject parsedMessage = null;
                if (inputString == null) {
                    throw new InterruptedException("Connection closed.");
                }
                try {
                    parsedMessage = JsonUtils.parseObject(inputString);
                } catch (JsonException e) {
                    System.err.println("Could not parse message from client: '" + inputString + "'.");
                    continue;
                }
                if (this.debugStream != null) {
                    this.debugStream.println("In: " + inputString);
                }
                inbox.put(parsedMessage);
            }
        } catch(InterruptedException e) {
            // Connection to socket interrupted.  Closing receiver.
            if (this.debugStream != null) {
                this.debugStream.println("Connection to socket interrupted.  Closing receiver.");
            }
        } catch(IOException e) {
            // Could not read from socket.  Closing receiver.
            if (this.debugStream != null) {
                this.debugStream.println("Could not read from socket.  Closing receiver.");
            }
        } finally {
            try {
                inbox.put(JsonUtils.getEmpyJsonObject());
            } catch (InterruptedException e1) {
                //System.err.println("Could not send termination null signal.");
                //e1.printStackTrace();
            }
            try {
                bufferedIn.close();
            } catch (IOException e) {
                System.err.println("Input reader already closed.");
                e.printStackTrace();
            }
        }
    }
    
}
