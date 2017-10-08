package project;

import org.newdawn.slick.SlickException;

public abstract class Block extends SmartSprite
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
