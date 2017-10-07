package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends SmartSprite {
    public Player (TileCoord pos) throws SlickException {
        super("res/player_left.png", pos);
    }

    // move player to a new position in the world
    public void move(Direction dir) {

    }

    public void update(Input input, int delta) {
        if (input.isKeyPressed(Input.KEY_UP)) {
            TileCoord pos = getPos();
//            if (!Loader.isBlocked(xPos, yPos - 1)) {
//                move(xPos, yPos - 1);
//            }
        }
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            TileCoord pos = getPos();
//            if (!Loader.isBlocked(xPos, yPos + 1)) {
//                move(xPos, yPos + 1);
//            }
        }
        if (input.isKeyPressed(Input.KEY_LEFT)) {
            TileCoord pos = getPos();
//            if (!Loader.isBlocked(xPos - 1, yPos)) {
//                move(xPos - 1, yPos);
//            }
        }
        if (input.isKeyPressed(Input.KEY_RIGHT)) {
            TileCoord pos = getPos();
//            if (!Loader.isBlocked(xPos + 1, yPos)) {
//                move(xPos + 1, yPos);
//            }
        }
    }
}
