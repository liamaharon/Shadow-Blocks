package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Skeleton extends Baddie {
    private Direction directionMoving;

    public Skeleton(TileCoord pos) throws SlickException {
        super("res/skull.png", pos);
        directionMoving = Direction.UP;
    }
}
