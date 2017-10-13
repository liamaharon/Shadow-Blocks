package project;

import org.newdawn.slick.SlickException;

/**
 * Represents a Tnt Block.
 * The TNT block behaves identically to the stone block, except that if it
 * is pushed into a Cracked Wall tile, both the wall and the block should be
 * destroyed, and an Explosion effect should be created at the TNT block’s
 * position. Note: once the TNT block has been destroyed, its movement can
 * no longer be undone.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Tnt extends Block
{

    /**
     * Constructor for the Tnt. Doesn't do anything special.
     * @param pos The Tnt's initial position
     */
    public Tnt(TileCoord pos) throws SlickException
    {
        super("res/tnt.png", pos);
    }

    /**
     * Tnt moves like other blocks except when the position its moving to is a
     * cracked wall, in which case it explodes both itself and the cracked wall
     * @param newPos       The position the Tnt is moving to
     * @param dir          The direction the Tnt is moving during this move
     * @param levelManager The LevelManager managing the Tnt's level
     */
    @Override
    public void move(TileCoord newPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        // Moving into a cracked wall, explode wall and remove self from game
        if (levelManager.getCrackedWallFromCoord(newPos) != null)
        {
            levelManager.getCrackedWallFromCoord(newPos).explode(levelManager);
            levelManager.removeSpriteFromCurGameState(this);
            return;
        }

        // Moving anywhere except a cracked wall, act as a normal block
        super.move(newPos, dir, levelManager);
    }

    /**
     * Unlike all other blocks, Tnt is not blocked by cracked walls
     * @param pos          The position we are checking is blocked to the Tnt
     * @param levelManager The LevelManager managing the Tnt's level
     * @return             Boolean representing if a Tnt is able to move to
     *                     the supplied pos
     */
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return levelManager.getCrackedWallFromCoord(pos) != null ||
               super.canMoveTo(pos, levelManager);
    }
}
