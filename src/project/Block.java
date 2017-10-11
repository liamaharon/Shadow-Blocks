package project;

import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class Block extends SmartSprite implements Serializable
{
    public Block(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    // update the curGameState 2D lookup list and set the block a new pos
    public void move(TileCoord newPos, LevelManager levelManager) throws SlickException
    {
        levelManager.updateCurState2DBlocksList(this.getPos(), newPos);
        setPos(newPos);
    }

    // most blocks are always blocked by walls, cracked walls, closed doors,
    // as well as other blocks. return false if blocked by any of these, else
    // true. note Tnt is an exception to this rule, and Overrides this method
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return
                (!(levelManager.tileIsBlockedByWall(pos) ||
                levelManager.getCrackedWall(pos) != null ||
                levelManager.tileIsBlockedByDoor(pos) ||
                levelManager.getPushableTile(pos) != null));
    }
}
