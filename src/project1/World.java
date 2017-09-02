package project1;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.List;


public class World {

	private static final String LVL_PLAN_LOC = "res/levels/0.lvl";
	private static int[] worldDimentions;
	public static ArrayList<List<List<Sprite>>> worldState;
	private static Sprite player;

	public World() {
        // load world state from file
		worldState = Loader.loadWorldFromFile(LVL_PLAN_LOC);
		worldDimentions = Loader.getWorldDimensions();
		player = findPlayerSprite();
	}
	
	public void update(Input input, int delta) {
	    player.update(input, delta);
	}

    // renders each sprite in the game world
    public void render(Graphics g) {
        for (List<List<Sprite>> x : worldState) {
            for (List<Sprite> y : x) {
                for (Sprite s : y) {
                    int xPos = (s.getxPos() * App.TILE_SIZE) + (App.SCREEN_WIDTH / 2) - (worldDimentions[0] * App.TILE_SIZE / 2);
                    int yPos = (s.getyPos() * App.TILE_SIZE) + (App.SCREEN_HEIGHT / 2) - (worldDimentions[1] * App.TILE_SIZE / 2);
                    s.getImg().draw(xPos, yPos);
                }
            }
        }
    }

    public static boolean isBlocked(int xPos, int yPos) {
	    if (xPos > worldDimentions[0] || yPos > worldDimentions[1]) {
	        return true;
        }
	    for (Sprite s : worldState.get(xPos).get(yPos)) {
            if (s.getSolid()) {
                return true;
            }
        }
        return false;
    }

    private Sprite findPlayerSprite() {
        for (List<List<Sprite>> x : worldState) {
            for (List<Sprite> y : x) {
                for (Sprite s : y) {
                    if (s instanceof Player) {
                        return s;
                    }
                }
            }
        }
        throw new java.lang.Error("Player not found in world state");
    }
}
