package project1;

import org.newdawn.slick.SlickException;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

public class Loader {
    // stores the dimensions of tile board
    public static int[] tileDimensions = new int[2];

	// Converts a world coordinate to a tile coordinate,
	// and returns if that location is a blocked tile
	public static boolean isBlocked(float x, float y) {
		// Default to blocked
		return true;
	}
		
	/**
	 * Loads the sprites from a given file. Adapted from Grok worksheet "Reading Files".
	 * @param filename
	 * @return
	 */
	public static List<Sprite> loadSprites(String filename) {

        List<Sprite> sprites = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filename))) {

            String text;

            // first line is always dimensions
            text = br.readLine();
            String[] dimensions = text.split(",");
            tileDimensions[0] = Integer.parseInt(dimensions[0]);
            tileDimensions[1] = Integer.parseInt(dimensions[1]);

            // next lines give sprite details
            while ((text = br.readLine()) != null) {
                sprites.add(stringToSprite(text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sprites;

	}

    // takes a line from CSV containing the details of a sprite and returns a sprite with those properties
	private static Sprite stringToSprite(String text) throws SlickException {
        String[] spriteDetails = text.split(",");

        String src;
        // because player's img does not match info loaded from the level, assign his src manually
        if (spriteDetails[0].equals("player")) {
            src = "res/player_left.png";
        } else {
            src = "res/" + spriteDetails[0] + ".png";
        }
	    float xTile = Float.parseFloat(spriteDetails[1]);
	    float yTile = Float.parseFloat(spriteDetails[2]);

        Sprite sprite = new Sprite(src,xTile,yTile);

	    return sprite;
    }


}
