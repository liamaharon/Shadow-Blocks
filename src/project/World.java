package project;

import java.io.File;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
    private File[] levels;
    private LevelManager curLevelManager;
    private int curLevelIndex = 0;

    public World() throws SlickException
    {
        // load in level files
        levels = Loader.getSortedLevels();

        // load up the first level!
        curLevelManager = Loader.loadLevel(levels[curLevelIndex]);
    }

    // continually update each sprite
    public void update(Input input) {
        curLevelManager.update(input);
    }

    // curLevelManager handles how it's rendered
    public void render(Graphics g) throws SlickException
    {
        curLevelManager.render();
    }
}
