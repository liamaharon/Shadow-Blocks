package project;

import org.newdawn.slick.SlickException;

public abstract class Baddie extends SmartSprite {

    public Baddie(String src, TileCoord pos) throws SlickException {
        super(src, pos);
    }

    @Override
    public void update() {
        super.update();
    }

    private void attack() {

    }

}
