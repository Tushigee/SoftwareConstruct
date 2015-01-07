package pingball.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages all the connections to clients
 *   Encapsulates the connections that the server currently maintains and maps the names of the client boards to those
 *   connections
 *
 * Connections are represented as ServerConnection's @see ServerConnection
 * 
 * THIS CLASS IS MUTABLE
 *
 * Rep Invariant: connections is not null, mapNameToConnection is not null, mapConnectionToName is not null,
 *   for every entry x,y in mapNameToConnection, there is an entry y,x in mapConnectionToName,
 *   for every key z in mapConnectionToName, z is in connections
 */

//THREAD SAFETY ARGUMENT:
//      Every method that mutates internal field of this class is synchronized.
//      Therefore, monitor pattern prevents it from race conditions. 
//      In addition, no mutable object returned from internal methods 
//      (Clients running in different thread cannot change this object's state).

public class ConnectionManager {
    
    /**
     * The set of connections, which may or may not
     *   be alive
     */
    private final Set<ServerConnection> connections;
    
    /**
     * Maps a name to its associated connection
     * 
     * Not all connections are named, and names can be
     *   assigned later
     */
    private final Map<String, ServerConnection> mapNameToConnection;
    
    /**
     * Maps a connection to its associated name
     * 
     * Not all connections are named, and names can be assigned later
     */
    private final Map<ServerConnection, String> mapConnectionToName;
    
    /**
     * Creates a connection manager
     */
    public ConnectionManager() {
        connections = Collections.synchronizedSet(new HashSet<ServerConnection>());
        mapNameToConnection = Collections.synchronizedMap(new HashMap<String, ServerConnection>());
        mapConnectionToName = Collections.synchronizedMap(new HashMap<ServerConnection, String>());
    }

    /**
     * Asserts that rep invariants are maintained
     */
    private void checkRep() {
        assert connections != null;
        assert mapNameToConnection != null;
        assert mapConnectionToName != null;

        for (ServerConnection serverConnection : mapConnectionToName.keySet()) {
            assert mapNameToConnection.containsKey(mapConnectionToName.get(serverConnection));
            assert connections.contains(serverConnection);
        }

    }
    
    /**
     * Adds a yet-to-be-named connection to the connection manager
     * @param connection the connection to add to the connection manager
     */
    public synchronized void addConnection(ServerConnection connection) {
        connections.add(connection);
        checkRep();
    }
    
    /**
     * Adds a named connection to the connection manager
     * @param connection the connection to add to the connection manager
     * @param name the name of the connection
     */
    public synchronized void addConnection(ServerConnection connection, String name) {
        if (mapNameToConnection.containsKey(name)) {
            throw new RuntimeException("Name in use.");
        }
        connections.add(connection);
        mapNameToConnection.put(name, connection);
        mapConnectionToName.put(connection, name);
        checkRep();
    }
    
    /**
     * Removes a connection from the connection manager.
     * 
     * This DOES NOT KILL the connection
     * 
     * @param connection the connection to remove from
     *   the connection to manager
     * @return whether or not the connection was removed
     */
    public synchronized boolean removeConnection(ServerConnection connection) {
        if (mapConnectionToName.containsKey(connection)) {
            String name = mapConnectionToName.get(connection);
            mapConnectionToName.remove(connection);
            mapNameToConnection.remove(name);
        }
        checkRep();
        return connections.remove(connection);
    }
    
    /**
     * Removes a connection from the connection manager.
     * 
     * This DOES NOT KILL the connection
     * 
     * @param name the name of the connection to remove
     *   from the connection manager
     * @return whether or not the connection was removed
     */
    public synchronized boolean removeConnection(String name) {
        if (! mapNameToConnection.containsKey(name)) {
            return false;
        }
        ServerConnection connection = mapNameToConnection.get(name);
        mapNameToConnection.remove(name);
        mapConnectionToName.remove(connection);
        checkRep();
        return connections.remove(connection);
    }
    
    /**
     * Sets the name of a connection
     * 
     * Pre-condition:  connection must exist within
     *   the connection manager and name must not
     *   be in use by another connection
     *   
     * @param connection the connection to rename
     * @param name the new name of the connection
     * @throws RuntimeException if connection is not
     *   within the connection manager or if name is
     *   not available
     */
    public synchronized void setName(ServerConnection connection, String name) {
        if (! connections.contains(connection)) {
            throw new RuntimeException("Cannot set the name of an unknown connection.");
        }
        if (mapNameToConnection.containsKey(name)) {
            if (mapNameToConnection.get(connection).equals(connection)) {
                return;
            }
            throw new RuntimeException("Name in use.");
        }
        mapNameToConnection.put(name, connection);
        mapConnectionToName.put(connection, name);
        checkRep();
    }
    
    /**
     * Checks whether or not the given name is in use
     * @param name the name of the connection
     * @return whether or not the name is in use
     */
    public synchronized boolean containsConnection(String name) {
        return mapNameToConnection.containsKey(name);
    }
    
    /**
     * Checks whether or not the given connection is
     *   contained in the connection manager
     * @param connection the connection
     * @return whether or not the connection is contained
     *   in the connection manager
     */
    public synchronized boolean containsConnection(ServerConnection connection) {
        return connections.contains(connection);
    }
    
    /**
     * Looks up a connection by its name
     * @param name the name of the connection
     * @return the connection or null if not found
     */
    public synchronized ServerConnection lookupByName(String name) {
        return mapNameToConnection.get(name);
    }
}
