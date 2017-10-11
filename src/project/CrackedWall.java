package project;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CrackedWall extends SmartSprite {
    private static String explodingAnimationSrc = "res/explosion.png";
    private boolean isExploding;
    private int timeExploded;

    public CrackedWall(TileCoord pos) throws SlickException {
        super("res/cracked_wall.png", pos);
        isExploding = false;
    }

    public void explode(LevelManager levelManager) {
        levelManager.removeSpriteFromCurGameState(this);
    }

    private void removeFromGame() {

    }

    @Override
    public void update(Input input, LevelManager levelManager) {
        super.update(input, levelManager);
    }

    @Override
    public void render(TileCoord worldDimensions) {
        super.render(worldDimensions);
    }
}
