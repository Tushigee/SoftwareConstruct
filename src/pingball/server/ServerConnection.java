package pingball.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.json.JsonObject;

import pingball.networking.MessageDispatcher;
import pingball.networking.MessageReceiver;

/**
 * Manages a client socket, creating an inbox and outbox
 *   for the client and dispatching MessageDispatcher and
 *   MessageReceiver threads for the socket's output and
 *   input streams.
 *  This class is MUTABLE
 * Rep Invariant: inbox is not null, outbox is not null, socket is not null
 */
public class ServerConnection {
 
    /**
     * The inbox for the socket (incoming messages from the client)
     */
    private final BlockingQueue<JsonObject> inbox = new LinkedBlockingQueue<JsonObject>();
    
    /**
     * The outbox for the socket (outgoing messages to the client)
     */
    private final BlockingQueue<JsonObject> outbox = new LinkedBlockingQueue<JsonObject>();
    
    /**
     * The thread which blocks on the socket's input stream,
     *   waiting for new messages
     */
    private Thread receiver;
    
    /**
     * The thread which blocks on the outbox, waiting for
     *   messages to send to the client
     */
    private Thread sender;
    
    /**
     * Whether or not the receiver thread has been started
     */
    private boolean receiverStarted = false;
    
    /**
     * Whether or not the dispatcher thread has been started
     */
    private boolean dispatcherStarted = false;
    
    /**
     * Whether or not the client is connected
     */
    private boolean isAlive = true;
    
    /**
     * The socket connected to the client
     */
    private final Socket socket;
    
    /**
     * Creates a new server connection to the client connected
     *   through clientSocket
     *   
     * @param clientSocket the socket through which the client
     *   is connected
     */
    public ServerConnection(Socket clientSocket) {
        this.socket = clientSocket;
    }

    /**
     * Enforces the rep invariant
     */
    private void checkRep() {
        assert socket != null;
        assert inbox !=null;
        assert outbox != null;
    }
    
    /**
     * Starts the receiver thread, which reads messages from
     *   the client and adds them to the inbox
     *   
     * @param debugStream a debugging stream which, if provided,
     *   messages will also be written to in addition to being
     *   added to the inbox
     * @throws IOException if there was an error getting the client
     *   socket's input stream
     */
    public synchronized void startReceiver(PrintStream debugStream) throws IOException {
        if (this.receiverStarted) {
            throw new RuntimeException("The receiver has already been started.");
        }
        this.receiver = new Thread(new MessageReceiver(socket.getInputStream(), this.inbox, debugStream));
        this.receiver.start();
        checkRep();
    }
    
    /**
     * Starts the receiver thread, which reads messages from
     *   the client and adds them to the inbox
     *   
     * @throws IOException if there was an error getting the client
     *   socket's input stream
     */
    public synchronized void startReceiver() throws IOException {
        this.startReceiver(null);
    }
    
    /**
     * Starts the dispatcher thread, which reads messages from
     *   the client's outbox and sends them to the client
     *   
     * @param debugStream a debugging stream which, if provided,
     *   messages will also be written to in addition to being
     *   sent to the client
     * @throws IOException if there was an error getting the client
     *   socket's output stream
     */
    public synchronized void startDispatcher(PrintStream debugStream) throws IOException {
        if (this.dispatcherStarted) {
            throw new RuntimeException("The dispatcher has already been started.");
        }
        this.sender = new Thread(new MessageDispatcher(socket.getOutputStream(), this.outbox, debugStream));
        this.sender.start();
        checkRep();
    }
    
    /**
     * Starts the dispatcher thread, which reads messages from
     *   the client's outbox and sends them to the client
     *   
     * @throws IOException if there was an error getting the client
     *   socket's output stream
     */
    public synchronized void startDispatcher() throws IOException {
        this.startDispatcher(null);
    }
    
    /**
     * @return whether or not the client is still connected
     */
    public synchronized boolean isAlive() {
        return this.isAlive;
    }
    
    /**
     * @return the inbox queue for the client (containing
     *   incoming messages from the client to the server)
     */
    public BlockingQueue<JsonObject> getInbox() {
        return this.inbox;
    }
    
    /**
     * @return the outbox queue for the client (containing
     *   outgoing messages from the server to the client)
     */
    public BlockingQueue<JsonObject> getOutbox() {
        return this.outbox;
    }
    
    /**
     * Kills the connection to the client, closing the
     *   socket
     *   
     * @throws IOException if there was an error
     *   while attempting to close the connection
     */
    public synchronized void kill() throws IOException {
        this.receiver.interrupt();
        this.sender.interrupt();
        this.isAlive = false;
        this.socket.close();
        checkRep();
    }
}
