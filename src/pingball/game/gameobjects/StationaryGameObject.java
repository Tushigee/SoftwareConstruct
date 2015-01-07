package pingball.game.gameobjects;

import physics.Vect;
import pingball.game.Board;

/**
 * StationaryGameObject represents any objects in that game that are stationary and do not
 *     advance to any new or different states
 * 
 * CLASSES WHICH IMPLEMENT THIS INTERFACE ARE MUTABLE
 */
public interface StationaryGameObject extends GameObject{

    /**
     * creates a new Absorber object with height height, width width, and position vector postion and 
     *    a reference to its respective board
     * @param board the board that this absorber is placed on
     * @param height integer representing the hight of the absorber in units of L
     * @param width integer representing the width of the absorber in units of L
     * @param position the vector refering to the location of the top-left-most corner of the absorber. Must
     *    represent an integer location
     * @return a new Absorber
     */
    public static StationaryGameObject absorber(Board board, int height, int width, Vect position){
        return new Absorber(board, height, width, position);
    }

    /**
     * Creates a new wall given a line segment and whether or not
     *   the wall starts as invisible
     * @param board the board this wall is placed on
     * @param position the position of the wall
     * @return a new wall
     */
    public static StationaryGameObject wall(Board board, Wall.WallPosition position) {
        return new Wall(board, position);
    }

    /**
     * Creates a new portal based on the board the portal is on,
     *   its position, its name, the destination's name, and the destination
     *   board's name
     * @param board the board the portal is on
     * @param northwestCorner the location of the portal
     * @param gadgetName the name of the portal
     * @param destGadget the name of the destination gadget of the portal
     * @param destBoard the name of the destination board of the portal
     * @return the new portal
     */
    public static StationaryGameObject portal(Board board, Vect northwestCorner, String gadgetName, String destGadget, String destBoard) {
        return new Portal(board, northwestCorner, gadgetName, destGadget, destBoard);
    }

    /**
     * Creates a localized portal based on the board the portal is on,
     *   its position, its name, the destination's name.
     * The portal is said to be localized, because it only sends balls
     *   to other portals on the board the portal is on.
     * @param board the board the portal is on
     * @param northwestCorner the location of the portal
     * @param gadgetName the name of the portal
     * @param destGadget the name of the destination gadget of the portal
     * @return the new portal
     */
    public static StationaryGameObject portal(Board board, Vect northwestCorner, String gadgetName, String destGadget) {
        return new Portal(board, northwestCorner, gadgetName, destGadget);
    }
}
