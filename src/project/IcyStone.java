package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class IcyStone extends Block {
    private final int QUARTER_SECOND_IN_MS = 250;
    private Direction directionMoving = Direction.NONE;
    private float timeLastMoved;

    public IcyStone(TileCoord pos) throws SlickException
    {
        super("res/ice.png", pos);
    }

    // need to record when icy stones are moved and in what direction, so
    // that we can make them slide after the initial push
    @Override
    public void move(TileCoord newPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        super.move(newPos, dir, levelManager);
        timeLastMoved = System.currentTimeMillis();
        directionMoving = dir;
    }

    // if the ice block is sliding and it hasn't moved in .25s, try to move it
    @Override
    public void update(Input input, LevelManager levelManager) throws SlickException
    {
        super.update(input, levelManager);
        if (
                directionMoving != Direction.NONE &&
                timeLastMoved <= System.currentTimeMillis() - QUARTER_SECOND_IN_MS
           )
        {
            // get the next position the ice wants to slide
        }
    }
}
