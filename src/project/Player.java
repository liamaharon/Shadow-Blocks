package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class Player extends SmartSprite implements Serializable
{
    public Player (TileCoord pos) throws SlickException
    {
        super("res/player_left.png", pos);
    }

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

    // players are also blocked by cracked walls and blocked blocks
    @Override
    public boolean canMoveTo(TileCoord pos, Direction dir, LevelManager levelManager)
    {
        return super.canMoveTo(pos, dir, levelManager) &&
               levelManager.getCrackedWallFromCoord(pos) == null &&
               !levelManager.tileIsBlockedByBlockedBlock(pos, dir);
    }

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
