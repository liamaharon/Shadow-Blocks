package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Mage extends Baddie {

    public Mage(TileCoord pos) throws SlickException {
        super("res/mage.png", pos);
    }

    @Override
    public void update(Input input, LevelManager levelManager) {
        super.update(input, levelManager);
    }
}
