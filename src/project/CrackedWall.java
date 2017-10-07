package project;

import org.newdawn.slick.SlickException;

public class CrackedWall extends SmartSprite {
    private static String explodingAnimationSrc = "res/explosion.png";
    private boolean isExploding;
    private int timeExploded;

    public CrackedWall(TileCoord pos) throws SlickException {
        super("res/cracked_wall", pos);
        isExploding = false;
    }

    public void explode() {

    }

    private void removeFromGame() {

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
