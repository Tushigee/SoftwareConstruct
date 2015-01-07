package pingball.game.util;

import java.util.List;
import java.util.ArrayList;

/**
 * An immutable 2D point of variable precision
 * 
 * @param <T> A number type to specify the precision of the Point object
 */
public class Point<T extends Number> {
    
    /**
     * The x-coordinate of the point
     */
    private final T x;
    
    /**
     * The y-coordinate of the point
     */
    private final T y;
    
    /**
     * Creates a point at the specified coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return the x coordinate of the point
     */
    public T getX() {
        return x;
    }
    
    /**
     * @return the y coordinate of the point
     */
    public T getY() {
        return y;
    }
    
    @Override
    public String toString() {
        return "Point<x=" + this.x + ", y=" + this.y + ">";
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point<?>)) {
            return false;
        }
        Point<?> ptOther = (Point<?>) other;
        if (ptOther.getX().getClass() != this.getX().getClass()) {
            return false;
        }
        return (ptOther.getX().equals(this.getX()) && ptOther.getY().equals(this.getY()));
    }
    
    @Override
    public int hashCode() {
        int scaledXHash = this.x.hashCode() >> (Integer.BYTES / 2);
        int scaledYHash = this.y.hashCode() << (Integer.BYTES / 2);
        return scaledXHash + scaledYHash;
    }
    
    /**
     * Generates a list of the points along the line between points A and B (inclusive)
     * The list is in the order that the points occur, beginning with pointA and
     *   ending with pointB
     * 
     * @param pointA the start point of the line (with integer precision)
     * @param pointB the end point of the line (with integer precision)
     * @return a list of the points between A and B (inclusive)
     */
    public static List<Point<Integer>> getPointsBetween(Point<Integer> pointA, Point<Integer> pointB) {
        // Generates list using Bresenham's line algorithm
        // Based on pseudo-code found at http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        List<Point<Integer>> points = new ArrayList<Point<Integer>>();
        
        int deltaX = pointB.getX() - pointA.getX();
        int deltaY = pointB.getY() - pointA.getY();
        
        int xStep = (deltaX >= 0) ? 1 : -1;
        int yStep = (deltaY >= 0) ? 1 : -1;
        
        // Special case for vertical lines
        if (deltaX == 0) {
            boolean increasingY = deltaY >= 0;
            int x = pointA.getX();
            for (int y = pointA.getY(); 
                    (increasingY && y <= pointB.getY()) || (!increasingY && y >= pointB.getY()); 
                    y += yStep) {
                points.add(new Point<Integer>(x, y));
            }
      
            return points;
        }
        
        double error = 0.;
        double deltaError = Math.abs(((double)deltaY) / ((double)deltaX));
        
        int y = pointA.getY();
        boolean increasingX = deltaX >= 0;
        for (int x = pointA.getX(); 
                (increasingX && x <= pointB.getX()) || (!increasingX && x >= pointB.getX()); 
                x += xStep) {
            points.add(new Point<Integer>(x, y));
            error += deltaError;
            if (error >= 0.5) {
                y += yStep;
                error -= 1.;
            }
        }
      
        return points;
    }
    
}
