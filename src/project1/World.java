package project1;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.List;


public class World {

    private List<Sprite> sprites = new ArrayList<>();

    private static final String LVL_PLAN_LOC = "res/levels/0.lvl";

	public World() {
        // load level plan into game world
		sprites = Loader.loadSprites(LVL_PLAN_LOC);
//		System.out.print(sprites);

	}
	
	public void update(Input input, int delta) {

	}
	
	public void render(Graphics g) {
        for (Sprite sprite: sprites) {
            sprite.render(g);
        }
	}
}
