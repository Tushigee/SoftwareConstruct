package pingball.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pingball.networking.BoardObjectInContext;

/**
 * Manages routes from one BoardObjectInContext to another, meaning the connection between any two walls or portals is
 *   encapsulated in this class
 * 
 * This includes tracking the relationships of routing balls 
 *   from the wall of one board to the wall of another board
 *  This class is MUTABLE, and THREAD SAFE
 * Rep Invariant: links is not null, for every entry x,y in links, there is an entry y,x
 */
// THREAD SAFETY ARGUMENT:
//        Every method that mutates internal field of this class is synchronized.
//        Therefore, monitor pattern prevents it from race conditions. 
//        In addition, no mutable object returned from internal methods 
//        (Clients running in different thread cannot change this object's state).  
public class RoutingManager {
    
    /**
     * The link map:  each entry is a directed edge, or route
     *   from key to value
     */
    private final Map<BoardObjectInContext, BoardObjectInContext> links;
    
    /**
     * Creates a new routing manager
     */
    public RoutingManager() {
        this.links = Collections.synchronizedMap(new HashMap<BoardObjectInContext, BoardObjectInContext>());
    }

    /**
     * Asserts that rep invariants are maintained
     */
    private void checkRep() {
        assert links != null;
        for (BoardObjectInContext obj : links.keySet()) {
            assert links.containsKey(links.get(obj));
        }
    }
    
    /**
     * Removes routes to and from the specified node
     * 
     * @param node1 the node
     * @return true if routes were removed
     */
    public synchronized boolean removeRoute(BoardObjectInContext node1) {
        if (! links.containsKey(node1)) {
            return false;
        }
        BoardObjectInContext node2 = links.get(node1);
        links.remove(node1);
        links.remove(node2);
        checkRep();
        return true;
    }
    
    /**
     * Adds a route between node1 and node2 (bidirectional)
     * @param node1 to be connected to node2
     * @param node2 to be connected to node1
     */
    public synchronized void addRoute(BoardObjectInContext node1, BoardObjectInContext node2) {
        if (links.containsKey(node1)) {
            removeRoute(node1);
        }
        if (links.containsKey(node2)) {
            removeRoute(node2);
        }
        links.put(node1, node2);
        links.put(node2, node1);
        checkRep();
    }
    
    /**
     * Check whether or not a route exists for the given
     *   node
     * @param source the node which may or may not be contained
     *   in a route
     * @return whether or not a route exists to or from source
     */
    public synchronized boolean containsRouteFrom(BoardObjectInContext source) {
        return links.containsKey(source);
    }
    
    /**
     * Retrieves the route associated with source
     * 
     * @param source the source object whose route is retrieved
     * @return the route associated with source or null if
     *   no such route exists
     */
    public synchronized BoardObjectInContext getRoute(BoardObjectInContext source) {
        return links.get(source);
    }
}
