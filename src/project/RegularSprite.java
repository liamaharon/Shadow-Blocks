package project;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class RegularSprite {
    private Image img;
    private TileCoord pos;

    public RegularSprite(String src, TileCoord pos) throws SlickException {
        img = new Image(src);
        this.pos = pos;
    }

    public void render(TileCoord worldDimensions) {
        Renderer.renderToScreen(img, pos, worldDimensions);
    }

    public TileCoord getPos() {
        return pos;
    }
}