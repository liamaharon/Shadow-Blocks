package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class Rogue extends Baddie implements Serializable
{

    public Rogue(TileCoord pos) throws SlickException
    {
        super("res/rogue.png", pos);
    }

    @Override
    public void update(Input input, LevelManager levelManager) throws SlickException
    {
        super.update(input, levelManager);
    }
}
