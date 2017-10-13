package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *The skeleton is a mindless being that moves up once per second until it
 * reaches a blocked tile, at which point it reverses direction and moves down
 * until it reaches a blocked tile, and so on. If the player makes contact with
 * the skeleton, the current level restarts.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Skeleton extends Baddie {
    private Direction directionMoving = Direction.UP;
    private int msSinceLastMove = 0;

    /**
     * Initialises the Skeleton
     * @param pos The initial position of the Skeleton
     */
    public Skeleton(TileCoord pos) throws SlickException {
        super("res/skull.png", pos);
    }

    /**
     * Every update update the msSinceLastMoved attribute. When the
     * skeleton hasn't moved for 1 second it needs to try move in it's current
     * direction moving. If it can't move in that direction it needs to reverse
     * and move in the opposite direction.
     * @param levelManager The LevelManger managing the Skeleton's level
     * @param input Represents any input made this update
     * @param delta Represents the time in ms since the last update
     */
    @Override
    public void update(LevelManager levelManager,
                       Input input,
                       int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
        // make note of when the Skeleton last moved
        msSinceLastMove += delta;
        // first check if the skeleton is blocked above and below. if it is,
        // do nothing
        TileCoord coordAbove = levelManager.getAdjacentTileCoord(getPos(),
                                                                 Direction.UP);
        TileCoord coordBelow = levelManager.getAdjacentTileCoord(getPos(),
                                                                 Direction.DOWN);
        if (!canMoveTo(coordAbove, levelManager) &&
            !canMoveTo(coordBelow, levelManager)
           ) return;

        // check if it's been more than 1s since the last move. if it has,
        // move
        final int ONE_SECOND_IN_MS = 1000;
        if (msSinceLastMove >= ONE_SECOND_IN_MS)
        {
            // get the next position the skeleton wants to move to
            TileCoord desiredPos = levelManager.getAdjacentTileCoord(getPos(),
                                                                     directionMoving);
            // if the skeleton can't move to the desired position, reverse
            // direction before moving
            if (!canMoveTo(desiredPos, levelManager))
            {
                switch(directionMoving)
                {
                    case UP: directionMoving = Direction.DOWN; break;
                    case DOWN: directionMoving = Direction.UP; break;
                }
            }

            // now we're facing in the right direction. get the position to move
            // to, and move!
            TileCoord nextPos = levelManager.getAdjacentTileCoord(getPos(),
                                                                 directionMoving);
            move(nextPos, directionMoving, levelManager);
        }
    }

    /**
     * When the Skeleton moves it needs to reset it's msSinceLastMoved attribute
     * to 0
     * @param pos The position to move to
     * @param directionMoving The direction the Skeleton is moving in
     * @param levelManager The LevelManager managing the Skeleton's level
     */
    @Override
    public void move(TileCoord pos,
                     Direction directionMoving,
                     LevelManager levelManager) throws SlickException
    {
        msSinceLastMove = 0;
        super.move(pos, directionMoving, levelManager);
    }

    /**
     * Skeletons are also blocked by blocks
     * @param pos The position we're checking the skeleton can move to
     * @param levelManager The LevelManager managing the Skeleton's level
     * @return Boolean representing if the Skeleton can move to the supplied
     *         position
     */
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return super.canMoveTo(pos, levelManager) &&
               levelManager.getBlockFromCoord(pos) == null;
    }
}
