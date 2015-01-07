package pingballGUI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import javax.swing.SwingUtilities;

import pingball.game.util.BoardNotFoundException;

/**
 * Represents a pingball game with a GUI. Not instantiated- the static main method
 * is run to create the game and GUI.   
 * MUTABLE
 */
public class Pingball {
    // Maximum port number as defined by ServerSocket 
    private static final int MAXIMUM_PORT = 65535;
    
    //hostname- if not present, connect to localhost
    private static Optional<String> host = Optional.empty();
    
    //port number- if not present, connect to the default port (10987)
    private static Optional<Integer> port = Optional.empty();
    
    //file to load the board from- if not present, loads an empty board.
    private static Optional<File> opFile = Optional.empty();
    
    /**
     * run a pingball game.
     * @param args in format [--host HOST] [--port PORT] [FILE] as specified
     * in the problem set specification (HOST is hostname, PORT is port number,
     * FILE is file name), all are optional
     */
    public static void main(final String[] args) {
        Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
        while ( ! arguments.isEmpty()) {
            String flag = arguments.remove();
            if (flag.equals("--host")) {
                host = Optional.of(arguments.remove());
            }
            else if (flag.equals("--port")) {
                port = Optional.of(Integer.parseInt(arguments.remove()));
                if (!(port.get() > 0 && port.get() < MAXIMUM_PORT)) {
                    throw new IllegalArgumentException("port " + port + " out of range");
                }
            }
            else {
                opFile = Optional.of(new File(flag));
            }
        }
        SwingUtilities.invokeLater(new 
                Runnable() {
            @Override
            public void run() {
                PingballGUI main;
                try {
                    main = new PingballGUI();
                    main.setTitle("Pingball Game");
                    main.initialize(opFile, host, port);
                    main.setVisible(true);
                } catch (BoardNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
