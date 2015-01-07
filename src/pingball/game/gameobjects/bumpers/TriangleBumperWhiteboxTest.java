package pingball.game.gameobjects.bumpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.Ball;
import pingball.game.util.PhysicsConfiguration;

public class TriangleBumperWhiteboxTest {
    
    /*
     * SEE ALSO:  pingball.game.gameobjects.tests.TriangleBumperTest
     * 
     * Get Closest Wall
     * -all 3 sides 
     * 
     * Get Closest Corner
     * -all 3 corners
     * -equidistant case 
     */

    final PhysicsConfiguration physicsConfig = new PhysicsConfiguration(
            new Vect(0.0, 1.0), 0.1, 0.1);

    @Test
    public void getClosestWallVerticalLeg() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Vect northwestCorner = new Vect(10, 10);
        Ball ball = new Ball(new Vect(8, 10), new Vect(3, 0));
        Double cornerX = northwestCorner.x();
        Double cornerY = northwestCorner.y();
        LineSegment horizontalLeg = new LineSegment(cornerX, cornerY,
                cornerX + 1, cornerY);
        LineSegment verticalLeg = new LineSegment(cornerX, cornerY, cornerX,
                cornerY + 1);
        LineSegment hypotenuse = new LineSegment(cornerX + 1, cornerY, cornerX,
                cornerY + 1);
        LineSegment testWall = triangleBumper.getClosestWall(
                Arrays.asList(horizontalLeg, verticalLeg, hypotenuse), ball);
        assertEquals(testWall, verticalLeg);
    }

    @Test
    public void getClosestWallHorizontalLeg() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Vect northwestCorner = new Vect(10, 10);
        Ball ball = new Ball(new Vect(10.5, 10.5), new Vect(0, -10));
        Double cornerX = northwestCorner.x();
        Double cornerY = northwestCorner.y();
        LineSegment horizontalLeg = new LineSegment(cornerX, cornerY,
                cornerX + 1, cornerY);
        LineSegment verticalLeg = new LineSegment(cornerX, cornerY, cornerX,
                cornerY + 1);
        LineSegment hypotenuse = new LineSegment(cornerX + 1, cornerY, cornerX,
                cornerY + 1);
        LineSegment testWall = triangleBumper.getClosestWall(
                Arrays.asList(horizontalLeg, verticalLeg, hypotenuse), ball);
        assertEquals(testWall, horizontalLeg);
    }

    @Test
    public void getClosestWallHypotenuse() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Vect northwestCorner = new Vect(10, 10);
        Ball ball = new Ball(new Vect(11, 11), new Vect(-1, -1));
        Double cornerX = northwestCorner.x();
        Double cornerY = northwestCorner.y();
        LineSegment horizontalLeg = new LineSegment(cornerX, cornerY,
                cornerX + 1, cornerY);
        LineSegment verticalLeg = new LineSegment(cornerX, cornerY, cornerX,
                cornerY + 1);
        LineSegment hypotenuse = new LineSegment(cornerX + 1, cornerY, cornerX,
                cornerY + 1);
        LineSegment testWall = triangleBumper.getClosestWall(
                Arrays.asList(horizontalLeg, verticalLeg, hypotenuse), ball);
        assertEquals(testWall, hypotenuse);
    }

    @Test
    public void getClosestCornerHorizontalLegVerticalLegIntersection() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Vect northwestCorner = new Vect(10, 10);
        Ball ball = new Ball(new Vect(9, 9), new Vect(1, 1));
        Double cornerX = northwestCorner.x();
        Double cornerY = northwestCorner.y();
        Circle verticalHortizonntalIntersectionCorner = new Circle(
                northwestCorner, 0);
        Circle hortizontalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX + 1, cornerY), 0);
        Circle verticalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX, cornerY + 1), 0);
        Circle testCorner = triangleBumper.getClosestCorner(Arrays.asList(
                verticalHortizonntalIntersectionCorner,
                hortizontalHypotenuseIntersectionCorner,
                verticalHypotenuseIntersectionCorner), ball);
        assertEquals(testCorner, verticalHortizonntalIntersectionCorner);

    }

    @Test
    public void getClosestCornerHorizontalLegHypotenuseIntersection() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Vect northwestCorner = new Vect(10, 10);
        Ball ball = new Ball(new Vect(12, 9), new Vect(-1, 1));
        Double cornerX = northwestCorner.x();
        Double cornerY = northwestCorner.y();
        Circle verticalHortizonntalIntersectionCorner = new Circle(
                northwestCorner, 0);
        Circle hortizontalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX + 1, cornerY), 0);
        Circle verticalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX, cornerY + 1), 0);
        Circle testCorner = triangleBumper.getClosestCorner(Arrays.asList(
                verticalHortizonntalIntersectionCorner,
                hortizontalHypotenuseIntersectionCorner,
                verticalHypotenuseIntersectionCorner), ball);
        assertEquals(testCorner, hortizontalHypotenuseIntersectionCorner);

    }

    @Test
    public void getClosestCornerVerticalHypotenuseIntersection() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Vect northwestCorner = new Vect(10, 10);
        Ball ball = new Ball(new Vect(9, 12), new Vect(1, -1));
        Double cornerX = northwestCorner.x();
        Double cornerY = northwestCorner.y();
        Circle verticalHortizonntalIntersectionCorner = new Circle(
                northwestCorner, 0);
        Circle hortizontalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX + 1, cornerY), 0);
        Circle verticalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX, cornerY + 1), 0);
        Circle testCorner = triangleBumper.getClosestCorner(Arrays.asList(
                verticalHortizonntalIntersectionCorner,
                hortizontalHypotenuseIntersectionCorner,
                verticalHypotenuseIntersectionCorner), ball);
        assertEquals(testCorner, verticalHypotenuseIntersectionCorner);
    }

    @Test
    public void getClosestCornerEquidistant() {
        final Board realBoard = new Board(20, 20, physicsConfig);
        TriangleBumper triangleBumper = new TriangleBumper(realBoard, new Vect(
                10, 10), 0);
        Vect northwestCorner = new Vect(10, 10);
        Ball ball = new Ball(new Vect(10.5, 9), new Vect(0, 1));
        Double cornerX = northwestCorner.x();
        Double cornerY = northwestCorner.y();
        Circle verticalHortizonntalIntersectionCorner = new Circle(
                northwestCorner, 0);
        Circle hortizontalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX + 1, cornerY), 0);
        Circle verticalHypotenuseIntersectionCorner = new Circle(new Vect(
                cornerX, cornerY + 1), 0);
        Circle testCorner = triangleBumper.getClosestCorner(Arrays.asList(
                verticalHortizonntalIntersectionCorner,
                hortizontalHypotenuseIntersectionCorner,
                verticalHypotenuseIntersectionCorner), ball);
        assertTrue(testCorner.equals(verticalHortizonntalIntersectionCorner)
                ^ testCorner.equals(hortizontalHypotenuseIntersectionCorner));
    }
}
