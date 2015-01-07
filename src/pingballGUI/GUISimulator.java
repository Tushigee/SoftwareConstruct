package pingballGUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.PrintStream;

import javax.swing.JPanel;

import pingball.game.Board;
/**
 * Represents GUISimulator which runs, and draws pingball board on GUI. 
 * This class is MUTABLE, and NOT THREAD SAFE. 
 * @author Myanganbayar
 *
 */
public class GUISimulator extends JPanel implements Runnable{
    /**
     * Java needs to serialize this object to save it efficiently
     */
    private static final long serialVersionUID = 1L;


    // AF:  The simulation thread which handles the event loop
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
     * Represents whether current simulation is cancelled/stopped simulating 
     * false - if simulation is running
     */
    private boolean isCancelled = false;
    
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
    private double timeFactor;
    
    /**
     * Milliseconds per second
     */
    private static final long MS_PER_SECOND = 1000;
    
    /**
     * Number of pixels corresponds to one L in the board
     */
    private static final int L = 25;
    
    /**
     * Size of one side of square shaped board in L (default size). 
     */
    private static final int sizeOfBoard = 20;
    /**
     * Boolean of whether the GUI is running or not
     */
    private boolean running;
    
    
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
    public GUISimulator(PrintStream out, Board board, long loopInterval, long renderInterval, double timeFactor) {
        int totalNumberOfPixels = sizeOfBoard*L;
        this.setPreferredSize(new Dimension(totalNumberOfPixels, totalNumberOfPixels));
        this.out = out;
        this.board = board;
        this.loopInterval = loopInterval;
        this.renderInterval = renderInterval;
        this.timeFactor = timeFactor;
        this.running = true;
        board.registerKeyListeners(this);

        this.requestFocusInWindow();
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
    public GUISimulator(PrintStream out, Board board, long loopInterval, long renderInterval) {
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

    /**
     * ends the simulation and kills the thread.
     */
    void endGUISimulation() {
        running = false;
    }

    /**
     * Pauses current simulation
     */
    void pause(){
        if (running = true) {
            isCancelled = true;
        }
    }
    /**
     * Resumes currently stopped simulation
     */
    void resume(){
        if (running = true) {
            isCancelled = false;
        }
    }
    
    /**
     * sets the time factor to the given speed.
     * @param speed desired new time factor.
     */
    void setTimeFactor(double speed) {
        timeFactor = speed;
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.translate(20, 20);
        board.draw((Graphics2D) g, L);
    }
     
    @Override
    public void run() {
        long lastRenderTime = 0;
        try {
            while (running) {
                if(!isCancelled){
                    long now = System.currentTimeMillis();
                    board.advance(((double)(loopInterval)) / (double)MS_PER_SECOND * timeFactor);
                    if (now - lastRenderTime >= renderInterval) {
                        clear();
                        super.repaint();
                        lastRenderTime = now;
                    }
                    long sleepFor = loopInterval;
                    if (sleepFor < 0L) sleepFor = 0L;
                    Thread.sleep(sleepFor);

                }
                if (!running) {
                    // Killing current thread since new thread is started by PingballGUI
                    Thread.currentThread().interrupt();
                }
            }
        } catch(InterruptedException e) {
            out.println("Interrupted.  Exiting.");
        }
    }
} 
