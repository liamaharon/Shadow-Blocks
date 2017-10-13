package project;

import org.newdawn.slick.SlickException;

import java.io.Serializable;

/**
 * The stone block is a simple block. When the player or the rogue moves into a
 * tile occupied by the stone, the stone should move one tile in the same
 * direction as the unit which moved into it. If the stone cannot move in that
 * direction due to a wall, neither the stone nor the unit should move.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class Stone extends Block implements Serializable
{
    public Stone (TileCoord pos) throws SlickException
    {
        super("res/stone.png", pos);
    }
}
