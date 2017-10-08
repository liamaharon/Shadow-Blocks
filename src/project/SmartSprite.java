package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class SmartSprite extends RegularSprite {

    public SmartSprite(String src, TileCoord pos) throws SlickException {
        super(src, pos);
    }

    public void update(Input input, LevelManager levelManager) {

    }
}
