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

    public void move(TileCoord pos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        setPos(pos);
    }

    // returns if a SmartSprite can move to a certain position (isn't blocked)
    // every SmartSprite is blocked by walls and closed doors. can be called
    // with or without direction the sprite is moving from.
    public boolean canMoveTo(TileCoord pos,
                             LevelManager levelManager)
    {
        return !(levelManager.tileIsBlockedByWall(pos) ||
                levelManager.tileIsBlockedByDoor(pos));
    }

    public boolean canMoveTo(TileCoord pos,
                             Direction directionMovingFrom,
                             LevelManager levelManager)
    {
        return !(levelManager.tileIsBlockedByWall(pos) ||
                levelManager.tileIsBlockedByDoor(pos));
    }

    // looks for any blocks in a position, and pushes them in a specified
    // direction if they exist there
    public void push(TileCoord pos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        if (levelManager.getBlockFromCoord(pos) != null)
        {
            // get position to push the block
            TileCoord posToPushTo = levelManager.getAdjacentTileCoord(pos, dir);
            levelManager.getBlockFromCoord(pos).move(posToPushTo, dir, levelManager);
        }
    }

    public  void update(LevelManager levelManager,
                                Input input,
                                int delta) throws SlickException
    {

    }
}
