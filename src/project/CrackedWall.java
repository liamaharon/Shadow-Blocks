package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CrackedWall extends SmartSprite {

    public CrackedWall(TileCoord pos) throws SlickException
    {
        super("res/cracked_wall.png", pos);
    }

    // create an explosion at the sprites position, and remove from game
    public void explode(LevelManager levelManager) throws SlickException
    {
        levelManager.removeSpriteFromCurGameState(this);
        levelManager.createExplosion(this.getPos());
    }
}
