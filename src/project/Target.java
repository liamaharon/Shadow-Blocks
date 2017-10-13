package project;

import org.newdawn.slick.SlickException;

/**
 * All target tiles must be covered by a block for the level to be completed.
 * Units can move freely through this tile.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Target extends RegularSprite {
    public Target (TileCoord pos) throws SlickException {
        super("res/target.png", pos);
    }
}
