package project;

import org.newdawn.slick.SlickException;

public class Wall extends RegularSprite {
    public Wall (TileCoord pos) throws SlickException {
        super("res/wall.png", pos);
    }
}
