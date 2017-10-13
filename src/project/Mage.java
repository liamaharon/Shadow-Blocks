package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The Mage attempts to track down the player using Algorithm 1, as described
 * in the spec. This algorithm should run each time the player makes a move.
 * If the player makes contact with the mage, the current level restarts.
 */
public class Mage extends Baddie {

    /**
     * Initialise the Mage with its image source location and initial position
     * @param pos Initial position
     */
    public Mage(TileCoord pos) throws SlickException {
        super("res/mage.png", pos);
    }

    /**
     * Mages are also blocked by CrackedWalls and Blocks
     * @param pos          The position being checked
     * @param levelManager The LevelManager managing the Mage's level
     * @return             Boolean representing if a Mage can move to the
     *                     pos input
     */
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager) {
        return super.canMoveTo(pos, levelManager) &&
                levelManager.getCrackedWallFromCoord(pos) == null &&
                levelManager.getBlockFromCoord(pos) == null;
    }

    /**
     * Algorithm to determine the movement of the Mage based on PlayerPosition
     * @param levelManager The LevelManager managing the Mage's level
     * @return             The direction the Mage should move
     */
    // determine what direction the mage should try to move in
    private Direction determineMovementDirection(LevelManager levelManager) {
        // get the distances between the Mage and Player, and which direction
        // the Mage should walk in each direction
        TileCoord playerCoord = levelManager.getCurGameState().getPlayerCoord();
        int distX = getPos().getX() - playerCoord.getX();
        Direction xDir = distX < 0 ? Direction.RIGHT : Direction.LEFT;

        int distY = getPos().getY() - playerCoord.getY();
        Direction yDir = distY < 0 ? Direction.DOWN : Direction.UP;

        // if movement if possible, move 1 tile in the direction of the player
        if (Math.abs(distX) > Math.abs(distY))
        {
            TileCoord desiredPos = levelManager.getAdjacentTileCoord(getPos(), xDir);
            if (canMoveTo(desiredPos, levelManager))
            {
                return xDir;
            }
        }
        else
        {
            TileCoord desiredPos = levelManager.getAdjacentTileCoord(getPos(), yDir);
            if (canMoveTo(desiredPos, levelManager))
            {
                return yDir;
            }
        }
        return Direction.NONE;
    }

    /**
     * If the player has moved this tick, the Mage needs to try to move according
     * to its algorithm
     * @param levelManager The LevelManager managing the Mage's level
     * @param input        Represents any input made this update
     * @param delta        Represents the time in ms since the last update was
     *                     made
     */
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
        // if the player hasn't moved this tick, don't do anything extra
        if (levelManager.getPlayerDirectionThisTick() == Direction.NONE) return;

        // get the direction the mage should to move in
        Direction movementDirection = determineMovementDirection(levelManager);

        // if the direction is NONE, do nothing
        if (movementDirection == Direction.NONE) return;

        // get the tile the mage should move to
        TileCoord nextPos = levelManager.getAdjacentTileCoord(getPos(),
                                                              movementDirection);
        // move to the next pos
        move(nextPos, movementDirection, levelManager);
    }
}
