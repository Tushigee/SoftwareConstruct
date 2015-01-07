package pingball.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import javax.json.JsonObject;

import pingball.game.BenchmarkBoards;
import pingball.game.Board;
import pingball.game.util.BoardNotFoundException;
import pingball.networking.ClientInformationMessage;
import pingball.networking.MessageDispatcher;
import pingball.networking.MessageProcessor;
import pingball.networking.MessageReceiver;
import pingball.networking.MessageType;
import pingball.parser.BoardParser;
import pingballGUI.GUISimulator;

/**
 * Pingball Client. Deprecated. Runs a simple simulation with no options beyond
 * command line arguments. Does not run a whole GUI.
 * 
 * This class is MUTABLE
 */
public class Pingball{

    /**
     * Whether or not the board should be printed to standard output.
     * Value for production:  true
     */
    private static final boolean ENABLE_TEXT_BOARD_RENDERING = true;

    /**
     * Whether or not network messages should be printed to standard output.
     * Value for production:  false
     */
    private static final boolean ENABLE_PRINTING_OF_MESSAGES = false;
    
    /**
     * The default board layout to use if no .pb file is specified
     */
    private static final BenchmarkBoards.KnownBoard DEFAULT_LAYOUT = 
            BenchmarkBoards.KnownBoard.DEFAULT;

    /**
     *  update physics every 50 ms
     */
    private static final long loopInterval = 50L;
    /**
     *  update graphics every 100 ms
     */
    private static final long renderInterval = 100L;
    /**
     *  playback speed factor:
     *  Value for production:  1.0
     */
    private static final double playbackSpeed = 1.0;

    /**
     * The default port for the server, if not specified for
     *   multiplayer play
     */
    private static final int DEFAULT_PORT = 10987;

    /**
     * The usage, printed to the user if invalid command-line
     *   options are specified
     */
    private static final String USAGE = "Single-player:  Pingball [FILE]"
            + System.lineSeparator() + "Multiplayer:  Pingball --host HOST [--port PORT] FILE";

    /**
     * the main method of the Pingball client
     * 
     * @param args Singleplayer:  Pingball [FILE]
     * Multiplayer: Pingball --host HOST [--port PORT] FILE
     */
    public static void main(String[] args) {
        
        try {
            if (args.length == 0) {
                
                singleMachinePlay(BenchmarkBoards.generateBoard(DEFAULT_LAYOUT));
                return;
            }
            if (args.length == 1) {
                singleMachinePlay(BoardParser.createBoard(new File(args[0])));
                return;
            }
            if (args.length == 3) {
                if (!args[0].equals("--host")) {
                    System.err.println(USAGE);
                    return;
                }
                serverPlay(args[1], DEFAULT_PORT, BoardParser.createBoard(new File(args[2])));
                return;
            }
            if (args.length == 5) {
                String host = null;
                boolean hostFound = false;
                int port = 0;
                boolean portFound = false;
                for (int arg = 0; arg < args.length - 2; arg += 2) {
                    if (args[arg].equals("--host")) {
                        hostFound = true;
                        host = args[arg + 1];
                        continue;
                    }
                    if (args[arg].equals("--port")) {
                        try {
                            port = Integer.parseInt(args[arg + 1]);
                            portFound = true;
                        } catch (NumberFormatException e) {
                            System.err.println(USAGE);
                            return;
                        }
                        continue;
                    }
                }
                if (! hostFound || ! portFound) {
                    System.err.println(USAGE);
                    return;
                }
                serverPlay(host, port, BoardParser.createBoard(new File(args[4])));
                return;
            }
            System.err.println(USAGE);
        } catch (BoardNotFoundException e) {
            System.err.println("The default board was not found in the package.  Please specify a .pb file.");
        }
    }

    /**
     * Starts an offline single-player game
     * 
     * @param board the board to use
     */
    public static void singleMachinePlay(Board board) {

        board.setConnectedToServer(false);
        PrintStream ps;
        try {
            ps = System.out;
            if (! ENABLE_TEXT_BOARD_RENDERING) {
                ps = new PrintStream("/dev/null");
            }
        } catch(FileNotFoundException e) {
            System.out.println("Could not print to /dev/null on current OS.  Printing to System.out anyway.");
            ps = System.out;
        }
        //TextSimulator simulator = 
        //        new TextSimulator(ps, board, 
        //                loopInterval, renderInterval, playbackSpeed);
        GUISimulator simulator = new GUISimulator(ps, board, 
                                loopInterval, renderInterval, playbackSpeed);
        //board.registerKeyListeners(simulator);
        // Run single-threaded:
        simulator.run();
    }

    /**
     * Starts an online multi-player game
     * 
     * @param host the server to connect to
     * @param port the server's port to connect to
     * @param board the board to use
     * @return 
     */
    @SuppressWarnings("resource")
    public static GUISimulator serverPlay1(String host, int port, Board board) {
        board.setConnectedToServer(true);
        System.out.println(board.getName());
        Socket connection = null;
        try {
            // Connect to server
            connection = new Socket(host, port);


            MessageReceiver messageReceiver;
            MessageDispatcher messageDispatcher;
            if (ENABLE_PRINTING_OF_MESSAGES) {
                messageReceiver = new MessageReceiver(connection.getInputStream(), board.getInbox(), System.out);
                messageDispatcher = new MessageDispatcher(connection.getOutputStream(), board.getOutbox(), System.out);
            } else {

                messageReceiver = new MessageReceiver(connection.getInputStream(), board.getInbox());
                messageDispatcher = new MessageDispatcher(connection.getOutputStream(), board.getOutbox());
            }

            Thread recvThread = new Thread(messageReceiver);
            recvThread.start();
            Thread sendThread = new Thread(messageDispatcher);
            sendThread.start();

            JsonObject clientInfoMessage = MessageProcessor.createMessage(MessageType.CLIENT_INFORMATION_MESSAGE,
                    new ClientInformationMessage(board.getName()));
            board.getOutbox().put(clientInfoMessage);

            PrintStream ps;
            try {
                ps = System.out;
                if (! ENABLE_TEXT_BOARD_RENDERING) {
                    ps = new PrintStream("/dev/null");
                }
            } catch(FileNotFoundException e) {
                System.out.println("Could not print to /dev/null on current OS.  Printing to System.out anyway.");
                ps = System.out;
            }
            GUISimulator simulator = 
                    new GUISimulator(ps, board, 
                            loopInterval, renderInterval, playbackSpeed);
            // Run single-threaded:
            return simulator;
            
        } catch (IOException e) {
            System.err.println("Could not connect to the server.");
        } catch (InterruptedException e) {
            // Connection interrupted.  Exitting
        } 
        return null;
    }
    
    public static void serverPlay(String host, int port, Board board) {
        board.setConnectedToServer(true);
        System.out.println(board.getName());
        Socket connection = null;
        try {
            // Connect to server
            connection = new Socket(host, port);
            
            MessageReceiver messageReceiver;
            MessageDispatcher messageDispatcher;
            if (ENABLE_PRINTING_OF_MESSAGES) {
                messageReceiver = new MessageReceiver(connection.getInputStream(), board.getInbox(), System.out);
                messageDispatcher = new MessageDispatcher(connection.getOutputStream(), board.getOutbox(), System.out);
            } else {

                messageReceiver = new MessageReceiver(connection.getInputStream(), board.getInbox());
                messageDispatcher = new MessageDispatcher(connection.getOutputStream(), board.getOutbox());
            }

            Thread recvThread = new Thread(messageReceiver);
            recvThread.start();
            Thread sendThread = new Thread(messageDispatcher);
            sendThread.start();

            JsonObject clientInfoMessage = MessageProcessor.createMessage(MessageType.CLIENT_INFORMATION_MESSAGE,
                    new ClientInformationMessage(board.getName()));
            board.getOutbox().put(clientInfoMessage);

            PrintStream ps;
            try {
                ps = System.out;
                if (! ENABLE_TEXT_BOARD_RENDERING) {
                    ps = new PrintStream("/dev/null");
                }
            } catch(FileNotFoundException e) {
                System.out.println("Could not print to /dev/null on current OS.  Printing to System.out anyway.");
                ps = System.out;
            }
            
            // Run single-threaded:
            
            GUISimulator simulator = 
                    new GUISimulator(ps, board, 
                            loopInterval, renderInterval, playbackSpeed);
            // Run single-threaded:
            Thread run = new Thread(simulator);
            run.start();
            

        } catch (IOException e) {
            System.err.println("Could not connect to the server.");
        } catch (InterruptedException e) {
            // Connection interrupted.  Exitting
        } 
    }
}
