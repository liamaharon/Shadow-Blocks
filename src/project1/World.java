package project1;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.List;

public class World {
  private ArrayList<Files> levels;
  private int curLevelIndex;
  private static final String LVL_PLAN_LOC = "res/levels/0.lvl";
  private static List<Sprite> sprites;

  public World() {
    // load in sprites from the level plan
    sprites = Loader.loadSpritesFromFile(LVL_PLAN_LOC);
  }

  // continually update each sprite
  public void update(Input input, int delta) {
    for (Sprite s : sprites) {
      s.update(input, delta);
    }
  }

  // continually render each sprite
  // note Graphics instance g is redundant. it's here because I plan on using it in project 2. in the spec it says we
  // are allowed to write our code with project 2 in mind
  public void render(Graphics g) {
    for (Sprite s : sprites) {
      s.render();
    }
  }
}
