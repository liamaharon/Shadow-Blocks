package project;

import java.io.File;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * World is in charge of storing level files, and deciding when to load
 * each level
 */
public class World {
    private File[] levels;
    private LevelManager curLevelManager;
    private int curLevelIndex = 0;

    /**
     * World loads level files from the loader on creation, and loads the
     * initial game level
     */
    public World() throws SlickException
    {
        // load in level files
        levels = Loader.getSortedLevels();

        // load up the first level
        curLevelManager = Loader.loadLevel(levels[curLevelIndex]);
    }

    /**
     * Loads the next level, if one exists
     */
    private void nextLevel() throws SlickException
    {
        // disallow trying to load a non-existent level
        if (curLevelIndex < levels.length-1)
        {
            curLevelIndex++;
            curLevelManager = Loader.loadLevel(levels[curLevelIndex]);
        }
    }

    /**
     * Called continuously by App. World passes this into the current
     * LevelManager to make changes to the level based on the update
     * @param input Represents user input made this update
     * @param delta Time since last update in ms
     */
    public void update(Input input, int delta) throws SlickException
    {
        // if level is completed, try to load the next level
        if (curLevelManager.getLevelComplete()) nextLevel();
        curLevelManager.update(input, delta);
    }

    /**
     * Called continuously by App. Signals sprites are ready to be rerendered.
     * World passes this to the current LevelManager to rerender the level
     * @param graphics object that can be used to draw custom things to
     *                 screen, ie a String
     */
    public void render(Graphics graphics)
    {
        curLevelManager.render(graphics);
    }
}
