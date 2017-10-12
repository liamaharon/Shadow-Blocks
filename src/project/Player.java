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



    // before a player moves the levelManager needs to save the game,
    // and take note that a player has moved this tick and where it was
    @Override
    public void move(TileCoord desiredPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        levelManager.saveState();
        levelManager.setPlayerMovedThisTick(true);
        levelManager.getCurGameState().setPlayerCoord(desiredPos);
        super.move(desiredPos, dir, levelManager);
    }

    // players are also blocked by cracked walls
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return super.canMoveTo(pos, levelManager) ||
               levelManager.getCrackedWallFromCoord(pos) != null;
    }
    // handle a user trying to move the player, resulting in nothing happening
    // if the move is impossible or the player and potentially affected blocks
    // also moving
    private void tryMove(TileCoord desiredPos,
                         LevelManager levelManager,
                         Direction dir) throws SlickException
    {
        // first check if position is blocked. in which case, do nothing
        if (!canMoveTo(desiredPos, levelManager)) return;

        // calculate the position of the second tile over from where the player
        // is trying to move. this is the position a pushable block would be
        // moved to if it exists at desiredPos
        TileCoord secondTileOverPos = getSecondTileOver(desiredPos, dir);
        // check if there's a tile in our desired position that is able to be
        // pushed in the direction we're going
        Block tileToPush = levelManager.getBlockFromCoord(desiredPos);

        // if a pushable tile is in our desired position but it can't move in
        // the direction we want, do nothing
        if (tileToPush != null &&
           !tileToPush.canMoveTo(secondTileOverPos, levelManager)
           ) return;

        // at this point in we're able to move, either because nothing is in
        // the path of the player or a block is there but we can move it out
        // of the way. move the player, and move a block if there's one there
        move(desiredPos, dir, levelManager);
        if (tileToPush != null)
        {
            tileToPush.move(secondTileOverPos, dir, levelManager);
        }
    }

    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        TileCoord desiredPos;
        if (input.isKeyPressed(Input.KEY_UP))
        {
            desiredPos = new TileCoord(getPos().getX(), getPos().getY()-1);
            tryMove(desiredPos, levelManager, Direction.UP);
        }
        else if (input.isKeyPressed(Input.KEY_DOWN))
        {
            desiredPos = new TileCoord(getPos().getX(), getPos().getY()+1);
            tryMove(desiredPos, levelManager, Direction.DOWN);
        }
        else if (input.isKeyPressed(Input.KEY_LEFT))
        {
            desiredPos = new TileCoord(getPos().getX()-1, getPos().getY());
            tryMove(desiredPos, levelManager, Direction.LEFT);
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT))
        {
            desiredPos = new TileCoord(getPos().getX()+1, getPos().getY());
            tryMove(desiredPos, levelManager, Direction.RIGHT);
        }
    }
}
