package project;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class RegularSprite implements Renderable, Serializable
{
    // mark transient because slick's Image isn't serializable and we aren't
    // allowed to use any other libraries to help us handle it.. :(
    private transient Image img;
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
