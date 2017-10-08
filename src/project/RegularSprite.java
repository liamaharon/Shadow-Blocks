package project;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class RegularSprite implements Renderable
{
    private Image img;
    private TileCoord pos;

    public RegularSprite(String src, TileCoord pos) throws SlickException
    {
        img = new Image(src);
        this.pos = pos;
    }

    public void render(TileCoord levelDimensions) {
       drawToScreen(img, pos, levelDimensions);
    }

    public TileCoord getPos() {
        return pos;
    }

    public void setPos(TileCoord pos) {
        this.pos = pos;
    }
}
