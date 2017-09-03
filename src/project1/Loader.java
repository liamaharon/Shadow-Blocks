package project1;

import org.newdawn.slick.SlickException;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    // stores the world dimensions
    private static int[] worldDimensions = new int[2];
    // stores a 2D list of booleans, representing the solidity of each tile in the world
    private static List<List<Boolean>> tileSolidity = new ArrayList<>();
    // list of sprites loaded from loadSpritesFromFile
    private static List<Sprite> sprites = new ArrayList<>();
		
	/**
	 * Loads the sprites from a given file into a list. Adapted from Grok worksheet "Reading Files".
	 * @param filename String
	 * @return worldState ArrayList<Sprite>[][]
	 */
	public static List<Sprite> loadSpritesFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String text;
            Sprite sprite;

            // first line is always dimensions
            text = br.readLine();
            String[] dimensions = text.split(",");
            worldDimensions[0] = Integer.parseInt(dimensions[0]);
            worldDimensions[1] = Integer.parseInt(dimensions[1]);
            // now that we know the dimensions of the world, initialise the list detailing if a tile is blocked
            initialiseTileSolidity();

            // next lines give sprite details. parse each sprite adding to the world object and updating if it's solid
            while ((text = br.readLine()) != null) {
                // parse sprite, update the solidity of the world, add the sprite to the sprite list
                sprite = stringToSprite(text);
                updateTileSolidity(sprite);
                sprites.add(sprite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprites;
	}

    // returns true if tile is inaccessible to the player, due to being out of bounds or blocked by a wall
    public static boolean isBlocked(int xPos, int yPos) {
        return (
                xPos < 0 ||
                yPos < 0 ||
                xPos >= worldDimensions[0] ||
                yPos >= worldDimensions[1] ||
                tileSolidity.get(xPos).get(yPos)
        );

    }

	// if sprite is not a wall, unsolidify its tile
	private static void updateTileSolidity(Sprite s) {
	    if (!(s instanceof Wall)) {
            tileSolidity
                    .get(s.getxPos())
                    .set(s.getyPos(), false);
        }
    }

	// initialises every tile to blocked
	private static void initialiseTileSolidity() {
	    int xMax = worldDimensions[0];
	    int yMax = worldDimensions[1];

        for (int i=0; i<xMax; i++) {
            tileSolidity.add(new ArrayList<>());
	        for (int j=0; j<yMax; j++) {
                tileSolidity.get(i).add(true);
            }
        }
    }

    // takes a line in CSV form containing the details of a sprite and returns a sprite with those properties
    private static Sprite stringToSprite(String text) throws SlickException {
        String[] spriteDetails = text.split(",");

        String src;
        String spriteIdentifier = spriteDetails[0];
        int xPos = Integer.parseInt(spriteDetails[1]);
        int yPos = Integer.parseInt(spriteDetails[2]);

        // check sprites are not being created outside the game world
        if (xPos >= worldDimensions[0] || yPos >= worldDimensions[1]) {
            throw new IllegalArgumentException("Sprite coordinates out of bounds");
        }

        // because player's img does not match info loaded from the level, assign his src manually
        if (spriteIdentifier.equals("player")) {
            src = "res/player_left.png";
        } else {
            src = "res/" + spriteIdentifier + ".png";
        }

	    switch(spriteIdentifier) {
            case "floor": return new Floor(src,xPos,yPos);
            case "wall": return new Wall(src,xPos,yPos);
            case "stone": return new Stone(src,xPos,yPos);
            case "target": return new Target(src,xPos,yPos);
            case "player": return new Player(src,xPos,yPos);
            default: throw new IllegalArgumentException("Unknown Sprite: " + spriteIdentifier);
        }
    }

    public static int[] getWorldDimensions() {
        return worldDimensions;
    }
}
