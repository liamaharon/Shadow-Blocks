package project;

import org.newdawn.slick.SlickException;

public class Target extends RegularSprite {
    public Target (TileCoord pos) throws SlickException {
        super("res/target.png", pos);
    }
}
