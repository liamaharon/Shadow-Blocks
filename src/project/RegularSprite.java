package project;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

/**
 * RegularSprites are 'dumb'. They never change after being initialised.
 */
public abstract class RegularSprite implements Renderable, Serializable
{
    // mark transient because slick's Image isn't serializable and we aren't
    // allowed to use any other libraries to help us handle it. need to
    // reassign img when made null in the DeepCopier class
    private transient Image img;
    private String imgSrc;
    private TileCoord pos;

    /**
     * Initialise the RegularSprite with the provided Image, and position
     * @param imgSrc The location of where the sprites image can be found
     * @param pos    The initial position of the sprite
     */
    public RegularSprite(String imgSrc, TileCoord pos) throws SlickException {
        this.imgSrc = imgSrc;
        img = new Image(imgSrc);
        this.pos = pos;
    }

    /**
     * Draws itself to the screen
     * @param levelDimensions The dimensions of the level the sprite exists in
     */
    public void render(TileCoord levelDimensions) {
        drawToScreen(img, pos, levelDimensions);
    }

    /**
     * @return The sprite's position
     */
    public TileCoord getPos() {
        return pos;
    }

    /**
     * Called by SmartSprites, this allows them to set themselves a new position
     * @param pos The new position to set
     */
    public void setPos(TileCoord pos) {
        this.pos = pos;
    }

    /**
     * Called by the DeepCopier class when it needs to reassign the sprite's
     * img
     * @param img The Image to be set
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * @return Returns the location of the image source
     */
    public String getImgSrc() {
        return imgSrc;
    }
}
