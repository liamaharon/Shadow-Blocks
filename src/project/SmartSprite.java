package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class SmartSprite extends RegularSprite implements Serializable
{

    public SmartSprite(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }


    // returns a TileCoord with position directly adjacent to the TileCoord
    // input, in the direction specified
    public TileCoord getSecondTileOver(TileCoord pos, Direction dir)
    {
        switch(dir)
        {
            case UP: return new TileCoord(pos.getX(), pos.getY()-1);
            case DOWN: return new TileCoord(pos.getX(), pos.getY()+1);
            case LEFT: return new TileCoord(pos.getX()-1, pos.getY());
            case RIGHT: return new TileCoord(pos.getX()+1, pos.getY());
            case NONE: return pos;
        }
        return null;
    }

    public void update(Input input, LevelManager levelManager) throws SlickException
    {

    }
}
