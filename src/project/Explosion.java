package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Explosion extends SmartSprite {
    // records how long the explosion has existed for
    private int msHasExisted = 0;

    public Explosion(TileCoord pos) throws SlickException
    {
        super("res/explosion.png", pos);
    }

    // every update, update msHasExisted and check if the explosion has
    // existed for more than 0.4s. if it has, remove it from the curGameState
    @Override
    public void update(LevelManager levelManager, Input input, int delta)
    {
        final int POINT_FOUR_SECONDS_IN_MS = 400;
        msHasExisted += delta;
        if (msHasExisted >= POINT_FOUR_SECONDS_IN_MS)
        {
            levelManager.removeSpriteFromCurGameState(this);
        }
    }

    @Override
    public void render(TileCoord levelDimensions)
    {
       super.render(levelDimensions);
    }
}
