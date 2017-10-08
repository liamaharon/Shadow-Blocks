package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class Baddie extends SmartSprite
{

    public Baddie(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    @Override
    public void update(Input input, LevelManager levelManager)
    {
        super.update(input, levelManager);
    }

    private void attack()
    {

    }

}
