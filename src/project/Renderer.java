package project;

import org.newdawn.slick.Image;

public abstract class Renderer {
    public static void renderToScreen(Image img, TileCoord pos, TileCoord worldDimensions) {
        // convert tile position to pixel position and draw to screen
        int xPixel = (pos.getX() * App.TILE_SIZE) + (App.SCREEN_WIDTH / 2) - (worldDimensions.getX() * App.TILE_SIZE / 2);
        int yPixel = (pos.getY() * App.TILE_SIZE) + (App.SCREEN_HEIGHT / 2) - (worldDimensions.getY() * App.TILE_SIZE / 2);
        img.draw(xPixel, yPixel);
    }
}
