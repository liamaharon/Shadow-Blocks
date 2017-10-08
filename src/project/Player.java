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

    // move player to a new position in the world if the tile is free
    private void move(TileCoord desiredPos, LevelManager levelManager)
    {
        if (levelManager.isBlocked(desiredPos)) return;
        // tile is free, save and move
        levelManager.saveState();
        setPos(desiredPos);
    }

    @Override
    public void update(Input input, LevelManager levelManager)
    {
        TileCoord desiredPos;
        if (input.isKeyPressed(Input.KEY_UP))
        {
            desiredPos = new TileCoord(getPos().getX(), getPos().getY()-1);
            move(desiredPos, levelManager);
        }
        else if (input.isKeyPressed(Input.KEY_DOWN))
        {
            desiredPos = new TileCoord(getPos().getX(), getPos().getY()+1);
            move(desiredPos, levelManager);
        }
        else if (input.isKeyPressed(Input.KEY_LEFT))
        {
            desiredPos = new TileCoord(getPos().getX()-1, getPos().getY());
            move(desiredPos, levelManager);
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT))
        {
            desiredPos = new TileCoord(getPos().getX()+1, getPos().getY());
            move(desiredPos, levelManager);
        }
    }
}
