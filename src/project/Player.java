package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

/**
 * The Player is the SmartSprite that the user of the game controls. It can
 * push blocks.
 */
public class Player extends SmartSprite implements Serializable
{
    /**
     * Initialise the Player with it's image src location and initial position
     * @param pos The Player's initial position
     */
    public Player (TileCoord pos) throws SlickException
    {
        super("res/player_left.png", pos);
    }

    /**
     * Before moving like a normal SmartSprite, the Player needs to update
     * it's position in the current GameState and push any tiles out of the way
     * @param desiredPos   The position the Player wishes to move to
     * @param dir          The direction the SmartSprite is moving
     * @param levelManager The LevelManager managing the SmartSprite's level
     */
    // update the player's position in current game state before moving
    @Override
    public void move(TileCoord desiredPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        levelManager.getCurGameState().setPlayerCoord(desiredPos);
        // the player pushes when it moves
        push(desiredPos, dir, levelManager);
        super.move(desiredPos, dir, levelManager);
    }

    /**
     * As well as being blocked by everything normal SmartSprites are, players
     * are blocked by blocked Blocks and CrackedWalls
     * @param pos          The position being checked
     * @param dir          The direction the Player is moving in
     * @param levelManager The LevelManager managing the Player's
     *                     level
     * @return             Boolean representing if the Player can move to the
     *                     specified position or not
     */
    @Override
    public boolean canMoveTo(TileCoord pos, Direction dir, LevelManager levelManager)
    {
        return super.canMoveTo(pos, dir, levelManager) &&
               levelManager.getCrackedWallFromCoord(pos) == null &&
               !levelManager.tileIsBlockedByBlockedBlock(pos, dir);
    }

    /**
     * If the user has requested the Player to move this tick, try to move it
     * @param levelManager The LevelManager managing the Player's level
     * @param input        Represents any input made this update
     * @param delta        Represents the time in ms since the last update was
     */
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        // set the direction the player wants to move, if any
        Direction dirToMove = levelManager.getPlayerDirectionThisTick();
        if (dirToMove != Direction.NONE)
        {
            TileCoord desiredPos = levelManager.getAdjacentTileCoord(getPos(), dirToMove);
            if (canMoveTo(desiredPos, dirToMove, levelManager))
            {
                move(desiredPos, dirToMove, levelManager);
            }
        }
    }
}
