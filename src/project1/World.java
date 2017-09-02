package project1;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.List;

public class World {
	private static final String LVL_PLAN_LOC = "res/levels/0.lvl";
	private static int[] worldDimensions;
	private static ArrayList<List<List<Sprite>>> worldState; // represent world as a 2D array of sprites
	private static Sprite player;

	public World() {
        // load in world and locate player sprite
		worldState = Loader.loadWorldFromFile(LVL_PLAN_LOC);
		worldDimensions = Loader.getWorldDimensions();
		player = findPlayerSprite();
	}

	// player is the only sprite that currently can be updated. this may change in future versions of the game
	public void update(Input input, int delta) {
	    player.update(input, delta);
	}

    // renders each sprite from the 2D worldState array
    public void render(Graphics g) {
        for (List<List<Sprite>> x : worldState) {
            for (List<Sprite> y : x) {
                for (Sprite s : y) {
                    int xPos = (s.getxPos() * App.TILE_SIZE) + (App.SCREEN_WIDTH / 2) - (worldDimensions[0] * App.TILE_SIZE / 2);
                    int yPos = (s.getyPos() * App.TILE_SIZE) + (App.SCREEN_HEIGHT / 2) - (worldDimensions[1] * App.TILE_SIZE / 2);
                    s.getImg().draw(xPos, yPos);
                }
            }
        }
    }

    // returns true if tile is inaccessible to the player
    public static boolean isBlocked(int xPos, int yPos) {
	    if (xPos > worldDimensions[0] || yPos > worldDimensions[1]) {
	        return true;
        }
	    for (Sprite s : worldState.get(xPos).get(yPos)) {
            if (s.getSolid()) {
                return true;
            }
        }
        return false;
    }

    // loops through the 2D sprite array, returns the player sprite
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

    public static ArrayList<List<List<Sprite>>> getWorldState() {
        return worldState;
    }
}
