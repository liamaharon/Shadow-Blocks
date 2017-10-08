package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Tnt extends Block {

    public Tnt(TileCoord pos) throws SlickException {
        super("res/tnt.png", pos);
    }

    @Override
    public void update(Input input, LevelManager levelManager) {
        super.update(input, levelManager);
    }


    private void collideWithCrackedWall(CrackedWall wall) {

    }

    private void removeFromGame() {

    }
}
