package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Explosion extends SmartSprite {
    private long expireTime;

    public Explosion(TileCoord pos) throws SlickException
    {
        super("res/explosion.png", pos);
        // take note of the UNIX time in ms that we want to remove the
        // explosion from existence
        final int POINT_FOUR_SECONDS_IN_MS = 400;
        expireTime = System.currentTimeMillis() + POINT_FOUR_SECONDS_IN_MS;
    }

    // every update check if the explosion has existed for more than 0.4s.
    // if it has, remove it from the curGameState
    @Override
    public void update(Input input, LevelManager levelManager)
    {
        if (System.currentTimeMillis() >= expireTime)
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
