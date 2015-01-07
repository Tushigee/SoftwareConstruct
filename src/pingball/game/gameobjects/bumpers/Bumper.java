package pingball.game.gameobjects.bumpers;

import physics.Vect;
import pingball.game.Board;
import pingball.game.gameobjects.StationaryGameObject;


/**
 * 
 *  Bumper represents a specific type of StationaryGameObject. Represents a static barrier in which
 *     balls can bounce off of
 *
 */
public interface Bumper extends StationaryGameObject {

    /**
     * creates a new SquareBumper with position vector position and a reference to its respective board
     * 
     * @param board the board that contains this square bumper
     * @param positionOfCenter the vector refering to the location of the top-left-most corner of the absorber
     * @return a new SquareBumper
     */
    public static Bumper square(Board board, Vect positionOfCenter){
        return new SquareBumper(board,positionOfCenter);
    }
    
    /**
     * Creates a new CircleBumper instance with the center of it at
     * positionOfCenter and radius .5
     * 
     * @param board
     *            : the instance of board that this CircleBumper instance is
     *            going to be placed on
     * @param positionOfCenter
     *            : a position vector representing the location of where the
     *            center of this CircleBumper instance is going to be placed.
     *            The CircleBumper must entirely fit within the bounds of the
     *            board instance that is passed in.
     * @return a new CirlceBumper
     */
    public static Bumper circle(Board board, Vect positionOfCenter){
        return new CircleBumper(board, positionOfCenter);
    }
    
    /**
     * creates a new TriangleBumper
     * 
     * @param board
     *            : the instance of the board this this TriangleBumper instance is
     *            going to be place on
     * @param positionOfCenter the position of the center of this TriangleBumper
     * @param orientation the orientation in which this TriangleBumper is arranged, as an angle of rotation
     * @return a new Triangle Bumper
     */
    public static Bumper triangle(Board board, Vect positionOfCenter, int orientation){
        return new TriangleBumper(board, positionOfCenter, orientation);
    }
}
