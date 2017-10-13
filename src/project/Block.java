package project;

import org.newdawn.slick.SlickException;

import java.io.Serializable;

/**
 * A Block is a tile that is pushable
 */
public abstract class Block extends SmartSprite implements Serializable
{
    /**
     * Initialise the block
     * @param src The location of the Blocks image
     * @param pos The initial position of the Block
     */
    public Block(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    /**
     * A special 2D array of Blocks is stored in GameState to allow to quick
     * lookup of Blocks. When we move a Block, we also need to move the Block's
     * position within this 2D array.
     * @param newPos       The Block's new position
     * @param dir          The direction the SmartSprite is moving
     * @param levelManager The LevelManager managing the SmartSprite's level
     */
    @Override
    public void move(TileCoord newPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        levelManager.updateCurState2DBlocksList(this.getPos(), newPos);
        super.move(newPos, dir, levelManager);
    }

    /**
     * Returns if a Block can move to a certain position (isn't blocked).
     * Blocks are blocked by everything SmartSprites are, plus CrackedWalls &
     * other Blocks.
     * @param pos          The position being checked
     * @param levelManager The LevelManager managing the Block's level
     * @return             If the Block can move to the position specified
     */
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return super.canMoveTo(pos, levelManager) &&
               levelManager.getCrackedWallFromCoord(pos) == null &&
               levelManager.getBlockFromCoord(pos) == null;
    }
}
