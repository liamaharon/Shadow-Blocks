package project1;

import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite {
	private Image img;
	private float xTile;
	private float yTile;

	public Sprite(String src, float xTile, float yTile) throws SlickException {
		img = new Image(src);
		this.xTile = xTile;
		this.yTile = yTile;
	}
	
	public void update(Input input, int delta) {
	}
	
	public void render(Graphics g) {
	    float xWorld = (xTile + Loader.tileDimensions[0] / 2) * App.TILE_SIZE;
	    float yWorld = (yTile + Loader.tileDimensions[1] / 2) * App.TILE_SIZE;

	    img.draw(xWorld, yWorld);
	}
}
