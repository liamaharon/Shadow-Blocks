package project;

import java.io.Serializable;

public class TileCoord implements Serializable
{
    private int x;
    private int y;

    public TileCoord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    // TileCoords are equal if their x and y values are equal.
    // also ensure the object passed is not null, and is in fact a TileCoord
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        else if (!(obj instanceof TileCoord)) return false;
        else return getY() == ((TileCoord) obj).getY() &&
                    getX() == ((TileCoord) obj).getX();
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

}
