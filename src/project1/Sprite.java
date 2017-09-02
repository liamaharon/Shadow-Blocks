package project1;

import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Sprite {
    private Image img;
    private int xPos;
    private int yPos;
    private boolean solid;

    public Sprite(String src, int xPos, int yPos, boolean solid) throws SlickException {
        img = new Image(src);
        this.xPos = xPos;
        this.yPos = yPos;
        this.solid = solid;
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

    public Image getImg() {
        return img;
    }

    public boolean getSolid() {
        return solid;
    }

    public void update(Input input, int delta) {

    }

    public void move(int xPos, int yPos) {

    }
}