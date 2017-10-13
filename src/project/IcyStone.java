package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The ice block behaves similarly to the stone block, except that when it is
 * pushed it should continue moving in the direction it has been moved at a
 * rate of one tile every 0.25 seconds.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class IcyStone extends Block {
    private Direction directionMoving = Direction.NONE;
    private int msSinceLastMove = 0;

    /**
     * Initialise the IcyStone
     * @param pos
     * @throws SlickException
     */
    public IcyStone(TileCoord pos) throws SlickException
    {
        super("res/ice.png", pos);
    }

    /**
     * Takes note of the direction the icy stone was pushed to, and resets
     * msSinceLast move attribute before moving
     * @param newPos       The Block's new position
     * @param dir          The direction the SmartSprite is moving
     * @param levelManager The LevelManager managing the SmartSprite's level
     */
    @Override
    public void move(TileCoord newPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        super.move(newPos, dir, levelManager);
        directionMoving = dir;
        msSinceLastMove = 0;
    }

    /**
     * Every update check if the IcyStone hasn't moved in 250ms. If it hasn't,
     * try move in its direction. If it can't move any further, stop it from
     * moving any further
     * @param levelManager The LevelManager managing the IcyStone's level
     * @param input        Represents any input made this update
     * @param delta        Represents the time in ms since the last update was
     *                     made
     */
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
        msSinceLastMove += delta;
        final int QUARTER_SECOND_IN_MS = 250;
        if (directionMoving != Direction.NONE &&
            msSinceLastMove >= QUARTER_SECOND_IN_MS)
        {
            // get the next position the ice wants to slide
            TileCoord desiredPos = levelManager.getAdjacentTileCoord(getPos(), directionMoving);
            // if it can't move to that position, set its direction to NONE
            if (!canMoveTo(desiredPos, levelManager))
            {
                directionMoving = Direction.NONE;
            }
            // else, it can move to the new position, so move there
            else
            {
                move(desiredPos, directionMoving, levelManager);
            }
        }
    }
}
