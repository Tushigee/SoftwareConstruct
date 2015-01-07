/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package pingball.server.tests;

import physics.Vect;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.Wall;
import pingball.networking.*;
import pingball.server.PingballServer;

import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

/**
 * todo
 */
public class SystemTestUtil {

    /**
     * Pingball server port.
     */
    static final int port = 4000 + (int)(Math.random() * 32768);
    static final String portStr = Integer.toString(port);

    /**
     * Return the absolute path of the specified file resource on the classpath.
     * @throws java.io.IOException if a valid path to an existing file cannot be returned
     */
    private static String getResourcePath(String fileName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url == null) {
            throw new IOException("Failed to locate resource " + fileName);
        }
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException urise) {
            throw new IOException("Invalid URL: " + urise);
        }
        String path = file.getAbsolutePath();
        if ( ! file.exists()) {
            throw new IOException("File " + path + " does not exist");
        }
        return path;
    }

    /**
     * Start a PingballServer with the given debug mode and board file.
     */
    static ThreadWithObituary startPingballServer(InputStream in) throws IOException {
        return startPingballServer(in, "--port", portStr);
    }

    /**
     * Start a PingballServer with the given command-line arguments.
     */
    static ThreadWithObituary startPingballServer(InputStream in, final String... args) {
        return new ThreadWithObituary(new Runnable() {
            public void run() {
                PingballServer.main(args, in);
            }
        });
    }

    /**
     * Connect to a PingballServer and return the connected socket.
     * @param server if not null, abort connection attempts if that thread dies
     */
    static Socket connectToPingballServer(ThreadWithObituary server) throws IOException {
        Socket socket = null;
        final int MAX_ATTEMPTS = 20;
        int attempts = 0;
        do {
            try {
                socket = new Socket("127.0.0.1", port);
            } catch (ConnectException ce) {
                if (server != null && ! server.thread().isAlive()) {
                    throw new IllegalStateException("Server thread is not running", server.error());
                }
                if (++attempts > MAX_ATTEMPTS) {
                    throw new IOException("Exceeded max connection attempts", ce);
                }
                try { Thread.sleep(attempts * 10); } catch (InterruptedException ie) { };
            }
        } while (socket == null);
        socket.setSoTimeout(3000);
        return socket;
    }

    /**
     * Return the next non-empty line of input from the given stream, or null if
     * the end of the stream has been reached.
     */
    static String nextNonEmptyLine(BufferedReader in) throws IOException {
        while (true) {
            String ret = in.readLine();
            if (ret == null || ! ret.equals(""))
                return ret;
        }
    }

}

/** A thread and possibly the error that terminated it. */
class ThreadWithObituary {
    
    private final Thread thread;
    private Throwable error = null;
    
    /** Create and start a new thread. */
    ThreadWithObituary(Runnable runnable) {
        thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable error) {
                error.printStackTrace();
                ThreadWithObituary.this.error = error;
            }
        });
        thread.start();
    }
    
    /** Return the thread. */
    synchronized Thread thread() { return thread; }
    
    /** Return the error that terminated the thread, if any. */
    synchronized Throwable error() { return error; }
}

class GameScenario {

    private final BoardObjectInContext source;
    private final BoardObjectInContext destination;
    private final Ball ball;
    private final boolean serverMaintainedConnectionMapping;
    private static final int SIZE = 20;
    private static final int VELOCITY_RANGE = 50;

    GameScenario(BoardObjectInContext source, BoardObjectInContext destination, Ball ball, boolean serverMaintainedConnectionMapping) {
        this.source = source;
        this.destination = destination;
        this.ball = ball;
        this.serverMaintainedConnectionMapping = serverMaintainedConnectionMapping;
    }

    public static GameScenario generateRandomWallToWall() {
        Random random = new Random();
        Ball ball = new Ball(new Vect(random.nextDouble() * SIZE, random.nextDouble() * SIZE),
                new Vect(random.nextDouble() * VELOCITY_RANGE, random.nextDouble() * VELOCITY_RANGE));
        BoardObjectInContext source = new BoardObjectInContext("Source", BoardObjectType.WALL, Wall.WallPosition.BOTTOM);
        BoardObjectInContext destination = new BoardObjectInContext("Destination", BoardObjectType.WALL, Wall.WallPosition.TOP);
        return new GameScenario(source, destination, ball, true);
    }

    public static GameScenario generateRandomPortalToPortal() {
        Random random = new Random();
        Ball ball = new Ball(new Vect(random.nextDouble() * SIZE, random.nextDouble() * SIZE),
                new Vect(random.nextDouble() * VELOCITY_RANGE, random.nextDouble() * VELOCITY_RANGE));
        BoardObjectInContext source = new BoardObjectInContext("Source", BoardObjectType.NAMED, "sObj");
        BoardObjectInContext destination = new BoardObjectInContext("Destination", BoardObjectType.NAMED, "dObj");
        return new GameScenario(source, destination, ball, false);
    }

    public JsonObject generateBRMWithDestination(){
        return MessageProcessor.createMessage(MessageType.BALL_ROUTING_MESSAGE,
                new BallRoutingMessage(ball, source, destination));
    }

    public JsonObject generateBRMWithoutDestination(){
        return MessageProcessor.createMessage(MessageType.BALL_ROUTING_MESSAGE,
                new BallRoutingMessage(ball, source, null));
    }

    public JsonObject generateCIMSource() {
        return MessageProcessor.createMessage(MessageType.CLIENT_INFORMATION_MESSAGE,
                new ClientInformationMessage(source.getBoardName()));
    }

    public JsonObject generateCIMDestination() {
        return MessageProcessor.createMessage(MessageType.CLIENT_INFORMATION_MESSAGE,
                new ClientInformationMessage(destination.getBoardName()));
    }

    public String generateWallConnection() {
        if (source.getId() == Wall.WallPosition.TOP || source.getId() == Wall.WallPosition.BOTTOM) {
            return "v " + source.getBoardName() + " " + destination.getBoardName();
        } else {
            return "h " + source.getBoardName() + " " + destination.getBoardName();
        }
    }

    public WallConnectionInformationMessage getWCIM(boolean isConnected) {
        return new WallConnectionInformationMessage(source, destination, isConnected);
    }

    public Ball getBall() {
        return ball;
    }

    public BoardObjectInContext getSource() {
        return source;
    }

    public BoardObjectInContext getDestination() {
        return destination;
    }
}
