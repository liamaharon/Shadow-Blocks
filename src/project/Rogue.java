package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Rogue extends Baddie {

    public Rogue(TileCoord pos) throws SlickException {
        super("res/rogue.png", pos);
    }

    @Override
    public void update(Input input, LevelManager levelManager) {
        super.update(input, levelManager);
    }
}
