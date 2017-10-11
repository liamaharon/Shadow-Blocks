package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Door extends SmartSprite {
    private boolean isOpen;

    public Door(TileCoord pos) throws SlickException {
        super("res/door.png", pos);
        isOpen = false;
    }

    @Override
    public void update(Input input, LevelManager levelManager) throws SlickException
    {
        super.update(input, levelManager);
    }

    @Override
    public void render(TileCoord worldDimensions) {
        super.render(worldDimensions);
    }
}
