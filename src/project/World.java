package project;

import java.io.File;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World {
    private File[] levels;
    private LevelManager curLevelManager;

    public World() throws SlickException {
        // load in level files
        levels = Loader.getSortedLevels();

        // load up each level and play!
        for (File level : levels) {
            curLevelManager = Loader.loadLevel(level);

        }
    }

    // continually update each sprite
    public void update(Input input, int delta) {
        // for (Sprite s : sprites) {
        //   s.update(input, delta);
        // }
    }

    // continually render each sprite
    // note Graphics instance g is redundant. it's here because I plan on using it in project 2. in the spec it says we
    // are allowed to write our code with project 2 in mind
    public void render(Graphics g) {
        // for (Sprite s : sprites) {
        //   s.render();
        // }
    }
}
