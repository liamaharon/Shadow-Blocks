package project;

import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class Block extends SmartSprite implements Serializable
{
    public Block(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    // when a block moves it also needs to update the curGameState 2D block
    // array
    @Override
    public void move(TileCoord newPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        levelManager.updateCurState2DBlocksList(this.getPos(), newPos);
        super.move(newPos, dir, levelManager);
    }

    // (most) blocks are also blocked by other blocks && cracked walls
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return super.canMoveTo(pos, levelManager) &&
               levelManager.getCrackedWallFromCoord(pos) == null &&
               levelManager.getBlockFromCoord(pos) == null;
    }
}
