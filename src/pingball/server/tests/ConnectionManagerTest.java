package pingball.server.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import pingball.server.ConnectionManager;
import pingball.server.ServerConnection;

import java.net.Socket;

/**
 * Test partitioning
 *
 * Contains by name and connection
 *   - after adding connection
 *   - after adding and removing connection
 *
 * Lookup by name
 *   - after adding connection
 *   - after adding and removing connection
 *
 * Remove by name and connection
 *   - after adding connection
 *   - without adding connection
 *
 */
public class ConnectionManagerTest {

    private static ServerConnection sc;
    private static Socket so;

    @BeforeClass
    public static void setUp() {
        so = new Socket();
        sc = new ServerConnection(so);
    }


    @Test
    public void testContainsByConnection() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.addConnection(sc);
        assertTrue(connectionManager.containsConnection(sc));
    }

    @Test
    public void testContainsByName() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.addConnection(sc, "name");
        assertTrue(connectionManager.containsConnection(sc));
        assertTrue(connectionManager.containsConnection("name"));
    }

    @Test
    public void testContainsByConnectionAfterRemove() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.addConnection(sc);
        connectionManager.removeConnection(sc);
        assertFalse(connectionManager.containsConnection(sc));
    }

    @Test
    public void testContainsByNameAfterRemove() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.addConnection(sc, "name");
        connectionManager.removeConnection("name");
        assertFalse(connectionManager.containsConnection(sc));
        assertFalse(connectionManager.containsConnection("name"));
    }

    @Test
    public void testLookUpByName() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.addConnection(sc, "name");
        assertEquals(sc, connectionManager.lookupByName("name"));
    }

    @Test
    public void testGetByNameAfterRemoveByConnection() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.addConnection(sc, "name");
        connectionManager.removeConnection(sc);
        assertEquals(null, connectionManager.lookupByName("name"));
    }

    @Test
    public void testRemoveByNameAfterAddingByConnectionOnly() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.addConnection(sc);
        assertFalse(connectionManager.removeConnection("name"));
    }

}
