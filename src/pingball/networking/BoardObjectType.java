package pingball.networking;

/**
 * A type specifier for a BoardObjectInContext
 */
public enum BoardObjectType {
    /**
     * The BoardObjectInContext is a wall
     */
    WALL,
    /**
     * The BoardObjectInContext is a named game object (like a portal)
     */
    NAMED
}
