package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class IcyStone extends Block {
    private Direction directionMoving;
    private int timeLastMoved;

    public IcyStone(TileCoord pos) throws SlickException
    {
        super("res/ice.png", pos);
        directionMoving = Direction.NONE;
    }

    @Override
    public void update(Input input, LevelManager levelManager) throws SlickException
    {
        super.update(input, levelManager);
    }
}
