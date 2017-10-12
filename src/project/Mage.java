package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Mage extends Baddie {

    public Mage(TileCoord pos) throws SlickException {
        super("res/mage.png", pos);
    }

    // Mages are also blocked by cracked walls and all blocks
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager) {
        return super.canMoveTo(pos, levelManager) &&
                levelManager.getCrackedWallFromCoord(pos) == null &&
                levelManager.getBlockFromCoord(pos) == null;
    }

    // determine what direction the mage should try to move in
    private Direction determineMovementDirection(LevelManager levelManager) throws SlickException
    {
        TileCoord playerCoord = levelManager.getCurGameState().getPlayerCoord();
        int distX = getPos().getX() - playerCoord.getX();
        Direction xDir = distX < 0 ? Direction.RIGHT : Direction.LEFT;
        int distY = getPos().getY() - playerCoord.getY();
        Direction yDir = distY < 0 ? Direction.DOWN : Direction.UP;
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

    // if the player has moved this tick, the Mage needs to try to move according
    // to its algorithm
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
        // if the player hasn't moved this tick, don't do anything extra
        if (levelManager.getPlayerDirectionThisTick() == Direction.NONE) return;

        // get the direction the mage should to move in
        Direction movementDirection = determineMovementDirection(levelManager);

        System.out.println(movementDirection);

        // if the direction is NONE, do nothing
        if (movementDirection == Direction.NONE) return;

        // get the tile the mage should move to
        TileCoord nextPos = levelManager.getAdjacentTileCoord(getPos(),
                                                              movementDirection);
        // move to the next pos
        move(nextPos, movementDirection, levelManager);
    }
}
