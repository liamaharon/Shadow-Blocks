package project;

import org.newdawn.slick.SlickException;

public class Tnt extends Block {

    public Tnt(TileCoord pos) throws SlickException
    {
        super("res/tnt.png", pos);
    }

    // Tnt moves like other blocks except when the position its moving to is a
    // cracked wall, in which case it explodes both itself and the cracked wall
    // a cracked wall in it it explodes both itself and the cracked wall
    @Override
    public void move(TileCoord newPos,
                     Direction dir,
                     LevelManager levelManager) throws SlickException
    {
        // moving into a cracked wall, explode wall and remove self from game
        if (levelManager.getCrackedWallFromCoord(newPos) != null)
        {
            levelManager.getCrackedWallFromCoord(newPos).explode(levelManager);
            levelManager.removeSpriteFromCurGameState(this);
            return;
        }

        // moving anywhere except a cracked wall, act as a normal block
        super.move(newPos, dir, levelManager);
    }


    // unlike all other blocks, Tnt is not blocked by cracked walls
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return  levelManager.getCrackedWallFromCoord(pos) != null ||
                super.canMoveTo(pos, levelManager);
    }
}
