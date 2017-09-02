package project1;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Sprite {
    public Player (String src, int xPos, int yPos) throws SlickException {
        super(src, xPos, yPos, false);
    }

    public void move(int xPos, int yPos) {
        World.worldState
                .get(getxPos())
                .get(getyPos())
                .remove(this);
        World.worldState
                .get(xPos)
                .get(yPos)
                .add(this);
        setxPos(xPos);
        setyPos(yPos);
    }

    public void update(Input input, int delta) {
        if (input.isKeyPressed(Input.KEY_UP)) {
            int xPos = getxPos();
            int yPos = getyPos();
            if (!World.isBlocked(xPos, yPos - 1)) {
                move(xPos, yPos - 1);
            }
        }
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            int xPos = getxPos();
            int yPos = getyPos();
            if (!World.isBlocked(xPos, yPos + 1)) {
                move(xPos, yPos + 1);
            }
        }
        if (input.isKeyPressed(Input.KEY_LEFT)) {
            int xPos = getxPos();
            int yPos = getyPos();
            if (!World.isBlocked(xPos - 1, yPos)) {
                move(xPos - 1, yPos);
            }
        }
        if (input.isKeyPressed(Input.KEY_RIGHT)) {
            int xPos = getxPos();
            int yPos = getyPos();
            if (!World.isBlocked(xPos + 1, yPos)) {
                move(xPos + 1, yPos);
            }
        }
    }
}
