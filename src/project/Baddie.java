package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Baddies represent units that should restart the game on contact with the
 * Player sprite
 */
public abstract class Baddie extends SmartSprite
{

    /**
     * Initialises a new Baddie
     * @param src The location of the Baddie's img src
     * @param pos The initial position of the baddie
     */
    public Baddie(String src, TileCoord pos) throws SlickException
    {
        super(src, pos);
    }

    /**
     * Baddies are also blocked by CrackedWalls
     * @param pos          The position being checked
     * @param levelManager The LevelManager managing the Baddie's level
     * @return             Boolean representing if the Baddie can move to the
     *                     supplied position
     */
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return super.canMoveTo(pos, levelManager) ||
               levelManager.getCrackedWallFromCoord(pos) != null;
    }
    /**
     * Overloading of the above method
     * @param pos          The position being checked
     * @param dir          The direction the baddie is moving
     * @param levelManager The LevelManager managing the Baddie's
     *                     level
     * @return             Boolean representing if the baddie can move to the
     *                     supplied position
     */
    @Override
    public boolean canMoveTo(TileCoord pos, Direction dir, LevelManager levelManager)
    {
        return super.canMoveTo(pos, levelManager) ||
                levelManager.getCrackedWallFromCoord(pos) != null;
    }

    /**
     * The Baddie tries to attack the player every update
     * @param levelManager The LevelManager managing the Baddie's level
     * @param input        Represents any input made this update
     * @param delta        Represents the time in ms since the last update was
     *                     made
     */
    @Override
    public void update(LevelManager levelManager, Input input, int delta) throws SlickException
    {
        attack(getPos(), levelManager);
    }

    /**
     * If the Baddie is in the same tile as the Player, restart the level
     * @param pos          The position we're looking for Player
     * @param levelManager The LevelManager managing the Baddie's level
     */
    private void attack(TileCoord pos, LevelManager levelManager)
    {
        if (levelManager.getCurGameState().getPlayerCoord().equals(pos))
        {
            levelManager.restartLevel();
        }
    }
}
