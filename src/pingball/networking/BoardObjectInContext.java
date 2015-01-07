package pingball.networking;

import pingball.game.gameobjects.Wall;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Associates an object (represented as BoardObjectType and EITHER name OR id) with its board (represented by board name)
 */
public class BoardObjectInContext implements JsonSerializable{
    
    /*
     * WIRE PROTOCOL:
     *   {"boardname":<BOARD NAME>,"boardObjectType":<NAMED or WALL>,"name":<NAME>,"id":<WALL POSITION>}
     *   Where <BOARD NAME> is a string representing the containing board's name,
     *     <NAMED or WALL> is a string representing the value of the BoardObjectType enum,
     *       representing whether the object referred to is an ordinary GameObject or outer wall
     *     <NAME> is a string referring to the name of the object (if NAMED)
     *     <WALL POSITION> is the position of the wall (LEFT, RIGHT, TOP, or BOTTOM) if WALL or
     *       NONE if NAMED
     */
    
    // AF: Represents BoardObjectInContext message that includes information about object such as left wall and right wall etc.., 
    //         and enable information to go across network  
          
    
    /**
     * The JSON key for board name
     */
    public static final String KEY_BOARDNAME = "boardname";
    
    /**
     * The JSON key for object type
     */
    public static final String KEY_BOARD_OBJECT_TYPE = "boardObjectType";
    
    /**
     * The JSON key for name
     */
    public static final String KEY_NAME = "name";
    
    /**
     * The JSON key for ID (what wall type it is, if a wall)
     */
    public static final String KEY_ID = "id";
    
    /**
     * The name of the board containing the object
     */
    private final String boardName;
    // Rep Invariant: should be valid board name with following specified grammar - [A-Za-z_][A-Za-z_0-9]*
    
    /**
     * The type of object (wall or named)
     */
    private final BoardObjectType boardObjectType;
    
    /**
     * The name of the object (only relevant if of the
     *   named type)
     */
    private final String name;
    
    /**
     * The "id" of the object:  what kind of wall it is
     *   (left, right, top, bottom, or none if not relevant)
     */
    private final Wall.WallPosition id;
    
    /**
     * Checks that the Representation Invariants is upheld
     */
    private void checkrep(){
        String grammarOfBoardName = "[A-Za-z_][A-Za-z_0-9]*";
        assert(this.boardName.matches(grammarOfBoardName));
    }
    
    /**
     * Creates a new BoardObjectInContext
     * 
     * @param boardName String representing the name of the board that the object is in
     * @param boardObjectType BoardObjectType indicating whether the object is a wall or named
     * @param name String representing the name of the object itself
     * @param id WallPosition indicating which wall it is on the board (top, bottom, left, right)
     */
    public BoardObjectInContext(String boardName, BoardObjectType boardObjectType, String name, Wall.WallPosition id) {
        this.boardName = boardName;
        this.boardObjectType = boardObjectType;
        this.name = name;
        this.id = id;
    }

    /**
     * Contructor for wall objects
     * @param boardName String representing the name of the board that the object is in
     * @param boardObjectType BoardObjectType indicating whether the object is a wall or named
     * @param id WallPosition indicating which wall it is on the board (top, bottom, left, right)
     */
    public BoardObjectInContext(String boardName, BoardObjectType boardObjectType, Wall.WallPosition id) {
        this(boardName, boardObjectType, "", id);
    }

    /**
     * Constructor for named objects
     * @param boardName String representing the name of the board that the object is in
     * @param boardObjectType BoardObjectType indicating whether the object is a wall or named
     * @param gadgetName String representing the name of the object itself
     */
    public BoardObjectInContext(String boardName, BoardObjectType boardObjectType, String gadgetName) {
        this(boardName, boardObjectType, gadgetName, Wall.WallPosition.NONE);
    }
    
    /**
     * @return the name of the board to which the object belongs
     */
    public String getBoardName() {
        return boardName;
    }

    /**
     * @return the type of the object (wall or named)
     */
    public BoardObjectType getBoardObjectType() {
        return boardObjectType;
    }
    
    /**
     * Pre-condition:  the object must be of type
     *   BoardObjectType.NAMED
     *   
     * @return the name of the object
     */
    public String getName() {
        return name;
    }
    
    /**
     * Pre-condition:  the object must be of type
     *   BoardObjectType.WALL
     *   
     * @return which wall (left, top, right, bottom)
     *   this object represents
     */
    public Wall.WallPosition getId() {
        return id;
    }

    @Override
    public int hashCode() {
        switch (boardObjectType) {
            case NAMED:
                return boardName.hashCode() ^ boardObjectType.toString().hashCode() ^ name.hashCode();
            case WALL:
                return boardName.hashCode() ^ boardObjectType.toString().hashCode() ^ id.toString().hashCode();
            default:
                return boardName.hashCode() ^ boardObjectType.toString().hashCode() ^ name.hashCode() ^ id.toString().hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoardObjectInContext) {
            BoardObjectInContext other = (BoardObjectInContext) obj;
            return other.boardName.equals(this.boardName) && other.boardObjectType.equals(this.boardObjectType) &&
                    other.name.equals(this.name) && other.id.equals(this.id);
        }
        return false;
    }

    @Override
    public JsonObject serializeJson() {
        JsonObjectBuilder boardBuilder = Json.createObjectBuilder();
        boardBuilder.add(KEY_BOARDNAME, this.boardName);
        boardBuilder.add(KEY_BOARD_OBJECT_TYPE, this.boardObjectType.toString());
        boardBuilder.add(KEY_NAME, this.name);
        boardBuilder.add(KEY_ID, this.id.toString());
        return boardBuilder.build();
    }

    /**
     * Parse a JSON object into a BoardObjectInContext
     * 
     * @param json the JSON object representing a BoardObjectInContext
     * @return the parsed BoardObjectInContext
     * @throws JsonException if the JSON object could not be
     *   parsed into a BoardObjectInContext
     */
    public static BoardObjectInContext parseJson(JsonObject json) throws JsonException {
        String boardname = json.getString(KEY_BOARDNAME);
        BoardObjectType type = BoardObjectType.valueOf(json.getString(KEY_BOARD_OBJECT_TYPE));
        String name = json.getString(KEY_NAME);
        Wall.WallPosition id = Wall.WallPosition.valueOf(json.getString(KEY_ID));
        BoardObjectInContext parsedBoardObjectInContext = new BoardObjectInContext(boardname, type, name, id);
        parsedBoardObjectInContext.checkrep();
        return parsedBoardObjectInContext;
    }
}
