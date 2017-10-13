package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The explosion effect is a sprite that should be displayed for 0.4 seconds,
 * then be destroyed.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Explosion extends SmartSprite {
    // records how long the explosion has existed for
    private int msHasExisted = 0;

    /**
     * Initialise a new explosion at the specified position
     * @param pos Initial position
     */
    public Explosion(TileCoord pos) throws SlickException
    {
        super("res/explosion.png", pos);
    }

    /**
     * Every update, update msHasExisted and check if the explosion has
     * existed for more than 0.4s. If it has, remove it from the curGameState
     * @param levelManager The LevelManager managing the Explosion's level
     * @param input        Represents any input made this update
     * @param delta        Represents the time in ms since the last update was
     *                     made
     */
    @Override
    public void update(LevelManager levelManager, Input input, int delta)
    {
        final int POINT_FOUR_SECONDS_IN_MS = 400;
        msHasExisted += delta;
        // remove explosion when it has existed to over 0.4s
        if (msHasExisted >= POINT_FOUR_SECONDS_IN_MS)
        {
            levelManager.removeSpriteFromCurGameState(this);
        }
    }
}
