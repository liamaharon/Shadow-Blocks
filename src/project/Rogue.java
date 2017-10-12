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

    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        super.update(levelManager, input, delta);
    }
}
