package project;

import org.newdawn.slick.SlickException;

/**
 * The cracked wall tile is identical to the wall tile, except that it can be
 * destroyed by pushing a TNT block into it.
 * Acknowledgement:
 * This game was designed and specification written by Eleanor McMurtry.
 */
public class CrackedWall extends SmartSprite {

    /**
     * Initialise the CrackedWall with its img source location
     * @param pos The initial position of the CrackedWall
     */
    public CrackedWall(TileCoord pos) throws SlickException
    {
        super("res/cracked_wall.png", pos);
    }

    /**
     * Removes the CrackedWall from the current GameState, leaving behind an
     * explosion
     * @param levelManager LevelManager managing the CrackedWall's level
     */
    public void explode(LevelManager levelManager) throws SlickException
    {
        levelManager.removeSpriteFromCurGameState(this);
        levelManager.createExplosion(this.getPos());
    }
}
