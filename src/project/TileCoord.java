package project;

import java.io.Serializable;

/**
 * TileCoord represents a set of x,y coordinates in a level.
 */
public class TileCoord implements Serializable
{
    private int x;
    private int y;

    /**
     * Initialise the TileCoord by setting the supplied coordinates
     * @param x The x coordinate to be set
     * @param y The y coordinate to be set
     */
    public TileCoord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * For an object to be equal with a TileCoord
     * 1. It must be another non-null TileCoord
     * 2. It's x and y attributes must match
     * @param obj The Object we are comparing to
     * @return Boolean val representing if the supplied object is equal to this
     *         TileCoord
     */
    @Override
    public boolean equals(Object obj)
    {
        // Ensure we're comparing to a non-null TileCoord
        if (obj == null || !(obj instanceof TileCoord)) return false;

        // Compare coordinates
        return getY() == ((TileCoord) obj).getY() &&
               getX() == ((TileCoord) obj).getX();
    }

    /**
     * @return The x coordinate of the TileCoord
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return The y coordinate of the TileCoord
     */
    public int getY()
    {
        return y;
    }

}
