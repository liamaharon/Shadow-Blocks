package project1;

import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Sprite {
    private Image img;
    private int xPos;
    private int yPos;

    public Sprite(String src, int xPos, int yPos) throws SlickException {
        img = new Image(src);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void render() {
        // convert tile position to pixel position and draw to screen
        int xPixel = (xPos * App.TILE_SIZE) + (App.SCREEN_WIDTH / 2) - (Loader.getWorldDimensions()[0] * App.TILE_SIZE / 2);
        int yPixel = (yPos * App.TILE_SIZE) + (App.SCREEN_HEIGHT / 2) - (Loader.getWorldDimensions()[1] * App.TILE_SIZE / 2);
        img.draw(xPixel, yPixel);
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void update(Input input, int delta) {

    }

    public void move(int xPos, int yPos) {

    }
}