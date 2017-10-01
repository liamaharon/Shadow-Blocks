package project1;

import org.newdawn.slick.SlickException;

public class Wall extends Sprite {
    public Wall (int xPos, int yPos) throws SlickException {
        super("res/wall.png", xPos, yPos);
    }
}
