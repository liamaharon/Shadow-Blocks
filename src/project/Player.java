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



    // save game and move the player
    private void move(TileCoord desiredPos, LevelManager levelManager)
    {
        levelManager.saveState();
        setPos(desiredPos);
    }
    // handle a user trying to move the player
    private void tryMove(TileCoord desiredPos, LevelManager levelManager, Direction dir)
    {
        // if the tile the user is moving to is blocked, do nothing
        if (levelManager.isBlocked(desiredPos)) return;
        // check if there's a tile in our desired position that is able to be
        // pushed in the direction we're going
        SmartSprite tileToPush = LevelManager.getPushableTile(desiredPos, dir);
        // move, pushing a tile out of the way if there's one there
        if (tileToPush != null)
        {
            tileToPush.move(desiredPos);
        }
        move(desiredPos, levelManager);
    }

    @Override
    public void update(Input input, LevelManager levelManager)
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
