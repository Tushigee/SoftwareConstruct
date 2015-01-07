package pingball.parser;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import physics.Vect;
import pingball.game.*;
import pingball.game.gameobjects.Ball;
import pingball.game.gameobjects.GameObject;
import pingball.game.gameobjects.MoveableGameObject;
import pingball.game.util.BoardNotFoundException;
import pingball.game.util.PhysicsConfiguration;
import pingball.game.util.TriggerListener;
import pingballGUI.*;

public class BoardParser {
    /* This class allows us to interact with the ANTLR created classes to create  
     * the boards that are specified in files. 
     * 
     * Thread safety: 	Upon creation, a parser stores its file contents in a final string, which is immutable. 
     * 					All parsing is done to that string, so even if the file changes contents during creation
     * 					of the board, we will only use the contents of the file when the parser is first initiated.
     * 
     * 					We can't do anything to ensure that the file is not changed while we read it, unfortunately.
     */

    /**
     * Actually makes a board out of the parsed pieces
     * @param file with board specifications, cannot be malformed
     * @return the board which meets the file's instructions
     * @throws BoardNotFoundException if the file is not found or if its contents are unreadable
     */
    private static BoardMaker getBoardMaker(File file) throws BoardNotFoundException{
        //debugging flag
        boolean debug = false;

        //Get file contents
        try{
            ArrayList<String> linesOfFile = getLinesFromFile(file);

            String fileContents = "";
            for (int i = 0; i < linesOfFile.size(); i++){
                fileContents += linesOfFile.get(i);
            }

            if (debug){
                //print file contents' string
                System.out.println(fileContents);
            }

            //Create a char stream using the contents of the file
            CharStream stream = new ANTLRInputStream(fileContents);

            //Create a stream of tokens, using the BoardFile lexer
            BoardFileLexer lexer = new BoardFileLexer(stream);
            lexer.reportErrorsAsExceptions();
            TokenStream tokens = new CommonTokenStream(lexer);

            //Put tokens -> parser
            BoardFileParser parser = new BoardFileParser(tokens);
            ParseTree tree = parser.root();

            if (debug){
                //Print the parsed text
                System.out.println(tree.toStringTree(parser));
            }
            if (debug){
                //Show the tree
                ((RuleContext)tree).inspect(parser);
            }

            //Make the walker to traverse the tree
            ParseTreeWalker walker = new ParseTreeWalker();
            BoardMaker boardMaker = new BoardMaker();
            walker.walk(boardMaker, tree);

            // return the boardmaker made by the listener
            return boardMaker;
        }
        catch (IOException ioe){
            throw new BoardNotFoundException();
        }
    }

    /**
     * Gets the lines out of a board file and returns them a List of Strings
     * @param file containing board specification
     * @return list of strings, each containing a line of the file
     * @throws IOException if file cannot be found or if the file is unreadable
     */
    private static ArrayList<String> getLinesFromFile(File file) throws IOException{
        ArrayList<String> allLines = new ArrayList<String>();
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            allLines.add(line);
            line = reader.readLine();
        }    
        reader.close();
    

        return allLines;	
    }
    
    /**
     * Returns a board from a file. Ignores all key press lines.
     * @param file with board specifications, cannot be malformed
     * @return the board which meets the file's instructions
     * @throws BoardNotFoundException if the file is not found or if its contents are unreadable
     */
    public static Board createBoard(File file) throws BoardNotFoundException{
        BoardMaker maker = getBoardMaker(file);
        Board board = maker.getBoard();
        Map<String, KeyListener> listeners = new HashMap<String, KeyListener>();
        Map<String, GameObject> keyUp = maker.getKeyUpTriggers();
        Map<String, GameObject> keyDown = maker.getKeyDownTriggers();
        Ball dispatcher = (Ball)MoveableGameObject.ball(new Vect(0,0), new Vect(0, 0));
        PhysicsConfiguration config = new PhysicsConfiguration(new Vect(0, 25),
                0.025, 0.025);
        //add key listeners to the board
        for(String key: keyUp.keySet()){
            GameObject actionObject = keyUp.get(key);
            listeners.put(key, new MagicKeyListener(new KeyListener(){
                
                private TriggerListener triggered = new TriggerListener() {
                    @Override
                    public void trigger(Ball dispatcher, PhysicsConfiguration config) {
                        actionObject.emitSpecialActionTrigger(dispatcher, config);
                    }
                };
                
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new RuntimeException("testing");
                    
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    // do nothing
                    
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (keyName.get(e.getKeyCode()).equals(key)){
                        triggered.trigger(dispatcher, config);
                    }
                    //throw new RuntimeException("keycode: "+e.getKeyCode() + keyName.get(e.getKeyCode())+key);
                }
                
            }));
        }
        
        for(String key: keyDown.keySet()){
            GameObject actionObject = keyDown.get(key);
            listeners.put(key, new MagicKeyListener(new KeyListener(){
                
                private TriggerListener triggered = new TriggerListener() {
                    @Override
                    public void trigger(Ball dispatcher, PhysicsConfiguration config) {
                        actionObject.emitSpecialActionTrigger(dispatcher, config);
                    }
                };
                
                @Override
                public void keyTyped(KeyEvent e) {
                    // do nothing
                    
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (keyName.get(e.getKeyCode()).equals(key)){
                        triggered.trigger(dispatcher, config);
                    }
                    
                }

                @Override
                public void keyReleased(KeyEvent e) {
                 // do nothing
                }
                
            }));
        }
        board.setKeyListeners(listeners);
        return board;
    }
    
    /**
     * maps key event items to names of keys used in .pb file key statements.
     */
    private final static Map<Integer,String> keyName;
    static {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(KeyEvent.VK_A, "a");
        map.put(KeyEvent.VK_B, "b");
        map.put(KeyEvent.VK_C, "c");
        map.put(KeyEvent.VK_D, "d");
        map.put(KeyEvent.VK_E, "e");
        map.put(KeyEvent.VK_F, "f");
        map.put(KeyEvent.VK_G, "g");
        map.put(KeyEvent.VK_H, "h");
        map.put(KeyEvent.VK_I, "i");
        map.put(KeyEvent.VK_J, "j");
        map.put(KeyEvent.VK_K, "k");
        map.put(KeyEvent.VK_L, "l");
        map.put(KeyEvent.VK_M, "m");
        map.put(KeyEvent.VK_N, "n");
        map.put(KeyEvent.VK_O, "o");
        map.put(KeyEvent.VK_P, "p");
        map.put(KeyEvent.VK_Q, "q");
        map.put(KeyEvent.VK_R, "r");
        map.put(KeyEvent.VK_S, "s");
        map.put(KeyEvent.VK_T, "t");
        map.put(KeyEvent.VK_U, "u");
        map.put(KeyEvent.VK_V, "v");
        map.put(KeyEvent.VK_W, "w");
        map.put(KeyEvent.VK_X, "x");
        map.put(KeyEvent.VK_Y, "y");
        map.put(KeyEvent.VK_Z, "z");
        map.put(KeyEvent.VK_0, "0");
        map.put(KeyEvent.VK_1, "1");
        map.put(KeyEvent.VK_2, "2");
        map.put(KeyEvent.VK_3, "3");
        map.put(KeyEvent.VK_4, "4");
        map.put(KeyEvent.VK_5, "5");
        map.put(KeyEvent.VK_6, "6");
        map.put(KeyEvent.VK_7, "7");
        map.put(KeyEvent.VK_8, "8");
        map.put(KeyEvent.VK_9, "9");
        map.put(KeyEvent.VK_SHIFT, "shift");
        map.put(KeyEvent.VK_CONTROL, "ctrl");
        map.put(KeyEvent.VK_ALT, "alt");
        map.put(KeyEvent.VK_META, "meta");
        map.put(KeyEvent.VK_SPACE, "space");
        map.put(KeyEvent.VK_LEFT, "left");
        map.put(KeyEvent.VK_RIGHT, "right");
        map.put(KeyEvent.VK_UP, "up");
        map.put(KeyEvent.VK_DOWN, "down");
        map.put(KeyEvent.VK_MINUS, "minus");
        map.put(KeyEvent.VK_EQUALS, "equals");
        map.put(KeyEvent.VK_BACK_SPACE, "backspace");
        map.put(KeyEvent.VK_OPEN_BRACKET, "openbracket");
        map.put(KeyEvent.VK_CLOSE_BRACKET, "closebracket");
        map.put(KeyEvent.VK_BACK_SLASH, "backslash");
        map.put(KeyEvent.VK_SEMICOLON, "semicolon");
        map.put(KeyEvent.VK_QUOTE, "quote");
        map.put(KeyEvent.VK_ENTER, "enter");
        map.put(KeyEvent.VK_COMMA, "comma");
        map.put(KeyEvent.VK_PERIOD, "period");
        map.put(KeyEvent.VK_SLASH, "slash");
        keyName = Collections.unmodifiableMap(map);}
}