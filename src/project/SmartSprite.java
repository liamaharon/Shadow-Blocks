package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

/**
 * A SmartSprite represents a Sprite whos state can change as a result of the
 * Update method being called
 */
public abstract class SmartSprite extends RegularSprite implements Serializable
{

    /**
     * SmartSprites initialise the same way as RegularSprites
     * @param src Represents the sprite's img source
     * @param pos Represents the initial position of the sprite
     */
    public SmartSprite(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    /**
     * Most SmartSprites can move. Moving involves changing the sprites Pos
     * attribute to where it should move to.
     * @param pos          The position to move to
     * @param dir          The direction the SmartSprite is moving
     * @param levelManager The LevelManager managing the SmartSprite's level
     */
    public void move(TileCoord pos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        setPos(pos);
    }

    /**
     * Returns if a SmartSprite can move to a certain position (isn't blocked).
     * Every SmartSprite is blocked by Walls and closed Doors. Can be called
     * with or without direction the sprite is moving from.
     * @param pos          The position being checked
     * @param levelManager The LevelManager managing the SmartSprite's level
     * @return             If the SmartSprite can move to the position specified
     */
    public boolean canMoveTo(TileCoord pos,
                             LevelManager levelManager)
    {
        return !(levelManager.tileIsBlockedByWall(pos) ||
                levelManager.tileIsBlockedByDoor(pos));
    }
    /**
     * Overloading of the canMoveTo() method above. Identical at this level.
     * @param pos                 The position being checked
     * @param directionMovingFrom The direction the SmartSprite would be moving
     *                            from
     * @param levelManager        The LevelManager managing the SmartSprite's
     *                            level
     * @return If the SmartSprite can move to the position specified
     */
    public boolean canMoveTo(TileCoord pos,
                             Direction directionMovingFrom,
                             LevelManager levelManager)
    {
        return !(levelManager.tileIsBlockedByWall(pos) ||
                levelManager.tileIsBlockedByDoor(pos));
    }

    /**
     * If blocks exist in the supplied position, push them in the supplied
     * direction
     * @param pos          The position being pushed
     * @param dir          The direction of the push
     * @param levelManager The LevelManager managing the SmartSprite's level
     */
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

    /**
     * The definition of a SmartSprite is that they're able to change depending
     * on information supplied by update calls. Update is called on SmartSprites
     * using this method
     * @param levelManager The LevelManager managing the SmartSprtie's level
     * @param input        Represents any input made this update
     * @param delta        Represents the time in ms since the last update was
     *                     made
     */
    public void update(LevelManager levelManager,
                                Input input,
                                int delta) throws SlickException
    {

    }
}
