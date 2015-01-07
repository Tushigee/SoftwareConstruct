package pingballGUI;

import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pingball.client.Pingball;
import pingball.game.BenchmarkBoards;
import pingball.game.Board;
import pingball.game.util.BoardNotFoundException;
import pingball.parser.BoardParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Optional;

/**
 * The user interface for a Pingball game.
 * 
 * New feature: a slider that adjusts the speed of the game. The slider is
 * reset to 1.0 every time a board is reset.
 * 
 * MUTABLE, and contains mutable objects. See the threadsafety argument (and the
 * threadsafety arguments for all of this project) in README.md.
 * @author kennygea, btushig, dve
 */
public class PingballGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    /** GUI components */
    private final JPanel contentPane;
    private final JButton loadBoardButton;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton restartButton;
    private final JButton connectToServerButton;
    private final JButton disconnectFromServerButton;
    private final JTextField host;
    private final JLabel hostName;
    private final JTextField port;
    private final JLabel portName;
    private final JLabel connectedTo;
    private final JLabel boardName;
    private final CardLayout gameCardLayout = new CardLayout();
    private final JPanel gamePanel = new JPanel(gameCardLayout);
    private final JSlider speedSlider;

    /** Initial sizes of the GUI */
    private final int defaultSize = 100;
    private final int defaultWidth = 656;
    private final int defaultHeight = 715;
    private final int defaultFontSize = 12;

    private File boardFile = null;
    private static String hostNumber = null;
    private static int defaultPortNumber = 10987;
    private static int portNumber = defaultPortNumber;

    private PrintStream ps = System.out;
    
    /** number of milliseconds per update of the physics package*/
    private long loopInterval = 50L;
    
    /** number of milliseconds per update of the display*/
    private long renderInterval = 100L;
    private Board board;
    private GUISimulator gameSimulator;
    
    /** thread running the simulation*/
    private Thread gameThread;
    private Thread serverThread;
    
    /** whether a thread with a game simulator is currently running */
    private Boolean isRunning = false;
    
    /** determines speed of the simulation - 1.0 is normal speed, <1 are slower,
     * >1 are faster.
     */
    private double timeFactor = 1.0;
    
    /** whether the simulation is paused or not */
    private boolean isPaused = false;

    /** speed parameters for the simulation */
    static final int FPS_MIN = 1;
    static final int FPS_MAX = 25;
    static final int FPS_INIT = 10; 

    /**
     * makes a new GUI for the pingball game.
     * @throws BoardNotFoundException if the selected board cannot be found.
     */
    public PingballGUI() throws BoardNotFoundException {  
        this.addWindowListener(new DisconnectOnClose());
        /* Loading boardfile
         * Opens a window to select .pb extension files. By design, user cannot
         * choose files that do not end with the .pb extension. Then, files not formatted correctly throw an
         * exception. Then parses the .pb file and displays a non-moving board on the GUI*/
        loadBoardButton = new JButton("Select Board File");
        loadBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser("src/pingball/boards");
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new pbFilter());
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    board.disconnect();
                    connectedTo.setText("Host: SinglePlayer" + " | Port: " + portNumber);
                    isRunning = false;
                    boardFile = fc.getSelectedFile();
                    String boardString = boardFile.getName();
                    if (!boardName.getText().equals(boardString)) {
                        try {
                            board = BoardParser.createBoard(boardFile);
                            boardName.setText(board.getName());
                            gameSimulator.endGUISimulation();
                        } catch (BoardNotFoundException|RuntimeException e1) {
                            boardName.setText("NOT A VALID BOARD FILE");
                            try {
                                board = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.EMPTY);
                            } catch (BoardNotFoundException e2) {
                                e2.printStackTrace();
                            }
                            //e1.printStackTrace();
                        }
                        gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
                        gameSimulator.setFocusable(true);
                        board.registerKeyListeners(gamePanel);
                        updateGamePanel();
                        gameSimulator.requestFocus();
                    }
                    else {
                        isRunning = false;
                        gameSimulator.endGUISimulation();
                        try {
                            board = BoardParser.createBoard(boardFile);
                        } catch (BoardNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
                        updateGamePanel();
                        gameSimulator.requestFocus();
                    }
                }
            }
        });

        /* Start button
         * Either: starts a new game, or resumes a paused game*/
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        
        

        /* Pause Button
         * Pauses the board display*/
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning == true) {
                    gameSimulator.pause();
                    isPaused = true;
                    gameSimulator.requestFocus();
                }
            }

        });
        /**
         * ActionListener restart
         * Creates a new action listener that waits for a button click. If clicked, resets the GUI
         * and disconnects from server play.
         */
        ActionListener restart = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(serverThread != null && serverThread.isAlive()) {
                    serverThread.interrupt();
                }
                isRunning = false;
                board.disconnect();
                gameSimulator.endGUISimulation();
                try {
                    board = BoardParser.createBoard(boardFile);
                } catch (BoardNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                hostNumber = null;
                portNumber = defaultPortNumber;
                connectedTo.setText("Host: SinglePlayer" + " | Port: " + portNumber);
                gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
                updateGamePanel();  
                gameSimulator.requestFocus();
            }
        };
        /* Restart button
         * Resets the visual display on the GUI. Game stops moving. Resets connection
         * to server too. */
        restartButton = new JButton("Restart");
        restartButton.addActionListener(restart);

        /*Connect to Server Button*/
        connectToServerButton = new JButton("Connect To Server");
        connectToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(serverThread.isAlive());
                serverThread = connectToServerThread();
                serverThread.start();
            }
        });
        
        /* Disconnect button action listener 
         * Waits for a button click to disconnect the board from the server. Game simulation
         * continues as normal. Balls lost are lost forever, unless reconnected. */
        ActionListener disconnect = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.disconnect();
                hostNumber = null;
                portNumber = defaultPortNumber;
                connectedTo.setText("Host: SinglePlayer" + " | Port: " + portNumber);
                gameSimulator.requestFocus();
            }
        };
        /*Button to Disconnect From Sever*/
        disconnectFromServerButton = new JButton("Disconnect");
        disconnectFromServerButton.addActionListener(disconnect);

        /* JSlider to adjust the speed of the pingball game.
         * Starts at 1x speed. Can change speed between .1x to 2.5x speed */
        speedSlider = new JSlider(SwingConstants.VERTICAL, FPS_MIN, FPS_MAX, FPS_INIT);
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int rate = source.getValue();
                    double floor = 10.0;
                    double newTimeFactor = rate/floor;
                    gameSimulator.setTimeFactor(newTimeFactor);
                }
                gameSimulator.requestFocus();
            }
        });
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setComponentOrientation (ComponentOrientation.RIGHT_TO_LEFT );

        Dictionary<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(1), new JLabel(".1"));
        int ticks = 5; 
        for (int i = 5; i <= FPS_MAX; i += ticks) {
            labelTable.put(new Integer(i), new JLabel(Double.toString(i/10.0) + "x"));
        }
        speedSlider.setSnapToTicks(true);    
        speedSlider.setLabelTable(labelTable);
        
        /*Labeling stuff*/
        
        /* Host: the IP address or Host name that the board can connect to. */
        host = new JTextField("");
        hostName = new JLabel("Host Name");
        
        /* Port: The port number for the pingball client to listen for incoming requests 
         * and make outgoing requests. */
        portName = new JLabel("Port Number");
        port = new JTextField("");
        connectedTo = new JLabel("Host: Single Player | Port: " + portNumber);
        connectedTo.setFont(new Font("Times New", Font.BOLD, defaultFontSize));
        boardName = new JLabel("Current Board: None");


        /*Setting Layout
         * Create a JPanel so we can format the layout of the GUI easier. */
        
        contentPane = new JPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(defaultSize, defaultSize, defaultWidth, defaultHeight);

        contentPane.add(loadBoardButton);
        contentPane.add(startButton);
        contentPane.add(pauseButton);
        contentPane.add(restartButton);
        contentPane.add(connectToServerButton);
        contentPane.add(disconnectFromServerButton);
        contentPane.add(host);
        contentPane.add(hostName);
        contentPane.add(portName);
        contentPane.add(port);
        contentPane.add(connectedTo);
        contentPane.add(gamePanel);
        contentPane.add(speedSlider);
        
        /* Creating a GroupLayout manager
         * This keeps the loadBoardButton, startButton, pauseButton, restartButton, and connectedToButton 
         * on one line, hostName, host, portName, port textfields and labels on the second lines,
         * connectToServerButton and disconnectFromServerButton on the third line and
         * the gameSimulator JPanel and speed JSlider on the last line.
         * Looks something like:
         * [Select Board From] [File] [Start] [Pause] [Restart] Host: Single Player | Port: 10987
         * Host Name:_____________________ Port Number: ___________________________
         * [Connect to Server] [Disconnect] 
         * _______________________
         * |                     |  | 2.5x
         * |                     |  |
         * |                     |  |
         * |                     |  | 2.0x
         * |                     |  |
         * |                     |  |
         * |                     |  | 1.0x
         * |                     |  |
         * |_____________________|  | 0.1x
         *  */
        GroupLayout layout = new GroupLayout(contentPane);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(loadBoardButton)
                        .addComponent(startButton)
                        .addComponent(pauseButton)
                        .addComponent(restartButton)
                        .addComponent(connectedTo))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(hostName)
                                .addComponent(host)
                                .addComponent(portName)
                                .addComponent(port))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(connectToServerButton)   
                                        .addComponent(disconnectFromServerButton)
                                        .addComponent(boardName))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(speedSlider).addGap(20)
                                                .addComponent(gamePanel))
                );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loadBoardButton)
                        .addComponent(startButton)
                        .addComponent(pauseButton)
                        .addComponent(restartButton)
                        .addComponent(connectedTo))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(hostName)
                                .addComponent(host)
                                .addComponent(portName)
                                .addComponent(port))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(connectToServerButton)
                                        .addComponent(disconnectFromServerButton)
                                        .addComponent(boardName)).addGap(20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                                .addComponent(gamePanel)
                                                .addComponent(speedSlider))
                );
        contentPane.setLayout(layout);
        this.setContentPane(contentPane); 
       
    }

    /**
     * checks to see if a remote location is accepting connections
     * @param hostname hostname of remote location
     * @param portNumber desired port of connection
     * @return true if the desired port is in service, false otherwise
     */
    public boolean ping(String hostname, int portNumber){
        try{
            (new Socket(hostname, portNumber)).close();
        }
        catch (IOException e){
            return false;
        }
        return true;
    }
    
    /**
     * starts the game simulator. If a game simulator thread is currently 
     * running, resumes that thread. If a game simulator thread is not currently
     * running, creates a new thread and starts the game.
     */
    private void start(){
        if (!isRunning) {
            gameThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    gameSimulator.run();
                    gameSimulator.requestFocus();
                }
            });
            gameThread.start();
            isRunning = true;
            isPaused = false;
            gameSimulator.requestFocus();
        }
        else {
            isPaused = false;
            gameSimulator.resume();
            gameSimulator.requestFocus();
        }
    }
    
    /**
     * resets the game panel and the UI. Sets the speed slider to 1.0 and
     * displays the new simulator instead of the old one.
     */
    private void updateGamePanel() {
        speedSlider.setValue(10);
        timeFactor = 1.0;
        gamePanel.removeAll();
        gamePanel.add(gameSimulator);
        gamePanel.updateUI(); 

    }
    



    /**
     * Creates a new thread to handle setting the host and port incase of unresponsive server.
     * Then starts a new game simulator. If the host/port
     * combination is invalid, gives the user an error notification and continues
     * play in single-player mode.
     * @param host hostname of the desired server. If null, starts play in
     * single-player mode.
     * @param port port number of the desired server.
     */
    private void setHostAndPort(String host, Integer port) {
        new Thread(new Runnable() { 
            @Override
            public void run () {
                hostNumber = host;
                portNumber = port;
                if (host != null) {
                    if (ping(hostNumber, portNumber)) {
                        connectedTo.setText("Host: " + hostNumber + " | Port: " + portNumber);
                        gameSimulator = Pingball.serverPlay1(hostNumber, portNumber, board);
                        updateGamePanel();
                        if (isRunning){
                            isRunning = false;
                            start();
                        }
                        if (isPaused){
                            gameSimulator.pause();
                        }
                        gameSimulator.requestFocus(); 
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid Host and/or Port Number \n"
                                + "Starting Single Player", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        setHostAndPort(null, defaultPortNumber);
                    }
                }
                else {
                    connectedTo.setText("Host: Single Player | Port: " + portNumber);
                }
            }
        }).start();
    }
    /**
     * Creates  new thread to listen for server connection and then connect, preventing GUI from getting
     * stuck if server is non-responsive.
     * @return a new Thread to run the connect to server code
     */
    
    public Thread connectToServerThread() {
        return new Thread(
        new Runnable() {
            @Override
            public void run() {
                hostNumber = host.getText();
                if (hostNumber.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "No host given, defaulting \n"
                            + "to localhost", "No Host Error", JOptionPane.ERROR_MESSAGE);
                    hostNumber = "localhost";
                    gameSimulator.requestFocus();
                }
                
                if (port.getText().matches("[0-9]+")) {
                    portNumber = Integer.valueOf(port.getText());
                }
                if (ping(hostNumber, portNumber)) {
                    board.disconnect();
                    gameSimulator.endGUISimulation();
                    gameSimulator.requestFocus();
                    host.setText("");
                    port.setText("");
                    connectedTo.setText("Host: " + hostNumber + " | Port: " + portNumber);
                    gameSimulator = Pingball.serverPlay1(hostNumber, portNumber, board);
                    if (isRunning){
                        isRunning = false;
                        start();
                    }
                    if (isPaused){
                        gameSimulator.pause();
                    }
                    updateGamePanel();
                    gameSimulator.requestFocus(); 
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid Host and/or Port Number \n"
                            + "Continuing Simulation", "Connection Error", JOptionPane.ERROR_MESSAGE);
                    host.setText("");
                    port.setText("");
                    gameSimulator.requestFocus();
                }
            }
        });
    }
    
    /**
     * initializes the GUI with the given parameters. Generally called when 
     * initializing the game from the command line.
     * @param fileBoard .pb file containing the board information. If it is
     * not present, the UI is initialized with an empty board.
     * @param host hostname to connect to. If present, starts play in multiplayer
     * mode connected to a server. If absent, starts play in single-player mode.
     * @param port port number to connect to. If not present and playing in
     * multiplayer mode, defaults to 10987.
     * @throws BoardNotFoundException if the file cannot be found.
     * @throws IOException if the server connection cannot be made.
     */
    void initialize(Optional<File> fileBoard, Optional<String> host, Optional<Integer> port) throws BoardNotFoundException, IOException {
        if ((fileBoard.isPresent()) && (host.isPresent()) && (port.isPresent())) {
            boardFile = fileBoard.get();
            board = BoardParser.createBoard(boardFile);
            boardName.setText(board.getName());
            setHostAndPort(host.get(), port.get());
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        else if ((fileBoard.isPresent()) && (host.isPresent()) && !(port.isPresent())) {
            boardFile = fileBoard.get();
            board = BoardParser.createBoard(fileBoard.get());
            boardName.setText(board.getName());
            setHostAndPort(host.get(), defaultPortNumber);
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        else if ((fileBoard.isPresent()) && !(host.isPresent()) && (port.isPresent())) {
            boardFile = fileBoard.get();
            board = BoardParser.createBoard(fileBoard.get());
            boardName.setText(board.getName());
            setHostAndPort(null, port.get());
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        else if (!(fileBoard.isPresent()) && (host.isPresent()) && (port.isPresent())) {
            boardFile = new File("src/pingball/boards/empty.pb");
            board = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.EMPTY);
            boardName.setText(board.getName());
            setHostAndPort(host.get(), port.get());
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        else if (!(fileBoard.isPresent()) && !(host.isPresent()) && (port.isPresent())) {
            boardFile = new File("src/pingball/boards/empty.pb");
            board = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.EMPTY);
            boardName.setText(board.getName());
            setHostAndPort(null, port.get());
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        else if (!(fileBoard.isPresent()) && (host.isPresent()) && !(port.isPresent())) {
            //System.out.println(6);
            boardFile = new File("src/pingball/boards/empty.pb");
            board = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.EMPTY);
            boardName.setText(board.getName());
            setHostAndPort(host.get(), defaultPortNumber);
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        else if ((fileBoard.isPresent()) && !(host.isPresent()) && !(port.isPresent())) {
            boardFile = fileBoard.get();
            board = BoardParser.createBoard(fileBoard.get());
            boardName.setText(board.getName());
            setHostAndPort(null, defaultPortNumber);
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        else if (!(fileBoard.isPresent()) && !(host.isPresent()) && !(port.isPresent())) {
            boardFile = new File("src/pingball/boards/empty.pb");
            board = BenchmarkBoards.generateBoard(BenchmarkBoards.KnownBoard.EMPTY);
            boardName.setText(board.getName());
            setHostAndPort(null, defaultPortNumber);
            gameSimulator = new GUISimulator(ps, board, loopInterval, renderInterval, timeFactor);
            updateGamePanel();
        }
        gamePanel.requestFocus();
    }
    
    /**
     * Disconnects the board when the GUI exits, to make sure all of the
     * wall connections on the server have been broken.
     */
    class DisconnectOnClose implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {
            if (board != null){
                board.disconnect();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {}

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
        
    }
}
