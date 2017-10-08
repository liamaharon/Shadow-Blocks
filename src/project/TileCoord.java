package project;

public class TileCoord implements Comparable<TileCoord>
{
    private int x;
    private int y;

    public TileCoord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(TileCoord anotherTileCoord)
    {
        if (this.x == anotherTileCoord.getX() &&
            this.getY() == anotherTileCoord.getY())
        {
            return 0;
        }
        return -1;
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
