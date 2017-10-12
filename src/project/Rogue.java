package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class Rogue extends Baddie implements Serializable
{
    private Direction directionMoving = Direction.LEFT;

    public Rogue(TileCoord pos) throws SlickException
    {
        super("res/rogue.png", pos);
    }

    // The Rogue pushes as it moves
    @Override
    public void move(TileCoord pos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        push(pos, dir, levelManager);
        super.move(pos, dir, levelManager);
    }

    // Rogues are also blocked by cracked walls and blocked blocks
    @Override
    public boolean canMoveTo(TileCoord pos, Direction dir, LevelManager levelManager)
    {
        return super.canMoveTo(pos, dir, levelManager) &&
                levelManager.getCrackedWallFromCoord(pos) == null &&
                !levelManager.tileIsBlockedByBlockedBlock(pos, dir);
    }

    // if the player has moved this tick, the Rogue needs to try to move in
    // its direction moving. if it can't move in that direction, it needs to
    // reverse and try to move in the opposite direction.
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
        // if the player hasn't moved this tick, don't do anything extra
        if (levelManager.getPlayerDirectionThisTick() == Direction.NONE) return;
        // check if the rogue is blocked both left and right. if it is,
        // don't do anything extra
        TileCoord coordLeft = levelManager.getAdjacentTileCoord(getPos(), Direction.LEFT);
        TileCoord coordRight = levelManager.getAdjacentTileCoord(getPos(), Direction.RIGHT);
        if (!canMoveTo(coordLeft, directionMoving, levelManager) &&
            !canMoveTo(coordRight, directionMoving, levelManager)
           ) return;

        // get the next position the rogue wants to move to
        TileCoord desiredPosition = levelManager.getAdjacentTileCoord(getPos(),
                                                                      directionMoving);
        // if the rogue can't move to it's desired position, reverse its
        // direction before moving
        if (!canMoveTo(desiredPosition, directionMoving, levelManager))
        {
            switch(directionMoving)
            {
                case LEFT: directionMoving = Direction.RIGHT; break;
                case RIGHT: directionMoving = Direction.LEFT; break;
            }
        }

        // now we're facing in the right direction. get the position to move
        // to, and move!
        TileCoord nextPos = levelManager.getAdjacentTileCoord(getPos(),
                                                              directionMoving);
        move(nextPos, directionMoving, levelManager);
    }
}
