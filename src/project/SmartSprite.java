package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class SmartSprite extends RegularSprite implements Serializable
{

    public SmartSprite(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    public void update(Input input, LevelManager levelManager) throws SlickException
    {

    }
}
