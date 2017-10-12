package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class IcyStone extends Block {
    private Direction directionMoving = Direction.NONE;
    private int msSinceLastMove = 0;

    public IcyStone(TileCoord pos) throws SlickException
    {
        super("res/ice.png", pos);
    }

    // take note of the direction the icy stone was pushed and reset
    // msSinceLastMove
    @Override
    public void move(TileCoord newPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        super.move(newPos, dir, levelManager);
        directionMoving = dir;
        msSinceLastMove = 0;
    }

    // update the time since the icy stone last moved. if the ice block is
    // sliding and it hasn't moved in .25s, try to move it
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
            TileCoord desiredPos = getSecondTileOver(getPos(), directionMoving);
            // if it can't move to that position, set its direction to NONE
            if (!canMoveTo(desiredPos, levelManager))
            {
                directionMoving = Direction.NONE;
            }
            // else, it can move to the new position, so move there!
            else
            {
                move(desiredPos, directionMoving, levelManager);
            }
        }
    }
}
