package project1;

import org.newdawn.slick.SlickException;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    // stores the world dimensions
    private static int[] worldDimensions = new int[2];
    // represents the game world. first two lists represent x,y, next list represents the sprites at those
    // coordinates. (can have stacked sprites)
    private static ArrayList<List<List<Sprite>>> worldState = new ArrayList<>();
		
	/**
	 * Loads the sprites from a given file and puts them into a single state representing the game world.
     * Adapted from Grok worksheet "Reading Files".
	 * @param filename String
	 * @return worldState ArrayList<Sprite>[][]
	 */
	public static ArrayList<List<List<Sprite>>> loadWorldFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String text;

            // first line is always dimensions
            text = br.readLine();
            String[] dimensions = text.split(",");
            worldDimensions[0] = Integer.parseInt(dimensions[0]);
            worldDimensions[1] = Integer.parseInt(dimensions[1]);
            // now that we know the dimensions of the world, initialise it
            initialiseWorldState();

            // next lines give sprite details. parse each sprite and add to the world object
            while ((text = br.readLine()) != null) {
                addSpriteToWorld(stringToSprite(text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return worldState;
	}

	// initialises world state to the dimensions of the world
	private static void initialiseWorldState() {
	    int xMax = worldDimensions[0];
	    int yMax = worldDimensions[1];

        for (int i=0; i<xMax; i++) {
            worldState.add(new ArrayList<>());
	        for (int j=0; j<yMax; j++) {
	            worldState.get(i).add(new ArrayList<>());
            }
        }
    }

    // adds sprite to its corresponding position in the game world
    private static void addSpriteToWorld(Sprite sprite) {
        int xPos = sprite.getxPos();
        int yPos = sprite.getyPos();
        worldState
                .get(xPos)
                .get(yPos)
                .add(sprite);
    }

    // takes a line in CSV form containing the details of a sprite and returns a sprite with those properties
    private static Sprite stringToSprite(String text) throws SlickException {
        String[] spriteDetails = text.split(",");

        String spriteIdentifier = spriteDetails[0];
        int xPos = Integer.parseInt(spriteDetails[1]);
        int yPos = Integer.parseInt(spriteDetails[2]);
        // assert sprites are not being created outside the game world
        if (xPos >= worldDimensions[0] || yPos >= worldDimensions[1]) {
            throw new IllegalArgumentException("Sprite coordinates out of bounds");
        }

        String src;
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
