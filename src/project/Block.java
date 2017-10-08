package project;

import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class Block extends SmartSprite implements Serializable
{
    boolean isCoveringTarget;

    public Block(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    public void checkIfCoveringTarget()
    {

    }
}
