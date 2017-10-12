package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Skeleton extends Baddie {
    private Direction directionMoving = Direction.UP;
    private double msSinceLastMove = 0;

    public Skeleton(TileCoord pos) throws SlickException {
        super("res/skull.png", pos);
    }

    // update msSinceLastMoved. when the skeleton hasn't moved for 1 second it
    // needs to try move in direction moving. if it can't move in that
    // direction it needs to reverse and move in the opposite direction
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
        msSinceLastMove += delta;
        // first check if the skeleton is blocked above and below. if it is,
        // do nothing
        TileCoord coordAbove = getSecondTileOver(getPos(), Direction.UP);
        TileCoord coordBelow = getSecondTileOver(getPos(), Direction.DOWN);
        if (!canMoveTo(coordAbove, levelManager) &&
            !canMoveTo(coordBelow, levelManager)
           ) return;

        final int ONE_SECOND_IN_MS = 1000;
        if (msSinceLastMove >= ONE_SECOND_IN_MS)
        {
            // get the next position the skeleton wants to move to
            TileCoord desiredPos = getSecondTileOver(getPos(), directionMoving);
            // if the skeleton can't move to the desired position, reverse
            // direction before moving
            if (!canMoveTo(desiredPos, levelManager) && directionMoving == Direction.UP)
            {
                directionMoving = Direction.DOWN;
            }
            else if (!canMoveTo(desiredPos, levelManager) && directionMoving == Direction.DOWN)
            {
                directionMoving = Direction.UP;
            }
            // now we're facing in the right direction. get the position to move
            // to, and move!
            TileCoord nextPos = getSecondTileOver(getPos(), directionMoving);
            move(nextPos, directionMoving, levelManager);
        }
    }

    // reset msSinceLastMove when a move is made
    @Override
    public void move(TileCoord pos,
                     Direction directionMoving,
                     LevelManager levelManager) throws SlickException
    {
        msSinceLastMove = 0;
        super.move(pos, directionMoving, levelManager);
    }

    // skeletons are also blocked by blocks
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return super.canMoveTo(pos, levelManager) ||
               levelManager.getBlockFromCoord(pos) != null;
    }
}
