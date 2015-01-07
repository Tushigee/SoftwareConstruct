package pingball.server;

import javax.json.JsonObject;

import pingball.game.gameobjects.Wall;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.networking.MessageProcessor;
import pingball.networking.MessageType;
import pingball.networking.WallConnectionInformationMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Server that listens for pingball clients (that have a named board) wanting to connect.  Creates new Threads to
 *   send and receive messages to/from the clients.  Also creates thread that listens for terminal commands for joining
 *   two boards that belong to clients.  This class is the core of the server system - it starts all of the other
 *   threads that handle some server functionality individually
 *  This class is IMMUTABLE, and THREAD SAFE. 
 * Rep Invariant: serverSocket is not null, connectionManager is not null, routingManager is not null,
 */

// THREAD SAFETY ARGUMENT:
//      Server is thread safe since you can run only one server for certain address of host. 
//      All the internal classes and methods such as ConnectionManager and RoutingManager 
//      are thread safe as stated in README.md, and in their own thread safety argument. 

public class PingballServer {

    /**
     * Whether or not network messages should be printed to standard output.
     * Value for production:  false
     */
    private static final boolean ENABLE_PRINTING_OF_MESSAGES = false;

    /** Default server port. */
    public static final int DEFAULT_PORT = 10987;
    /** Maximum port number as defined by ServerSocket. */
    private static final int MAXIMUM_PORT = 65535;

    /** Socket for receiving incoming connections. */
    private final ServerSocket serverSocket;

    /**
     * The connection manager for managing inboxes/outboxes for connected
     *   clients
     */
    private final ConnectionManager connectionManager = new ConnectionManager();

    /**
     * Routing manager for managing which clients walls are connected to other clients
     *   walls
     */
    private final RoutingManager routingManager = new RoutingManager();
    
    /**
     * The input stream from which server commands are read
     */
    private final InputStream in;

    /**
     * The pattern for a legal board name
     */
    private static final String regexSubpatternName = "[A-Za-z_][A-Za-z_0-9]*";

    /**
     * The pattern for a legal input to the server
     */
    private static final Pattern inputRegex = Pattern.compile("^\\s*(h|v)\\s+(" + regexSubpatternName
            + ")\\s+(" + regexSubpatternName + ")\\s*$");

    /**
     * The prompt to give the user if a command-line command is unrecognized
     */
    private static final String unrecognizedCommand = "Unrecognized server command.";

    /**
     * Creates a new pingball server
     * @param port the port on which to run the server
     * @param in the input stream from which to read server commands
     * @throws IOException
     */
    public PingballServer(int port, InputStream in) throws IOException {
        serverSocket = new ServerSocket(port);
        this.in = in;
    }

    /**
     * Asserts that rep invariants are maintained
     */
    private void checkRep() {
        assert this.connectionManager != null;
        assert this.serverSocket != null;
        assert this.routingManager != null;
    }

    /**
     * Run the server, listening for client connections and handling them.
     * Never returns unless an exception is thrown.
     *
     * @throws IOException if the main server socket is broken
     *                     (IOExceptions from individual clients do *not* terminate serve())
     */
    public void serve() throws IOException {

        // Create a thread for handling command line input
        new Thread(){
            /**
             * Send information to toBoard telling it that it is
             *   connected to connectedWith
             * @param toBoard the object which will be informed of its
             *   new connection
             * @param connectedWith the object which toBoard is connected
             *   with
             * @throws InterruptedException if the operation was interrupted
             *   before finishing
             */
            private void sendConnectionInformation(BoardObjectInContext toBoard, BoardObjectInContext connectedWith, boolean connected) throws InterruptedException {
                BlockingQueue<JsonObject> outbox = connectionManager.lookupByName(toBoard.getBoardName()).getOutbox();
                WallConnectionInformationMessage wcim = new WallConnectionInformationMessage(toBoard, connectedWith, connected);
                outbox.put(MessageProcessor.createMessage(MessageType.WALL_CONNECTION_INFORMATION_MESSAGE, wcim));
            }
            
            /**
             * Parses and executes the command, string
             * 
             * @param string the command to parse and execute
             */
            private void inputHandler(String string) {
                Matcher parsed = inputRegex.matcher(string);
                if (! parsed.matches()) {
                    System.err.println(unrecognizedCommand);
                    return;
                }
                boolean horizontal = parsed.group(1).equals("h");
                String board1 = parsed.group(2);
                String board2 = parsed.group(3);
                synchronized(connectionManager) { 
                    if (! connectionManager.containsConnection(board1)) {
                        System.err.println("Board not connected:  " + board1);
                        return;
                    }
                    if (! connectionManager.containsConnection(board2)) {
                        System.err.println("Board not connected:  " + board2);
                        return;
                    }
                    BoardObjectInContext boardObj1;
                    BoardObjectInContext boardObj2;
                    if (horizontal) {
                        boardObj1 = new BoardObjectInContext(board1, 
                                BoardObjectType.WALL, "", Wall.WallPosition.RIGHT);
                        boardObj2 = new BoardObjectInContext(board2, 
                                BoardObjectType.WALL, "", Wall.WallPosition.LEFT);
                    } else {
                        boardObj1 = new BoardObjectInContext(board1, 
                                BoardObjectType.WALL, "", Wall.WallPosition.BOTTOM);
                        boardObj2 = new BoardObjectInContext(board2, 
                                BoardObjectType.WALL, "", Wall.WallPosition.TOP);
                    }
                    if (routingManager.containsRouteFrom(boardObj1)) {
                        try {
                            sendConnectionInformation(boardObj1, routingManager.getRoute(boardObj1), false);
                            sendConnectionInformation(routingManager.getRoute(boardObj1), boardObj1, false);
                        } catch(InterruptedException e) {
                            // pass
                        }
                        routingManager.removeRoute(boardObj1);
                    }
                    if (routingManager.containsRouteFrom(boardObj2)) {
                        try {
                            sendConnectionInformation(boardObj2, routingManager.getRoute(boardObj2), false);
                            sendConnectionInformation(routingManager.getRoute(boardObj2), boardObj2, false);
                        } catch(InterruptedException e) {
                            // pass
                        }
                        routingManager.removeRoute(boardObj2);
                    }
                    routingManager.addRoute(boardObj1, boardObj2);
                    try {
                        sendConnectionInformation(boardObj1, boardObj2, true);
                        sendConnectionInformation(boardObj2, boardObj1, true);
                    } catch (InterruptedException e) {
                        System.err.println("Outbox full.");
                    }
                }
            }

            @Override
            public void run(){
                checkRep();
                Scanner sc = new Scanner(PingballServer.this.in);
                while(sc.hasNextLine()) {
                    inputHandler(sc.nextLine());
                }
                sc.close();
            }
        }.start();
        while (! serverSocket.isClosed()) {

            // The socket will be closed by its containing
            //   ServerConnection object
            Socket socket = serverSocket.accept();
            ServerConnection connection = new ServerConnection(socket);
            ClientProcessor processor = new ClientProcessor(connection, connectionManager, routingManager);

            if (ENABLE_PRINTING_OF_MESSAGES) {
                connection.startDispatcher(System.out);
                connection.startReceiver(System.out);
            } else {
                connection.startDispatcher();
                connection.startReceiver();
            }
            new Thread(processor).start();
        }
        serverSocket.close();
    }

    /**
     * The main method for PingballServer (runs the server)
     * 
     * @param args accepts one argument:  "--port" and an integer
     *   representing the port to bind
     */
    public static void main(String[] args) {
        main(args, System.in);
    }
    /**
     * The main method for PingballServer (runs the server)
     * 
     * @param args accepts one argument:  "--port" and an integer
     *   representing the port to bind
     * @param in the input stream from which to read server commands
     */
    public static void main(String[] args, InputStream in) {
        // Command-line argument parsing is provided. Do not change this method.
        int port = DEFAULT_PORT;

        Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
        try {
            while ( ! arguments.isEmpty()) {
                String flag = arguments.remove();
                try {
                    if (flag.equals("--port")) {
                        port = Integer.parseInt(arguments.remove());
                        if (port < 0 || port > MAXIMUM_PORT) {
                            throw new IllegalArgumentException("port " + port + " out of range");
                        }
                    } else {
                        throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
                    }
                } catch (NoSuchElementException nsee) {
                    throw new IllegalArgumentException("missing argument for " + flag);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("unable to parse number for " + flag);
                }
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.err.println("usage: PingballServer [--port PORT]");
            return;
        }
 
        try {
            runPingballServer(port, in);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     * Starts the PingballServer, binding the specified port
     * @param port the port on which to run the server
     * @param in the input stream from which to read server commands
     * @throws IOException if there was an error binding the
     *   port
     */
    public static void runPingballServer(int port, InputStream in) throws IOException {
        PingballServer server = new PingballServer(port, in);
        server.serve();
    }
}
