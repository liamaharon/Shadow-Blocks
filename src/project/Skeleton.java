package project;

import org.newdawn.slick.SlickException;

public class Skeleton extends Baddie {
    private Direction directionMoving;

    public Skeleton(TileCoord pos) throws SlickException {
        super("res/skeleton.png", pos);
        directionMoving = Direction.UP;
    }

    @Override
    public void update() {
        super.update();
    }
}
