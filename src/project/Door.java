package project;

import org.newdawn.slick.SlickException;

public class Door extends SmartSprite {
    private boolean isOpen;

    public Door(TileCoord pos) throws SlickException {
        super("res/door.png", pos);
        isOpen = false;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(TileCoord worldDimensions) {
        super.render(worldDimensions);
    }
}
