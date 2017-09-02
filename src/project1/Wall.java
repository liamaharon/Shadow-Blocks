package project1;

import org.newdawn.slick.SlickException;

public class Wall extends Sprite {
    public Wall (String src, int xPos, int yPos) throws SlickException {
        super(src, xPos, yPos, true);
    }
}
