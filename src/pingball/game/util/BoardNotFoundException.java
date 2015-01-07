package pingball.game.util;

/**
 * Thrown when an unknown board is requested
 */
public class BoardNotFoundException extends Exception {
    
    
    /**
     * Default serial version 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new BoardNotFoundException without a
     *   message
     */
    public BoardNotFoundException() {
        super();
    }
    
    /**
     * Creates a new BoardNotFoundException with the
     *   specified message
     * @param msg the message associated with the
     *   exception
     */
    public BoardNotFoundException(String msg) {
        super(msg);
    }
}
