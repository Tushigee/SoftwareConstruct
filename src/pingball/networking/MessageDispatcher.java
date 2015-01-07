package pingball.networking;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;

import javax.json.JsonObject;

/**
 *  Sends messages in the outbox queue to the provided
 *    output stream.
 *  This class is MUTABLE and THREAD SAFE. 
 */

// THREAD SAFETY ARGUMENT:
//      This object communicates with other threads only through its blocking queue, outbox.
//      This object writes messages to output stream out, in deterministic order 
//      since there is only one MessageDispatcher for per client, and only one thread will run it. 
public class MessageDispatcher implements Runnable {
    
    // AF: represents message dispatcher with BlockingQueue outbox, used to read message from
    //                          and Output stream "out "to write received messages.
    
    
    /**
     * The new line character to be used in output
     *   streams
     */
    private static final String NEWLINE = "\r\n";
    
    /**
     * The output stream associated with the message dispatcher
     */
    private final OutputStream out;
    // Representation Invariant: cannot be null
    
    /**
     * The outbox queue from which to read and dispatch
     *   messages
     */
    private final BlockingQueue<JsonObject> outbox;
    
    /**
     * A stream to be used to print what messages are sent
     *   whenever they are sent
     */
    private final PrintStream debugStream;
    
    /**
     * Checks whether RI is upheld
     */
    private void checkRep(){
        assert this.out != null;
    }
    
    /**
     * Creates a new ClientMessageDispatcher given the output
     *   stream to which messages will be written and the
     *   queue from which messages are read
     * @param out the output stream to which messages are written
     * @param outbox the queue from which messages are read
     * @param printMessagesToDebugStream a stream used for debugging purposes:  if provided,
     *   all outgoing messages will also be printed to this stream for debugging
     *   purposes
     */
    public MessageDispatcher(OutputStream out, BlockingQueue<JsonObject> outbox, PrintStream printMessagesToDebugStream) {
        this.out = out;
        this.outbox = outbox;
        this.debugStream = printMessagesToDebugStream;
    }
    
    /**
     * Creates a new ClientMessageDispatcher given the output
     *   stream to which messages will be written and the
     *   queue from which messages are read
     * @param out the output stream to which messages are written
     * @param outbox the queue from which messages are read
     */
    public MessageDispatcher(OutputStream out, BlockingQueue<JsonObject> outbox) {
        this(out, outbox, null);
    }

    @Override
    public void run() {
        BufferedWriter bufferedOut = new BufferedWriter(new OutputStreamWriter(out));
        checkRep();
        try {
            while (true) {
                String output = outbox.take().toString();
                if (this.debugStream != null) {
                    this.debugStream.println("Out: " + output);
                }
                bufferedOut.write(output + NEWLINE);
                bufferedOut.flush();
            }
        } catch(InterruptedException e) {
            // Connection to socket interrupted.  Closing dispatcher.
            if (this.debugStream != null) {
                this.debugStream.println("Connection to socket interrupted.  Closing dispatcher.");
            }
        } catch(IOException e) {
            // Could write to socket.  Closing dispatcher.
            System.out.println("io exception");
            if (this.debugStream != null) {
                this.debugStream.println("Could write to socket.  Closing dispatcher.");
            }
        }  finally {
            try {
                bufferedOut.close();
            } catch (IOException e) {
                System.err.println("Output writer already closed.");
                e.printStackTrace();
            }
        }
    }

}
