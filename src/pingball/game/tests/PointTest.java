package pingball.game.tests;

import java.util.List;

import org.junit.Test;

import pingball.game.util.Point;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Point class
 */
public class PointTest {
    /**
     * Test Point equality
     */
    @Test public void testEquality() {
        Point<Integer> point1 = new Point<Integer>(5, 7);
        Point<Integer> point2 = new Point<Integer>(5, 7);
        assertEquals(point1, point2);
        assertEquals(point1.hashCode(), point2.hashCode());
    } 
    
    /**
     * Test getPointsBetween
     * 
     * Basic test for horizontal straight line
     */
    @Test public void testPointsBetweenBasicHorizontal() {
        Point<Integer> startPoint = new Point<Integer>(-1, -1);
        Point<Integer> endPoint = new Point<Integer>(21, -1);
        
        List<Point<Integer>> pointsBetween = Point.getPointsBetween(startPoint, endPoint);
        assertFalse(pointsBetween.isEmpty());
        
        int index = 0;
        int y = -1;
        for (int x = -1; x <= 21; ++x) {
            assertEquals(pointsBetween.get(index), new Point<Integer>(x, y));
            ++index;
        }
    }
    
    /**
     * Test getPointsBetween
     * 
     * Basic test for vertical straight line
     */
    @Test public void testPointsBetweenBasicVertical() {
        Point<Integer> startPoint = new Point<Integer>(-1, -1);
        Point<Integer> endPoint = new Point<Integer>(-1, 21);
        
        List<Point<Integer>> pointsBetween = Point.getPointsBetween(startPoint, endPoint);
        assertFalse(pointsBetween.isEmpty());
        
        int index = 0;
        int x = -1;
        for (int y = -1; y <= 21; ++y) {
            assertTrue(index < pointsBetween.size());
            assertEquals(pointsBetween.get(index), new Point<Integer>(x, y));
            ++index;
        }
    }
    
    /**
     * Test getPointsBetween
     * 
     * Basic test for diagonal line going down and to the right
     */
    @Test public void testPointsBetweenBasicDiagonalDownRight() {
        Point<Integer> startPoint = new Point<Integer>(-1, -1);
        Point<Integer> endPoint = new Point<Integer>(1, 1);
        
        List<Point<Integer>> pointsBetween = Point.getPointsBetween(startPoint, endPoint);
        assertEquals(pointsBetween.size(), 3);
        
        assertEquals(pointsBetween.get(0), startPoint);
        assertEquals(pointsBetween.get(1), new Point<Integer>(0, 0));
        assertEquals(pointsBetween.get(2), endPoint);
    }
    
    /**
     * Test getPointsBetween
     * 
     * Basic test for diagonal line going up and to the left
     */
    @Test public void testPointsBetweenBasicDiagonalUpLeft() {
        Point<Integer> startPoint = new Point<Integer>(1, 1);
        Point<Integer> endPoint = new Point<Integer>(-1, -1);
        
        List<Point<Integer>> pointsBetween = Point.getPointsBetween(startPoint, endPoint);
        assertEquals(pointsBetween.size(), 3);
        
        assertEquals(pointsBetween.get(0), startPoint);
        assertEquals(pointsBetween.get(1), new Point<Integer>(0, 0));
        assertEquals(pointsBetween.get(2), endPoint);
    }
    
    /**
     * Test getPointsBetween
     * 
     * Basic test for diagonal line going down and to the left
     */
    @Test public void testPointsBetweenBasicDiagonalDownLeft() {
        Point<Integer> startPoint = new Point<Integer>(1, -1);
        Point<Integer> endPoint = new Point<Integer>(-1, 1);
        
        List<Point<Integer>> pointsBetween = Point.getPointsBetween(startPoint, endPoint);
        assertEquals(pointsBetween.size(), 3);
        
        assertEquals(pointsBetween.get(0), startPoint);
        assertEquals(pointsBetween.get(1), new Point<Integer>(0, 0));
        assertEquals(pointsBetween.get(2), endPoint);
    }
    
    /**
     * Test getPointsBetween
     * 
     * Basic test for diagonal line going up and to the right
     */
    @Test public void testPointsBetweenBasicDiagonalUpRight() {
        Point<Integer> startPoint = new Point<Integer>(-1, 1);
        Point<Integer> endPoint = new Point<Integer>(1, -1);
        
        List<Point<Integer>> pointsBetween = Point.getPointsBetween(startPoint, endPoint);
        assertEquals(pointsBetween.size(), 3);
        
        assertEquals(pointsBetween.get(0), startPoint);
        assertEquals(pointsBetween.get(1), new Point<Integer>(0, 0));
        assertEquals(pointsBetween.get(2), endPoint);
    }
    
}
