package project;

import org.newdawn.slick.SlickException;

/**
 * Represents a Wall Tile.
 * The wall tile has no special effects. Units cannot move through this tile.
 * Acknowledgement
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Wall extends RegularSprite {
    public Wall (TileCoord pos) throws SlickException {
        super("res/wall.png", pos);
    }
}
