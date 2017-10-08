package project;

import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class Stone extends Block implements Serializable
{
    public Stone (TileCoord pos) throws SlickException
    {
        super("res/stone.png", pos);
    }
}
