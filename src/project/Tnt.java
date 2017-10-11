package project;

import org.newdawn.slick.SlickException;

public class Tnt extends Block {

    public Tnt(TileCoord pos) throws SlickException {
        super("res/tnt.png", pos);
    }

    // Tnt moves like other blocks except when the position its moving to is a
    // cracked wall, in which case it explodes both itself and the cracked wall
    // a cracked wall in it it explodes both itself and the cracked wall
    @Override
    public void move(TileCoord newPos, LevelManager levelManager) throws SlickException
    {
        // moving into a cracked wall, explode wall and remove self from game
        if (levelManager.getCrackedWall(newPos) != null)
        {
            levelManager.getCrackedWall(newPos).explode(levelManager);
            levelManager.removeSpriteFromCurGameState(this);
            return;
        }

        // moving anywhere except a cracked wall, act as a normal block
        super.move(newPos, levelManager);
    }


    // Tnt is blocked by everything normal blocks are, except cracked walls.
    // so if the block is a cracked wall, return true, else return the result
    // of regular block checks
    @Override
    public boolean canMoveTo(TileCoord pos, LevelManager levelManager)
    {
        return  levelManager.getCrackedWall(pos) != null ||
                super.canMoveTo(pos, levelManager);
    }

    private void collideWithCrackedWall(CrackedWall wall) {

    }
}
