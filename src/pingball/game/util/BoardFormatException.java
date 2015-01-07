package pingball.game.util;

/**
 * An exception thrown when a Board input file is improperly formatted 
 */
public class BoardFormatException extends Exception {
    
    /**
     * Serial version
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a BoardFormatException without a message
     */
    public BoardFormatException() {
        super();
    }
    
    /**
     * Create a BoardFormatException with the given message
     * @param message the message to include with the exception
     */
    public BoardFormatException(String message) {
        super(message);
    }
}
