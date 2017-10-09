package project;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class RegularSprite implements Renderable, Serializable
{
    // mark transient because slick's Image isn't serializable and we aren't
    // allowed to use any other libraries to help us handle it.. :( need to
    // reassign img when made null in the Copier class
    private transient Image img;
    private String imgSrc;
    private TileCoord pos;

    public RegularSprite(String imgSrc,TileCoord pos) throws SlickException {
        this.imgSrc = imgSrc;
        img = new Image(imgSrc);
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

    public void setImg(Image img) {
        this.img = img;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}
