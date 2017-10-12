package project;

import java.io.File;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
    private File[] levels;
    private LevelManager curLevelManager;
    private int curLevelIndex = 1;

    public World() throws SlickException
    {
        // load in level files
        levels = Loader.getSortedLevels();

        // load up the first level
        curLevelManager = Loader.loadLevel(levels[curLevelIndex]);
    }

    private void nextLevel() throws SlickException
    {
        // disallow trying to load a non-existent level
        if (curLevelIndex < levels.length-1)
        {
            curLevelIndex++;
            curLevelManager = Loader.loadLevel(levels[curLevelIndex]);
        }
    }

    // continually update each sprite until level is completed
    public void update(Input input, int delta) throws SlickException
    {
        if (curLevelManager.getLevelComplete()) nextLevel();
        curLevelManager.update(input, delta);
    }

    // curLevelManager handles how the current level is rendered
    public void render(Graphics graphics) throws SlickException
    {
        curLevelManager.render(graphics);
    }
}
