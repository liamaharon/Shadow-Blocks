package project;

import org.newdawn.slick.SlickException;

public class Stone extends Block {
    public Stone (TileCoord pos) throws SlickException {
        super("res/stone.png", pos);
    }
}
