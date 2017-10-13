package project;

import org.newdawn.slick.SlickException;

/**
 * The floor tile has no special effects. Units can move freely through this
 * tile.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Floor extends RegularSprite {
    public Floor (TileCoord pos) throws SlickException {
        super("res/floor.png", pos);
    }
}
