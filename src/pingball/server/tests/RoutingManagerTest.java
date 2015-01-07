package pingball.server.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import pingball.game.gameobjects.Wall;
import pingball.networking.BoardObjectInContext;
import pingball.networking.BoardObjectType;
import pingball.server.RoutingManager;
import static org.junit.Assert.*;
/**
 * Testing partition
 *
 * Contains
 *   - after adding route
 *   - after adding and removing
 *
 * Get
 *   - after adding
 *   - after adding and removing
 *
 * Remove
 *   - after adding
 *   - without adding
 */
public class RoutingManagerTest {

    private static BoardObjectInContext obj1;
    private static BoardObjectInContext obj2;

    @BeforeClass
    public static void setUp() {
        obj1 = new BoardObjectInContext("one", BoardObjectType.WALL, Wall.WallPosition.BOTTOM);
        obj2 = new BoardObjectInContext("two", BoardObjectType.WALL, Wall.WallPosition.TOP);
    }

    @Test
    public void testContainsAfterAdd() {
        RoutingManager routingManager = new RoutingManager();
        routingManager.addRoute(obj1, obj2);
        assertTrue(routingManager.containsRouteFrom(obj1));
        assertTrue(routingManager.containsRouteFrom(obj2));
    }

    @Test
    public void testGetAfterAdd() {
        RoutingManager routingManager = new RoutingManager();
        routingManager.addRoute(obj1, obj2);
        assertEquals(routingManager.getRoute(obj1), obj2);
        assertEquals(routingManager.getRoute(obj2), obj1);
    }

    @Test
    public void testRemoveAfterAdd() {
        RoutingManager routingManager = new RoutingManager();
        routingManager.addRoute(obj1, obj2);
        assertTrue(routingManager.removeRoute(obj1));
        assertFalse(routingManager.removeRoute(obj2));
    }

    @Test
    public void testContainsAfterAddAndRemove() {
        RoutingManager routingManager = new RoutingManager();
        routingManager.addRoute(obj1, obj2);
        routingManager.removeRoute(obj1);
        assertFalse(routingManager.containsRouteFrom(obj1));
        assertFalse(routingManager.containsRouteFrom(obj2));
    }

    @Test
    public void testGetAfterAddAndRemove() {
        RoutingManager routingManager = new RoutingManager();
        routingManager.addRoute(obj1, obj2);
        routingManager.removeRoute(obj1);
        assertNull(routingManager.getRoute(obj1));
    }

    @Test
    public void testRemoveNoAdd() {
        RoutingManager routingManager = new RoutingManager();
        assertFalse(routingManager.removeRoute(obj1));
    }



}
