package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

/**
 * The rogue takes one step left each time the player moves, unless the rogue
 * would walk into a wall; the rogue then reverses direction and moves right
 * until they reach a wall, and so on. The rogue pushes any blocks they make
 * contact with. If the player makes contact with the rogue, the current level
 * restarts.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Rogue extends Baddie implements Serializable
{
    private Direction directionMoving = Direction.LEFT;

    /**
     * Initialise the Rogue with the supplied position and it's own img src
     * @param pos The initial position of the Rogue
     */
    public Rogue(TileCoord pos) throws SlickException
    {
        super("res/rogue.png", pos);
    }

    /**
     * The Rogue moves like other sprites, except it also pushes blocks out of
     * the way
     * @param pos          The position to move to
     * @param dir          The direction the SmartSprite is moving
     * @param levelManager The LevelManager managing the SmartSprite's level
     */
    @Override
    public void move(TileCoord pos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        push(pos, dir, levelManager);
        super.move(pos, dir, levelManager);
    }

    /**
     * Rogues are also blocked by CrackedWalls and blocked Blocks
     * @param pos          The position we want to know if the Rogue can move to
     * @param dir          The direction the Rogue is moving from
     * @param levelManager The LevelManager managing the Rogues level
     * @return             Boolean representing if the Rogue can move to the
 *                         supplied position
     */
    @Override
    public boolean canMoveTo(TileCoord pos, Direction dir, LevelManager levelManager)
    {
        return super.canMoveTo(pos, dir, levelManager) &&
               levelManager.getCrackedWallFromCoord(pos) == null &&
               !levelManager.tileIsBlockedByBlockedBlock(pos, dir);
    }

    /**
     * If the player has moved this tick, the Rogue needs to try to move in
     * its direction moving. If it can't move in that direction, it needs to
     * reverse and try to move in the opposite direction.
     * @param levelManager The LevelManager managing the Rogues level
     * @param input        Represents any input
     * @param delta        Represents time in ms since the last update
     */
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
