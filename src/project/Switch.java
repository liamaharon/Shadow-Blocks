package project;

import org.newdawn.slick.SlickException;

/**
 *The switch tile has the special effect that when a block is pushed onto it,
 * it “unlocks” the door tile. There will only be one switch tile and one door
 * tile in any given level. Once the block is pushed off the switch tile, the
 * door tile is locked again.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Switch extends RegularSprite {

    public Switch(TileCoord pos) throws SlickException {
        super("res/switch.png", pos);
    }
}
