package pingball.game;

import java.io.PrintStream;

/**
 *  A runnable which manages running the simulation:
 *    advancing the physics engine and rendering to textual
 *    output
 *    
 *  This class is technically immutable
 */
public class TextSimulator implements Runnable {
    
    // AF:  The simulation thread which handles the even loop
    // RI:  RenderInterval >= loopInterval >= 0.  timeFactor > 0
    /**
     * Checks to ensure the RI is upheld
     */
    private void checkRep() {
        assert board != null;
        assert out != null;
        assert renderInterval >= loopInterval;
        assert loopInterval >= 0L;
        assert timeFactor > 0;
    }
    
    /**
     * The board to for which to run simulation
     */
    private final Board board;
    /**
     * How often to update the physics engine
     */
    private final long loopInterval;
    /**
     * How often to render the representation of
     *   the board
     */
    private final long  renderInterval;
    /**
     * The stream to which the representation is
     *   printed
     */
    private final PrintStream out;
    /**
     * The time factor.  1.0 is real time.
     *   Less than 1.0 is slow-mo
     *   More than 1.0 is fast-motion
     */
    private final double timeFactor;
    
    /**
     * Milliseconds per second
     */
    private static final long MS_PER_SECOND = 1000;
    
    /**
     * Creates a new Simulator based on the output stream,
     *   the board which will be simulated, the interval which
     *   the physics engine will be updated, and the interval which
     *   the graphics will be rendered
     * Pre-conditions:  RenderInterval >= loopInterval >= 0.  timeFactor > 0
     * @param out the output stream where the textual representation of
     *   the board will be printed
     * @param board the board to simulate (it WILL be modified)
     * @param loopInterval the interval (in ms) to update the physics engine
     * @param renderInterval the interval (in ms) to update the graphics
     *    -  RenderInterval must be greater than or equal to loopInterval
     * @param timeFactor used to speed up or slow down simulation playback.
     *   1.0 is default time
     *   &lt; 1.0 is slow-motion
     *   &gt; 1.0 is fast-motion
     */
    public TextSimulator(PrintStream out, Board board, long loopInterval, long renderInterval, double timeFactor) {
        this.out = out;
        this.board = board;
        this.loopInterval = loopInterval;
        this.renderInterval = renderInterval;
        this.timeFactor = timeFactor;
        checkRep();
    }
    /**
     * Creates a new Simulator based on the output stream,
     *   the board which will be simulated, the interval which
     *   the physics engine will be updated, and the interval which
     *   the graphics will be rendered
     * @param out the output stream where the textual representation of
     *   the board will be printed
     * @param board the board to simulate (it WILL be modified)
     * @param loopInterval the interval (in ms) to update the physics engine
     * @param renderInterval the interval (in ms) to update the graphics
     */
    public TextSimulator(PrintStream out, Board board, long loopInterval, long renderInterval) {
        this(out, board, loopInterval, renderInterval, 1);
    }
    
    /**
     * Number of empty lines used to clear output stream
     */
    private static final int NUMBER_EMPTY_LINES = 50;
    
    /**
     * Clears the output stream using new lines
     */
    private void clear() {
        for (int i = 0; i < NUMBER_EMPTY_LINES; ++i) {
            out.println();
        }
    }

    @Override
    public void run() {
        long lastRenderTime = 0;
        try {
            while (true) {
                long now = System.currentTimeMillis();
                board.advance(((double)(loopInterval)) / (double)MS_PER_SECOND * timeFactor);
                if (now - lastRenderTime >= renderInterval) {
                    clear();
                    board.renderText(out);
                    lastRenderTime = now;
                }
                long sleepFor = loopInterval;
                if (sleepFor < 0L) sleepFor = 0L;
                Thread.sleep(sleepFor);
            }
        } catch(InterruptedException e) {
            out.println("Interrupted.  Exiting.");
        }
    }
}
