package pingball.parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import physics.Vect;
import pingball.game.util.*;
import pingball.game.gameobjects.*;
import pingball.game.*;
import pingball.game.gameobjects.bumpers.*;
import pingball.parser.BoardFileParser.CommentLineContext;
import pingball.parser.BoardFileParser.KeyContext;
import pingball.parser.BoardFileParser.KeyLineContext;
import pingball.parser.BoardFileParser.KeySpecContext;


/*
 * An implementation of the listener for the BoardFile tree, 
 * which creates a board from the file specifications
 * 
 * This BoardMaker class starts by setting defaults, upon entering boardLine, 
 * and then edits them as necessary as per file specifications.
 * 
 * Since all of these functions take an BoardFileParser.[FOO]Context object as input,
 * they can only really be called by the walker which is generated in BoardParser.
 * 
 * They are public as an artifact of the antlr infrastructure. Can't change that.
 */
public class BoardMaker implements BoardFileListener {
    
    private static PhysicsConfiguration DEFAULT_CONFIG = new PhysicsConfiguration(new Vect(0, 25),
            0.025, 0.025);

	//Board to be returned, temporarily set to a dummy, nonnull board
	Board board = new Board("ERROR", 20, 20, DEFAULT_CONFIG, false);
	//Temporary storage for gadget attributes
	private HashMap<String,String> gameObjectAttributes = new HashMap<String,String>();
	//Temporary storage for board attributes
	private HashMap<String,String> boardAttributes = new HashMap<String,String>();
	//Stationary game objects to be added to the board
	private HashMap<String,StationaryGameObject> nameToStationary = new HashMap<String,StationaryGameObject>();
	//Moveable game objects to be added to the board
	private HashMap<String, MoveableGameObject> nameToMoveable = new HashMap<String, MoveableGameObject>();
	//Balls to be added to the board
    private HashMap<String, Ball> nameToBall = new HashMap<String, Ball>();
	//Flag to only capture the first nameSpec as board name
	private boolean hasPulledBoardName = false;
	//maps key names to GameObjects triggered by that key when the key is released
	private Map<String, GameObject> keyUpTriggers = new HashMap<String, GameObject>();
	//maps key names to GameObjects triggered by that key when the key is pressed
	private Map<String, GameObject> keyDownTriggers = new HashMap<String, GameObject>();
	
	/**
	 * Returns a board for use by the program
	 * @return a board
	 */
	public Board getBoard(){
		return this.board;
	}
	
	/**
	 * get the key up triggers that should be associated with the board.
	 * @return a mapping from key name strings to game objects that should be triggered
	 * when the user releases the key with the specified key name.
	 */
	public Map<String, GameObject> getKeyUpTriggers(){
	    return new HashMap<String, GameObject>(keyUpTriggers);
	}
	
	/**
     * get the key down triggers that should be associated with the board.
     * @return a mapping from key name strings to game objects that should be triggered
     * when the user presses the key with the specified key name.
     */
	public Map<String, GameObject> getKeyDownTriggers(){
        return new HashMap<String, GameObject>(keyDownTriggers);
    }
	
	@Override public void enterRoot(BoardFileParser.RootContext ctx){}
	/**
	 * Upon exit of root (end of successful parsing) add all of the gadgets 
	 * and balls to the board 
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitRoot(BoardFileParser.RootContext ctx){
		for (String name : nameToMoveable.keySet()){
            board.addMoveableObject(name, nameToMoveable.get(name));
        }
		for (String name : nameToStationary.keySet()){
            board.addStationaryObject(name, nameToStationary.get(name));
        }
		for (String name : nameToBall.keySet()){
            board.addBall(name, nameToBall.get(name));
        }
		board.toString();
		boardAttributes.clear();
	}
	
	/**
	 * Set the defaults for a board, when a board line is entered
	 * This allows for only some of the attributes to be specified by the program
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterBoardLine(BoardFileParser.BoardLineContext ctx){
		boardAttributes.clear();
		boardAttributes.put("gravity", "25.");
		boardAttributes.put("friction1", ".025");
		boardAttributes.put("friction2", ".025");
	}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override public void exitBoardLine(BoardFileParser.BoardLineContext ctx){	
	    PhysicsConfiguration config = new PhysicsConfiguration(new Vect(0, Double.parseDouble(boardAttributes.get("gravity"))),
                Double.parseDouble(boardAttributes.get("friction1")), 
                Double.parseDouble(boardAttributes.get("friction2")));
        board = new Board(boardAttributes.get("name"), 20, 20, config, false);
	}
	
	/**
	 * The first time this is called, store name as a board attribute
	 * After that, store name as a board element attribute
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitNameSpec(BoardFileParser.NameSpecContext ctx){
		if (!hasPulledBoardName){
		    String name = ctx.getChild(2).getText();
			boardAttributes.put("name", name);
			hasPulledBoardName = true;
		}
		else{
			gameObjectAttributes.put("name", ctx.getChild(2).getText());
		}
	}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override public void enterFriction2Spec(BoardFileParser.Friction2SpecContext ctx){}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override public void exitFriction2Spec(BoardFileParser.Friction2SpecContext ctx){}

	/**
	 * Set default otherBoard to current board name, in case it is not otherwise specified
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterPortalLine(BoardFileParser.PortalLineContext ctx){
		gameObjectAttributes.clear();
		gameObjectAttributes.put("otherBoard", boardAttributes.get("name"));
	}
	
	/**
	 * Make a portal and add it to the list of gadgets associated with their names
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitPortalLine(BoardFileParser.PortalLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Vect position = new Vect(Integer.parseInt(gameObjectAttributes.get("x_value")), Integer.parseInt(gameObjectAttributes.get("y_value")));
		String otherBoard = gameObjectAttributes.get("otherBoard");
		String otherPortal = gameObjectAttributes.get("otherPortal");
		if (otherBoard != null){
		    nameToStationary.put(name, StationaryGameObject.portal(this.board, position, name, otherPortal, otherBoard));
		}
		else{
		    nameToStationary.put(name, StationaryGameObject.portal(this.board, position, name, otherPortal));
		}
		
		
		gameObjectAttributes.clear();
	}

	/**
	 * Clear board element attributes just in case
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterBallLine(BoardFileParser.BallLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Make a ball and add it to the list of balls
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitBallLine(BoardFileParser.BallLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Vect position = new Vect(Double.parseDouble(gameObjectAttributes.get("x_value")), Double.parseDouble(gameObjectAttributes.get("y_value")));
		Vect velocity = new Vect(Double.parseDouble(gameObjectAttributes.get("x_velocity")), Double.parseDouble(gameObjectAttributes.get("y_velocity")));
		nameToBall.put(name, new Ball(position, velocity));
		gameObjectAttributes.clear();
	}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterYSpec(BoardFileParser.YSpecContext ctx){}
	/**
	 * Store y value as a board element attribute 
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitYSpec(BoardFileParser.YSpecContext ctx){
		gameObjectAttributes.put("y_value", ctx.getChild(2).getText());
	}

	
	@Override
	public void enterYVelSpec(BoardFileParser.YVelSpecContext ctx){}
	/**
	 * Store y velocity as a board element attribute 
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitYVelSpec(BoardFileParser.YVelSpecContext ctx){
		gameObjectAttributes.put("y_velocity", ctx.getChild(2).getText());
	}

	/**
	 * Clear old board element attributes, just in case
	 */
	@Override
	public void enterLeftFlipLine(BoardFileParser.LeftFlipLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Make a left flipper and add it to the list of gadgets associated with their names
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitLeftFlipLine(BoardFileParser.LeftFlipLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Point<Integer> corner = new Point<Integer>(Integer.parseInt(gameObjectAttributes.get("x_value")), Integer.parseInt(gameObjectAttributes.get("y_value")));
		int orientation = Integer.parseInt(gameObjectAttributes.get("orientation"));
		nameToMoveable.put(name, MoveableGameObject.flipper(corner, true, orientation));
		gameObjectAttributes.clear();
	}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterOtherBoardSpec(BoardFileParser.OtherBoardSpecContext ctx){}
	/**
	 * Store otherBoard as a board element attribute 
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitOtherBoardSpec(BoardFileParser.OtherBoardSpecContext ctx){
		gameObjectAttributes.put("otherBoard", ctx.getChild(2).getText());
	}

	@Override
	public void enterGravitySpec(BoardFileParser.GravitySpecContext ctx){}
	@Override
	public void exitGravitySpec(BoardFileParser.GravitySpecContext ctx){}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterWidthSpec(BoardFileParser.WidthSpecContext ctx){}
	/**
	 * store width as a board element attribute
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitWidthSpec(BoardFileParser.WidthSpecContext ctx){
		gameObjectAttributes.put("width", ctx.getChild(2).getText());
	}
	
	/**
	 * Clear board element attributes just in case
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterCircBumperLine(BoardFileParser.CircBumperLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Make a circle bumper and add it to the list of gadgets associated with their names
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitCircBumperLine(BoardFileParser.CircBumperLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Vect position = new Vect(Integer.parseInt(gameObjectAttributes.get("x_value")), Integer.parseInt(gameObjectAttributes.get("y_value")));
		Bumper newBump = Bumper.circle(this.board, position);
		nameToStationary.put(name, newBump);
	}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterXSpec(BoardFileParser.XSpecContext ctx){}
	/**
	 * Store x value in board element attributes
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitXSpec(BoardFileParser.XSpecContext ctx){
		gameObjectAttributes.put("x_value", ctx.getChild(2).getText());
	}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterNameSpec(BoardFileParser.NameSpecContext ctx){}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterOtherPortalSpec(BoardFileParser.OtherPortalSpecContext ctx){}
	/**
	 * Store other portal name in board element attributes
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitOtherPortalSpec(BoardFileParser.OtherPortalSpecContext ctx){
		gameObjectAttributes.put("otherPortal", ctx.getChild(2).getText());
	}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterAntlrFloat(BoardFileParser.AntlrFloatContext ctx){}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitAntlrFloat(BoardFileParser.AntlrFloatContext ctx){}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterFriction1Spec(BoardFileParser.Friction1SpecContext ctx){}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitFriction1Spec(BoardFileParser.Friction1SpecContext ctx){}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterHeightSpec(BoardFileParser.HeightSpecContext ctx){}
	/**
	 * Store height in board element attribute
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitHeightSpec(BoardFileParser.HeightSpecContext ctx){
		gameObjectAttributes.put("height", ctx.getChild(2).getText());
	}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterXFloatSpec(BoardFileParser.XFloatSpecContext ctx){}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitXFloatSpec(BoardFileParser.XFloatSpecContext ctx){
		gameObjectAttributes.put("x_value", ctx.getChild(2).getText());
	}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterAntlrInt(BoardFileParser.AntlrIntContext ctx){}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitAntlrInt(BoardFileParser.AntlrIntContext ctx){}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterBoardElementLine(BoardFileParser.BoardElementLineContext ctx){	}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitBoardElementLine(BoardFileParser.BoardElementLineContext ctx){}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterAbsorberLine(BoardFileParser.AbsorberLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Make an absorber and add it to the list of gadgets associated with their names
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitAbsorberLine(BoardFileParser.AbsorberLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Vect position = new Vect(Integer.parseInt(gameObjectAttributes.get("x_value")), Integer.parseInt(gameObjectAttributes.get("y_value")));
		int height = Integer.parseInt(gameObjectAttributes.get("height"));
		int width = Integer.parseInt(gameObjectAttributes.get("width"));
		StationaryGameObject newAbs = StationaryGameObject.absorber(this.board, height, width, position);
		nameToStationary.put(name, newAbs);
		gameObjectAttributes.clear();
	}

	@Override public void enterBoardSpecifiers(BoardFileParser.BoardSpecifiersContext ctx){	}
	/**
	 * In a board specifier, get the attribute name & the value and store it in the lookupMap
	 * @param some context thing that the walker uses
	 */
	@Override
	//In a board specifier, get the attribute name & the value and store it in the lookupMap
	public void exitBoardSpecifiers(BoardFileParser.BoardSpecifiersContext ctx){
	    for (int i=0; i<ctx.getChildCount();i++){
	        gameObjectAttributes.put(ctx.getChild(i).getChild(0).getText(), ctx.getChild(i).getChild(2).getText());
	    }
	}

	/**
	 * Clear board element attributes just in case
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterSqBumperLine(BoardFileParser.SqBumperLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Creates a square bumper and adds it to the list which associates it with its name
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitSqBumperLine(BoardFileParser.SqBumperLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Vect position = new Vect(Integer.parseInt(gameObjectAttributes.get("x_value")), Integer.parseInt(gameObjectAttributes.get("y_value")));
		nameToStationary.put(name, Bumper.square(this.board, position));
		gameObjectAttributes.clear();
	}

	@Override
	public void enterOrientationSpec(BoardFileParser.OrientationSpecContext ctx){}
	@Override
	public void exitOrientationSpec(BoardFileParser.OrientationSpecContext ctx){
		gameObjectAttributes.put("orientation", ctx.getStop().getText());
	}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterOtherLine(BoardFileParser.OtherLineContext ctx){
	}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitOtherLine(BoardFileParser.OtherLineContext ctx){}

	/**
	 * Clear board element attributes just in case before creating trigger
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterFireLine(BoardFileParser.FireLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Adds action to the trigger gadget
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitFireLine(BoardFileParser.FireLineContext ctx){
	    Map<String, GameObject> allObjects = new HashMap<String, GameObject>();
	    for (String name: nameToStationary.keySet()){
	        allObjects.put(name, nameToStationary.get(name));
	    }
	    for (String name: nameToMoveable.keySet()){
            allObjects.put(name, nameToMoveable.get(name));
        }
		GameObject triggerGadget = allObjects.get(gameObjectAttributes.get("trigger"));
		GameObject actionGadget = allObjects.get(gameObjectAttributes.get("action"));
		triggerGadget.addCollisionTriggerListener(new TriggerListener() {
            @Override
            public void trigger(Ball dispatcher, PhysicsConfiguration config) {
                actionGadget.emitSpecialActionTrigger(dispatcher, config);
            }
        });
	}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterYFloatSpec(BoardFileParser.YFloatSpecContext ctx){}
	/**
	 * Store y value as a board element attribute
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitYFloatSpec(BoardFileParser.YFloatSpecContext ctx){
	    gameObjectAttributes.put("y_value", ctx.getChild(2).getText());
	}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterActionSpec(BoardFileParser.ActionSpecContext ctx){}
	/**
	 * Store action as a board element attribute
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitActionSpec(BoardFileParser.ActionSpecContext ctx){
		gameObjectAttributes.put("action", ctx.getChild(2).getText());
	}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterXVelSpec(BoardFileParser.XVelSpecContext ctx){}
	/**
	 * Store x velocity as a board element attribute
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitXVelSpec(BoardFileParser.XVelSpecContext ctx){
		gameObjectAttributes.put("x_velocity", ctx.getChild(2).getText());
	}
	
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterName(BoardFileParser.NameContext ctx){}
	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitName(BoardFileParser.NameContext ctx){}

	/**
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterTriggerSpec(BoardFileParser.TriggerSpecContext ctx){}
	/**
	 * Store Trigger specification as a board element attribute
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitTriggerSpec(BoardFileParser.TriggerSpecContext ctx){
		gameObjectAttributes.put("trigger", ctx.getChild(2).getText());
	}
	
	/**
	 * Clear board element attributes just in case before creating right flipper 
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterRightFlipLine(BoardFileParser.RightFlipLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Make a right flipper and add it to the list of gadgets associated with their names
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitRightFlipLine(BoardFileParser.RightFlipLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Point<Integer> corner = new Point<Integer>(Integer.parseInt(gameObjectAttributes.get("x_value")), Integer.parseInt(gameObjectAttributes.get("y_value")));
		int orientation = Integer.parseInt(gameObjectAttributes.get("orientation"));
		nameToMoveable.put(name, MoveableGameObject.flipper(corner, false, orientation));
		gameObjectAttributes.clear();
	}
	
	/**
	 * Clear board element attributes just in case before creating triangle bumper
	 * @param some context thing that the walker uses
	 */
	@Override
	public void enterTrBumperLine(BoardFileParser.TrBumperLineContext ctx){
		gameObjectAttributes.clear();
	}
	/**
	 * Make a triangle bumper and add it to the list of gadgets associated with their names
	 * @param some context thing that the walker uses
	 */
	@Override
	public void exitTrBumperLine(BoardFileParser.TrBumperLineContext ctx){
		String name = gameObjectAttributes.get("name");
		Vect position = new Vect(Integer.parseInt(gameObjectAttributes.get("x_value")), Integer.parseInt(gameObjectAttributes.get("y_value")));
		int orientation = Integer.parseInt(gameObjectAttributes.get("orientation"));
		nameToStationary.put(name, Bumper.triangle(this.board, position, orientation));
		gameObjectAttributes.clear();
	}

	@Override
	public void enterEveryRule(ParserRuleContext arg0) {	}
	@Override
	public void exitEveryRule(ParserRuleContext arg0) { }
	@Override
	public void visitErrorNode(ErrorNode arg0) {	}
	@Override
	public void visitTerminal(TerminalNode arg0) { }
	@Override
	public void enterCommentLine(CommentLineContext ctx) {	}
	@Override
	public void exitCommentLine(CommentLineContext ctx) {	}

    @Override
    public void enterKeyLine(KeyLineContext ctx) {
        gameObjectAttributes.clear();
        
    }

    @Override
    public void exitKeyLine(KeyLineContext ctx) {
        Map<String, GameObject> allObjects = new HashMap<String, GameObject>();
        for (String name: nameToStationary.keySet()){
            allObjects.put(name, nameToStationary.get(name));
        }
        for (String name: nameToMoveable.keySet()){
            allObjects.put(name, nameToMoveable.get(name));
        }
        String keyAction = ctx.getChild(0).getText();
        String triggered = gameObjectAttributes.get("action");
        String key = gameObjectAttributes.get("key");
        if (!allObjects.containsKey(triggered)){
            throw new RuntimeException("no item with name "+ triggered+" found");
        }
        GameObject triggeredObject  = allObjects.get(triggered);
        if (keyAction.equals("keydown")){
            keyDownTriggers.put(key, triggeredObject);
        }
        else if (keyAction.equals("keyup")){
            keyUpTriggers.put(key, triggeredObject);
        }
        else{
            throw new RuntimeException("bad keyline: " + keyAction);
        }
        
    }

    @Override
    public void enterKeySpec(KeySpecContext ctx) {}

    @Override
    public void exitKeySpec(KeySpecContext ctx) {
        gameObjectAttributes.put("key", ctx.getChild(2).getText());
        
    }

    @Override
    public void enterKey(KeyContext ctx) {}

    @Override
    public void exitKey(KeyContext ctx) {}
}

