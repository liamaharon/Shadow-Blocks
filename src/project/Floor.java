package project;

import org.newdawn.slick.SlickException;

public class Floor extends RegularSprite {
    public Floor (TileCoord pos) throws SlickException {
        super("res/floor.png", pos);
    }
}
